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

public class Leetcode698PartitiontoKEqualSumSubsets7
{

  // Time complexity:0(k^n), n is nums length
  // Space complexity: (n)
  public static boolean separate(int[] nums, int k) {
    if (nums == null || nums.length == 0 || k < 1 || k > nums.length) return false;

    if (k == 1) return true;

    int sum = Arrays.stream(nums).sum();
    if (sum % k != 0) return false;

    int[] kLeft = new int[k];
    Arrays.fill(kLeft, sum / k);
    return recursion(nums, 0, kLeft);
  }

  static boolean recursion(int nums[], int i, int kLeft[]) {
    if (i == nums.length) {
      return !Arrays.stream(kLeft).anyMatch((e) -> e != 0);
    }

    for (int j = 0; j < kLeft.length; j++) {
      if (kLeft[j] - nums[i] >= 0) {
        kLeft[j] -= nums[i];
        if (recursion(nums, i + 1, kLeft)) return true;
        kLeft[j] += nums[i];
      }
    }
    return false;
  }
  //---------------------------------------------------
  public static void main(String[] args) {
    int[] nums = new int[] {1, 2, 3, 4, 5, 6};
    int k = 3;
    System.out.println(" -- okay -- ");
    System.out.println(separate(nums, k) == true ? "Can  " : "No ");
    nums = new int[] {4, 2, 2, 3, 6, 6, 1};
    k = 4;
    System.out.println(separate(nums, k) == true ? "Can  " : "No ");
    nums = new int[] {2, 1, 2, 2, 1, 1};
    k = 3;
    System.out.println(separate(nums, k) == true ? "Can  " : "No ");
    nums = new int[] {2, 2, 2, 2, 2, 2};
    k = 3;
    System.out.println(separate(nums, k) == true ? "Can  " : "No ");
    nums = new int[] {3, 1, 1, 2, 1, 1};
    k = 3;
    System.out.println(separate(nums, k) == true ? "Can  " : "No ");
    nums = new int[] {2, 2, 2, 1, 1, 1};
    k = 3;
    System.out.println(separate(nums, k) == true ? "Can  " : "No ");

    System.out.println(" -- false -- ");
    nums = new int[] {3, 1, 1, 2, 1, 1};
    k = 7;
    System.out.println(separate(nums, k) == true ? "Can  " : "No ");
    nums = new int[] {3, 1, 1, 2, 1, 1};
    k = 0;
    System.out.println(separate(nums, k) == true ? "Can  " : "No ");
    nums = null;
    k = 2;
    System.out.println(separate(nums, k) == true ? "Can  " : "No ");
    nums = new int[] {3, 1, 1, 2, 1, 1};
    k = 2;
    System.out.println(separate(nums, k) == true ? "Can  " : "No ");
    nums =
        new int[] {
          5, 5, 5, 5, 5,
          5, 5, 5, 5, 5,
          5, 5, 5, 5, 5,
          5
        };
    k = 5;
    System.out.println(separate(nums, k) == true ? "Can  " : "No ");
  }
}
