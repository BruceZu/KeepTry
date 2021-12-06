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

public class Leetcode152MaximumProductSubarray {
  /*

    152. Maximum Product Subarray
    Given an integer array nums, find a contiguous non-empty subarray
    within the array that has the largest product, and return the product.

    It is guaranteed that the answer will fit in a 32-bit integer.
    A subarray is a contiguous subsequence of the array.


   Input: nums = [2,3,-2,4]
   Output: 6
   Explanation: [2,3] has the largest product 6.


   Input: nums = [-2,0,-1]
   Output: 0
   Explanation: The result cannot be 2, because [-2,-1] is not a subarray.


   Constraints:

   1 <= nums.length <= 2 * 104
   -10 <= nums[i] <= 10
   The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
  */

  /*
  product of 2 negative number will be positive
  Idea:
  same like Kadane Algorithm
  O(N) time, O(1) space
  */
  public static int maxProduct(int[] A) {
    if (A.length == 0) return 0;

    int max = A[0]; // max product of all sub arrays  ending at index i, initial value is A[0]
    int min = A[0]; // min product of all sub arrays  ending at index i, initial value is A[0]
    int r = A[0];

    int max_, min_; // next max and min
    for (int i = 1; i < A.length; i++) {
      max_ = Math.max(Math.max(max * A[i], min * A[i]), A[i]);
      min_ = Math.min(Math.min(max * A[i], min * A[i]), A[i]);
      r = Math.max(max_, r);

      max = max_;
      min = min_;
    }
    return r;
  }

  /*
  Observations:
    negative numbers is even or odd.
    odd: either the left end one or the right end one should be counted,

    scanning from left and from right to see.
    0 is delimiter, product accumulation will be reset to 1
    O(N) time and O(1) spaces
  */
  public static int maxProduct2(int[] nums) {
    int max = Integer.MIN_VALUE;
    int N = nums.length;

    int p = 1;
    for (int i = 0; i < N; i++) {
      max = Math.max(p *= nums[i], max);
      if (nums[i] == 0) {
        p = 1;
      }
    }

    p = 1;
    for (int i = N - 1; i >= 0; i--) {
      max = Math.max(p *= nums[i], max);
      if (nums[i] == 0) {
        p = 1;
      }
    }
    return max;
  }

  /*
   Amazon OA question
   given int array with length >=1, value is 1 or -1
   output is the length of the max sub array whose member product is 1

   same like Kadane Algorithm
   update each max sub array ending at current index i and with element product 1 or -1
   and maintain the length of max sub array in the whole given array scope and whose element product is 1
   O(N) time and O(1) space
  */
  public static int lengthOfMaxSubArray(int[] A) {
    int max = 0;
    int L1 = 0; // length of max sub array ending at current index i and product of element is 1
    int L_1 = 0; // length of max sub array ending at current index i and product of element is -1
    for (int v : A) {
      if (v == 1) {
        L1++;
        if (L_1 != 0) L_1++;
      } else {
        // v is -1
        int tmp = L1;
        L1 = L_1 == 0 ? 0 : L_1 + 1;
        L_1 = tmp == 0 ? 1 : tmp + 1;
      }
      max = Math.max(max, L1);
    }
    return max;
  }
  // -----------------------------------------------

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

    System.out.println(lengthOfMaxSubArray(new int[] {1, -1, -1, 1, 1, -1}));
    System.out.println(lengthOfMaxSubArray(new int[] {1, -1, -1, -1, 1, 1}));
    System.out.println(lengthOfMaxSubArray(new int[] {-1, -1, 1, -1, 1, 1}));
  }
}
