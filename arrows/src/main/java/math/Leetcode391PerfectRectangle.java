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

package math;

import java.util.*;

/**
 * <pre>
 * <a href="https://leetcode.com/problems/perfect-rectangle/?tab=Description">LeetCode</a>
 */
public class Leetcode391PerfectRectangle {
    static public boolean isRectangleCover(int[][] rectangles) {
        int x1 = Integer.MAX_VALUE, x2 = Integer.MIN_VALUE, y1 = Integer.MAX_VALUE, y2 = Integer.MIN_VALUE;
        int areaSum = 0;
        Set<String> vetexes = new HashSet();
        for (int[] rectangle : rectangles) {

            int recX1 = rectangle[0], recY1 = rectangle[1], recX2 = rectangle[2], recY2 = rectangle[3];
            x1 = Math.min(x1, recX1);
            x2 = Math.max(x2, recX2);

            y1 = Math.min(y1, recY1);
            y2 = Math.max(y2, recY2);
            areaSum += (recX2 - recX1) * (recY2 - recY1);

            if (!vetexes.add(recX1 + "" + recY1)) vetexes.remove(recX1 + "" + recY1);
            if (!vetexes.add(recX2 + "" + recY2)) vetexes.remove(recX2 + "" + recY2);
            if (!vetexes.add(recX1 + "" + recY2)) vetexes.remove(recX1 + "" + recY2);
            if (!vetexes.add(recX2 + "" + recY1)) vetexes.remove(recX2 + "" + recY1);

        }

        if (vetexes.size() != 4
                || !vetexes.contains(x1 + "" + y1)
                || !vetexes.contains(x2 + "" + y2)
                || !vetexes.contains(x1 + "" + y2)
                || !vetexes.contains(x2 + "" + y1)
                || areaSum != (x2 - x1) * (y2 - y1)) return false;
        return true;
    }

//        return vetexes.contains(x1 + "" + y1) // 1 vertex just exists
//                && vetexes.contains(x2 + "" + y2)
//                && vetexes.contains(x1 + "" + y2)
//                && vetexes.contains(x2 + "" + y1)
//                && vetexes.size() == 4 // 2 just 4 left
//                && areaSum == (x2 - x1) * (y2 - y1);// 3 area
//    }

    public static void main(String[] args) {
        System.out.println(isRectangleCover(new int[][]{{1, 1, 3, 3}, {3, 1, 4, 2}, {3, 2, 4, 4}, {1, 3, 2, 4}, {2, 3, 3, 4}}));
        System.out.println(isRectangleCover(new int[][]{{0, 0, 1, 1}, {0, 1, 3, 2}, {1, 0, 2, 2}}));
        System.out.println(isRectangleCover(new int[][]{{0, 0, 2, 2}, {1, 1, 3, 3}, {2, 0, 3, 1}, {0, 3, 3, 4}}));
        System.out.println(isRectangleCover(new int[][]{{0, 0, 3, 3}, {1, 1, 2, 2}, {1, 1, 2, 2}}));
        System.out.println(isRectangleCover(new int[][]{{0, 0, 1, 1}, {0, 0, 2, 1}, {1, 0, 2, 1}, {0, 2, 2, 3}}));
    }
}
