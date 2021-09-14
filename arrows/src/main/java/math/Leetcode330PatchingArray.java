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

package math;

public class Leetcode330PatchingArray {
  /*
        330. Patching Array
    Given a sorted integer array nums and an integer n,
    add/patch elements to the array such that
    any number in the range [1, n] inclusive can be formed
    by the sum of some elements in the array.

    Return the minimum number of patches required.


    Input: nums = [1,3], n = 6
    Output: 1
    Explanation:
    Combinations of nums are [1], [3], [1,3], which form possible sums of: 1, 3, 4.
    Now if we add/patch 2 to nums, the combinations are: [1], [2], [3], [1,3], [2,3], [1,2,3].
    Possible sums are 1, 2, 3, 4, 5, 6, which now covers the range [1, 6].
    So we only need 1 patch.


    Input: nums = [1,5,10], n = 20
    Output: 2
    Explanation: The two patches can be [2, 4].


    Input: nums = [1,2,2], n = 5
    Output: 0

    Constraints:

        1 <= nums.length <= 1000
        1 <= nums[i] <= 104
        nums is sorted in ascending order.
        1 <= n <= 2^31 - 1
  */
  /*
    Idea:
    Watch the case [1,5,10], n = 20
    let r as the right side number value of continuous sum range [0, r]
    initial r=0; and expected next number nums[i] should be <= r+1, else need a patch = r+1;
    if  nums[i]==r+1 then r will be r+(r+1)
    if  nums[i]< r+1 then r will be r+nums[i];
    i=0 numb[0] is 1, so need not patch and r=r+1= 1;
    i=1,numb[1] is 5, not expected <=2
         so need a patch =2;
         with the patch, now r=r+2=3 and expected <=4, but numb[1] is 5
         so need a patch =4;
         with the patch, now r=r+4=7 and expected <=8,   numb[1] is 5 works now
         so now r=r+5=12,  expected <=13,  move i to next
     i=2,numb[2] is 10, need not patch
         r=r+10=22> target 20 then break the loop.

    Note ' 1 <= n <= 2^31 - 1'
    so the `r` of continuous sum range [0, r] should be a long type

    Observe
      - we must patch the expected number else continuous sum range [0, r] can not continue


   O(m+logN) time, M is the length of nums. N is the give number n;
   O(1) space
  */
  public static int minPatches(int[] nums, int n) {
    int patches = 0, N = nums.length;
    long r = 0; // need use long to avoid overflow
    int i = 0;
    while (r < n) {
      if (i < N && nums[i] > r + 1 || i == N) {
        patches++; // patch = r+1
        r = 2 * r + 1;
      } else {
        r = r + nums[i];
        i++;
      }
    }
    return patches;
  }

  // same idea:
  public static int minPatches_(int[] nums, int n) {
    int patches = 0, i = 0;
    long expected = 1; // [0, r], expectedNext = r+1
    while (expected <= n) {
      if (i < nums.length && nums[i] <= expected) expected += nums[i++];
      else {
        expected <<= 1;
        patches++; // increase the answer
      }
    }
    return patches;
  }
}
