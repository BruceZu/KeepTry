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

package map;

public class Leetcode1160FindWordsThatCanBeFormedbyCharacters {
  /*
  Leetcode 1160. Find Words That Can Be Formed by Characters

  You are given an array of strings words and a string chars.
  A string is good if it can be formed by characters from chars
  (each character can only be used once).

  Return the sum of lengths of all good strings in words.


  Input: words = ["cat","bt","hat","tree"], chars = "atach"
  Output: 6
  Explanation: The strings that can be formed are "cat" and "hat" so the answer is 3 + 3 = 6.

  Input: words = ["hello","world","leetcode"], chars = "welldonehoneyr"
  Output: 10
  Explanation: The strings that can be formed are "hello" and "world" so the answer is 5 + 5 = 10.


  Constraints:

  1 <= words.length <= 1000
  1 <= words[i].length, chars.length <= 100
  words[i] and chars consist of lowercase English letters.
  */
  public int countCharacters(String[] words, String chars) {
    int[] cnt = new int[26];
    int ans = 0;
    for (char c : chars.toCharArray()) ++cnt[c - 'a'];
    for (String w : words) {
      int[] cnt_ = new int[26];
      boolean match = true;
      for (char c : w.toCharArray()) {
        if (++cnt_[c - 'a'] > cnt[c - 'a']) {
          match = false;
          break;
        }
      }
      if (match) ans += w.length();
    }
    return ans;
  }
}
