//  Copyright 2017 The keepTry Open Source Project
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

package tree.heap;

import javax.management.RuntimeErrorException;
import java.util.Arrays;

public class Leetcode215KthLargestElementinanArray2 {

  // Use quick sort idea, check Arrays.sort();
  // O(N) best case / O(N^2) worst case running time
  public int findKthLargest(int[] nums, int k) {
    if (nums == null || nums.length == 0 || k < 0 || k > nums.length)
      throw new RuntimeException("invalid input");
    if (nums.length == 1) return nums[0];
    // At least 2 elements
    return partition(nums, 0, nums.length - 1, nums.length - k);
  }

  // k is the index of kth largest element
  // start and end is the index of element, as the scope of sums to be took into account
  public int partition(int[] nums, int start, int end, int k) {
    int l = start, r = end;
    // select the last element as the pivot value
    int p = nums[r];

    // use pivot value to partition all element: those < pivot is allocated to be left of index 'l'
    // others , those > or == pivot value, is allocated to be l and its right.
    for (int i = l; i < r; i++) {
      if (nums[i] < p) {
        int t = nums[l];
        nums[l] = nums[i];
        nums[i] = t;
        l++;
      }
    }

    // let pivot be the one with index l, thus use it to check if the expected kth largest element
    // is found.
    int t = nums[l];
    nums[l] = nums[r];
    nums[r] = t;

    // now all elements that > or == pivot are at index [l, r], and element at index l is the pivot
    // value
    // check
    if (l == k) return nums[l];
    else if (l < k) return partition(nums, l + 1, r, k);
    else
      //  (l > k)
      return partition(nums, start, l - 1, k);
  }
}
