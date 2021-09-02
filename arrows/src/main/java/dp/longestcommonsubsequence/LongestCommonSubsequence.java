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
  LCS[i, j] = max(LCS[i − 1, j], LCS[i, j − 1]).
  LCS[i, j] = 1 + LCS[i − 1, j − 1].

2> Find the sequence
   when the LCS number == T
   see the Leetcode727MinimumWindowSubsequence
*/
public class LongestCommonSubsequence {
  // runtime O(mn)
  public static int longestCommonSubsequenceLengthBottomUp(String S, String T) {
    // corner cases checking
    if (S == null || S.isEmpty() || T == null || T.isEmpty()) return 0;

    char[] s = S.toCharArray(), t = T.toCharArray();
    int M = S.length(), N = T.length();
    int dp[][] = new int[N + 1][M + 1];
    // benefit with initial zero index row and column; cons: when using the value of s and t. care
    // the index
    for (int c = 1; c <= M; c++) {
      for (int r = 1; r <= N; r++) {
        if (s[c - 1] == t[r - 1]) dp[r][c] = 1 + dp[r - 1][c - 1];
        else dp[r][c] = Math.max(dp[r - 1][c], dp[r][c - 1]);
      }
    }

    // debugPrint(cn, rm, dp);
    int r = N, c = M, left = dp[N][M];
    List<String> all = new ArrayList<>();
    recursionFindAll(all, new char[left], dp, r, c, left, s, t);
    return dp[N][M];
  }

  private static void debugPrint(int cn, int rm, int[][] dp) {
    for (int r = 0; r <= rm; r++) {
      for (int c = 0; c <= cn; c++) {
        System.out.print(" " + dp[r][c] + " ");
      }
      System.out.println();
    }
  }

  public static void recursionFindAll(
      List<String> all, char[] one, int[][] dp, int r, int c, int left, char[] s, char[] t) {
    if (left == 0) {
      all.add(new String(one));
      System.out.println(one);
      return;
    }

    if (dp[r][c] == dp[r - 1][c - 1] + 1 && s[c - 1] == t[r - 1]) {
      one[left - 1] = s[c - 1];
      recursionFindAll(all, one, dp, r - 1, c - 1, left - 1, s, t);
    } else {
      if (dp[r][c] == dp[r - 1][c]) {
        recursionFindAll(all, one, dp, r - 1, c, left, s, t);
      }
      if (dp[r][c] == dp[r][c - 1]) {
        recursionFindAll(all, one, dp, r, c - 1, left, s, t);
      }
    }
  }

  // runtime O(mn)
  public static int longestCommonSubsequenceLengthTopDown(String S, String T) {
    // corner cases checking
    if (S == null || S.isEmpty() || T == null || T.isEmpty()) return 0;

    char[] s = S.toCharArray(), t = T.toCharArray();
    int cn = S.length(), rm = T.length();
    Integer[][] log = new Integer[rm + 1][cn + 1];
    int result = valueOf(cn, rm, s, t, log);
    debugPrint(cn, rm, log);
    return result;
  }

  // recursion Top-Down
  private static int valueOf(int col, int row, char[] s, char[] t, Integer[][] log) {
    if (log[row][col] != null) return log[row][col]; // add this. use memorized result
    int result;
    if (col == 0 || row == 0) {
      result = 0;
    } else if (s[col - 1] == t[row - 1]) {
      result = 1 + valueOf(col - 1, row - 1, s, t, log);
    } else {
      result = Math.max(valueOf(col - 1, row, s, t, log), valueOf(col, row - 1, s, t, log));
    }
    log[row][col] = result; // memorizing
    return result;
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
