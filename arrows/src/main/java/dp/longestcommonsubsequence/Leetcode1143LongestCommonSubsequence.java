//  Copyright 2022 The KeepTry Open Source Project
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

public class Leetcode1143LongestCommonSubsequence {
  /*
   Leetcode 1143. Longest Common Subsequence
    Given two strings text1 and text2,
    return the length of their longest common subsequence.
    If there is no common subsequence, return 0.

    A subsequence of a string is a new string generated from the original string
    with some characters (can be none) deleted without changing the
    relative order of the remaining characters.

    For example, "ace" is a subsequence of "abcde".
    A common subsequence of two strings is a subsequence that is common to both strings.

    Input: text1 = "abcde",
           text2 = "ace"
    Output: 3
    Explanation: The longest common subsequence is "ace" and its length is 3.


    Input: text1 = "abc",
           text2 = "abc"
    Output: 3
    Explanation: The longest common subsequence is "abc" and its length is 3.


    Input: text1 = "abc",
           text2 = "def"
    Output: 0
    Explanation: There is no such common subsequence, so the result is 0.


    Constraints:
    1 <= text1.length, text2.length <= 1000
    text1 and text2 consist of only lowercase English characters.
  */
  /*
   Watch: restriction: 2 strings are not null and empty
          no space

   dynamic programming
   O(M*N) time and
   O(M+N) space. can select the shorter one as the top horizon one,
                            the longer one as the left vertical one
   https://imgur.com/m4fVJHX
  */
  public int longestCommonSubsequence(String A, String B) {
    char[] a = A.toCharArray();
    char[] b = B.toCharArray();
    int[] dp = new int[b.length + 1];

    for (int i = 0; i < a.length; i++) {
      char c = a[i];

      int[] dp_ = new int[b.length + 1];
      for (int j = 1; j <= b.length; j++) {
        char cc = b[j - 1];
        dp_[j] = Math.max(dp_[j - 1], dp[j]);
        dp_[j] = Math.max(dp_[j], dp[j - 1] + (cc == c ? 1 : 0)); // need ()
      }
      dp = dp_;
    }
    return dp[b.length];
  }

  // avoid new dp_
  public int longestCommonSubsequence_(String A, String B) {
    char[] a = A.toCharArray();
    char[] b = B.toCharArray();
    int[] dp = new int[b.length + 1];
    int[] dp_ = new int[b.length + 1];

    for (int i = 0; i < a.length; i++) {
      char c = a[i];
      // dp_= new int[b.length+1];
      for (int j = 1; j <= b.length; j++) {
        char cc = b[j - 1];
        dp_[j] = Math.max(dp_[j - 1], dp[j]);
        dp_[j] = Math.max(dp_[j], dp[j - 1] + (cc == c ? 1 : 0)); // need ()
      }
      int[] tmp = dp;
      dp = dp_;
      dp_ = tmp;
    }
    return dp[b.length];
  }
}
