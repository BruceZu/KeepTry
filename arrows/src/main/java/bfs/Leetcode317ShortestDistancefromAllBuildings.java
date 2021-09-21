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

package bfs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/*
Leetcode 317. Shortest Distance from All Buildings

You are given an m x n grid of values 0, 1, or 2, where:
    each 0 marks an empty land that you can pass by freely,
    each 1 marks a building that you cannot pass through, and
    each 2 marks an obstacle that you cannot pass through.

You want to build a house on an empty land that reaches all buildings in the shortest total travel distance.
You can only move up, down, left, and right.

Return the shortest travel distance for such a house.
If it is not possible to build such a house according to the above rules, return -1.

The total travel distance is the sum of the distances between the houses
of the friends and the meeting point.
The distance is calculated using Manhattan Distance, where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.


Input: grid = [
[1,0,2,0,1],
[0,0,0,0,0],
[0,0,1,0,0]
]
Output: 7
Explanation: Given three buildings at (0,0), (0,4), (2,2), and an obstacle at (0,2).
The point (1,2) is an ideal empty land to build a house,
as the total travel distance of 3+3+1=7 is minimal.
So return 7.

Input: grid = [[1,0]]
Output: 1

Input: grid = [[1]]
Output: -1

Constraints:
    m == grid.length
    n == grid[i].length
    1 <= m, n <= 50
    grid[i][j] is either 0, 1, or 2.
    There will be at least one building in the grid.
 */
public class Leetcode317ShortestDistancefromAllBuildings {
  /*
  Watch:
   graph with each edge to have the same weight of 1.
   BFS can be used to find the shortest path between a starting cell and any other reachable cell.

  BFS from building to empty cell.
  When there are fewer houses than empty lands, then this approach will require less time
  than the previous approach and vice versa.

  Prepare distance[][] sum, distance[i][j] keeps steps from cell[i][j] to all or part buildings
  because some building maybe not reachable
  So need to know distance[i][j] is of all or part buildings, a way is keep num[i][j] for checking

  At last check the smallest cell value of distance[i][j] sum and num[i][j]=total building number

  Runtime O((M*N）^2)
  Space O(M*N)
  M is rows number
  N is column number
  */
  static int m, n;
  static int[] d4 = {-1, 0, 1, 0, -1};

  public static int shortestDistance__(int[][] g) {
    m = g.length;
    n = g[0].length;
    // sum[i][j]keep sum distance from an empty sum[i][j] cell to num[i][j] buildings
    int[][] sum = new int[m][n];
    int[][] num = new int[m][n];

    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        if (g[i][j] == 1) { // a building
          bfs(g, i, j, sum, num);
        }
      }
    }

    int total = 0; // total buildings number in grid
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        if (g[i][j] == 1) {
          total++;
        }
      }
    }
    int r = Integer.MAX_VALUE;
    boolean findOne = false;
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        if (g[i][j] == 0 && num[i][j] == total) {
          findOne = true;
          r = Math.min(r, sum[i][j]);
        }
      }
    }
    return findOne ? r : -1;
  }

  private static void bfs(int[][] g, int i, int j, int[][] sum, int[][] num) {
    Queue<Integer> q = new LinkedList<>();
    boolean[][] vis = new boolean[m][n];
    q.offer(i * n + j);
    vis[i][j] = true;

    int steps = 0;
    while (!q.isEmpty()) {
      steps++;
      for (int size = q.size(); size > 0; size--) {
        int cur = q.poll();
        j = cur % n;
        i = cur / n;
        for (int k = 0; k < 4; ++k) {
          int r = i + d4[k], c = j + d4[k + 1];
          if (r >= 0 && r < m && c >= 0 && c < n && g[r][c] == 0 && !vis[r][c]) {
            vis[r][c] = true;

            sum[r][c] += steps;
            num[r][c]++;

            q.offer(r * n + c);
          }
        }
      }
    }
  }

  /*---------------------------------------------------------------------------
  BFS from Empty Land to All buildings
  Runtime O((M*N）^2)
  Space O(M*N) used by BFS
  M is rows number
  N is column number
  */
  public static int shortestDistance_(int[][] grid) {
    m = grid.length;
    n = grid[0].length;

    int total = 0; // building numbers
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        if (grid[i][j] == 1) {
          total++;
        }
      }
    }

    int r = Integer.MAX_VALUE;
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        if (grid[i][j] == 0) { // an empty cell
          r = Math.min(r, bfs(grid, i, j, total));
        }
      }
    }
    return r == Integer.MAX_VALUE ? -1 : r;
  }
  /*
    from each space point to calculate all distance from it to reachable buildings
    if it cannot reach all houses, return MAX value to make it be ignored
  */
  private static int bfs(int[][] g, int i, int j, int total) {
    Queue<Integer> q = new LinkedList<>();
    Set<Integer> v = new HashSet<>();
    int sum = 0;
    int num = 0;
    int steps = 0;
    m = g.length;
    n = g[0].length;

    q.offer(i * n + j);
    v.add(i * n + j);

    while (!q.isEmpty()) {
      steps++;
      for (int size = q.size(); size > 0; size--) {
        int cur = q.poll();
        i = cur / n;
        j = cur % n;
        for (int k = 0; k < 4; ++k) {
          int x = i + d4[k], y = j + d4[k + 1];
          if (x >= 0 && x < m && y >= 0 && y < n && !v.contains(x * n + y) && g[x][y] != 2) {
            v.add(x * n + y);
            if (g[x][y] == 1) {
              num++;
              sum += steps;
            }
            if (g[x][y] == 0) q.offer(x * n + y);
          }
        }
      }
    }
    return num == total ? sum : Integer.MAX_VALUE;
  }

  /*
    BFS from Houses to Empty Land (Optimized)

    using a `visited` variable to mark visited cells. it is only used for
    a building, it is local variable.

    Alternative: use the provided grid: During the first BFS we can change the visited empty cell values from 0 to -1. next building -1->-2, and so on...
    It is global variable and will affect different BFS for different buildings.
    each BFS only visit cell that is reachable from all buildings.


    The `num` variable is can be saved too. this requires carefully tracking the result.
      - only when sum[][] is changed
      - need re-initial to be MAX for each building.
    In short get the minimum value for each building updated sum[][].
  */

  public static int shortestDistance(int[][] g) {
    int d4[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    int M = g.length;
    int N = g[0].length;

    int[][] sum = new int[M][N];
    int empty = 0; // empty cell value
    int a = Integer.MAX_VALUE;

    for (int i = 0; i < M; ++i) {
      for (int j = 0; j < N; ++j) {
        if (g[i][j] == 1) { // a building
          a = Integer.MAX_VALUE;
          Queue<int[]> q = new LinkedList<>();
          q.offer(new int[] {i, j});

          int steps = 0;
          while (!q.isEmpty()) {
            steps++;
            for (int s = q.size(); s > 0; s--) {
              int[] cur = q.poll();

              for (int[] d : d4) {
                int r = cur[0] + d[0], c = cur[1] + d[1];

                if (r >= 0 && r < M && c >= 0 && c < N && g[r][c] == empty) {
                  g[r][c]--; // here

                  sum[r][c] += steps;
                  q.offer(new int[] {r, c});

                  a = Math.min(a, sum[r][c]);
                }
              }
            }
          } // current building is done
          empty--; // for next building
        }
      }
    }
    return a == Integer.MAX_VALUE ? -1 : a;
  }

  /* --------------------------------------------------------------------------
  some scenario test
  */
  public static void main(String[] args) {
    int[][] grid =
        new int[][] {
          {1, 0, 2, 0, 1},
          {0, 0, 0, 0, 0},
          {0, 0, 1, 0, 0}
        };
    System.out.println(shortestDistance(grid) == 7);
    System.out.println(shortestDistance__(grid) == 7);
    System.out.println(shortestDistance_(grid) == 7);

    // There is building (2,3) not reachable by all empty cells
    grid =
        new int[][] {
          {1, 0, 2, 2, 2, 0},
          {0, 0, 2, 0, 2, 0},
          {1, 0, 2, 1, 2, 0},
          {0, 0, 2, 2, 2, 0},
          {1, 0, 0, 0, 0, 0}
        };
    System.out.println(shortestDistance__(grid) == -1);
    System.out.println(shortestDistance_(grid) == -1);

    // E.g. the last building can not be reachable by empty cell[0][1]
    grid =
        new int[][] {
          {1, 0, 0, 1, 0, 0, 1},
          {1, 0, 2, 2, 2, 0, 0},
          {0, 1, 2, 0, 2, 0, 0},
          {0, 0, 2, 2, 2, 0, 1},
          {0, 0, 0, 0, 0, 0, 1}
        };
    System.out.println(shortestDistance__(grid) == -1);
    System.out.println(shortestDistance_(grid) == -1);
    // all buildings are reachable from all empty cell
    grid =
        new int[][] {
          {1, 0, 0, 1, 0, 0, 1},
          {1, 0, 2, 2, 2, 0, 0},
          {0, 0, 2, 0, 2, 0, 0},
          {0, 0, 2, 2, 2, 0, 1},
          {0, 0, 0, 0, 0, 0, 1}
        };
    System.out.println(shortestDistance__(grid) == 31);
    System.out.println(shortestDistance_(grid) == 31);
  }
}
