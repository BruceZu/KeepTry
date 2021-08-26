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

import java.util.LinkedList;
import java.util.Queue;

public class Leetcode200NumberofIslands {
  /*
     200. Number of Islands

          m x n 2D binary grid
          represents a map of '1's (land) and '0's (water),
          return the number of islands.

          An island is surrounded by water and is formed
          by connecting adjacent lands horizontally or vertically.
          You may assume all four edges of the grid are all surrounded by water.


        Input: grid = [
          ["1","1","1","1","0"],
          ["1","1","0","1","0"],
          ["1","1","0","0","0"],
          ["0","0","0","0","0"]
        ]
        Output: 1

        Input: grid = [
          ["1","1","0","0","0"],
          ["1","1","0","0","0"],
          ["0","0","1","0","0"],
          ["0","0","0","1","1"]
        ]
        Output: 3

        Input: grid = [
           ["1","0","1"],
           ["0","1","0"],
           ["1","0","1"]]
        Output: 5


        Constraints:

            m == grid.length
            n == grid[i].length
            1 <= m, n <= 300
            grid[i][j] is '0' or '1'.
  */

  /*
   Idea:
    BFS
    - change grid[i][j] from '1' to '0' to mark as visited
      before put it in queue
    - each cell can be accessed at most twice O(m*n) time and
      the queue at most keep  min{m,n} cells O(min{m,n}) space

    Alternative
    - DFS the Space complexity : worst case O(M×N)
      in case that the grid map is filled with lands where DFS goes by M×N deep
    - Union Found: O(M×N) time and space
  */
  private int[][] d4 = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
  private int result = 0;
  private int m, n;

  public int numIslands(char[][] grid) {
    m = grid.length;
    n = grid[0].length;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        bfs(grid, i, j);
      }
    }
    return result;
  }

  private void bfs(char[][] g, int i, int j) {
    if (g[i][j] == '0') return;
    result++;
    Queue<int[]> q = new LinkedList();
    g[i][j] = '0'; // mark as visited before offer it in queue
    q.offer(new int[] {i, j});
    while (!q.isEmpty()) {
      int[] cur = q.poll();
      for (int[] d : d4) {
        int r = cur[0] + d[0], c = cur[1] + d[1];
        if (r >= 0 && r < m && c >= 0 && c < n && g[r][c] == '1') {
          g[r][c] = '0';
          q.offer(new int[] {r, c});
        }
      }
    }
  }
}
