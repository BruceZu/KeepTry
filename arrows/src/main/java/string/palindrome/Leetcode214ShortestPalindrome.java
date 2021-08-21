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

/*
214. Shortest Palindrome
Given a string s, you can convert it to a palindrome by adding characters in front of it.
Find and return the shortest palindrome you can find by performing this transformation.

Example 1:
Input: s = "aacecaaa"
Output:   "aaacecaaa"

Example 2:
Input: s = "abcd"
Output: "dcbabcd"

Constraints:
    0 <= s.length <= 5 * 10^4
    s consists of lowercase English letters only.
 */
public class Leetcode214ShortestPalindrome {
  /* Idea 2 pointers ----------------------------------------------------------------
   O(N^2) time and O(N) space
    palindrome behavior:
    l             l
    aba   =>   aba
      r        r

    no palindrome:
    l                 l      l           l     l         l
    abaxaybz =>  abaxaybz => abaxa => abaxa => aba => aba
           r     r               r    r          r    r
    get the width of the max palindrome substring starting from index 0.

    result = zby|  ax| aba |xa  |ybz

    Steps:
      l: the width of the max palindrome starting from index 0 of given string
         its possible value is [1, N].
     - If at last, the l is on the N, then s is a palindrome, each move of r will make l move a step too
     - Else current s is cut into [0,l-1],[l,N-1] 2 parts and
       let [0,l-1] go on with recursion. [0,l-1]  is shorter than s
       this guarantee the recursion will reach an end at last.
  */
  public String shortestPalindrome7(String s) {
    if (s == null || s.length() <= 1) return s;
    int N = s.length(), l = 0;
    for (int r = N - 1; 0 <= r; r--) if (s.charAt(l) == s.charAt(r)) l++;
    if (l == N) return s;
    String tail = s.substring(l);
    return new StringBuilder(tail).reverse() + shortestPalindrome7(s.substring(0, l)) + tail;
  }
  /*
  Idea Manacher's algorithm  ------------------------------------------------------------------
   O(N) time and space
   */
  public static String shortestPalindrome6(String s) {
    if (s == null || s.length() <= 1) return s;
    int[] v = Manacher.getRadiusOfVirtualTranslatedStringOf(s);

    // '0 <= s.length <= 5 * 10^4'. So use int is enough
    int N = 2 * s.length() + 1;
    int w = 1; // at least 1: the palindromic sub-string with the first char
    for (int i = N - 1; i >= 0; i--) {
      if ((i - v[i]) / 2 == 0) {
        w = Math.max(w, v[i]);
        break;
      }
    }
    return new StringBuilder(s.substring(w)).reverse() + s;
  }

  /* --------------------------------------------------------------------------
  Idea:
  widest prefix and suffix of  s + '#' + reversed s'
  - without # it does not work, e.g. 'aaaa' => 'aaaaaaaa', widest prefix and suffix is 8 not 4
  - this idea apply to KMP
  O(N) time and space
  */
  public String shortestPalindrome5(String s) {
    if (s == null || s.length() <= 1) return s;
    String tmp = s + "#" + new StringBuilder(s).reverse();
    char[] a = tmp.toCharArray();
    int N = tmp.length();

    int w[] = new int[N]; // the width of the widest prefix and suffix of string [0,i]
    for (int i = 0; i < N; i++) {
      int j = i;
      while (j > 0 && a[w[j - 1]] != a[i]) j = w[j - 1];
      if (j > 0) w[i] = w[j - 1] + 1;
      // in Java, default w[i] is 0;
    }
    return new StringBuilder(s.substring(w[N - 1])).reverse() + s;
  }

  /* --------------------------------------------------------------------------
  Idea:
   KMP(Knuth–Morris–Pratt)
   Find start index of s in its reverse
   find `abaxyz` from `zyxaba` or find `aba` from `aba`
  O(N) time and space
  */
  public String shortestPalindrome4(String s) {
    if (s == null || s.length() <= 1) return s;
    int N = s.length();
    char[] I = s.toCharArray();
    int[] w = new int[N];
    for (int i = 0; i < N; i++) {
      int j = i;
      while (0 < j && I[w[j - 1]] != I[i]) j = w[j - 1];
      if (0 < j) w[i] = w[j - 1] + 1;
    }

    char[] S = new StringBuilder(s).reverse().toString().toCharArray();
    int u = 0, d = 0;
    while (true) {
      while (u < N && d < N && S[u] == I[d]) {
        u++;
        d++;
      }
      // d==N when s is palindrome
      if (d == N || u == N) return new StringBuilder(s.substring(d)).reverse() + s;
      if (d == 0) u++;
      else d = w[d - 1];
    }
  }

  /* Idea hash ----------------------------------------------------------------
  O(N) time
  O(N) space, not take in account the substring comparing used to avoid hash collision

  base is 26
  modulus: Mersenne prime 8191
  em: the value of b^{j}%m, initial value is 1 used by the right most char,
      keep updating it used for next char in extending hash from right to left
  max: keep tracking the last char index of the longest palindrome starting from str[0]
  */
  public static String shortestPalindrome3(String str) {
    if (str.length() <= 1) return str;
    char[] s = str.toCharArray(), s2 = new StringBuilder(str).reverse().toString().toCharArray();
    int N = str.length(), hl = 0, hr = 0, b = 26, m = 8191, em = 1;
    int max = 0;
    for (int i = 0; i < N; i++) {
      int cl = s[i] - 'a';
      hl = (hl * b % m + cl) % m;

      int cr = s2[N - 1 - i] - 'a';
      hr = (cr * em % m + hr) % m;
      em = em * b % m; // used by next cr

      if (hl == hr) { // double check by compare 2 sub-string values
        boolean same = true;
        for (int x = 0; x <= i; x++) {
          if (s[x] != s2[N - 1 - (i - x)]) { // Note: it is N - 1 - i + x here.
            same = false;
            break;
          }
        }
        if (same) max = i;
      }
    }
    return new StringBuilder(str.substring(max + 1)).reverse().append(str).toString();
  }

  /*
  Idea Hash  ------------------------------------------------------------------
  without tmp char array, make space O(1)
  Improvement of above solution by observation
  - the `cl` is same as `cr`
  - when hl==hr, if 2 substrings are same, plus one is the other reverse version,
        then substring[0, i] SHOULD be a palindrome, so just check
        from 0 and i toward centering side.

  O(N) time
  O(1) space, not take in account the substring comparing used to avoid hash collision
   */
  public static String shortestPalindrome2(String s) {
    if (s.length() <= 1) return s;
    int N = s.length(), hl = 0, hr = 0, b = 26, m = 8191, em = 1;
    int max = 0;
    for (int i = 0; i < N; i++) {
      int c = s.charAt(i) - 'a';
      hl = hl * b % m + c;

      hr = (c * em % m + hr) % m;
      em = em * b % m;
      if (hl == hr) {
        int l = 0, r = i;
        while (l <= r && s.charAt(l++) == s.charAt(r--))
          ;
        if (l > r) max = i;
      }
    }
    return new StringBuilder(s.substring(max + 1)).reverse().append(s).toString();
  }

  /*
  Idea Hash  ------------------------------------------------------------------

   Same as JDK String.hashcode()
   base: 31
   modulus: Integer.MAX_VALUE+1(it is Integer.MIN_VALUE actually)

  O(N) time
  O(1) space, not take in account the substring comparing used to avoid hash collision
   */
  public static String shortestPalindrome(String s) {
    if (s.length() <= 1) return s;
    int N = s.length(), hl = 0, hr = 0, b = 31, em = 1, max = 0;
    for (int i = 0; i < N; i++) {
      int c = s.charAt(i);
      hl = hl * b + c;

      hr = c * em + hr;
      em = b * em;
      if (hl == hr) {
        int l = 0, r = i;
        while (l <= r && s.charAt(l++) == s.charAt(r--))
          ;
        if (l > r) max = i;
      }
    }
    return new StringBuilder(s.substring(max + 1)).reverse().append(s).toString();
  }
  /*
  Idea(TODO)
   1 Fast Fourier Transform,Fourier transform coefficients.
      https://hackmd.io/@firejox/HkBxRYbrN?type=view
   2 BoyerMoore can be used here?
  */
}
