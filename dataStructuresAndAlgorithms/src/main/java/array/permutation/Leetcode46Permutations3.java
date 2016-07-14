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

public class Leetcode46Permutations3 {
    private List<List<Integer>> r;
    private int[] ms;
    // ---------------

    /**
     * <pre>
     * Note:
     * 2 swaps:
     * fist swap: choices of current number depends on pre
     * without the last swap:
     *      [[1,2,3],[1,3,2],
     *      [3,1,2],[3,2,1],   3 get back 1 then 2 push 1 to the last
     *      [1,2,3],[1,3,2]]   now 1 is duplicated
     *
     * for array with duplicated element. this approach can not work.
     * e.g.: see {@link Leetcode47PermutationsII2#permuteUnique(int[])}
     *   1 1 2 2
     *     1 2 2
     *     2 1 2
     *       1 2
     *       2 1 ->   1 2 2 1
     *     2 2 1 ->   1 2 2 1
     *
     * for the case [1, 2, 3]
     * Result is
     *      [1, 2, 3]
     *      [1, 3, 2]
     *      [2, 1, 3]
     *      [2, 3, 1]
     *      [3, 2, 1]
     *      [3, 1, 2]
     *
     *
     *  Note:
     *  <1>
     *   before swap need check i!=j;
     *   Here the nums is distinct numbers:
     *   i == j means
     *   ms[i]^=ms[i]  // ms[i] ==0
     *   ms[i]^=ms[i]  // ms[i] ==0
     *   ms[i]^=ms[i]  // ms[i] ==0
     *
     *   So if the nums is not distinct numbers.
     *   Do not use ^= operation.
     *
     *   <2> if use while loop make sure the variable not affect the actions in loop;
     *
     *     it is ok:
     *      int choice = si;
     *      while (choice <=ei){  // wrong while (choice++ <=ei){
     *          swap(si, choice);
     *          pNextNum(si + 1, ei);
     *          swap(si, choice);
     *          choice ++;
     *      }
     * }
     */
    private void swap(int i, int j) {
        if (i != j) {
            ms[i] ^= ms[j];
            ms[j] ^= ms[i];
            ms[i] ^= ms[j];
        }
    }

    private void pNextNum(int si, int ei) {
        if (si == ei) {
            List p = new ArrayList(ms.length);
            for (int i = 0; i < ms.length; i++) {
                p.add(ms[i]);
            }
            r.add(p);
            return;
        }

        for (int curChoice = si; curChoice <= ei; curChoice++) {
            swap(si, curChoice);
            pNextNum(si + 1, ei);
            swap(si, curChoice);
        }
    }

    public List<List<Integer>> permute(int[] in) {
        if (in == null) {
            return null;
        }
        ms = in;
        r = new ArrayList();
        pNextNum(0, ms.length - 1);

        return r;
    }
}
