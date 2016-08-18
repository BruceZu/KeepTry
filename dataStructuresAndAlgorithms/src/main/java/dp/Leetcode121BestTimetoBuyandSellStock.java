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
 *     121. Best Time to Buy and Sell Stock  Q
 * Difficulty: Easy
 * Say you have an array for which the ith element is the price of a given stock on day i.
 *
 * If you were only permitted to complete at most one transaction
 * (ie, buy one and sell one share of the stock),
 *
 * design an algorithm to find the maximum profit.
 *
 * Example 1:
 * Input: [7, 1, 5, 3, 6, 4]
 * Output: 5
 *
 * max. difference = 6-1 = 5 (not 7-1 = 6, as selling price needs to be larger than buying price)
 * Example 2:
 * Input: [7, 6, 4, 3, 1]
 * Output: 0
 *
 * In this case, no transaction is done, i.e. max profit = 0.
 * Subscribe to see which companies asked this question
 *
 * Tags
 *      Array
 *      Dynamic Programming
 * Similar Problems
 *      (M) Maximum Subarray
 *      (M) Best Time to Buy and Sell Stock II
 *      (H) Best Time to Buy and Sell Stock III
 *      (H) Best Time to Buy and Sell Stock IV
 *      (M) Best Time to Buy and Sell Stock with Cooldown
 *
 *  see Kadane's Algorithm: {@link KadaneAlgorithmMaxSubArray Kadane's Algorithm}
 *
 *  @see <a href="https://leetcode.com/problems/best-time-to-buy-and-sell-stock/">leetcode</a>
 */
public class Leetcode121BestTimetoBuyandSellStock {
    public int maxProfit(int[] prices) {
        int maxCur = 0, maxSoFar = 0;
        int iv; //  incame of 1 stock: yesterday buy, and today sell.
        for (int i = 1; i < prices.length; i++) {
            iv = prices[i] - prices[i - 1];
            maxCur = (0 > maxCur ? 0 : maxCur) + iv;
            maxSoFar = maxCur > maxSoFar ? maxCur : maxSoFar;
        }
        return maxSoFar;
    }
}
