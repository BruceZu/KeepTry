//  Copyright 2021 The KeepTry Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package map;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Stripe {
  /*
  Question comes from https://www.1point3acres.com/bbs/thread-668514-1-1.html

  4 actions
    - 1 CHARGE, customer-> merchant
      2 CONFIRM <-mutually exclusive->REFUND,


      CHARGE->CONFIRM: approved. no refund
      CHARGE->REFUND, no confirm
      3 REFUND: refunded charge's amount should not be included
        in the payout balance. but the card network processing fee
        should be deduced from the payout balance. strip processing
        fee not be deduced in this case

      4 PAYOUT:  stripe-> merchant.
                up until that point of receiving the action
                print the merchant's balance from beginning
                or the previous payout action if any
                then merchant payout balance is reset to 0

                include only confirmed charges
                after deducing card network and stripe
                processing fee
                Strip has a fixed processing fee of 2%.


     Input Format
     N
     CARD_NETWORK<space>PERCENT
     no duplicated entries for the same card network

     M
     actions

   Constrains
     2<=N<=5 (Integer)
     0<=M<=100(Integer)
     0.1<=PERCENT<=10.0 (Float)
     CARD_NETWORK (String)

   Actions:
     /charge?network=<CARD_NETWORK>&amount=<AMOUNT>&merchant_id=<MERCHANT_ID>&charge_id=<CHARGE_ID>

     network: credit card String
     amount: 0<=AMOUNT<=4,294,967,295 Integer
     merchant_id: String
     charge_id:String

     /payout?merchant_id=<MERCHANT_ID>

     /confirm?charge_id=<CHARGE_ID>
     /refund?charge_id=<CHARGE_ID>

  Comments on the input
     - All actions are well-formed URLs. need not verifying
     the ordering of query parameters could change
     - charge_id is unique
     - input action are properly ordered. refund or confirm will not
       occur before the charge action itself
     - charge action only use the card network whose processing fee
       percentage is specified in the input

  Output
     for each payout action in the input

      merchant ID, payout balance.
      rounded *up* to the nearest whole number integer
      do not round the numbers after each action. Only the final
      number to be printed


   Example
      2
      visa 2.0
      mastercard 3.0
      3
      /charge?network=visa&amount=100&merchant_id=m001&charge_id=c001
      /confirm?charge_id=c001
      /payout?merchant_id=m001

      output
      m001, 96
      Stripe precessing fee is fixed 2%

      --------
      2
      visa 2.0
      mastercard 3.0
      5
      /charge?network=visa&amount=100&merchant_id=m001&charge_id=c001
      /charge?merchant_id=m001&amount=56network=mastercard&charge_id=c002
      /refund?charge_id=c001
      /confirm?charge_id=c002
      /payout?merchant_id=m001

      output
      m001, 52
      Stripe precessing fee is fixed 2%
      for c001: refund,
                but need pay visa fee
                -0.02*100=-2
      for c002: confirmed
                +56*(1-0.03+0.02)=53.2
      So left   51.2
      round_up(51.2)=52


     --------
      2
      visa 2.0
      mastercard 3.0
      8
      /charge?merchant_id=m001&charge_id=c001&amount=1000&network=visa
      /charge?merchant_id=m001&charge_id=c002&amount=1000&network=mastercard
      /confirm?charge_id=c001
      /confirm?charge_id=c002
      /payout?merchant_id=m001

      /charge?merchant_id=m001&charge_id=c003&amount=1000&network=visa
      /confirm?charge_id=c003
      /payout?merchant_id=m001

      output
       m001, 1910
       m001, 960

     Explain
       +1000-20-20
       +1000-20-30
       total 1910

       +1000-20-20
       total 960

     Followup
     merchant has so many refund
     so that the balance is negative, how to handle it???
   */

  public void processActions(List<String> lines) {
    int i = 1;
    for (; i <= Integer.valueOf(lines.get(0)); i++) {
      String[] kv = lines.get(i).split(" ");
      fee.put(kv[0], Float.valueOf(kv[1]));
    }
    i++;
    for (; i < lines.size(); i++) {
      parse(lines.get(i));
    }
  }

  private void parse(String action) {
    String[] a = action.split("\\?");
    String[] ps = a[1].split("&");
    Map<String, String> map = new HashMap<>();
    for (String p : ps) {
      String[] kv = p.split("=");
      map.put(kv[0], kv[1]);
    }

    String k = a[0].substring(1);
    switch (k) {
      case "charge":
        charge(map.get("merchant_id"), map.get("charge_id"), map.get("network"), map.get("amount"));
        break;
      case "refund":
        refund(map.get("charge_id"));
        break;
      case "confirm":
        confirm(map.get("charge_id"));
        break;
      case "payout":
        payout(map.get("merchant_id"));
        break;
      default:
    }
  }

  private static float stripFee = 2.0f;
  private Map<String, Float> fee;

  private Map<String, BigDecimal> payout;

  private Map<String, String> chargeOf;
  private Map<String, String> charges;
  private Map<String, String> chargeCard;

  public Stripe() {
    fee = new HashMap<>();

    payout = new HashMap<>();
    chargeOf = new HashMap<>();
    charges = new HashMap<>();
    chargeCard = new HashMap<>();
  }

  private void charge(String merchat_id, String charge_id, String network, String amount) {
    chargeOf.put(charge_id, merchat_id);
    chargeCard.put(charge_id, network);
    charges.put(charge_id, amount);
  }

  private void refund(String chargeId) {
    String merchant = chargeOf.get(chargeId);
    payout.putIfAbsent(merchant, BigDecimal.ZERO);
    BigDecimal balance = payout.get(merchant);
    // what happened if the fist charge is refunded and no money in merchant payout balance

    BigDecimal charge = new BigDecimal(charges.get(chargeId));
    BigDecimal rate = BigDecimal.valueOf(fee.get(chargeCard.get(chargeId)));
    BigDecimal reduce = charge.multiply(rate).divide(BigDecimal.valueOf(100.00));
    payout.put(merchant, balance.subtract(reduce));
    // charges.remove(chargeId);
  }

  private void confirm(String chargeId) {
    String id = chargeId;
    String m = chargeOf.get(id);
    payout.putIfAbsent(m, BigDecimal.ZERO);
    BigDecimal b = payout.get(m);

    // what happened if the fist charge is refunded and no money in merchant payout balance
    BigDecimal charge = new BigDecimal(charges.get(id));
    BigDecimal rates =
        BigDecimal.valueOf(fee.get(chargeCard.get(id))).add(BigDecimal.valueOf(stripFee));
    BigDecimal reduce = charge.multiply(rates).divide(BigDecimal.valueOf(100.00));
    payout.put(m, b.add(charge.subtract(reduce)));

    //  charges.remove(chargeId);
  }

  private void payout(String merchant) {
    String m = merchant;
    payout.putIfAbsent(m, BigDecimal.ZERO);
    System.out.println(m + " " + payout.get(m).setScale(0, RoundingMode.CEILING));
    payout.put(m, BigDecimal.ZERO);
  }

  public static void main(String[] args) {
    List<String> lines = new LinkedList<>();
    lines.add("2");
    lines.add("visa 2.0");
    lines.add("mastercard 3.0");
    lines.add("3");
    lines.add("/charge?network=visa&amount=100&merchant_id=m001&charge_id=c001");
    lines.add("/confirm?charge_id=c001");
    lines.add("/payout?merchant_id=m001");
    Stripe t = new Stripe();
    t.processActions(lines);

    // m001, 96
    lines = new LinkedList<>();
    lines.add("2");
    lines.add("visa 2.0");
    lines.add("mastercard 3.0");
    lines.add("5");
    lines.add("/charge?network=visa&amount=100&merchant_id=m001&charge_id=c001");
    lines.add("/charge?merchant_id=m001&amount=56&network=mastercard&charge_id=c002");
    lines.add("/refund?charge_id=c001");
    lines.add("/confirm?charge_id=c002");
    lines.add("/payout?merchant_id=m001");
    t = new Stripe();
    t.processActions(lines);

    // m001, 52
    lines = new LinkedList<>();
    lines.add("2");
    lines.add("visa 2.0");
    lines.add("mastercard 3.0");
    lines.add("8");
    lines.add("/charge?merchant_id=m001&charge_id=c001&amount=1000&network=visa");
    lines.add("/charge?merchant_id=m001&charge_id=c002&amount=1000&network=mastercard");
    lines.add("/confirm?charge_id=c001");
    lines.add("/confirm?charge_id=c002");
    lines.add("/payout?merchant_id=m001");

    lines.add("/charge?merchant_id=m001&charge_id=c003&amount=1000&network=visa");
    lines.add("/confirm?charge_id=c003");
    lines.add("/payout?merchant_id=m001");
    t = new Stripe();
    t.processActions(lines);

    // m001, 1910
    // m001, 960
  }
}
