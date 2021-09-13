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

package sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Leetcode759EmployeeFreeTime {
  /*
       759. Employee Free Time

   We are given a list schedule of employees, which represents the working time for each employee.
   Each employee has a list of non-overlapping Intervals, and these intervals are in sorted order.
   Return the list of finite intervals representing common, positive-length free time for
   all employees, also in sorted order.

   (Even though we are representing Intervals in the form [x, y],
   the objects inside are Intervals, not lists or arrays.
   For example, schedule[0][0].start = 1, schedule[0][0].end = 2,
   and schedule[0][0][0] is not defined).

   Also, we wouldn't include intervals like [5, 5] in our answer, as they have zero length.

   Input: schedule = [[[1,2],[5,6]],[[1,3]],[[4,10]]]
   Output: [[3,4]]
   Explanation: There are a total of three employees, and all common
   free time intervals would be [-inf, 1], [3, 4], [10, inf].
   We discard any intervals that contain inf as they aren't finite.

   Input: schedule = [[[1,3],[6,7]],[[2,4]],[[2,5],[9,12]]]
   Output: [[5,6],[7,9]]

   Constraints:

       1 <= schedule.length , schedule[i].length <= 50
       0 <= schedule[i].start < schedule[i].end <= 10^8
  */
  /*
  Watch:
    0 <= schedule[i].start < schedule[i].end
    use a min heap or only sort
    O(NlogN) time O(N) space
   */

  public List<Interval> employeeFreeTime(List<List<Interval>> avails) {
    List<Interval> r = new ArrayList<>();
    List<Interval> timeLine = new ArrayList<>();
    avails.forEach(e -> timeLine.addAll(e));
    Collections.sort(timeLine, (Comparator.comparingInt(a -> a.start)));

    Interval tmp = timeLine.get(0);
    for (Interval each : timeLine) {
      if (tmp.end < each.start) {
        r.add(new Interval(tmp.end, each.start));
        tmp = each;
      } else tmp = tmp.end < each.end ? each : tmp;
    }
    return r;
  }

  class Interval {
    public int start;
    public int end;

    public Interval() {}

    public Interval(int _start, int _end) {
      start = _start;
      end = _end;
    }
  }
  ;
}
