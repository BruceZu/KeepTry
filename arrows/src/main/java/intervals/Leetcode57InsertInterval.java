//  Copyright 2022 The KeepTry Open Source Project
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

package intervals;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Leetcode57InsertInterval {
  /*
    Leetcode 57. Insert Interval
    You are given an array of non-overlapping intervals `intervals` where
    intervals[i] = [starti, endi] represent the start and the end of the ith interval
    and intervals is sorted in ascending order by starti.

    You are also given an interval newInterval = [start, end] that represents the start and end
    of another interval.

    Insert newInterval into intervals such that intervals is still sorted in ascending order
    by starti and intervals still does not have any overlapping intervals
    (merge overlapping intervals if necessary).

    Return intervals after the insertion.


    Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
    Output: [[1,5],[6,9]]


    Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
    Output: [[1,2],[3,10],[12,16]]
    Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].


    Constraints:

    0 <= intervals.length <= 104
    intervals[i].length == 2
    0 <= starti <= endi <= 105
    intervals is sorted by starti in ascending order.
    newInterval.length == 2
    0 <= start <= end <= 105
  */

  /*
  O(N) time, space
  */
  public int[][] insert_(int[][] intervals, int[] newInterval) {
    int[] ni = newInterval;
    LinkedList<int[]> ans = new LinkedList<>();
    for (int[] i : intervals) {
      if (ni[0] <= i[0] && i[0] <= ni[1]
          || ni[0] <= i[1] && i[1] <= ni[1]
          || ni[0] <= i[0] && i[1] <= ni[1]
          || i[0] <= ni[0] && ni[1] <= i[1]) {
        // has overlap
        ni = new int[] {Math.min(i[0], ni[0]), Math.max(i[1], ni[1])};
      } else {
        ans.add(i);
      }
    }

    // now have to find the right location for the new intreval to keep ascending order of result
    /*
     Cases:  [[3,5], [12,15]], and  [6,6]
             [[]], and  [6,6]
             [[1,2]], and [6,6]
    */

    int i = -1;
    if (ans.size() == 0 || ni[1] < ans.get(0)[0]) i = 0; // only it || left side
    else if (ans.get(ans.size() - 1)[1] < ni[0]) i = ans.size(); // right side
    else { // somewhere middle
      int[] p = null; // pre
      int curi = -1;
      for (int[] I : ans) {
        curi++;
        if (p == null) {
          p = I;
          continue;
        }
        if (p[1] < ni[0] && ni[1] < I[0]) {
          i = curi;
          break;
        }
        p = I;
      }
    }
    ans.add(i, ni);
    return ans.toArray(new int[ans.size()][2]);
  }

  /*
  Idea:
  the new one should wait in line by its start value
  Details:
  i is a interval in sorted non-overlapping intervals
  if keep i[0] < new1[0]:
  and if         new1[0] <= i[1] then there is overlap
  O(N) time, space
  */
  public int[][] insert(int[][] is, int[] new1) {
    int i = 0, n = is.length;
    LinkedList<int[]> r = new LinkedList<>();
    while (i < n && is[i][0] < new1[0]) {
      r.add(is[i++]);
    }
    // it is the turn of the new one
    if (r.isEmpty() || r.getLast()[1] < new1[0]) {
      r.add(new1);
    } else {
      int[] t = r.removeLast(); // tail
      t[1] = Math.max(t[1], new1[1]);
      r.add(t);
    }
    // continue other in original line ordered by start value
    while (i < n) {
      int[] I = is[i];
      if (r.getLast()[1] < I[0]) r.add(I);
      else {
        int[] t = r.removeLast();
        t[1] = Math.max(t[1], I[1]);
        r.add(t);
      }
      i++;
    }
    return r.toArray(new int[r.size()][2]);
  }

  /*
  Idea:
    clear both side intervals who have not overlap with the new one
    O(N) time, space
  */
  public int[][] insert__(int[][] is, int[] new1) {
    List<int[]> r = new LinkedList<>();
    int i = 0;
    while (i < is.length && is[i][1] < new1[0]) {
      r.add(is[i++]);
    }
    // merge new one with those who has overlap with it if they exists
    while (i < is.length && is[i][0] <= new1[1]) {
      new1 = new int[] {Math.min(new1[0], is[i][0]), Math.max(new1[1], is[i][1])};
      i++;
    }
    r.add(new1);

    while (i < is.length) r.add(is[i++]);
    return r.toArray(new int[r.size()][2]);
  }

  /* --------------------------------------------------------------------------
  Assume the intervals is no-overlappting, no-sorted random intervals
  return intervals without no-overlappting intervals after inserting the newOne
  O(N) time and
  O(N) space, used by ans list
  */
  public static List<int[]> insert(List<int[]> intervals, int[] newOne) {
    List<int[]> ans = new ArrayList<>();
    if (intervals == null || intervals.size() == 0) {
      ans.add(newOne);
      return ans;
    }

    for (int[] i : intervals) {
      if (!(i[1] < newOne[0] || newOne[1] < i[0])) {
        newOne = new int[] {Math.min(i[0], newOne[0]), Math.max(i[1], newOne[1])};
      } else {
        ans.add(i);
      }
    }
    ans.add(newOne);
    return ans;
  }

  /*
  Followup: new one-> new ones
  new ones: O(NlogN) sort and merge
  now: 2 sorted no-overlapping list
  O(M+N):  select smaller start of current first of 2 list
           compare it with the tail of result list

  better solution?
  */
  public static List<int[]> insert(List<int[]> intervals, List<int[]> newOnes) {
    List<int[]> ans = new ArrayList<>();
    // Todo:
    return ans;
  }

  public static void main(String[] p) {
    List<int[]> list = new ArrayList<>();
    list.add(new int[] {3, 5});
    list.add(new int[] {1, 2});
    list.add(new int[] {6, 7});
    list.add(new int[] {8, 10});
    list.add(new int[] {12, 16});
    List<int[]> ans = insert(list, new int[] {4, 8});
    for (int[] each : ans) {
      System.out.println("[" + each[0] + "," + each[1] + "]");
    }
  }
}
