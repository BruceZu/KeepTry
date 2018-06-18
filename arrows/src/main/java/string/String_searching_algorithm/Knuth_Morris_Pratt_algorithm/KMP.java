//  Copyright 2016 The Sawdust Open Source Project
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

package string.String_searching_algorithm.Knuth_Morris_Pratt_algorithm;

import java.util.Arrays;

/**
 * <a href ="https://en.wikipedia.org/wiki/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm">wiki</a>
 * <pre>
 *   worst-case performance is O(k⋅n)
 *   s[] length is n
 *   W[] length is k
 *   e.g.    s[] =  AAAAAA
 *           W[] =  AAB
 */
public class KMP {
  /**
   * <pre>
   * get Partial Match Table of pattern w
   * assume str length is at least 1.
   * assume str does not contain supplementary character
   * <img src="../../../../resources/KMP_partial_T.png" height="450" width="500">
   * O(m), where k is the length of pattern w
   *
   *
   * "Partial match" table (also known as "failure function")
   * when mismatch happen between s[si] and w[wi].
   * need move w right side some distance. and then compare updated si and wi or only wi
   * and continue compare s[si] and w[wi].
   *
   * The PMT determines the distance and how to decide the wi and si.
   * The goal of the table is: not to let any character of S match successfully more than once .
   *
   *
   * meaning of value of w[i] is:
   * for the array:start with index 0 and end up with current index of i (including),
   * the length of
   * - the longest prefix starting from the w[0], used by w
   * - at the same time it is should also be the longest suffix end up with w[i] (including). used by s.
   * - it is also the new wi, fantastic.
   * Use of "Partial match" table:
   * get the value of "Partial match" table of pattern 'w' at index of 'wi-1':
   * T[wi-1]; wi>=1.
   * when the w[0] is not match. move w right side 1 index;（ｉt is si++;）
   * This is special case. precess it before using PMT
   */
  static int[] getPartialMatchTable(String w) {
    char[] chars = w.toCharArray();
    int[] T = new int[chars.length];

    T[0] = 0;
    for (int i = 1; i < chars.length; i++) {
      int v = T[i - 1]; // is the length of .. and also is the index of w
      while (true) {
        if (chars[i] == chars[v]) {
          T[i] = v + 1; // is length of ...
          break;
        }
        if (v == 0) { // reach the index 0
          T[i] = 0;
          break;
        }
        v = T[v - 1]; // v>=1, continue loop;  v is index .
      }
    }
    return T;
  }
  
  // get the index of str where find the first match of give str string.
  // O(n+m), where n is the length of s, m is the length of w
  static int KMP(String s, String w) {
    if (w == null || s == null || w.length() > s.length()) {
      return -1;
    }

    if (w.isEmpty()) {
      return 0;
    }
    if (s.isEmpty()) return -1;

    int T[] = getPartialMatchTable(w);
    int si = 0;
    int wi = 0;
    while (true) { // when si is s.length; firstly check wi, then check si.
      if (wi == w.length()) {
        return si - w.length();
      }
      if (si == s.length()) {
        return -1;
      }
      if (w.charAt(wi) == s.charAt(si)) {
        si++;
        wi++;
      } else {
        if (wi == 0) si++;
        else wi = T[wi - 1]; // fantastic
      }
    }
  }

  /**
   * <pre>
   * haystack = "hello world"
   * needle = "world"
   * return 6
   *
   * haystack length is m
   * needle length is n
   * O(n*(m-n))
   *
   * Give a worse case
   *
   * @param haystack
   * @param needle
   * @return index of the char from where the needle appears for the fist time.
   */
  public static int forceWay(String haystack, String needle) {
    // check corner cases
    if (haystack == null || needle == null) {
      return -1;
    }
    if (needle.isEmpty()) {
      return 0;
    }
    //
    int max = haystack.length() - needle.length();
    for (int i = 0; i <= max; i++) { // care it is <= not <
      char curC = haystack.charAt(i);
      if (curC == needle.charAt(0)) {

        boolean found = true;
        for (int j = 1; j < needle.length(); j++) {
          if (needle.charAt(j) != haystack.charAt(i + j)) {
            found = false;
            break;
          }
        }

        if (found) {
          return i;
        }
      }
    }
    return -1;
  }
}
