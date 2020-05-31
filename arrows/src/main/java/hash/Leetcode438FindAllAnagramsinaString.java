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

package hash;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Strings consists of
 * lowercase English letters only and
 * the length of both strings s and p will not be larger than 20,100.
 * The order of output does not matter.
 *
 * <a href="https://leetcode.com/problems/find-all-anagrams-in-a-string/#/description">Leetcode</a>
 */
public class Leetcode438FindAllAnagramsinaString {
  // O(N) use array as map, 128 length.
  public static List<Integer> findAnagrams2(String s, String p) {
    List<Integer> list = new ArrayList<>();
    if (s == null || s.length() == 0 || p == null || p.length() == 0 || p.length() > s.length()) {
      return list;
    }
    // at least there is one element; let r start from index of 0
    int[] map = new int[123]; // lowercase English letters only. a is 97, z is 122;A is 65, Z is 90
    char[] chars = p.toCharArray();
    for (char c : chars) {
      map[c]++;
    }

    int l = 0, r = 0, len = p.length();
    while (r < s.length()) {
      if (map[s.charAt(r)] >= 1) {
        len--;
      }
      map[s.charAt(r)]--; // always ++

      if (r - l == p.length()) {
        if (map[s.charAt(l)] >= 0) {
          len++;
        }
        map[s.charAt(l)]++; // always --;
        l++;
      }

      if (len == 0) {
        list.add(l);
      }

      // for next loop
      r++;
    }
    return list;
  }

  public static void main(String[] args) {
    System.out.println(findAnagrams3("cbaebabacd", "abc"));
  }

  // O(N) use array as map, length 26
  public static List<Integer> findAnagrams(String s, String p) {
    List<Integer> result = new ArrayList<>();
    if (s == null || s.length() == 0 || p == null || p.length() == 0 || p.length() > s.length()) {
      return result;
    }

    int[] map = new int[26]; // lowercase English letters only
    char[] chars = p.toCharArray();
    for (char c : chars) map[c - 'a']++;

    int l = 0, r = 0, len = p.length();
    while (r < s.length()) {
      if (map[s.charAt(r) - 'a']-- >= 1) len--;
      if (r - l == p.length() && map[s.charAt(l++) - 'a']++ >= 0) len++;
      if (len == 0) result.add(l);
      r++;
    }
    return result;
  }

  // O(N)
  public static List<Integer> findAnagrams3(String o, String t) {
    List<Integer> re = new ArrayList();
    if (o == null || t == null || o.length() == 0 || t.length() == 0) return re;
    // at least one char length

    char[] temp = t.toCharArray();
    int[] m = new int[123]; // 'z' is 122
    for (char c : temp) m[c]++;

    int l = 0, r = 0, size = t.length();
    char[] s = o.toCharArray();
    while (r < s.length) {
      if (m[s[r]] >= 1) size--;
      m[s[r]]--;
      r++;
      // above 3 step can be wrote into one line
      while (size == 0) { // not match this condition then save all operations in it
        // now the l maybe is 0;
        if (r - l == t.length()) re.add(l);
        if (m[s[l++]]++ >= 0) size++; // merge 3 steps into one
      }
    }
    return re;
  }
}
