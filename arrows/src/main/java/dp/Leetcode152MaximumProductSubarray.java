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
 * 152. Maximum Product Subarray
 * Difficulty: Medium
 * Find the contiguous subarray within an array (containing at least one number) which has the largest product.
 *
 * For example, given the array [2,3,-2,4],
 * the contiguous subarray [2,3] has the largest product = 6.
 *
 * Tags
 *      Array
 *      Dynamic Programming
 * Similar Problems
 *      (M) Maximum Subarray
 *      (E) House Robber
 *      (M) Product of Array Except Self
 *   ==============================================================
 *      product of 2 negative number will be positive
 *
 *    Idea:
 *      same like Kadane Algorithm
 *
 *               1   2    -1      3       -2      -1
 *   maxbyfar    1   2/2  -2/-1   -3/3    -6/12   -12/6
 *   minbyfar    1   2/2  -2/-2   -6/-6   12/-6   6/-12
 *
 *   max         1   2    2       3       12      12
 *
 *   ??
 *       how to analyze this kind question in DP thought?
 *
 *   @see <a href="https://leetcode.com/problems/maximum-product-subarray/">leetcode</a>
 */
public class Leetcode152MaximumProductSubarray {
    public static int maxProduct(int[] A) {
        if (A.length == 0) {
            return 0;
        }

        int maxEndsHere = A[0];
        int minEndsHere = A[0];
        int max = A[0];

        int newMax, newMin;
        for (int i = 1; i < A.length; i++) {
            newMax = Math.max(Math.max(maxEndsHere * A[i], minEndsHere * A[i]), A[i]); // 2
            newMin = Math.min(Math.min(maxEndsHere * A[i], minEndsHere * A[i]), A[i]); // 3
            max = Math.max(newMax, max); // 1

            maxEndsHere = newMax;
            minEndsHere = newMin;
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(
                maxProduct(new int[]{1, 2, -1, 3, -2, -1}));
    }

    /**
     * Not DP Idea:
     * observations: negative numbers is even of odd.
     * if it's odd, either the left end one or the right end one should be counted,
     * scanning from left and from right to see.
     * 0 is a kind of delimiter, product accumulation will be reset to 1
     */
    public int maxProduct2(int[] nums) {
        int max = Integer.MIN_VALUE;
        int product = 1;
        int len = nums.length;

        for (int i = 0; i < len; i++) {
            max = Math.max(product *= nums[i], max);
            if (nums[i] == 0) {
                product = 1;
            }
        }

        product = 1;
        for (int i = len - 1; i >= 0; i--) {
            max = Math.max(product *= nums[i], max);
            if (nums[i] == 0) {
                product = 1;
            }
        }
        return max;
    }
}
