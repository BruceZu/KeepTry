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

package array;

public class Leetcode665NondecreasingArray {
  /*
   665. Non-decreasing Array
        Given an array nums with n integers,
        your task is to check if it could become non-decreasing
        by modifying at most one element.

        We define an array is non-decreasing if nums[i] <= nums[i + 1] holds
        for every i (0-based) such that (0 <= i <= n - 2).

    Input: nums = [4,2,3]
    Output: true
    Explanation: You could modify the first 4 to 1 to get a non-decreasing array.


    Input: nums = [4,2,1]
    Output: false
    Explanation: You can't get a non-decreasing array by modify at most one element.


    Constraints:

    n == nums.length
    1 <= n <= 10^4
    -10^5 <= nums[i] <= 10^5
  */
  /*
  https://imgur.com/xrMQE9g

  how to judge at most one misplaced element?
  find one misplaced element, add a patch to make misplaced element matched
  which one is the misplaced element?
  any of them can be, how to recognize it out
  As assume at most only one misplaced element:
   i-2|MIN, i-1 > i, i+1|MAX
   so either i-2|MIN <= i  or  i-1 <= i+1|MAX. at least one
      [1, 3 , 7, 12, 3, 15]
                     |
                  misplaced
      [1, 3 , 7, 12, 9, 11]
                 |
               misplaced
   if neither exist it is not at most one misplaced element:
     [3, 4, 2, 3 ]

  O(N) time, O(1) space
  */
  public boolean checkPossibility(int[] a) {
    if (a == null || a.length <= 1) return true;
    int N = a.length;
    boolean findOne = false;
    for (int i = 1; i < N; i++) {
      // i-2|MIN, i-1, i, i+1|MAX
      if (a[i] < a[i - 1]) {
        if (findOne) return false;
        if (a[i - 1] <= (i + 1 < N ? a[i + 1] : Integer.MAX_VALUE)) {
          a[i] = a[i - 1];
          findOne = true;
        } else if (a[i] >= (i - 2 >= 0 ? a[i - 2] : Integer.MIN_VALUE)) {
          a[i - 1] = a[i];
          findOne = true;
        }
        // not at least one:[ 3 4 2 3 ]
        else return false;
      }
    }
    return true;
  }

  /*
  Always check and make sure the previous part are non-descending
  https://imgur.com/eZLnunK
    when i-2<0  let MIN as it.
   */
  public boolean checkPossibility2(int[] A) {
    boolean findOne = false;
    for (int i = 1; i < A.length; i++) {
      if (A[i - 1] > A[i]) {
        if (findOne) return false;

        findOne = true;
        if (i < 2 || A[i - 2] <= A[i]) {
          A[i - 1] = A[i];
        } else {
          A[i] = A[i - 1];
        }
      }
    }

    return true;
  }

  /*
  Assume at most one misplaced element
  when find a unmatched pair, which one is the misplaced element?
  any of them can be
  */
  // find out which one is misplaced from index  in i-1 or i
  // and rearrange the array to be non-descending order
  private void reArrange(int[] a, int i) {
    // assume it is i
    // try back forward from element at index i
    int j = i;
    while (j - 1 >= 0 && a[j] < a[j - 1]) {
      swap(a, j, j - 1);
      j--;
    }
    // try forward from original element at index i-1, but
    //  after above loop i-1 and i is swapped, it is at index i.
    j = i;
    while (j + 1 < a.length && a[j] > a[j + 1]) {
      swap(a, j, j + 1);
      j++;
    }
  }

  private void swap(int[] a, int i, int j) {
    if (i != j) {
      int tmp = a[i] ^ a[j];
      a[i] = a[i] ^ tmp;
      a[j] = a[j] ^ tmp;
    }
  }
}
