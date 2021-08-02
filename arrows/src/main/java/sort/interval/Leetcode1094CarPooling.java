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

package sort.interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Leetcode1094CarPooling {

  // O(max{N,M}) time, O(M) space
  public boolean carPooling2(int[][] trips, int capacity) {
    /*
        trips.length <= 1000
        trips[i].length == 3
        1 <= trips[i][0] <= 100
        0 <= trips[i][1] < trips[i][2] <= 1000
        1 <= capacity <= 100000
    */

    int[] t = new int[1001]; // default 0
    // O(N) N is trips length
    for (int[] trip : trips) {
      t[trip[1]] += trip[0];
      t[trip[2]] -= trip[0];
    }

    int realNeed = 0;
    // O(M) M is the max value of trips[i][1] and trips[i][2]
    for (int i : t) {
      realNeed += i;
      if (realNeed > capacity) return false;
    }
    return true;
  }

  /*
  When N is small and M is very large
  O(NlogN) time and O(N) space

  Note: when there is + and - on the same location
        let - firstly then + operation
        else the order will make the result  wrong, as it comes from sum;
        the temp sum is decided by the order
        E.g.
        [[4,5,6],[6,4,7],[4,3,5],[2,3,5]], 13
        true
   */
  public static boolean carPooling1(int[][] trips, int capacity) {
    List<int[]> p = new ArrayList(trips.length * 2);
    for (int[] i : trips) {
      p.add(new int[] {i[1], i[0]});
      p.add(new int[] {i[2], -i[0]});
    }
    Collections.sort(
        p,
        (a, b) -> {
          if (a[0] == b[0]) return a[1] - b[1];
          return a[0] - b[0];
        });
    int r = 0, sum = 0;
    for (int[] i : p) {
      sum += i[1];
      if (sum > r) r = sum;
    }
    return r <= capacity;
  }

  public static boolean carPooling(int[][] trips, int capacity) {
    List<int[]> s = new ArrayList(trips.length);
    List<int[]> e = new ArrayList(trips.length);
    for (int[] i : trips) {
      s.add(new int[] {i[1], i[0]});
      e.add(new int[] {i[2], -i[0]});
    }
    Collections.sort(s, Comparator.comparingInt(a -> a[0]));
    Collections.sort(e, Comparator.comparingInt(a -> a[0]));

    int r = 0, sum = 0;
    int i = 0, j = 0;
    while (i < trips.length) {
      while (j < trips.length && e.get(j)[0] <= s.get(i)[0]) sum += e.get(j++)[1];
      sum += s.get(i++)[1];
      if (sum > r) r = sum;
    }
    return r <= capacity;
  }
}
