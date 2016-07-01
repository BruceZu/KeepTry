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
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * Difficulty: Medium
 * Given a set of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.
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
 *
 * Tags Array Backtracking
 *
 *    loop     solution  target
 *                        7
 *    2 3 6 7  | 2       |5
 *    2 3 6 7  | 2 2     |3
 *    2 3 6 7  | 2 2 2   |1
 *    2 3 6 7  | 2 2 2 2 |-1 back
 *
 *                        7
 *    2 3 6 7  | 2       |5
 *    2 3 6 7  | 2 2     |3
 *    2 3 6 7  | 2 2 3   |0  ok back
 *    2 3 6 7  |
 *
 *                        7
 *    2 3 6 7  | 2       |5
 *    2 3 6 7  | 2 3     |2
 *    2 3 6 7  | 2 3 3   |-1 back
 *    2 3 6 7  |
 *
 *                        7
 *    2 3 6 7  | 2       |5
 *    2 3 6 7  | 2 6     |-1 back
 *    2 3 6 7  |
 *    2 3 6 7  |
 *
 *                        7
 *    2 3 6 7  | 3       |4
 *    2 3 6 7  | 3 3     |1
 *    2 3 6 7  | 3 3 3   |-2 back
 *    2 3 6 7  |
 *
 *                        7
 *    2 3 6 7  | 3       |4
 *    2 3 6 7  | 3 6     |-2 back
 *    2 3 6 7  |
 *    2 3 6 7  |
 *                        7
 *    2 3 6 7  | 6       |1
 *    2 3 6 7  | 6       |-5 back
 *    2 3 6 7  |
 *    2 3 6 7  |
 *                        7
 *    2 3 6 7  | 7       |0 0k back
 *    2 3 6 7  |
 *    2 3 6 7  |
 *    2 3 6 7  |
 *
 *    over
 *
 * @see <a href="https://leetcode.com/problems/combination-sum/">link</a></a>
 */
public class Leetcode39CombinationSum {
    private void select(int[] c /* candidates*/, int remain, int target, List<List<Integer>> all, List<Integer> s/*solution*/) {
        for (int i = remain; i < c.length; i++) {
            int v = c[i];
            if (v > target) {
                return;
            }
            s.add(v);

            if (target == v) {
                all.add(new ArrayList(s));
                s.remove(s.size() - 1);
                return;
            }

            select(c, i, target - v, all, s);
            s.remove(s.size() - 1);
        }
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);

        List<List<Integer>> all = new ArrayList<>();
        select(candidates, 0, target, all, new ArrayList<Integer>());
        return all;
    }
}
