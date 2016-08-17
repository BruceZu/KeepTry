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
 *
 *              c a c a 9 d 9 e 9 9
 *   if allocate same char together
 *              c c a a d e 9 9 9 9
 *
 *   if   index = 0 select c, select it only once
 *   then index = 1 can still select c. still only once
 *
 *   ' i=cur || i>cur && ms[ i]!=ms[cur] &&  ms[i]!=ms[i-1]; ' can <strong>NOT</strong> work
 *   Because even sorted or allocate same char together as above.
 *   swap will get next number's choice scope not in order.
 *   the only way is use a set to make sure duplicated choices is used only once.
 */
public class Leetcode47PermutationsII2 {
    private int[] ms;
    private List<List<Integer>> r;

    private void swap(int i, int j) {
        if (i != j && ms[i] != ms[j]) {
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
        Set usedChoice = new HashSet(ei - si + 1);
        for (int ci = si; ci <= ei; ci++) {
            int choice = ms[ci];
            if (!usedChoice.contains(choice)) {
                usedChoice.add(choice); // note

                swap(si, ci);
                pNextNum(si + 1, ei);
                swap(si, ci);
            }
        }
    }

    public List<List<Integer>> permuteUnique(int[] in) {
        if (in == null) {
            return null;
        }
        ms = in;
        r = new ArrayList();
        pNextNum(0, ms.length - 1);

        return r;
    }
}
