//  Copyright 2018 The KeepTry Open Source Project
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

package dp.longestcommonsubsequence;

import java.util.ArrayList;
import java.util.List;

/*
The Longest Common Subsequence (LCS) problem
 1> first: finding the length of the LCS
 LCS[i][j]: keep the longest common sequence length of
            string a (indexth 1~i) and
            string b (indexth 1~j)

 if 2 current char value are:
 same:  LCS[i, j] = 1 + LCS[i − 1, j − 1].
 else:  LCS[i, j] = max(LCS[i − 1, j], LCS[i, j − 1]).
       m a n b c q
       0 0 0 0 0 0
  x 0  0 0 0 0 0 0
  a 0  0 1 1 1 1 1
  y 0  0 1 1 1 1 1
  b 0  0 1 1 2 2 2
  c 0  0 1 1 2 3 3
  z 0  0 1 1 2 3 3

2> Find the sequence
   when the LCS number == T
   see the Leetcode727MinimumWindowSubsequence

 The Longest Common sub-string (LCA) problem
 difference:
  same:  LCS[i, j] = 1 + LCS[i − 1, j − 1].
  else:  LCS[i, j] = 0; // here
       m a n b c q
       0 0 0 0 0 0
  x 0  0 0 0 0 0 0
  a 0  0 0 1 0 0 0
  y 0  0 0 0 0 0 0
  b 0  0 0 0 1 0 0
  c 0  0 0 0 0 2 0
  z 0  0 0 0 0 0 0
 tracking the longLen and longLenEndIdexOf one string
 with them to get the Longest Common sub-string

*/
public class LongestCommonSubsequence {
  // runtime O(mn)
  public static int longestCommonSubsequenceLengthBottomUp(String A, String B) {
    // corner cases checking
    if (A == null || A.isEmpty() || B == null || B.isEmpty()) return 0;

    char[] a = A.toCharArray(), b = B.toCharArray();
    int M = A.length(), N = B.length();
    int dp[][] = new int[N + 1][M + 1];
    // initial zero for  0th /row,  0th/column;
    for (int i = 1; i <= M; i++) { // 1 based index in dp
      for (int j = 1; j <= N; j++) { // 1 based index in dp
        if (a[i - 1] == b[j - 1]) dp[j][i] = 1 + dp[j - 1][i - 1];
        else dp[j][i] = Math.max(dp[j - 1][i], dp[j][i - 1]);
      }
    }

    // debugPrint(cn, rm, dp);
    int r = N, c = M, len = dp[N][M];
    List<String> all = new ArrayList<>();
    recursionFindAll(all, new char[len], dp, r, c, len, a, b);
    return dp[N][M];
  }

  // from right bottom corner to backtrack all LCS strings along the dp direction
  public static void recursionFindAll(
      List<String> all, char[] one, int[][] dp, int i, int j, int len, char[] a, char[] b) {
    if (len == 0) {
      all.add(new String(one));
      System.out.println(one);
      return;
    }

    if (dp[i][j] == dp[i - 1][j - 1] + 1 && a[j - 1] == b[i - 1]) {
      one[len - 1] = a[j - 1];
      recursionFindAll(all, one, dp, i - 1, j - 1, len - 1, a, b);
    } else {
      if (dp[i][j] == dp[i - 1][j]) {
        recursionFindAll(all, one, dp, i - 1, j, len, a, b);
      }
      if (dp[i][j] == dp[i][j - 1]) {
        recursionFindAll(all, one, dp, i, j - 1, len, a, b);
      }
    }
  }

  private static void debugPrint(int cn, int rm, int[][] dp) {
    for (int r = 0; r <= rm; r++) {
      for (int c = 0; c <= cn; c++) {
        System.out.print(" " + dp[r][c] + " ");
      }
      System.out.println();
    }
  }

  // runtime O(mn) find out one, may have more result.
  public static int twoStrLongestCommonSubsequenceLengthTopDown(String A, String B) {
    // corner cases checking
    if (A == null || A.isEmpty() || B == null || B.isEmpty()) return 0;
    char[] s = A.toCharArray(), s2 = B.toCharArray();
    int M = A.length(), N = B.length();
    Integer[][] cache = new Integer[N + 1][M + 1];
    int ans = valueOf(M, N, s, s2, cache);
    debugPrint(M, N, cache);
    return ans;
  }

  // recursion Top-Down
  private static int valueOf(int ith, int jth, char[] a, char[] b, Integer[][] cache) {
    if (cache[jth][ith] != null) return cache[jth][ith]; // add this. use memorized result
    int ans;
    if (ith == 0 || jth == 0) ans = 0;
    else if (a[ith - 1] == b[jth - 1]) ans = 1 + valueOf(ith - 1, jth - 1, a, b, cache);
    else ans = Math.max(valueOf(ith - 1, jth, a, b, cache), valueOf(ith, jth - 1, a, b, cache));

    cache[jth][ith] = ans; // memorizing
    return ans;
  }

  private static void debugPrint(int cn, int rm, Integer[][] dp) {
    for (int r = 0; r <= rm; r++) {
      for (int c = 0; c <= cn; c++) {
        if (dp[r][c] == null) {
          System.out.print(" / ");
        } else {
          System.out.print(" " + dp[r][c] + " ");
        }
      }
      System.out.println();
    }
  }
}
