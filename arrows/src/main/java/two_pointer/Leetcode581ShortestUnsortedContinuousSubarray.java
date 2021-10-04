//  Copyright 2021 The KeepTry Open Source Project
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

package two_pointer;

public class Leetcode581ShortestUnsortedContinuousSubarray {
  /*
  Leetcode 581. Shortest Unsorted Continuous Subarray

    Given an integer array nums, you need to find one continuous
    subarray that if you only sort this subarray in ascending order,
    then the whole array will be sorted in ascending order.

    Return the shortest such subarray and output its length.


    Input: nums = [2,6,4,8,10,9,15]
    Output: 5
    Explanation: You need to sort [6, 4, 8, 10, 9] in ascending order to make the whole array sorted in ascending order.
    Example 2:

    Input: nums = [1,2,3,4]
    Output: 0
    Example 3:

    Input: nums = [1]
    Output: 0


    Constraints:

    1 <= nums.length <= 10^4
    -10^5 <= nums[i] <= 10^5

    Follow up: Can you solve it in O(n) time complexity?
  */

  /*
  [1,2,3,3,3]
  [1,3,2,2,2]
  It is not ascending, it is un-descending

  the subarray max and min

  the left boundary [0,x][x+1, y-1] [y, N-1]
  nums[x]<= min of [x+1, N-1] or [x+1, y-1]
  nums[y]>= max of [0, y-1] or [x+1, y-1]

  O(N) time and space
  */
  public int findUnsortedSubarray_(int[] nums) {
    int N = nums.length;
    int[] min = new int[N], max = new int[N];
    int cMin = Integer.MAX_VALUE;
    for (int i = N - 1; i >= 0; i--) {
      min[i] = Math.min(nums[i], cMin);
      cMin = min[i];
    }
    int cMax = Integer.MIN_VALUE;
    for (int i = 0; i < N; i++) {
      max[i] = Math.max(nums[i], cMax);
      cMax = max[i];
    }

    int i = 0, r = nums.length - 1;
    while (i <= nums.length - 2 && nums[i] <= min[i + 1]) i++;
    if (i == nums.length - 1) return 0;
    while (r >= 1 && nums[r] >= max[r - 1]) r--;
    return r - i + 1;
  }
  /*
   find out the min and max of [x+1, y-1]
   O(N) time O(1) space
  */
  public int findUnsortedSubarray(int[] nums) {
    int min = Integer.MAX_VALUE;
    boolean flag = false;
    for (int i = 1; i < nums.length; i++) {
      if (nums[i - 1] > nums[i])
        flag = true; // downward slope start and start track the min of nums[i] from now on
      if (flag) min = Math.min(min, nums[i]);
    }

    int l;
    for (l = 0; l < nums.length; l++) {
      if (nums[l] > min) break;
    }
    //
    flag = false;
    int max = Integer.MIN_VALUE;
    for (int i = nums.length - 2; i >= 0; i--) {
      if (nums[i] > nums[i + 1])
        flag = true; // rising up slope start and start track the max of nums[i] from now on
      if (flag) max = Math.max(max, nums[i]);
    }
    int r;
    for (r = nums.length - 1; r >= 0; r--) {
      if (nums[r] < max) break;
    }
    // [l,r] is the sub array
    return Math.max(0, r - l + 1);
  }
}
