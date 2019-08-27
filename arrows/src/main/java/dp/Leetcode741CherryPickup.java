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
    public static int cherryPickup2(int[][] g) {
        if (g == null) return 0;
        for (int[] r : g) {
            if (r == null || r.length == 0) return 0;
        }

        int N = g.length;
        int dp[][] = new int[N][N];
        dp[0][0] = g[0][0];
        for (int t = 1; t <= 2 * N - 2; t++) {
            for (int r = Math.min(t, N - 1); 0 <= r; r--) {
                for (int rr = Math.min(t, N - 1); 0 <= rr; rr--) {
                    int j = t - r, jj = t - rr;
                    if (0 <= j && j <= Math.min(t, N - 1) && 0 <= jj && jj <= Math.min(t, N - 1)) {

                        if (g[r][j] == -1 || g[rr][jj] == -1) {
                            dp[r][rr] = -1;
                            continue;
                        }

                        int ll = (r == t || rr == t) ? -1 : dp[r][rr];
                        int lu = (r == t || rr - 1 < 0) ? -1 : dp[r][rr - 1];
                        int ul = (r - 1 < 0 || rr == t) ? -1 : dp[r - 1][rr];
                        int uu = (r - 1 < 0 || rr - 1 < 0) ? -1 : dp[r - 1][rr - 1];

                        int pre = -1;
                        if (ll != -1) pre = Math.max(pre, ll);
                        if (lu != -1) pre = Math.max(pre, lu);
                        if (ul != -1) pre = Math.max(pre, ul);
                        if (uu != -1) pre = Math.max(pre, uu);
                        dp[r][rr] = pre;

                        if (dp[r][rr] == -1) {
                            continue;
                        }

                        // Note: use () here
                        dp[r][rr] += g[r][j] + (r == rr ? 0 : g[rr][jj]);
                    }
                }
            }
        }
        int r = dp[N - 1][N - 1];
        return r == -1 ? 0 : r;
    }

    // bottom up runtime O(N^3), space O(N^3)
    public static int cherryPickup3(int[][] g) {
        if (g == null) return 0;
        for (int[] r : g) {
            if (r == null || r.length == 0) return 0;
        }

        // dp[r][rr]: max cherries got by walking simultaneously from (0,0) via 2 paths to (r,t-r)
        // and (rr,t-rr)
        // When r = N-1, rr = N-1, t = 2*N -2. Got the answer.
        int N = g.length;
        int dp[][][] = new int[2 * N - 1][N][N];
        dp[0][0][0] = g[0][0];
        for (int t = 1; t <= 2 * N - 2; t++) {
            for (int r = 0; r <= Math.min(t, N - 1); r++) {
                for (int rr = 0; rr <= Math.min(t, N - 1); rr++) {
                    int j = t - r, jj = t - rr;
                    if (0 <= j && j <= Math.min(t, N - 1) && 0 <= jj && jj <= Math.min(t, N - 1)) {
                        if (g[r][j] == -1 || g[rr][jj] == -1) {
                            dp[t][r][rr] = Integer.MIN_VALUE;
                            continue;
                        }

                        // max cherries got from left A B paths:
                        int pre = Integer.MIN_VALUE;
                        if (r <= t - 1 && rr <= t - 1) { // or use getPre2()
                            pre = Math.max(pre, dp[t - 1][r][rr]);
                        }
                        if (r <= t - 1 && 0 <= rr - 1) {
                            pre = Math.max(pre, dp[t - 1][r][rr - 1]);
                        }
                        if (0 <= r - 1 && rr <= t - 1) {
                            pre = Math.max(pre, dp[t - 1][r - 1][rr]);
                        }
                        if (0 <= r - 1 && 0 <= rr - 1)
                            pre = Math.max(pre, dp[t - 1][r - 1][rr - 1]);

                        if (pre == Integer.MIN_VALUE) {
                            dp[t][r][rr] = Integer.MIN_VALUE;
                            continue;
                        }

                        // Note: use () here
                        int curAB = g[r][j] + (r == rr ? 0 : g[rr][jj]);
                        dp[t][r][rr] = pre + curAB;
                    }
                }
            }
        }
        // Note: when resule is -1, for user it should be 0;
        int r = dp[2 * N - 2][N - 1][N - 1];
        return r == Integer.MIN_VALUE ? 0 : r;
    }

    private static int getPre2(int[][][] dp, int t, int r, int rr) {
        if (r < 0 || rr < 0 || t < r || t < rr) return -1;
        return dp[t][r][rr];
    }

    private static int r(int[][] g, int r, int rr, int t, int[][][] cache) {
        // Note: border
        if (r == 0 && rr == 0 && t == 0) {
            cache[0][0][0] = g[0][0];
            return cache[0][0][0];
        }

        // calculate
        int N = g.length;
        int j = t - r, jj = t - rr;
        if (0 <= r && r <= Math.min(N - 1, t) &&
            0 <= rr && rr <= Math.min(N - 1, t) &&
            0 <= j  && j <= Math.min(N - 1, t) &&
            0 <= jj && jj <= Math.min(N - 1, t) &&
            g[r][j] != -1  && g[rr][jj] != -1) {
            if (cache[t][r][rr] != 0) return cache[t][r][rr];

            int maxPre2 = -1;
            maxPre2 = Math.max(maxPre2, r(g, r, rr, t - 1, cache));
            maxPre2 = Math.max(maxPre2, r(g, r, rr - 1, t - 1, cache));
            maxPre2 = Math.max(maxPre2, r(g, r - 1, rr, t - 1, cache));
            maxPre2 = Math.max(maxPre2, r(g, r - 1, rr - 1, t - 1, cache));

            // Note:
            if (maxPre2 != -1) {
                cache[t][r][rr] = maxPre2 + g[r][j] + (r == rr ? 0 : g[rr][jj]);
            } else cache[t][r][rr] = -1;
            return cache[t][r][rr];
        }
        return -1;
    }

    // top down runtime O(?), space O(N^3)
    public static int cherryPickup(int[][] g) {
        if (g == null) return 0;
        for (int[] r : g) {
            if (r == null || r.length == 0) return 0;
        }
        //
        int N = g.length;
        int[][][] cache = new int[2 * N - 1][N][N];
        int r = r(g, N - 1, N - 1, 2 * N - 2, cache);
        return r == -1 ? 0 : r;
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
