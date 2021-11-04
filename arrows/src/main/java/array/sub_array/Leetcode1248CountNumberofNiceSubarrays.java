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

package array.sub_array;

import java.util.HashMap;
import java.util.Map;

public class Leetcode1248CountNumberofNiceSubarrays {
  /*
    Leetcode 1248. Count Number of Nice Subarrays

    Given an array of integers nums and an integer k. A continuous subarray is called nice if there are k odd numbers on it.

    Return the number of nice sub-arrays.

    Input: nums = [1,1,2,1,1], k = 3
    Output: 2
    Explanation: The only sub-arrays with 3 odd numbers are [1,1,2,1] and [1,2,1,1].

    Input: nums = [2,4,6], k = 1
    Output: 0
    Explanation: There is no odd numbers in the array.


    Input: nums = [2,2,2,1,2,2,1,2,2,2], k = 2
    Output: 16


    Constraints:

    1 <= nums.length <= 50000
    1 <= nums[i] <= 10^5
    1 <= k <= nums.length
  */

  /*
  initial window is [left, i]
  O(N) time O(1) space
  k is number of odd in window[left, i]; left can be at odd or even number index, left move when odd number is k
  cnt is number of matched sub-array
  cnt happen when `i` reach 5, and exist till `i` is at even number
  */
  public int numberOfSubarrays(int[] nums, int K) {
    int k = 0, cnt = 0, r = 0, left = 0;
    for (int i = 0; i < nums.length; i++) {
      if ((nums[i] & 1) == 1) {
        k++;
        cnt = 0; // cnt: lifetime
      }
      while (k == K) {
        if ((nums[left] & 1) == 1) k--;
        left++;
        cnt++;
      }
      r += cnt;
    }
    return r;
  }
  /*
  Hint: After replacing each even by zero and every odd by one can we use prefix sum to find answer
        because '... if there are k odd numbers on it'
        means the sum of odd is k, focus on the quantity of odd, E.g. for sub-array  [7, 7 ,7] k is 3 not 1

  move the focus from subarray to the other part of the wold
  O(N) time, space
   */
  public int numberOfSubarrays_(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);
    int r = 0, odd = 0;
    for (int i = 0; i < nums.length; i++) {
      if ((nums[i] & 1) == 1) odd++;
      if (map.containsKey(odd - k)) r += map.get(odd - k);
      map.put(odd, map.getOrDefault(odd, 0) + 1);
    }
    return r;
  }

  /*
   Exactly K times = at most K times - at most K - 1 times
  */
  public int numberOfSubarrays__(int[] A, int k) {
    return atMost(A, k) - atMost(A, k - 1);
  }
  //   O(N) time O(1) space, God like simple
  public int atMost(int[] A, int n) {
    int a = 0, l = 0, k = 0; // window is [l, r], each time move one step
    for (int r = 0; r < A.length; r++) {
      k += A[r] & 1;
      while (k == n + 1) k -= A[l++] & 1;
      a += r - l + 1;
      // number of all sub-array: end at r, including at most n odd number, or counter of odd number <= n
    }
    return a;
  }
}
