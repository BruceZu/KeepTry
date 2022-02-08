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

package two_pointer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Leetcode986IntervalListIntersections {
  /*
   Leetcode 986. Interval List Intersections

   You are given two lists of closed intervals, firstList and secondList,
   where
   firstList[i] = [starti, endi] and
   secondList[j] = [startj, endj].
   Each list of intervals is pairwise disjoint and in sorted order.

   Return the intersection of these two interval lists.

   A closed interval [a, b] (with a <= b) denotes the set of real numbers x with a <= x <= b.

   The intersection of two closed intervals is a set of real numbers that
   are either empty or represented as a closed interval.
   For example, the intersection of [1, 3] and [2, 4] is [2, 3].


   Input: firstList = [[0, 2],[5,  10],  [13,  23],[24, 25]],
         secondList =   [[1,  5],[8, 12],   [15,   24],[25, 26]]
   Output: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]


   Input: firstList = [[1, 3], [5, 9]],
          secondList = []
   Output: []


   Constraints:

   0 <= firstList.length, secondList.length <= 1000
   firstList.length + secondList.length >= 1
   0 <= starti < endi <= 10^9
   endi < starti+1
   0 <= startj < endj <= 10^9
   endj < startj+1
  */
  /*
   check if there is overlap of 2 interval by
      - maxStarts <= minEnds? have : no
      -  (aEnd<bStart||bEnd<aStart)? no : have
   https://imgur.com/jEqCfZ3

   move only one step of either list,  which list?
      A: has overlap: check both end, select smaller one
         no overlap: check start and end
      B: only check both end. select smaller one or both if both ends are same
      A is same as B
      https://imgur.com/a/Wl25Ht7
  */
  public int[][] intervalIntersection_(int[][] A, int[][] B) {
    int i = 0, j = 0; // i: index of s; j: index of f
    List<int[]> ans = new ArrayList<>();
    while (i < A.length && j < B.length) {
      int[] a = A[i];
      int[] b = B[j];

      int maxl = Math.max(b[0], a[0]), minr = Math.min(b[1], a[1]);
      if (maxl <= minr) { // has overlap
        ans.add(new int[] {maxl, minr});
      }
      // discard at least one
      if (b[1] < a[1]) j++; // smaller end
      else if (a[1] < b[1]) i++;
      else { // same end
        i++;
        j++;
      }
    }

    return ans.toArray(new int[ans.size()][]);
  }

  public int[][] intervalIntersection(int[][] A, int[][] B) {
    List<int[]> ans = new ArrayList();
    int i = 0, j = 0;

    while (i < A.length && j < B.length) {
      int maxL = Math.max(A[i][0], B[j][0]);
      int minR = Math.min(A[i][1], B[j][1]);
      if (maxL <= minR) ans.add(new int[] {maxL, minR});

      // check both end and remove one which is the smaller one. if both end are same then next step
      // has not overlap
      if (A[i][1] < B[j][1]) i++;
      else j++;
    }

    return ans.toArray(new int[ans.size()][]);
  }
  /*---------------------------------------------------------------------------
  overlap of all sorted list of intervals

  Logic:
  in `matches`
  - each list has 1 or more time intervals ordered by time and none of the intervals within a overlap.
  - each time interval has 1 or more snapshot match some condition intensity >= given number
  */
  static List<MotionPeriod> motionPeriodsForCameras(List<List<MotionPeriod>> matches) {
    Iterator<List<MotionPeriod>> it = matches.iterator();
    List<MotionPeriod> r = new LinkedList<>(); // result
    while (it.hasNext()) {
      if (r.size() == 0) r = it.next();
      else {
        r = overlapOfAll(r, it.next());
      }
    }
    return r;
  }

  /*
  overlap of 2 sorted list of intervals
  2 point: each time move one step in either list
  Assume a and b is not empty or null
  */
  private static List<MotionPeriod> overlapOfAll(List<MotionPeriod> a, List<MotionPeriod> b) {
    List<MotionPeriod> r = new LinkedList<>();
    while (a.size() > 0 && b.size() > 0) {
      MotionPeriod ai = a.get(0), bi = b.get(0); // left end of a and b

      if (ai.end < bi.start) a.remove(0);
      else if (bi.end < ai.start) b.remove(0);
      else { // has overlap
        r.add(new MotionPeriod(Math.max(ai.start, bi.start), Math.min(ai.end, bi.end)));
        if (ai.end < bi.end) a.remove(0);
        else b.remove(0);
      }
    }
    return r;
  }

  static class MotionPeriod {
    int start;
    int end;

    public MotionPeriod(int start, int end) {
      this.start = start;
      this.end = end;
    }
  }
}
