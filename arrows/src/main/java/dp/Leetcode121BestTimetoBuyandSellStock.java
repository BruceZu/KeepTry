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

/*
121. Best Time to Buy and Sell Stock

You are given an array prices where prices[i] is the price of a given stock on the ith day.
You want to maximize your profit by choosing a single day to buy one stock and choosing
a different day in the future to sell that stock.

Return the maximum profit you can achieve from this transaction.
If you cannot achieve any profit, return 0.



Input: prices = [7,1,5,3,6,4]
Output: 5
Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.


Input: prices = [7,6,4,3,1]
Output: 0
Explanation: In this case, no transactions are done and the max profit = 0.


Constraints:
1 <= prices.length <= 10^5
0 <= prices[i] <= 10^4
*/
/*
Watch: must buy before sell

keep min and max price does not work
need make sure the max is after min price

keep tacking the min price from start to current price
calculate the diff = current price - min price in the scope [start, current price]
select the max one;
 */
public class Leetcode121BestTimetoBuyandSellStock {
  public int maxProfit(int ps[]) {
    int min = ps[0];
    int r = 0;
    for (int p : ps) {
      min = Math.min(p, min);
      r = Math.max(r, p - min);
    }
    return r;
  }
}
