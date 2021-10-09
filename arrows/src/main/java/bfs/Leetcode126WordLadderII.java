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
    (Bruce: compared with `127. Word Ladder` which only ask the number
           of all the content: all the shortest transformation sequences)

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
beginWord = "hit", endWord = "cog",
wordList = ["hot","dot","dog","lot","log"]
Output: []


Input: s = "a";  e = "c"; arr = new String[]{"a", "b", "c"};
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
      once find the target string then stop further level expanding-> shortest(s)
      2-1 how to keep the path？ it is graph: Map<from, Set<to>>, need build the graph, nodes and edges.
          once find the end word the graph is enough and stop. then dfs find out all path from start to end word
      2-2 avoid revisiting a string in dictionary? Use the directory itself to remove visited word
       - To make sure the shortest path:
         not visit string already visited in the path
         not visit string in other path but at the above level
           - one more paths can merge into one path at a point/word
           so the right time to mark a word as visited should be after all current level words are calculated out
           E.g.
               red -> rex -> tex->
               red -> ted -> tex->
           or
               p a r i s
              /          \
              -->              p a r k s -->
              \          /
               m a r k s
               Do not tag the 'parks' as visited only when one path at the current level is handled

     3  DFS.
        how to improve performance avoid visited longer path if one path is find.


  O(N*K^2+α) time
    every word will be traversed and for each word, we will find the neighbors
    25*K*K
    O(K2).  all the N words will be O(N*K^2)

    N is the size of directory,
    K is the length of word.
    α is the number of possible paths from beginWord to endWord in the directed graph
      assuming that every layer except the first and the last layer in the DAG has x number of words
      and is fully connected to the next layer.
      Let h represent the height of the DAG, so the total number of paths will be
      x^h, h= (N-2)/x .
      so x^{(N-2)/x}  total paths,
      which is maximized when x=2.718, which we will round to 3  because x must be an integer.
      Thus the upper bound for α is  3^{(N/3)}
      this is a very loose bound

  O(NK) space

 */
public class Leetcode126WordLadderII {
  private Map<String, Set<String>> graph; // current string: transformed string(s)
  private String S, E; // start, end word
  private String[] tmp;
  private List<List<String>> ans; // shortest paths

  public List<List<String>> findLadders(String S, String E, List<String> wordList) {
    ans = new ArrayList();
    Set<String> D = new HashSet(wordList);
    if (!D.contains(E)) return ans;

    this.S = S;
    this.E = E;
    graph = new HashMap();
    if (!bfsBuildGraph(D)) return ans;

    tmp = new String[wordList.size()];
    tmp[0] = S;
    dfsPaths(S, 1);
    return ans;
  }

  private boolean bfsBuildGraph(Set<String> D) {
    Queue<String> q = new LinkedList();
    q.offer(S);
    D.remove(S);

    while (!q.isEmpty()) {
      Set<String> layer = new HashSet(); // reduce duplicate strings in next layer
      for (int i = q.size(); i >= 1; i--) {
        String cur = q.poll();
        Set<String> inexts = transformed(cur, D);

        graph.put(cur, inexts);
        layer.addAll(inexts);
      }
      if (layer.contains(E)) return true; // exist from here normally
      for (String str : layer) q.offer(str);
      D.removeAll(layer); // the right time to mark word in the new layer as visited
    }
    return false; // not find the `end` word
  }

  // only change one char: K is word length, K*25
  // check existence in directory O(K)
  // in total K*25*K, O(k^2) time
  private Set<String> transformed(String str, Set<String> D) {
    Set<String> r = new HashSet();
    char[] a = str.toCharArray();
    for (int i = 0; i < a.length; i++) {
      char ci = a[i];
      for (char c = 'a'; c <= 'z'; c++) {
        if (c == ci) continue;
        a[i] = c; // note
        String s = String.valueOf(a);
        if (D.contains(s)) r.add(s);
      }
      a[i] = ci;
    }
    return r;
  }

  private void dfsPaths(String w, int size) {
    if (w.equals(E)) ans.add(Arrays.asList(Arrays.copyOf(tmp, size)));
    if (graph.containsKey(w)) {
      for (String s : graph.get(w)) {
        tmp[size] = s;
        dfsPaths(s, size + 1);
      }
    }
  }
}
