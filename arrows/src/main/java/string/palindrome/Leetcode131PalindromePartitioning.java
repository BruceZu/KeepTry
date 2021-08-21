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
import java.util.List;

public class Leetcode131PalindromePartitioning {
  /*
     131. Palindrome Partitioning
     Given a string s, partition s such that every substring of the partition is a palindrome.
     Return all possible palindrome partitioning of s.

   Input: s = "aab"
   Output: [["a","a","b"],["aa","b"]]

   Input: s = "a"
   Output: [["a"]]

   Constraints:
       1 <= s.length <= 16
       s contains only lowercase English letters.
  */

  /*
  Idea:
     calculate whether any given subarray is palindrome ore not.
     DFS to try partition the string.

    O(N⋅2^N) time N is the length of string sss.
           In the worst case, e.g. 'aaaa'  2^N possible substrings
           take O(N) to generate each substring using substr
    O(N^2) space
   */
  public List<List<String>> partition_(String s) {
    List<List<String>> r = new ArrayList<>();
    if (s == null || s.length() == 0) return r;
    boolean[][] dp = isPalindrome(s.toCharArray());
    dfs(r, new ArrayList<>(), dp, s, 0);
    return r;
  }

  private boolean[][] isPalindrome(char[] s) {
    int N = s.length;
    boolean dp[][] = new boolean[N][N]; // default false
    for (int r = 0; r < N; r++) {
      for (int l = r; 0 <= l; l--) { // not backward is still okay
        //   s contains only lowercase English letters.'
        if (s[l] == s[r] && (l >= r - 2 || dp[l + 1][r - 1])) dp[l][r] = true;
      }
    }
    return dp;
  }

  private void dfs(
      List<List<String>> a, List<String> tmp, boolean[][] isPalindrome, String s, int from) {
    if (from == s.length()) {
      a.add(new ArrayList<>(tmp));
      return;
    }
    for (int r = from; r < s.length(); r++) {
      if (isPalindrome[from][r]) {
        tmp.add(s.substring(from, r + 1));
        dfs(a, tmp, isPalindrome, s, r + 1);
        tmp.remove(tmp.size() - 1);
      }
    }
  }

  /*
   merge together
    "aabb"
    possible cut in the first level call
    a
    aa
    aab
    aabb : how to know the dp[i+1][j-1]? it is got in the second level call
  */
  public List<List<String>> partition(String s) {
    int N = s.length();
    boolean[][] dp = new boolean[N][N];
    List<List<String>> r = new ArrayList<>();
    dfs(r, s, 0, new ArrayList<>(), dp);
    return r;
  }

  void dfs(List<List<String>> result, String s, int from, List<String> tmp, boolean[][] dp) {
    if (from >= s.length()) result.add(new ArrayList<>(tmp));
    for (int to = from; to < s.length(); to++) {
      if (s.charAt(from) == s.charAt(to) && (from >= to - 2 || dp[from + 1][to - 1])) {
        dp[from][to] = true;
        tmp.add(s.substring(from, to + 1));
        dfs(result, s, to + 1, tmp, dp);
        tmp.remove(tmp.size() - 1);
      }
    }
  }
}
