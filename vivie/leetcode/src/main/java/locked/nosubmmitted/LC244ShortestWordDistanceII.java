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

package locked.nosubmmitted;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 244. Shortest Word Distance II
 * https://leetcode.com/problems/shortest-word-distance-ii/
 * <p/>
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
 *
 * </pre>
 */
public class LC244ShortestWordDistanceII {
    // Your WordDistance object will be instantiated and called as such:
    // WordDistance wordDistance = new WordDistance(words);
    // wordDistance.shortest("word1", "word2");
    // wordDistance.shortest("anotherWord1", "anotherWord2");
    class WordDistance {

        /**
         * the fast one beat 99%
         */

        private HashMap<String, ArrayList<Integer>> map;

        /**
         * beat 97.07
         *
         * @param words The idea is to store unique words into a HashMap,
         *              where the value of the HashMap is a list of locations of all of the same words.
         */
        public WordDistance(String[] words) {
            map = new HashMap<String, ArrayList<Integer>>();
            for (int i = 0, L = words.length; i < L; i++) {
                if (!map.containsKey(words[i])) {
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(i);
                    map.put(words[i], list);
                } else
                    map.get(words[i]).add(i);
            }
        }

        public int shortest(String word1, String word2) {
            ArrayList<Integer> list1 = map.get(word1);
            ArrayList<Integer> list2 = map.get(word2);
            int size1 = list1.size();
            int size2 = list2.size();
            int shortest = 0x7fffffff;
            int i = 0, j = 0;
            while (i < size1 && j < size2) {
                if (shortest == 1) return shortest;
                int w1 = list1.get(i);
                int w2 = list2.get(j);
                if (w1 < w2) {
                    shortest = w2 - w1 < shortest ? w2 - w1 : shortest;
                    ++i;
                } else {
                    shortest = w1 - w2 < shortest ? w1 - w2 : shortest;
                    ++j;
                }
            }
            return shortest;
        }
    }
}
