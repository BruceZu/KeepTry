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

package dp;

/**
 * <pre>
 * 115. Distinct Subsequences
 * Difficulty: Hard
 * Given a string S and a string T, count the number of distinct subsequences of T in S.
 *
 * A subsequence of a string is a new string which is formed from the original
 * string by deleting some (can be none) of the characters without disturbing
 * the relative positions of the remaining characters.
 * (ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).
 *
 * Here is an example:
 *                      S = "rabbbit", T = "rabbit"
 *
 *              Return 3.
 *
 * Hide Tags
 *      Dynamic Programming
 *      String
 *
 *   ===================================================
 *   DFS is easy but VERY slow
 *   DP is fast {@link Leetcode115DistinctSubsequences2}
 *
 *  @see <a href= "https://leetcode.com/problems/distinct-subsequences/">leetcode</a>
 */
public class Leetcode115DistinctSubsequences {
    private static void backtracking(char[] t, int tofind,
                                     char[] s, int from,
                                     int[] r) {

        if (tofind == t.length) {
            r[0]++;
            return;
        }
        for (int i = from; i < s.length; i++) {
            if (s[i] == t[tofind]) {
                backtracking(t, tofind + 1, s, i + 1, r);
            }
        }
    }

    public static int numDistinct(String s, String t) {
        int[] r = new int[1];
        backtracking(t.toCharArray(), 0, s.toCharArray(), 0, r);
        return r[0];
    }
}
