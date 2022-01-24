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

public class Leetcode980UniquePathsIII {
  /*
   Leetcode 980. Unique Paths III
   you are given an m x n integer array grid where grid[i][j] could be:

    1 representing the starting square. There is exactly one starting square.
    2 representing the ending square. There is exactly one ending square.
    0 representing empty squares we can walk over.
    -1 representing obstacles that we cannot walk over.
    Return the number of 4-directional walks from the starting square to the ending square,
    that walk over every non-obstacle square exactly once.



    Input: grid = [[1,0,0,0],[0,0,0,0],[0,0,2,-1]]
    Output: 2
    Explanation: We have the following two paths:
    1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2)
    2. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2)



    Input: grid = [[1,0,0,0],[0,0,0,0],[0,0,0,2]]
    Output: 4
    Explanation: We have the following four paths:
    1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2),(2,3)
    2. (0,0),(0,1),(1,1),(1,0),(2,0),(2,1),(2,2),(1,2),(0,2),(0,3),(1,3),(2,3)
    3. (0,0),(1,0),(2,0),(2,1),(2,2),(1,2),(1,1),(0,1),(0,2),(0,3),(1,3),(2,3)
    4. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2),(2,3)



    Input: grid = [[0,1],[2,0]]
    Output: 0
    Explanation: There is no path that walks over every empty square exactly once.
    Note that the starting and ending square can be anywhere in the grid.


    Constraints:

    m == grid.length
    n == grid[i].length
    1 <= m, n <= 20
    1 <= m * n <= 20
    -1 <= grid[i][j] <= 2
    There is exactly one starting cell and one ending cell.

  */
  /*
    Observer:
       the walk path should walk over every non-obstacle square exactly once.
       result can be 0
       us marking as visited
       dfs backtracking

    O(3 ^N) Time
    O( N) space
  */

  int M, N; // rows and columns number
  int[][] grid;
  int ans;
  int[] r4 = {0, 0, 1, -1};
  int[] c4 = {1, -1, 0, 0};

  public int uniquePathsIII(int[][] grid) {
    int remain = 0, row0 = 0, col0 = 0;

    this.M = grid.length;
    this.N = grid[0].length;

    for (int r = 0; r < M; ++r)
      for (int c = 0; c < N; ++c) {
        int cell = grid[r][c];
        if (cell >= 0) remain += 1;
        if (cell == 1) { // start cell
          row0 = r;
          col0 = c;
        }
      }

    ans = 0;
    this.grid = grid;
    backtrack(row0, col0, remain);
    return this.ans;
  }

  protected void backtrack(int row, int col, int remain) {
    if (this.grid[row][col] == 2 && remain == 1) { // walk all cell and reach the target cell
      ans += 1;
      return;
    }

    int v = grid[row][col];
    grid[row][col] = -4; // visited by marking it as -4
    remain -= 1;

    for (int i = 0; i < 4; ++i) {
      int r_ = row + r4[i];
      int c_ = col + c4[i];
      // invalid or visited or obstacle
      if (0 > r_ || r_ >= M || 0 > c_ || c_ >= N || grid[r_][c_] < 0) continue;
      backtrack(r_, c_, remain);
    }
    grid[row][col] = v; // unmark
  }
}
