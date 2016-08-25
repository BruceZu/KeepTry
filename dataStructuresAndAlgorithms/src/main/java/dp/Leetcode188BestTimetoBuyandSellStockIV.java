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
 *
 *  @see <a href="http://blog.csdn.net/fightforyourdream/article/details/14503469">reference </a>
 *
 *    on day i, it can happen more transactions on the same day. while
 *    the effective result is buy, sell or nothing.
 *    price i-1 price i
 *    nothing   nothing
 *    buy1      buy1
 *    sell1     sell1
 *    buy2      buy2
 *    sell2     sell2
 *    ...       ...
 *
 *
 */
public class Leetcode188BestTimetoBuyandSellStockIV {
    public int maxProfit(int k, int[] prices) {
        // write your code here
        if (k == 0) {
            return 0;
        }
        if (k >= prices.length / 2) {
            int profit = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] > prices[i - 1]) {
                    profit += prices[i] - prices[i - 1];
                }
            }
            return profit;
        }
        int n = prices.length;
        int[][] mustsell = new int[n + 1][n + 1];   // mustSell[i][j] 表示前i天，至多进行j次交易，第i天必须sell的最大获益
        int[][] globalbest = new int[n + 1][n + 1];  // globalbest[i][j] 表示前i天，至多进行j次交易，第i天可以不sell的最大获益

        mustsell[0][0] = globalbest[0][0] = 0;
        for (int i = 1; i <= k; i++) {
            mustsell[0][i] = globalbest[0][i] = 0;
        }

        for (int day = 1; day < n; day++) {
            int gainorlose = prices[day] - prices[day - 1];
            mustsell[day][0] = 0;
            for (int t = 1; t <= k; t++) {
                mustsell[day][t] = Math.max(globalbest[(day - 1)][t - 1] + gainorlose,
                        mustsell[(day - 1)][t] + gainorlose);
                globalbest[day][t] = Math.max(globalbest[(day - 1)][t], mustsell[day][t]);
            }
        }
        return globalbest[(n - 1)][k];
    }
};
