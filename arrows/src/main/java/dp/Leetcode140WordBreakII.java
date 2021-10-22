//  Copyright 2021 The KeepTry Open Source Project
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

package dp;

import java.util.*;

public class Leetcode140WordBreakII {
  /*
    140. Word Break II

    Given a string s and a dictionary of strings wordDict,
    add spaces in s to construct a sentence where each word
    is a valid dictionary word.
    Return all such possible sentences in any order.

    Note that the same word in the dictionary may be reused multiple times in the segmentation.

    Input: s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
    Output: ["cats and dog","cat sand dog"]


    Input: s = "pineapplepenapple", wordDict = ["apple","pen","applepen","pine","pineapple"]
    Output: ["pine apple pen apple","pineapple pen apple","pine applepen apple"]
    Explanation: Note that you are allowed to reuse a dictionary word.


    Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
    Output: []


    Constraints:

    1 <= s.length <= 20
    1 <= wordDict.length <= 1000
    1 <= wordDict[i].length <= 10
    s and wordDict[i] consist of only lowercase English letters.
    All the strings of wordDict are unique.
  */

  /*
  Idea:
    DFS(backtracking, DP top-down which need a cache)
    if current str is "" then return "";
    For each word the current str starts with in dictionary:
      merge it with all substring returned sentences(if existing) to result;
      if no sub sentences, return empty result
      if no word found the cur str starts with return empty result

  It's a tree data structure
  visit certain nodes multiple times, but visit each edge once and only once.
  the number of edges depends on the construction of the input string and the word dictionary.
  In the worst case, there could be NN valid postfixes, i.e. each prefix of the input
  string is a valid word: s=aaa and the word dictionary as wordDict=["a", "aa", "aaa"].
  N: the length of the input string
  W: the number of words in the dictionary

   TODO: O(?) time and space.
  */
  public List<String> wordBreak(String s, List<String> wordDict) {
    return dfs(s, wordDict, new HashMap<>());
  }

  /*
   List with "": previous part of current sentence is valid and end.
   empty list: current sentence is invalid
  */
  List<String> dfs(String s, List<String> d, Map<String, List<String>> cache) {
    if (cache.containsKey(s)) return cache.get(s);
    // when to stop if dfs
    List<String> r = new LinkedList();
    if (s.isEmpty()) {
      r.add("");
      return r;
    }
    for (String w : d) { // skill for each w from dictionary to check if s starts with it.
      if (s.startsWith(w)) {
        List<String> list = dfs(s.substring(w.length()), d, cache);
        for (String l : list) {
          String v = l.isEmpty() ? "" : " " + l;
          r.add(w + v);
        }
      }
    }
    cache.put(s, r);
    return r; // empty list if no word the current s starts with
  }
}
