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

package dp;

import java.util.Arrays;

public class Leetcode714BestTimetoBuyandSellStockwithTransactionFee {
  /*
  Question:
     as many transactions as you like, but you need to pay the transaction fee for each transaction.
     you must sell the stock before you buy again.

  From the Leetcode188BestTimetoBuyandSellStockIV
  at most N-1 Transaction, [0,N-2]
  Easy to get this variation
  But is too slow  O(N^2) time and O(N) space
  */
  public int maxProfit2(int[] prices, int fee) {
    if (prices == null || prices.length <= 1) return 0;
    int N = prices.length;
    int[] c = new int[N - 1];
    Arrays.fill(c, Integer.MAX_VALUE);
    int[] m = new int[N - 1]; // m[i] the max profit, money, for at most i times T
    for (int p : prices) {
      for (int k = 0; k <= N - 2; k++) {
        c[k] = Math.min(c[k], p - (k == 0 ? 0 : m[k - 1]));
        m[k] = Math.max(m[k], p - c[k] - fee);
      }
    }
    return m[N - 1];
  }
  /*
  As the there is no limit on the times of Transaction 'as many transactions as you like'
  Then the inner loop should not be there anymore

  State machine now for any day will be in only one of 2 status
     hold---(rest)----> hold || sold---(buy) --->hold
     sold---(rest)----> sold || hold---(sell)---> sold

  2 status
  3 action: buy sell rest
  O(N) time and O(1) space
   */
  public int maxProfit(int[] prices, int fee) {
    if (prices == null || prices.length <= 1) return 0;

    // h,s: max profit in hold, sold status
    int h = Integer.MIN_VALUE;
    int s = 0; // only sold can buy
    for (int p : prices) {
      h = Math.max(h, s - p); // cost cut
      s = Math.max(s, h + p - fee);
    }
    return s; // only care the max profit in the last sold status
  }
}
