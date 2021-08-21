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

package unionfind;

import java.util.*;

public class Leetcode827MakingALargeIsland {
  /*
  827. Making A Large Island
  n x n binary matrix grid.
  change at most one 0 to be 1.
  Return the size of the largest island in grid after applying this operation.
  An island is a 4-directionally connected group of 1s.

   Input: grid = [[1,0],
                  [0,1]]
   Output: 3

   Input: grid = [[1,1],
                  [1,0]]
   Output: 4

   Input: grid = [[1,1],
                  [1,1]]
   Output: 4

   n == grid.length
   n == grid[i].length
   1 <= n <= 500
   grid[i][j] is either 0 or 1.
  */
  /*
    Steps:
    1. paint all connected 1 value cells with a color Id start from 2.
       (kind of union() and find() ) to identify the island.
       and use a Map to keep  `color_id : size` relation.
    2. try each 0 value cell and check max size of island.
     Note
     - The same island can connect to a 0 value cell more than one side.
     -  there may be not any 0 value cell in grid

   Pros of paint all connected 1 value cells with a color Id
  - need not visited variable in BFS and DFS
  - updating the input grid to save space
  - each to calculate the max size of island

    O(N*M) Time, O(C) space
    M is row number, N is column number.
    C is colour number or island number.
    */
  public int largestIsland(int[][] grid) {
    int M = grid.length, N = grid[0].length;
    Map<Integer, Integer> size = new HashMap<>();
    int colour = 2;
    // painting
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        if (grid[i][j] == 1) {
          size.put(colour, paint1s(i, j, grid, M, N, colour));
          colour++;
        }
      }
    }
    // try largest island from each 0 value cell
    int max = 0;
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        if (grid[i][j] == 0) {
          Set<Integer> islColors = new HashSet();
          add(i - 1, j, M, N, grid, islColors);
          add(i + 1, j, M, N, grid, islColors);
          add(i, j - 1, M, N, grid, islColors);
          add(i, j + 1, M, N, grid, islColors);
          int tmp = 1; // if cell is marked as 1
          for (int c : islColors) tmp += size.get(c);
          max = Math.max(max, tmp);
        }
      }
    }
    // note max can be 0 in scenario where there is not any 0 value cell
    return max == 0 ? M * N : max;
  }

  private void add(int i, int j, int M, int N, int[][] grid, Set<Integer> islColors) {
    if (0 <= i && i < M && 0 <= j && j < N && grid[i][j] != 0) islColors.add(grid[i][j]);
  }
  // Assume cell[r][c] value is 1.
  //  1 Start from cell[r][c] to paint all connected 1 value cell to be colour Id
  //  2 return the size of the painted cells
  // no `visited` variable in DFS
  private int paint1s(int r, int c, int[][] g, int M, int N, int color) {
    if (r < 0 || r == M || c < 0 || c == N || g[r][c] != 1) return 0;
    g[r][c] = color;
    return 1
        + paint1s(r - 1, c, g, M, N, color)
        + paint1s(r + 1, c, g, M, N, color)
        + paint1s(r, c - 1, g, M, N, color)
        + paint1s(r, c + 1, g, M, N, color);
  }
}
