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

public class Partition2 {
  // Time complexity:O() n is nums.length ???
  // Space complexity:O(k)
  public static boolean separate(int[] nums, int k) {
    if (nums == null || nums.length == 0 || k < 1 || k > nums.length) return false;
    if (k == 1) return true;
    if (nums.length < k) return false;
    int sum = Arrays.stream(nums).sum();
    if (sum % k != 0) return false;

    boolean inOtherBucket[] = new boolean[nums.length];
    int buckets[] = new int[k];
    buckets[0] = nums[nums.length - 1];
    inOtherBucket[nums.length - 1] = true;

    return recursion(nums, buckets, inOtherBucket, sum / k, k, 0, 0);
  }

  /** @param k bucket */
  public static boolean recursion(
      int[] nums, int kSum[], boolean used[], int ave, int k, int ki, int nFrom) {
    if (kSum[ki] == ave) {
      if (ki == k - 2) return true; // includes checking if there are nums left.

      return recursion(nums, kSum, used, ave, k, ki + 1, 0);
    }

    // current bucket, next num
    for (int i = nFrom; i < nums.length; i++) {
      if (used[i]) continue;
      if (kSum[ki] + nums[i] <= ave) {
        used[i] = true;
        kSum[ki] += nums[i];

        if (recursion(nums, kSum, used, ave, k, ki, i + 1)) return true;

        used[i] = false;
        kSum[ki] -= nums[i];
      }
    }
    return false; // try all num and failed for current bucket
  }
  //------------------
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
  }
}
