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

/** @see <a href="https://leetcode.com/articles/partition-to-k-equal-sum-subsets/">Leetcode</a> */
public class Leetcode698PartitiontoKEqualSumSubsets {
  // DP
  // Runtime  n*((2^n)-1)  =>  O(n*2^n)
  // space 2*n
  public static boolean canPartitionKSubsets(int[] nums, int k) {
    if (nums == null || nums.length == 0 || k < 1 || k > nums.length) return false;
    Arrays.sort(nums);
    int sum = Arrays.stream(nums).sum();
    int target = sum / k;
    if (sum % k > 0 || nums[nums.length - 1] > target) return false;

    boolean[] isValidPlacing = new boolean[1 << nums.length]; // valid meaning see the following
    isValidPlacing[0] = true;
    int[] total = new int[1 << nums.length];

    for (int placing = 0; placing < (1 << nums.length); placing++) {
      if (!isValidPlacing[placing]) continue;
      for (int i = 0; i < nums.length; i++) {
        int placingWithI = placing | (1 << i);
        if (placing != placingWithI && !isValidPlacing[placingWithI]) { // performance improve
          if (nums[i] <= target - (total[placing] % target)) { // this is the valid
            isValidPlacing[placingWithI] = true;
            total[placingWithI] = total[placing] + nums[i];
            //                        System.out.println(
            //                            String.format(
            //                                "%s <-%7s  + nums[%s] %s =>  %s <- %7s ",
            //                                total[placing],
            //                                Integer.toBinaryString(placing),
            //                                i,
            //                                nums[i],
            //                                total[placingWithI],
            //                                Integer.toBinaryString(placingWithI)));
            if (placingWithI == (1 << nums.length) - 1) {
              return true;
            }
          } else {
            break; // performance improve
          }
        }
      }
    }

    return isValidPlacing[(1 << nums.length) - 1];
  }
  //----------------------------------------------------------------
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
    nums =
        new int[] {
          5, 5, 5, 5, 5,
          5, 5, 5, 5, 5,
          5, 5, 5, 5, 5,
          5
        };
    k = 5;
    System.out.println(canPartitionKSubsets(nums, k) == true ? "Can  " : "No ");
  }
}
