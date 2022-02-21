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

package backtracing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Leetcode79WordSearch {
  /*
  79. Word Search

  Given an m x n grid of characters board and a string word, return true if word exists in the grid.

  The word can be constructed from letters of sequentially adjacent cells,
  where adjacent cells are horizontally or vertically neighboring.
  The same letter cell may not be used more than once.

  Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
  Output: true

  Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
  Output: true

  Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
  Output: false


  Constraints:

  m == board.length
  n = board[i].length
  1 <= m, n <= 6
  1 <= word.length <= 15
  board and word consists of only lowercase and uppercase English letters.


  Follow up: Could you use search pruning to make your solution faster with a larger board?
  */

  static int M, N;
  static char[][] grid;

  public static boolean exist__(char[][] g, String word) {
    if (word == null || word.length() == 0) return true;
    if (g == null || g.length == 0 || g.length * g[0].length < word.length()) return false;

    M = g.length;
    N = g[0].length;
    grid = g;

    boolean[][] vist = new boolean[M][N];
    char[] w = word.toCharArray();

    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        if (g[i][j] == w[0]) {
          vist[i][j] = true;
          if (dfs(i, j, w, 1, vist, 0)) return true;
          vist[i][j] = false;
        }
      }
    }
    return false;
  }

  // from: 0 original, 1 left , 2 right, 3 top, 4 down
  private static boolean dfs(int r, int c, char[] w, int i, boolean[][] vis, int from) {
    if (i == w.length) return true;

    if (from != 1 && c - 1 >= 0 && vis[r][c - 1] == false && grid[r][c - 1] == w[i]) { // left
      vis[r][c - 1] = true;
      if (dfs(r, c - 1, w, i + 1, vis, 2)) return true;
      vis[r][c - 1] = false;
    }

    if (from != 2 && c + 1 < N && vis[r][c + 1] == false && grid[r][c + 1] == w[i]) { // right
      vis[r][c + 1] = true;
      if (dfs(r, c + 1, w, i + 1, vis, 1)) return true;
      vis[r][c + 1] = false;
    }

    if (from != 3 && r - 1 >= 0 && vis[r - 1][c] == false && grid[r - 1][c] == w[i]) { // up
      vis[r - 1][c] = true;
      if (dfs(r - 1, c, w, i + 1, vis, 4)) return true;
      vis[r - 1][c] = false;
    }

    if (from != 4 && r + 1 < M && vis[r + 1][c] == false && grid[r + 1][c] == w[i]) { // down
      vis[r + 1][c] = true;
      if (dfs(r + 1, c, w, i + 1, vis, 3)) return true;
      vis[r + 1][c] = false;
    }
    return false;
  }
  /* ---------------------------------------------------------------------------
    A. use # mark visited
    B. move logic 2 and 3  to the head of dfs.
     -1 r, c are valid index,
     -2 visited?
     -3 cell value and current char match?
    C check word current index is end at once after match checking
    D. put 4 direction dfs in the if condition
    E. remove 'from' which is sort of visited
  */

  public static boolean exist_(char[][] g, String word) {
    if (word == null || word.length() == 0) return true;
    if (g == null || g.length == 0 || g.length * g[0].length < word.length()) return false;

    M = g.length;
    N = g[0].length;
    grid = g;

    char[] w = word.toCharArray();
    for (int i = 0; i < g.length; i++) {
      for (int j = 0; j < g[0].length; j++)
        if (dfs(i, j, w, 0)) {
          return true;
        }
    }
    return false;
  }
  // assume r and c is valid index in grid, i is valid index in w
  private static boolean dfs(int r, int c, char[] w, int i) {
    if (grid[r][c] != '#' && grid[r][c] == w[i]) {
      // match: cell and current char
      // check word end at once. decide continue dfs of not
      if (i == w.length - 1) return true;
      // dfs ---
      grid[r][c] = '#';
      if (c + 1 < N && dfs(r, c + 1, w, i + 1) // right
          || c - 1 >= 0 && dfs(r, c - 1, w, i + 1) // left
          || r - 1 >= 0 && dfs(r - 1, c, w, i + 1) // top
          || r + 1 < M && dfs(r + 1, c, w, i + 1)) { // down
        return true;
      }
      grid[r][c] = w[i];
      // dfs ---
    }
    return false;
  }

  /* ---------------------------------------------------------------------------
    move validation of row and column index into dfs start part
    and checking the word current index i here together.

    Time O(N⋅3^L) N is the number of cells, L is the length of the word
    Space O(L)
  */

  public static boolean exist(char[][] board, String word) {
    grid = board;
    M = board.length;
    N = board[0].length;

    for (int row = 0; row < M; ++row)
      for (int col = 0; col < N; ++col) {
        if (backtrack(row, col, word, 0)) {
          return true;
        }
      }
    return false;
  }

  protected static boolean backtrack(int r, int c, String w, int i) {
    if (i == w.length()) return true; // need not continue
    if (r < 0 || r == M || c < 0 || c == N || grid[r][c] != w.charAt(i)) return false;
    // Instead of doing the validation of row and column index before the recursive call
    // we do it at the start part here.  this allow code reach the bottom case,
    // {{a}} and "a", either of neighbor index would be invalid.

    /*  explore 4 direction */
    boolean find = false;
    // mark the path before the next exploration
    grid[r][c] = '#';

    int[] r4 = {0, 1, 0, -1};
    int[] c4 = {1, 0, -1, 0};
    for (int d = 0; d < 4; ++d) {
      find = backtrack(r + r4[d], c + c4[d], w, i + 1);
      if (find) break;
      // Instead of returning directly,  break out of the loop and do the cleanup before returning.
    }

    /*  no matter find or not, restore cell value, not affect other test cases */
    grid[r][c] = w.charAt(i);
    return find;
  }

  /* --------------------------------------------------------------------------
  get only one of matched paths' location if it exists
  */
  public static List<int[]> find(char[][] g, String w) {
    if (g == null) return new ArrayList<>();
    List<int[]> path = new LinkedList<>();
    grid = g;
    M = grid.length;
    N = grid[0].length;

    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        boolean find = dfs(i, j, 0, w.toCharArray(), path);
        if (find) return path;
      }
    }
    return path;
  }
  /*
  Not assume the current location is valid in grid
  return true: found a matched path
  */
  private static boolean dfs(int r, int c, int i, char[] w, List<int[]> path) {
    if (i == w.length) return true; // find a match
    // continue:
    //  - current not valid
    if (r == M || r < 0 || c == N || c < 0 || grid[r][c] == '#' || grid[r][c] != w[i]) return false;
    //  - current valid, mark, keep and try next 4 directions

    path.add(new int[] {r, c});
    grid[r][c] = '#'; // original value is  grid[r][c] or  w[i]
    // backtracking start
    boolean find = false;
    if (dfs(r + 1, c, i + 1, w, path) // down
        || dfs(r - 1, c, i + 1, w, path) // up.
        || dfs(r, c + 1, i + 1, w, path) // right
        || dfs(r, c - 1, i + 1, w, path)) // left
    {
      find = true;
    }

    // backtracking end
    grid[r][c] = w[i]; // always clean #
    if (!find) path.remove(path.size() - 1); // if found then keep the path no touched.
    return find;
  }
  // --------------------------------------------------------------------------
  public static void main(String[] argv) {
    char[][] grid1 = {
      {'c', 'c', 'x', 't', 'i', 'b'},
      {'c', 'c', 'a', 't', 'n', 'i'},
      {'a', 'c', 'n', 'n', 't', 't'},
      {'t', 'c', 's', 'i', 'p', 't'},
      {'a', 'o', 'o', 'o', 'a', 'a'},
      {'o', 'a', 'a', 'a', 'o', 'o'},
      {'k', 'a', 'i', 'c', 'k', 'i'}
    };
    String word1 = "catnip";
    String word2 = "cccc";
    String word3 = "s";
    String word4 = "bit";
    String word5 = "aoi";
    String word6 = "ki";
    String word7 = "aaa";
    String word8 = "ooo";

    char[][] grid2 = {{'a'}};
    String word9 = "a";
    // test---------------

    //    List<String> words = Arrays.asList(word1, word2, word3, word4, word5, word6, word7,
    // word8);
    //    for (String w : words) {
    //      for (int[] location : find(grid1, w)) {
    //        System.out.print(Arrays.toString(location));
    //      }
    //      System.out.println(" - ");
    //    }
    //    System.out.println(" ====  ");
    for (int[] location : find(grid2, word9)) {
      System.out.print(Arrays.toString(location));
    }
    System.out.println(" - ");
  }
}
