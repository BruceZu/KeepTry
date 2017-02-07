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

package greedy;

/**
 * <pre>
 * 55. Jump Game
 * Difficulty: Medium
 * Given an array of non-negative integers, you are initially positioned at the first index of the array.
 *
 * Each element in the array represents your maximum jump length at that position.
 *
 * Determine if you are able to reach the last index.
 *
 * For example:
 * A = [2,3,1,1,4], return true.
 *
 * A = [3,2,1,0,4], return false.
 *
 * Company Tags Microsoft
 * Tags Array Greedy
 * ----------------------------------------------------------------------------
 *
 *  the cost of checking does not deserve the benefit:
 *      if (greedyTo >= nums.length - 1) {
 *          return true;
 *      }
 */
public class Leetcode55JumpGame {
    public boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }
        int i = nums.length - 2;
        int requiredMinimumSteps = 1;
        while (i > 0) {
            if (nums[i] >= requiredMinimumSteps) {
                requiredMinimumSteps = 1;
            } else {
                requiredMinimumSteps++;
            }
            i--;
        }
        return requiredMinimumSteps <= nums[0];
    }
}

