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

package array;

/**
 * Given an array of n integers where n > 1, nums, return an array output such that output[i] is
 * equal to the product of all the elements of nums except nums[i].
 *
 * <p>Solve it without division and in O(n).
 *
 * <p>For example, given [1,2,3,4], return [24,12,8,6].
 *
 * <p>Follow up:
 *
 * <p>Could you solve it with constant space complexity? (Note: The output array does not count as
 * extra space for the purpose of space complexity analysis.)
 * https://leetcode.com/problems/product-of-array-except-self/
 */
public class Leetcode238ProductOfArrayExceptSelf {
  public static int[] productExceptSelf2(int[] nums) {
    int[] r = new int[nums.length];
    for (int i = 0; i < r.length; i++) {
      r[i] = 1;
    }
    for (int i = 0, j = nums.length - 1, lp = 1, rp = 1; i < nums.length; ++i, --j) {
      r[i] *= lp;
      lp *= nums[i];
      r[j] *= rp;
      rp *= nums[j];
    }
    return r;
  }

  public static int[] productExceptSelf(int[] nums) {
    int[] r = new int[nums.length];
    r[r.length - 1] = 1;
    for (int i = r.length - 2; i >= 0; i--) {
      r[i] = r[i + 1] * nums[i + 1];
    }
    int lp = 1;
    for (int i = 1; i < r.length; i++) {
      lp *= nums[i - 1];
      r[i] *= lp;
    }
    return r;
  }
}
