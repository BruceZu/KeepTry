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

/**
 * <pre>
 *     60. Permutation Sequence  QuestionEditorial Solution  My Submissions
 *
 * Difficulty: Medium
 * The set [1,2,3,…,n] contains a total of n! unique permutations.
 *
 * By listing and labeling all of the permutations in order,
 *
 *       We get the following sequence (ie, for n = 3):
 *
 *       "123"
 *       "132"
 *       "213"
 *       "231"
 *       "312"
 *       "321"
 *
 * Given n and k, return the kth permutation sequence.
 *
 * Note: Given n will be between 1 and 9 inclusive.
 *
 * Tags:
 *          Backtracking Math
 * Similar Problems
 * (M) Next Permutation
 * (M) Permutations
 *
 *
 * ===================================================================================
 *   This works but too slow.
 *       "123"
 *       "132"
 *       "213"  // rotate approach can not match this requirement
 *       "231"
 *       "312"  // swap  approach can not match this requirement
 *       "321"
 * @see <a href = "https://leetcode.com/problems/permutation-sequence/">leetcode</a>
 */
public class Leetcode60PermutationSequence {

    private int[] arr;
    private boolean[] used;
    private int k;
    private int sum = 0;
    private int[] re;
    private int size;

    private String per() {
        if (size == re.length) {
            sum++;
            if (sum == k) {
                StringBuilder r = new StringBuilder();
                for (int i = 0; i < re.length; i++) {
                    r.append(re[i]);
                }
                return r.toString();
            }
            return "";
        } else {
            String r = "";
            for (int ci = 1; ci < arr.length; ci++) {
                if (used[ci]) {
                    continue;
                }
                used[ci] = true;
                re[size] = arr[ci];
                size++;

                r = per();
                if (sum == k) {
                    return r;
                }

                used[ci] = false;
                size--;
            }
            return r;
        }
    }

    public String getPermutation(int n, int k) {
        if (n <= 1) {
            return "" + n + "";
        }
        arr = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            arr[i] = i;
        }
        used = new boolean[n + 1];
        re = new int[n];
        size = 0;
        this.k = k;
        return per();
    }
}
