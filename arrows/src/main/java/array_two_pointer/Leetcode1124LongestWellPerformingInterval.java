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

package array_two_pointer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * <pre>
 * We are given hours, a list of the number of hours worked per day for a given employee.
 *
 * A day is considered to be a tiring day if and only if the number of hours worked is
 * (strictly) greater than 8.
 *
 * A well-performing interval is an interval of days for which the number of tiring days is
 * strictly larger than the number of non-tiring days.
 *
 * Return the length of the longest well-performing interval.
 *
 * Example 1:
 *
 * Input: hours = [9,9,6,0,6,6,9]
 * Output: 3
 * Explanation: The longest well-performing interval is [9,9,6].
 *
 *
 * Constraints:
 *
 * 1 <= hours.length <= 10000
 * 0 <= hours[i] <= 16
 */
public class Leetcode1124LongestWellPerformingInterval {

    public static int longestWPI2(int[] hours) {
        for (int i = 0; i < hours.length; i++) {
            // hours[i] = hours[i] > 8 ? 1 : -1;
        }
        int maxl = 0;
        // O(N^2)
        for (int i = 0; i < hours.length; i++) {
            int j = i;
            int sum = 0;
            while (j < hours.length) {
                sum += hours[j];
                if (sum >= 1) maxl = Math.max(maxl, j - i + 1);
                j++;
            }
        }
        return maxl;
    }

    // O(N)
    public static int longestWPI3(int[] h) {
        System.out.println(Arrays.toString(h));
        for (int i = 0; i < h.length; i++) {
            //  h[i] = h[i] > 8 ? 1 : -1;
        }
        // prefix sum to its index
        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0, r = 0;
        for (int i = 0; i < h.length; i++) {
            sum += h[i];
            if (sum >= 1) {
                r = i + 1;
                System.out.print("1>");
            } else {
                // keep the first index of sum for get largest required sub-array.
                if (!map.containsKey(sum)) map.put(sum, i);

                if (map.containsKey(sum - 1)) { // sum <= 0, sum - (sum-1) = 1;
                    r = Math.max(r, i - map.get(sum - 1));
                    System.out.print("2>");
                } else { // use older or initial one.
                    System.out.print("3>");
                }
            }
            System.out.println(String.format("i=%s;sum=%s;r=%s", i, sum, r));
        }
        return r;
    }

    /**
     * <pre>
     * O(N)
     * index i < j, (i, j) is a valid pair if prefixSum[j] - prefixSum[i] >= K
     *
     * goal is to get max j-i
     *
     * - fix j and minimize i. Consider any i1 < i2 < j and prefixSum[i1] <= prefixSum[i2].
     *   if (i1, j) is valid  it is longer than (i2, j).
     *  = > candidates can form a strictly monotone decreasing stack.
     *
     * - fix i and maximize j. Consider any i < j1 < j2 and prefixSum[j2] - prefix[i] >= K.
     * (i, j2) is better.
     *  => iterate j from end to begin and once find a valid (i, j) need to keep i in smdStack any longer.
     */
    public static int longestWPI(int[] h) {
        int[] s = new int[h.length + 1]; // prefixSum
        for (int i = 1; i <= h.length; ++i) {
            s[i] = s[i - 1] + (h[i - 1] /* > 8 ? 1 : -1*/);
        }
        // strictly monotone decreasing index stack
        Stack<Integer> ciStack = new Stack();
        for (int i = 0; i <= h.length; ++i) {
            if (ciStack.empty() || s[i] < s[ciStack.peek()]) ciStack.push(i);
        }

        int r = 0, k = 1;
        for (int j = h.length; j >= 0; --j) {
            //  stack skill: check empty to avoid exception
            while (!ciStack.empty() && s[j] - s[ciStack.peek()] >= k) {
                r = Math.max(r, j - ciStack.peek());
                ciStack.pop();
            }
        }
        return r;
    }
    // -----------------------------------
    public static void main(String[] args) {
        System.out.println(longestWPI(new int[] {1, -1, -1, -1, -1, -1, -1, 1, -1, 1, -1}) == 3);
        System.out.println(longestWPI(new int[] {-1, 1, -1}) == 1);
        System.out.println(longestWPI(new int[] {-1, -1, 1}) == 1);
        System.out.println(longestWPI(new int[] {1, 1, -1, -1, -1, -1, -1, 1}) == 3);
        System.out.println(longestWPI(new int[] {-1, 1, -1, -1, 1, 1, 1, 1, 1, -1}) == 10);
        System.out.println(longestWPI(new int[] {1, 1, -1, -1, -1, -1, 1}) == 3);
        System.out.println(longestWPI(new int[] {1, -1, -1, 1, -1, -1, -1, 1, 1, 1, 1}) == 11);
        System.out.println(longestWPI(new int[] {-1, -1, -1, -1, -1, 1, 1}) == 3);
        System.out.println(
                longestWPI(new int[] {1, -1, -1, 1, -1, 1, 1, -1, -1, -1, -1, -1, -1}) == 7);

        System.out.println(longestWPI2(new int[] {1, -1, -1, -1, -1, -1, -1, 1, -1, 1, -1}) == 3);
        System.out.println(longestWPI2(new int[] {-1, 1, -1}) == 1);
        System.out.println(longestWPI2(new int[] {-1, -1, 1}) == 1);
        System.out.println(longestWPI2(new int[] {1, 1, -1, -1, -1, -1, -1, 1}) == 3);
        System.out.println(longestWPI2(new int[] {-1, 1, -1, -1, 1, 1, 1, 1, 1, -1}) == 10);
        System.out.println(longestWPI2(new int[] {1, 1, -1, -1, -1, -1, 1}) == 3);
        System.out.println(longestWPI2(new int[] {1, -1, -1, 1, -1, -1, -1, 1, 1, 1, 1}) == 11);
        System.out.println(longestWPI2(new int[] {-1, -1, -1, -1, -1, 1, 1}) == 3);
        System.out.println(
                longestWPI2(new int[] {1, -1, -1, 1, -1, 1, 1, -1, -1, -1, -1, -1, -1}) == 7);

        System.out.println(longestWPI3(new int[] {1, -1, -1, -1, -1, -1, -1, 1, -1, 1, -1}) == 3);
        System.out.println(longestWPI3(new int[] {-1, 1, -1}) == 1);
        System.out.println(longestWPI3(new int[] {-1, -1, 1}) == 1);
        System.out.println(longestWPI3(new int[] {1, 1, -1, -1, -1, -1, -1, 1}) == 3);
        System.out.println(longestWPI3(new int[] {-1, 1, -1, -1, 1, 1, 1, 1, 1, -1}) == 10);
        System.out.println(longestWPI3(new int[] {1, 1, -1, -1, -1, -1, 1}) == 3);
        System.out.println(longestWPI3(new int[] {1, -1, -1, 1, -1, -1, -1, 1, 1, 1, 1}) == 11);
        System.out.println(longestWPI3(new int[] {-1, -1, -1, -1, -1, 1, 1}) == 3);
        System.out.println(
                longestWPI3(new int[] {1, -1, -1, 1, -1, 1, 1, -1, -1, -1, -1, -1, -1}) == 7);
    }
}
