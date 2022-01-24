//  Copyright 2022 The KeepTry Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the 'License');
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an 'AS IS' BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package tree.trie;

import java.util.*;

public class Leetcode212wordsearchII {
  /*
   Leetcode 212. Word Search II
   Given an m x n board of characters and a list of strings words,
   return all words on the board.

   Each word must be constructed from letters of sequentially adjacent cells,
   where adjacent cells are horizontally or vertically neighboring.
   The same letter cell may not be used more than once in a word.

    Input: board = [
    ['o','a','a','n'],
    ['e','t','a','e'],
    ['i','h','k','r'],
    ['i','f','l','v']
    ],
    words = ['oath','pea','eat','rain']
    Output: ['eat','oath']
    Example 2:


    Input: board = [
    ['a','b'],
    ['c','d']
    ],
     words = ['abcb']
    Output: []


    Constraints:

    m == board.length
    n == board[i].length
    1 <= m, n <= 12
    board[i][j] is a lowercase English letter.
    1 <= words.length <= 3 * 104
    1 <= words[i].length <= 10
    words[i] consists of lowercase English letters.
    All the strings of words are unique.
  */

  /* --------------------------------------------------------------------------
   BFS does not work see https://imgur.com/aBmIMgD
  */
  public static List<String> findWords__(char[][] a, String[] words) {
    List<String> r = new LinkedList<>();
    if (a == null || words == null) return r;
    int M = a.length, N = a[0].length;
    Map<Character, List<Integer>> map = new HashMap<>();
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        map.computeIfAbsent(a[i][j], k -> new LinkedList<>()).add(i * N + j);
      }
    }

    for (String w : words) {
      char head = w.charAt(0);
      if (map.containsKey(head)) {
        for (Integer l : map.get(head)) {
          if (bfs(l, w, a, M, N)) {
            r.add(w);
            break;
          }
        }
      }
    }
    return r;
  }

  private static boolean bfs(int location, String word, char[][] a, int M, int N) {
    Queue<Integer> q = new LinkedList<>();
    Set<Integer> vis = new HashSet<>();
    q.offer(location);
    vis.add(location);
    int layer = 0; // next layer

    char[] w = word.toCharArray();
    int[][] d4 = new int[][] {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    while (!q.isEmpty() && layer + 1 < w.length) { // at most check length layer in BFS
      layer++;
      int size = q.size();
      while (size-- >= 1) {
        // for new layer
        int curl = q.poll();
        int curr = curl / N, curc = curl % N;
        for (int[] d : d4) {
          int ni = curr + d[0], nc = curc + d[1];
          int nl = ni * N + nc;
          if (0 <= ni
              && ni < M
              && 0 <= nc
              && nc < N
              && !vis.contains(nl)
              && a[ni][nc] == w[layer]) {
            vis.add(nl);
            q.offer(nl);
          }
        }
      }
    }

    return layer == w.length - 1 && !q.isEmpty();
  }
  /* --------------------------------------------------------------------------
    backtracking
    Time Limit Exceeded
  */
  public static List<String> findWords_(char[][] a, String[] words) {
    List<String> r = new LinkedList<>();
    if (a == null || words == null) return r;
    int M = a.length, N = a[0].length;
    Map<Character, List<Integer>> map = new HashMap<>();
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        map.computeIfAbsent(a[i][j], k -> new LinkedList<>()).add(i * N + j);
      }
    }

    for (String w : words) {
      char head = w.charAt(0);
      if (map.containsKey(head)) {
        for (Integer l : map.get(head)) {
          Set<Integer> vis = new HashSet<>();
          vis.add(l);
          if (backtracking(l, w.toCharArray(), 0, a, M, N, vis)) {
            r.add(w);
            break;
          }
        }
      }
    }
    return r;
  }

  private static boolean backtracking(
      int location, char[] w, int index, char[][] a, int M, int N, Set<Integer> vis) {
    if (index == w.length - 1) return true;
    int[][] d4 = new int[][] {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    int curr = location / N, curc = location % N;
    for (int[] d : d4) {
      int ni = curr + d[0], nc = curc + d[1];
      int nl = ni * N + nc;
      if (0 <= ni
          && ni < M
          && 0 <= nc
          && nc < N
          && !vis.contains(nl)
          && a[ni][nc] == w[index + 1]) {
        vis.add(nl);
        if (backtracking(nl, w, index + 1, a, M, N, vis)) return true;
        vis.remove(nl);
      }
    }
    return false;
  }
  /* --------------------------------------------------------------------------
    Trie + backtracking:   revers comparison:   each cell <=> Tire node's children
                                               4 neighbor <=> Tire node's grand children

    visited: mark #
    avoid duplicated result:  set found w=null
    avoid duplicate access:   remove the visited leaf node. the time complexity
                              of the overall algorithm sort of depends on the size of the Trie
    https://imgur.com/jkxRAeQ
    Runtime: O(M(4⋅3^(L-1)), M is the number of cells in the board,  L is the maximum length of words.
             in worst case: https://imgur.com/MtmSonV. above time complexity is estimated under the assumption
             that the Trie data structure would not change once built. If gradually remove the nodes in Trie,
             we could greatly improve the time complexity, since the cost of backtracking would reduced to zero
             once we match all the words in the dictionary, i.e. the Trie becomes empty.
             the first step you can explore four directions. After that first step you can only explore three directions,
             excluding the cell you just come from. Therefore 4 * 3^(L-1).

    Space: O(N),  N is the total number of letters in the words.
  */

  class Solution {
    static class TrieNode {
      // save space of 26 English letters; and know itself by the key
      // if it is empty, means  current node is leaf node
      HashMap<Character, TrieNode> children = new HashMap<>();
      String w = null; // it can be the index of word in words
    }

    char[][] A = null;
    ArrayList<String> ans = new ArrayList<>(); // why not set? for duplicated

    public List<String> findWords(char[][] board, String[] words) {
      this.A = board;

      TrieNode root = new TrieNode();
      for (String w : words) {
        TrieNode n = root;
        for (Character c : w.toCharArray()) {
          n = n.children.computeIfAbsent(c, k -> new TrieNode());
        }
        n.w = w;
      }

      //  Backtracking starting from each cell
      for (int r = 0; r < board.length; ++r) {
        for (int c = 0; c < board[r].length; ++c) {
          if (root.children.containsKey(board[r][c])) {
            backtracking(r, c, root);
          }
        }
      }

      return ans;
    }

    private void backtracking(int r, int c, TrieNode head) {
      Character v = A[r][c];
      TrieNode vnode = head.children.get(v);

      //  there is any match word?
      if (vnode.w != null) {
        ans.add(vnode.w);
        vnode.w = null; // avoid duplicated, this is why ans is list till works, not use set.
      }

      A[r][c] = '#'; //  visited
      int[] rOff = {-1, 0, 1, 0};
      int[] cOff = {0, 1, 0, -1};
      for (int i = 0; i < 4; ++i) {
        int r_ = r + rOff[i];
        int c_ = c + cOff[i];
        if (r_ < 0 || r_ >= A.length || c_ < 0 || c_ >= A[0].length) continue;

        if (vnode.children.containsKey(A[r_][c_])) { // neighbor
          backtracking(r_, c_, vnode);
        }
      }
      A[r][c] = v;

      // Optimization: incrementally remove the visited leaf node which w is guarantee to be null
      if (vnode.children.isEmpty()) head.children.remove(v);
    }
  }

  /*---------------------------------------------------------------------------
  Trie node use array[26] not map.
  cons: no way to trim the leaf node
  pros:

  */
  class Solution2 {
    public List<String> findWords(char[][] A, String[] words) {
      List<String> ans = new ArrayList<>();
      TrieNode root = buildTrie(words);
      for (int i = 0; i < A.length; i++) {
        for (int j = 0; j < A[0].length; j++) {
          backtracking(A, i, j, root, ans);
        }
      }
      return ans;
    }

    public void backtracking(char[][] A, int i, int j, TrieNode root, List<String> ans) {
      char c = A[i][j];
      if (c == '#' || root.children[c - 'a'] == null) return;
      root = root.children[c - 'a'];
      if (root.word != null) { // found
        ans.add(root.word);
        root.word = null; // de-duplicate
      }

      A[i][j] = '#';
      if (i > 0) backtracking(A, i - 1, j, root, ans);
      if (i < A.length - 1) backtracking(A, i + 1, j, root, ans);
      if (j > 0) backtracking(A, i, j - 1, root, ans);
      if (j < A[0].length - 1) backtracking(A, i, j + 1, root, ans);
      A[i][j] = c;

      // no way to trim the leaf node
    }

    public TrieNode buildTrie(String[] words) {
      TrieNode root = new TrieNode();
      for (String w : words) {
        TrieNode n = root;
        for (char c : w.toCharArray()) {
          int i = c - 'a';
          if (n.children[i] == null) n.children[i] = new TrieNode();
          n = n.children[i];
        }
        n.word = w;
      }
      return root;
    }

    static class TrieNode {
      TrieNode[] children = new TrieNode[26];
      String word;
    }
  }
  // ---------------------------------------------------------------------------
  public static void main(String[] args) {

    System.out.println(
        findWords_(
            new char[][] {
              {'a', 'b', 'c'},
              {'a', 'e', 'd'},
              {'a', 'f', 'g'}
            },
            new String[] {"eaabcdgfa"}));

    System.out.println(
        findWords_(
            new char[][] {
              {'o', 'a', 'b', 'n'},
              {'o', 't', 'a', 'e'},
              {'a', 'h', 'k', 'r'},
              {'a', 'f', 'l', 'v'}
            },
            new String[] {"oa", "oaa"}));

    System.out.println(
        findWords_(
            new char[][] {
              {'a'},
            },
            new String[] {"ab"}));
    System.out.println(
        findWords_(
            new char[][] {
              {'o', 'a', 'a', 'n'},
              {'e', 't', 'a', 'e'},
              {'i', 'h', 'k', 'r'},
              {'i', 'f', 'l', 'v'}
            },
            new String[] {"oath", "pea", "eat", "rain"}));
  }
}
