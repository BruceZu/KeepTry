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

package prefix_suffix_sum;

import java.util.Arrays;

public class Leetcode2017GridGame {
  /*
    2017. Grid Game

    You are given a 0-indexed 2D array grid of size 2 x n, where grid[r][c]
    represents the number of points at position (r, c) on the matrix.

    Two robots are playing a game on this matrix.
    Both robots initially start at (0, 0) and want to reach (1, n-1).
    Each robot may only move to the right ((r, c) to (r, c + 1)) or down ((r, c) to (r + 1, c)).
    At the start of the game, the first robot moves from (0, 0) to (1, n-1),
    collecting all the points from the cells on its path.

    For all cells (r, c) traversed on the path, grid[r][c] is set to 0.
    Then, the second robot moves from (0, 0) to (1, n-1),
    collecting the points on its path. Note that their paths may intersect with one another.

    The first robot wants to minimize the number of points collected by the second robot.
    In contrast, the second robot wants to maximize the number of points it collects.
    If both robots play optimally, return the number of points collected by the second robot.

    Input: grid = [[2,5,4],[1,5,1]]
    Output: 4
    Explanation: The optimal path taken by the first robot is shown in red, and the optimal path taken by the second robot is shown in blue.
    The cells visited by the first robot are set to 0.
    The second robot will collect 0 + 0 + 4 + 0 = 4 points.
    Example 2:


    Input: grid = [[3,3,1],[8,5,2]]
    Output: 4
    Explanation: The optimal path taken by the first robot is shown in red, and the optimal path taken by the second robot is shown in blue.
    The cells visited by the first robot are set to 0.
    The second robot will collect 0 + 3 + 1 + 0 = 4 points.
    Example 3:


    Input: grid = [[1,3,1,15],[1,3,3,1]]
    Output: 7
    Explanation: The optimal path taken by the first robot is shown in red, and the optimal path taken by the second robot is shown in blue.
    The cells visited by the first robot are set to 0.
    The second robot will collect 0 + 1 + 3 + 3 + 0 = 7 points.


    Constraints:

    grid.length == 2
    n == grid[r].length
    1 <= n <= 5 * 104
    1 <= grid[r][c] <= 105
  */

  /*
  Note the constraints
  Watch the process
       robots cannot go up, we need to find the best point i for the first robot to go down.
       robot 1 and robot 2 can only move down once. So that is why there is in total
       n possible paths of Robot 1

       For the second robot, we only have two choices
  O(N) time O(1) space
  Note the constraints
   - the prefix/suffix sum can overflow int,  use long type

  Code: top is suffix sum, down is prefix sum
   */
  public static long gridGame(int[][] grid) {
    long top = Arrays.stream(grid[0]).asLongStream().sum();
    long down = 0;
    long r = Long.MAX_VALUE;
    for (int i = 0; i < grid[0].length; i++) {
      top -= grid[0][i];
      r = Math.min(r, Math.max(top, down));
      down += grid[1][i];
    }
    return r;
  }
  /*
  Why won't this DP approach work?

  Firstly, I find the path which has the maximum points for Robot 1 then I make all its path cells as zeroes.
  Then finding the path which has the maximum points for Robot 2?

  It shows  finding the path which has the maximum points for Robot 1, it's not guarantee that
  the minimum points of Robot 2.
  */

  public static void main(String[] args) {

    System.out.println(
        gridGame(
                new int[][] {
                  {20, 3, 20, 17, 2, 12, 15, 17, 4, 15},
                  {20, 10, 13, 14, 15, 5, 2, 3, 14, 3}
                })
            == 63);
    // answer is sum of top suffix sum: 12, 15, 17, 4, 15
    // DP it is down prefix sum: 20, 10, 13, 14, 15, 5, 2, 3, 14,
    System.out.println(
        gridGame(
                new int[][] {
                  {1, 5, 5, 3},
                  {5, 5, 3, 3}
                })
            == 8);

    // answer is up suffix sum: 5,3
    // DP has 4 path for robot 1
    //    1 _ 6 - 11 - 14
    //    |   |    |   |
    //    6- 11 - 14 - 17
    //  which one is better?

    System.out.println(
        gridGame(
                new int[][] {
                  {1, 5, 5, 3},
                  {5, 5, 1, 1}
                })
            == 8);

    // answer is down prefix sum: 1,5
    // DP the max path for robot 1:
    //    1 - 6 - 11 - 14
    //                |
    //    6  11   12  15
    //   then set the pat as 0, then the answer will be sum of: 5,5,1
  }
}
