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

package two_pointer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Leetcode3LongestSubstringWithoutRepeatingCharacters {
  /*
      3. Longest Substring Without Repeating Characters

    Given a string s, find the length of the longest substring without repeating characters.



    Example 1:

    Input: s = "abcabcbb"
    Output: 3
    Explanation: The answer is "abc", with the length of 3.

    Example 2:

    Input: s = "bbbbb"
    Output: 1
    Explanation: The answer is "b", with the length of 1.

    Example 3:

    Input: s = "pwwkew"
    Output: 3
    Explanation: The answer is "wke", with the length of 3.
    Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.

    Example 4:

    Input: s = ""
    Output: 0



    Constraints:

        0 <= s.length <= 5 * 104
        s consists of English letters, digits, symbols and spaces.
  */
  /*
  checking s:
    null, empty
  Idea:
    2 pointer sliding window
    data structure:
    - 2 pointer for current window [l, ...] r,
      l and r is initialized as 0
      sliding window [l ,...] r, is not in window,
      r is the index of next char will be  in window or the length where
      calculate the max for the last time  and break the loop.
    - set represent the window unique chars
    O(N) time,
    O(1) space, at more 128

    int[26] for Letters 'a' - 'z' or 'A' - 'Z'
    int[128] for ASCII
    int[256] for Extended ASCII

   */

  public static int lengthOfLongestSubstring__(String s) {
    if (s == null || s.length() == 0) return 0;
    if (s.length() == 1) return 1;
    char[] a = s.toCharArray();
    int l = 0, r = 0;

    int max = 0;
    Set<Character> uni = new HashSet();
    while (r < a.length) {
      while (r < a.length && !uni.contains(a[r])) {
        uni.add(a[r]);
        r++;
      }
      max = Math.max(max, r - l);
      if (r == a.length) break; // note here
      while (a[l] != a[r]) { // r should be a valid index
        uni.remove(a[l]);
        l++;
      }
      uni.remove(a[l]);
      l++;
    }
    return max;
  }

  /*
   how to keep` r` out of window but always in valid index range?
   thus `l` will never care about overflow
   Let l do all things it may need to do before moving r.
   -  r in window now, before moving the r do:
   -  remove duplicate, this requires a Map or bucket not Set.
   -  before duplicated appears: there is no duplicated, and tracing max length is done
  */
  public static int lengthOfLongestSubstring_(String s) {
    if (s == null || s.length() == 0) return 0;
    int[] f = new int[128];
    char[] a = s.toCharArray();
    int l = 0, r = 0, max = 0;
    while (r < s.length()) {
      int c = a[r];
      f[c]++;

      while (f[c] == 2) {
        f[a[l]]--;
        l++;
      }
      max = Math.max(r - l + 1, max);

      r++;
    }
    return max;
  }

  /*
     it is slow to move `l` one char by one char till find the duplicated char?
     Map<Char, its index>, when a char is found duplicated
     - tracking max
     - move l to be Math.max(map.get(a[r]) + 1, l);
        why max,  see `abba`
                       0123
                       when r at second b
                         - l will be index 2
                       when r at the second a
                         - l will be index 1?
       map.put(char, r)
   It is still
   O(N) time,
   O(1) space, at more 128
  */
  public static int lengthOfLongestSubstring(String s) {
    if (s == null || s.length() == 0) return 0;
    Map<Character, Integer> map = new HashMap<>(); // char -> current index
    char[] a = s.toCharArray();
    int l = 0, r = 0, max = 0;
    while (r < s.length()) {
      if (map.containsKey(a[r])) {
        max = Math.max(max, r - l);
        l = Math.max(map.get(a[r]) + 1, l);
      }
      map.put(a[r], r);
    }
    max = Math.max(max, r - l);
    return max;
  }
}
