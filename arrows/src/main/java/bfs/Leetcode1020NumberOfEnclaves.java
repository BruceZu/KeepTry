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

package bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Leetcode1020NumberOfEnclaves {
  // DFS O(n * m) time and space
  public int numEnclaves(int[][] A) {
    // update A to mark cell' 1 to be 0 for boundary cells
    for (int i = 0; i < A.length; i++)
      for (int j = 0; j < A[0].length; j++)
        if (i == 0 || j == 0 || i == A.length - 1 || j == A[i].length - 1) dfs(A, i, j);

    int r = 0;
    // counts the remaining land.
    for (int i = 0; i < A.length; i++)
      for (int j = 0; j < A[0].length; j++) {
        if (A[i][j] == 1) r++;
      }
    return r;
  }

  void dfs(int[][] A, int i, int j) {
    if (i < 0 || j < 0 || i == A.length || j == A[i].length || A[i][j] == 0) return;
    A[i][j] = 0;
    dfs(A, i + 1, j);
    dfs(A, i - 1, j);
    dfs(A, i, j + 1);
    dfs(A, i, j - 1);
  }
  // -----------------------------------------------------------------------------------

  // BFS O(n * m) time and space. with a queue

  public int numEnclaves2(int[][] A) {
    Queue<int[]> q = new LinkedList<int[]>();
    int sum = 0;
    // sum of '1' and put boundary '1' in queue
    for (int i = 0; i < A.length; ++i)
      for (int j = 0; j < A[0].length; ++j) {
        sum += A[i][j];
        if (i * j == 0 || i == A.length - 1 || j == A[i].length - 1) q.add(new int[] {i, j});
      }
    // calculate all boundary '1' and subtract 1 from sum
    while (!q.isEmpty()) {
      int x = q.peek()[0], y = q.peek()[1];
      q.remove();
      if (x < 0 || y < 0 || x == A.length || y == A[x].length || A[x][y] != 1) continue;
      A[x][y] = 0;
      sum--;
      q.add(new int[] {x + 1, y});
      q.add(new int[] {x - 1, y});
      q.add(new int[] {x, y + 1});
      q.add(new int[] {x, y - 1});
    }
    return sum;
  }
}
