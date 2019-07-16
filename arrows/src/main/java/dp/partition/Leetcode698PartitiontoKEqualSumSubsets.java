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
    /** <pre>
     * DP: Bottom-Up version
     * cons: only works when
     * - the numbers.length <= 31
     * - sum <= 2^63-1
     *
     * 0 <= i <= (2 ^ nums.length ) - 1;
     * r is the representation of i as an unsigned integer in base 2.
     * r is taken as a combination of elements in array by the mapping relation:
     * r is the result of  1 << each selected element index
     * ave = sum/k; if (r mapped combination 's sum) % ave <= ave then isValid[r] = true
     *
     * Runtime complexity:  n*((2^n)-1)  =>  O(n*2^n)
     * n is limited to be <= 31
     *
     * space complexity:  O(2^n)
     * follow up: what if we have n > 64 numbers?
     */
    public static boolean canPartitionKSubsets(int[] nums, int k) {
        //        System.out.println("array: " + Arrays.toString(nums) + ", k=" + k);
        if (nums == null || nums.length == 0 || k < 1 || k > nums.length) return false;
        int n = nums.length;
        Arrays.sort(nums);
        int sum = Arrays.stream(nums).sum();
        int ave = sum / k;
        if (sum % k > 0 || nums[n - 1] > ave) return false;
        int[] sumOf = new int[1 << n];

        boolean[] isValid = new boolean[1 << n];
        isValid[0] = true;
        //        System.out.println("sum <-      r  + A[i]      => sumOf[newr] <- newr");
        for (int r = 0; r < (1 << n); r++) {
            if (!isValid[r]) continue;
            for (int i = 0; i < n; i++) {
                int newr = r | (1 << i);
                if (newr != r && !isValid[newr]) { // performance improve
                    if (nums[i] + (sumOf[r] % ave) <= ave) {
                        isValid[newr] = true;
                        sumOf[newr] = sumOf[r] + nums[i];
                        //                        System.out.println(
                        //                                String.format(
                        //                                        "r：%7s,sum:%3s,   +
                        // nums[%s:%7s]:%s => newr: %7s,sum:%3s,  ",
                        //                                        Integer.toBinaryString(r),
                        //                                        sumOf[r],
                        //                                        i,
                        //                                        Integer.toBinaryString(1 << i),
                        //                                        nums[i],
                        //                                        Integer.toBinaryString(newr),
                        //                                        sumOf[newr]));

                        if (newr == (1 << n) - 1) {
                            return true;
                        }
                    } else {
                        break; // performance improve
                    }
                }
            }
        }

        return isValid[(1 << n) - 1];
    }
}
