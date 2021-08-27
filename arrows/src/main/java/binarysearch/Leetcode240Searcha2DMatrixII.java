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

package binarysearch;

public class Leetcode240Searcha2DMatrixII {
  /*
    240. Search a 2D Matrix II
     Write an efficient algorithm that searches
     for a target value in an m x n integer matrix. The matrix has the following properties:

         Integers in each row    are sorted in ascending from left to right.
         Integers in each column are sorted in ascending from top to bottom.


     Input: matrix = [[1, 4, 7, 11,15],
                      [2, 5, 8, 12,19],
                      [3, 6, 9, 16,22],
                      [10,13,14,17,24],
                      [18,21,23,26,30]], target = 5 Output: true

     Input: matrix = [[1, 4, 7, 11,15],
                      [2, 5, 8, 12,19],
                      [3, 6, 9, 16,22],
                      [10,13,14,17,24],
                      [18,21,23,26,30]], target = 20 Output: false

    Input: matrix =
          [[1, 2, 3, 4, 5 ],
           [6, 7, 8, 9, 10],
           [11,12,13,14,15],
           [16,17,18,19,20],
           [21,22,23,24,25]] target= 19 Output: true
   Input: matrix =
          [[1,   2,   118],
           [99,  100, 119],
           [107, 200, 250],
           [119, 300, 350], target= 118  Output: true

     Constraints:
         m == matrix.length
         n == matrix[i].length
         1 <= n, m <= 300
         -109 <= matix[i][j] <= 109
         All the integers in each row are sorted in ascending order.
         All the integers in each column are sorted in ascending order.
         -109 <= target <= 109
  */
  /*
    Understanding
     - next 2 directions: right and down.
     - Brute force:  (M*N) time, O(1) space
     - use binary search row by row or column by column O(mlogn) or O(nlogm) time, O(1) space
  */
  /* --------------------------------------------------------------------------
  Idea:
    along the diagonal left-top----> right-down
    do binary search on vertical and horizontal 2 direction

    O(log(n!)+log((m!)/((m-n)!)) time. m>n, m and n are row number or column number.
    O(1) space
  */
  int M, N;

  public boolean searchMatrix_(int[][] matrix, int t) {
    if (matrix == null || matrix.length == 0) return false;
    M = matrix.length;
    N = matrix[0].length;
    // iterate over matrix diagonals
    int d = Math.min(M, N);
    for (int i = 0; i < d; i++) {
      boolean v = binarySearch(matrix, t, i, true);
      boolean h = binarySearch(matrix, t, i, false);
      if (v || h) return true;
    }
    return false;
  }

  private boolean binarySearch(int[][] matrix, int t, int i, boolean isVertical) {
    int l = i, r = isVertical ? N - 1 : M - 1; // l and r are possible index of t
    while (l <= r) { // t may not in the [l, r] scope, then at last r<l
      int m = l + r >>> 1;
      int mv;
      if (isVertical) mv = matrix[i][m]; // searching a column
      else mv = matrix[m][i]; // searching a row

      if (mv < t) l = m + 1;
      else if (mv > t) r = m - 1;
      else return true;
    }
    return false;
  }
  /* --------------------------------------------------------------------------
   Idea  divide and conquer
   Observe:
   partition  matrix into 4 sorted sub-matrices, two of which
   might contain `target` and two of which definitely do not.

  - Determine  whether a `target `  appear in it.
    - array has zero cells
    -   `target` < the array's smallest element ( the top-left corner) or
              it > the array's largest element (  the bottom-right corner)
  - else `target` could potentially be present.
  Idea:
  from the matrix's middle column and find ceiling(target) row
  each divide half cells are left.

   assume m~n:
   O((logn)^2) time
   O(logn)  space for call stack,
   https://en.wikipedia.org/wiki/Master_theorem_(analysis_of_algorithms)
   */
  private int[][] mtx;
  private int t;

  public boolean searchMatrix__(int[][] matrix, int t) {
    this.mtx = matrix;
    this.t = t;

    if (matrix == null || matrix.length == 0) return false;
    M = matrix.length;
    N = matrix[0].length;
    return half(0, 0, M - 1, N - 1);
  }

  /*
   matrix[r][c] is left top cell,  matrix[r2][c2] is right bottom cell
   binary search to find the ceiling cell of t
  */
  private boolean half(int r, int c, int r2, int c2) {
    if (c > c2 || r > r2) return false; // no height or no width.
    else if (t < mtx[r][c] || t > mtx[r2][c2]) return false;

    int m = c + (c2 - c) / 2;
    int cr = rowCeilingOnColumn(mtx, m, r, r2, t);
    if (cr < M && mtx[cr][m] == t) return true;
    return half(cr, c, r2, m - 1) || half(r, m + 1, cr - 1, c2);
  }
  /*
   [i, j] is valid row scope, from within this scope search the ceiling of t
   c: column
   mtx: matrix
  */
  private int rowCeilingOnColumn(int[][] mtx, int c, int i, int j, int t) {
    while (i < j) {
      int m = i + j >>> 1;
      if (mtx[m][c] >= t) j = m;
      else i++;
    }
    if (mtx[i][c] < t) i++; // [-1，3]target is 3
    return i; // expected >=t
  }
  /* --------------------------------------------------------------------------
  Observe from the matrix properties:
     if cell[i][j] < target
       then cells left and top of it are all invalid
     if  cell[i][j]> target
       then cells right and down of it are all invalid

     if start from left - bottom cell which has not down and left
        then only one way to go no matter
         it < target  only can go right
         or it > target. only can go up
        from then on next cell has only one way to go as the previous cell has been visited

    It also works starting from right top cell
    O(M+N) time
    O(1) space
  */
  public boolean searchMatrix(int[][] A, int t) {
    int M = A.length, N = A[0].length;
    int r = M - 1, c = 0;
    while (r >= 0 && c < N) {
      if (A[r][c] == t) return true;
      if (A[r][c] < t) c++;
      else r--;
    }
    return false;
  }

  /*
  update searchMatrix() with binary search
    c++; -> binary search the ceiling cell on current row
            it works to use Arrays.binarySearch() but it requires
            no duplicated cell value and transfer negative result
                   c = Arrays.binarySearch(A[r], c + 1, N, t);
                   if (c < 0) c = ~c; // c maybe out of valid boundary
    r--; -> binary search floor cell on the column

   Does it fast? in the worst case  O(M+N)(logM+logN) time
   */
  public boolean searchMatrix2(int[][] A, int t) {
    int M = A.length, N = A[0].length;
    int r = M - 1, c = 0;
    while (r >= 0 && c < N) {
      if (A[r][c] == t) return true;
      if (A[r][c] < t) {
        c = columnCeilingOnRow(A, r, c + 1, N - 1, t);
      } else r = rowFloorOnColumn(A, c, 0, r - 1, t);
    }
    return false;
  }

  /*
   Search floor of t. E.g. search floor of 3 or 4 in [1,2,3,3,5]

   Here is working on a given column `c` of sorted matrix `A`
   [i, j] is row index scope
   i: never moved, or its value <=t . `i` is always valid in matrix
   j: never moved, or not checked and j+1 value >t
   note: m=(i + j >>> 1) + 1;  avoid endless loop
  */
  private int rowFloorOnColumn(int[][] A, int c, int i, int j, int t) {
    while (i < j) {
      int m = (i + j >>> 1) + 1;
      if (A[m][c] <= t) i = m;
      else j = j - 1;
    }
    // i==j now, double check
    if (A[i][c] > t) return i - 1;
    return i; // expected <=t
  }

  /*
  Search ceiling of t. E.g. search ceiling of 3 or 4 in [1,2,3,3,5]
  Here is working on a given row `r` of sorted matrix `A`
  [i, j] is column index scope
  i: never moved, or not checked and i-1 value <t, `i` may be N
  j: never moved, or its value>=t
  */
  private int columnCeilingOnRow(int[][] A, int r, int i, int j, int t) {
    if (i == A[0].length) return i;
    while (i < j) {
      int m = i + j >>> 1;
      if (A[r][m] >= t) j = m;
      else i = m + 1;
    }
    // i==j now, double check
    if (A[r][i] < t) return i + 1;
    return i; // expected >=t
  }
}
