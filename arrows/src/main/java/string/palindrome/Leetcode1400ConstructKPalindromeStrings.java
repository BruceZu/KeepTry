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

public class Leetcode1400ConstructKPalindromeStrings {
  /*
    Given a string s and an integer k.
    construct k non-empty palindrome strings using all the characters in s.

    Return True if you can use all the characters in s to construct k palindrome strings
    or False otherwise.

  Input: s = "annabelle", k = 2
  Output: true
  Explanation: can construct two palindromes using all characters in s.
  Some possible constructions
  "anna" + "elble",
  "anbna" + "elle",
  "anellena" + "b"

  Input: s = "leetcode", k = 3
  Output: false
  Explanation: impossible to construct 3 palindromes using all the characters of s.

  Input: s = "true", k = 4
  Output: true
  Explanation: The only possible solution is to put each character in a separate string.

  Input: s = "yzyzyzyzyzyzyzy", k = 2
  Output: true
  Explanation: Simply you can put all z's in one string and all y's in the other string.
  Both strings will be palindrome.

  Input: s = "cr", k = 7
  Output: false
  Explanation:  don't have enough characters in s to construct 7 palindromes.


      1 <= s.length <= 10^5
      All characters in s are lower-case English letters.
      1 <= k <= 10^5

     */
  public boolean canConstruct(String s, int k) {
    int c = 0;
    for (int i = 0; i < s.length(); i++) c ^= 1 << s.charAt(i) - 'a';
    // how many 1 bit in the number c
    int sum = 0;
    while (c != 0) {
      c &= (c - 1);
      sum++;
    }
    return sum <= k && k <= s.length();
  }
}
