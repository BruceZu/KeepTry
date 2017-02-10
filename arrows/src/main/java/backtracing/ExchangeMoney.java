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

package backtracing;

import java.util.Arrays;

/**
 * <pre>
 * Minimum number of coins I can use to get to a given amount
 * US coins: 1 25 50
 *
 * greedy can not work, e.g. [50， 10， 3], 62
 */
public class ExchangeMoney {

    static int minumNumberOfChanges = Integer.MAX_VALUE;

    // e.g. 20,  5, 10, 1.  do not need remember the 'from' index
    private static void recursionWithNoSortedArray(int[] coinValueOf, int leftNeedToChange, int numberOfChangedCoins) {
        if (leftNeedToChange == 0) {
            minumNumberOfChanges = minumNumberOfChanges > numberOfChangedCoins
                    ? numberOfChangedCoins
                    : minumNumberOfChanges;
            return;
        }

        for (int i = 0; i < coinValueOf.length; i++) {
            if (leftNeedToChange < coinValueOf[i]) {
                continue;
            }

            recursionWithNoSortedArray(coinValueOf,
                    leftNeedToChange - coinValueOf[i],
                    numberOfChangedCoins + 1); // care "left - conins[i]"
        }
    }

    // e.g. 1, 5, 10, 20, do not need remember the 'from' index, each loop from index 0 to try
    private static void recursionWithAscendingArray(int[] coinValueOf,
                                                    int leftNeedToChange,
                                                    int numberOfChangedCoins) {
        if (leftNeedToChange == 0) {
            minumNumberOfChanges = minumNumberOfChanges > numberOfChangedCoins
                    ? numberOfChangedCoins
                    : minumNumberOfChanges;
            return;
        }

        for (int i = 0; i < coinValueOf.length; i++) {
            if (leftNeedToChange < coinValueOf[i]) {
                break; // stop this way
            }
            recursionWithAscendingArray(coinValueOf,
                    leftNeedToChange - coinValueOf[i],
                    numberOfChangedCoins + 1); // care "left - conins[i]"
        }
    }

    // e.g. 20, 10, 5, 1
    private static void recursionWithDescendingArray(int[] coinValueOf,
                                                     int leftNeedToChange,
                                                     int from, // this is optional
                                                     int numberOfChangedCoins) {
        if (leftNeedToChange == 0) {
            minumNumberOfChanges = minumNumberOfChanges > numberOfChangedCoins
                    ? numberOfChangedCoins
                    : minumNumberOfChanges;
            return;
        }

        for (int i = from; i < coinValueOf.length; i++) { // without the from, it still works
            if (coinValueOf[i] > leftNeedToChange) {
                continue; // try next coin
            }
            recursionWithDescendingArray(coinValueOf,
                    leftNeedToChange - coinValueOf[i],
                    i,
                    numberOfChangedCoins + 1);
        }
    }

    public static void main(String[] args) {
        int sum = 18;
        int[] coins = new int[]{10, 5, 4, 1};

        recursionWithDescendingArray(coins, sum, 0, 0);
        System.out.println(minumNumberOfChanges);

        minumNumberOfChanges = Integer.MAX_VALUE;
        Arrays.sort(coins);
        recursionWithAscendingArray(coins, sum, 0);
        System.out.println(minumNumberOfChanges);

        minumNumberOfChanges = Integer.MAX_VALUE;
        coins = new int[]{5, 4, 10, 1};
        recursionWithNoSortedArray(coins, sum, 0);
        System.out.println(minumNumberOfChanges);

        //
        System.out.println("-------------");
        minumNumberOfChanges = Integer.MAX_VALUE;
        coins = new int[]{50, 3, 10};
        sum = 62;
        recursionWithNoSortedArray(coins, sum, 0);
        System.out.println(minumNumberOfChanges);

        minumNumberOfChanges = Integer.MAX_VALUE;
        coins = new int[]{50, 10, 3};
        recursionWithDescendingArray(coins, sum, 0, 0);
        System.out.println(minumNumberOfChanges);

        minumNumberOfChanges = Integer.MAX_VALUE;
        Arrays.sort(coins);
        recursionWithAscendingArray(coins, sum, 0);
        System.out.println(minumNumberOfChanges);
    }
}
