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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * Given a collection of numbers that might contain duplicates, return all possible unique permutations.
 *
 * For example,
 * [1,1,2] have the following unique permutations:
 * [
 *  [1,1,2],
 *  [1,2,1],
 *  [2,1,1]
 * ]
 * Subscribe to see which companies asked this question
 *  ====================================================================================================
 * Note:
 *   use 'Set used...', do not need sort the input firstly.
 *
 *              c a c a 9 d 9 e 9 9
 *   if allocate same char together
 *              c c a a d e 9 9 9 9
 *
 *   if   index = 0 select c, select it only once
 *   then index = 1 can still select c. still only once
 *
 *   if sorted or allocate same char together as above.
 *   just make sure current choice is not same as prev.
 */
/*private int preChoice(int cur) {
        int pre = cur - 1;
        while (pre >= 0 && selected[pre]) {
            pre--;
        }
        return pre;
    }

    private void permuteUnique() {
        if (p.size() == ms.length) {
            r.add(new ArrayList(p));
            return;
        }

        for (int i = 0; i < ms.length; i++) {
            int pre = preChoice(i);   //   ------- NOTE
            if (!selected[i] && (pre == -1 || pre != -1 && ms[pre] != ms[i])) {  //   ------- NOTE

                p.add(ms[i]);
                selected[i] = true;

                permuteUnique();

                p.remove(p.size() - 1);
                selected[i] = false;
            }
        }
    }

    public List<List<Integer>> permuteUnique(int[] in) {
        if (in == null) {
            return null;
        }
        Arrays.sort(in); //   ------- NOTE

        ms = in;
        r = new ArrayList();
        p = new ArrayList(ms.length);
        selected = new boolean[ms.length];

        permuteUnique();
        return r;
    }*/

public class Leetcode47PermutationsII {
    private int[] ms;
    private List<List<Integer>> r;
    private List p;
    private boolean[] selected;

    private void permuteUnique() {
        if (p.size() == ms.length) {
            r.add(new ArrayList(p));
            return;
        }

        Set used = new HashSet();
        for (int i = 0; i < ms.length; i++) {
            if (selected[i] != true && !used.contains(ms[i])) {
                used.add(ms[i]);

                p.add(ms[i]);           //  -->
                selected[i] = true;     //  -->

                permuteUnique();

                p.remove(p.size() - 1); // <--
                selected[i] = false;    // <--
            }
        }
    }

    public List<List<Integer>> permuteUnique(int[] in) {
        if (in == null) {
            return null;
        }

        ms = in;
        r = new ArrayList(); //
        p = new ArrayList(ms.length);
        selected = new boolean[ms.length];

        permuteUnique();
        return r;
    }
}
