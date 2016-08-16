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

/**
 * <pre>
 *  Kadane Algorithm -> Maximum subarray problem
 *  Jay Kadane of Carnegie-Mellon University (Bentley 1984).
 *
 *  O(n) in time complexity and memory usage
 *
 * when all the elements are negative values?
 * 1    use a variable (max_singleelement).
 *      At the end, if this value is lower than 0 then return max_single_element
 *      otherwise return maxSoFar;
 *
 * 2    improved version:
 *      maxCur = Math.max(0     , maxCur += arr[i]);
 *      ->
 *      maxCur = Math.max(arr[i], maxCur += arr[i]);
 *                           |
 *  why does Kadane Algorithm work?
 *     why the the peak is the position where the Maximum subarray should end.
 *     why the position is the right start position, from where start a serial positive sum value, just after
 *     the pre position where sum value is <=0
 *     case of improved version:
 *     [-2, -3, 4, -1, -2, 1, 5, -3, 4 ]
 *     [-2]*
 *         [-3]
 *            [ 4]*
 *            [    3 ]
 *            [        1]
 *            [            2]
 *            [              7]*
 *            [                 4]
 *            [                     8]* peak
 *
 *    case of improved version:
 *     [0, -1, -2, -1, 0, 1, 2, 1, 0, -1, -2, -1, 0 ]
 *     [0]*
 *     [  -1]
 *          [ -2 ]
 *               [-1]
 *                   [0]
 *                   [   1]*
 *                   [     3]*
 *                   [        4]* peak end
 *                   [            4]
 *                   [               3]
 *                   [                   1]
 *                   [                       0]
 *                   [                          0]
 *     case of improved version:
 *        [-1, -2, -2, -1, -3]
 *        [-1]* peak
 *            [-2]
 *               [-2]
 *                   [-1]
 *                       [-3]
 *
 *
 *  Test case:
 *    maximum:
 *     -2, 1, -3, 4, -1, 2, 1, -5, 4                -> 6
 *     -2, -3, 4, -1, -2, 1, 5, -3, 4               -> 8
 *     -1, -2, -2, -1, -3                           -> -1
 *     1, 2, 2, 1, 3                                -> 9
 *     0, -1, -2, -1, 0, 1, 2, 1, 0, -1, -2, -1, 0  -> 4
 *
 *   mininum:
 *     -2, 1, -3, 4, -1, 2, 1, -5, 4                -> -5
 *     -2, -3, 4, -1, -2, 1, 5, -3, 4               -> -5
 *     -1, -2, -2, -1, -3                           -> -9
 *     1, 2, 2, 1, 3                                -> 1
 *     0, -1, -2, -1, 0, 1, 2, 1, 0, -1, -2, -1, 0  -> -4
 *
 *   more test case @see <a href="https://leetcode.com/problems/maximum-subarray/">leetcode</a>
 *
 *  @see <a href="https://en.wikipedia.org/wiki/Maximum_subarray_problem#Kadane.27s_algorithm">Kadane's algorithm</a>
 */
public class KadaneAlgorithm {
    public static int maximum(int[] arr) {
        int maxEndsHere = arr[0], peak = arr[0];

        for (int i = 1; i < arr.length; i++) {
            maxEndsHere = Math.max(arr[i], maxEndsHere += arr[i]);
            peak = Math.max(maxEndsHere, peak);
        }
        return peak;
    }

    // Alternative
    public static int maximum2(int[] arr) {
        int bottom = 0;
        int rightDelta = arr[0], sum = arr[0];

        for (int i = 1; i < arr.length; i++) {
            sum += arr[i];
            if (sum - bottom > rightDelta) {
                rightDelta = sum - bottom;
            }
            if (sum < bottom) {
                bottom = sum;
            }
        }
        return rightDelta;
    }

    public static int minimum(int[] arr) {
        int maxEndsHere = arr[0], peak = arr[0];

        for (int i = 1; i < arr.length; i++) {
            maxEndsHere = Math.min(arr[i], maxEndsHere += arr[i]);
            peak = Math.min(maxEndsHere, peak);
        }
        return peak;
    }

    // Alternative
    public static int minimum2(int[] arr) {
        int top = 0;
        int rightDelta = arr[0], sum = arr[0];

        for (int i = 1; i < arr.length; i++) {
            sum += arr[i];
            if (sum - top < rightDelta) {
                rightDelta = sum - top;
            }
            if (sum > top) {
                top = sum;
            }
        }
        return rightDelta;
    }
}
