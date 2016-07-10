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

package array.permutation;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Given a collection of distinct numbers, return all possible permutations.
 *
 * For example,
 * [1,2,3] have the following permutations:
 * [
 * [1,2,3],
 * [1,3,2],
 * [2,1,3],
 * [2,3,1],
 * [3,1,2],
 * [3,2,1]
 * ]
 *
 *  Tags Backtracking
 *  Similar Problems
 *  (M) Next Permutation
 *  (M) Permutations II
 *  (M) Permutation Sequence
 *  (M) Combinations
 *
 * Note:
 *   0  This is for * distinct * numbers
 *   1  current choice scope is depends on pre
 *   2  back-tracing need restore original status when step back:
 *         delete before return
 *         delete after call
 *   3 List methods:
 *     --  2 remove():
 *              remove(int index);
 *              remove(Object o);
 *     --  contains(): need check if current one is selected.
 *   4 Improve:
 *       a. use a boolean[] to check current choice is selected or not
 *       b. return directly once a permutation is finished.
 *       c. all parameters to be variables, easy to read.
 *
 * @see <a href = "https://leetcode.com/problems/permutations/">leetcode</a>
 */
public class Leetcode46Permutations {

    private List<List<Integer>> result;
    private List cp; //currentPermutation
    private boolean[] selected;
    private int[] nums;

    private void currentNumber() {
        if (cp.size() == nums.length) {
            result.add(new ArrayList(cp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (selected[i] == true) {
                continue;
            }
            cp.add(nums[i]);    //  -->
            selected[i] = true; //  -->

            currentNumber();

            cp.remove(cp.size() - 1); // <--
            selected[i] = false;      // <--
        }
    }

    /**
     * back-tracing
     */
    public List<List<Integer>> permute(int[] in) {
        if (in == null) {
            return null;
        }
        nums = in;
        result = new ArrayList();
        cp = new ArrayList(nums.length);
        selected = new boolean[nums.length];


        currentNumber();
        return result;
    }

    /**
     * <pre>
     *      1 2 3
     * rotate:
     *     1 2 3
     *           1 2 3
     *                  start = end
     *           1 3 2
     *                  start = end
     *     2 1 3
     *           2 1 3
     *                  start = end
     *           2 3 1
     *                  start = end
     *     3 1 2
     *           3 1 2
     *                  start = end
     *           3 2 1
     *                  start = end
     *
     *  look down to see the choices of fist , second, .... number of permutations.
     */
    public List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> ll = new ArrayList<List<Integer>>();
        nextNumber(nums, ll, 0, nums.length - 1);
        return ll;
    }

    public void nextNumber(int[] nums, List<List<Integer>> ll, int start, int end) {
        if (end == start) {// all numbers are decided
            List<Integer> l = new ArrayList<>();
            for (int n : nums) {
                l.add(n);
            }
            ll.add(l);
            return;
        } else {
            int choices = end - start + 1;
            for (int j = 1; j <= choices; j++) {
                nextNumber(nums, ll, start + 1, end);
                rotateToNextChoice(nums, start, end);
            }
        }
    }

    public void rotateToNextChoice(int[] num, int start, int end) {
        int temp = num[start];
        int i = start;
        while (i < end) {
            num[i] = num[i + 1];
            i++;
        }
        num[i] = temp;
    }
}
