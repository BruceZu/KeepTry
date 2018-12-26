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
 *   @see <a href="https://leetcode.com/problems/maximum-product-subarray/">leetcode</a>
 */
public class Leetcode152MaximumProductSubarray {
    // O(N)
    public static int maxProduct(int[] A) {
        if (A.length == 0) {
            return 0;
        }

        int max = A[0]; // in set of possible solutions ending at index i
        int min = A[0];
        // in set of possible solutions ending at index i. product of 2 negative numbers is positive
        // that is why need a min here.
        int r = A[0];

        int newMax, newMin;
        for (int i = 1; i < A.length; i++) {
            newMax = Math.max(Math.max(max * A[i], min * A[i]), A[i]);
            newMin = Math.min(Math.min(max * A[i], min * A[i]), A[i]);
            r = Math.max(newMax, r);

            max = newMax;
            min = newMin;
        }
        return r;
    }

    /**
     * Not DP Idea: observations: negative numbers is even or odd. odd: either the left end one or
     * the right end one should be counted,
     *
     * <p>scanning from left and from right to see. 0 is delimiter, product accumulation will be
     * reset to 1 O(N)
     */
    public static int maxProduct2(int[] nums) {
        int r = Integer.MIN_VALUE;
        int len = nums.length;

        int product = 1;
        for (int i = 0; i < len; i++) {
            r = Math.max(product *= nums[i], r);
            if (nums[i] == 0) {
                product = 1;
            }
        }

        product = 1;
        for (int i = len - 1; i >= 0; i--) {
            r = Math.max(product *= nums[i], r);
            if (nums[i] == 0) {
                product = 1;
            }
        }
        return r;
    }

    //-----------------------------------------------

    // TODO float?
    public static void main(String[] args) {
        System.out.println(maxProduct(new int[] {-2, 0, -1}));
        System.out.println(maxProduct(new int[] {0, 0, 0}));
        System.out.println(maxProduct(new int[] {0, 0, -2, 0}));
        System.out.println(maxProduct(new int[] {0, 0, 3, 0}));
        System.out.println(maxProduct(new int[] {1, 2, -1, 3, -2, -1}));
        System.out.println(maxProduct(new int[] {1, 2, 3, 2, -1, 2, 4, 5, 7 - 1, 1, 2}));

        System.out.println(maxProduct2(new int[] {-2, 0, -1}));
        System.out.println(maxProduct2(new int[] {0, 0, 0}));
        System.out.println(maxProduct2(new int[] {0, 0, -2, 0}));
        System.out.println(maxProduct2(new int[] {0, 0, 3, 0}));
        System.out.println(maxProduct2(new int[] {1, 2, -1, 3, -2, -1}));
        System.out.println(maxProduct2(new int[] {1, 2, 3, 2, -1, 2, 4, 5, 7 - 1, 1, 2}));
    }
}
