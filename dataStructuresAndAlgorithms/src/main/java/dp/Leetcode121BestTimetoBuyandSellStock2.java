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

public class Leetcode121BestTimetoBuyandSellStock2 {
    // draw a picture to see easily
    public int maxProfit(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int r = 0, min = prices[0];// max profit and so far min price

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < prices[i - 1]) {
                min = min < prices[i] ? min : prices[i];
            } else {
                int prof = prices[i] - min;
                r = r > prof ? r : prof;
            }
        }
        return r;
    }

    public int maxProfit2(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int r = 0, min = prices[0];
        for (int i = 1; i < prices.length; ++i) {

            if (prices[i] < min) {
                min = prices[i];
            } else {
                int prof = prices[i] - min;
                r = r > prof ? r : prof;
            }
        }
        return r;
    }

    public int maxProfit3(int[] prices) {
        int r = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length; i++) {

            if (prices[i] < min) {
                min = prices[i];
            } else {
                int prof = prices[i] - min;
                r = r > prof ? r : prof;
            }
        }
        return r;
    }

    public int maxProfit4(int[] prices) {
        int r = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length; i++) {

            if (prices[i] < min) {
                min = prices[i];
            } else if (prices[i] - min > r) {
                r = prices[i] - min;
            }
        }
        return r;
    }
}
