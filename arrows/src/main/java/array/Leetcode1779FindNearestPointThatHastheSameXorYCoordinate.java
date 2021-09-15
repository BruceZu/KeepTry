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

import java.util.*;

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
  // (N) time
  public int nearestValidPoint(int x, int y, int[][] points) {
    int a = -1, min = Integer.MAX_VALUE;
    for (int i = 0; i < points.length; ++i) {
      int dx = Math.abs(x - points[i][0]), dy = Math.abs(y - points[i][1]);
      if (dx * dy == 0 && (dy + dx) < min) {
        min = Math.abs(dx + dy);
        a = i;
      }
    }
    return a;
  }
  /*
  Nearest Neighbour City

   A number of cities are arranged on a graph that has been divided up like an ordinary Cartesian plane.
   Each city is located at an integral (x, y) coordinate intersection.
   City names and locations are given in the form of three arrays: c, x, and y, which are aligned
   by the index to provide the city name (c[i]), and its coordinates, (x[i], y[i]).

   Determine the name of the nearest city that
    - shares either an x or a y coordinate with the queried city.
    - If no other cities share an x or y coordinate, return 'NONE'.
    - If two cities have the same distance to the queried city, q[i], consider the one with first
      lexicographically order name (i.e. 'ab' < 'aba' < 'abb') as the closest choice.

   The distance is the Manhattan distance, the absolute difference in x plus the absolute difference in y.
   */
  /*

   O(NlogK) time
   O(QlogK) time
   N is the number of cities,
   K is the max number of cities with same x or y coordinate and
   Q is the number of queries.

   Map.get(k) maybe null
   Map.get(l).higherEntry(v) maybe null
   Map.get(l).lowerEntry(v)maybe null
   on the same x or y line: dx*dy==0
  */
  public List<String> closestStraightCity(String[] citys, int[] xs, int[] ys, String[] queryCitys) {
    Map<String, int[]> cs = new HashMap<>(); // city name: (x,y)
    Map<Integer, TreeMap<Integer, String>> X = new HashMap<>();
    // x line:  y1: city2 name( < city1name)  y3: city3 name
    Map<Integer, TreeMap<Integer, String>> Y = new HashMap<>();
    // y line:  x1: city1 name, x2: city2 name

    int N = citys.length;
    for (int i = 0; i < N; i++) {
      int x = xs[i], y = ys[i];
      String name = citys[i];
      X.computeIfAbsent(x, k -> new TreeMap<>());
      if (!X.get(x).containsKey(y) || name.compareTo(X.get(x).get(y)) < 0) {
        X.get(x).put(y, name); // lexicographically order name
      }

      Y.computeIfAbsent(y, k -> new TreeMap<>());
      if (!Y.get(y).containsKey(x) || name.compareTo(Y.get(y).get(x)) < 0) {
        Y.get(y).put(x, name); // lexicographically order name
      }
      cs.put(name, new int[] {x, y});
    }

    List<String> r = new ArrayList<>();
    for (String c : queryCitys) {
      int x = cs.get(c)[0], y = cs.get(c)[1];
      List<Choice> four = new ArrayList<>();
      collect(X, x, y, four);
      collect(Y, y, x, four);
      Collections.sort(
          four,
          (a, b) -> {
            if (a.distance == b.distance) return a.name.compareTo(b.name);
            return a.distance - b.distance;
          });
      r.add(four.isEmpty() ? "NONE" : four.get(0).name);
    }
    return r;
  }

  private void collect(Map<Integer, TreeMap<Integer, String>> L, int l, int v, List<Choice> four) {
    if (L.get(l) != null) {
      Map.Entry<Integer, String> hi = L.get(l).higherEntry(v);
      Map.Entry<Integer, String> low = L.get(l).lowerEntry(v);
      if (hi != null) four.add(new Choice(hi.getValue(), hi.getKey() - v));
      if (low != null) four.add(new Choice(low.getValue(), v - low.getKey()));
    }
  }

  class Choice {
    String name;
    int distance;

    public Choice(String name, int distance) {
      this.name = name;
      this.distance = distance;
    }
  }

  public static void main(String[] args) {
    Leetcode1779FindNearestPointThatHastheSameXorYCoordinate t =
        new Leetcode1779FindNearestPointThatHastheSameXorYCoordinate();

    t.closestStraightCity(
        new String[] {"a", "c", "bc", "ab", "d"},
        new int[] {1, 2, 2, 2, 2},
        new int[] {1, 0, 4, 4, 5},
        new String[] {"a", "c", "bc", "d", "ab"});
    //         "NONE", "ab","d","ab","d"
  }
}
