//  Copyright 2021 The KeepTry Open Source Project
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

package string.palindrome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 *    1.  <strong>sort</strong>.
 *
 *  index     0  1  2  3  4  5  6  7  8  9 10 11 12
 *           "3  3  4  a  a  a  a  b  b  b  b  c  c"
 *
 *    2.  loop:
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
 *    3. permuteLeftMirrorRight each number to get a permutation of left
 *       "3  a  a  b  b  c "
 *
 *      check duplicated:
 *           now the arr left is sorted. <strong>BUT</strong> if use swap way to permute current number
 *           of a permutation, <strong>MUST</strong> need a set to check
 *           if current choice is used, see {@link probability.permutation.Leetcode47PermutationsII2}
 *
 *      because this is permuteLeftMirrorRight the left half, so at least assume
 *      <strong>the arr length is 2</strong>.
 *
 *    4. once got a permutation of left, mirror -> right side.
 *
 *    5. & need in ()
 *
 *    ======================================================================================
 *
 *      // 16:32 ~57~17:02
 */
public class Leetcode267PalindromePermutationII {
    private void nextChoice(char[] arr, int i, int j) {
        if (i != j) {
            arr[i] ^= arr[j];
            arr[j] ^= arr[i];
            arr[i] ^= arr[j];
        }
    }

    private void mirror(char[] arr) {
        int i = 0, j = arr.length - 1;
        while (i < j) {
            arr[j] = arr[i];
            i++;
            j--;
        }
    }

    private void permute(int cur, char[] arr, List re) {
        if (cur == arr.length / 2 - 1) {
            mirror(arr);
            re.add(new String(arr));
            return;
        }

        Set usedChoice = new HashSet(arr.length / 2 - 1 - cur + 1);
        for (int i = cur; i <= arr.length / 2 - 1; i++) {
            char ic = arr[i];
            if (!usedChoice.contains(ic)) {
                usedChoice.add(ic);

                nextChoice(arr, cur, i);
                permute(cur + 1, arr, re);
                nextChoice(arr, cur, i);
            }
        }
    }

    public List<String> generatePalindromes(String str) {
        //
        List re = new ArrayList();
        if (str == null || str.length() == 0) {
            return re;
        }
        if (str.length() == 1) {
            re.add(str);
            return re;
        }

        //  "ababacabbc334 "
        //   "334aaaabbbbcc"
        char[] arr = str.toCharArray();
        int size = 0;
        Character middle = null;
        boolean oddArrMiddleFound = false;

        Arrays.sort(arr);
        for (int i = 0; i <= arr.length - 1; i++) { // need sort
            char cur = arr[i];
            if (i < arr.length - 1 && cur == arr[i + 1]) {
                arr[size++] = cur;
                i++;
                continue;
            }
            if ((arr.length & 1) == 1 && !oddArrMiddleFound) {
                middle = cur;
                oddArrMiddleFound = true;
                continue;
            }
            return re;
        }
        if ((arr.length & 1) == 1) {
            arr[arr.length / 2] = middle;
        }

        permute(0, arr, re);
        return re;
    }
}
