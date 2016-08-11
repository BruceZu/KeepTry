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
 *    |_____|   |_____|      |___|       : in came scope
 *
 *  Note:
 *      - each recursion's valid l and r
 *      - there is maxPrice in [l, r], not always means there is in came.
 *        if it is happen to be on l index.
 *  Runtime depends on where the max prices index appear, o(n) ~ o(n^2)
 *
 *  @see <a href="https://www.hackerrank.com/challenges/stockmax">hackerrank</a>
 */
public class HackerrankStockMaximize {
    static private long incameOf(int[] prices, int l, int r) {
        int maxPriceIndex = l;
        for (int i = l + 1; i <= r; i++) {
            if (prices[i] > prices[maxPriceIndex]) {
                maxPriceIndex = i;
            }
        }

        int maxPrice = prices[maxPriceIndex];
        long incame = 0;
        for (int k = l; k < maxPriceIndex; k++) { //
            incame += maxPrice - prices[k];
        }
        if (maxPriceIndex + 1 < r) {
            incame += incameOf(prices, maxPriceIndex + 1, r);
        }
        return incame;
    }

    static public void calculateAndPrint(Scanner in) {
        int size = Integer.valueOf(in.nextInt());
        int[] prices = new int[size];
        for (int i = 0; i < size; i++) {
            prices[i] = in.nextInt();
        }
        System.out.println(incameOf(prices, 0, prices.length - 1));
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int tests = Integer.valueOf(in.nextInt());
        for (int i = 1; i <= tests; i++) {
            calculateAndPrint(in);
        }
    }
}
