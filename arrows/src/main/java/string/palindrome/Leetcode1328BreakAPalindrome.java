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

public class Leetcode1328BreakAPalindrome {
  public String breakPalindrome(String palindrome) {
    if (palindrome == null) return palindrome;
    int n = palindrome.length();
    if (n == 0 || n == 1) {
      // can not replace to break palindrome
      return "";
    }

    char[] s = palindrome.toCharArray();
    // palindrome length can be 0, even or odd
    // not palindrome length >=2. even or odd
    // check wing only, not include the center
    // breaking palindrome only can happen on wing
    // if 'a' can replace one char in the wing then select the left most one to make the broken
    // palindrome to be the  smallest possible string
    for (int i = 0; i < n / 2; i++) {
      // palindrome consists of only lowercase English letters.

      if (s[i] > 'a') { // or !=
        s[i] = 'a';
        return new String(s);
      }
    }
    // So wings are all 'a', no matter it is even or odd parlindrome.
    // when it is odd palindrome, the center is 'a' or not.
    // breaking palindrome only can happen on wing.
    // let the right most 'a' to be 'b'.
    s[n - 1] = 'b';
    return new String(s);
  }
}
