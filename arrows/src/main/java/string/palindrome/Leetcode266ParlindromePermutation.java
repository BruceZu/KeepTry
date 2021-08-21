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

import java.util.HashSet;
import java.util.Set;

/*
266. Palindrome Permutation

   Given a string, determine if a permutation of the string could form a palindrome.

    "code" -> False,
    "aab" -> True,
    "carerac" -> True.

    1 <= s.length <= 5000
    s consists of only lowercase English letters.

 Understanding:
   So no supplementary characters
 O(N) time, O(1) space
 */
public class Leetcode266ParlindromePermutation {
  public boolean canPermutePalindrome3(String s) {
    char[] a = s.toCharArray();
    int[] f = new int[256]; // Frequency number is even or odd

    // index scope of times
    int l = a[0];
    int r = a[0];

    for (char c : a) {
      f[c] ^= 1;
      l = Math.min(l, c);
      r = Math.max(r, c);
    }
    int sum = 0;
    for (int i = l; i <= r; i++) sum += f[i];
    return (sum <= 1);
  }

  public boolean canPermutePalindrome2(String s) {
    char[] a = s.toCharArray();
    int oddCount = 0;
    int[] f = new int[128];
    for (int i = 0; i < a.length; i++) {
      f[a[i]]++;
      oddCount = (f[a[i]] % 2 == 0) ? oddCount - 1 : oddCount + 1;
    }
    return oddCount <= 1;
  }

  public boolean canPermutePalindrome(String s) {
    char[] a = s.toCharArray();
    Set<Character> set = new HashSet();

    for (int i = 0; i < a.length; i++) {
      char c = a[i];
      if (set.contains(c)) {
        set.remove(c);
      } else {
        set.add(c);
      }
    }
    return set.size() <= 1;
  }
}
