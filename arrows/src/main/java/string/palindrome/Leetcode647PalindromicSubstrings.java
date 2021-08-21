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

public class Leetcode647PalindromicSubstrings {
  /*
      647. Palindromic Substrings

    Given a string s, return the number of palindromic substrings in it.
    A string is a palindrome when it reads the same backward as forward.
    A substring is a contiguous sequence of characters within the string.

    Input: s = "abc"
    Output: 3
    Explanation: Three palindromic strings: "a", "b", "c".

    Input: s = "aaa"
    Output: 6
    Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".

    Constraints:

        1 <= s.length <= 1000
        s consists of lowercase English letters.

  */

  /*
  check all sub-string
  O(N^3) time O(1)space
  */
  private boolean isPalindrome(String s, int l, int r) {
    while (l < r) {
      if (s.charAt(l) != s.charAt(r)) return false;
      ++l;
      --r;
    }
    return true;
  }

  public int countSubstrings___(String s) {
    int a = 0;
    for (int l = 0; l < s.length(); ++l)
      for (int r = l; r < s.length(); ++r) a += isPalindrome(s, l, r) ? 1 : 0;
    return a;
  }

  /*
  reuse overlap, improve above solution-> O(N^2) time and space
  */

  public int countSubstrings__(String s) {
    int n = s.length(), a = 0;
    if (n <= 0) return 0;

    boolean[][] dp = new boolean[n][n];
    for (int i = 0; i < n; ++i, ++a) dp[i][i] = true;

    for (int i = 0; i < n - 1; ++i) {
      dp[i][i + 1] = (s.charAt(i) == s.charAt(i + 1));
      a += (dp[i][i + 1] ? 1 : 0);
    }

    for (int len = 3; len <= n; ++len)
      for (int i = 0, j = i + len - 1; j < n; ++i, ++j) {
        dp[i][j] = dp[i + 1][j - 1] && (s.charAt(i) == s.charAt(j));
        a += (dp[i][j] ? 1 : 0);
      }
    return a;
  }
  /*
   expanding idea
   O(N^2) time O(1) space
  */

  public int countSubstrings_(String s) {
    int a = 0;
    for (int i = 0; i < s.length(); ++i) {
      a += countPalindromesAroundCenter(s, i, i);
      a += countPalindromesAroundCenter(s, i, i + 1);
    }
    return a;
  }

  private int countPalindromesAroundCenter(String s, int l, int r) {
    int a = 0;
    while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
      a++;
      l--;
      r++;
    }
    return a;
  }

  /*
  Idea: Manacher
  'ab'  -> '_a_b_'  [01010]  => 2
  'aaa' -> '_a_a_a_'  [0123210]  => 6
   O(N) time and space
   */
  public static int countSubstrings(String s) {
    if (s == null || s.length() == 0) return 0;
    int v[] = Manacher.getRadiusOfVirtualTranslatedStringOf(s);
    int r = 0;
    int N = 2 * s.length() + 1;
    for (int i = 0; i < N; i++) if (v[i] != 0) r += (v[i] + 1) / 2;
    return r;
  }
}
