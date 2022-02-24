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

package random;

import java.util.Random;

public class Leetcode398RandomPickIndex {
  /*
      Leetcode 398. Random Pick Index
      Given an integer array nums with possible duplicates,
      randomly output the index of a given target number.
      You can assume that the given target number must exist in the array.

    Implement the Solution class:

    Solution(int[] nums) Initializes the object with the array nums.
    int pick(int target) Picks a random index i from nums where nums[i] == target.
    If there are multiple valid i's, then each index should have an equal probability of returning.


    Input
    ["Solution", "pick", "pick", "pick"]
    [[[1, 2, 3, 3, 3]], [3], [1], [3]]
    Output
    [null, 4, 0, 2]

    Explanation
    Solution solution = new Solution([1, 2, 3, 3, 3]);
    solution.pick(3); // It should return either index 2, 3, or 4 randomly.
                         Each index should have equal probability of returning.
    solution.pick(1); // It should return 0. Since in the array only nums[0] is equal to 1.
    solution.pick(3); // It should return either index 2, 3, or 4 randomly.
                         Each index should have equal probability of returning.

      1 <= nums.length <= 2 * 104
      -2^31 <= nums[i] <= 2^31 - 1
      target is an integer from nums.
      At most 10^4 calls will be made to pick.
  */
  static class Solution {
    int[] nums;
    Random r = new Random();

    public Solution(int[] nums) {
      // TODO: corner cases checking
      this.nums = nums;
    }

    /*
    Idea:
      This is a special case of `Reservoir Sampling` when the `k` is 1 The benefit of `Reservoir
      Sampling` algorithm is O(1) space
     */
    // O(N) time and O(1) space
    public int pick(int target) {
      int cnt = 0;
      int resultIndex = 0;
      for (int i = 0; i < nums.length; i++) {
        if (nums[i] != target) continue;
        if (r.nextInt(++cnt) == 0) resultIndex = i;
      }
      return resultIndex;
    }
  }
}

/**
 * Your Solution object will be instantiated and called as such: Solution obj = new Solution(nums);
 * int param_1 = obj.pick(target);
 */
