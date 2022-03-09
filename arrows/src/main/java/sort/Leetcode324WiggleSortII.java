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
import java.util.List;

public class Leetcode324WiggleSortII {
  /*
   324. Wiggle Sort II

    Given an integer array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....
    You may assume the input array always has a valid answer.


    Input: nums = [1,5,1,1,6,4]
    Output:       [1,6,1,5,1,4]
    Explanation: [1,4,1,5,1,6] is also accepted.

    Input: nums = [1,3,2,2,3,1]
    Output:       [2,3,1,3,1,2]

    Constraints:
        1 <= nums.length <= 5 * 10^4
        0 <= nums[i] <= 5000
        It is guaranteed that there will be an answer for the given input nums.
    Follow Up: Can you do it in O(n) time and/or in-place with O(1) extra space?

  */
  /*---------------------------------------------------------------------------
  Understand:
  1 `assume the input array always has a valid answer`
      [7,8,9,5,5,5,5,5,3]
      =>
      [  7   8   9   5  ]
      [5   5   5   5   3]
     "You may assume the input array always has a valid answer."
  2 Observe:
     need not sort, just separate array into 2 groups with quick select O(N) time O(1) space
  3 distribution
   - the median numbers are separated on both sides
   - keep same median number larger distance as they can.
   So, the wiggle sort order decide the order used to distribute the separated array
    https://imgur.com/oJSklty.png

  3 index relation between the separated array and wiggle sorted array
    https://imgur.com/80RkGIp.png

  4 distributed the separated array in-place, without extra space
     once get the pivot = A[N/2] by quick select
     with it if we directly apply the 3-way partition with order [biggers, pivot(s) , smallers] to original array.
     as a result the original array would become the expected 3 ways.

     Actually with the index mapping relation between
       - the 3 ways [biggers, pivot(s) , smallers] array and
       - `small, biggle, small` wiggle order sorted array
     passing the partition operation directly to target array index.
     so as the result, the original array is becomes `small, biggle, small` wiggle order sorted array.
     https://imgur.com/oXYCgmy.png
  */

  /*
  General solution:
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
  /*---------------------------------------------------------------------------
   O(N) time and O(1) space
  */
  int N;

  public void wiggleSort(int[] A) {
    N = A.length;
    // index of
    //   - median (odd length) or
    //   - left or right median (even length)
    //   in ascending order
    int m = N - 1 >> 1; //  N >> 1;
    quickSelect(A, 0, N - 1, m); // target is to get the A[m]
    partIn3ways(A, A[m]);
  }

  // K is 0-based index
  public void quickSelect(int[] a, int l, int r, int k) {
    while (true) { //   while (l < r) {
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
