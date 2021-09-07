//  Copyright 2016 The Sawdust Open Source Project
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

package array;

/*
243. Shortest Word Distance


Given an array of strings wordsDict and two different
strings that already exist in the array word1 and word2,
return the shortest distance between these two words in the list.


Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "coding", word2 = "practice"
Output: 3

Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "makes", word2 = "coding"
Output: 1

Constraints:
    1 <= wordsDict.length <= 3 * 104
    1 <= wordsDict[i].length <= 10
    wordsDict[i] consists of lowercase English letters.
    word1 and word2 are in wordsDict.
    word1 != word2
 */
/*
Understanding:
  note the constraints
 */
public class Leetcode243ShortestWordDistance {
  /*
  O(N) time, not take in account the time used by String.equals() on word1 and word2
  O(1) space
  */
  public int shortestDistance(String[] w, String word1, String word2) {
    int i = -1, j = -1, r = w.length;
    for (int x = 0; x < w.length; x++) {
      if (w[x].equals(word1)) {
        i = x;
        if (j != -1) r = Math.min(r, i - j);
      } else if (w[x].equals(word2)) {
        j = x;
        if (i != -1) r = Math.min(r, j - i);
      }
    }
    return r;
  }
}
