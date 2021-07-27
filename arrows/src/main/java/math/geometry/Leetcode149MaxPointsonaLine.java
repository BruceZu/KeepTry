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

package math.geometry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Leetcode149MaxPointsonaLine {
  /*
   Given an array of points where points[i] = [xi, yi]
   represents a point on the X-Y plane, return the
   maximum number of points that lie on the same straight line.

   1 <= points.length <= 300
   points[i].length == 2
   -104 <= xi, yi <= 104
   All the points are unique.
  */
  /*
  Idea:
  - null point
  - 1 point
  - 2 or more point
    given (x1,y1), (x2,y2)
    we can represent the line with 2 point or one point:
      -1_1-   As `All the points are unique.`
              when x1!=x2 and y1!=y2
              calculate 2 points that the line crossing with the x-axis and y-axis:
              (x,0), x=(y1x2-y2x1)/(y1-y2)
              (0,y), y=(x1y2-x2y1)/(x1-x2)
              watch the two lines: change y->x,x->y, get the second line from the first line
              use x + "&" + y as key to represent the line. NOTE: y can be negative

      -1_2-  for lines passing (0,0), need extra a angle to distinguish them
              NOTE:
              - result of division need be kept in double type
              - compare 2 double value need use Double.doubleToLongBits(angle);
              - angle can be express by
                 int gcd = BigInteger.valueOf(x1 - x2).gcd(BigInteger.valueOf(y1 - y2)).intValue();
                 String angle=(x1 - x2) / gcd + "&" + (y1 - y2) / gcd;
                 k = "zero" + angle;
      -2_1-  when x1==x2: use "Y-"+x as key to represent vertical lines
      -2_2-  when y1==y2: use "X-"+y as key to represent horizon lines

      O(N^2) time
      O(N) space
  */

  public static int maxPoints(int[][] points) {
    if (points == null || points.length <= 0) return 0;
    if (points.length == 1) return 1;
    Map<String, Set<int[]>> m = new HashMap();
    int r = 0;
    for (int i = 1; i < points.length; i++) {
      int x1 = points[i][0], y1 = points[i][1];
      for (int j = 0; j < i; j++) {
        int x2 = points[j][0], y2 = points[j][1];
        String k = "";
        if (x1 != x2 && y1 != y2) {
          double x = (y1 * x2 - y2 * x1) * 1d / (y1 - y2);
          double y = (x1 * y2 - x2 * y1) * 1d / (x1 - x2);
          k = x + "&" + y;
          if (x == 0 && y == 0) {
            double angle = (x1 - x2) * 1d / (y1 - y2);
            k = "zero" + Double.doubleToLongBits(angle); // angle
          }
        } else if (y1 == y2) {
          k = "X" + y1;
        } else {
          k = "Y" + x1;
        }
        m.putIfAbsent(k, new HashSet());
        m.get(k).add(points[i]);
        m.get(k).add(points[j]);
        if (m.get(k).size() > r) r = m.get(k).size();
      }
    }
    return r;
  }
}
