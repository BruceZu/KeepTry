//  Copyright 2017 The KeepTry Open Source Project
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

package dp.partition;

import java.util.Arrays;

public class Leetcode698PartitiontoKEqualSumSubsets4 {
  /**
   *
   * Back tracking - one num by one num
   * no matter the k and n and sum of numbers
   *
   * <pre>
   * Space Complexity: O(n). n is the number of nums
   * Time Complexity: O(k^{n-k}* k!)
   *
   * for the first k numbers, because function will stop when sumOfK[i] is zero,
   * so there is at most i choices for nums[i] when i <= k. That is k!
   * for the left N-K numbers, each has K choices after no element of groups is zero.
   * That is  k^{N-k}
   */
  public static boolean placingNextNum(int[] sumOfK, int i, int[] nums, int ave) {
    if (i < 0) return true; // all number is placed in a valid way.
    int v = nums[i];
    for (int j = 0; j < sumOfK.length; j++) {
      if (sumOfK[j] + v <= ave) {
        sumOfK[j] += v;
        if (placingNextNum(sumOfK, i - 1, nums, ave)) return true;
        sumOfK[j] -= v;
      }
      if (sumOfK[j] == 0)
        break; // performance improve. If current sumOfK[j] is 0, then left sumOfK is also 0
    }
    return false;
  }

  public static boolean canPartitionKSubsets(int[] nums, int k) {
    if (nums == null || nums.length == 0 || k < 1 || k > nums.length) return false;
    int n = nums.length;
    int sum = Arrays.stream(nums).sum();
    if (sum % k > 0) return false;
    int ave = sum / k;

    Arrays.sort(nums);
    int i = n - 1;
    if (nums[i] > ave) return false; // performance improve

    int ready = 0;
    while (i >= 0 && nums[i] == ave) { // performance improve
      ready++;
      i--;
    }

    return placingNextNum(new int[k - ready], i, nums, ave);
  }
}
