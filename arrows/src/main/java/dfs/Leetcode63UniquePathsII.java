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

import java.util.HashMap;
import java.util.Map;

public class Leetcode63UniquePathsII {
  /*
  Start  top-left corner of a m x n grid
  can only move either down or right at any point
  trying to reach the bottom-right corner
  trying to reach the bottom-right corner
  How many unique paths would there be

  Input: obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
  Output: 2
  Input: obstacleGrid = [[0,1],[0,0]]
  Output: 1


  m == obstacleGrid.length
  n == obstacleGrid[i].length
  1 <= m, n <= 100
  obstacleGrid[i][j] is 0 or 1.
  */

  /*
   Note: start cell is not 1 but the end cell maybe be 1
   O(M*N) time, O(1）space
  */
  public static int uniquePathsWithObstacles(int[][] g) {
    int m = g.length, n = g[0].length;
    if (g[m - 1][n - 1] == 1) return 0;
    else g[m - 1][n - 1] = 1;
    for (int i = m - 1; i >= 0; i--) {
      for (int j = n - 1; j >= 0; j--) {
        if (i == m - 1 && j == n - 1) continue;
        if (g[i][j] == 1) g[i][j] = 0;
        else g[i][j] = (i + 1 == m ? 0 : g[i + 1][j]) + (j + 1 == n ? 0 : g[i][j + 1]);
      }
    }
    return g[0][0];
  }

  public static void main(String[] args) {
    uniquePathsWithObstacles(
        new int[][] {
          {0, 0, 0},
          {0, 1, 0},
          {0, 0, 0}
        });
  }
  // DFS with cache
  public int uniquePathsWithObstacles_(int[][] g) {
    if (g[g.length - 1][g[0].length - 1] == 1) return 0;
    return h(0, 0, g.length, g[0].length, g, new HashMap());
  }

  private int h(int r, int c, int m, int n, int[][] g, Map<String, Integer> cache) {
    int a = 0;
    String k = r + "" + c;
    if (!cache.containsKey(k)) {
      if (r == m - 1 && c == n - 1) a = 1;
      else if (r == m || c == n || g[r][c] == 1) a = 0;
      else a = h(r + 1, c, m, n, g, cache) + h(r, c + 1, m, n, g, cache);
      cache.put(k, a);
    }
    return cache.get(k);
  }
}
