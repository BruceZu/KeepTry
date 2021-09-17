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

package dp;

public class Leetcode329LongestIncreasingPathinaMatrix {

  /*
  329. Longest Increasing Path in a Matrix

         Given an m x n integers matrix, return the length of the
         longest increasing path in matrix.

         From each cell, you can either move in four directions: left, right, up, or down.
         You may not move diagonally or move outside the boundary (i.e., wrap-around is not allowed).


    Input: matrix = [[9,9,4],
                     [6,6,8],
                     [2,1,1]]
    Output: 4
    Explanation: The longest increasing path is [1, 2, 6, 9].

    Input: matrix = [[3,4,5],
                     [3,2,6],
                     [2,2,1]]
    Output: 4
    Explanation: The longest increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed.

    Input: matrix = [[1]]
    Output: 1

    Constraints:
        m == matrix.length
        n == matrix[i].length
        1 <= m, n <= 200
        0 <= matrix[i][j] <= 231 - 1

  */
  /*
  Idea:
    watch
       9,9,4
       6,6,8
       2,1,1

    1> 9 has no neighbor value > it, then the len of the longest path from it is 1
    len of path start from 2 is 1 = length(6)
    len of path start from 4 is max(length(9), length(8)} + 1

    2> once length(x,y) is calculated, it will never be changed and can be used again
       by neighbor later  so cache it
    3> loop each cell and tracking the max length
    4> move always from a cell with a smaller value to a cell with a greater value.
       so need not visited variable


    O(m*n) time and space
  */
  static int[][] d4 = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
  static int m, n;
  static Integer[][] cache; // cache[i][j] is null: not be calculated yet

  public static int longestIncreasingPath(int[][] matrix) {
    m = matrix.length;
    n = matrix[0].length;
    cache = new Integer[m][n];
    int max = 0;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        max = Math.max(max, length(i, j, matrix));
      }
    }
    return max;
  }

  private static int length(int i, int j, int[][] matrix) {
    if (cache[i][j] != null) return cache[i][j];
    int v = matrix[i][j];
    int len = 1;
    for (int[] d : d4) {
      int r = i + d[0], c = j + d[1];
      if (r >= 0 && r < m && c >= 0 && c < n && matrix[r][c] > v) {
        len = Math.max(len, 1 + length(r, c, matrix));
      }
    }

    cache[i][j] = len;
    return cache[i][j];
  }

  /*
   f(i,j)= 1 + max{f(x,y)}
              (x,y) is a neighbor of(i,j) and
              matrix[x][y]>matrix[i][j]

  loop each one, if current cell is peak, a one that no neighbor value greater than its value
  then like idea in  "Topological order" or "Topological sorting": Peeling Onion

  1> collect all peak cells and start from them. not start from the naturel order the first or the last cell
  2> BFS
     visited<cell>
     2-D array as cache
     if a cell is a peak its length will not be changed then with it track the max

  O(m*n) time and space
   */

  /*
  Followup ====================================================================
   find the length of the longest path that have same values.
   [1,1],
   [5,5],
   [5,5]
   output 4
   */
  /*

   - a global max variable to tracking result

   Note
    1> "You may not move diagonally or move outside the boundary (i.e., wrap-around is not allowed)."
   so figure the max path from current cell from unvisited cells.
   Observe: because path of same value so need variable visited<cell location> to
            keep visited cell with the same value to avoid move back to previous visited cell
            so the `visited` is a local variable
            when calculate the longest path of cells with value 7 we never check cells without value 7
            so need not mark them as visited.
            so the `visited` is a local variable and only used for a target cell value

            loop each cell to try each cell as the start cell
            dfs() return longest path starting from the specific cell, in the unvisited cells whose value
                 is same as target.
                 so cache can NOT apply here as no way to know the visited cells.
       O(M*N) best case time,  O((M*N)^2) worse case time
       O(M*N) space used by visited

     2> It is NOT to calculate the max sum of connected cells with the same value
          That question  can also be done with DFS  in O(M*N) time and  if value is positive integer then the space can be O(1) by mark visited cell as -1
         As visited  is  a global  variable in this question
  */
  public static int longestPathWithSameValue(int[][] A) {
    int max = 0;
    m = A.length;
    n = A[0].length;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        boolean[][] vis = new boolean[A.length][A[0].length];
        max = Math.max(max, f(A, vis, i, j, A[i][j]));
      }
    }
    return max;
  }

  /*
  Note "You may not move diagonally or move outside the boundary (i.e., wrap-around is not
  allowed)."
     so it is not sum += f(A, vis, r, c, v);
   */
  private static int f(int[][] A, boolean[][] vis, int i, int j, int v) {
    vis[i][j] = true;
    int max = 1;
    for (int[] d : d4) {
      int r = i + d[0], c = j + d[1];
      if (r >= 0 && c >= 0 && r < m && c < n && !vis[r][c] && A[r][c] == v)
        max = Math.max(max, 1 + f(A, vis, r, c, v));
    }
    vis[i][j] = false;
    return max;
  }

  // ==========================================================================
  public static void main(String[] args) {
    int[][] matrix =
        (new int[][] {
          {7, 9, 6},
          {9, 9, 9},
          {2, 9, 1}
        });
    System.out.println(longestPathWithSameValue(matrix) == 3);

    matrix =
        new int[][] {
          {1, 1, 1, 2, 4},
          {5, 1, 5, 3, 1},
          {3, 4, 2, 1, 1}
        };
    System.out.println(longestPathWithSameValue(matrix) == 3);

    matrix =
        new int[][] {
          {9, 9, 9, 9, 9, 9, 9},
          {9, 9, 8, 9, 9, 9, 9},
          {9, 9, 9, 12, 9, 9, 9},
          {9, 9, 9, 12, 9, 9, 9},
          {9, 9, 9, 12, 9, 9, 9},
          {9, 9, 9, 12, 9, 9, 9}
        }; // 37
    System.out.println(longestPathWithSameValue(matrix) == 37);

    matrix =
        new int[][] {
          {1, 3, 1, 1},
          {0, 0, 5, 1},
          {0, 0, 5, 5}
        };
    System.out.println(longestPathWithSameValue(matrix) == 4);

    matrix =
        new int[][] {
          {1, 3, 1, 1},
          {5, 5, 5, 1},
          {5, 5, 5, 5}
        };
    System.out.println(longestPathWithSameValue(matrix) == 7);

    matrix =
        new int[][] {
          {1, 1, 1, 3, 2},
          {2, 1, 1, 4, 2},
          {3, 3, 1, 1, 1},
          {4, 4, 4, 3, 4}
        };
    System.out.println(longestPathWithSameValue(matrix) == 7);
  }
  /*
  Followup ====================================================================
   a matrix with no boundaries
   what is its meaning
  */

}
