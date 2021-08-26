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

public class Leetcode912SortanArray {
  /*
    912. Sort an Array
    Given an array of integers nums, sort the array in ascending order.

    Input: nums = [5,2,3,1]
    Output: [1,2,3,5]

    Input: nums = [5,1,1,2,0,0]
    Output: [0,0,1,1,2,5]

    Constraints:

        1 <= nums.length <= 5 * 104
        -5 * 104 <= nums[i] <= 5 * 104
  */
  /*---------------------------------------------------------------------------
   MergeSort
    for l=1,2,4,8,..., till l<N
    sort [i, i+l-1] [i+l, in{N-1,i+2*l-1], i=i+2*l till i+l<=N-1a
   O(NlogN) time
   O(N）space. used by the tmp assist array
  */
  public int[] sortArray_(int[] nums) {
    int[] tmp = new int[nums.length];
    int l = 1;
    while (l < nums.length) {
      int i = 0;
      while (i + l <= nums.length - 1) {
        sort(nums, i, i + l - 1, i + l, Math.min(nums.length - 1, i + 2 * l - 1), tmp);
        i = i + 2 * l; // skip 2*L
      }

      l <<= 1;
    }
    return nums;
  }

  private void sort(int[] a, int i, int ie, int j, int je, int[] tmp) {
    System.arraycopy(a, i, tmp, i, je + 1 - i);
    int k = i;
    while (i <= ie || j <= je) {
      if (j == je + 1 || i <= ie && tmp[i] < tmp[j]) a[k++] = tmp[i++];
      else a[k++] = tmp[j++];
    }
  }
  // initial version of sort
  private void sort_(int[] a, int i, int ie, int j, int je, int[] tmp) {
    System.arraycopy(a, i, tmp, i, je + 1 - i);
    int k = i;
    while (i <= ie || j <= je) {
      if (j == je + 1) while (i <= ie) a[k++] = tmp[i++];
      else if (i == ie + 1) while (j <= je) a[k++] = tmp[j++];
      else {
        if (tmp[i] < tmp[j]) a[k++] = tmp[i++];
        else a[k++] = tmp[j++];
      }
    }
  }
  /*---------------------------------------------------------------------------
   Quick Sort
    - pivot uses (left) median
    - partition array in 2 ways with pivot: [<p, p][>=p]

   O(NlogN) average time
   O(logN）average space used by call stack
  */
  public int[] sortArray(int[] nums) {
    if (nums == null || nums.length <= 1) return nums; // return `nums` not null
    quicksort2way(nums, 0, nums.length - 1);
    return nums;
  }

  private void quicksort2way(int[] a, int l, int r) {
    if (l >= r) return;
    int pi = partitionWithMedian(a, l, r);
    quicksort2way(a, l, pi - 1);
    quicksort2way(a, pi + 1, r);
  }

  private int partitionWithMedian(int[] a, int l, int r) {
    int len = r - l + 1;
    int pi = l + (len - 1 >>> 1);
    int pv = a[pi]; // keep the pivot value before it is swap to index r
    swap(a, pi, r);
    int s = l; // next index to sway into smaller
    for (int i = l; i < r; i++) if (a[i] < pv) swap(a, i, s++);
    swap(a, s, r);
    return s;
  }

  private void swap(int[] a, int i, int j) {
    if (i != j) {
      int t = a[i] ^ a[j];
      a[i] ^= t;
      a[j] ^= t;
    }
  }
}
