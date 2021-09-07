//  Copyright 2017 The keepTry Open Source Project
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

package two_pointer;

/*
76. Minimum Window Substring


Given two strings s and t of lengths m and n respectively,
return the minimum window substring of s such that every character in t (including duplicates)
is included in the window. If there is no such substring, return the empty string "".

The testcases will be generated such that the answer is unique.

Example 1:

Input: s = "ADOBECODEBANC", t = "ABC"
Output: "BANC"
Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.

Input: s = "a", t = "a"
Output: "a"
Explanation: The entire string s is the minimum window.

Input: s = "a", t = "aa"
Output: ""
Explanation: Both 'a's from t must be included in the window.
Since the largest window of s only has one 'a', return empty string.

Constraints:
    m == s.length
    n == t.length
    1 <= m, n <= 105
    s and t consist of uppercase and lowercase English letters.


Follow up: Could you find an algorithm that runs in O(m + n) time?
 */

/*
Understanding;
  why it is said 'The testcases will be generated such that the answer is unique."
 */
public class Leetcode76MinimumWindowSubstring {
  /*
    Idea:
       s = "ADOBECODEBANC", t = "ABC". Answer: "BANC"

       Use Not fixed length Siding window
       Data structure:
        - int[26] frequency of target String and current window which is saved in the code
                  only valid char have positive required left frequency, the number of
                  step-out window char number <= that step-in
      - int totalLeftValidCharFrequency
  */
  public static String minWindow(String S, String T) {
    if (S == null || T == null || S.length() == 0 || S.length() < T.length()) return "";
    int[] t = new int[123];
    for (char c : T.toCharArray()) t[c - 'A']++;

    char[] s = S.toCharArray();
    int minLen = Integer.MAX_VALUE; // minimum window's string length
    int minStart = -1;

    // l and i are current window index[l, r].  L is left required total valid char's left.
    int l = 0, L = T.length();
    for (int r = 0; r < s.length; r++) {
      int c = s[r] - 'A';
      if (t[c] >= 1) L--;
      t[c]--;

      while (L == 0) { // Minimum Window
        if (r - l + 1 < minLen) {
          minLen = r - l + 1;
          minStart = l;
        }
        c = s[l] - 'A';
        if (t[c] >= 0) L++;
        t[c]++;
        l++;
      }
    }
    return minLen == Integer.MAX_VALUE ? "" : S.substring(minStart, minStart + minLen);
  }
}
