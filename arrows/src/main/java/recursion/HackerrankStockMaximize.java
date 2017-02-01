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

package recursion;import java.util.Scanner;

/**
 * <pre>
 *
 *          /\
 *         /  \
 *        /    \      /-----
 *       /      \    /      \    /\
 *      /        \  /        \  /  \
 *     /          \/          \/    \
 *    |_____|   |_____|      |___|       : in come scope
 *
 *  there is day with higher price than today's price then buy
 *  sell on the day with highest price compared to today's price.
 *
 *  Runtime depends on where the max prices index appear, o(n) ~ o(n^2)
 *
 *  @see <a href="https://www.hackerrank.com/challenges/stockmax">hackerrank</a>
 */
public class HackerrankStockMaximize {
    static private long incomeOf(int[] prices, int from, int to) {
        // find the day with max price compared to the price of 'from' day
        int maxPriceDay = from;
        for (int i = from + 1; i <= to; i++) {
            if (prices[i] > prices[maxPriceDay]) {
                maxPriceDay = i;
            }
        }

        int maxPrice = prices[maxPriceDay];
        long income = 0;
        for (int day = from; day < maxPriceDay; day++) { //
            income += maxPrice - prices[day];
        }
        // left part after the day with the max price
        if (maxPriceDay + 1 < to) {
            income += incomeOf(prices, maxPriceDay + 1, to);
        }
        return income;
    }

    static public void calculateAndPrint(Scanner in) {
        int size = Integer.valueOf(in.nextInt());
        int[] prices = new int[size];
        for (int i = 0; i < size; i++) {
            prices[i] = in.nextInt();
        }
        System.out.println(incomeOf(prices, 0, prices.length - 1));
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int tests = Integer.valueOf(in.nextInt());
        for (int i = 1; i <= tests; i++) {
            calculateAndPrint(in);
        }
    }
}
