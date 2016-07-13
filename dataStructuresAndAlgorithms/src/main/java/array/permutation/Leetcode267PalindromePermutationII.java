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
import java.util.List;

/**
 * <pre>
 * 267. Palindrome Permutation II
 * Difficulty: Medium
 * Given a string s, return all the palindromic permutations (without duplicates) of it.
 * Return an empty list if no palindromic permutation could be form.
 *
 * For example:
 *
 * Given s = "aabb", return ["abba", "baab"].
 *
 * Given s = "abc", return [].
 *
 * Hint:
 *
 * If a palindromic permutation exists, we just need to generate the first half of the string.
 * To generate all distinct permutations of a (half of) string, use a similar approach
 * from: Permutations II or Next Permutation.
 *
 * Tags Backtracking
 * Similar Problems
 *       (M) Next Permutation
 *       (M) Permutations II
 *       (E) Palindrome Permutation
 *
 * ========================================================================================
 *   "a b a b a c a b b c 3 3 4 "
 *  the left side:
 *    1- sort.
 *
 *  index     0  1  2  3  4  5  6  7  8  9 10 11 12
 *           "3  3  4  a  a  a  a  b  b  b  b  c  c"
 *
 *    2- loop:
 *       if find pair, keep one. (need sort in advance)
 *       if length is odd and find single char, allocate it to middle.
 *       (only when the string length is odd and there is only one single char is valid)
 *
 *
 *            0  1  2  3  4  5  6  7  8  9 10 11 12
 *           "3  a  a  b  b  c  4  b  b  b  b  c  c"
 *                              |
 *                            13/2
 *       the middle character:
 *           kept till the loop is over, then allocate it to the middle index
 *           avoiding <strong>break the pairs in the loop</strong>.
 *
 *    3 permuteLeftMirrorRight each number to get a permutation of left
 *       "3  a  a  b  b  c "
 *
 *      check duplicated:
 *           now the arr left is sorted. so we do not need a set to check
 *           if current choice is used. just compare to the pre one.
 *      because this is permuteLeftMirrorRight the left half, so at least assume
 *      <strong>the arr length is 2</strong>.
 *
 *    4  once got a permutation of left, mirror -> right side.
 */
public class Leetcode267PalindromePermutationII {
    List<String> results;
    char[] arr;

    private void mirror() {
        int i = 0;
        int j = arr.length - 1 - i;
        while (i < j) {
            arr[j] = arr[i];
            i++;
            j = arr.length - 1 - i;
        }
    }

    private void nextChoice(int i, int j) {
        if (i != j) {
            arr[i] ^= arr[j];
            arr[j] ^= arr[i];
            arr[i] ^= arr[j];
        }
    }

    private void permuteLeftMirrorRight(int cur) {
        if (cur == arr.length / 2 - 1) { //means need arr.length >= 2
            mirror();
            results.add(new String(arr));
            return;
        }

        char prev = arr[cur];
        for (int i = cur; i <= arr.length / 2 - 1; i++) {
            if (i == cur || i > cur && arr[i] != prev) {
                prev = arr[i];

                nextChoice(cur, i);
                permuteLeftMirrorRight(cur + 1);
                nextChoice(cur, i);
            }
        }
    }

    public List<String> generatePalindromes(String s) {
        results = new ArrayList<String>();

        if (s == null || s.length() == 0) {
            return results;
        }
        arr = s.toCharArray();
        if (s.length() == 1) {
            results.add(s);
            return results;
        }

        // -------
        Arrays.sort(arr);

        Character oddArrSingleMiddleChar = null;
        boolean found = false;

        for (int i = 0, halfSize = 0; i < arr.length; i++) {
            char cur = arr[i];
            if (i < arr.length - 1 && cur == arr[i + 1]) {
                arr[halfSize] = cur;
                halfSize++;
                i++;
                continue;
            }
            if ((arr.length & 1) == 1 && !found) {
                oddArrSingleMiddleChar = cur;
                found = true;
            } else {
                return results; // empty
            }
        }
        if ((arr.length & 1) == 1) {
            arr[arr.length / 2] = oddArrSingleMiddleChar;
        }

        permuteLeftMirrorRight(0);
        return results;
    }
}
