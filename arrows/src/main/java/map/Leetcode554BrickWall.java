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

package map;

import java.util.HashMap;
import java.util.List;

public class Leetcode554BrickWall {
  /*
  Leetcode 554. Brick Wall

  There is a rectangular brick wall in front of you with n rows of bricks.
  The ith row has some number of bricks each of the same height (i.e., one unit)
  but they can be of different widths. The total width of each row is the same.

  Draw a vertical line from the top to the bottom and cross the least bricks.
  If your line goes through the edge of a brick, then the brick is not considered as crossed.
  You cannot draw a line just along one of the two vertical edges of the wall, in
  which case the line will obviously cross no bricks.

  Given the 2D array wall that contains the information about the wall, return
  the minimum number of crossed bricks after drawing such a vertical line.


  Input: wall = [[1,2,2,1],[3,1,2],[1,3,2],[2,4],[3,1,2],[1,3,1,1]]
  Output: 2

  Input: wall = [[1],[1],[1]]
  Output: 3


  Constraints:

  n == wall.length
  1 <= n <= 10^4
  1 <= wall[i].length <= 10^4
  1 <= sum(wall[i].length) <= 2 * 10^4
  sum(wall[i]) is the same for each row i.
  1 <= wall[i][j] <= 2^31 - 1
   */

  /*
  HashMap( index, number of row which just has a brick end here)

  Time complexity : O(n) n is the total number of bricks in a wall.
  Space complexity : O(m).  m refers to the width of the wall.
   */
  public int leastBricks(List<List<Integer>> wall) {
    HashMap<Integer, Integer> map = new HashMap<>();
    for (List<Integer> row : wall) { // for each row
      int sum = 0; // presum
      for (int i = 0; i < row.size() - 1; i++) { // do not check the end index
        sum += row.get(i);
        map.put(sum, map.getOrDefault(sum, 0) + 1);
      }
    }

    int max = 0;
    for (int k : map.keySet()) {
      max = Math.max(max, map.get(k));
    }
    int rows = wall.size();
    return rows - max;
  }
}
