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

package sort;

import java.util.*;

public class Leetcode414ThirdMaximumNumber {

  /*
   414. Third Maximum Number
    Given an integer array nums,
    return the third distinct maximum number in this array.
    If the third maximum does not exist, return the maximum number.

   Input: nums = [3,2,1]
   Output: 1
   Explanation:
   The first distinct maximum is 3.
   The second distinct maximum is 2.
   The third distinct maximum is 1.


   Input: nums = [1,2]
   Output: 2
   Explanation:
   The first distinct maximum is 2.
   The second distinct maximum is 1.
   The third distinct maximum does not exist, so the maximum (2) is returned instead.


   Input: nums = [2,2,3,1]
   Output: 1
   Explanation:
   The first distinct maximum is 3.
   The second distinct maximum is 2 (both 2's are counted together since they have the same value).
   The third distinct maximum is 1.


   Constraints:

   1 <= nums.length <= 104
   -2^31 <= nums[i] <= 2^31 - 1


   Follow up:
   Can you find an O(n) solution?
  */

  /*
  Collections.min()
  Time: O(n)
  Space:O(1).
   */
  public int thirdMax(int[] nums) {
    Set<Integer> uniqTop3 = new HashSet<Integer>();
    for (int n : nums) {
      uniqTop3.add(n);
      if (uniqTop3.size() > 3) uniqTop3.remove(Collections.min(uniqTop3));
    }
    if (uniqTop3.size() == 3) return Collections.min(uniqTop3);
    return Collections.max(uniqTop3);
  }

  public int thirdMax_(int[] nums) {
    Integer m1 = null;
    Integer m2 = null;
    Integer m3 = null;
    for (Integer n : nums) {
      if (n.equals(m1) || n.equals(m2) || n.equals(m3)) continue;
      if (m1 == null || n > m1) {
        m3 = m2;
        m2 = m1;
        m1 = n;
      } else if (m2 == null || n > m2) {
        m3 = m2;
        m2 = n;
      } else if (m3 == null || n > m3) {
        m3 = n;
      }
    }
    return m3 == null ? m1 : m3;
  }
  /*
  Follow up:

  if
  - take into account the duplicated number?
  -  v is no-negative integer?
  return top 3
     initial 3 variable as -1;
  */
  public static void top3(int[] nums) {
    if (nums == null || nums.length == 0){
      System.out.println("null");
      return;
    }

    int[] m = new int[3];
    Arrays.fill(m, -1);
    for (int n : nums) {
      for (int i = 0; i < 3; i++) {
        if (n >= m[i]) {
          int t = m[i];
          m[i] = n;
          n = t;
        }
      }
    }
    System.out.println(
        String.format("%s, %s. %s", m[0], m[1] == -1 ? "null" : m[1], m[2] == -1 ? "null" : m[2]));
  }

  public static void main(String[] args) {
    top3(null);
    top3(new int[] {1});
    top3(new int[] {1, 2});
    top3(new int[] {1, 2, 3});
    top3(new int[] {1, 2, 2, 133});
    top3(new int[] {1, 32, 22, 133});
  }
}
