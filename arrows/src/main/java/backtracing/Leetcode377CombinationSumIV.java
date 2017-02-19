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

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://leetcode.com/problems/combination-sum-iv/?tab=Description">Leetcode</a>
 */
public class Leetcode377CombinationSumIV {
    private static int result = 0;
    // backtracking
    // DP
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

    public static void main(String[] args) {
        System.out.println( combinationSum4(new int[]{},1));
    }
}
