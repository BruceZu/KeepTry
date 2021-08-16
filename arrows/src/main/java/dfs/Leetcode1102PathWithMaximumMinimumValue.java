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

import java.util.*;

public class Leetcode1102PathWithMaximumMinimumValue {
  /* 1102. Path With Maximum Minimum Value
   Given an m x n integer matrix grid, return the
   maximum score of a path starting at (0, 0) and ending at (m - 1, n - 1)
   moving in the 4 cardinal directions.

   The score of a path is the minimum value in that path.
   For example, the score of the path 8 → 4 → 5 → 9 is 4.

   m == grid.length
   n == grid[i].length
   1 <= m, n <= 100
   0 <= grid[i][j] <= 109

    Example 1   [[3,4,6,3,4],
                 [0,2,1,1,7],
                 [8,8,3,2,7],
                 [3,2,4,9,8],
                 [4,1,2,0,0],
                 [4,6,5,4,3]]
    Output: 3

   Example 2:
          2 0 5 2 0
          2 4 4 4 3
          1 5 0 0 0
          5 4 4 3 1
          1 3 1 5 3
   Output: 2

  Example 3
  [[1,0,1,1,1,0,0],
   [0,1,1,1,1,1,0],
   [1,0,1,1,1,1,0],
   [1,1,1,0,1,1,0],
   [1,0,1,1,0,1,0]]
   Output: 0
  */
  /*---------------------------------------------------------------------------
  Understanding:
    greedy does not work, see example 2
  Idea:  DFS, but Time Limit Exceeded
    O(2^min(n,m)) time,
    O(N*M) space
   */
  private static int[][] d4 = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

  public int maximumMinimumPath___(int[][] grid) {
    Set<String> v = new HashSet();
    v.add(0 + "-" + 0);
    return dfs(grid, 0, 0, grid[0][0], v);
  }

  private int dfs(int[][] g, int r, int c, int maxmin, Set<String> v) {
    if (r == g.length - 1 && c == g[0].length - 1) return Math.min(g[r][c], maxmin);
    int answer = 0;
    for (int[] d : d4) {
      int nr = r + d[0], nc = c + d[1];
      if (nr >= 0 && nc >= 0 && nr < g.length && nc < g[0].length) {
        String k = nr + "-" + nc;
        if (!v.contains(k)) {
          v.add(k);
          answer = Math.max(answer, dfs(g, nr, nc, Math.min(maxmin, g[nr][nc]), v));
          v.remove(k);
        }
      }
    }
    return answer;
  }
  /*---------------------------------------------------------------------------
  Idea:
   get cell value range, and sort it
   Binary search to find the upper boundary cell value
   test each one's connection with DFS or BFS
   here is BFS
  O(M*N*logV) time, V is distinct cell value number, at most it is M*N
  O(M*N) space
  */
  public int maximumMinimumPath__(int[][] grid) {
    int M = grid.length, N = grid[0].length;
    int small = Math.min(grid[0][0], grid[M - 1][N - 1]);
    Set<Integer> set = new HashSet<>();
    for (int i = 0; i < M; i++)
      for (int j = 0; j < N; j++) if (grid[i][j] <= small) set.add(grid[i][j]);

    Integer[] value = new Integer[set.size()];
    set.toArray(value);
    Arrays.sort(value);
    if (bfs(grid, M, N, small)) return small;
    int l = 0, r = value.length - 1;
    while (l < r - 1) {
      int mid = l + r >>> 1;
      if (bfs(grid, M, N, value[mid])) l = mid;
      else r = mid;
    }
    return value[l];
  }

  private boolean bfs(int[][] grid, int M, int N, int small) {
    Queue<int[]> q = new LinkedList<>();
    Set<Integer> v = new HashSet<>();

    v.add(0);
    q.offer(new int[] {0, 0});
    while (!q.isEmpty()) {
      int[] cur = q.poll();
      if (cur[0] == M - 1 && cur[1] == N - 1) return true;
      for (int[] d : d4) {
        int nr = d[0] + cur[0], nc = +d[1] + cur[1];
        int id = nr * N + nc;
        if (!v.contains(id) && nr >= 0 && nc >= 0 && nr < M && nc < N && grid[nr][nc] >= small) {
          v.add(id);
          q.offer(new int[] {nr, nc});
        }
      }
    }
    return false;
  }

  /*---------------------------------------------------------------------------
  Idea:
   sort all cell by cell value in descending
   union found the visited cell one by one and check start cell and end cell connection

  O(M*N*log(M*N)) time
  O(M*N) space
  */
  public int maximumMinimumPath_(int[][] grid) {
    int M = grid.length, N = grid[0].length;
    int[] root = new int[M * N], rank = new int[M * N];
    for (int i = 0; i < M * N; i++) root[i] = i;

    int[][] g = new int[M * N][3];
    int k = 0;
    for (int i = 0; i < M; i++) for (int j = 0; j < N; j++) g[k++] = new int[] {i, j, grid[i][j]};
    Arrays.sort(g, (a, b) -> b[2] - a[2]);
    Set<Integer> v = new HashSet<>();
    for (int[] c : g) {
      int ID = c[0] * N + c[1];
      v.add(ID);
      for (int[] d : d4) {
        int nr = d[0] + c[0], nc = d[1] + c[1];
        int id = nr * N + nc;
        if (nr >= 0 && nc >= 0 && nr < M && nc < N && v.contains(id)) union(root, rank, ID, id);
        if (find(root, 0) == find(root, M * N - 1)) return c[2];
      }
    }
    return -1; // never come here;
  }

  private int find(int[] root, int i) {
    int start = i;
    while (root[i] != i) i = root[i];

    int r = i;
    while (start != i) {
      int n = root[start];
      root[start] = i;
      start = n;
    }
    return r;
  }

  private void union(int[] root, int[] rank, int i, int j) {
    int ri = find(root, i), rj = find(root, j);
    if (ri == rj) return;
    // union
    if (rank[ri] == rank[rj]) {
      root[ri] = rj;
      rank[rj]++;
    } else if (rank[ri] < rank[rj]) root[ri] = rj;
    else root[rj] = ri;
  }

  public static void main(String[] args) {
    Leetcode1102PathWithMaximumMinimumValue t = new Leetcode1102PathWithMaximumMinimumValue();
    t.maximumMinimumPath_(
        new int[][] {
          {1, 2, 3, 5, 1, 1, 0},
          {5, 1, 4, 1, 3, 4, 5},
          {1, 3, 0, 1, 3, 2, 0},
          {2, 1, 0, 4, 0, 0, 2},
          {1, 1, 0, 3, 4, 0, 3},
        });
  }
  /*---------------------------------------------------------------------------
  Idea:
    Djkstra
      weight is not shorted distance, instead it is the min node value in the path.
      affect the Heap sort priority.
      any path further weight only <= current weight.
      max heap keep item [row, column, min node value on the path]
    The heap at most keep M*N items
    So O(M*N*logV) time, V is distinct cell value number, at most it is M*N
       O(M*N) space
   */

  public int maximumMinimumPath(int[][] grid) {
    int M = grid.length, N = grid[0].length;
    Queue<int[]> q = new PriorityQueue<>((a, b) -> b[2] - a[2]); // max heap
    Set<Integer> v = new HashSet<>();

    q.offer(new int[] {0, 0, grid[0][0]}); // note the third value is grid[row][column]
    v.add(0);
    while (!q.isEmpty()) {
      int[] cur = q.poll();
      if (cur[0] == M - 1 && cur[1] == N - 1) return cur[2];
      for (int[] d : d4) {
        int nr = cur[0] + d[0], nc = cur[1] + d[1];
        int id = nr * N + nc; // note it is N not M
        if (!v.contains(id) && nr >= 0 && nc >= 0 && nr < M && nc < N) {
          v.add(id);
          q.offer(new int[] {nr, nc, Math.min(cur[2], grid[nr][nc])});
        }
      }
    }
    return -1; // never come here;
  }
}
