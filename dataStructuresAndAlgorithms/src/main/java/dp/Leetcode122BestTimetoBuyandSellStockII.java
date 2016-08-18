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
 * 122. Best Time to Buy and Sell Stock II
 * Difficulty: Medium
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.
 *
 * Design an algorithm to find the maximum profit.
 * You may complete as <strong>many transactions</strong> as you like
 * (ie, buy one and sell one share of the stock multiple times).
 * However, you may <strong>not</strong> engage in <strong>multiple transactions at the same time </strong>
 * (ie, you must sell the stock before you buy again).
 *
 * Subscribe to see which companies asked this question
 *
 * Tags
 *      Array
 *      Greedy
 * Similar Problems
 *      (E) Best Time to Buy and Sell Stock
 *      (H) Best Time to Buy and Sell Stock III
 *      (H) Best Time to Buy and Sell Stock IV
 *      (M) Best Time to Buy and Sell Stock with Cooldown
 *
 * ===============================================================
 * Draw a picture and easy to see the resolution.
 * see {@link Leetcode122BestTimetoBuyandSellStockII2}
 */
public class Leetcode122BestTimetoBuyandSellStockII {

    public int maxProfit(int[] prices) {
        int l  = prices.length;
        if (l < 2) return 0;
        // above is just for performance reason

        int prof = 0;
        for (int i = 1; i <  l ; i++) {
            if (prices[i] > prices[i - 1]) {
                prof += prices[i] - prices[i - 1];
            }
        }
        return prof;
    }
}
