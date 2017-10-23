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

// DP

/** @see <a href="https://leetcode.com/articles/partition-to-k-equal-sum-subsets/">Leetcode</a> */
public class Leetcode698PartitiontoKEqualSumSubsets2 {
  static boolean recursion(int placing, int leftSum, boolean[] isValid, int[] nums, int ave) {
    if (placing == ((1 << nums.length) - 1)) return true;

    for (int i = 0; i < nums.length; i++) {
      if ((((1 << i) | placing) != placing) && nums[i] <= ((leftSum - 1) % ave + 1)) {
        // System.out.println(String.format("try %6s", Integer.toBinaryString(placing | (1 << i))));
        if (recursion(placing | (1 << i), leftSum - nums[i], isValid, nums, ave)) {
          // System.out.println(String.format("works %6s", Integer.toBinaryString(placing | (1 << i))));
          return true;
          // isValid[placing] = true;
          //  break;
        }
        // else
        //   System.out.println(String.format("not works %6s", Integer.toBinaryString(placing | (1 << i))));
      }
    }
    // if (isValid[placing] == true)
    //  System.out.println(String.format("valid order %6s", Integer.toBinaryString(placing)));
    return false; //isValid[placing];
  }

  public static boolean canPartitionKSubsets(int[] nums, int k) {
    if (nums == null || nums.length == 0 || k < 1 || k > nums.length) return false;
    int sum = Arrays.stream(nums).sum();
    if (sum % k > 0) return false;

    boolean[] isValid = new boolean[1 << nums.length];
    isValid[(1 << nums.length) - 1] = true;
    // to see if the valid placing will via this placling : (1 << nums.length) - 1
    //System.out.println(String.format("default %6s is true", Integer.toBinaryString((1 << nums.length) - 1)));
    return recursion(0, sum, isValid, nums, sum / k);
  }

  //---------------------------------------------------
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
