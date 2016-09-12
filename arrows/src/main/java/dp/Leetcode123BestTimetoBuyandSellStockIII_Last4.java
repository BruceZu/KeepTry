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
 * @see <a href="http://www.jiuzhang.com/solutions/best-time-to-buy-and-sell-stock-iii/"> jiuzhang</a>
 */
public class Leetcode123BestTimetoBuyandSellStockIII_Last4 {
    // see jiuzhang.  todo merge all loop in one
    public int maxProfit(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int n = prices.length;
        int[] leftProfit = new int[n];

        int maxLeftProfit = 0, minPrice = prices[0];
        for (int i = 1; i < n; i++) { // from 1
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else {
                maxLeftProfit = Math.max(maxLeftProfit, prices[i] - minPrice);
            }
            leftProfit[i] = maxLeftProfit;
        }

        int r = leftProfit[n - 1]; //
        int maxRightProfit = 0, maxPrice = prices[n - 1];
        for (int i = n - 2; i >= 0; i--) { // from n-2
            if (prices[i] > maxPrice) {
                maxPrice = prices[i];
            } else {
                maxRightProfit = Math.max(maxRightProfit, maxPrice - prices[i]);
            }
            r = Math.max(r, maxRightProfit + leftProfit[i]);
        }
        return r;
    }
}
