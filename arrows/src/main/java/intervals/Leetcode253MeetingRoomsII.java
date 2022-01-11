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

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Leetcode253MeetingRoomsII {
  /*
   253. Meeting Rooms II


    Given an array of meeting time intervals intervals where
    intervals[i] = [starti, endi],
    return the minimum number of conference rooms required.


    Input: intervals = [[0,30],[5,10],[15,20]]
    Output: 2


    Input: intervals = [[7,10],[2,4]]
    Output: 1

    Constraints:

        1 <= intervals.length <= 104
        0 <= starti < endi <= 106
  */

  /*
  Understanding
    an end time `y]` must have a start time `[x`

  */
  /*---------------------------------------------------------------------------
  Idea:
   sort by start time in ascending order
   min heap of end time
   result is the size of heap

  O(NlogN) time
  O(N）space used by min heap, contain N elements in the worst case
  */
  public int minMeetingRooms(int[][] A) {
    if (A == null || A.length == 0) return 0;
    Arrays.sort(A, Comparator.comparingInt(a -> a[0]));
    Queue<Integer> q = new PriorityQueue<>(A.length, Comparator.comparingInt(a -> a));
    q.offer(A[0][1]); // end time
    for (int i = 1; i < A.length; i++) {
      if (q.peek() <= A[i][0]) q.poll();
      q.offer(A[i][1]);
    }
    return q.size();
  }
  /*---------------------------------------------------------------------------
  Idea:

    Input: intervals = [[0,30],[5,10],[15,20]]
    Output: 2                10  20  30
                        0  5   15
    Input: intervals = [[7,10],[2,4]]
    Output: 1            4   10
                      2    7

   O(NlogN) time
   O(N）space
  */
  public int minMeetingRooms2(int[][] A) {
    if (A == null || A.length == 0) return 0;
    int[] s = new int[A.length];
    int[] e = new int[A.length];
    for (int i = 0; i < A.length; i++) {
      s[i] = A[i][0];
      e[i] = A[i][1];
    }
    Arrays.sort(e);
    Arrays.sort(s);
    int i = 0, j = 0, r = 0;
    while (i < A.length) {
      if (s[i++] < e[j]) r++;
      else j++;
    }

    return r;
  }
  /*---------------------------------------------------------------------------
  Idea:
   O(M) M is the max value of end
   when M is very large: split each interval into 2 cell
       [start 1]
       [end  -1]
    2 N cells, sort in ascending O(NlogN) time, O(N) space
    loop them and keep tracking the max sum of cell[1], O(N) time
   */
  public static int minMeetingRooms3(int[][] A) {
    int l = 0;
    for (int[] i : A) l = Math.max(l, i[1]);

    int[] t = new int[l + 1];
    for (int[] i : A) {
      t[i[0]]++;
      t[i[1]]--;
    }
    int max = 0;
    int s = 0;
    for (int i : t) {
      s += i;
      max = Math.max(max, s);
    }
    return max;
  }

  /* --------------------------------------------------------------------------
   Question: one room, calculate the max meetings it can hold
   Idea: sort the meetings with end time
         always select the meeting which finished firstly than any other
         once it is selected then any meeting whose start time is < the end
         time of selected meeting will not be arranged.

   O(NlogN) time, O(1) extra space
  */
  public static int maxMeetings(int[][] A) {
    Arrays.sort(A, Comparator.comparingInt(a -> a[1]));
    int e = A[0][1];
    int r = 1;
    for (int i = 1; i < A.length; i++) {
      if (e < A[i][0]) {
        r++;
        e = A[i][1];
      }
    }
    return r;
  }
}
