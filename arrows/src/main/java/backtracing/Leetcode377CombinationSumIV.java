//  Copyright 2017 The keepTry Open Source Project
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

package backtracing;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://leetcode.com/problems/combination-sum-iv/?tab=Description">Leetcode</a>
 * Follow up:
 * What if negative numbers are allowed in the given array?
 * How does it change the problem?
 * What limitation we need to add to the question to allow negative numbers?
 */
public class Leetcode377CombinationSumIV {
    //-------------------------------DP left to right --------------
    // O(target ^ nums.length)
    static public int combinationSum4_(int[] nums, int target) {
        int[] numOfSumsOf = new int[target + 1]; // cache, default is 0
        numOfSumsOf[0] = 1;
        for (int i = 1; i <= target; i++) {
            for (int iNum = 0; iNum < nums.length; iNum++) {
                if (i - nums[iNum] >= 0) {
                    numOfSumsOf[i] += numOfSumsOf[i - nums[iNum]];
                }
            }
        }
        return numOfSumsOf[target];
    }

    //-------------------------------   DP  recursion ------------------------
    // cache: static private Map<Integer, Integer> numberOfSumsOf = new HashMap();
    // sort maybe help to improve performance
    static public int combinationSum4(int[] nums, int target) {
        int numberOfSums_default_0 = 0; //
        for (int i = 0; i < nums.length; i++) {
            if (target == nums[i]) {
                numberOfSums_default_0 += 1;
            } else if (target > nums[i]) { // assume all number is positive
                numberOfSums_default_0 += combinationSum4(nums, target - nums[i]);
            }
        }
        return numberOfSums_default_0;
    }

    //---------------------------    backtracking  ------------------------
    // O(target ^ nums.length)
    static class BackTracking {
        private static int result = 0;

        // improvement : sort and checking the scope
        public static int combinationSum4(int[] nums, int target) {
            selectOne(nums, target, 0);
            return result;
        }

        private static void selectOne(int[] nums, int target, int curSum) {
            if (curSum == target) {
                result++;
                return; // care
            }
            if (curSum > target) {
                return;
            }
            for (int i = 0; i < nums.length; i++) {
                selectOne(nums, target, curSum + nums[i]);
            }
        }
    }

    // ----------------------------------
    public static void main(String[] args) {
        // System.out.println(combinationSum4(new int[]{}, 1));
        System.out.println(combinationSum4(new int[]{1, 2, 3}, 4));
    }
}
