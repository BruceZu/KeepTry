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

/**
 * @see <a href="https://leetcode.com/articles/partition-to-k-equal-sum-subsets/">Leetcode</a>
 *     <pre>
 *         1 <= k <= len(nums) <= 16.
 *         0 < nums[i] < 10000.
 */
public class Leetcode698PartitiontoKEqualSumSubsets {
  //
  // DP: Bottom-Up version
  // cons: only works when the numbers.length <=64 and sum <=2^64-1
  //
  // Runtime complexity:  n*((2^n)-1)  =>  O(n*2^n)   n is limited. 2^n to be < Long.MAX_VALUE
  //                                      what if we have n > 64 numbers?
  // space complexity:  O(2^n)
  public static boolean canPartitionKSubsets(int[] nums, int k) {
    if (nums == null || nums.length == 0 || k < 1 || k > nums.length) return false;
    int n = nums.length;
    Arrays.sort(nums);
    int sum = Arrays.stream(nums).sum();
    int ave = sum / k;
    if (sum % k > 0 || nums[n - 1] > ave) return false;

    int[] total = new int[1 << n];
    boolean[] valided = new boolean[1 << n]; // valided meaning：see the following
    valided[0] = true;

    for (int placing = 0; placing < (1 << n); placing++) {
      if (!valided[placing]) continue;
      for (int i = 0; i < n; i++) {
        int newPlacing = placing | (1 << i);
        if (placing != newPlacing && !valided[newPlacing]) { // performance improve
          if (nums[i] + (total[placing] % ave) <= ave) { // this is the meaning of valided
            valided[newPlacing] = true;
            total[newPlacing] = total[placing] + nums[i];
            //                        System.out.println(
            //                            String.format(
            //                                "%s <-%7s  + nums[%s] %s =>  %s <- %7s ",
            //                                total[placing],
            //                                Integer.toBinaryString(placing),
            //                                i,
            //                                nums[i],
            //                                total[placingWithI],
            //                                Integer.toBinaryString(placingWithI)));
            if (newPlacing == (1 << n) - 1) {
              return true;
            }
          } else {
            break; // performance improve
          }
        }
      }
    }

    return valided[(1 << n) - 1];
  }
}
