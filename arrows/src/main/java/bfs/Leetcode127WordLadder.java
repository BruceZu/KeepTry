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

import java.util.*;

/*

Leetcode 127. Word Ladder

A transformation sequence from word beginWord to word endWord using a dictionary wordList is
a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:

Every adjacent pair of words differs by a single letter.
Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
sk == endWord
Given two words, beginWord and endWord, and a dictionary wordList, return the number of
words in the shortest transformation sequence from beginWord to endWord, or 0 if no such sequence exists.

Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
Output: 5
Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot" -> "dog" -> cog", which is 5 words long.


Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
Output: 0
Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.


Constraints:

1 <= beginWord.length <= 10
endWord.length == beginWord.length
1 <= wordList.length <= 5000
wordList[i].length == beginWord.length
beginWord, endWord, and wordList[i] consist of lowercase English letters.
beginWord != endWord
All the words in wordList are unique.
 */

/*
walk together towards middle; it is accepted somtimes
*/
public class Leetcode127WordLadder {
  // only one char is diff
  public static boolean diff1(char[] arr, String str) {
    int diff = 0;
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] != str.charAt(i)) {
        diff++;
        if (diff == 2) {
          return false;
        }
      }
    }
    return diff == 1;
  }

  public static int ladderLength__(String start, String end, List<String> wordList) {
    Set<String> D = new HashSet<>(wordList);
    Set<String> F = new HashSet<>(D.size()); // left boundary
    Set<String> T = new HashSet<>(D.size()); // right  boundary

    if (!D.contains(end)) return 0;

    D.remove(start); // visited
    F.add(start);

    D.remove(end); // visited
    T.add(end);

    int steps = 1;
    while (!F.isEmpty()) {
      Set<String> layer = new HashSet<>(); // next layer
      for (String cur : F) {
        char[] curarr = cur.toCharArray();
        for (String aim : T) {
          if (diff1(curarr, aim)) {
            return steps + 1;
          }
        }

        for (String e : D) {
          if (diff1(curarr, e)) {
            layer.add(e);
          }
        }
        D.removeAll(layer); // remove it ASAP, because find one answer is enough
      }

      if (layer.isEmpty()) {
        return 0;
      }
      if (layer.size() < T.size()) {
        F = layer;
      } else {
        F = T;
        T = layer;
      }
      steps++;
    }
    return 0;
  }

  public int ladderLength_(String beginWord, String endWord, Set<String> wordlist) {
    Set<String> D = new HashSet<>(wordlist);
    if (!D.contains(endWord)) return 0;

    Set<String> F = new HashSet<>(), T = new HashSet<>();

    F.add(beginWord);
    T.add(endWord);

    HashSet<String> vis = new HashSet<>();
    int len = 1;
    while (!F.isEmpty() && !T.isEmpty()) {
      if (F.size() > T.size()) {
        Set<String> set = F;
        F = T;
        T = set;
      }

      Set<String> layer = new HashSet<>();
      for (String w : F) {
        char[] chs = w.toCharArray();

        for (int i = 0; i < chs.length; i++) {
          for (char c = 'a'; c <= 'z'; c++) {
            char old = chs[i];
            chs[i] = c;
            String t = String.valueOf(chs);

            if (T.contains(t)) {
              return len + 1;
            }

            if (!vis.contains(t) && D.contains(t)) {
              layer.add(t);
              vis.add(t);
            }
            chs[i] = old;
          }
        }
      }

      F = layer;
      len++;
    }

    return 0;
  }
}
