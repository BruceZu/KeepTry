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

public class Leetcode516LongestPalindromicSubsequence {
  /*
     516. Longest Palindromic Subsequence

     Given a string s, find the longest palindromic subsequence's length in s.
     A subsequence is a sequence that can be derived from another sequence by
     deleting some or no elements without changing the order of the remaining elements.


   Input: s = "bbbab"
   Output: 4, "bbbb".

   Input: s = "cbbd"
   Output: 2, "bb".

   Constraints:
       1 <= s.length <= 1000
       s consists only of lowercase English letters.
  */

  /*
  top down
  Sequence idea. use palindrome to defining the sequence
  dfs + cache
  */
  public int longestPalindromeSubseq2(String s) {
    int n = s.length();
    int[][] c = new int[n][n];
    return dfs(c, 0, n - 1, s);
  }

  private int dfs(int[][] c, int l, int r, String s) {
    if (c[l][r] != 0) return c[l][r]; // 0 or cached
    else if (s.charAt(l) == s.charAt(r)) {
      if (l >= r - 2) c[l][r] = r - l + 1;
      else c[l][r] = dfs(c, l + 1, r - 1, s) + 2;
    } else c[l][r] = Math.max(dfs(c, l, r - 1, s), dfs(c, l + 1, r, s));
    return c[l][r];
  }

  /*
   Bottom up
   `bbabc|b`
   O(N^2) time and space
  */
  public int longestPalindromeSubseq(String s) {
    int N = s.length();
    int[][] dp = new int[N][N];
    for (int r = 0; r < N; r++) {
      for (int l = r; l >= 0; l--) {
        if (s.charAt(l) == s.charAt(r)) {
          if (l >= r - 2) dp[l][r] = r - l + 1;
          else dp[l][r] = 2 + dp[l + 1][r - 1];
        } else {
          dp[l][r] = Math.max(dp[l + 1][r], dp[l][r - 1]);
          //              the dp[l + 1][r] require calculate backward in the inner loop l
        }
      }
    }
    return dp[0][N - 1];
  }
}
