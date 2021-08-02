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
  126. Word Ladder II

    A transformation sequence from word beginWord to word endWord using
    a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:

    Every adjacent pair of words differs by a single letter.
    Every si for 1 <= i <= k is in wordList.

    Note that beginWord does not need to be in wordList.
    sk == endWord

Given two words, beginWord and endWord, and a dictionary wordList,
return all the shortest transformation sequences from beginWord to endWord,
or an empty list if no such sequence exists.

Each sequence should be returned as a list of the words [beginWord, s1, s2, ..., sk].



Example

Input:
beginWord = "hit",
endWord =   "cog",
wordList = ["hot","dot","dog","lot","log","cog"]

Output: [["hit","hot","dot","dog","cog"],
         ["hit","hot","lot","log","cog"]]

Explanation: There are 2 shortest transformation sequences:
"hit" -> "hot" -> "dot" -> "dog" -> "cog"
"hit" -> "hot" -> "lot" -> "log" -> "cog"



Input:
beginWord = "hit",
endWord = "cog",
wordList = ["hot","dot","dog","lot","log"]

Output: []


Input: s = "a";
       e = "c";
       arr = new String[]{"a", "b", "c"};
Output: [['a','c']]

Constraints:
    1 <= beginWord.length <= 5
    endWord.length == beginWord.length
    1 <= wordList.length <= 1000
    wordList[i].length == beginWord.length
    beginWord, endWord, and wordList[i] consist of lowercase English letters.
    beginWord != endWord
    All the words in wordList are unique.
*/

/*
Understanding
   It is an undirected acyclic graph, edge weight is 1, vertex existence is decided by dictionary
   https://leetcode.com/problems/word-ladder-ii/Figures/126/126A.png

   1 'same length': only change, no add and delete operation
      'All the words in wordList are unique.'.
       end word may not be in the dictionary.
   2  BFS
      once find the target string then stop further level expanding
      2-1 how to keep the path and what data structure should be in queue
      2-2 how to avoid revisiting a string in dictionary?
       - should not visit string already in the path
       - how about other string in other path but in above level?
          if that path reach target string, then that path is shorter than current path.
          so current path need not repeat left part of that path.
          if that path can not reach target string at last then need not to go that way.
       so any string on any path visited at previous level should not be visited again
       for current level: current path visited string can also be used by another path at the same level
       so take these string as visited only after all path of current level are processed

       `find a possible string from dictionary, if it is visited then do not use it in current path`
       this is same as remove the visited string from dictionary and find possible dictionary
       this also make sure no edge between any 2 strings at the same layer

       E.g. at same ith step, some 2 path merge at one point:
         red -> rex -> tex->
         red -> ted -> tex->
         or
           p a r i s
          /          \
      -->              p a r k s -->
          \          /
           m a r k s

       String s = "magic";
       String e = "pearl";
       String[] arr = new String[]{ ... };

       expected answer:
         [["magic", "manic", "mania", "maria", "maris", "paris", "parks", "perks", "peaks", "pears", "pearl"],
                                                                   |
          ["magic", "manic", "mania", "maria", "marta", "marty", "party", "parry", "perry", "peary", "pearl"],
          ["magic", "manic", "mania", "maria", "marta", "marty", "marry", "merry", "perry", "peary", "pearl"],
          ["magic", "manic", "mania", "maria", "marta", "marty", "marry", "parry", "perry", "peary", "pearl"],
          ["magic", "manic", "mania", "maria", "maris", "marks", "parks", "perks", "peaks", "pears", "pearl"]]
                                                                   |
       wrong answer(lost one):
         [["magic", "manic", "mania", "maria", "maris", "paris", "parks", "perks", "peaks", "pears", "pearl"],
           ["magic", "manic", "mania", "maria", "marta"," marty", "party", "parry", "perry", "peary"," pearl"]
          ["magic", "manic", "mania", "maria", "marta"," marty", "marry", "parry", "perry", "peary", "pearl"],
          ["magic", "manic", "mania", "maria", "marta", "marty", "marry", "merry", "perry", "peary", "pearl"],
          ]
        So do not tag the 'parks' as visited only when on path at the current level is handled

     3  DFS.
        how to improve performance avoid visited longer path if one path is find.

  N is the size of directory, K is the length of word.
  O(NK^2+α) time
      assuming that every layer except the first and the last layer in the DAG has x
      number of words and is fully connected to the next layer. Let h represent the
      height of the DAG, so the total number of paths will be
      x^h, h= (N-2)/x .
      so x^{(N-2)/x}  total paths,
      which is maximized when x=2.718, which we will round to 3  because x must be an integer.
      Thus the upper bound for α is  3^{(N/3)}
      this is a very loose bound

  O(NK) space

 */
public class Leetcode126WordLadderII {
  private Map<String, Set<String>> graph; // current string: transformed string(s)
  private String begin, end;
  private String[] tmpPath;
  private List<List<String>> shortests;

  public List<List<String>> findLadders(String begin, String end, List<String> wordList) {
    shortests = new ArrayList();
    Set<String> D = new HashSet(wordList);
    if (!D.contains(end)) return shortests;

    this.begin = begin;
    this.end = end;
    graph = new HashMap();
    if (!bfsBuildGraph(D)) return shortests;

    tmpPath = new String[wordList.size()];
    tmpPath[0] = begin;
    dfsPaths(begin, 1);
    return shortests;
  }

  private boolean bfsBuildGraph(Set<String> D) {
    Queue<String> q = new LinkedList();
    q.offer(begin);
    D.remove(begin);

    while (!q.isEmpty()) {
      Set<String> newL = new HashSet(); // reduce duplicate strings in next layer
      for (int i = q.size(); i >= 1; i--) {
        String str = q.poll();
        Set<String> ts = transformed(str, D);

        graph.put(str, ts);
        newL.addAll(ts);
      }
      if (newL.contains(end)) return true;
      for (String str : newL) q.offer(str);
      D.removeAll(newL);
    }
    return false;
  }

  private Set<String> transformed(String str, Set<String> D) {
    Set<String> r = new HashSet();
    char[] arr = str.toCharArray();
    for (int i = 0; i < arr.length; i++) {
      char ci = arr[i];
      for (char c = 'a'; c <= 'z'; c++) {
        if (c == ci) continue;
        arr[i] = c; // note
        String t = String.valueOf(arr);
        if (D.contains(t)) r.add(t);
      }
      arr[i] = ci;
    }
    return r;
  }

  private void dfsPaths(String k, int size) {
    if (k.equals(end)) shortests.add(Arrays.asList(Arrays.copyOf(tmpPath, size)));
    if (graph.containsKey(k)) {
      for (String t : graph.get(k)) {
        tmpPath[size] = t;
        dfsPaths(t, size + 1);
      }
    }
  }
}
