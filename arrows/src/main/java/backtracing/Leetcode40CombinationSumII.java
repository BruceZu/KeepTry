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
 * <a href="https://leetcode.com/problems/combination-sum-ii/?tab=Description">LeetCode</a>
 */
public class Leetcode40CombinationSumII {
    // Avoid repeating use a same item by sort && jump duplicates and let next selection from i+1
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList();
        // int put check
        // if( candidates == null || candidates.length == 0) return result;
        Arrays.sort(candidates);
        selectOne(candidates, target, result, new <Integer>ArrayList(), 0, 0);
        return result;
    }

    /**
     * performance improvement:
     * using int[] to replace List<Integer> cur
     * in for loop: if(target-sum<candidates[i])  break;
     */
    private void selectOne(int[] candidates, int target, List<List<Integer>> result, List<Integer> cur, int sum, int from) {
        if (sum == target) {
            result.add(new ArrayList(cur));
            return;
        }
        if (sum > target) {
            return;
        }

        for (int i = from; i < candidates.length; i++) {
            if (i != from && candidates[i] == candidates[i - 1]) { // jump duplicates
                continue;
            }
            cur.add(candidates[i]);
            selectOne(candidates, target, result, cur, sum + candidates[i], i + 1);// from i+1
            cur.remove(cur.size() - 1);
        }
    }
}
