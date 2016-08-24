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
 *     ?? how to make it simple
 *     1 space O(1)
 *     2 Math.max -> ?:
 *     3 watch the relation
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
 *     4 at start (index =-1) the max profit of sell is 0; the max profit of buy is Integer.MIN_VALUE
 *       this can feasible only for space O(1) version.
 *
 *     day:    0         1         2        3       4      ...
 *     MIN    buy1      buy1       buy1     buy1    buy1   ...
 *     0                sell1      sell1    sell1   sell1  ...
 *     MIN                         buy2     buy2    buy2   ...
 *     0                                    sell2   sell2  ...
 *
 *     5 cancel 'zero' operation.
 *     6 simplify return result.
 *
 *  fast version see Leetcode123BestTimetoBuyandSellStockIII3
 */
public class Leetcode123BestTimetoBuyandSellStockIII2 {

    public static int maxProfit2(int[] prices) {
        int b1, s1, b2, s2;
        b1 = Integer.MIN_VALUE;
        s1 = 0;
        b2 = Integer.MIN_VALUE;
        s2 = 0;
        for (int i = 0; i < prices.length; i++) {
            s2 = s2 > b2 + prices[i] ? s2 : b2 + prices[i];
            b2 = b2 > s1 - prices[i] ? b2 : s1 - prices[i];
            s1 = s1 > b1 + prices[i] ? s1 : b1 + prices[i];
            b1 = b1 > -prices[i] ? b1 : -prices[i];
        }
        return s2;
    }
}
