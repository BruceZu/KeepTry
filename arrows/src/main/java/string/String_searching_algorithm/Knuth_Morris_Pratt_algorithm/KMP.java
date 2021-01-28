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

public class KMP {
  // O(N）
  public static int[] getWidestBorderLength(char[] I) {
    if (I == null || I.length == 0) return null;
    // N >= 1
    int N = I.length;
    // In KMP it is called as the failure function lookup table
    int[] len = new int[N];

    // For index is 0 or after loop cur index is 0
    // - No len[d-1] value to refer and base on it to calculate len[d]
    // - For sub-string str[0], its widest border length is 0. Allow empty prefixes and suffixes.
    //   In Java len[0] default value is 0;
    for (int d = 0; d < N; d++) {
      int i = d; // Do not change d
      /*
      // loop 1 or loop 2
      // loop 1:
      while (i > 0) { // At most check len[d-1] steps.
        int mi = len[i - 1];
        // mi is length of widest border of sub-staring [0, i-1] of I
        // mi is also mirror index, mi - 1 + 1, used to compare with S[u].
        if (I[mi] == I[d]) {
          len[d] = mi + 1;
          break;
        }
        i = mi;
      }
      */

      // loop 2
      while (0 < i && I[len[i - 1]] != I[d]) i = len[i - 1];
      if (i != 0) len[d] = len[i - 1] + 1;
      if (i == 0) len[d] = 0; // default;
    }
    return len;
  }

  public static int indexOfFirstFoundByKMP(String strS, String strI) {
    // O(M + N):
    if (strS == null || strI == null || strI.length() > strS.length()) return -1;
    char[] S = strS.toCharArray(), I = strI.toCharArray();
    int M = S.length, N = I.length;
    int u = 0, d = 0;
    int[] len = getWidestBorderLength(I);
    while (true) {
      while (u < M && d < N && S[u] == I[d]) { // Three conditions
        u++;
        d++;
      }
      if (d == N) return u - N;
      // Check i fistly before checking s. If need fins all matched sub-strings,
      // then collect  result here and continue
      if (u == M) return -1;
      // Move d in I right forward, or move u in S left forward
      if (d == 0) {
        u++;
      } else {
        d = len[d - 1]; // d >= 1

        // Performance improvement, not change algorithm time:
        // Replace above one line by the following loop 1 or loop 2
        /*
                // loop 1
                int i = d; // do not change d
                while (0 < i && I[len[i - 1]] == I[d]) i = len[i - 1];
                if (i > 0) d = len[i - 1];
                else d = 0;
        */

        /*
        // loop 2
        int i = d; // do not change d
        while (0 < i) { // 1> i >= 1. It is possible to has back forward mirror
          int mi = len[i - 1];
          if (I[mi] != I[i]) { // 2> stop find back forward. move to mirror index and leave loop
            i = mi;
            break;
          }
          i = mi; // 3> move to mirror index and continue find back forward.
        }
        // i still can be
        //  - 0, and I[0] is still same as I[d] which is != S[u],
        //    in this case move S back forward. that means to compare S[u+1] with I[0]
        //  - 0, and I[0] != I[d]
        //  - not 0, and I[0] != I[d]
        d = i;
        */
      }
    }
  }

  // ---------------------------- No comments version -----------------------------
  public static int[] getWidestBorderLengthNoComments(char[] I) {
    if (I == null || I.length == 0) return null;
    int N = I.length;
    int[] len = new int[N];

    for (int d = 0; d < N; d++) {
      int i = d;
      while (0 < i && I[len[i - 1]] != I[d]) i = len[i - 1];
      if (i != 0) len[d] = len[i - 1] + 1;
    }
    return len;
  }

  public static int indexOfFirstFoundByKMPNoComments(String strS, String strI) {
    if (strS == null || strI == null || strI.length() > strS.length()) return -1;
    char[] S = strS.toCharArray(), I = strI.toCharArray();
    int M = S.length, N = I.length;
    int u = 0, d = 0;
    int[] len = getWidestBorderLengthNoComments(I);
    while (true) {
      while (u < M && d < N && S[u] == I[d]) {
        u++;
        d++;
      }
      if (d == N) return u - N;
      if (u == M) return -1;
      if (d == 0) {
        u++;
      } else {
        d = len[d - 1];
      }
    }
  }
  // ---------------------------- Merge together version -----------------------------
  public static int indexOfFirstFoundByKMPMerge(String strS, String strI) {
    if (strS == null || strI == null || strI.length() > strS.length()) return -1;
    char[] S = strS.toCharArray(), I = strI.toCharArray();
    int M = S.length, N = I.length;
    // prepare widest border array
    int[] len = new int[N];
    for (int d = 0; d < N; d++) {
      int i = d;
      while (0 < i && I[len[i - 1]] != I[d]) i = len[i - 1];
      if (i != 0) len[d] = len[i - 1] + 1;
    }
    // compare
    int u = 0, d = 0;
    while (true) {
      while (u < M && d < N && S[u] == I[d]) {
        u++;
        d++;
      }
      if (d == N) return u - N;
      if (u == M) return -1;
      if (d == 0) {
        u++;
      } else {
        d = len[d - 1];
      }
    }
  }
}
