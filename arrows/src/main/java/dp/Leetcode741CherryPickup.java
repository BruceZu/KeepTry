//  Copyright 2019 The KeepTry Open Source Project
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

package dp;

/**
 * <pre>
 * In a N x N grid representing a field of cherries, each cell is one of three possible integers.
 *
 *
 *
 * 0 means the cell is empty, so you can pass through;
 * 1 means the cell contains a cherry, that you can pick up and pass through;
 * -1 means the cell contains a thorn that blocks your way.
 *
 *
 * Your task is to collect maximum number of cherries possible by following the rules below:
 *
 *
 *
 * Starting at the position (0, 0) and reaching (N-1, N-1) by moving right or down through valid path cells (cells with value 0 or 1);
 * After reaching (N-1, N-1), returning to (0, 0) by moving left or up through valid path cells;
 * When passing through a path cell containing a cherry, you pick it up and the cell becomes an empty cell (0);
 * If there is no valid path between (0, 0) and (N-1, N-1), then no cherries can be collected.
 *
 *
 *
 *
 * Example 1:
 *
 * Input: grid =
 * [[0, 1, -1],
 *  [1, 0, -1],
 *  [1, 1,  1]]
 * Output: 5
 * Explanation:
 * The player started at (0, 0) and went down, down, right right to reach (2, 2).
 * 4 cherries were picked up during this single trip, and the matrix becomes [[0,1,-1],[0,0,-1],[0,0,0]].
 * Then, the player went left, up, up, left to return home, picking up one more cherry.
 * The total number of cherries picked up is 5, and this is the maximum possible.
 *
 *
 * Note:
 *
 * grid is an N by N 2D array, with 1 <= N <= 50.
 * Each grid[i][j] is an integer in the set {-1, 0, 1}.
 * It is guaranteed that grid[0][0] and grid[N-1][N-1] are not -1.
 *
 *<a href="https://leetcode.com/problems/cherry-pickup/">leetcode 741 Cherry Pickup</a>
 *
 * Solutions:
 *   Assume g:square
 *  (0,0): left top point, value is not -1.
 *  (N-1, N-1): right down point. value is not -1.
 *
 */
public class Leetcode741CherryPickup {
    // bottom up runtime O(N^3), space O(N^2)
  public static int cherryPickup(int[][] g) {
    if (g == null || g[0] == null || g[0].length == 0) return 0;
    int N = g.length;

    // dp[Xr][Yr]: max number of cherry got by start concurrently along 2 paths from start cell,
    // after s steps , reached 2
    // location (Xr, s-Xr) and (Yr, s-Yr) on the diagonal line.
    int[][] dp = new int[N][N];
    // Note: initial value
    dp[0][0] = g[0][0];

    // Note: start from 1, else result always is -1
    for (int s = 1; s <= 2 * N - 2; s++) {
      // bottom up, step by step. or diagonal line by diagonal line
      // ascending order to save space complicity.
      for (int Xr = Math.min(s, N - 1); Xr >= 0; Xr--) {
        for (int Yr = Math.min(s, N - 1); Yr >= 0; Yr--) {
          int Xc = s - Xr, Yc = s - Yr;
          if (0 <= Xc && Xc <= N - 1 && 0 <= Yc && Yc <= N - 1) {
            // no way in these 2 location
            if (g[Xr][Xc] == -1 || g[Yr][Yc] == -1) {
              dp[Xr][Yr] = -1;
              continue;
            }

            // to see if these 2 locations reachable from start
            int ll = (Xr == s || Yr == s) ? -1 : dp[Xr][Yr];
            int lt = (Xr == s || Yr - 1 < 0) ? -1 : dp[Xr][Yr - 1];
            int tl = (Xr - 1 < 0 || Yr == s) ? -1 : dp[Xr - 1][Yr];
            int tt = (Xr - 1 < 0 || Yr - 1 < 0) ? -1 : dp[Xr - 1][Yr - 1];

            int maxPre = Math.max(ll, lt);
            maxPre = Math.max(maxPre, tl);
            maxPre = Math.max(maxPre, tt);

            if (maxPre == -1) {
              dp[Xr][Yr] = -1;
              continue;
            }
            // reachable.
            dp[Xr][Yr] = maxPre + (Xr == Yr ? g[Xr][Xc] : g[Xr][Xc] + g[Yr][Yc]);
          }
        }
      }
    }
    return dp[N - 1][N - 1] == -1 ? 0 : dp[N - 1][N - 1];
  }

  // ---------------------------------------------------------------------------------------
  public static void main(String[] args) {
    System.out.println(
        5
            == cherryPickup(
                new int[][] {
                  {0, 1, 0},
                  {0, 1, 1},
                  {1, 1, 0}
                }));
    System.out.println(
        5
            == cherryPickup(
                new int[][] {
                  {0, 1, -1},
                  {1, 1, 1},
                  {-1, 1, 0}
                }));
    System.out.println(
        2
            == cherryPickup(
                new int[][] {
                  {0, 0, 1},
                  {0, 1, 0},
                  {1, 0, 0}
                }));
    System.out.println(
        4
            == cherryPickup(
                new int[][] {
                  {1, 1},
                  {1, 1},
                }));
    System.out.println(
        8
            == cherryPickup(
                new int[][] {
                  {1, 1, 1},
                  {1, 1, 1},
                  {1, 1, 1}
                }));
    System.out.println(
        0
            == cherryPickup(
                new int[][] {
                  {1, 1, -1},
                  {1, -1, 1},
                  {-1, 1, 1}
                }));
    System.out.println(
        0
            == cherryPickup(
                new int[][] {
                  {1, -1, 1, -1, 1, 1, 1, 1, 1, -1},
                  {-1, 1, 1, -1, -1, 1, 1, 1, 1, 1},
                  {1, 1, 1, -1, 1, 1, 1, 1, 1, 1},
                  {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                  {-1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                  {1, -1, 1, 1, 1, 1, -1, 1, 1, 1},
                  {1, 1, 1, -1, 1, 1, -1, 1, 1, 1},
                  {1, -1, 1, -1, -1, 1, 1, 1, 1, 1},
                  {1, 1, -1, -1, 1, 1, 1, -1, 1, -1},
                  {1, 1, -1, 1, 1, 1, 1, 1, 1, 1}
                }));
    System.out.println(
        15
            == cherryPickup(
                new int[][] {
                  {1, 1, 1, 1, 0, 0, 0},
                  {0, 0, 0, 1, 0, 0, 0},
                  {0, 0, 0, 1, 0, 0, 1},
                  {1, 0, 0, 1, 0, 0, 0},
                  {0, 0, 0, 1, 0, 0, 0},
                  {0, 0, 0, 1, 0, 0, 0},
                  {0, 0, 0, 1, 1, 1, 1}
                }));
  }
}
