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
public class LC245ShortestWordDistanceIII {


    /**
     * the fast one beat 99%  no shared code
     */

    /**
     * beat 85%
     */
    public int shortestWordDistance(String[] words, String word1, String word2) {
        int res = Integer.MAX_VALUE, m = -1, n = -1;

        if (word1.equals(word2)) {
            for (int i = 0; i < words.length; i++) {
                if (words[i].equals(word1)) {

                    if (m == -1) {
                        m = i;
                    } else {
                        res = Math.min(res, i - m);
                        m = i;
                    }

                }
            }
        } else {
            for (int i = 0; i < words.length; i++) {
                if (words[i].equals(word1)) {
                    m = i;
                    if (m != -1 && n != -1) {
                        res = Math.min(res, Math.abs(m - n));
                    }
                }

                if (words[i].equals(word2)) {
                    n = i;
                    if (m != -1 && n != -1) {
                        res = Math.min(res, Math.abs(m - n));
                    }
                }
            }
        }
        return res;

    }

    /**
     * same speed
     */
    public int shortestWordDistance2(String[] words, String word1, String word2) {
        int len = words.length, min = Integer.MAX_VALUE;

        if (!word1.equals(word2)) {//if work for shortest word distance, only using the code of if clause
            int index = -1;
            for (int i = 0; i < len; i++) {
                if (words[i].equals(word1) || words[i].equals(word2)) {
                    if (index != -1 && !words[i].equals(words[index]))
                        min = Math.min(min, i - index);
                    index = i;
                }
            }
        } else {//deal with the cases that are the same parameters
            int index = -1;
            for (int i = 0; i < len; i++) {
                if (words[i].equals(word1)) {
                    if (index != -1)
                        min = Math.min(min, i - index);
                    index = i;
                }
            }
        }
        return min;
    }
    /**
     * other idea
     * In this post, Stefan's solutions are very concise. However,
     * I found that the following solution only runs 280ms on OJ,
     * which beats all other java solutions so far. It is fast because
     * it only checks word1.equals(word2) once at the beginning.
     * I wonder in real practice, do we prefer cleaner code or faster solution
     * if we cannot achieve both with one solution?
     * I hope people who have more industrial experience could give some adivce.
     */
}
