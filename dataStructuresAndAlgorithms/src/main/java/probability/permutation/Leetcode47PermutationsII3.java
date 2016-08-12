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
 *              c a c a 9 d 9 e 9 9
 *   if allocate same char together
 *              c c a a d e 9 9 9 9
 *
 *   if   index = 0 select c, select it only once
 *   then index = 1 can still select c. still only once
 *
 *   if sorted or allocate same char together as above.
 *   just make sure current choice is not same as prev.
 *   e.g.
 */
/*
  <pre>
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
        int pre = ms[si]; --------------------------------------------------------//  NOTE
        while (choices  >= 1) {
            if (choices== ei - si + 1 || choices<ei - si + 1 &&  ms[si]!=pre) { --//  NOTE
                pre = ms[si]; ----------------------------------------------------//  NOTE
                pNextNumber(si + 1, ei);
            }
            rotateNextChoice(si, ei);
            choices--;
        }
    }

    public List<List<Integer>> permuteUnique(int[] in) {
        if (in == null) {
            return null;
        }
        Arrays.sort(in);    -----------------------------------------------------//  NOTE
        ms = in;
        r = new ArrayList();
        pNextNumber(0, ms.length - 1);
        return r;
    }*/

public class Leetcode47PermutationsII3 {
    private int[] ms;
    private List<List<Integer>> r;

    private void rotateNextChoice(int si, int ei) {
        int siv = ms[si];
        for (int i = si; i < ei; i++) {
            ms[i] = ms[i + 1];
        }
        ms[ei] = siv;
    }

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
        Set used = new HashSet(); // do not need sort input firstly
        while (choices-- >= 1) {
            if (!used.contains(ms[si])) {
                used.add(ms[si]);
                pNextNumber(si + 1, ei);
            }
            rotateNextChoice(si, ei);
        }
    }

    public List<List<Integer>> permuteUnique(int[] in) {
        if (in == null) {
            return null;
        }
        ms = in;
        r = new ArrayList();
        pNextNumber(0, ms.length - 1);
        return r;
    }
}
