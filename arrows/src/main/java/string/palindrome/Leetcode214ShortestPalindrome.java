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

import java.util.Arrays;

/*
Given a string s, you can convert it to a palindrome by adding characters in front of it.
Find and return the shortest palindrome you can find by performing this transformation.

Example 1:
Input: s = "aacecaaa"
Output: "aaacecaaa"

Example 2:
Input: s = "abcd"
Output: "dcbabcd"

Constraints:
    0 <= s.length <= 5 * 104
    s consists of lowercase English letters only.
 */
public class Leetcode214ShortestPalindrome {

  // With Manacher's algorithm idea, O(N) time and space

  /*
     With KMP, O(N) time and space
     Direct applying widest border idea to this scenario does not work
      - 'border' does not apply to palindrome. So need check firstly if s itself
        already is a palindrome.
      - See 'aabba', N=5,
        concatenate it with its reverse: 'aabbaabbaa',
                                    w[]:  0100123456 ,
        w[2*N - 1]==6 6 > N, s.substring(6) will out of index.
  */
  public String shortestPalindrome2(String s) {
    if (s == null || s.length() <= 1) return s;

    int N = s.length();
    char[] I = s.toCharArray();
    int[] w = new int[N];
    for (int d = 0; d < N; d++) {
      int i = d;
      while (0 < i && I[w[i - 1]] != I[d]) i = w[i - 1];
      if (0 < i) w[d] = w[i - 1] + 1;
    }

    char[] S = new StringBuilder(s).reverse().toString().toCharArray();
    int u = 0, d = 0;
    while (true) {
      while (u < N && d < N && S[u] == I[d]) {
        u++;
        d++;
      }
      // if(d == N)..., no happen in this case
      if (u == N) {
        return new StringBuilder(s.substring(d)).reverse().toString() + s;
      }
      if (d == 0) u++;
      else d = w[d - 1];
    }
  }

  // 2 pointers. O(N^2) time, O(N) space
  public String shortestPalindrome3(String s) {
    if (s == null || s.length() <= 1) return s;
    int l = 0;
    for (int r = s.length() - 1; 0 <= r; r--) {
      if (s.charAt(l) == s.charAt(r)) {
        l++;
      }
    }
    // palindrome makes l now be right of the palindrome.
    if (l == s.length()) return s;
    String tail = s.substring(l);
    return new StringBuilder(tail).reverse().toString()
        + shortestPalindrome3(s.substring(0, l))
        + tail;
  }
}
