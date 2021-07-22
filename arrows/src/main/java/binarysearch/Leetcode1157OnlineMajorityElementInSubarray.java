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

package binarysearch;

import java.util.*;

public class Leetcode1157OnlineMajorityElementInSubarray {
  /*
       1 <= arr.length <= 2 * 10^4
       1 <= arr[i] <= 2 * 10^4
       0 <= left <= right < arr.length
       threshold <= right - left + 1
       2 * threshold > right - left + 1
       At most 10^4 calls will be made to query.

   The majority element of a subarray is an element that occurs threshold times or more in the subarray.

   Input
   ["MajorityChecker", "query", "query", "query"]
   [[[1, 1, 2, 2, 1, 1]], [0, 5, 4], [0, 3, 3], [2, 3, 2]]
   Output
   [null, 1, -1, 2]

   Explanation
   MajorityChecker majorityChecker = new MajorityChecker([1, 1, 2, 2, 1, 1]);
   majorityChecker.query(0, 5, 4); // return 1
   majorityChecker.query(0, 3, 3); // return -1
   majorityChecker.query(2, 3, 2); // return 2


  Understand:
         threshold >(right - left + 1)/2 so the answer at most is one;
         array element is positive value, >0;

  */

  /*===========================================================================
  Idea: binary search
   with Map<Integer, TreeSet<Integer>>:
       each distinct element value v : TreeSet of index of v.
   Time Limit Exceeded
   */
  class MajorityChecker4_ {
    Map<Integer, TreeSet<Integer>> d;

    public MajorityChecker4_(int[] arr) {
      d = new HashMap<>();
      for (int i = 0; i < arr.length; i++) {
        d.putIfAbsent(arr[i], new TreeSet<>());
        d.get(arr[i]).add(i);
      }
    }

    public int query(int left, int right, int threshold) {
      for (Map.Entry<Integer, TreeSet<Integer>> e : d.entrySet()) {
        if (e.getValue().subSet(left, true, right, true).size() >= threshold) return e.getKey();
      }
      return -1;
    }
  }
  // ---------------------------------------------------------------------------
  /*
  Same Idea binary search
   with Map<Integer, List<Integer>> :
       each  distinct element value v : list of index of v.
   Time Limit Exceeded
   */
  class MajorityChecker4__ {
    Map<Integer, List<Integer>> d;

    public MajorityChecker4__(int[] arr) {
      d = new HashMap<>();
      for (int i = 0; i < arr.length; i++) {
        d.putIfAbsent(arr[i], new ArrayList<>());
        d.get(arr[i]).add(i);
      }
    }

    public int query(int left, int right, int threshold) {
      for (Map.Entry<Integer, List<Integer>> e : d.entrySet()) {
        int l = Collections.binarySearch(e.getValue(), left);
        if (l < 0) l = ~l;
        int r = Collections.binarySearch(e.getValue(), right);
        if (r < 0) r = ~r - 1;
        if (r - l + 1 >= threshold) return e.getKey();
      }
      return -1;
    }
  }
  // ---------------------------------------------------------------------------
  /*
  Same Idea binary search
   with List<Integer>[]  :
       each  distinct element value v as index : value is the list of index in arr of v.
   Note: '1 <= arr[i] <= 2 * 10^4'
   MajorityChecker() O(N）time and space
   query() O(1)~O(logN) time depends on distinct arr[i],O(1) space
   */
  class MajorityChecker4 {

    List<Integer>[] d = new List[20001];

    public MajorityChecker4(int[] A) {
      for (int i = 0; i < A.length; i++) {
        if (d[A[i]] == null) d[A[i]] = new ArrayList();
        d[A[i]].add(i);
      }
    }

    public int query(int left, int right, int threshold) {
      for (int i = 0; i < d.length; i++) {
        if (d[i] == null) continue;
        int l = Collections.binarySearch(d[i], left);
        if (l < 0) l = ~l;
        int r = Collections.binarySearch(d[i], right);
        if (r < 0) r = ~r - 1;
        if (r - l + 1 >= threshold) return i;
      }
      return -1;
    }
  }
  /*===========================================================================
  Random pick majority possibility
   notice:
    1> The above binary search algorithm have to check each distinct element value's index array
       O(distinct value)
    2>
    "threshold <= right - left + 1
    2 * threshold > right - left + 1"
    There is at most one majority element in the given subarray

    So: alternative of 1> is to try some times, e.g.  try=7~10 , of random index
        to make sure the majority element appear if it exists.
        Note: the value of try=7~10 depends on test cases of leetcode.
        - too bigger it is Time Limit Exceeded
        - too small it is not enough to make find the  majority element
        The possibility of missing the majority element = 1/(2^try)
        So this solution is not general solution

   */
  static class MajorityChecker3 {

    int[] a;
    List<Integer>[] d = new List[20001];
    // O(N) time, space
    public MajorityChecker3(int[] A) {
      a = A;
      for (int i = 0; i < A.length; i++) {
        if (d[A[i]] == null) d[A[i]] = new ArrayList();
        d[A[i]].add(i);
      }
    }
    // O(logN) time, O(1) space
    public int query(int l, int r, int t) {
      Random ran = new Random();
      Set<Integer> seen = new HashSet<>();
      for (int i = 0; i < 8; i++) {
        int idx = l + ran.nextInt(r - l + 1);
        if (seen.contains(idx)) continue;
        seen.add(idx);
        if (d[a[idx]].size() < t) continue;
        int li = Collections.binarySearch(d[a[idx]], l);
        if (li < 0) li = ~li;
        int ri = Collections.binarySearch(d[a[idx]], r);
        if (ri < 0) ri = ~ri - 1;
        if (ri - li + 1 >= t) return a[idx];
      }
      return -1;
    }
  }
  /*===========================================================================
  Idea: boyer–moore majority vote algorithm
   */
  class MajorityChecker2 {
    int[] arr;
    // O(1) time, space
    public MajorityChecker2(int[] arr) {
      this.arr = arr;
    }
    // O(subarray length) time, O(1) space.
    public int query(int left, int right, int threshold) {
      int m = -1, n = 0;
      for (int i = left; i <= right; i++) {
        if (n == 0) {
          m = arr[i];
          n = 1;
        } else if (m == arr[i]) n++;
        else n--;
      }
      int c = 0;
      for (int i = left; i <= right; i++) {
        if (m == arr[i]) c++;
      }
      return c >= threshold ? m : -1;
    }
  }
}
