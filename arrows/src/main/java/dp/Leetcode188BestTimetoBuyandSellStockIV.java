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

/**
 * <pre>
 *     188. Best Time to Buy and Sell Stock IV
 * Difficulty: Hard
 * Say you have an array for which the ith element is the price of a given stock on day i.
 *
 * Design an algorithm to find the maximum profit. You may complete
 * <strong> at most k</strong> transactions.
 *
 * Note:
 * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
 *
 *
 * Tags Dynamic Programming
 * Similar Problems
 * (E) Best Time to Buy and Sell Stock
 * (M) Best Time to Buy and Sell Stock II
 * (H) Best Time to Buy and Sell Stock III
 * =====================================================================================
 * <pre>
 *     max profit:
 *
 *     day            0  1  2  3  4  5  6  7
 *
 *     price          2  5  7  1  4  3  1  3
 *     transaction
 *       0            0  0  0  0  0  0  0  0     // no transaction no profit
 *       1            0  3  5  5  5  5  5  5
 *       2            0  3  5  5  8
 *       3            0
 *       ...
 *
 *                profit of any times transaction on the first day is 0, as only have one day.
 *
 *
 * Easier to understand.
 * Time complexity is O(k * number of days ^ 2)
 *
 * T[t][d] = max(T[t][d-1],
 *               max(prices[d] - prices[pre] + T[t-1][pre]) // where pre is 0...d-1
 *               )
 *
 *  =====>
 * O(K * number of days)
 *  Formula is
 *   maxDiff = Math.max(maxDiff, T[t - 1][d-1] - prices[d-1]);
 *   T[t][d] = Math.max(T[t][d - 1], prices[d] + maxDiff);
 *
 *  or
 *   T[t][d] = Math.max(T[t][d - 1], prices[d] + maxDiff);
 *   maxDiff = Math.max(maxDiff, T[t - 1][d] - prices[d]);  // used for next turn
 *
 *  =====>
 *   one dimension array
 *
 * @see <a href="https://www.youtube.com/watch?v=oDhu5uGq_ic">youtube, idea of Tushar Roy</a>
 */
public class Leetcode188BestTimetoBuyandSellStockIV {
    // same as Leetcode122BestTimetoBuyandSellStockII
    private static int greedy(int[] prices) {
        int r = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                r += prices[i] - prices[i - 1];
            }
        }
        return r;
    }

    //Idea  Easier to understand
    public static int maxProfitSlowSolution(int prices[], int k) {
        if (k == 0 || prices.length == 0) {
            return 0;
        }
        if (k > prices.length / 2) {
            return greedy(prices);
        }
        int T[][] = new int[k + 1][prices.length];

        for (int t = 1; t <= k; t++) {
            for (int d = 1; d < prices.length; d++) {
                int maxVal = 0;
                for (int pre = 0; pre < d; pre++) {
                    maxVal = Math.max(maxVal, prices[d] - prices[pre] + T[t - 1][pre]);
                }
                T[t][d] = Math.max(T[t][d - 1], maxVal);
            }
        }

        return T[k][prices.length - 1];
    }

    // improve to O(K * number of days)
    public static int maxProfit(int k, int[] prices) {
        if (k == 0 || prices.length == 0) {
            return 0;
        }
        if (k > prices.length / 2) {
            return greedy(prices);
        }
        int T[][] = new int[k + 1][prices.length];

        for (int t = 1; t < T.length; t++) {
            int maxDiff = 0 - prices[0];
            for (int d = 1; d < prices.length; d++) {
                T[t][d] = Math.max(T[t][d - 1], prices[d] + maxDiff);
                maxDiff = Math.max(maxDiff, T[t - 1][d] - prices[d]);
            }
        }
        return T[k][prices.length - 1];
    }

    // improved, using one dimension array
    public static int maxProfitOneDimensionArray(int[] prices, int k) {
        if (k == 0 || prices.length == 0) {
            return 0;
        }
        if (k > prices.length / 2) {
            return greedy(prices);
        }
        int T[] = new int[prices.length];

        for (int t = 1; t < T.length; t++) {
            int maxDiff = 0 - prices[0];
            for (int d = 1; d < prices.length; d++) {
                int newTd = Math.max(T[d - 1], prices[d] + maxDiff);
                maxDiff = Math.max(maxDiff, T[d] - prices[d]);
                T[d] = newTd;
            }
        }
        return T[prices.length - 1];
    }
};
