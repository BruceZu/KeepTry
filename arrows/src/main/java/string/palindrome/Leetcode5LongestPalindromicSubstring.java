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

/*
5. Longest Palindromic Substring
Given a string s, return the longest palindromic substring in s.

Input: s = "babad"
Output: "bab"
Note: "aba" is also a valid answer.

Input: s = "cbbd"
Output: "bb"

Input: s = "a"
Output: "a"

Input: s = "ac"
Output: "a"

Constraints:

   1 <= s.length <= 1000
   s consist of only digits and English letters.
*/
public class Leetcode5LongestPalindromicSubstring {
  private int start, size;

  /*---------------------------------------------------------------------------
   Idea: Expand from each i and i-1,i when i>0 toward 2 sides
   O(N^2) time
   O(N) space
  */
  public String longestPalindrome(String s) {
    for (int i = 0; i < s.length(); i++) {
      expand(s, i, i);
      if (i > 0) expand(s, i - 1, i);
    }
    return s.substring(start, start + size);
  }

  private void expand(String s, int l, int r) {
    while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
      l--;
      r++;
    }
    l++;
    r--;
    if (size < r - l + 1) {
      start = l;
      size = r - l + 1;
    }
  }

  /*---------------------------------------------------------------------------
   Idea:  Manacher's algorithm with virtual translated string
  */
  public String longestPalindrome2(String s) {
    int[] v = Manacher.getRadiusOfVirtualTranslatedStringOf(s);
    int idx = 0; // in virtual string the longest palindrome sub-string center index
    int N = 2 * s.length() + 1;
    for (int i = 0; i < N; i++) if (v[i] > v[idx]) idx = i;

    int start = (idx - v[idx]) / 2;
    return s.substring(start, start + v[idx]);
  }
}
