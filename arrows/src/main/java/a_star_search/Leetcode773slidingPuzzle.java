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

package a_star_search;

import java.util.*;

public class Leetcode773slidingPuzzle {
  /*
   773. Sliding Puzzle

   On an 2 x 3 board, there are five tiles labeled from 1 to 5,
   and an empty square represented by 0. A move consists of choosing 0
   and a 4-directionally adjacent number and swapping it.

   The state of the board is solved if and only if the board is
    [[1,2,3],
    [4,5,0]].

   Given the puzzle board board, return the least number of moves
   required so that the state of the board is solved.
   If it is impossible for the state of the board to be solved, return -1.

  Input: board = [[1,2,3],[4,0,5]]
  Output: 1
  Explanation: Swap the 0 and the 5 in one move.

  Input: board = [[1,2,3],[5,4,0]]
  Output: -1
  Explanation: No number of moves will make the board solved.

  Input: board = [[4,1,2],[5,0,3]]
  Output: 5
  Explanation: 5 is the smallest number of moves that solves the board.
  An example path:
  After move 0: [[4,1,2],[5,0,3]]
  After move 1: [[4,1,2],[0,5,3]]
  After move 2: [[0,1,2],[4,5,3]]
  After move 3: [[1,0,2],[4,5,3]]
  After move 4: [[1,2,0],[4,5,3]]
  After move 5: [[1,2,3],[4,5,0]]

  Input: board = [[3,2,4],[1,5,0]]
  Output: 14


  Constraints:

  board.length == 2
  board[i].length == 3
  0 <= board[i][j] <= 5
  Each value board[i][j] is unique.
  */

  static class E {
    public int[][] b;
    public int r, c;

    public E(int[][] b, int r, int c) {
      this.b = b;
      this.r = r;
      this.c = c;
    }
  }
  /*
  BFS
  */
  public int slidingPuzzle_(int[][] board) {
    int M = board.length, N = board[0].length;
    int ri = 0, ci = 0;
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        if (board[i][j] == 0) {
          ri = i;
          ci = j;
          break;
        }
      }
    }
    Set<String> vis = new HashSet<>();
    Queue<E> q = new LinkedList<>();

    String target = "[[1, 2, 3], [4, 5, 0]]";
    if (Arrays.deepToString(board).equals(target)) return 0;
    vis.add(Arrays.deepToString(board));
    q.offer(new E(board, ri, ci));

    int steps = 0;
    int[][] d4 = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    while (!q.isEmpty()) {
      steps++;
      int size = q.size();
      while (size-- > 0) {
        E e = q.poll();
        int x = e.r, y = e.c;
        for (int[] d : d4) {
          int r = x + d[0], c = y + d[1];
          if (r >= 0 && r < M && c >= 0 && c < N) {
            int[][] copy = clone(e.b);
            int tmp = copy[x][y];
            copy[x][y] = copy[r][c];
            copy[r][c] = tmp;
            String k = Arrays.deepToString(copy);
            if (!vis.contains(Arrays.deepToString(copy))) {
              vis.add(k);
              if (k.equals(target)) {
                return steps;
              }
              q.offer(new E(copy, r, c));
            }
          }
        }
      }
    }
    return -1; // never comes here
  }

  private int[][] clone(int[][] a) {
    int[][] r = new int[a.length][a[0].length];
    for (int i = 0; i < a.length; i++) {
      r[i] = a[i].clone();
    }
    return r;
  }
  /* --------------------------------------------------------------------------
   A * search
  */
  static class Entry {
    public int[][] b;
    public int r, c;
    int steps, left;

    public Entry(int[][] b, int row, int column, int steps, int left) {
      this.b = b;
      this.r = row;
      this.c = column;
      this.steps = steps;
      this.left = left; // Manhattan distance
    }
  }

  public int slidingPuzzle(int[][] board) {
    int M = board.length, N = board[0].length;
    int ri = 0, ci = 0;
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        if (board[i][j] == 0) {
          ri = i;
          ci = j;
          break;
        }
      }
    }
    Set<String> vis = new HashSet<>();
    int targetRow = 1, targetColumn = 2;
    Queue<Entry> q = new PriorityQueue<>((a, b) -> a.steps + a.left - b.steps - b.left);

    String target = "[[1, 2, 3], [4, 5, 0]]";
    if (Arrays.deepToString(board).equals(target)) return 0;
    vis.add(Arrays.deepToString(board));
    q.offer(new Entry(board, ri, ci, 0, Math.abs(ri - targetRow) + Math.abs(ci - targetColumn)));

    int[][] d4 = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    while (!q.isEmpty()) {
      Entry e = q.poll();
      int x = e.r, y = e.c;
      for (int[] d : d4) {
        int r = x + d[0], c = y + d[1];
        if (r >= 0 && r < M && c >= 0 && c < N) {
          int[][] copy = clone(e.b);
          int tmp = copy[x][y];
          copy[x][y] = copy[r][c];
          copy[r][c] = tmp;
          String k = Arrays.deepToString(copy);
          if (!vis.contains(Arrays.deepToString(copy))) {
            vis.add(k);
            if (k.equals(target)) {
              return e.steps + 1;
            }
            q.offer(
                new Entry(
                    copy, r, c, e.steps + 1, Math.abs(r - targetRow) + Math.abs(c - targetColumn)));
          }
        }
      }
    }
    return -1; // never comes here
  }
  /*
  Skill:   swap 2 valid cell(r1,c1) with cell(r2,c2) in 2-D array
           can be converted to swap 2 cell in 1-D array
           cell(M*r1+c1) with cell(M*r2+c2)
  */
}
