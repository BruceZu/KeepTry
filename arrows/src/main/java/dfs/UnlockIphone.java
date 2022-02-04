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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class UnlockIphone {
  /*
   Unlock iphone by swiping 4 char without leaving the board
   1 start from given cell number
   2 do not reuse the number. // understood as the cell location
   3 no diagonal, only 4 direction
   4 answer is the one with max sum
  */
  /*
  DFS.
  Note:
    1 keep the result by copy not reference
    2 recursion/backtracking template need restore to original status
    3 board can have duplicated value
    4 assume
       - the long type is enough, sum use long type
       - boad[i][j] is non-negative number
  */
  static int M, N;
  static long ansSum;
  static int[] ans;

  // Assume the given cell location is valid
  public static int[] connect4char(int r, int c, int[][] board) {
    ans = new int[4];
    if (board == null) return new int[0];
    M = board.length;
    N = board[0].length;
    ansSum = Integer.MIN_VALUE;
    ans = new int[4];

    dfs(0, r, c, board, new HashSet<>(), 0, new int[4]);
    return ansSum == Integer.MIN_VALUE ? new int[0] : ans;
  }

  /*
  vis: visited on current sequence
   */
  private static void dfs(
      int index, int r, int c, int[][] board, Set<Integer> vis, long sum, int[] tmp) {
    if (index == 4) {
      if (sum > ansSum) {
        ansSum = sum;
        ans = Arrays.copyOf(tmp, 4);
      }
      return;
    }
    int location = r * N + c; // board can have duplicated value
    if (vis.contains(location)) return;
    vis.add(location);
    int v = board[r][c];
    tmp[index] = v;
    // next 4 direction
    if (r + 1 < M) dfs(index + 1, r + 1, c, board, vis, sum + v, tmp); // down
    if (r - 1 >= 0) dfs(index + 1, r - 1, c, board, vis, sum + v, tmp); // up
    if (c + 1 < N) dfs(index + 1, r, c + 1, board, vis, sum + v, tmp); // right
    if (c - 1 >= 0) dfs(index + 1, r, c - 1, board, vis, sum + v, tmp); // right
    // back to original
    vis.remove(location);
    // tmp need not restore
  }

  public static void main(String[] args) {
    System.out.println(
        Arrays.toString(
            connect4char(
                0,
                1,
                new int[][] {
                  {1, 2, 3},
                  {4, 5, 6},
                  {7, 8, 9}
                })));
    System.out.println(
        Arrays.toString(
            connect4char(
                0,
                1,
                new int[][] {
                  {1, 2, 3, 11, 11},
                  {1, 15, 6, 0, 0},
                  {0, 0, 0, 1, 1}
                })));
  }
}
