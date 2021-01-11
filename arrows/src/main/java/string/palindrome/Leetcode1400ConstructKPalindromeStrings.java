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
  public boolean canConstruct(String s, int k) {
    if (s == null) return false;
    if (s.length() == 0) {
      if (k == 0) return true;
      else return false;
    }
    if (k == 0) return false;

    if (k > s.length()) return false;

    int c = 0;
    for (int i = 0; i < s.length(); i++) {
      // All characters in s are lower-case English letters.
      c ^= 1 << s.charAt(i) - 'a';
    }
    int sumOdd = 0;
    while (c != 0) {
      c &= (c - 1);
      sumOdd++;
    }

    if (k < sumOdd) return false;
    return true;
  }
}
