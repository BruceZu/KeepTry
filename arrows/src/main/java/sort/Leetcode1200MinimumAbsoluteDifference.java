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

package sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leetcode1200MinimumAbsoluteDifference {
  /*
   Leetcode  1200. Minimum Absolute Difference

   Given an array of distinct integers arr,
   find all pairs of elements with the minimum absolute difference of any two elements.

   Return a list of pairs in ascending order(with respect to pairs), each pair [a, b] follows

   a, b are from arr
   a < b
   b - a equals to the minimum absolute difference of any two elements in arr


   Input: arr = [4,2,1,3]
   Output: [[1,2],[2,3],[3,4]]
   Explanation: The minimum absolute difference is 1.
                List all pairs with difference equal to 1 in ascending order.


   Input: arr = [1,3,6,10,15]
   Output: [[1,3]]


   Input: arr = [3,8,-10,23,19,-4,-14,27]
   Output: [[-14,-10],[19,23],[23,27]]


   Constraints:

   2 <= arr.length <= 105
   -106 <= arr[i] <= 106
  */
  /*
  Watch:
   brute force approach,  iterate every possible pair from the array.
   time O(n^2)

   Improvement:
   picked two integers a and b  ( assume  a <  b ).
   if there exists X from the array
   a < X < b.
   hence X - a and b - X are the absolute difference < b-a
   = > Sorting
   Time: O(N⋅log(N))
   space depends on
   - the sorting algorithm  Arrays.sort(int[]) is Dual-Pivot Quicksort
   - the list used to keep answer pairs
   space is O(N)

   For some scenarios where
   - the value range is small
   - and value range M <= array length N
   => counting sort  Time: O(N), Space O(N)
      value [-10^6 ~  -10^6]
      The max diff =2⋅10^6
  O(M+ N) time and space, M is value range

  compared bucket sort, counting sort stores a single number (the count of items) per bucket
  counting sort is not a comparison sort, the O(n⋅log(n)) time does not apply.
  */

  public List<List<Integer>> minimumAbsDifference(int[] arr) {
    // arr is not null
    Arrays.sort(arr);

    int mind = Integer.MAX_VALUE; // minPairDiff
    List<List<Integer>> ans = new ArrayList();

    for (int i = 0; i < arr.length - 1; ++i) {
      int curd = arr[i + 1] - arr[i];

      if (curd == mind) {
        ans.add(Arrays.asList(arr[i], arr[i + 1]));
      } else if (curd < mind) {
        mind = curd;
        ans.clear(); // better than new
        ans.add(Arrays.asList(arr[i], arr[i + 1]));
      }
    }
    return ans;
  }

  /*
  count sort


     array: min ... max
     line:  0   ... max-min (length is max-min+1)
  */
  public List<List<Integer>> minimumAbsDifference_countSort(int[] arr) {
    // value range count M=max-min+1,
    // it is also the length of 0-based array used in count sort
    int min = arr[0];
    int max = arr[0];
    for (int v : arr) {
      min = Math.min(min, v);
      max = Math.max(max, v);
    }

    int M = max - min + 1;
    boolean[] line = new boolean[M]; // default false
    for (int v : arr) {
      line[v - min] = true;
    }

    int md = max - min; // init minimum diff
    int p = 0;
    // p: previous number mapped index in line
    // the min is mapped to the index of 0 in line
    List<List<Integer>> ans = new ArrayList();
    for (int i = 1; i <= max - min; i++) {
      if (!line[i]) continue;
      int curd = i - p;
      if (curd == md) {
        ans.add(Arrays.asList(p + min, i + min));
      } else if (curd < md) {
        md = curd;
        ans = new ArrayList();
        ans.add(Arrays.asList(p + min, i + min));
      }

      p = i;
    }

    return ans;
  }
}
