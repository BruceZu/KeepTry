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

import java.util.LinkedList;
import java.util.Queue;

public class Leetcode1030MatrixCellsinDistanceOrder {
  /*
  Return the coordinates of all cells in the matrix, sorted by their distance from (r0, c0)
  from smallest distance to largest distance.
  Here, the distance between two cells (r1, c1) and (r2, c2) is the Manhattan distance,
  |r1 - r2| + |c1 - c2|.  (You may return the answer in any order that satisfies this condition.)
  */
  public int[][] allCellsDistOrder(int R, int C, int r0, int c0) {
    boolean[][] visited = new boolean[R][C];
    int[][] result = new int[R * C][2];
    int i = 0;
    Queue<int[]> queue = new LinkedList();
    queue.offer(new int[] {r0, c0});
    while (!queue.isEmpty()) {
      int[] cell = queue.poll();
      int r = cell[0];
      int c = cell[1];

      if (r < 0 || r >= R || c < 0 || c >= C) { // invalid
        continue;
      }
      if (visited[r][c]) {// visited
        continue;
      }

      result[i] = cell;
      i++;
      visited[r][c] = true;

      queue.offer(new int[] {r, c - 1});
      queue.offer(new int[] {r, c + 1});
      queue.offer(new int[] {r - 1, c});
      queue.offer(new int[] {r + 1, c});
    }
    return result;
  }
}
