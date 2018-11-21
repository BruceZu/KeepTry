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
   * for the array:start with index 0 and end up with current index of i-1 (including),
   * the length of
   * - the longest prefix starting from the w[0], used by w
   * - at the same time it is should also be the longest suffix end up with w[i-1] (including). used by s.
   * - it is also the new wi, fantastic.
   * Use of "Partial match" table:
   * get the value of "Partial match" table of pattern 'w' at index of 'wi':
   *
   * when the w[0] is not match. move w right side 1 index;（ｉt is si++;）
   * This is special case. precess it before using PMT
   */
  static int[] newIndexToTry2(String word) {
    // get Partial Match Table
    if (word == null || word.isEmpty()) return null;
    char[] w = word.toCharArray();
    int[] I = new int[w.length];
    // length of longest proper prefix and subfix;
    // also is the next index of w to try when current char comparing failed between S and W.

    I[0] = -1;
    if (w.length == 1) return I;

    I[1] = 0;
    for (int i = 2; i < w.length; i++) {
      int l = I[i - 1]; // is the length of .. and also is the index of w
      char currentChar = w[i - 1];
      while (true) {
        if (currentChar == w[l]) {
          I[i] = l + 1; // is length of .... need not checking the longer ones.
          break;
        }
        if (l == 0) {
          // 0 length of longest proper prefix and subfix,
          // means now compare directly with w[0] to see if it is possible to get
          // 1 or 0 length of longest proper prefix and subfix.
          I[i] = 0;
          break;
        }
        l = I[l]; // v>=1, continue loop;  v is index .
      }
    }
    return I;
  }

  // O(m) m is the length of word
  static int[] newIndexToTry(String word) {
    if (word == null || word.isEmpty()) return null;
    char[] w = word.toCharArray();
    int[] T = new int[w.length];

    T[0] = -1;
    if (w.length == 1) return T;

    int wi = 0; // index of char in W
    int i = 1; // index in T[]

    while (i < w.length) {
      if (w[i] == w[wi]) {
        T[i] = T[wi]; // performance feature
      } else {
        T[i] = wi;
        // calculate for next i
        wi = T[wi]; // performance improve;
        while (wi >= 0 && w[i] != w[wi]) {
          wi = T[wi];
        }
      }
      i++;
      wi++;
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

    int NI[] = newIndexToTry(w);
    int si = 0;
    int wi = 0;
    while (si < s.length()) { // when si is s.length; firstly check wi, then check si.
      if (w.charAt(wi) == s.charAt(si)) {
        // wi and si go ahead 1 step.
        si++;
        wi++;
        if (wi == w.length()) {
          return si - w.length();
        }
      } else {
        if (NI[wi] == -1) si++; //  wi is still, which is 0 now, si go ahead 1 step
        else wi = NI[wi]; // fantastic. si is still, wi is backtracking try, at most wi steps
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    System.out.println(KMP.KMP("ABCABD", "CA"));
    System.out.println(KMP.KMP("ABAABAAC", "CA"));

    System.out.println(Arrays.toString(KMP.newIndexToTry("ABCDABD")));
    System.out.println(Arrays.toString(KMP.newIndexToTry("ABACABABC")));
    System.out.println(Arrays.toString(KMP.newIndexToTry("PARTICIPATE IN PARACHUTE")));
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
