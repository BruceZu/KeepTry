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

package array.duplicated;

/**
 * <pre>
 * Difficulty: Hard
 * Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), prove that at least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.
 *
 * Note:
 *     a   You must not modify the array (assume the array is read only).
 *     b   You must use only constant, O(1) extra space.
 *     c   Your runtime complexity should be less than O(n2).
 *     d   There is only one duplicate number in the array, but it could be repeated more than once.
 *
 * Tags:
 *      Binary Search
 *      Array
 *      Two Pointers
 * Similar Problems
 *      (H) First Missing Positive
 *      (M) Single Number
 *      (M) Linked List Cycle II
 *      (M) Missing Number
 *
 *
 * Ideas:
 *    a => or array as index and check sign is not allowed
 *    b => can use array as index and check times
 *    d => xor cannot work.
 *
 *  Notes:
 *    1  times length is nums.length
 *    2  return -1 is no meaning but need this line
 *
 *  @see <a href="https://leetcode.com/problems/find-the-duplicate-number/" > leetcoce</a>
 */
public class Leetcode287FindtheDuplicateNumber {
  public int findDuplicate(int[] nums) {
    int[] times = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
      int v = nums[i];
      if (times[v] != 0) {
        return v;
      }
      times[v]++;
    }
    return -1;
  }
}
