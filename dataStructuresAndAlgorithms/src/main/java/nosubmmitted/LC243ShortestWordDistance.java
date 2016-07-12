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

package nosubmmitted;

/**
 * 243. Shortest Word Distance
 * https://leetcode.com/problems/shortest-word-distance/
 * <p/>
 * <pre>
 *     Difficulty: Easy
 * Given a list of words and two words word1 and word2, return the shortest distance between
 * these two words in the list.
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
 * Hide Tags: Array
 * Hide Similar Problems:
 * (M) Shortest Word Distance II
 * (M) Shortest Word Distance III
 *
 * </pre>
 */
public class LC243ShortestWordDistance {

    // the fast beat 98% no shared code

    /**
     * beat 61.45
     *
     * @param words
     * @param word1
     * @param word2
     * @return I inital the index1 and index2 as words.length which makes
     * the Math.abs(index1-index2) will be 2 times of words.length.
     * No need to initial it as Integer.MAXVALUE or Integer.MAXVALUE/2 .
     * AC O(n) Java Solution, index initial to words.length
     */
    public int shortestDistance(String[] words, String word1, String word2) {
        int index1 = words.length, index2 = -words.length, minLen = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word1)) {
                index1 = i;
                minLen = Math.min(Math.abs(index1 - index2), minLen);
            } else if (words[i].equals(word2)) {
                index2 = i;
                minLen = Math.min(Math.abs(index1 - index2), minLen);
            }
        }
        return minLen;
    }

    /**
     * save speed as above
     */
    public int shortestDistance2(String[] words, String word1, String word2) {
        int p1 = words.length, p2 = -words.length;
        int dis = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word1)) {
                p1 = i;
            } else if (words[i].equals(word2)) {
                p2 = i;
            } else {
                continue;
            }
            dis = Math.min(dis, Math.abs(p1 - p2));
        }
        return dis;
    }
}
