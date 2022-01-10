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

package two_pointer;

public class Leetcode161OneEditDistance {
  /*
  Leetcode 161. One Edit Distance
  Given two strings s and t, return true if they are both one edit distance apart, otherwise return false.

  A string s is said to be one distance apart from a string t if you can:

  Insert exactly one character into s to get t.
  Delete exactly one character from s to get t.
  Replace exactly one character of s with a different character to get t.


  Input: s = "ab", t = "acb"
  Output: true
  Explanation: We can insert 'c' into s to get t.


  Input: s = "", t = ""
  Output: false
  Explanation: We cannot get t from s by only one step.


  Constraints:
  0 <= s.length, t.length <= 104
  s and t consist of lowercase letters, uppercase letters, and digits.
   */
  public boolean isOneEditDistance__(String S, String T) {
    int i = -1, M = S.length(), j = -1, N = T.length();
    char[] a = S.toCharArray(), b = T.toCharArray();
    if (Math.abs(M - N) > 1) return false;
    // size diff is 0 or 1
    // left side
    while (i < M - 1 && j < N - 1 && a[i + 1] == b[j + 1]) {
      i++;
      j++;
    }

    // equals
    if (M == N && i == M - 1) return false;
    // right side
    while (M > 0 && N > 0 && a[M - 1] == b[N - 1]) {
      M--;
      N--;
    }

    return Math.max(M - i, N - j) < 3;
  }

  // 2 pointers to go through each string and compare
  public boolean isOneEditDistance_(String s, String t) {
    char[] longer = s.length() > t.length() ? s.toCharArray() : t.toCharArray();
    char[] shorter = s.length() > t.length() ? t.toCharArray() : s.toCharArray();

    if (longer.length - shorter.length > 1) return false;
    // diff is 1 or 0
    int cnt = 0; // number of disagreement

    int i = 0, j = 0; // j for shorter
    while (true) {
      while (j < shorter.length && longer[i] == shorter[j]) {
        ++i;
        ++j;
      }

      if (j == shorter.length) {
        if (cnt == 0 && i == longer.length - 1 || cnt == 1 && i == longer.length) return true;
        return false;
      }
      if (++cnt > 1) return false;

      // same length, replace
      if (longer.length == shorter.length) {
        ++i;
        ++j;
      } else { // delete from longer
        ++i;
      }
    }
  }

  public boolean isOneEditDistance(String s, String t) {
    int M = s.length();
    int N = t.length();

    // Ensure that s is shorter than t.
    if (M > N) return isOneEditDistance(t, s);
    if (N - M > 1) return false;
    // Len diff is 0 or 1 now
    for (int i = 0; i < M; i++) {
      if (s.charAt(i) != t.charAt(i)) {
        if (M == N) return s.substring(i + 1).equals(t.substring(i + 1));
        else return s.substring(i).equals(t.substring(i + 1));
      }
    }
    return (M + 1 == N);
  }
}
