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

package math;

public class Leetcode1252CellswithOddValuesinaMatrix {
  /*
   Leetcode 1252. Cells with Odd Values in a Matrix

    There is an m x n matrix that is initialized to all 0's.
    There is also a 2D array indices where each indices[i] = [ri, ci]
    represents a 0-indexed location to perform some increment operations on the matrix.

    For each location indices[i], do both of the following:

    Increment all the cells on row ri.
    Increment all the cells on column ci.
    Given m, n, and indices, return the number of odd-valued cells in the matrix after applying the increment to all locations in indices.

    Input: m = 2, n = 3, indices = [[0,1],[1,1]]
    Output: 6
    Explanation: Initial matrix = [[0,0,0],[0,0,0]].
    After applying first increment it becomes [[1,2,1],[0,1,0]].
    The final matrix is [[1,3,1],[1,3,1]], which contains 6 odd numbers.
    Example 2:


    Input: m = 2, n = 2, indices = [[1,1],[0,0]]
    Output: 0
    Explanation: Final matrix = [[2,2],[2,2]]. There are no odd numbers in the final matrix.


    Constraints:

    1 <= m, n <= 50
    1 <= indices.length <= 100
    0 <= ri < m
    0 <= ci < n


    Follow up: Could you solve this in O(n + m + indices.length) time with only O(n + m) extra space?
  */

  public int oddCells(int m, int n, int[][] indices) {
    byte[] rows = new byte[m], cols = new byte[n];
    for (int[] e : indices) {
      rows[e[0]] ^= 1;
      cols[e[1]] ^= 1;
    }
    int oddSum = 0;
    for (int i : rows) oddSum += i;
    int evenSum = m - oddSum;
    int r = 0;
    for (int i : cols) {
      if (i == 0) r += oddSum;
      else r += evenSum;
    }
    return r;
  }
}
