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

package bitmanipulation;

/*
169. Majority Element

Difficulty: Easy
Given an array of size n, find the majority element.
The majority element is the element that appears more than ⌊ n/2 ⌋ times.

You may assume that the array is
- non-empty
- the majority element always exist
in the array.


n == nums.length
1 <= n <= 5 * 104
-2^31 <= nums[i] <= 2^31 - 1

Idea:
  1 vote. {@link array.Leetcode169MajorityElement2#majorityElement(int[])}
  2 check sum of 1 on column i of number bit representation > n/2

O(N) time, O(1) space
 */
public class Leetcode169MajorityElement {
  public static int majorityElement1(int[] nums) {
    int r = 0;
    for (int i = 0; i <= 31; i++) {
      int b = 0; // sum of 1 on column i of number bit representation
      for (int n : nums) {
        b += n >>> i & 1;
      }
      if (b > nums.length >>> 1) r += 1 << i;
    }
    return r;
  }
}
