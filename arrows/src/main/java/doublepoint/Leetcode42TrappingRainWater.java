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

package doublepoint;

/**
 * <pre>
 * 42. Trapping Rain Water
 * Difficulty: Hard
 * Given n non-negative integers representing an elevation map where the width of each bar is 1,
 * compute how much water it is able to trap after raining.
 *
 * For example,
 * Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.
 *
 *
 * The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1].
 * In this case, 6 units of rain water (blue section) are being trapped.
 *
 * Tags
 *      Array
 *      Stack
 *      Two Pointers
 * Similar Problems
 *      (M) Container With Most Water
 *      (M) Product of Array Except Self
 *      ========================================================================
 *      Cases:
 *      4, 2, 0, 3, 2, 4, 3, 4 -> 10
 *      0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 -> 6
 *      2, 1, 0, 2 -> 3
 *      5, 5, 1, 7, 1, 1, 5, 2, 7, 6 -> 9
 *      4, 2, 3 -> 1
 *      Idea: compare value of l and r to decide go from next or pre.
 *            if next or pre can contain water, can calculate it
 *            else update l or r.
 *      Note:
 *            once l and r is updated, need compare them again to decide to use next or pre.
 *            always make sure the next and pre is valid
 */
public class Leetcode42TrappingRainWater {

    public static int trap(int[] arr) {
        if (arr == null || arr.length <= 2) {
            return 0;
        }
        int l = 0;
        int r = arr.length - 1;
        int result = 0;

        while (l < r) {
            if (arr[l] <= arr[r]) {
                int next = l + 1;
                while (next < r && arr[next] <= arr[l]) {
                    result += arr[l] - arr[next];
                    next++;

                }
                l = next;
            } else {
                int pre = r - 1;
                while (l < pre && arr[pre] <= arr[r]) {
                    result += arr[r] - arr[pre];
                    pre--;

                }
                r = pre;
            }
        }
        return result;
    }
}
