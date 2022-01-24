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

public class Leetcode53MaximumSubarray {
  /*
    53. Maximum Subarray
    Given an integer array nums, find the contiguous subarray
    (containing at least one number)
    which has the largest sum and return its sum.

    A subarray is a contiguous part of an array.

    Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
    Output: 6
    Explanation: [4,-1,2,1] has the largest sum = 6.


    Input: nums = [1]
    Output: 1


    Input: nums = [5,4,-1,7,8]
    Output: 23



    Constraints:

        1 <= nums.length <= 3 * 10^4
        -10^5 <= nums[i] <= 10^5

  */
  /*
   Kadane's Algorithm
   [−2,1,−3,4,−1,2,1,−5,4], [4,−1,2,1] sum = 6.
   [−2,1,−3,4,2,−5,4], [4,2] sum = 6.
   [4,2], [4,2] sum = 6.
   [-4,-2], [-2] sum = -2.

   [3,4,0,-7,7],itself, sum=7 there are more than sub-string with the max sum 7
   [3,4],itself,sum=7

   If: current sum is negative, why not start a new accumulate from next number?
   else: current sum>=0 still have change to accumulate up >= pre max and extending the length of sub-string

   O(N) time
   O(1) space
  */

  public int maxSubArray(int[] nums) {
    int r = -100000, sum = 0;
    for (int i : nums) {
      sum = Math.max(0, sum) + i;
      r = Math.max(r, sum);
    }
    return r;
  }

  /*
  Follow up:
  If you have figured out the O(n) solution,
     try coding another solution using the divide and conquer approach,
  which is more subtle.
  see `Finding subsegments with the maximal sum`*/

  /*
  Follow up:
    find the sub-array
    if there are more of them find out the first one
  */
}
