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
     * no matter the array length and and sum.
     *
     * <pre>
     * Space Complexity: O(n). n is the number of nums
     *
     * Time Complexity: O(k^{n-k}* k!)
     * for the first k numbers: k!
     * for the left n-k numbers: each has k choices, so k^{n-k}
     */
    public static boolean placingNextNum(int[] jarSum, int i, int[] nums, int ave) {
        if (i == -1) return true; // all number is placed in a valid way.
        for (int j = 0; j < jarSum.length; j++) {
            if (jarSum[j] + nums[i] <= ave) {
                jarSum[j] += nums[i];
                if (placingNextNum(jarSum, i - 1, nums, ave)) return true;
                jarSum[j] -= nums[i];
            }
            if (jarSum[j] == 0)
                return false; // performance improve. If current sumOfK[j] is 0, then left sumOfK is
            // also 0, need not try the left jar. this make the runtime of the first k numbers: k!
            //                    0 0 0 0 ... (k jars)
            // the first try only 1 0 0 0 ...
            // the second try the 1 1 0 0 ...
            // the third  try the 1 1 1 0 ...
        }
        return false;
    }

    public static boolean canPartitionKSubsets(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1 || k > nums.length) return false;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % k != 0) return false;

        int ave = sum / k;
        Arrays.sort(nums);
        if (nums[nums.length - 1] > ave) return false;

        // after sorted the array
        int ready = 0;
        int i = nums.length - 1;
        while (i >= 0 && nums[i] == ave) { // performance improve
            ready++;
            i--;
        }

        return placingNextNum(new int[k - ready], i, nums, ave);
    }

    // =============================================================================
    // Time Complexity: O(?)
    public static boolean canPartitionKSubsets2(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1 || k > nums.length) return false;

        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % k != 0) return false;
        int ave = sum / k;
        Arrays.sort(nums);
        if (nums[nums.length - 1] > ave) return false;

        // after sorted the array
        int ready = 0;
        int i = nums.length - 1;
        while (i >= 0 && nums[i] == ave) { // performance improve
            ready++;
            i--;
        }
        k = k - ready;
        System.out.println(Arrays.toString(nums) + ",k=" + k + ",ave=" + ave);
        return backTracking(nums, 0, 0, k, ave, new boolean[nums.length]);
    }

    // combination for each ave one by one.
    public static boolean backTracking(
            int[] nums, int selectedSum, int from, int leftJarNum, int ave, boolean[] used) {
        if (selectedSum == ave) {
            System.out.println("got one");
            if (leftJarNum - 1 == 0) return true;
            return backTracking(nums, 0, 0, leftJarNum - 1, ave, used);
        }

        for (int j = from; j < nums.length; j++) {
            if (!used[j] && selectedSum + nums[j] <= ave) {
                used[j] = !used[j];
                if (backTracking(nums, selectedSum + nums[j], j + 1, leftJarNum, ave, used)) {
                    System.out.println(
                            String.format("num[%s]=%s,get:%s", j, nums[j], selectedSum + nums[j]));
                    return true;
                }
                used[j] = !used[j];
            }
        }

        return false;
    }
}
