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

package probability.permutation;

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
    private List<List<Integer>> r;
    private List p;
    private int[] ms;
    private boolean[] selected;

    private void permute3() {
        if (p.size() == ms.length) {
            r.add(new ArrayList(p));
            return;
        }

        for (int i = 0; i < ms.length; i++) {
            if (selected[i] != true) {
                p.add(ms[i]);//  -->
                selected[i] = true;//  -->

                permute3();

                p.remove(p.size() - 1); // <--
                selected[i] = false; // <--
            }
        }
    }


    /**
     * back-tracing
     * Big O: runtime O(N!), space O(N!)
     */
    public List<List<Integer>> permute3(int[] in) {
        if (in == null) {
            return null;
        }
        ms = in;
        r = new ArrayList();
        p = new ArrayList(ms.length);
        selected = new boolean[ms.length];

        permute3();
        return r;
    }
}
