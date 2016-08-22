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
 *  309. Best Time to Buy and Sell Stock with Cooldown
 *  Difficulty: Medium
 *  Say you have an array for which the ith element is the price of a given stock on day i.
 *
 *  Design an algorithm to find the maximum profit.
 *  You may complete as many transactions as you like
 *  (ie, buy one and sell one share of the stock multiple times) with the following restrictions:
 *
 *  You may not engage in multiple transactions at the same time
 *          (ie, you must sell the stock before you buy again).
 *  After you sell your stock, you cannot buy stock on next day.
 *          (ie, cooldown 1 day)
 *  Example:
 *
 *      prices = [1, 2, 3, 0, 2]
 *      maxProfit = 3
 *      transactions = [buy, sell, cooldown, buy, sell]
 *
 *
 * Tags
 *      Dynamic Programming
 * Similar Problems
 *      (E) Best Time to Buy and Sell Stock
 *      (M) Best Time to Buy and Sell Stock II
 *  ---------------------------------------------------------------------
 *
 *   ?? :
 *     1 what characters of this question connect it to DP?
 *     2 can some point judge decide the result of the whole view perspective?
 *       no.
 *       how to carry the affect of those happened before to now and how to carry it again to future?
 *     3 what is the true question of it, what does it want indeed.
 *     4 what is the deference between it with greedy(leetcode 122) and max (leetcode 121)
 *     5 if on i day, sell then buy, the result is same with not buy and no sell,
 *       but the next day will be cooldown?
 *
 *   analyze:
 *
 *     the objective is to get max profit with the last status of no share in hand:
 *          a serious of operations end up with sell or cooldown.
 *
 *          ... ..., pre 2 day,      pre 1 day,   last day
 *                                                sell
 *                                    sell        cooldown
 *                  sell              coodwon     nothing happen after cooldown
 *                  ... ...
 *          but sell depends buy or 'nothing happen after buy' happen before it.
 *          and 'cooldown' need sell happen just 1 day before it
 *          and 'nothing happen after cooldown' need cooldown happen before it.
 *          total relation as:
 *              buy -(next 1 or n days) -> sell
 *              sell -(next day)-> cooldown
 *              cooldown -(next 1 or n days) -> buy
 *
 *     all possible operation on day i and result status:
 *          buy                             ->  1 share in hand
 *          nothing happen after buy        ->  1 share in hand
 *          sell                            ->  0 share in hand
 *          cooldown                        ->  0 share in hand
 *          nothing happen after cooldown   ->  0 share in hand
 *
 *     so max profit of 0 share on be last day:
 *
 *          max(
 *              1 max profit of 'sell' happen on last day,
 *              2 max profit of 'cooldown' happen on last day,
 *              3 max profit of 'nothing happen after cooldown' happen on last day,
 *          }
 *
 * try make it simple  ---------------------------------------
 *
 *     all possible operation on day i and result status:
 *          endByBuy                        ->  1 share in hand
 *          sell                            ->  0 share in hand
 *          endByCooldown                   ->  0 share in hand
 *
 *     so max profit of 0 share on be last day:
 *
 *          max(
 *              1 max profit of 'sell' happen on last day,
 *              2 max profit of 'endByCooldown' happen on last day,
 *          }
 *
 *     DP translations of max profit: drawing a picture will be easy to understand.
 *              max_endBysell[last] = max_endByBuy[last - 1] + prices[last];
 *              max_endByCooldown[last] = Math.max(max_endByCooldown[last - 1], max_endBysell[last - 1]);
 *
 *         then: max_endByBuy[i] = Math.max(max_endByCooldown[i - 1] - prices[i], max_endByBuy[i - 1]);
 *
 *      Initial:
 *
 *         max_endBySell[0] = Integer.MIN_VALUE;
 *         max_endByCooldown[0] = 0;
 *         max_endByBuy[0] = -prices[0];
 *
 *   see also {@link Leetcode309BestTimetoBuyandSellStockwithCooldown2}
 *
 * @see <a href="https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/"> leetcode </a>
 */
public class Leetcode309BestTimetoBuyandSellStockwithCooldown {
    public int maxProfit(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int[] sell = new int[prices.length];
        int[] cool = new int[prices.length];
        int[] buy = new int[prices.length];

        sell[0] = Integer.MIN_VALUE;
        cool[0] = 0;
        buy[0] = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            sell[i] = buy[i - 1] + prices[i];
            cool[i] = Math.max(cool[i - 1], sell[i - 1]);
            buy[i] = Math.max(cool[i - 1] - prices[i], buy[i - 1]);
        }
        return Math.max(sell[prices.length - 1], cool[prices.length - 1]);
    }

    // O(1) space
    public int maxProfit2(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int sell = Integer.MIN_VALUE;
        int cool = 0;
        int buy = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            int newSell = buy + prices[i];
            int newBuy = Math.max(cool - prices[i], buy);
            int newCool = Math.max(cool, sell);
            sell = newSell;
            buy = newBuy;
            cool = newCool;
        }
        return Math.max(sell, cool);
    }
}
