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

package queue;

import java.util.PriorityQueue;

/**
 * money you have and price of items are given
 * try to find out 2 items their cost is mostly near the money
 * and return their index
 */
public class BestBuy {

    private static class PossibleResult implements Comparable {
        int[] index;
        int totalMoney;

        public PossibleResult(int index1, int index2, int sum) {
            this.index = new int[]{index1, index2};
            this.totalMoney = sum;
        }

        @Override
        public int compareTo(Object o) {
            return ((PossibleResult) o).totalMoney - this.totalMoney;
        }
    }

    private static void findOtherOne(int[] prices,
                                     int i, int from, int lessThan,
                                     PriorityQueue<PossibleResult> results) {
        if (from >= prices.length) {
            return;
        }
        for (int o = from; o < prices.length; o++) {
            if (prices[o] <= lessThan) {
                results.offer(new PossibleResult(i, o, prices[i] + prices[o]));
            }
        }
    }

    /**
     * @param prices list of price of items
     * @param money  money we have.
     * @return the index of items
     */
    public static int[] best2ItemsToBuyWith(int[] prices, int money) {
        if (prices == null || prices.length <= 1) {
            return null;
        }
        PriorityQueue<PossibleResult> results = new PriorityQueue<>();
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] > money) {
                continue;
            }
            findOtherOne(prices, i, i + 1, money - prices[i], results);
        }

        PossibleResult r = results.peek();
        if (r != null) {
            return r.index;
        }
        return null;
    }
}
