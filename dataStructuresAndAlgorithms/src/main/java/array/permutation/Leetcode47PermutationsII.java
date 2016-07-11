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
import java.util.Arrays;
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
 *
 *  Tags  Backtracking
 *  Similar Problems
 * (M) Next Permutation
 * (M) Permutations
 * (M) Palindrome Permutation II
 */
public class Leetcode47PermutationsII {
    private int[] ms;
    private List<List<Integer>> r;
    private List p;
    private boolean[] selected;

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
        Set usedChoice = new HashSet(ei - si + 1); // do not need sort firstly
        for (int ci = si; ci <= ei; ci++) {
            int choice = ms[ci];
            if (usedChoice.contains(choice)) {
                continue;
            }
            usedChoice.add(choice);
            swap(si, ci);
            pNextNum(si + 1, ei);
            swap(si, ci);
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

    // ---------------------------------------------------------

    /**
     * Note:  use 'Set used...', do not need sort the input firstly.
     */
    private void permuteUnique() {
        if (p.size() == ms.length) {
            r.add(new ArrayList(p));
            return;
        }

        //Set used = new HashSet();
        for (int i = 0; i < ms.length; i++) {
            if (!selected[i] && !(i - 1 >= 0 && ms[i - 1] == ms[i] && !selected[i - 1])) { // need sort input firstly
                //if (selected[i] != true && !used.contains(ms[i])) {
                // used.add(ms[i]);
                p.add(ms[i]);//  -->
                selected[i] = true;//  -->

                permuteUnique();

                p.remove(p.size() - 1); // <--
                selected[i] = false; // <--
            }
        }
    }

    public List<List<Integer>> permuteUnique2(int[] in) {
        if (in == null) {
            return null;
        }
        Arrays.sort(in);
        ms = in;
        r = new ArrayList();
        p = new ArrayList(ms.length);
        selected = new boolean[ms.length];

        permuteUnique();
        return r;
    }

    // ---------------------------------------------------------
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

    public List<List<Integer>> permuteUnique3(int[] in) {
        if (in == null) {
            return null;
        }
        ms = in;
        r = new ArrayList();
        pNextNumber(0, ms.length - 1);
        return r;
    }

    // ---------------------------------------------------------
    //fast one:
}
