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

/*
53. Maximum Subarray
Find the contiguous subarray within an array
  (containing at least one number) which has the largest sum.

 [−2,1,−3,4,−1,2,1,−5,4], [4,−1,2,1] sum = 6.
 [−2,1,−3,4,2,−5,4], [4,2] sum = 6.
 [4,2], [4,2] sum = 6.
 [-4,-2], [-2] sum = -2.

 [3,4,0,-7,7],itself, sum=7 there are more than sub-string with the max sum 7
 [3,4],itself,sum=7

 If: current sum is negative, why not start a new accumulate from next number?
 else: current sum>=0 still have change to accumulate up >= pre max and extending the length of sub-string

*/
public class Leetcode53MaximumSubarray {
  public int maxSubArray(int[] nums) {
    int N = nums.length;
    int r = nums[0], m = nums[0];
    for (int i = 1; i < N; i++) {
      m = ((m < 0) ? 0 : m) + nums[i];
      r = m >= r ? m : r; // == is useful to figure out a case of sub-string with the max sum.
    }
    return r;
  }
}
