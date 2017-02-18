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

package backtracing;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Difficulty: Medium
 * Given a set of candidate numbers (C) and a target number (T),
 * find all unique combinations in C where the candidate numbers sums to T.
 *
 * The same repeated number may be chosen from C unlimited number of times.
 *
 * Note:
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 * For example, given candidate set [2, 3, 6, 7] and target 7,
 * A solution set is:
 * [
 * [7],
 * [2, 2, 3]
 * ]
 * @see <a href="https://leetcode.com/problems/combination-sum/">link</a></a>
 */
public class Leetcode39CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // int put check
        List<List<Integer>> result = new ArrayList();
        selectOne(candidates, target, result, new <Integer>ArrayList(), 0, 0);
        return result;
    }

    private void selectOne(int[] candidates, int target, List<List<Integer>> result, List<Integer> cur, int sum, int from) {
        if (sum == target) {
            result.add(new ArrayList(cur));
            return;
        }
        if (sum > target) {
            return;
        }

        for (int i = from; i < candidates.length; i++) {
            cur.add(candidates[i]);
            selectOne(candidates, target, result, cur, sum + candidates[i], i);// from i
            cur.remove(cur.size() - 1);
        }
    }
}
