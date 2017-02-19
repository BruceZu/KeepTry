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
import java.util.Arrays;
import java.util.List;

/**
 * <a href="https://leetcode.com/problems/permutations/?tab=Description">LeetCode</a>
 */
public class Leetcode46Permutations {
    class Solution {
        //---------- -using  'if (!cur.contains(nums[i])) ' to decide the scope of next one to be select-----
        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> r = new ArrayList<>();
            select(nums, r, new ArrayList<Integer>(), 0);
            return r;
        }

        private void select(int[] nums, List<List<Integer>> r, List<Integer> cur, int from) {
            if (cur.size() == nums.length) {
                r.add(new ArrayList(cur));
                return;
            }
            for (int i = 0; i < nums.length; i++) {
                if (!cur.contains(nums[i])) {
                    cur.add(nums[i]);
                    select(nums, r, cur, i + 1);
                    cur.remove(cur.size() - 1);
                }
            }
        }
    }

    //---------- -using swap to decide the scope of next one to be select-----
    // assume each number is distinct
    // with: swap the current option to the scope start.
    // scope: start and to:
    //   to the end
    //
    // relation of current option and next recursion's  scope start:
    //      next recursion's  scope start is current scope's second one,
    // stop:
    //    only one left in scope, its permutation is itself
    // note: each time need restore back to original status before select next option
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        perm(result, nums, 0);
        return result;
    }

    private static void perm(List<List<Integer>> result, int[] nums, int selectFrom) {
        if (selectFrom == nums.length - 1) {
            // int [] -> Integer[], else cannot using Arrays.asList(r)
            List<Integer> r = new ArrayList<Integer>(nums.length);
            int i=0;
            while (i < nums.length) {
                r.add(nums[i++]);
            }
            result.add( r);
            return;
        }
        for (int i = selectFrom; i < nums.length; i++) {
            fixCurOptionAtScopeLeft(nums, selectFrom, i);
            perm(result, nums, selectFrom + 1);
            fixCurOptionAtScopeLeft(nums, i, selectFrom);
        }
    }

    private static void fixCurOptionAtScopeLeft(int[] nums, int scopeLeftSide, int i) {
        if (scopeLeftSide == i) {
            return;
        }
        nums[scopeLeftSide] ^= nums[i];
        nums[i] ^= nums[scopeLeftSide];
        nums[scopeLeftSide] ^= nums[i];
    }
}
