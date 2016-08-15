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
 * O(n) in time complexity and memory usage
 *
 * when all the elements are negative values?
 * 1    use a variable (max_singleelement).
 *      At the end,
 *      if this value is lower than 0 then return max_single_element
 *      otherwise return maxSoFar;
 *
 * 2    improved version // Statement A ->
 *      maxCur = Math.max(arr[i], maxCur += arr[i]);
 *                           |
 *
 * Test case: [-2, -3, 4, -1, -2, 1, 5, -3, 4 ] -> 8
 *              0  4   3   1  2  7   4  8
 *              -3  4   3   1  2  7   4  8 //improved version
 *
 *            [- 1,  - 2,  - 2,  - 1,  - 3]
 *              0     0      0     0   -> max_single_element = -1
 *              -2    -2    -1     -3  -> maxSoFar = -1; //improved version
 *
 *  @see <a href="https://en.wikipedia.org/wiki/Maximum_subarray_problem#Kadane.27s_algorithm">Kadane's algorithm</a>
 */
public class KadaneAlgorithm {
    public static int go(int[] arr) {
        int maxCur = arr[0], maxSoFar = arr[0];

        for (int i = 1; i < arr.length; i++) {
            maxCur = Math.max(arr[i], maxCur += arr[i]); // Statement A, improved version
            maxSoFar = Math.max(maxCur, maxSoFar);
        }
        return maxSoFar;
    }

    public static int go2(int[] arr) {
        int min = 0;
        int max = arr[0], sum = arr[0];

        for (int i = 1; i < arr.length; i++) {
            sum += arr[i];
            if (sum - min > max) {
                max = sum - min;
            }
            if (sum < min) {
                min = sum;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(go(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        System.out.println(go(new int[]{-2, -3, 4, -1, -2, 1, 5, -3, 4}));
        System.out.println(go(new int[]{-1, -2, -2, -1, -3}));
        System.out.println(go(new int[]{1, 2, 2, 1, 3}));
        System.out.println();
        System.out.println(go2(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        System.out.println(go2(new int[]{-2, -3, 4, -1, -2, 1, 5, -3, 4}));
        System.out.println(go2(new int[]{-1, -2, -2, -1, -3}));
        System.out.println(go2(new int[]{1, 2, 2, 1, 3}));
    }
}
