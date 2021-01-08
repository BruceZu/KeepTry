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

public class Leetcode5LongestPalindromicSubstring {
  public String longestPalindrome(String s) {
    if (s == null || s.length() == 0) return s;
    int N = 2 * s.length() + 1;
    char[] T = new char[N];
    for (int i = 0; i < N; i++) {
      // assume '#' is not in s
      T[i] = (i & 1) == 1 ? s.charAt(i / 2) : '#';
    }

    int[] radius = new int[N];
    // when index==0, for "#"
    int maxRight = 0,
        c = 0; // axis or center of palindrome which has the rightmost boundary character
    int rc = 0; // first longest palindromic sub-string center index in T
    for (int i = 1; i < N; i++) {
      if (i <= maxRight) {
        int mirror = 2 * c - i; // axis-(i-axis)
        radius[i] = Math.min(maxRight - i, radius[mirror]);
        if (radius[mirror] != maxRight - i) {
          // need not update maxRight, axis, and result
          continue;
        }
      }

      int l = i - (1 + radius[i]), r = i + (1 + radius[i]);
      while (l >= 0 && r < N && T[l] == T[r]) {
        radius[i]++;
        l--;
        r++;
      }

      if (i + radius[i] > maxRight) {
        maxRight = i + radius[i];
        c = i;
      }
      // application: longest palindromic sub-string
      if (radius[i] > radius[rc]) {
        rc = i;
      }
    }
    int f = (rc - radius[rc]) / 2;
    return s.substring(f, f + radius[rc]);
  }

  /** when the division character is not easy to find,use virtual augmented string */
  public String longestPalindrome2(String s) {
    if (s == null || s.length() == 0) return s;
    int N = 2 * s.length() + 1;
    int[] radius = new int[N];
    // when index==0, for virtual division character
    int c = 0; // axis or center of palindrome which has the rightmost boundary character
    int rc = 0; // first longest palindromic sub-string center index in T
    for (int i = 1; i < N; i++) {
      int maxRight = c + radius[c];
      if (i <= maxRight) {
        int mirror = 2 * c - i; // axis-(i-axis)
        radius[i] = Math.min(maxRight - i, radius[mirror]); // reuse
        if (radius[mirror] != maxRight - i) {
          // need not update maxRight, axis, and result
          continue;
        }
      }

      for (int l = i - (1 + radius[i]), r = i + (1 + radius[i]); ; ) {
        if (l < 0 || r == N || (l & 1) == 1 && s.charAt(l / 2) != s.charAt(r / 2)) {
          break;
        }
        radius[i]++;
        l--;
        r++;
      }

      if (i + radius[i] > c + radius[c]) {
        c = i;
      }
      // application: longest palindromic sub-string
      if (radius[i] > radius[rc]) {
        rc = i;
      }
    }
    int f = (rc - radius[rc]) / 2;
    return s.substring(f, f + radius[rc]);
  }
}
