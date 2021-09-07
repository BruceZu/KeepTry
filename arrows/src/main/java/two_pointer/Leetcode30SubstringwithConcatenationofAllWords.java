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

package two_pointer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leetcode30SubstringwithConcatenationofAllWords {
  /*
   30. Substring with Concatenation of All Words

   You are given a string s and an array of strings words
   of the same length. Return all starting indices of substring(s)
   in s that is a concatenation of each word in words exactly once,
   in any order, and without any intervening characters.

   You can return the answer in any order.

   Input: s = "barfoothefoobarman", words = ["foo","bar"]
   Output: [0,9]
   Explanation: Substrings starting at index 0 and 9 are "barfoo" and "foobar" respectively.
   The output order does not matter, returning [9,0] is fine too.

   Input: s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
   Output: []

   Input: s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
   Output: [6,9,12]

   Constraints:

       1 <= s.length <= 104
       s consists of lower-case English letters.
       1 <= words.length <= 5000
       1 <= words[i].length <= 30
       words[i] consists of lower-case English letters.
  */
  /*
    Watch: 'concatenation of each word in words exactly once,
            in any order, and without any intervening characters.'
            'strings words of the same length'
     Input: s = "barfoothefoobarman", words = ["foo","bar"]  Output: [0,9]
     Input: s = "xbarfoothefoobarman", words = ["foo","bar"]  Output: [1,10]
     Input: s = "xxbarfoothefoobarman", words = ["foo","bar"]  Output: [2,11]
     Input: s = "xxxbarfoothefoobarman", words = ["foo","bar"]  Output: [3,12]
   Idea:
     - start from 0~L-1
     - take the word in words, not char, as the smallest unit.
     - for each outer loop, convert words to map<w:frequency>, `R= ws.length;`  is left required word number.
       R and map will be dirty and need re-initial.
     - each step of index l and r of window is a word width
     - The sliding window is fixed length L*N

   O(M*L) time,
   O(N*L) space,
   M is s.length,
   N is ws[0] length, also is required left number of words
   L is ws.length,
  */
  public List<Integer> findSubstring(String S, String[] ws) {
    List<Integer> a = new ArrayList();
    for (int i = 0; i < ws[0].length(); i++) generalSolution(S, ws, i, a);
    return a;
  }

  private void generalSolution(String S, String[] ws, int from, List<Integer> a) {
    int L = ws[0].length(), N = ws.length, R = N;
    Map<String, Integer> m = new HashMap();
    for (String w : ws) m.put(w, m.getOrDefault(w, 0) + 1);

    int l = from, r = from;
    while (r <= S.length() - L) {
      String w = S.substring(r, r + L);
      if (m.containsKey(w)) {
        if (m.get(w) >= 1) R--;
        m.put(w, m.get(w) - 1);
      }
      r = r + L;
      if (r - l == L * N) {
        if (R == 0) a.add(l);
        w = S.substring(l, l + L);
        if (m.containsKey(w)) {
          if (m.get(w) >= 0) R++;
          m.put(w, m.get(w) + 1);
        }
        l = l + L;
      }
    }
  }
}
