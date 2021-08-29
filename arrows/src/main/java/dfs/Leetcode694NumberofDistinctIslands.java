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

package dfs;

import java.util.HashSet;
import java.util.Set;

public class Leetcode694NumberofDistinctIslands {
  /*

    694. Number of Distinct Islands

     You are given an m x n binary matrix grid.
     An island is a group of 1's (representing land) connected 4-directionally
     (horizontal or vertical.)
     You may assume all four edges of the grid are surrounded by water.

     An island is considered to be the same as another
     if and only if one island can be translated (and not rotated or reflected) to equal the other.

     Return the number of distinct islands.


     Input: grid = [[1,1,0,0,0],
                    [1,1,0,0,0],
                    [0,0,0,1,1],
                    [0,0,0,1,1]]
     Output: 1


     Input: grid = [[1,1,0,1,1],
                    [1,0,0,0,0],
                    [0,0,0,0,1],
                    [1,1,0,1,1]]
     Output: 3



     Constraints:

         m == grid.length
         n == grid[i].length
         1 <= m, n <= 50
         grid[i][j] is either 0 or 1.
  */
  /*
  Idea
    mark cell from 1 to 0 as visited
    dfs, the first visited cell of island as (0,0), all same island cells' recalculated coordinates are connected
    in string by coordinates or direction, and put in set.
    result is the set size
    Note: need not sort. because all island are visited in the same dfs way.
   O(M*N) time. 0 cell will be visited twice. by dfs and outer loops
              1 cells are visited three times. 2 of dfs.
   O(N*N) space by Set
  */
  int m, n;
  int[][] g;
  int[][] d4 = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

  public int numDistinctIslands(int[][] g) {
    this.g = g;
    m = g.length;
    n = g[0].length;
    Set<String> r = new HashSet();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (g[i][j] == 1) {
          StringBuilder s = new StringBuilder();
          g[i][j] = 0; // mark as visited
          dfs(g, i, j, i, j, s);
          r.add(s.toString());
        }
      }
    }
    return r.size();
  }

  private void dfs(int[][] g, int r, int c, int i, int j, StringBuilder s) {
    s.append(i - r).append(j - c);
    for (int[] d : d4) {
      int ni = i + d[0], nj = j + d[1];
      if (ni >= 0 && ni < m && nj >= 0 && nj < n && g[ni][nj] == 1) {
        g[ni][nj] = 0;
        dfs(g, r, c, ni, nj, s);
      }
    }
  }
}
