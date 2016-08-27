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

import java.util.Arrays;

/**
 * //todo later
 *
 * @see <a href="https://discuss.leetcode.com/topic/37874/3ms-java-dp-solution-beats-96-3-o-kn-time-o-n-space">other idea </a>
 * @see <a href="https://discuss.leetcode.com/topic/37991/2-ms-java-dp-solution-beats-99-87">other idea </a>
 */
public class Leetcode188BestTimetoBuyandSellStockIV_todo2 {
    // same as Leetcode122BestTimetoBuyandSellStockII
    private static int greedy(int[] prices) {
        int r = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                r += prices[i] - prices[i - 1];
            }
        }
        return r;
    }
    // use 2 array to keep the status at the same time;
    public static int maxProfit(int[] prices, int k) {
        if (k == 0 || prices.length == 0) {
            return 0;
        }
        if (k > prices.length / 2) {
            return greedy(prices);
        }
        int[] buy = new int[k];
        int[] sell = new int[k];

        Arrays.fill(buy, Integer.MIN_VALUE);

        for (int price : prices) {
            int preSell = 0;
            for (int t = 0; t < k; t++) {
                buy[t] = Math.max(buy[t], preSell - price);
                sell[t] = Math.max(sell[t], buy[t] + price);
                preSell = sell[t];
            }
        }

        return sell[k - 1];
    }

    public static void main(String[] args) {
        System.out.println(
                maxProfit(new int[]{2, 5, 7, 1, 4, 3, 1, 3}, 3));
    }
}
