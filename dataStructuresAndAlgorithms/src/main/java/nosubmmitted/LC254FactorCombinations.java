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

package nosubmmitted;

import java.util.ArrayList;
import java.util.List;

/**
 * 254. Factor Combinations <pre>
 * https://leetcode.com/problems/factor-combinations/
 * <p/>
 * Difficulty: Medium
 * Numbers can be regarded as product of its factors. For example,
 * <p/>
 * 8 = 2 x 2 x 2;
 * = 2 x 4.
 * Write a function that takes an integer n and return all possible combinations of its factors.
 * <p/>
 * Note:
 * Each combination's factors must be sorted ascending, for example: The factors of 2 and 6 is [2, 6], not [6, 2].
 * You may assume that n is always positive.
 * Factors should be greater than 1 and less than n.
 * Examples:
 * input: 1
 * output:
 * []
 * input: 37
 * output:
 * []
 * input: 12
 * output:
 * [
 * [2, 6],
 * [2, 2, 3],
 * [3, 4]
 * ]
 * input: 32
 * output:
 * [
 * [2, 16],
 * [2, 2, 8],
 * [2, 2, 2, 4],
 * [2, 2, 2, 2, 2],
 * [2, 4, 4],
 * [4, 8]
 * ]
 * Hide Company Tags LinkedIn Uber
 * Hide Tags Backtracking
 * Hide Similar Problems (M) Combination Sum
 * </pre>
 */
public class LC254FactorCombinations {
    // the fast one should beat 98%

    /**
     * beat 70.85% <pre>
     * <p/>
     * The upper bound for the factors of n is (int)sqrt(n), and when you
     * find one factor of n, then put the factor and its corresponding factor
     * to a temp list, and add the temp list to the result list. Then we remove
     * the larger factor from the temp list, and recursively do the larger factor
     * from the smaller factor to upper bound for the same procedure.
     * <p/>
     * For example, n = 16. Let the variable i be from 2 to 4, when i = 2,
     * then i is one factor of 16, and its corresponding factor is 8,
     * so we add 2 and 8 to a temp list, then add the temp list to the result list.
     * And remove 8 from the temp list, and recursively do 8 from 2 to 2 for the same procedure.
     * <p/>
     * The result should be: [2, 8] [2, 2, 4] [2, 2, 2, 2] [4, 4]
     */
    public class Solution {
        public List<List<Integer>> getFactors(int n) {
            List<List<Integer>> res = new ArrayList<>();
            backTrack(res, new ArrayList<Integer>(), 2, n);
            return res;
        }

        public void backTrack(List<List<Integer>> res, List<Integer> cur, int start, int n) {
            int upper = (int) Math.sqrt(n);
            for (int i = start; i <= upper; i++) {
                int factor = -1;
                if (n % i == 0) {
                    factor = n / i;
                }
                if (factor != -1 && factor >= i) {
                    cur.add(i);
                    cur.add(factor);
                    res.add(new ArrayList<Integer>(cur));
                    cur.remove(cur.size() - 1);
                    backTrack(res, cur, i, factor);
                    cur.remove(cur.size() - 1);
                }
            }
        }

        /**
         * same speed as above
         */
        public List<List<Integer>> getFactors2(int n) {
            List<List<Integer>> res = new ArrayList<List<Integer>>();
            addToRes(res, new ArrayList<Integer>(), n);
            return res;
        }

        public void addToRes(List<List<Integer>> res, List<Integer> list, int target) {
            if (target == 1) {
                return;
            }
            if (list.size() > 0) {
                list.add(target);
                res.add(new ArrayList(list));
                list.remove(list.size() - 1);
            }
            //avoid duplicate cases
            int i = 0;
            i = list.size() == 0 ? 2 : list.get(list.size() - 1);
            for (; i <= Math.sqrt(target); i++) {
                if (target % i == 0) {
                    list.add(i);
                    addToRes(res, list, target / i);
                    list.remove(list.size() - 1);
                }
            }
        }


        /**
         * same speed as above
         */
        public List<List<Integer>> getFactors3(int n) {
            List<List<Integer>> ans = new ArrayList<>();
            backtrack3(n, ans, new ArrayList<Integer>());
            return ans;
        }

        public void backtrack3(int n, List<List<Integer>> ans, List<Integer> branch) {

            // A little bit different from traditional backtracking, as long as branch is not empty, we accept the combination.
            if (branch.size() > 0) {
                branch.add(n);
                ans.add(new ArrayList<>(branch));
                branch.remove(branch.size() - 1);
                // We want to continue to break down n, so we can not return here.
            }
            // Start from 2, till the square root of N, skip other bigger numbers to avoid duplication.
            for (int i = 2; i <= Math.sqrt(n); i++) {
                // We want to skip smaller factors to avoid duplication.
                // Eg. if n = 32, [4, 8] is valid, but we want to skip [4, 2, 4] and [4, 2, 2]
                if (n % i == 0 && (branch.isEmpty() || branch.get(branch.size() - 1) <= i)) {
                    branch.add(i);
                    backtrack3(n / i, ans, branch);
                    branch.remove(branch.size() - 1); // Backtrack the last factor
                }
            }
        }
    }
}