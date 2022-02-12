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
       as many transactions as you like, but you need to pay
       the transaction fee for each transaction.
       you must sell the stock before you buy again.
  */
  /*
   pay the fee when sell the stock
   no transaction number limitation
  */

  /*---------------------------------------------------------------------------
  From the Leetcode188BestTimetoBuyandSellStockIV
  at most N-1 possible transaction, [0,N-2]
  Easy to get this variation
  But is too slow  O(N^2) time and O(N) space
  */
  public int maxProfit_(int[] prices, int fee) {
    if (prices == null || prices.length <= 1) return 0;
    int N = prices.length;
    int[] min_cost = new int[N - 1];
    Arrays.fill(min_cost, Integer.MAX_VALUE);
    int[] max$ = new int[N - 1]; // m[i] the max profit, money, for at most i times T
    for (int p : prices) {
      for (int k = 0; k <= N - 2; k++) {
        min_cost[k] = Math.min(min_cost[k], p - (k == 0 ? 0 : max$[k - 1]));
        max$[k] = Math.max(max$[k], p - min_cost[k] - fee);
      }
    }
    return max$[N - 1];
  }
  /*---------------------------------------------------------------------------
  Improvement:
  As there is no limit on transaction number 'as many transactions as you like'
  State machine:
     any day will be in only one of 2 status
     h0, h1: max profit for hold and not-hold status by the end of current day
     calculate them with previous day value, no transaction number limitation and concern
     in the status machine:

     1---(keep)----> 1,  0---(buy) ---> 1
     0---(keep)----> 0,  1---(sell)---> 0

  O(N) time and O(1) space
   */
  public int maxProfit(int[] prices, int fee) {
    int h0 = 0,
        h1 = -prices[0]; // max profit for hold and not-hold status by the end of current day
    for (int i = 1; i < prices.length; i++) {
      int tmp_h1 = Math.max(h1, h0 - prices[i]);
      int tmp_h0 = Math.max(h0, h1 + prices[i] - fee);
      h1 = tmp_h1;
      h0 = tmp_h0;
    }
    return h0;
  }
  /*---------------------------------------------------------------------------
  https://imgur.com/a/lca1ChI
  We can transform h0 first without using temporary variables.
  because selling and buying on the same day can't be better
  than just continuing to hold the stock, as there is fee and all are max relation
  h1= max(h1, h0-p)
              today_h0   = max{h0,   h1+p-fee}
              today_h0-p = max{h0-p, h1-fee}
  if replace h0 with today_h0:
  h1= max{h1, h0-p, h1-fee}
  h1= max{h1, h0-p}
  So it is right to calculate h1 with today h0
  this way need not temporary variable
   */
  public int maxProfit2(int[] prices, int fee) {
    int h0 = 0, h1 = -prices[0];
    for (int i = 1; i < prices.length; i++) {
      h0 = Math.max(h0, h1 + prices[i] - fee);
      h1 = Math.max(h1, h0 - prices[i]);
    }
    return h0;
  }

  public int maxProfit2_(int[] prices, int fee) {
    int h0 = 0, h1 = -1000000000;
    for (int i = 0; i < prices.length; i++) {
      h0 = Math.max(h0, h1 + prices[i] - fee);
      h1 = Math.max(h1, h0 - prices[i]);
    }
    return h0;
  }
  /*---------------------------------------------------------------------------
  We can transform h1 first without using temporary variables.
      h1 = Math.max(h1, h0 - p);
      h0 = Math.max(h0, h1 + p - fee);
         replace h1 with today_h1 which is represented by h1
         = Math.max(h0, h1 + p - fee, h0 - p + p - fee );
         = Math.max(h0, h1 + p - fee, h0 - fee );
         = Math.max(h0, h1 + p - fee);
   */
  public int maxProfit3(int[] prices, int fee) {
    if (prices == null || prices.length <= 1) return 0;
    int h0 = 0, h1 = Integer.MIN_VALUE;
    for (int p : prices) {
      h1 = Math.max(h1, h0 - p);
      h0 = Math.max(h0, h1 + p - fee);
    }
    return h0;
  }

  public int maxProfit3_(int[] prices, int fee) {
    int h0 = 0, h1 = -1000000000;
    for (int p : prices) {
      h1 = Math.max(h1, h0 - p);
      h0 = Math.max(h0, h1 + p - fee);
    }
    return h0;
  }

  public int maxProfit3__(int[] prices, int fee) {
    int h0 = 0, h1 = -prices[0];
    for (int i = 1; i < prices.length; i++) {
      h1 = Math.max(h1, h0 - prices[i]);
      h0 = Math.max(h0, h1 + prices[i] - fee);
    }
    return h0;
  }
}
