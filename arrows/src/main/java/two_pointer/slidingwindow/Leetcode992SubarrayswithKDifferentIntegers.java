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

package two_pointer.slidingwindow;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Leetcode992SubarrayswithKDifferentIntegers {

  /*
    Leetcode 992. Subarrays with K Different Integers

    Given an integer array nums and an integer k, return the number of good subarrays of nums.
    A good array is an array where the number of different integers in that array is exactly k.
    For example, [1,2,3,1,2] has 3 different integers: 1, 2, and 3.
    A subarray is a contiguous part of an array.


    Input: nums = [1,2,1,2,3], k = 2
    Output: 7
    Explanation: Subarrays formed with exactly 2 different integers:
          [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2]
    Example 2:

    Input: nums = [1,2,1,3,4], k = 3
    Output: 3
    Explanation: Subarrays formed with exactly 3 different integers: [1,2,1,3], [2,1,3], [1,3,4].


    Constraints:

    1 <= nums.length <= 2 * 104
    1 <= nums[i], k <= nums.length
  */

  /*
   sliding window can not fix it directly in a simple way
   But it is easy to figure out the number of subway with at most k distinct number
   the number of sub-array just including exactly k distinct number =
      at most(k) - at most(k-1)
   God like simple
  */
  public int subarraysWithKDistinct(int[] nums, int k) {
    return atMost(nums, k) - atMost(nums, k - 1);
  }

  // O(N) time, O(k) space
  private int atMost(int[] nums, int k) {
    int l = 0, a = 0; // window[l,r]
    Map<Integer, Integer> map = new HashMap();
    for (int r = 0; r < nums.length; r++) {
      map.put(nums[r], map.getOrDefault(nums[r], 0) + 1);
      while (map.size() == k + 1) {
        // adjust left index of window
        map.put(nums[l], map.get(nums[l]) - 1);
        if (map.get(nums[l]) == 0) map.remove(nums[l]);
        l++;
      }
      a += r - l + 1; // number all sub-array: end at r and including at most k distinct number;
    }
    return a;
  }
  /*
  Intuition
  use sliding window: index scope [l,r]
  1 each time move in the window only one char: array[r]
  2 then calculate the number of all sub-array that end with index r and containing k unique number
  3 for this, ony l and r is not enough, so need a third variable to keep the number of chars moved
    out window when the counter of the current window unique number <= k.
    So the third variable is only for duplicate number in array, if the array has not duplicated number
    it is easy


   Compared with other:
      1> solution 1 of Leetcode1248CountNumberofNiceSubarrays
       Same part: idea is same, see above 1,2, and 3
       Different part:
          there the array is composed by 0 and 1, any 1 is different with other 1
          actually care the sum of 1 or quantity of 1 in window is enough, so need not a map like that
          here.
          here need know the quality of number in window: count of the unique number in the window,
          so need a map.

      2> compare with at most idea
          same part:
             move in window only one number A[r]
             calculate total number of sub-array end at r
          different part:
             what sub-array
             there sub-array containing <=k unique number, so using l and r is enough
             here  sub-array containing ==k unique number, l and r is not enough

      O(N) time and O(k)space
   */
  public int subarraysWithKDistinct__(int[] A, int K) {
    int a = 0, dup = 0;
    Map<Integer, Integer> m = new HashMap<>();
    int l = 0;
    for (int r = 0; r < A.length; r++) {
      m.put(A[r], m.getOrDefault(A[r], 0) + 1);
      if (m.size() == K + 1) {
        m.remove(A[l]); // why there in only one A[l] in the window ? see the next loop
        l++;
        dup = 0;
      }
      if (m.size() == K) {
        while (m.get(A[l]) > 1) { // shrink left end of window
          dup++;
          m.put(A[l], m.get(A[l]) - 1);
          l++;
        }
        a += dup + 1;
      }
    }
    return a;
  }
  /*
  use a counter and a array to act as map to represent the number and their quantity in the
  window
  f[i]: number i's frequency in window [l,r].  '1 <= nums[i], k <= nums.length'
  c:  count of unique number in window [l,r]
   */
  public int subarraysWithKDistinct_(int[] A, int K) {
    int a = 0, dup = 0;
    int[] m = new int[A.length + 1];
    int l = 0, c = 0;
    for (int r = 0; r < A.length; r++) {
      if (m[A[r]]++ == 0) c++;
      if (c == K + 1) {
        m[A[l++]]--;
        c--;
        dup = 0;
      }

      if (c == K) {
        while (m[A[l]] > 1) { // shrink left end of window
          dup++;
          m[A[l++]]--;
        }
        a += dup + 1;
      }
    }
    return a;
  }
}
