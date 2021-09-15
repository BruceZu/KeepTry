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

package map;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class FindShortestInterval {
  /*
        Given a list of intervals, and a list of integers,
        calculate the best interval for each number in that list of intervals.

        Best intervals in defined as the shortest length interval that covers the number
        (e.g, [1.10] and [2,5] both cover number 4, but [2.5] is the best interval among these two).
        Also, the given list of intervals does not partially overlap,
        they are either disjoint or one fully overlaps another


      Input:
      intervals = {{2, 3}, {1, 20}, {15, 16}, {2, 5}, {1, 8}, {9, 12}, {6, 8}}
      nums = {3, 5, 7, 9, 15, 17, 40}
      Output: {{2, 3}, {2, 5}, {6, 8}, {9, 12}, {15, 16}, {1, 20}, {}}

      Explanation:
      for 3, these intervals cover it: {2,3}, {1,20}, {2,5}, {1,8},
      and {2,3} is the one has shortest length (=1),
      so output {2,3} for 3
  */
  /*

      A          [  ]
      B          [                       ]
      C                  [               ]
      D                      [     ]
      E   [                                     ]

    x:    2      4  6    8   10    12    14     16

    check:
        1 2  3   45 6 7  8 9,10,11,12  13,14, 15 16,  18

       can we assume for any interval start can be ==end? yes


  'the given list of intervals does not partially overlap,
   they are either disjoint or one fully overlaps another'

  Observer: with this property
      and convert the intervals into
      TreeMap<left,  TreeSet<rights>>
      TreeMap<right, TreeSet<lefts>>

      this property guarantee:
        for a given number
        if we can find the shorted interval from left side then
        we can not find another one from right side, vice versa.
            [   []  a ], find answer from right side
            [ a []    ], find answer from left side
        so if check from both side, each side only need check only once
        we can find the answer with O(logN) time

       for 18, higher(18) is null then it is null

       for 7 check its ceiling(7) is D, D's start > 7,
            higher(D) is C. C's start > 7
            higher(C) is B, B's start < 7.  so B is the answer.
       for 3 if we check intervals sorted by `start` it will fast.
     */
  private static List<int[]> shortestInterval(int[][] intervals, int[] nums) {
    TreeMap<Integer, TreeSet<Integer>> L = new TreeMap<>();
    TreeMap<Integer, TreeSet<Integer>> R = new TreeMap<>();
    for (int[] i : intervals) {
      int l = i[0], r = i[1];
      L.putIfAbsent(l, new TreeSet<>());
      L.get(l).add(r); // intervals with same start

      R.putIfAbsent(r, new TreeSet<>());
      R.get(r).add(l); // intervals with same end
    }
    List<int[]> a = new ArrayList<>();
    for (int n : nums) {
      Integer l = L.floorKey(n); // <= n
      Integer r = R.ceilingKey(n); // >= n

      if (l == null || r == null) {
        a.add(null);
        continue;
      }

      Integer ceil = L.get(l).ceiling(n);
      if (ceil != null) {
        a.add(new int[] {l, ceil});
        continue;
      }

      Integer floor = R.get(r).floor(n);
      if (floor != null) {
        a.add(new int[] {floor, r});
        continue;
      }
    }
    return a;
  }

  public static void main(String[] args) {
    shortestInterval(
        new int[][] {
          {4, 6},
          {4, 14},
          {8, 14},
          {10, 12},
          {2, 16},
        },
        new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 18});
  }
}
