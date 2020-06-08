//  Copyright 2020 The KeepTry Open Source Project
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

public class Leetcode1463CherryPickupII {
  //  Time: O(9 * m * n^2), where m is number of rows, n is number of cols of grid.
  //  Space: O(m * n^2)
  public static int cherryPickup(int[][] g) {
    if (g == null || g[0] == null || g[0].length == 0) return 0;
    int M = g.length, N = g[0].length;
    int[][][] dp = new int[M][N][N];
    // init
    dp[0][0][N - 1] = g[0][0] + g[0][N - 1];
    for (int Xc = 0; Xc <= N - 1; Xc++) {
      for (int Yc = 0; Yc <= N - 1; Yc++) {
        // Note: use ||
        if (Xc != 0 || Yc != N - 1) dp[0][Xc][Yc] = -1;
      }
    }
    // calculate dp[r][Xc][Yc]
    int r = 0;
    for (int s = 1; s <= M - 1; s++) {
      for (int Xc = 0; Xc <= N - 1; Xc++) {
        for (int Yc = 0; Yc <= N - 1; Yc++) {

          int ll = (Xc - 1 < 0 || Yc - 1 < 0) ? -1 : dp[s - 1][Xc - 1][Yc - 1];
          int li = Xc - 1 < 0 ? -1 : dp[s - 1][Xc - 1][Yc];
          int lr = (Xc - 1 < 0 || Yc + 1 > N - 1) ? -1 : dp[s - 1][Xc - 1][Yc + 1];
          int il = Yc - 1 < 0 ? -1 : dp[s - 1][Xc][Yc - 1];
          int ii = dp[s - 1][Xc][Yc];
          int ir = Yc + 1 > N - 1 ? -1 : dp[s - 1][Xc][Yc + 1];
          int rl = (Xc + 1 > N - 1 || Yc - 1 < 0) ? -1 : dp[s - 1][Xc + 1][Yc - 1];
          int ri = Xc + 1 > N - 1 ? -1 : dp[s - 1][Xc + 1][Yc];
          int rr = (Xc + 1 > N - 1 || Yc + 1 > N - 1) ? -1 : dp[s - 1][Xc + 1][Yc + 1];

          int preMax = Math.max(ll, li);
          preMax = Math.max(preMax, lr);
          preMax = Math.max(preMax, il);
          preMax = Math.max(preMax, ii);
          preMax = Math.max(preMax, ir);
          preMax = Math.max(preMax, rl);
          preMax = Math.max(preMax, ri);
          preMax = Math.max(preMax, rr);
          if (preMax == -1) {
            dp[s][Xc][Yc] = -1;
            continue;
          }

          dp[s][Xc][Yc] = preMax + (Xc == Yc ? g[s][Xc] : g[s][Xc] + g[s][Yc]);
          if (s == M - 1) {
            r = Math.max(r, dp[s][Xc][Yc]);
          }
        }
      }
    }
    return r;
  }

  // use dfs recursion. result is dp[0][0][N-1]
  public int cherryPickup2(int[][] grid) {
    int M = grid.length, N = grid[0].length;
    Integer[][][] dp = new Integer[M][N][N];
    return dfs(grid, M, N, 0, 0, N - 1, dp);
  }

  int dfs(int[][] grid, int M, int N, int r, int Xc, int Yc, Integer[][][] dp) {
    if (r == M) return 0; //
    if (dp[r][Xc][Yc] != null) return dp[r][Xc][Yc];
    int nMax = 0;
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        int nXc = Xc + x, nYc = Yc + y;
        if (0 <= nXc && nXc <= N - 1 && 0 <= nYc && nYc <= N - 1) {
          nMax = Math.max(nMax, dfs(grid, M, N, r + 1, nXc, nYc, dp));
        }
      }
    }
    int cherries = Xc == Yc ? grid[r][Xc] : grid[r][Xc] + grid[r][Yc];
    return dp[r][Xc][Yc] = nMax + cherries;
  }
}
