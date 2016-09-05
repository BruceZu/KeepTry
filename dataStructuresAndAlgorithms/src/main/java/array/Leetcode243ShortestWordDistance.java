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

/**
 * 243. Shortest Word Distance
 * Esay
 * <pre>
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
 * You may assume that
 *         word1 does not equal to word2,
 *         word1 and word2 are both in the list.
 *
 * Tags: Array
 *
 * ==================================================================================================
 *
 *   elements maybe duplicated. ["a","a","b","b"], "a","b"
 */
public class Leetcode243ShortestWordDistance {
    // 3ms
    public int shortestDistance1(String[] words, String A, String B) {
        int p1 = -1, p2 = -1; // index for A and B
        int dis = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(A)) {
                p1 = i;
            } else if (words[i].equals(B)) {
                p2 = i;
            } else {
                continue;
            }
            if (p1 != -1 && p2 != -1) {
                dis = dis < Math.abs(p1 - p2) ? dis : Math.abs(p1 - p2);
            }
        }
        return dis;
    }

    /**
     * start from each word1, to find word2 in both forward and backward direction,
     * use min to record the answer.
     * 2ms. depends on leetcode test case.
     */
    public static int shortestDistance(String[] words, String A, String B) {
        int dis = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(A)) {
                int steps = 1;
                while (true) {
                    int l = i - steps, r = i + steps;
                    if (l >= 0 && words[l].equals(B) || r < words.length && words[r].equals(B)) {
                        dis = dis < steps ? dis : steps;
                        break;
                    }
                    steps++;
                }
            }
        }
        return dis;
    }
}
