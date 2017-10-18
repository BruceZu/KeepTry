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

package numberPartitioning;

import java.util.Arrays;

public class Leetcode698PartitiontoKEqualSumSubsets4 {
  // Space Complexity: O(N)
  // Time Complexity: O(k^{N-k}* k!) ????
  //  As we skip additional zeroes in groups,
  //  naively we will make O(k!) calls to placingNextNum, ????
  //  then an additional O(k^{N-k}) calls after every element of groups is nonzero.

  public static boolean placingNextNum(int[] k, int row, int[] nums, int ave) {
    if (row < 0) return true;
    int v = nums[row];
    for (int i = 0; i < k.length; i++) {
      if (k[i] + v <= ave) {
        k[i] += v;
        if (placingNextNum(k, row - 1, nums, ave)) return true;
        k[i] -= v;
      }
      if (k[i] == 0) break; // performance improve
    }
    return false;
  }

  public static boolean canPartitionKSubsets(int[] nums, int k) {
    if (nums == null || nums.length == 0 || k < 1 || k > nums.length) return false;
    int sum = Arrays.stream(nums).sum();
    if (sum % k > 0) return false;
    int ave = sum / k;

    Arrays.sort(nums);
    int row = nums.length - 1;
    if (nums[row] > ave) return false; // performance improve

    int ready = 0;
    while (row >= 0 && nums[row] == ave) { // performance improve
      ready++;
      row--;
    }

    return placingNextNum(new int[k - ready], row, nums, ave);
  }

  public static void main(String[] args) {

    int[] nums = new int[] {1, 2, 3, 4, 5, 6};
    int k = 3;
    System.out.println(" -- okay -- ");
    System.out.println(canPartitionKSubsets(nums, k) == true ? "Can  " : "No ");
    nums = new int[] {4, 2, 2, 3, 6, 6, 1};
    k = 4;
    System.out.println(canPartitionKSubsets(nums, k) == true ? "Can  " : "No ");
    nums = new int[] {2, 1, 2, 2, 1, 1};
    k = 3;
    System.out.println(canPartitionKSubsets(nums, k) == true ? "Can  " : "No ");
    nums = new int[] {2, 2, 2, 2, 2, 2};
    k = 3;
    System.out.println(canPartitionKSubsets(nums, k) == true ? "Can  " : "No ");
    nums = new int[] {3, 1, 1, 2, 1, 1};
    k = 3;
    System.out.println(canPartitionKSubsets(nums, k) == true ? "Can  " : "No ");
    nums = new int[] {2, 2, 2, 1, 1, 1};
    k = 3;
    System.out.println(canPartitionKSubsets(nums, k) == true ? "Can  " : "No ");

    System.out.println(" -- false -- ");
    nums = new int[] {3, 1, 1, 2, 1, 1};
    k = 7;
    System.out.println(canPartitionKSubsets(nums, k) == true ? "Can  " : "No ");
    nums = new int[] {3, 1, 1, 2, 1, 1};
    k = 0;
    System.out.println(canPartitionKSubsets(nums, k) == true ? "Can  " : "No ");
    nums = null;
    k = 2;
    System.out.println(canPartitionKSubsets(nums, k) == true ? "Can  " : "No ");
    nums = new int[] {3, 1, 1, 2, 1, 1};
    k = 2;
    System.out.println(canPartitionKSubsets(nums, k) == true ? "Can  " : "No ");
    nums = new int[] {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
    k = 5;
    System.out.println(canPartitionKSubsets(nums, k) == true ? "Can  " : "No ");
  }
}
