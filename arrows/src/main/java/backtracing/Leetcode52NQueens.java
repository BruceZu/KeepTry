//  Copyright 2017 The keepTry Open Source Project
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

package backtracing;

import java.util.*;

public class Leetcode52NQueens {
  /*
   Leetcode   52. N-Queens II

    The n-queens puzzle is the problem of placing n queens on an n x n chessboard
    such that no two queens attack each other.

    Given an integer n, return the number of distinct solutions to the n-queens puzzle.

    Input: n = 4
    Output: 2
    Explanation: There are two distinct solutions to the 4-queens puzzle as shown.


    Input: n = 1
    Output: 1

    Constraints:

    1 <= n <= 9
  */
  /*
   O(N!) time. N is the number of queens (which is the same as the width and height of the board).
   O(N) space. 3 sets
  */
  private Set<Integer> col = new HashSet(); // occupied Column
  private Set<Integer> dg135 = new HashSet(); // occupied Diagonals 135
  private Set<Integer> dg45 = new HashSet(); // occupied Diagonals 45

  public int totalNQueens(int n) {
    int[] ans = new int[1];
    backtracking(0, n, ans);
    return ans[0];
  }

  private void backtracking(int r, int N, int[] ans) {
    if (r == N) {
      ans[0]++;
      return;
    }

    int diff, sum;
    for (int c = 0; c < N; c++) { // check each column
      if (col.contains(c)) continue;

      diff = r - c;
      if (dg135.contains(diff)) continue;

      sum = r + c;
      if (dg45.contains(sum)) continue;
      // -----
      col.add(c);
      dg135.add(diff);
      dg45.add(sum);

      backtracking(r + 1, N, ans);

      col.remove(c);
      dg135.remove(diff);
      dg45.remove(sum);
      // -----
    }
  }
}

class Solution {
  private int size;

  public int totalNQueens(int n) {
    size = n;
    return backtrack(0, new HashSet<>(), new HashSet<>(), new HashSet<>());
  }

  private int backtrack(int r, Set<Integer> dg135, Set<Integer> dg45, Set<Integer> cols) {
    if (r == size) return 1;

    int ans = 0;
    for (int c = 0; c < size; c++) {
      int diff = r - c;
      int sum = r + c;

      if (cols.contains(c) || dg135.contains(diff) || dg45.contains(sum)) {
        continue;
      }

      cols.add(c);
      dg135.add(diff);
      dg45.add(sum);

      ans += backtrack(r + 1, dg135, dg45, cols);

      cols.remove(c);
      dg135.remove(diff);
      dg45.remove(sum);
    }

    return ans;
  }
}
