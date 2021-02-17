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

import java.util.*;

/**
 * <pre>
 * <a href="https://leetcode.com/problems/minimum-window-substring/?tab=Description">LeetCode</a>
 * data structure:  using mapping and length of target
 * algorithm: 2 pointers
 */
public class Leetcode76MinimumWindowSubstring {
  public static String minWindow(String s, String t) {
    if (s == null || t == null || s.length() == 0 || s.length() < t.length()) return "";

    Map<Character, Integer> m = new HashMap();
    for (char c : t.toCharArray()) {
      m.put(c, m.getOrDefault(c, 0) + 1);
    }

    int l = 0, r = 0, counter = t.length();

    char[] o = s.toCharArray();

    // keep the candidate or result
    int len = Integer.MAX_VALUE;
    int start = -1;
    while (r < o.length) {
      char cr = o[r];
      if (m.containsKey(cr)) {
        if (m.get(cr) >= 1) counter--;
        m.put(cr, m.get(cr) - 1);
      }
      r++;

      while (counter == 0) {
        if (r - l < len) {
          len = r - l;
          start = l;
        }
        char lc = o[l];
        if (m.containsKey(lc)) {
          if (m.get(lc) >= 0) counter++;
          m.put(lc, m.get(lc) + 1);
        }
        l++;
      }
    }
    return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
  }

  // leetcode 3. Longest Substring Without Repeating Characters
  public static int lengthOfLongestSubstring(String s) {
    if (s == null || s.length() == 0) return 0;
    // at least length is 1
    Map<Character, Integer> map = new HashMap();

    int l = 0, r = 0;
    char[] o = s.toCharArray();

    int ll = Integer.MIN_VALUE;
    while (r < s.length()) {
      char rc = o[r];
      map.put(rc, map.getOrDefault(rc, 0) + 1);
      r++;

      while (map.get(rc) == 2) {
        // move left
        char lc = o[l];
        map.put(lc, map.get(lc) - 1);
        l++;
      }
      // current l may be at the repeated char
      ll = Math.max(r - l, ll);
    }
    return ll;
  }

  // Leetcode 30. Substring with Concatenation of All Words  <3>
  // O(W.S). W: word length, S: source string length
  public List<Integer> findSubstring3(String S, String[] L) {
    List<Integer> res = new ArrayList();
    if (S == null || L == null || S.length() == 0 || L.length == 0) {
      return res;
    }
    // at least S have one char
    int wl = L[0].length();

    for (int start = 0; start < wl; start++) {
      Map<String, Integer> m = new HashMap();
      for (String w : L) {
        m.put(w, m.getOrDefault(w, 0) + 1);
      }
      int counter = L.length;
      int l = start, r = start;
      while (r <= S.length() - wl) {
        // r in window
        String ru = S.substring(r, r + wl);
        if (m.containsKey(ru)) {
          if (m.get(ru) >= 1) counter--;
          m.put(ru, m.get(ru) - 1);
        }
        r = r + wl;

        if (r - l == wl * L.length) {
          // maybe it is firstly reach a window length
          if (counter == 0) res.add(l);

          // l out of window
          String lu = S.substring(l, l + wl);
          if (m.containsKey(lu)) {
            if (m.get(lu) >= 0) counter++;
            m.put(lu, m.get(lu) + 1);
          }
          l = l + wl;
        }
      } // end while. at this time the model and counter is dirty, nee reset.
    }
    return res;
  }
  // Leetcode 30. Substring with Concatenation of All Words  <2>
  // O(S). S: source string length.
  //   use a mirror model and counter 2 to
  //   save the reset work of create model and counter.
  public List<Integer> findSubstring2(String S, String[] L) {
    List<Integer> res = new ArrayList();
    if (S == null || L == null || S.length() == 0 || L.length == 0) {
      return res;
    }
    // at least S have one char
    Map<String, Integer> m = new HashMap();
    for (String w : L) {
      m.put(w, m.getOrDefault(w, 0) + 1);
    }
    int counter = L.length;
    int wl = L[0].length();

    for (int start = 0; start < wl; start++) {
      int l = start, r = start;
      Map<String, Integer> m2 = new HashMap();
      int counter2 = 0;

      while (r <= S.length() - wl) {
        // r in window
        String ru = S.substring(r, r + wl);
        if (m.containsKey(ru)) {
          m2.put(ru, m2.getOrDefault(ru, 0) + 1);
          if (m2.get(ru) >= 1 && m2.get(ru) <= m.get(ru)) counter2++; // [1, x]
        }
        r = r + wl;
        if (r - l == wl * L.length) {
          // maybe it is firstly reach a window length
          if (counter == counter2) res.add(l); // not compare with 0

          // l out of window
          String lu = S.substring(l, l + wl);
          if (m.containsKey(lu)) {
            m2.put(lu, m2.getOrDefault(lu, 0) - 1);
            if (m2.get(lu) >= 0 && m2.get(lu) < m.get(lu)) counter2--; // [0, x)
          }
          l = l + wl;
        }
      }
    }
    return res;
  }
  // Leetcode 30. Substring with Concatenation of All Words  <1> best
  // only change ** 2 lines ** compared with the above one:
  public List<Integer> findSubstring(String S, String[] L) {
    List<Integer> res = new ArrayList();
    if (S == null || L == null || S.length() == 0 || L.length == 0) {
      return res;
    }
    // at least S have one char
    Map<String, Integer> m = new HashMap();
    for (String w : L) {
      m.put(w, m.getOrDefault(w, 0) + 1);
    }
    int counter = L.length;
    int wl = L[0].length();

    for (int start = 0; start < wl; start++) {
      int l = start, r = start;
      Map<String, Integer> m2 = new HashMap();
      int counter2 = 0;

      while (r <= S.length() - wl) {
        // r in window
        String ru = S.substring(r, r + wl);
        if (m.containsKey(ru)) {
          m2.put(ru, m2.getOrDefault(ru, 0) + 1);
          if (m2.get(ru) >= 1 && m2.get(ru) <= m.get(ru)) counter2++; // [1, x]
        }
        r = r + wl;
        while (counter == counter2) { // not compare with 0 . ** 2 lines start **
          // maybe it is firstly reach a window length
          if (r - l == wl * L.length) res.add(l); //          ** 2 lines end **

          // l out of window
          String lu = S.substring(l, l + wl);
          if (m.containsKey(lu)) {
            m2.put(lu, m2.getOrDefault(lu, 0) - 1);
            if (m2.get(lu) >= 0 && m2.get(lu) < m.get(lu)) counter2--; // [0, x)
          }
          l = l + wl;
        }
      }
    }
    return res;
  }

  // Leetcode 159 Longest Substring with At Most Two Distinct Characters

  /**
   * Given a string s , find the length of the longest substring t that contains at most 2 distinct
   * characters.
   *
   * <p>Example 1: Input: "eceba" Output: 3 Explanation: tis "ece" which its length is 3.
   *
   * <p>Example 2: Input: "ccaabbb" Output: 5 Explanation: tis "aabbb" which its length is 5.
   */
  // runtime O(N), space O(1)
  // use Map.size() to maintain the kinds of char.
  static int lengthOfLongestSubstringTwoDistinct(String str) {
    if (str == null || str.length() <= 2) return 0;
    // at lest there are 3 chars
    Map<Character, Integer> map = new HashMap<>();
    int l = 0, r = 0, res = Integer.MIN_VALUE;
    char[] s = str.toCharArray();
    while (r < s.length) {
      // r in window
      char rc = s[r];
      map.put(rc, map.getOrDefault(rc, 0) + 1);
      r++;

      while (map.size() == 3) {
        // l out of window
        char lc = s[l];
        map.put(lc, map.get(lc) - 1);
        if (map.get(lc) == 0) map.remove(lc); // maintain the counter. HashMap remove() is O(1)
        l++;
      }
      // counter is <=2
      res = Math.max(res, r - l);
    }

    return res;
  }

  // not use Map.size(), instead use a counter to maintain the kinds of char.
  public static int lengthOfLongestSubstringTwoDistinct2(String s) {
    Map<Character, Integer> map = new HashMap<>();
    int l = 0, r = 0, res = 0, counter = 0;

    while (r < s.length()) {
      char c = s.charAt(r);
      map.put(c, map.getOrDefault(c, 0) + 1);
      if (map.get(c) == 1) counter++;
      r++;

      while (counter == 3) {
        char lc = s.charAt(l);
        map.put(lc, map.get(lc) - 1);
        if (map.get(lc) == 0) counter--;
        l++;
      }
      res = Math.max(res, r - l);
    }
    return res;
  }

  // Leetcode 904. Fruit Into Baskets
  // 2 pointer
  public int totalFruit2(int[] tree) {
    if (tree == null || tree.length == 0) return 0;
    // at least there is one element

    int l = 0, r = 0;
    int res = 0;
    Map<Integer, Integer> m = new HashMap();
    // model of target. Use map.size() keep the counter of distinct type

    while (r < tree.length) {
      // r in window
      int rt = tree[r]; // r pointer 's fruit type
      m.put(rt, m.getOrDefault(rt, 0) + 1);
      r++;

      while (m.size() == 3) {
        // l in window
        int lt = tree[l]; // r pointer 's fruit type
        m.put(lt, m.get(lt) - 1);
        if (m.get(lt) == 0) m.remove(lt); // update the map size
        l++;
      }
      // calculate the length/number of fruits with type x<=2
      res = Math.max(res, r - l);
    }
    return res;
  }

  public int totalFruit(int[] tree) {
    if (tree == null || tree.length == 0) return 0;
    // at least there is one element
    // a b c is the tree type
    // q  is the fruit number of the last second and last one type
    // qb is the fruit number of the last one type
    int res = 0, q = 0, qb = 0, a = 0, b = 0;
    for (int c : tree) {
      q = c == a || c == b ? q + 1 : qb + 1;
      res = Math.max(res, q);
      qb = c == b ? qb + 1 : 1;
      if (c != b) {
        a = b;
        b = c;
      }
    }
    return res;
  }
}
