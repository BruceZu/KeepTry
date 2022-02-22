//  Copyright 2022 The KeepTry Open Source Project
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

package dfs;

import com.google.common.collect.Lists;

import java.util.*;

public class ShortestPathWithAllTreasure {
  /*
     given board and start, end location
      1 is treasure.
     -1 is can not walk through
      0 is able to walk through
     return all possible shortest path from start to end and get all treasure

     board3 = [
            [  1,  0,  0, 0, 0 ],
            [  0, -1, -1, 0, 0 ],
            [  0, -1,  0, 1, 0 ],
            [ -1,  0,  0, 0, 0 ],
            [  0,  1, -1, 0, 0 ],
            [  0,  0,  0, 0, 0 ],
            ]


    treasure(board3, (5, 0), (0, 4)) -> None

    treasure(board3, (5, 2), (2, 0)) ->
            [(5, 2), (5, 1), (4, 1), (3, 1), (3, 2), (2, 2), (2, 3), (1, 3), (0, 3), (0, 2), (0, 1), (0, 0), (1, 0), (2, 0)]
            Or
            [(5, 2), (5, 1), (4, 1), (3, 1), (3, 2), (3, 3), (2, 3), (1, 3), (0, 3), (0, 2), (0, 1), (0, 0), (1, 0), (2, 0)]

    treasure(board3, (0, 0), (4, 1)) ->
            [(0, 0), (0, 1), (0, 2), (0, 3), (1, 3), (2, 3), (2, 2), (3, 2), (3, 1), (4, 1)]
            Or
            [(0, 0), (0, 1), (0, 2), (0, 3), (1, 3), (2, 3), (3, 3), (3, 2), (3, 1), (4, 1)]
  */
  /*
   visited: marking -1
   Note:
   - any return happen should restore the status to original version before step in: all variables
   - List.copyOf(path)
  */
  int[][] board;
  int sr, sc, er, ec, M, N, T;
  Set<List<int[]>> ans;
  int minLen;

  public Set<List<int[]>> treasure(int[][] board, int sr, int sc, int endr, int endc) {
    this.board = board;
    this.sr = sr;
    this.sc = sc;
    this.er = endr;
    this.ec = endc;
    if (board == null || board.length == 0 || board[0] == null || board[0].length == 0)
      return new HashSet<>();
    // Assume at least there are 2 cells in the board
    // value of start and end location is not -1
    M = board.length;
    N = board[0].length;

    // Number of all treasure
    T = 0;
    for (int r = 0; r < M; r++) {
      for (int c = 0; c < N; c++) {
        if (board[r][c] == 1) T++;
      }
    }
    ans = new HashSet<>();
    minLen = Integer.MAX_VALUE;
    List<int[]> path = new LinkedList<>();
    dfs(sr, sc, 0, path);
    return ans;
  }

  private void dfs(int r, int c, int x, List<int[]> path) {
    if (r < 0 || r >= M || c < 0 || c >= N || board[r][c] == -1) return;
    // v is 1 or 0 , or end location
    if (board[r][c] == 1) x++;
    path.add(new int[] {r, c});
    if (r == er && c == ec) {
      if (x == T) {
        if (path.size() < minLen) {
          ans = new HashSet<>();
          ans.add(List.copyOf(path));
          minLen = path.size();
        } else if (path.size() == minLen) {
          ans.add(List.copyOf(path));
        }
      }

      path.remove(path.size() - 1); // crucial part: restore the path
      return;
    }
    int old = board[r][c];
    board[r][c] = -1;
    //
    dfs(r + 1, c, x, path); // down
    dfs(r - 1, c, x, path); // up
    dfs(r, c + 1, x, path); // right
    dfs(r, c - 1, x, path); // left
    //
    path.remove(path.size() - 1);
    board[r][c] = old;
  }

  public static void main(String[] args) {
    ShortestPathWithAllTreasure t = new ShortestPathWithAllTreasure();

    int[][] board3 =
        new int[][] {
          {1, 0, 0, 0, 0},
          {0, -1, -1, 0, 0},
          {0, -1, 0, 1, 0},
          {-1, 0, 0, 0, 0},
          {0, 1, -1, 0, 0},
          {0, 0, 0, 0, 0},
        };

    print(t.treasure(board3, 5, 0, 0, 4));
    print(t.treasure(board3, 5, 2, 2, 0));
    print(t.treasure(board3, 0, 0, 4, 1));
    /*
         board3 = [
                0   1   2  3  4
           0 [  1,  0,  0, 0, 0 ],
           1 [  0, -1, -1, 0, 0 ],
           2 [  0, -1,  0, 1, 0 ],
           3 [ -1,  0,  0, 0, 0 ],
           4 [  0,  1, -1, 0, 0 ],
           5 [  0,  0,  0, 0, 0 ],
            ]
    treasure(board3, (5, 0), (0, 4)) -> None
    treasure(board3, (5, 2), (2, 0)) ->
            [(5, 2), (5, 1), (4, 1), (3, 1), (3, 2), (2, 2), (2, 3), (1, 3), (0, 3), (0, 2), (0, 1), (0, 0), (1, 0), (2, 0)]
            Or
            [(5, 2), (5, 1), (4, 1), (3, 1), (3, 2), (3, 3), (2, 3), (1, 3), (0, 3), (0, 2), (0, 1), (0, 0), (1, 0), (2, 0)]

    treasure(board3, (0, 0), (4, 1)) ->
            [(0, 0), (0, 1), (0, 2), (0, 3), (1, 3), (2, 3), (2, 2), (3, 2), (3, 1), (4, 1)]
            Or
            [(0, 0), (0, 1), (0, 2), (0, 3), (1, 3), (2, 3), (3, 3), (3, 2), (3, 1), (4, 1)]
     */

  }

  static void print(Set<List<int[]>> ans) {
    System.out.println("--- start");
    for (List<int[]> e : ans) {
      for (int[] l : e) {
        System.out.print(Arrays.toString(l));
      }
      System.out.println();
    }
    System.out.println("--- end");
  }
}
