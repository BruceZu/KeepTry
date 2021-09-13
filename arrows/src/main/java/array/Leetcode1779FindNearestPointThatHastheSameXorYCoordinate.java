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

package array;

public class Leetcode1779FindNearestPointThatHastheSameXorYCoordinate {
  /*
     1779. Find Nearest Point That Has the Same X or Y Coordinate


    You are given two integers, x and y, which represent your current location on a
    Cartesian grid: (x, y). You are also given an array points where each points[i] = [ai, bi]
    represents that a point exists at (ai, bi). A point is valid if it shares the
    same x-coordinate or the same y-coordinate as your location.

    Return the index (0-indexed) of the valid point with the smallest
    Manhattan distance from your current location.
    If there are multiple, return the valid point with the smallest index.
    If there are no valid points, return -1.

    The Manhattan distance between two points
    (x1, y1) and (x2, y2) is abs(x1 - x2) + abs(y1 - y2).


    Input: x = 3, y = 4, points = [[1,2],[3,1],[2,4],[2,3],[4,4]]
    Output: 2
    Explanation: Of all the points, only [3,1], [2,4] and [4,4]
    are valid. Of the valid points, [2,4] and [4,4] have the smallest
    Manhattan distance from your current location, with a distance of 1.
    [2,4] has the smallest index, so return 2.


    Input: x = 3, y = 4, points = [[3,4]]
    Output: 0
    Explanation: The answer is allowed to be on the same location as
    your current location.


    Input: x = 3, y = 4, points = [[2,3]]
    Output: -1
    Explanation: There are no valid points.

    Constraints:

        1 <= points.length <= 104
        points[i].length == 2
        1 <= x, y, ai, bi <= 104
  */
  public int nearestValidPoint(int x, int y, int[][] points) {
    int a = -1, min = Integer.MAX_VALUE;
    for (int i = 0; i < points.length; ++i) {
      int dx = x - points[i][0], dy = y - points[i][1];
      if (dx * dy == 0 && Math.abs(dy + dx) < min) {
        min = Math.abs(dx + dy);
        a = i;
      }
    }
    return a;
  }
}
