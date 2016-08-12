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

public class Leetcode46Permutations2 {
    private List<List<Integer>> r;
    private int[] ms;

    /**
     * <pre>
     *      1 2 3
     * rotate:
     *     1 2 3
     *           1 2 3
     *                  start = end
     *           rotate
     *
     *           1 3 2
     *                  start = end
     *           rotate
     *           1 2 3
     *     rotate
     *     2 3 1
     *           2 3 1
     *                  start = end
     *           rotate
     *           2 1 3
     *                 start = end
     *           rotate
     *           2 3 1
     *     rotate
     *     3 1 2
     *           3 1 2
     *                  start = end
     *           rotate
     *           3 2 1
     *                  start = end
     *           rotate
     *           3 1 2
     *    rotate
     *    1 2 3
     *    the result is
     *      [1, 2, 3]
     *      [1, 3, 2]
     *      [2, 3, 1]
     *      [2, 1, 3]
     *      [3, 1, 2]
     *      [3, 2, 1]
     *
     *  look down to see the choices of fist , second, .... number of permutations. like a Permutation Lock.
     *
     *  Big O: run time O(N!), space  O(N!)
     */
    private void rotateNextChoice(int si, int ei) {
        int siv = ms[si];
        for (int i = si; i < ei; i++) {
            ms[i] = ms[i + 1];
        }
        ms[ei] = siv;
    }

    /**
     * <pre>
     * Note:
     *   1 Arrays.asList(ms) can not work; it will wrap a []
     *   2 choice need -- each loop
     *   3 the loop only about choices, not affect next recursive which always is 'si +1'
     *
     * @param si start index
     * @param ei end index
     */
    private void pNextNumber(int si, int ei) {
        if (si == ei) {
            List<Integer> p = new ArrayList(ms.length);
            for (int i = 0; i < ms.length; i++) {
                p.add(ms[i]);
            }
            r.add(p);
            return;
        }
        int choices = ei - si + 1;
        while (choices-- >= 1) {
            pNextNumber(si + 1, ei);
            rotateNextChoice(si, ei);
        }
    }

    /**
     * <pre>
     * 10 minutes
     * Note:
     *   input check null
     *   it is length -1, not length.
     */
    public List<List<Integer>> permute2(int[] in) {
        if (in == null) {
            return null;
        }
        ms = in;
        r = new ArrayList();
        pNextNumber(0, ms.length - 1);
        return r;
    }
}
