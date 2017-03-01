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

package graph.graph_directed_topological_sort;

import java.util.*;

/**
 * Difficulty: Hard
 * There is a new alien language which uses the latin alphabet.
 * However, the order among letters are unknown to you.
 * You receive a list of words from the dictionary, where words are <strong>sorted lexicographically</strong>
 * by the rules of this new language.
 * Derive the order of letters in this language.
 * a
 * For example,
 * Given the following words in dictionary,
 * <pre>
 * [
 *  "wrt",
 *  "wrf",
 *  "er",
 *  "ett",
 *  "rftt"
 * ]
 * The correct order is: "wertf".
 *
 * Note:
 *      You may assume all letters are in lowercase.
 *      If the order is invalid, return an empty string.  --> circle, or may not be not in lexicographically sorted order
 *      here may be multiple valid order of letters, return any one of them is fine. --> more 'next'
 *
 * Hide Company Tags: Google Airbnb Facebook Twitter Snapchat Pocket Gems
 * Hide Tags: Graph, Topological Sort
 * Hide Similar Problems (M) Course Schedule II
 */
public class Leetcode269AlienDictionary {

    /**
     * Use boolean[from][to] edge and  int[] status present graph
     *
     * @param words
     * @param edge           [from][to]
     * @param isExistInWords
     */
    public static void buildGraph(String[] words, boolean[][] edge, boolean[] isExistInWords) {

        for (int i = 0; i < words.length; i++) {
            for (char c : words[i].toCharArray()) {
                isExistInWords[c - 'a'] = true;
            }
            if (i > 0) {// start from second word
                String preW = words[i - 1], curW = words[i];
                // 2  validate input
                if (curW.length() < preW.length() && preW.startsWith(curW)) {
                    throw new InputMismatchException("Avoid \"lexicographically\" order");
                }
                // 3  find at most one edge per word
                int len = Math.min(preW.length(), curW.length());
                for (int j = 0; j < len; j++) {
                    char preC = preW.charAt(j), curC = curW.charAt(j);
                    if (preC != curC) {
                        edge[preC - 'a'][curC - 'a'] = true;
                        break;
                    }
                }
            }
        }
    }

    public static String alienOrder(String[] words) {
        boolean[][] edges = new boolean[26][26];
        boolean[] isExistInWords = new boolean[26];
        try {
            buildGraph(words, edges, isExistInWords);
        } catch (InputMismatchException e) {
            return "";
        }

        //todo topological sort
        return "";
    }
}
