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

package bfs;

import java.util.LinkedList;
import java.util.Queue;

public class Leetcode1162AsFarfromLandasPossible {
  /*

  n == grid.length
  n == grid[i].length
  1 <= n <= 100
  grid[i][j] is 0 or 1

  n x n grid where 0 represents water and 1 represents land
  find a water cell such that its distance to the nearest land
  cell is maximized, and return the distance.
  If no land or water exists in the grid, return -1.

  The distance used in this problem is the Manhattan distance:
  the distance between two cells (x0, y0) and (x1, y1) is
  |x0 - x1| + |y0 - y1|.

  Idea:
  think of this problem in a backwards way,
  expanding outward from each land cell.

  O(N^2) time and space
  */
  // q: cells of 1
  // s: max steps, record max steps in advance;
  // not use q.size() in for loop
  // c: cell
  // grid[x][y] = 1; // visited
  public int maxDistance(int[][] grid) {
    // 1 <= n <= 100
    int N = grid.length;
    Queue<int[]> q = new LinkedList();
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++) if (grid[i][j] == 1) q.offer(new int[] {i, j});

    int s = 0;
    int[][] d4 = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    while (!q.isEmpty()) {
      s++; // record in advance
      int l = q.size();
      while (l-- > 0) {
        int[] c = q.poll();
        int x_ = c[0], y_ = c[1];
        for (int[] d : d4) { // 4 directions
          int x = x_ + d[0], y = y_ + d[1];
          if (x < N && y < N && x >= 0 && y >= 0 && grid[x][y] == 0) {
            grid[x][y] = 1;
            q.offer(new int[] {x, y});
          }
        }
      }
    }
    return --s == 0 ? -1 : s;
  }
}
