//  Copyright 2019 The KeepTry Open Source Project
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

package binarysearch;

public class Leetcode1011CapacityToShipPackagesWithinDDays {

    /**
     *  <pre>
     *  - difference with backtracking
     *  - difference with partition
     *  - this is to figure out the minimum capacity: minimum max sum
     *    -- the max value of elements is also a bar
     *    -- during the binary trying, the first matched capacity may be is not the border value
     *       so need continue trying and narrow the scope, till only one is left which is the border one. or the left bar.
     *  - how to get the max minimum sum
     *  - if do not use sum, else use max
     *  - if it is array in circle.
     *
     *  Note:
     * 1 <= D <= weights.length <= 50000
     * 1 <= weights[i] <= 500
     * <a href='https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/'>leetcode</a>
     */
    public static int shipWithinDays(int[] weights, int withinDays) {
        int weightest = Integer.MIN_VALUE, totalWeigh = 0;
        for (int w : weights) {
            weightest = Math.max(w, weightest);
            totalWeigh += w;
        }
        int lc = weightest, rc = totalWeigh;
        if (withinDays == 1) return totalWeigh;
        while (lc < rc) {
            int midc = (lc + rc) / 2;
            int days = cutWithCapacity(weights, midc);
            if (days > withinDays) {
                // need bigger capacity
                // move slowly
                lc = midc == lc ? rc : midc;
                // move fast in valid scope: lc = midc+1
            }
            if (days < withinDays) {
                // need smaller capacity
                // move slowly
                rc = midc;
                // move fast in valid scope: rc = midc == lc ? lc : midc - 1;
            }

            if (days == withinDays) {
                // midc is valid, but need try the smaller capacity
                rc = midc;
            }
        }
        int daysWithResultCapacity = cutWithCapacity(weights, lc);
        return lc; // or rc
    }

    /**
     * <pre>
     * Assume
     * - @param weights element is positive integer. length >=2
     * - @param capacity <= element
     *
     * split the array to sub arrays by calculating sum of sub array:
     * if adding the current element value
     * to current sub array's sum will make the sum excess the capacity,
     * then start next sub array from current element.
     *
     * return number of sub arrays
     *
     * By the way, may be need to return the max or minimum value
     * of current split sub arrays' sums
     *
     */
    private static Integer cutWithCapacity(int[] weights, int capacity) {
        if (weights == null || weights.length < 2) return null;

        int count = 0, curSum = 0;
        for (int w : weights) {
            if (w > capacity) return null;
            if (curSum + w > capacity) {
                if (curSum != 0) {
                    count++;
                }
                curSum = w;
            } else {
                curSum += w;
            }
        }

        return ++count;
    }

    public static void main(String[] args) {
        System.out.println(shipWithinDays(new int[] {1, 2, 3}, 1) == 6);
        System.out.println(shipWithinDays(new int[] {3, 2, 2, 4, 1, 4}, 3) == 6);
        System.out.println(shipWithinDays(new int[] {1, 2, 3, 4, 4, 6, 7, 8, 9, 10}, 5) == 14);
        System.out.println(shipWithinDays(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 5) == 15);
        System.out.println(shipWithinDays(new int[] {1, 2, 3, 1, 1}, 4) == 3);
        System.out.println(
                shipWithinDays(new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 6) == 3);
    }
}
