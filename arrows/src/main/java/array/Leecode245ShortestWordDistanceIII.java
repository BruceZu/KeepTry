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

/**
 * 245. Shortest Word Distance III
 * https://leetcode.com/problems/shortest-word-distance-iii/
 * <pre>
 *     Difficulty: Medium
 * This is a follow up of Shortest Word Distance. The only difference is now word1 could be the same as word2.
 *
 * Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.
 *
 * word1 and word2 may be the same and they represent two individual words in the list.
 *
 * For example,
 * Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
 *
 * Given word1 = “makes”, word2 = “coding”, return 1.
 * Given word1 = "makes", word2 = "makes", return 3.
 *
 * Note:
 * You may assume word1 and word2 are both in the list.
 *
 * Hide Company Tags LinkedIn
 * Hide Tags Array
 * Hide Similar Problems (E) Shortest Word Distance (M) Shortest Word Distance II
 *
 * </pre>
 */
public class Leecode245ShortestWordDistanceIII {
    //
    public int shortestWordDistance(String[] words, String word1, String word2) {
        int result = Integer.MAX_VALUE, indexW1 = -1, indexW2 = -1;

        if (word1.equals(word2)) {
            for (int i = 0; i < words.length; i++) {
                if (words[i].equals(word1)) {
                    if (indexW1 != -1) {
                        result = Math.min(result, i - indexW1);
                    }
                    indexW1 = i;
                }
            }
        } else {
            for (int i = 0; i < words.length; i++) {
                if (words[i].equals(word1)) {
                    indexW1 = i;
                } else if (words[i].equals(word2)) {
                    indexW2 = i;
                }

                if (indexW1 != -1 && indexW2 != -1) {
                    result = Math.min(result, Math.abs(indexW1 - indexW2));
                }
            }
        }
        return result;
    }


    // same idea. just save a parameter
    public int shortestWordDistance2(String[] words, String word1, String word2) {
        int min = Integer.MAX_VALUE;
        int indexW1or2 = -1;
        if (word1.equals(word2)) {//if work for shortest word distance, only using the code of if clause
            for (int i = 0; i < words.length; i++) {
                if (words[i].equals(word1)) {
                    if (indexW1or2 != -1)
                        min = Math.min(min, i - indexW1or2);
                    indexW1or2 = i;
                }
            }
        } else {//deal with the cases that are the same parameters
            for (int i = 0; i < words.length; i++) {
                String curW = words[i];
                if (curW.equals(word1) || curW.equals(word2)) {
                    if (indexW1or2 != -1 && !curW.equals(words[indexW1or2]))
                        min = Math.min(min, i - indexW1or2);
                    indexW1or2 = i;
                }
            }
        }
        return min;
    }
}
