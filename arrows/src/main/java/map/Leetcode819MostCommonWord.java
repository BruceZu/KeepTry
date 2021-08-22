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

package map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Leetcode819MostCommonWord {
  /*
      819. Most Common Word

      a string paragraph and a string array of the banned words banned,
      return the most frequent word that is not banned.
      It is guaranteed
        - there is at least one word that is not banned,
        - that the answer is unique.

     The words in paragraph are case-insensitive and
     the answer should be returned in lowercase.


  Input: paragraph = "Bob hit a ball, the hit BALL flew far after it was hit.", banned = ["hit"]
  Output: "ball"
  Explanation:
  "hit" occurs 3 times, but it is a banned word.
  "ball" occurs twice (and no other word does), so it is the most frequent non-banned word in the paragraph.
  Note that words in the paragraph are not case sensitive,
  that punctuation is ignored (even if adjacent to words, such as "ball,"),
  and that "hit" isn't the answer even though it occurs more because it is banned.


  Input: paragraph = "a.", banned = []
  Output: "a"

  Constraints:

      1 <= paragraph.length <= 1000
      paragraph consists of English letters, space ' ', or one of the symbols: "!?',;.".
      0 <= banned.length <= 100
      1 <= banned[i].length <= 10
      banned[i] consists of only lowercase English letters.

   */

  /*
  Understanding
   - all of them are not letter
    String str = "!?',;.";
    for (int i = 0; i < str.length(); i++) {
      if (Character.isLetter((str.charAt(i)))) {
        System.out.println(str.charAt(i)); // no print out
      }
    }
  - Note: paragraph not all in lower cass letter.
  O(M+N) time and space

  */
  public String mostCommonWord(String paragraph, String[] banned) {
    Set<String> ban = new HashSet();
    for (String word : banned) ban.add(word);

    String r = "";
    int maxf = 0;
    Map<String, Integer> map = new HashMap();
    StringBuilder tmp = new StringBuilder();

    char[] a = paragraph.toCharArray();
    for (int i = 0; i < a.length; i++) {
      char currChar = a[i];

      if (Character.isLetter(currChar)) {
        tmp.append(Character.toLowerCase(currChar));
        if (i != a.length - 1) continue;
      }

      if (tmp.length() > 0) {
        String w = tmp.toString();
        if (!ban.contains(w)) {
          int f = map.getOrDefault(w, 0) + 1;
          map.put(w, f);
          if (f > maxf) {
            r = w;
            maxf = f;
          }
        }
        tmp = new StringBuilder();
      }
    }
    return r;
  }

  public static void main(String[] args) {
    String str = "!?',;."; // all of them are not letter
    for (int i = 0; i < str.length(); i++) {
      if (Character.isLetter((str.charAt(i)))) {
        System.out.println(str.charAt(i));
      }
    }
  }
}
