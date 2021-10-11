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

import java.util.*;

public class Capital {
  /*
     Question comes from strip capital
     https://www.1point3acres.com/bbs/thread-795712-1-1.html

     load:  charges a fixed load fee on top of the original loan amount.
     some percentage of the merchant's future sales goes towards repayment, until the total owed amount is repaid

     Design a bookkeeping system for a modified version of Stripe Capital.
     4 API
      1 merchant create a loan
      2 merchant pay down a loan manually
      3 merchant process transactions, from which some percentage of the processed amount goes towards repayment towards
        a loan
      4 merchant increase an existing loan's amount.


    Task:
      evaluate each line of stdin: API method: comma seprated parameters for the API
      all evaluating all actions, print out a list of $merchant_id, $outstanding_debt pairs
          skipping over merchants who do not have an outstanding balance.
          the list should be lexicographically sorted by the merchant ID

   Keep in mind:
     input format are validate and parsable
     code should be correct and with good quality

  System Behavior
     - The version of Capital will represent all monetary amount as U.S. cents in integers
       e.g. amount =1000 => $10.00 USD
     - A merchant may have multiple outstanding loans
     - loan IDs are unique to a given merchant only

     - a loan's outstanding balance should never to negative. ignore the remaining amount in
       the casing of overpayment.
     - after a loan is fully paid off it becomes inactive and a merchant can not increase its amount
     - truncate repayments when applicable. e.g. if withholding from a transaction is 433.64 cents
       truncate to 433 cents
     - handle invalid API actions, ex: attempting to pay-off a nonexistent loan
     */

  Map<String, Map<String, Integer>> book;

  public Capital() {
    book = new HashMap<>();
  }

  /*
  Assume input is ready:
      List<String> lines=new LinkedList<>();
      try(BufferedReader br=  Files.newBufferedReader(Paths.get(""))){
       String line;
       while((line=br.readLine())!=null){
        lines.add(line);
     }
     evaluateAndPrint(lines);
   }
   */
  public void evaluateAndPrint(List<String> lines) {
    for (String line : lines) {
      String[] fp = line.split(": ");
      String[] ps = fp[1].split(",");
      switch (fp[0]) { // use reflection?
        case "CREATE_LOAN":
          validParameters("CREATE_LOAN", ps, 3);
          createLoad(ps[0], ps[1], Integer.valueOf(ps[2]));
          break;
        case "PAY_LOAN":
          validParameters("PAY_LOAN", ps, 3);
          payLoan(ps[0], ps[1], Integer.valueOf(ps[2]));
          break;

        case "INCREASE_LOAN":
          validParameters("INCREASE_LOAN", ps, 3);
          increase_loan(ps[0], ps[1], Integer.valueOf(ps[2]));
          break;
        case "TRANSACTION_PROCESSED":
          validParameters("TRANSACTION_PROCESSED", ps, 4);
          transaction_processed(ps[0], ps[1], Integer.valueOf(ps[2]), Integer.valueOf(ps[3]));
          break;
        default:
          throw new RuntimeException("not defined API");
      }
    }
    print();
  }

  private void validParameters(String api, String[] ps, int expected) {
    if (ps.length != expected) throw new RuntimeException(api + " API parameters number is wrong");
  }
  /*
   lexicographically sorted by the merchant ID
  */
  private void print() {
    Map<String, Integer> r = new TreeMap<>();
    for (String m : book.keySet()) {
      int total = 0;
      for (int av : book.get(m).values()) {
        total += av;
      }
      if (total > 0) r.put(m, total);
    }
    for (Map.Entry<String, Integer> e : r.entrySet()) {
      System.out.println(e.getKey() + " " + e.getValue());
    }
  }

  /*
  merchant_id is non-empty
  loan_id is non-empty
  amount:  >=0, initial loan amount
  Ex:  CREATE_LOAD: merchant1, loan1, 1000
   */
  public void createLoad(String merchant_id, String loan_id, Integer amount) {
    book.putIfAbsent(merchant_id, new HashMap<>());
    if (book.get(merchant_id).containsKey(loan_id))
      throw new RuntimeException("account exists already");
    book.get(merchant_id).put(loan_id, amount);
  }

  /*
  Merchant pays off their loan on a one-time basis
   merchant_id is non-empty
   loan_id is non-empty
   amount:  >=0, The amount given back to Stripe
  Ex: PAY_LOAN: merchant1, loan1, 1000
   */
  public void payLoan(String merchant_id, String loan_id, Integer amount) {
    validate(merchant_id, loan_id);
    int cur = book.get(merchant_id).get(loan_id);
    book.get(merchant_id).put(loan_id, cur - amount);
    if (cur - amount <= 0) {
      book.get(merchant_id).remove(loan_id); // inactive and disappear.
    }
  }
  /*
  Merchant increases an existing loan
  merchant_id is non-empty
  loan_id is non-empty
  amount:  >=0, the  amount to increase the loan by
  Ex: INCREASE_LOAN: merchant1, loan1, 100
   */
  public void increase_loan(String merchant_id, String loan_id, Integer amount) {
    validate(merchant_id, loan_id);
    int cur = book.get(merchant_id).get(loan_id);
    if (cur + amount < 0) throw new RuntimeException(" too much to afford the loan");
    book.get(merchant_id).put(loan_id, cur + amount);
  }
  /*
   A single transaction,
   A portion of the transaction amount is withheld to pay down the merchant's outstanding loans.
   merchant_id is non-empty
   loan_id is non-empty
   amount:  >=0, the amount of transaction processed
   repayment_percentage: the percentage of the transaction amount that goes towards repayment
                         1<=x<=100
   Ex: TRANSACTION_PROCESSED: merchant1, loan1, 500, 10
  */
  public void transaction_processed(
      String merchant_id, String loan_id, Integer amount, Integer repayment_percentage) {
    validate(merchant_id, loan_id);
    int cur = book.get(merchant_id).get(loan_id);
    int pay = amount * repayment_percentage / 100;
    book.get(merchant_id).put(loan_id, cur - pay);
    if (cur - pay <= 0) {
      book.get(merchant_id).remove(loan_id); // inactive and disappear.
    }
  }

  private void validate(String merchant_id, String loan_id) {
    if (!book.containsKey(merchant_id) && book.get(merchant_id).containsKey(loan_id))
      throw new RuntimeException("account not exists or be inactive already");
  }

  public static void main(String[] args) {
    Capital t = new Capital();
    List<String> input = new ArrayList<>();
    input.add("CREATE_LOAN: acct_foobar,loan1,5000");
    input.add("PAY_LOAN: acct_foobar,loan1,1000");
    List<String> output = new ArrayList<>();
    t.evaluateAndPrint(input);
    output.add("acct_foobar,4000");

    input = new ArrayList<>();
    input.add("CREATE_LOAN: acct_foobar,loan1,5000");
    input.add("CREATE_LOAN: acct_foobar,loan2,5000");
    input.add("TRANSACTION_PROCESSED: acct_foobar,loan1,500,10");
    input.add("TRANSACTION_PROCESSED: acct_foobar,loan2,500,1");
    t = new Capital();
    t.evaluateAndPrint(input);
    output = new ArrayList<>();
    output.add("acct_foobar,9945");

    input = new ArrayList<>();
    input.add("CREATE_LOAN: acct_foobar,loan1,1000");
    input.add("CREATE_LOAN: acct_foobar,loan2,2000");
    input.add("CREATE_LOAN: acct_barfoo,loan1,3000");
    input.add("TRANSACTION_PROCESSED: acct_foobar,loan1,100,1");
    input.add("PAY_LOAN: acct_barfoo,loan1,1000");
    input.add("INCREASE_LOAN: acct_foobar,loan2,1000");
    t = new Capital();
    t.evaluateAndPrint(input);
    output = new ArrayList<>();
    output.add("acct_barfoo,2000");
    output.add("acct_foobar,3999");
  }
}
