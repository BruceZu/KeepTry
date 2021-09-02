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

package two_pointer.slidingwindow;
/*
727. Minimum Window Subsequence

Given strings s1 and s2, return the minimum contiguous substring part of s1,
so that s2 is a subsequence of the part.

If there is no such window in s1 that covers all characters in s2,
return the empty string "".
If there are multiple such minimum-length windows, return the one with the left-most starting index.

Input: s1 = "abcdebdde", s2 = "bde" Output: "bcde"
Explanation:
"bcde" is the answer because it occurs before "bdde" which has the same length.
"deb" is not a smaller window because the elements of s2 in the window must occur in order.

Example 2:

Input: s1 = "jmeqksfrsdcmsiwvaovztaqenprpvnbstl", s2 = "u"
Output: ""



Constraints:

    1 <= s1.length <= 2 * 104
    1 <= s2.length <= 100
    s1 and s2 consist of lowercase English letters.
*/

public class Leetcode727MinimumWindowSubsequence {
  /*
   Observe
     T=ab,  S=aaxbab
     T=abd, S=aaxxbxadbd
     T=ab   S=aaaaaaaaaab
  */

  /*---------------------------------------------------------------------------
   From each index as from index and search T, tracking the shortest one
   in the worst case like `T=ab   S=aaaaaaaaaab `
   it is O(n!) time
   O(1) space
  */
  public String minWindow___(String S, String T) {
    int len = Integer.MAX_VALUE, l = -1, r = -1;
    for (int from = 0; from < S.length(); ) {
      int i = from, j = 0, curs = -1;
      // search T
      while (i < S.length()) {
        if (S.charAt(i) == T.charAt(j)) {
          if (j == 0) curs = i;
          if (j == T.length() - 1) {
            if (i - curs + 1 < len) {
              len = i - curs + 1;
              l = curs;
              r = i;
            }
            break;
          }
          j++;
        }
        i++;
      }
      if (i == S.length()) break;
      from = curs + 1;
    }
    return l == -1 ? "" : S.substring(l, r + 1);
  }
  /*---------------------------------------------------------------------------
   improve above solution
     greedily search T not only from left to right=> end index
     also from right to left => start.
     to avoid  `T=ab   S=aaaaaaaaaab ` in above solution.
   O(M*N) time O(1) space
   Worst case: S=aaaaaaa, T=aa
   Best case: S=abcabcabc T=abc
  */
  public String minWindow__(String S, String T) {
    int len = Integer.MAX_VALUE, l = -1;
    for (int i = 0; i < S.length(); i++) {
      int j = 0;
      while (i < S.length()) { // search T
        if (S.charAt(i) == T.charAt(j)) {
          if (j == T.length() - 1) break;
          j++;
        }
        i++;
      }
      if (i == S.length()) break;
      int end = i;
      while (j >= 0) { // from right to left
        if (S.charAt(i) == T.charAt(j)) {
          if (j == 0) {
            if (end - i + 1 < len) {
              len = end - i + 1;
              l = i;
            }
            break;
          }
          j--;
        }
        i--;
      }
    }
    return l == -1 ? "" : S.substring(l, l + len);
  }

  public String minWindow_(String s, String t) {
    char[] S = s.toCharArray(), T = t.toCharArray();
    int i = 0, j = 0, N = S.length, M = T.length, start = -1, len = N;
    while (i < N) {
      if (S[i] == T[j]) {
        if (++j == M) {
          int end = i;
          //  right to left of T
          while (--j >= 0) {
            while (S[i--] != T[j])
              ;
          }
          ++i;
          ++j;
          // record the current smallest candidate
          if (end - i + 1 < len) {
            len = end - i + 1;
            start = i;
          }
        }
      }
      ++i;
    }
    return start == -1 ? "" : s.substring(start, start + len);
  }

  /*---------------------------------------------------------------------------
    Ideas from 'longest common subsequence'
     T=abd, S=aaxxbxadbd
         0 1 2 3 4 5 6 7 8  9  10
           a a x x b x a d  b  d
         1 2 3 4 5 6 7 8 9 10 11 (initial value used later, 0 has special meaning in 0 column)
       a 0 1 2         7
       b 0         2        7
       d 0               2     7

      dp[r][c] nearest start index of T in S
      T[r-1]==S[c-1]: dp[r][c]=dp[r-1][c-1]
                 else dp[r][c]=dp[r][c-1]
       dp[r][c]==0 means not in sequence of S containg T
       at last from the last row to get all len of sequence of S containg T
       and tracking the shortest one
    O(N^2) time, space it can be O(N) space
  */
  public String minWindow(String S, String T) {
    return null;
  }
}
