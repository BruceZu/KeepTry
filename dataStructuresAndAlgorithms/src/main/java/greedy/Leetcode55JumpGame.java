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
        if (nums.length < 2) {
            return true;
        }
        for (int cur = nums.length - 2; cur >= 0; cur--) {
            if (nums[cur] == 0) {
                int njs = 1; // need jumps
                while (nums[cur] < njs) {
                    njs++;
                    cur--;
                    if (cur < 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean canJump2(int[] nums) {
        if (nums.length < 2) {
            return true;
        }
        int njs = 1;
        for (int cur = nums.length - 2; cur >= 0; cur--) {
            if (nums[cur] < njs) {
                njs++;
            } else {
                njs = 1;
            }
        }
        return njs == 1;
    }

    public static boolean canJump3(int[] nums) {
        int gt = 0; // greedyTo
        for (int cur = 0; cur < nums.length; cur++) {
            if (gt < cur) {
                return false;
            }
            int curgt = cur + nums[cur];
            gt = curgt > gt ? curgt : gt;
        }
        return true;
    }
}

