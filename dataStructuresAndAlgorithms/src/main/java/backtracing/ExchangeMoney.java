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

/**
 * <pre>
 * Minimum number of coins I can use to get to a given amount
 * US coins: 1 25 50
 *
 * greedy can not work, e.g. [50， 10， 3], 62
 *
 * care:
 *    do not change the 'left' variable between recursion, it will used for next turn in the loop.
 *    next recursion from current i ----> make sure the resolutions is not repeated as a set or collection.
 *
 *
 * performance :
 * sort array in ascending order then we can
 * check current left with conins[i], break early.
 */
public class ExchangeMoney {
    public static int exchange(int sum) {
        int[] r = new int[0];
        recursionWithAscendingArray(new int[]{1, 25, 50}, sum, 0, r, 0);
        return r[0];
    }

    private static void recursionWithDescendingArray(int[] conins, int left, int from, int[] result, int counts) {
        if (left == 0) {
            result[0] = result[0] > counts ? counts : result[0];
            return;
        }

        for (int i = from; i < conins.length; i++) {
            if (left < conins[i]) {
                continue;
            }
            recursionWithDescendingArray(conins, left - conins[i], i, result, counts + 1); // care "left - conins[i]"
        }
    }

    private static void recursionWithNoSortedArray(int[] conins, int left, int from, int[] result, int counts) {
        if (left == 0) {
            result[0] = result[0] > counts ? counts : result[0];
            return;
        }

        for (int i = from; i < conins.length; i++) {
            if (left < conins[i]) {
                continue;
            }
            recursionWithNoSortedArray(conins, left - conins[i], i, result, counts + 1); // care "left - conins[i]"
        }
    }

    private static void recursionWithAscendingArray(int[] conins, int left, int from, int[] result, int counts) {
        if (left == 0) {
            result[0] = result[0] > counts ? counts : result[0];
            return;
        }

        for (int i = from; i < conins.length; i++) {
            if (left < conins[i]) {
                break;
            }
            recursionWithAscendingArray(conins, left - conins[i], i, result, counts + 1); // care "left - conins[i]"
        }
    }

    public static void main(String[] args) {
        System.out.println(exchange(278));
    }
}
