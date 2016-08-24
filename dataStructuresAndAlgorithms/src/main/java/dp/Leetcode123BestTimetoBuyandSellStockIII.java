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
 *     123. Best Time to Buy and Sell Stock III
 * Difficulty: Hard
 * Say you have an array for which the ith element is the price of a given stock on day i.
 *
 * Design an algorithm to find the <strong>maximum</strong> profit.
 * You may complete <strong>at most two transactions</strong>
 *
 * Note:
 * You may not engage in multiple transactions at the same time
 * (ie, you must sell the stock before you buy again).
 *
 * Subscribe to see which companies asked this question
 *
 * Tags
 *      Array
 *      Dynamic Programming
 *  Problems
 *          (E) Best Time to Buy and Sell Stock
 *          (M) Best Time to Buy and Sell Stock II
 *          (H) Best Time to Buy and Sell Stock IV
 *
 *  ===============================================================================
 *
 *   ??
 *    1 how to translation to sub question
 *
 *   analyze:
 *     one transaction is buy + sell
 *
 *     max profit of day i that with the last operation of:
 *           zero, never buy or sell
 *           buy1
 *           sell1
 *           buy2
 *           sell2
 *
 *     relation:
 *
 *     day   0        1        2      3     4    ...
 *           0        0        0      0     0
 *                \       \      \      \
 *           buy1 - buy1  - buy1 - buy1 - buy1   ...
 *                 \      \      \       \
 *                  sell1 - sell1- sell1 - sell1 ...
 *                         \     \       \
 *                          buy2 - buy2  - buy2  ...
 *                                \       \
 *                                 sell2  -sell2 ...
 *
 *     initial value:
 *     day:   0      1        2      3     4    ...
 *          buy1 - buy1 - buy1 - buy1 - buy1   ...
 *               \       \       \       \
 *          0     sell1 -  sell1- sell1 - sell1 ...
 *                       \       \       \
 *          MIN           buy2  - buy2 - buy2  ...
 *                               \       \
 *          0                    sell2  - sell2 ...
 *
 *    "at most two transactions" means at last day max profit of the max one of
 *       1  never buy or sell
 *       2  the last operation is sell1 on some day <= last
 *       3  the last operation is sell2 on some day <= last
 *
 *   Initial value:
 *      Integer.MIN_VALUE - some positive value will = a very big number;
 *      So it only can be initial value of buy and make sure it cannot be selected as the result of
 *      next sell value depends on this buy.
 *
 *   make it simple see {@link Leetcode123BestTimetoBuyandSellStockIII2}
 */
public class Leetcode123BestTimetoBuyandSellStockIII {
    // DP basic idea
    public static int maxProfit(int[] prices) {
        int[] sell2 = new int[prices.length],
                sell1 = new int[prices.length],
                buy2 = new int[prices.length],
                buy1 = new int[prices.length];

        if (prices.length == 0) {
            return 0;
        }
        buy1[0] = -prices[0];
        sell1[0] = 0;
        buy2[0] = Integer.MIN_VALUE;
        sell2[0] = 0;

        for (int i = 1; i < prices.length; i++) {
            buy1[i] = Math.max(buy1[i - 1], 0 - prices[i]);
            sell1[i] = Math.max(sell1[i - 1], buy1[i - 1] + prices[i]);
            buy2[i] = Math.max(buy2[i - 1], sell1[i - 1] - prices[i]);
            sell2[i] = Math.max(sell2[i - 1], buy2[i - 1] + prices[i]);
        }
        int happen = Math.max(sell2[prices.length - 1], sell1[prices.length - 1]);
        return happen > 0 ? happen : 0;
    }
}
