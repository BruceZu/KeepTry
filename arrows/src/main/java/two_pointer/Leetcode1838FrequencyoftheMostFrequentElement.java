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

package two_pointer;

import java.util.Arrays;

public class Leetcode1838FrequencyoftheMostFrequentElement {
  /*
   Leetcode 1838. Frequency of the Most Frequent Element

    The frequency of an element is the number of times it occurs in an array.
    You are given an integer array nums and an integer k. In one operation,
    you can choose an index of nums and increment the element at that index by 1.

    Return the maximum possible frequency of an element after performing at most k operations.


    Input: nums = [1,2,4], k = 5
    Output: 3
    Explanation: Increment the first element three times and the second element two times to make nums = [4,4,4].
    4 has a frequency of 3.


    Input: nums = [1,4,8,13], k = 5
    Output: 2
    Explanation: There are multiple optimal solutions:
    - Increment the first element three times to make nums = [4,4,8,13]. 4 has a frequency of 2.
    - Increment the second element four times to make nums = [1,8,8,13]. 8 has a frequency of 2.
    - Increment the third element five times to make nums = [1,4,13,13]. 13 has a frequency of 2.


    Input: nums = [3,9,6], k = 2
    Output: 1


    Constraints:

    1 <= nums.length <= 105
    1 <= nums[i] <= 105
    1 <= k <= 105
  */

  /*
    1.
     Do not care how the k is distributed, instead, focus on the possible max frequency on each index, starting from one side.
    E.g. from the last element of the sorted A[], for each r, try to extend the l with k
    using sliding window to reuse the common part of 2 neighbor window
     https://imgur.com/a/kvsnRJO
     O(N) time O(1) space
    2.
    it can be applied to duplicated numbers cases in array
  */
  public int maxFrequency_(int[] A, int k) {
    int a = 1; // at lest 1
    Arrays.sort(A); // O(NlogN) time
    // O(N) time
    int r = A.length - 1, l = r;
    for (; r >= 1; r--) {
      while (l >= 0 && k >= A[r] - A[l]) {
        k -= A[r] - A[l];
        l--; // l stop at unreachable index by k
      }
      a = Math.max(a, r - l);
      k += (A[r] - A[r - 1]) * (r - l - 1);
    }
    return a;
  }

  /*
  Sliding window [l, r]
  r start from left side of array.
  calculate the window number sum+k this requires long type
  the l of window is the extendable limitation with k for r
  https://imgur.com/a/eBPWz2y
  */
  public int maxFrequency(int[] A, int k) {
    Arrays.sort(A);
    int a = 1, l = 0, r;
    long wsum = 0;
    for (r = 0; r < A.length; r++) {
      wsum += A[r];
      while (wsum + k < (long) A[r] * (r - l + 1)) {
        wsum -= A[l];
        l++;
      }
      a = Math.max(a, r - l + 1);
    }
    return a;
  }
  /*
   save the window num which is long then let k is long Type
   watch
   https://imgur.com/a/HahdQXP
   observe:
     when k can NOT extend current window to next number
         - just keep current window which has happened and maybe the max one.
           It is use `if` replace `while`, it can save
            -- operation of variable `a`
            -- compare operation to tracing max frequency
     else:
         - extend current window.
  */
  public static int maxFrequency(int[] A, long k) {
    Arrays.sort(A);
    int l = 0, r;
    for (r = 0; r < A.length; r++) {
      k += A[r];
      if (k < (long) A[r] * (r - l + 1)) {
        k -= A[l];
        l++;
      }
    }
    return r - l;
  }

  public static void main(String[] args) {
    System.out.println(
        maxFrequency(new int[] {1, 2, 3, 20, 21, 30, 50, 51, 52, 53, 54, 55}, 6l) == 4);
  }
}
