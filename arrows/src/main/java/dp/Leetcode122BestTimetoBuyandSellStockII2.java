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

public class Leetcode122BestTimetoBuyandSellStockII2 {
    /**
     * <pre>
     * IF sure need find out all the bottom and peak and sum all uphill value by peak_i - bottom_i
     *
     * up: last update is increase?
     * bottom: Bottom price. Default is prices[0].
     *         It maybe just the bottom if it starts with uphill,
     *         else it starts with downhill and will be replaced by first bottom or end of prices.
     *            - bottom peak
     *            - bottom(peak)  bottom  peak
     *         With the default value, always we have a bottom price.
     *         Once a peak appears, calculate profit once.
     *         Once a peak appears, before next peak appear, sure will appear a bottom, or ...
     *             - peak bottom peak
     *             - peak end
     *             - peak bottom end
     *
     * Comparing to today's price, next day price will be -> , down, up
     *
     * test case:
     *      [],
     *      [1],
     *      [3,3]
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        Boolean up = null;
        int pro = 0;
        int bottom = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] == prices[i - 1]) {
                continue;
            }
            if (prices[i] > prices[i - 1]) {
                if (up != null && !up) {
                    bottom = prices[i - 1];
                }
                up = true;
            } else {
                if (up != null && up) {
                    pro += prices[i - 1] - bottom;
                }
                up = false;
            }
        }
        if (up != null && up) {
            pro += prices[prices.length - 1] - bottom;
        }
        return pro;
    }
}
