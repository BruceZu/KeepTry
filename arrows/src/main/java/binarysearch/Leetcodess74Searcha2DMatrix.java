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

public class Leetcodess74Searcha2DMatrix {
  /*
    Leetcode   74. Search a 2D Matrix

    Write an efficient algorithm that searches for a value in an m x n matrix.
    This matrix has the following properties:

    Integers in each row are sorted from left to right.
    The first integer of each row is greater than the last integer of the previous row.

    Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
    Output: true
    Example 2:


    Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
    Output: false


    Constraints:

    m == matrix.length
    n == matrix[i].length
    1 <= m, n <= 100
    -104 <= matrix[i][j], target <= 104
  */

  /*
  binary search
  convert from 1-D array index to 2-D array index:
  array[x] and matrix[i][j]

  i=x/N;
  j=x%N;

  N=matrix[0].length

  x= i*N+j
  O(log(M*N)) time
  O(1) space
  */
  public boolean searchMatrix(int[][] matrix, int target) {
    if (matrix == null || matrix[0] == null) return false;
    int M = matrix.length, N = matrix[0].length;
    if (matrix[0][0] > target || matrix[M - 1][N - 1] < target) return false;

    int l = 0, r = M * N - 1;
    while (l <= r) {
      int m = l + r >>> 1;
      int mv = matrix[m / N][m % N];
      if (mv == target) return true;
      if (mv < target) l = m + 1;
      else r = m - 1;
    }
    return false;
  }
}
