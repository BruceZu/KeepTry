//  Copyright 2017 The KeepTry Open Source Project
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

package greedy;

import java.util.Arrays;
import java.util.Comparator;

public class MaxOverloapAreas {

  // O(NlogN)
  // One Of the Max overlaps Areas. Maybe there are more.
  public static int[] maxOverlapScope(int[][] intervals) {

    int[][] starts = Arrays.copyOf(intervals, intervals.length);
    int[][] ends = Arrays.copyOf(intervals, intervals.length);

    Arrays.sort(starts, Comparator.comparing((p) -> p[0])); // NlogN
    Arrays.sort(ends, Comparator.comparing((p) -> p[1]));

    int endi = 0;

    long cur = 1;
    int[] curArea = new int[2];
    curArea[0] = starts[0][0];
    curArea[1] = ends[0][1];

    long max = 1;
    int[] maxArea = new int[2];
    maxArea[0] = starts[0][0];
    maxArea[1] = ends[0][1];

    for (int starti = 1; starti < intervals.length; starti++) {

      while (ends[endi][1] < starts[starti][0]) {
        cur -= 1;
        endi++;
      }
      curArea[0] = starts[starti][0];
      curArea[1] = ends[endi][1];
      cur += 1;

      if (max < cur) {
        max = cur;
        maxArea[0] = curArea[0];
        maxArea[1] = curArea[1];
      }
    }
    return maxArea;
  }

  public static void main(String[] args) {
    int[] r = maxOverlapScope(new int[][] {{1, 5}, {2, 6}, {3, 4}});
    System.out.println(r.toString());
  }
}
