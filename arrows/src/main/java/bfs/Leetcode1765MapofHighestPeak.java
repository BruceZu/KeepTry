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

public class Leetcode1765MapofHighestPeak {
  /*
    Leetcode 1765. Map of Highest Peak
    You are given an integer matrix isWater of size m x n that represents a map of land and water cells.

    If isWater[i][j] == 0, cell (i, j) is a land cell.
    If isWater[i][j] == 1, cell (i, j) is a water cell.
    You must assign each cell a height in a way that follows these rules:

    The height of each cell must be non-negative.
    If the cell is a water cell, its height must be 0.
    Any two adjacent cells must have an absolute height difference of at most 1.
    A cell is adjacent to another cell if the former is directly
    north, east, south, or west of the latter (i.e., their sides are touching).
    Find an assignment of heights such that the maximum height in the matrix is maximized.

    Return an integer matrix height of size m x n where height[i][j] is cell (i, j)'s height.
    If there are multiple solutions, return any of them.

    Input: isWater = [[0,1],
                      [0,0]]
    Output: [[1,0],
             [2,1]]
    Explanation: The image shows the assigned heights of each cell.
    The blue cell is the water cell, and the green cells are the land cells.


    Input: isWater = [[0,0,1],
                      [1,0,0],
                      [0,0,0]]
    Output: [[1,1,0],
             [0,1,1],
             [1,2,2]]
    Explanation: A height of 2 is the maximum possible height of any assignment.
    Any height assignment that has a maximum height of 2 while still meeting the rules will also be accepted.


    Constraints:

    m == isWater.length
    n == isWater[i].length
    1 <= m, n <= 1000
    isWater[i][j] is 0 or 1.
    There is at least one water cell.
  */

  /*
  Understanding
    - `Any two adjacent cells must have an absolute height difference of 'at most' 1. `
       means the diff <=1. can be 0 or 1.

    - `Find an assignment of heights such that the maximum height in the matrix is maximized.`?
   */
  /*
  BFS
  operation in place
  O(M*N) time, space used by queue
   */
  /*
  Follow up:
     the 4 sides out of matix are water
   */
  public int[][] highestPeak(int[][] isWater) {
    if (isWater == null || isWater[0] == null) return null;
    int M = isWater.length, N = isWater[0].length;
    Queue<int[]> q = new LinkedList<>();
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        if (isWater[i][j] == 1) {
          isWater[i][j] = 0;
          q.offer(new int[] {i, j});
        } else isWater[i][j] = -1;
      }
    }
    int[][] d4 = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    int h = 0;
    while (q.size() > 0) {
      h++;
      int s = q.size();
      while (s-- > 0) {
        int[] cell = q.poll();
        int i = cell[0], j = cell[1];
        for (int[] d : d4) {
          int r = i + d[0], c = j + d[1];
          if (r >= 0 && r < M && c >= 0 && c < N && isWater[r][c] == -1) {
            isWater[r][c] = h;
            q.offer(new int[] {r, c});
          }
        }
      }
    }
    return isWater;
  }
}
