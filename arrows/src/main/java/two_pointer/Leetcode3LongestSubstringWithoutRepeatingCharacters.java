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
    Leetcode 3. Longest Substring Without Repeating Characters

    Given a string s, find the length of the longest substring without repeating characters.

    Input: s = "abcabcbb"
    Output: 3
    Explanation: The answer is "abc", with the length of 3.

    Input: s = "bbbbb"
    Output: 1
    Explanation: The answer is "b", with the length of 1.


    Input: s = "pwwkew"
    Output: 3
    Explanation: The answer is "wke", with the length of 3.
    Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.

    Input: s = ""
    Output: 0

    Constraints:

        0 <= s.length <= 5 * 104
        s consists of English letters, digits, symbols and spaces.
  */

  /*
  sliding window:
  each time take in one element at index r
    - if window does not contain it, add it directly
    - else: firstly updating map and l till window does not contain the char at r, then add the char.
  map keep char and frequency
  can use a array as the map.

  O(N) time,
  O(Min{N,m}) space, map is upper bounded by the size of the string n and the size of the charset/alphabet m.
  */
  public int lengthOfLongestSubstring__(String str) {
    if (str == null || str.length() == 0) return 0;
    char[] arr = str.toCharArray();
    Map<Character, Integer> map = new HashMap<>();
    int l = 0, r = 0, a = 0;
    for (; r < arr.length; r++) {
      char c = arr[r];
      if (!map.containsKey(c)) {
        map.put(c, 1);
        a = Math.max(a, r - l + 1);
      } else {
        while (map.containsKey(c)) {
          char lv = arr[l];
          map.put(lv, map.get(lv) - 1);
          if (map.get(lv) == 0) map.remove(lv);
          l++;
        }
        map.put(c, 1);
      }
    }
    return a;
  }
  /*
  Actually this is my version
  each time move one step of right pointer and maintain
    - distinguish sliding window
    - current longest sub distinguish string

  The original question need return the longest substring itself
   */
  public int lengthOfLongestSubstring(String str) {
    if (str == null || str.length() == 0) return 0;
    char[] s = str.toCharArray();
    int N = s.length;
    Map<Character, Integer> map = new HashMap<>();
    String ans = "";
    int len = 0;
    int l = 0, r = 0;
    // r is the right pointer, related char will be accepted in sliding window specified by index
    // range [l,r]
    while (true) {
      if (r < N) {
        char c = s[r];
        map.putIfAbsent(c, 0);
        if (map.get(c) == 1) { // duplicated char
          map.put(c, 2);
          while (map.get(s[r]) == 2 && l <= r) {
            map.put(s[l], map.get(s[l]) - 1); // forget updating the window char frequency
            if (map.get(s[l]) == 0) map.remove(s[l]);
            l++;
          }
          r++;
        } else { // new distinguish char
          map.put(c, 1);
          r++;
          if (r - l > len) {
            len = r - l;
            ans = str.substring(l, r);
          }
        }
      } else { // r reaches the end of string
        break;
      }
    }

    return len;
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
   O(m) space, at most 128
  */
  public int lengthOfLongestSubstring_(String s) {
    if (s == null || s.length() == 0) return 0;
    Map<Character, Integer> map = new HashMap<>(); // char -> current index
    char[] a = s.toCharArray();
    int l = 0, r = 0, max = 0;
    for (; r < a.length; r++) {
      char c = a[r];
      if (map.containsKey(c)) {
        max = Math.max(max, r - l);
        l = Math.max(map.get(c) + 1, l); // at least move to index of map.get(cur) + 1
      }
      map.put(c, r);
    }
    max = Math.max(max, r - l);
    return max;
  }
}
