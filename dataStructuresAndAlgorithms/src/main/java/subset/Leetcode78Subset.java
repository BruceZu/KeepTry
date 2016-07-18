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

package subset;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 78. Subsets
 * Difficulty: Medium
 * Given a set of distinct integers, nums, return all possible subsets.
 *
 * Note:
 * The solution set must not contain duplicate subsets.
 *
 * Followup:
 *   + assume 1:  Elements in a subset must be in non-descending order.
 *   + assume 2:  calculate subsets with a given size.  0<= size <= array.length.
 *
 * For example,
 * If nums = [1,2,3], a solution is:
 *
 * [
 * [3],
 * [1],
 * [2],
 * [1,2,3],
 * [1,3],
 * [2,3],
 * [1,2],
 * []
 * ]
 * Company Tags Amazon Uber Facebook
 * Tags Array Backtracking Bit Manipulation
 * Similar Problems (M) Generalized Abbreviation
 * ==================================================================================================
 * 1> Back-tracing: running time O(n^2)
 *    back-tracing, need restore to original status. next number's choices depends the above ones
 *   the empty set.
 *   Left bound of loop:
 *                  never look back.
 *                  e.g.: if {1,3} is selected,
 *                  now the third one will be selected from 4 and 5,
 *                  do not care 2, because {1,3,2} is same as {1,2,3} which has been done.
 *   Followup:
 *    1:  -> + assume 1:  Arrays.sort(nums);
 *    2:  -> + assume 2:
 *             check the result length before add it to result:
 *              if(selected.size() == 3) {
 *                     // add
 *              }
 *             performance:
 *               right bound check in loop:
 *               selected numbers, i, left numbers
 *               i< arr.length -(size - selected)
 *
 *               E.g.: sorted array {1,2,3,4,5}. size =3.
 *               Right bound of loop:
 *                  First number  has 3 choices.
 *                      [1, 2, 3]
 *                      [1, 2, 4]
 *                      [1, 2, 5]
 *                      [1, 3, 4]
 *                      [1, 3, 5]
 *                      [1, 4, 5]
 *
 *                      [2, 3, 4]
 *                      [2, 3, 5]
 *                      [2, 4, 5]
 *
 *                      [3, 4, 5]
 *
 * 2> Bit Manipulation:
 *  nums is distinguish and nums.length<=64.
 *
 * 3> from empty set,
 *    in a loop:
 *       add new subsets by inserting current number into existing subsets.
 *
 * @see bitmanipulation.Leetcode78SubsetBitManipulation
 * @see Leetcode78Subset2
 */
public class Leetcode78Subset {
    /**
     * @see <a href ="https://www.mathsisfun.com/combinatorics/combinations-permutations.html">Combinations and Permutations</a>
     */
    private static void subsets(int[] arr, List<List<Integer>> r, ArrayList<Integer> selected, int from) {
        while (from < arr.length) {
            selected.add(arr[from]);

            r.add((ArrayList<Integer>) selected.clone());
            subsets(arr, r, selected, from + 1);

            selected.remove(selected.size() - 1);
            from++;
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<Integer>());

        subsets(nums, result, new ArrayList<Integer>(), 0);
        return result;
    }
}
