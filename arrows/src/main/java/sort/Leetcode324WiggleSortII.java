//  Copyright 2016 The Sawdust Open Source Project
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

import java.util.Arrays;

public class Leetcode324WiggleSortII {
  /*
   324. Wiggle Sort II

    Given an integer array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....
    You may assume the input array always has a valid answer.


    Input: nums = [1,5,1,1,6,4]
    Output: [1,6,1,5,1,4]
    Explanation: [1,4,1,5,1,6] is also accepted.

    Input: nums = [1,3,2,2,3,1]
    Output: [2,3,1,3,1,2]

    Constraints:
        1 <= nums.length <= 5 * 10^4
        0 <= nums[i] <= 5000
        It is guaranteed that there will be an answer for the given input nums.
    Follow Up: Can you do it in O(n) time and/or in-place with O(1) extra space?

  */
  /*---------------------------------------------------------------------------
    Understand:
     arrange number from bigger -> small order to target location
         1,    3,   5, ..., then
      0,    2,    4, ...

               [biggers ->      medians]  odd index
     [medians ->        smallers]            even index
     - the median numbers are separated on both sides
     - in `small, bigger, small` wiggle sort
  */
  /*---------------------------------------------------------------------------
   Idea:
   Sort array + clone + arrange
   N is 6: left median at the target array end
   [1,1,2,2,2,3]
   [  3   2   2] else does not work:  [1   1   2  ] or [  1   1   2]
   [2 3 1 2 1 2]                      [1 2 1 2 2 3]    [2 1 2 1 3 2] not `small, big, small` wiggle sort

   N is 5:  median is at the target array start
    0 1 2 3 4  original index
   [1,1,2,2,3]
   [  3   2  ]
   [2 3 1 2 1]
    0 1 2 3 4  target index

   target index=(2 * (N - original index ) + 1) % (N | 1)

  O(NlgN) time and O(N) extra space
   */
  public static void wiggleSort_(int[] a) {
    int N = a.length;
    Arrays.sort(a);
    int[] o = Arrays.copyOf(a, N);

    for (int i = N - 1; i >= 0; i--) {
      a[(2 * (N - 1 - i) + 1) % (N | 1)] = o[i];
    }
  }
  /* --------------------------------------------------------------------------
    Observe:
    1> If medians are at their right location
      it still works
      [7,8,9,5,5,5,1,2,3]
      arrange number in descending order to target index 1, 3 ,5,....0, 2, 4...
      [  7   8   9   5  ]
      [5 7 5 8 1 9 2 5 3]
      how about   [7,5,5,5,5,5,5,5,3]? "You may assume the input array always has a valid answer."

      So: need not sort with O(nlogn) time
      Instead, apply partition with pivotal idea:
      - with quick select to get the (left) median value,(with median pivotal to make O(N) time O(1) space)
      - with the median value to part array in 3 way.  [>p] [=p] [<p]. Thus, the medians are collected together. Then arrange as above said

    2> Observe: from within the wiggle sorted array to find the 3 ways partitioned result by
       pivotal or (left) median along the member,bigger-> pivot-> smaller,arranged order
       which is a function of general index 0,1,2...N-1

      2-1> N=6 even length array
       0  1  2  3  4  5  index (original)
      [3, 2  2  2, 1  1]  the order of number being arranged. Descending.

      [2, 3  1  2, 1  2]  target array, wiggle sorted
          0     1     2   index (original)
       3     4     5      index (original)
       0  1  2  3  4  5   current index, N=6, N|1=7

      Observe:
           3 2 2 2 1 1  number value   line a
           0 1 2 3 4 5  original index line b
           1 3 5 0 2 4  target index   line c

      2-2> N=5 odd length array
      [13212] quick select -> median is 2 -> partition into 3 way in descending:
       0 1 2 3 4  index (original)
      [3 2 2 1 1]  the order of number being arranged. Descending.

      [2 3 1 2 1]  target array, wiggle sorted
         0   1     index (original)
       2   3   4   index (original)
       0 1 2 3 4   current index, N=5
    Observe:
           3 2 2 2 1    number value    line a
           0 1 2 3 4    original index  line b
           1 3 0 2 4    target index    line c
  Find:
        - from lines b and c: target index of each A[i] is (i*2 +1) % (N|1)
        - loop along line b to apply partition idea, it works.
          it also works for line c, no matter line a is a sorted array or not, only
          if the median value is provided. with this order we find partitioned descending
          array in wiggle sorted array.

         The order is the one with it to arrange imaged descending partitioned/sorted array
         [biggers, pivot(s) , smallers] number to its target index of `small, biggle, small`
         wiggle sorted array.
         [ 7      2      5     4     8      5    9    6    5 ]
                1(0)          3(1)         5(2)      7(3)
          0(4)          2(5)        4(6)        6(7)      8(8)
         quick select -> (left median) is 5
         partition array   [ 7 2 5 4 8 5 9 6 5 ] with 5 along the order:
         1  3  5  7; 0  2  4  6  8   target index of `small, biggle, small` wiggle sorted array.
        (0, 1, 2, 3, 4, 5, 6, 7, 8)  index of imaged descending partitioned/sorted array

    partition with pivotal is O(N) time O(1) space
   */

  int N;

  public void wiggleSort(int[] a) {
    N = a.length;
    // index of (left) median;
    int m = N - 1 >> 1;
    quickSelect(a, 0, N - 1, m);
    partIn3ways(a, a[m]);
  }

  public void quickSelect(int[] a, int l, int r, int k) {
    while (true) {
      int p = separate(a, l, r);
      if (p == k) return;
      else if (p > k) r = p - 1;
      else l = p + 1;
    }
  }
  /*
    Ascending order
    [l, < pivotal value], pivotal it self,[ >= pivotal, r]
  */
  private int separate(int[] a, int l, int r) {
    int p = (l + r) >> 1, pv = a[p]; // keep pivot value before move it to index r
    swap(a, p, r);
    int s = l;
    for (int i = l; i <= r - 1; i++) if (a[i] < pv) swap(a, i, s++);
    swap(a, s, r);
    return s;
  }

  private void swap(int[] a, int l, int r) {
    if (l != r) {
      int t = a[l] ^ a[r];
      a[l] ^= t;
      a[r] ^= t;
    }
  }

  public void partIn3ways(int[] a, int p) {
    int b = 0, s = N - 1; // for next Bigger, Smaller
    int i = 0; // original index in descending partitioned array
    while (i <= s) {
      if (a[f(i)] > p) swap(a, f(i++), f(b++));
      else if (a[f(i)] < p) swap(a, f(i), f(s--)); // not move i
      else i++;
    }
  }
  // i's target array index
  public int f(int i) {
    return (2 * i + 1) % (N | 1);
  }
}
