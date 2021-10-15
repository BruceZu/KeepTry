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

package array;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Points {
  /*
    Fetch Rewards Coding Exercise - Backend Software Engineering
    What do I need to submit?
    Please write a web service that accepts HTTP requests and returns responses based on the conditions outlined in the next
    section. You can use any language and frameworks you choose.
    We must be able to run your web service; provide any documentation necessary to accomplish this as part of the repository you submit.
    Please assume the reviewer has not executed an application in your language/framework before when developing your documentation.
    What does it need to do?

    Background
    Our users have points in their accounts.
    Users only see a single balance in their accounts.
    But for reporting purposes we actually track their points per payer/partner.
    In our system, each transaction record contains:
       payer (string), points (integer), timestamp (date).

    For earning points it is easy to assign a payer, we know which actions earned the points.
    And thus which partner should be paying for the points.

    When a user spends points, they don't know or care which payer the points come from.
    But, our accounting team does care how the points are spent.
    There are two rules for determining what points to "spend" first:
    ● We want the oldest points to be spent first (oldest based on transaction timestamp, not the order they’re received)
    ● We want no payer's points to go negative.

    We expect your web service to
    Provide routes that:
    ● Add transactions for a specific payer and date.
    ● Spend points using the rules above and return a list of
       { "payer": <string>, "points": <integer> } for each call.
    ● Return all payer point balances.

    Note:
    ● We are not defining specific requests/responses. Defining these is part of the exercise.
    ● We don’t expect you to use any durable data store. Storing transactions in memory is acceptable for the exercise.

    Example
    Suppose you call your add transaction route with the following sequence of calls:
    ● { "payer": "DANNON", "points": 1000, "timestamp": "2020-11-02T14:00:00Z" }
    ● { "payer": "UNILEVER", "points": 200, "timestamp": "2020-10-31T11:00:00Z" }
    ● { "payer": "DANNON", "points": -200, "timestamp": "2020-10-31T15:00:00Z" }
    ● { "payer": "MILLER COORS", "points": 10000, "timestamp": "2020-11-01T14:00:00Z" }
    ● { "payer": "DANNON", "points": 300, "timestamp": "2020-10-31T10:00:00Z" }

    Then you call your spend points route with the following request:
    { "points": 5000 }
    The expected response from the spend call would be:
    [
    { "payer": "DANNON", "points": -100 },
    { "payer": "UNILEVER", "points": -200 },
    { "payer": "MILLER COORS", "points": -4,700 }
    ]
    A subsequent call to the points balance route, after the spend, should returns the following results:
    {
    "DANNON": 1000,
    "UNILEVER": 0,
    "MILLER COORS": 5300
    }
    How do I submit it?
    Provide a link to a public repository, such as GitHub or BitBucket, that contains your code to your recruiter.
    FAQ
    How will this exercise be evaluated?
    An engineer will review the code you submit.
    At a minimum they must be able to run the service and the service must provide the expected results.
    You should provide any necessary documentation within the repository.
    While your solution does not need to be fully production ready,
    you are being evaluated so put your best foot forward.

    I have questions about the problem statement
    For any requirements not specified via an example, use your best judgement to determine the expected result.
    How long do I have to complete the exercise?
    There is no time limit for the exercise. Out of respect for your time, we designed this exercise with the
    intent that it should take you a few hours.
    But, please take as much time as you need to complete the work.
  */

  static class T {
    public String payer;
    public int points;
    public Date timestamp;

    public T(String payer, int points, Date timestamp) {
      this.payer = payer;
      this.points = points;
      this.timestamp = timestamp;
    }
  }
  // assume  Long.MIN_VALUE <= total points of user <= Long.MAX_VALUE
  Map<String, Long> payerAccounts;
  Queue<T> q; // transactions;
  Long total;

  public Points() {
    total = 0l;
    payerAccounts = new HashMap<>();
    q = new PriorityQueue<>(Comparator.comparing(a -> a.timestamp));
  }

  /*
   assume transaction input string is in valid format
   E.g.  "payer: DANNON, points: 1000, timestamp: "2020-11-02T14:00:00Z"
  */
  public void addTransaction(String transaction) throws ParseException {
    String[] kvs = transaction.split(",\\s+");
    Map<String, String> map = new HashMap<>();
    for (String e : kvs) {
      String[] kv = e.split(":\\s+");
      map.put(kv[0], kv[1]);
    }
    String payer = map.get("payer");
    int points = Integer.valueOf(map.get("points"));
    if (points == 0l) return;

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
    sf.setTimeZone(TimeZone.getTimeZone("UTC"));
    Date timestamp = sf.parse(map.get("timestamp"));

    q.offer(new T(payer, points, timestamp));
    payerAccounts.put(payer, payerAccounts.getOrDefault(payer, 0l) + points);
    total += points;
  }

  /*
  spend rules:
   We want the oldest points to be spent first (oldest based on transaction timestamp, not the order they’re received)
   We want no payer's points to go negative.

  Assume
    - spend points is positive or > 0;
    - before spend points, all payer's point is not negative or >= 0.
  */
  public void spendPoints(int points) {
    if (points <= 0) return;
    if (total < points) return;

    Map<String, Long> out = new HashMap<>();

    List<T> noTouch = new ArrayList<>();
    while (!q.isEmpty() && points > 0) {
      T t = q.poll();
      Long remove = Math.min(points, Math.min(t.points, payerAccounts.get(t.payer)));
      if (remove != t.points) {
        t.points -= remove;
        noTouch.add(t);
        // 1. can not reduce at present if the payer balance = 0 but
        // later the record maybe be reduced when the payer balance is >0
        // 2. possible not the payer balance is still >0, in this case now points is 0.
      }

      payerAccounts.put(t.payer, payerAccounts.get(t.payer) - remove);
      total -= remove;
      out.put(t.payer, out.getOrDefault(t.payer, 0l) + remove);

      points -= remove;
      if (points == 0) break;
      if (q.isEmpty()) {
        for (T no : noTouch) {
          q.offer(no);
        }
        noTouch = new ArrayList<>();
      }
    }

    for (T no : noTouch) {
      q.offer(no);
    }

    NumberFormat f = NumberFormat.getNumberInstance();
    for (Map.Entry<String, Long> e : out.entrySet()) {
      System.out.println("payer: " + e.getKey() + ", points: -" + f.format(e.getValue()));
    }
  }
  // Return all payer point balances.
  public void allPayerBalances() {
    NumberFormat f = NumberFormat.getNumberInstance();
    for (Map.Entry<String, Long> e : payerAccounts.entrySet()) {
      System.out.println("payer: " + e.getKey() + ", points: " + f.format(e.getValue()));
    }
  }

  public static void main(String[] args) throws ParseException {
    Arrays.asList(
        "payer: DANNON, points: 300, timestamp: 2020-10-31T10:00:00Z",
        "payer: UNILEVER, points: 200, timestamp: 2020-10-31T11:00:00Z",
        "payer: DANNON, points: -200, timestamp: 2020-10-31T15:00:00Z",
        "payer: MILLER COORS, points: 10000, timestamp: 2020-11-01T14:00:00Z",
        "payer: DANNON, points: 1000, timestamp: 2020-11-02T14:00:00Z");
    Points t = new Points();
    for (String tranaction :
        Arrays.asList(
            "payer: DANNON, points: 300, timestamp: 2020-10-31T10:00:00Z",
            "payer: UNILEVER, points: 200, timestamp: 2020-10-31T11:00:00Z",
            "payer: DANNON, points: -200, timestamp: 2020-10-31T15:00:00Z",
            "payer: MILLER COORS, points: 10000, timestamp: 2020-11-01T14:00:00Z",
            "payer: DANNON, points: 1000, timestamp: 2020-11-02T14:00:00Z")) {
      t.addTransaction(tranaction);
    }

    t.spendPoints(5000);
    System.out.println("-------");
    t.allPayerBalances();

    /*
      record:  A,   200, 1
               A,  -100, 2
               B,   100, 3

      spend points 200 or 100 or 150,


     record:   A,   200, 1
               A,  -100, 2
               B,   150, 3

      spend points   150,
     */
  }

}
