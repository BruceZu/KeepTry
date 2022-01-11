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

public class Leetcode1353MaximumNumberofEventsThatCanBeAttended {

  /*
     Given an array of events where
     events[i] = [startDayi, endDayi].
     Every event i starts at startDayi and ends at endDayi.

     You can attend an event i at any day d where
     startTimei <= d <= endTimei.
     Notice that you can only attend one event at any time d.

     Return the maximum number of events you can attend.

      1 <= events.length <= 10^5
      events[i].length == 2
      1 <= startDayi <= endDayi <= 10^5

  Input: events = [[1,2],[2,3],[3,4]]
  Output: 3

  Input: events= [[1,2],[2,3],[3,4],[1,2]]
  Output: 4

  Input: events = [[1,4],[4,4],[2,2],[3,4],[1,1]]
  Output: 4

  Input: events = [[1,100000]]
  Output: 1

  Input: events = [[1,1],[1,2],[1,3],[1,4],[1,5],[1,6],[1,7]]
  Output: 7

  Input: events = [[1,5],[1,5],[1,5],[2,3],[2,3]]
  Output: 5

  Input: events = [[1,2],[1,2],[3,3],[1,5],[1,5]]
             12345
             --
             --
             -----
             -----
               --
               --
  Output: 5

  Input: events = [1,5][1,5][3,4][3,4]
          12345
          -----
          -----
            --
            --
  Output: 4

  sort by: start day in ascending order, then end day in ascending order
  It is wrong to sort by: end day in ascending order, then start day in ascending order
  E.g. [[1,5],[1,5],[1,5],[2,3],[2,3]], expect 5

  check day by day, date starts from 1, not checking meeting by meeting,
  this is useful when there are duplicated meetings cover more than 1 day

  once a meeting is attended, discard it. and should not select next meeting from next one in above sorted array
  E.g. [[1,5],[1,5],[1,5],[2,3],[2,3]], expect 5
  So also need another min heap to select the right one in this scenario.

  O(NlogN) time, O(N) space
  */
  public static int maxEvents(int[][] A) {
    // 1 <= events.length <= 10^5
    Arrays.sort(A, (a, b) -> (a[0] == b[0]) ? a[1] - b[1] : a[0] - b[0]);

    Queue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
    int r = 0, i = 0;
    for (int d = 1; d <= 100000; d++) { // 1 <= startDayi <= endDayi <= 10^5
      while (i < A.length && A[i][0] <= d && d <= A[i][1]) q.offer(A[i++]); // new

      while (!q.isEmpty() && q.peek()[1] < d) q.poll(); // old

      if (!q.isEmpty()) {
        r++;
        q.poll();
      }
    }
    return r;
  }
}
