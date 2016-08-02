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

package bfs;

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * 127. Word Ladder
 * Difficulty: Medium
 * Given two words (beginWord and endWord), and a dictionary's word list,
 * find the length of shortest transformation sequence from beginWord to endWord,
 * such that:
 *
 * Only one letter can be changed at a time
 * Each intermediate word must exist in the word list
 * For example,
 *
 * Given:
 *      beginWord = "hit"
 *      endWord = "cog"
 *      wordList = ["hot","dot","dog","lot","log"]
 *
 * As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 * return its length 5.
 *
 * <strong>Note</strong>
 *      Return 0 if there is no such transformation sequence.
 *      All words have the same length.
 *      All words contain only lowercase alphabetic characters.
 *
 * Company Tags Amazon LinkedIn Snapchat Facebook Yelp
 * ===========================================================================
 *
 *  Firstly, backtrace idea. too slow.
 *  Secondly, like BSF in binary tree. Remove begin and end word from directory at first.
 *  compare each current words, start from begin word, with end word if match done;
 *  from the directory remove all next words to which the current words can transform and keep them in an array.
 *  then take these next words as current words and iteratively go till no words can picked out from directory,
 *  that is no found.
 *  as following:
 *      need not keep str1a in pool when second step is str1b;
 *      need not keep str2a in pool when second step is str1b;
 *
 *  It is better but still slow.
 *
 *        diff one char
 *    |------|---------|
 *
 *                   str2a
 *                /  str2b
 *          str1a -  str2c
 *
 *          str1b
 *   begin  str1c               ...      end
 *          str1d
 *                            |__left__|
 *
 *          |_______original pool______|
 *
 *   Thirdly let them walk together towards middle; it is accepted somtimes, but still slow;
 *           further see {@link Leetcode127WordLadder2}
 */
public class Leetcode127WordLadder {
    // only one char is diff
    public static boolean canGo(char[] e, String last) {
        int diff = 0;

        for (int i = 0; i < e.length; i++) {
            if (e[i] != last.charAt(i)) {
                diff++;
                if (diff == 2) {
                    return false;
                }
            }
        }
        return diff == 1;
    }

    public static int ladderLength(String start, String end, Set<String> pool) {

        Set<String> starts = new HashSet<>(pool.size());
        Set<String> aims = new HashSet<>(pool.size());

        pool.remove(start);
        starts.add(start);

        pool.remove(end);
        aims.add(end);

        int num = 1;
        while (!starts.isEmpty()) {
            Set<String> nexts = new HashSet<>();
            for (String cur : starts) {
                char[] curs = cur.toCharArray();
                for (String aim : aims) {
                    if (canGo(curs, aim)) {
                        num++;
                        return num;
                    }
                }

                for (String e : pool) {
                    if (canGo(curs, e)) {
                        nexts.add(e);
                    }
                }
                pool.removeAll(nexts); // remove it ASAP
            }

            if (nexts.isEmpty()) {
                return 0;
            }
            if (nexts.size() < aims.size()) {
                starts = nexts;
            } else {
                starts = aims;
                aims = nexts;
            }
            num++;
        }
        return 0;
    }
}
