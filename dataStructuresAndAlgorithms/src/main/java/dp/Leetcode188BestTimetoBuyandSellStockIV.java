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
 *
 *    every day may happen more times buy sell buy sell ....
 *    the effective result is have 1 share, no share by the end of that day.
 *    because buy,sell, buy = buy;  sell, buy, sell = sell.
 *
 *    1 transactions = buy and sell
 *    1 transaction cover 2 days in effective view.
 *
 *   == so the objective is:
 *
 *              no share in hand by the end of ith day
 *               | in some day of i days
 *               |  |  kth transaction end up with sell.
 *               |  |  |
 *    max_profit_0_[i][k]
 *    so what is   max_profit_1_[i][k]
 *                            |  |  |
 *                            |  |  kth transaction follow up with a buy
 *                            |  in some day of i days
 *                            1 share in hand by the end of ith day.
 *
 *     T = Transaction
 *                1 T  ]
 *     buy,  sell;  buy, sell; buy, sell;
 *                          2 T   ]
 *
 *
 *    buy,  sell; is the first T, why  buy,  sell;  buy, is also the first T?
 *
 *
 *          max_profit_0_[i-1][k]            ---  > max_profit_0_[i][k]
 *                                             /
 *          max_profit_1_[i-1][k-1] + price[i]
 *
 *   == then how to translate max_profix_1[][] to max_profix_0[][]?
 *
 *          max_profit_1_[i-1][k]            ---  > max_profit_1_[i][k]
 *                                             /
 *          max_profit_0_[i-1][k] - price[i]
 *                     |       |
 *                     note    note
 *                            |_____________kth T cover 2 days________|
 *   == Initial value:
 *
 */
public class Leetcode188BestTimetoBuyandSellStockIV {
    private int maxP(int[] prices) {
        int res = 0;
        for (int i = 0; i < prices.length; i++) {
            if (i > 0 && prices[i] > prices[i - 1]) {
                res += prices[i] - prices[i - 1];
            }
        }
        return res;
    }

    public int maxProfit(int k, int[] prices) {
        if (k > prices.length / 2) {
            return maxP(prices);
        }
        int[][] max_1_ = new int[prices.length][k + 1];
        int[][] max_0_ = new int[prices.length][k + 1];
        max_1_[0][0] = -prices[0];

        for (int d = 1; d < prices.length; d++) {
            max_1_[d][0] = Math.max(max_1_[d - 1][0], -prices[d]);
        }
        for (int t = 1; t <= k; t++) {
            max_1_[0][t] = -prices[0];
        }
        for (int day = 1; day < prices.length; day++) {
            for (int t = 1; t <= k; t++) {
                max_1_[day][t] = Math.max(max_1_[day - 1][t], max_0_[day - 1][t] - prices[day]);
                max_0_[day][t] = Math.max(max_0_[day - 1][t], max_1_[day - 1][t - 1] + prices[day]);
            }
        }
        return Math.max(max_1_[prices.length - 1][k], max_0_[prices.length - 1][k]);
    }

    //// TODO: 8/25/16 how to translate to space O(1)
};
