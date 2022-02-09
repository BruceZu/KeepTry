//  Copyright 2016 The Sawdust Open Source Project
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leetcode188BestTimetoBuyandSellStockIV {
  /*
    188. Best Time to Buy and Sell Stock IV

     You are given an integer array prices where prices[i] is the price of a given stock on the ith day, and an integer k.
     Find the maximum profit you can achieve. You may complete at most k transactions.

     Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).

     Input: k = 2, prices = [2,4,1]
     Output: 2
     Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.


     Input: k = 2, prices = [3,2,6,5,0,3]
     Output: 7
     Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6),
     profit = 6-2 = 4. Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.


     Constraints:

     0 <= k <= 100
     0 <= prices.length <= 1000
     0 <= prices[i] <= 1000

  */
  /*
  at most k transactions.
  you must sell the stock before you buy again.
  */

  /* --------------------------------------------------------------------------
  https://i.imgur.com/Id0cLTh.png
  Time and space: O(NK),
  space can be:  O(K), each column only need previous column
  */
  public int maxProfit__(int k, int[] prices) {
    int n = prices.length;
    if (n <= 0 || k <= 0) return 0;
    if (2 * k > n) { // convert to as many times as you can
      int res = 0;
      for (int i = 1; i < n; i++) {
        res += Math.max(0, prices[i] - prices[i - 1]);
      }
      return res;
    }

    // dp[i][k][1/0]: max profix of
    //  - ith day end,
    //  - in kth transactions: bought:1, no-op, sold:0, no-op.   k: 0~ K. 0: no transaction happen
    // yet.
    //  - with stack in bought:1, sold:0 status
    int[][][] dp = new int[n][k + 1][2];

    // initialize:
    // 1> the array with -inf,  use -1e9 to prevent overflow
    for (int i = 0; i < n; i++) {
      for (int j = 0; j <= k; j++) {
        dp[i][j][0] = -1000000000;
        dp[i][j][1] = -1000000000;
      }
    }

    // 2> set starting value
    dp[0][0][0] = 0;
    dp[0][1][1] = -prices[0];

    // column by column
    for (int i = 1; i < n; i++) {
      for (int j = 0; j <= k; j++) {
        dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);
        // No bought 1 status for transaction 0.
        if (j > 0) {
          dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
        }
      }
    }

    int ans = 0;
    for (int j = 0; j <= k; j++) { // last column with sold 0 status: all transaction. 0-K
      ans = Math.max(ans, dp[n - 1][j][0]);
    }

    return ans;
  }
  /* --------------------------------------------------------------------------
   Time O(n(n−k)) if 2k < n;
        O(n)      if 2k > n
        n is the length of the price sequence.
   The maximum size of transactions is O(n), and we need O(n−k) iterations.
   Space O(n). need a list to store transactions.
  */
  public int maxProfit_(int k, int[] prices) {
    int N = prices.length;
    // solve special cases
    if (N <= 0 || k <= 0) return 0;

    // find all consecutively increasing subsequence as one transaction
    List<int[]> ts = new ArrayList<>();
    int start = 0; // only update when going down
    int end = 0;
    for (int i = 1; i < N; i++) {
      if (prices[i] >= prices[i - 1]) {
        end = i;
      } else {
        if (end > start) ts.add(new int[] {start, end});
        start = i;
      }
    }
    // pick up the last transaction if it is there
    if (end > start) {
      int[] t = {start, end};
      ts.add(t);
    }
    // any 2 transactions in ts is not consecutive

    while (ts.size() > k) { // O(n(n−k)) time
      // get the smallest loss profit caused by delete
      int di = 0;
      int min_diff_d = Integer.MAX_VALUE;
      for (int i = 0; i < ts.size(); i++) {
        int[] me = ts.get(i);
        int diff = prices[me[1]] - prices[me[0]];
        if (diff < min_diff_d) {
          min_diff_d = diff;
          di = i;
        }
      }

      // get the smallest loss profit caused by merge
      /*
       price [a b] [ c d]
       loss profit=  d - c + b - a - (d -a)
                  =  b - c
       https://imgur.com/PyL9kzG.png
      */
      int mi = 0;
      int min_diff_m = Integer.MAX_VALUE;
      for (int i = 1; i < ts.size(); i++) {
        int[] pre = ts.get(i - 1);
        int[] me = ts.get(i);
        int diff = prices[pre[1]] - prices[me[0]];
        if (diff < min_diff_m) {
          min_diff_m = diff;
          mi = i;
        }
      }

      // delete or merge
      if (min_diff_d <= min_diff_m) {
        ts.remove(di);
      } else {
        int[] pre = ts.get(mi - 1);
        int[] me = ts.get(mi);
        pre[1] = me[1]; // [ 0,  1][ me_0, me_1]
        ts.remove(mi);
      }
    }

    int ans = 0; // convert transaction to profit
    for (int[] t : ts) ans += prices[t[1]] - prices[t[0]];
    return ans;
  }

  /* --------------------------------------------------------------------------
   Idea same as `123. Best Time to Buy and Sell Stock III`
   extend Kadane's algorithm
   O(N*K) time and O(K) space
  */
  public static int maxProfit(int K, int[] prices) {
    if (K == 0 || prices == null || prices.length <= 1) return 0;
    int[] c = new int[K];
    Arrays.fill(c, Integer.MAX_VALUE);

    int[] m = new int[K];
    for (int p : prices) {
      for (int k = 0; k < K; k++) {
        c[k] = Math.min(c[k], p - (k == 0 ? 0 : m[k - 1]));
        m[k] = Math.max(m[k], p - c[k]);
      }
    }
    return m[K - 1];
  }
  /*
  The logic is same as above
  the above solution is column by column
  this solution is row and row
  the space is O(N)
  */

  public static int maxProfit2(int K, int[] prices) {
    if (K == 0 || prices == null || prices.length <= 1) return 0;
    int N = prices.length;
    int maxProfit[] =
        new int[N]; // pro[i]: by the end of i day, the max profit got from at most j transaction

    for (int j = 0; j < K; j++) {
      int minCost = Integer.MAX_VALUE; // candidate-cost
      for (int i = 0; i < N; i++) {
        int price = prices[i];
        minCost = Math.min(minCost, price - (j == 0 ? 0 : maxProfit[i]));
        maxProfit[i] = Math.max(i == 0 ? 0 : maxProfit[i - 1], price - minCost);
      }
    }
    return maxProfit[N - 1];
  }
}
