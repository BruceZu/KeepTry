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

/**
 * 244. Shortest Word Distance II
 * https://leetcode.com/problems/shortest-word-distance-ii/
 * <pre>
 * Difficulty: Medium
 * This is a follow up of Shortest Word Distance.
 * The only difference is now you are given the list of words and your method will be called repeatedly
 * many times with different parameters. How would you optimize it?
 *
 * Design a class which receives a list of words in the constructor,
 * and implements a method that takes two words word1 and word2 and return the shortest
 * distance between these two words in the list.
 *
 * For example,
 * Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
 *
 * Given word1 = “coding”, word2 = “practice”, return 3.
 * Given word1 = "makes", word2 = "coding", return 1.
 *
 * Note:
 * You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
 *
 * Hide Company Tags: LinkedIn
 * Hide Tags: Hash Table, Design
 * Hide Similar Problems: (E) Merge Two Sorted Lists (E) Shortest Word Distance (M) Shortest Word Distance III
 */
public class Leetcode244ShortestWordDistanceII {
  // Your WordDistance object will be instantiated and called as such:
  // WordDistance wordDistance = new WordDistance(words);
  // wordDistance.shortest("word1", "word2");
  // wordDistance.shortest("anotherWord1", "anotherWord2");
  class WordDistance {

    private HashMap<String, ArrayList<Integer>> indexexOf;

    // unique words  -> locations
    public WordDistance(String[] words) {
      indexexOf = new HashMap<>();
      for (int i = 0; i < words.length; i++) {
        if (!indexexOf.containsKey(words[i])) {
          ArrayList<Integer> indexes = new ArrayList<>();
          indexes.add(i);
          indexexOf.put(words[i], indexes);
        } else indexexOf.get(words[i]).add(i);
      }
    }

    public int shortest(String word1, String word2) {
      ArrayList<Integer> sortedIndexesA = indexexOf.get(word1);
      ArrayList<Integer> sortedIndexesB = indexexOf.get(word2);

      int shortest = Integer.MAX_VALUE;
      int iiA = 0, iiB = 0;
      while (iiA < sortedIndexesA.size() && iiB < sortedIndexesB.size()) {
        if (shortest == 1) return shortest;
        int indexA = sortedIndexesA.get(iiA);
        int indexB = sortedIndexesB.get(iiB);
        if (indexA < indexB) {
          shortest = indexB - indexA < shortest ? indexB - indexA : shortest;
          ++iiA;
        } else { // > or ==
          shortest = indexA - indexB < shortest ? indexA - indexB : shortest;
          ++iiB;
        }
      }
      return shortest;
    }
  }
}
