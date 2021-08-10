//  Copyright 2017 The keepTry Open Source Project
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*  244. Shortest Word Distance II
 Design a data structure that will be initialized with a string array,
 and then it should answer queries of the shortest distance between two
 different strings from the array.

 Implement the WordDistance class:

    WordDistance(String[] wordsDict) initializes the object with the strings array wordsDict.
    int shortest(String word1, String word2) returns the shortest distance between word1
                                             and word2 in the array wordsDict.
 Input
["WordDistance", "shortest", "shortest"]
[[["practice", "makes", "perfect", "coding", "makes"]], ["coding", "practice"], ["makes", "coding"]]
Output
[null, 3, 1]

Constraints:
    1 <= wordsDict.length <= 3 * 104
    1 <= wordsDict[i].length <= 10
    wordsDict[i] consists of lowercase English letters.
    word1 and word2 are in wordsDict.
    word1 != word2
    At most 5000 calls will be made to shortest.

Understanding:
    Note:
      " word1 and word2 are in wordsDict.
        word1 != word2"
      wordsDict may have duplicated words
  O(N) time and space.
   N words in the original list,
   K and L represent the number of occurrences  of the two words.
   K=O(N),L=O(N). Therefore, the overall time complexity would also be O(N)


 */
public class Leetcode244ShortestWordDistanceII {
  class WordDistance1 {
    Map<String, List<Integer>> location = new HashMap();

    public WordDistance1(String[] w) {
      for (int i = 0; i < w.length; i++) {
        location.putIfAbsent(w[i], new ArrayList());
        location.get(w[i]).add(i);
      }
    }

    public int shortest(String word1, String word2) {
      List<Integer> l = location.get(word1);
      List<Integer> r = location.get(word2);

      Integer nl = null, nr = null;
      int i = 0, j = 0;
      int a = 30000;
      while (i < l.size() || j < r.size()) {
        if (a == 1) return 1;
        if (i == l.size()) {
          a = Math.min(a, r.get(j) - nl);
          break;
        }
        if (j == r.size()) {
          a = Math.min(a, l.get(i) - nr);
          break;
        }
        if (l.get(i) < r.get(j)) {
          if (nr != null) a = Math.min(a, l.get(i) - nr);
          nl = l.get(i++);
        } else {
          if (nl != null) a = Math.min(a, r.get(j) - nl);
          nr = r.get(j++);
        }
      }
      return a;
    }
  }
  /*
   Improvement
   (does not change the time and space)
   compare 2 sorted distinct arrays

   case1
       [3   7]
     [1   5   8]
   case2
      [  3  4   7]
     [ 1      5   8]
    case3
      [  3  4   7]
     [ 1      5   8  9]
    case4
      [  3  4   7]
     [ 1           9]
   compared with above solution:
   pros: do not need `Integer nl = null, nr = null;`
         easy control the boundary: i and j.
  */
  class WordDistance {
    HashMap<String, ArrayList<Integer>> location;

    public WordDistance(String[] w) {
      location = new HashMap<>();
      for (int i = 0; i < w.length; i++) {
        location.putIfAbsent(w[i], new ArrayList());
        location.get(w[i]).add(i);
      }
    }

    public int shortest(String word1, String word2) {
      ArrayList<Integer> l = location.get(word1), r = location.get(word2);
      int i = 0, j = 0, a = 30000;
      while (i < l.size() && j < r.size()) {
        a = Math.min(a, Math.abs(l.get(i) - r.get(j)));
        if (l.get(i) < r.get(j)) i++;
        else j++;
      }
      return a;
    }
  }
}
