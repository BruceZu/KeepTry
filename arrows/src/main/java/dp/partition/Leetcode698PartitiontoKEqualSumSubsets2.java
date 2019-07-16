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
 * <pre>
 * @see <a href="https://leetcode.com/articles/partition-to-k-equal-sum-subsets/">Leetcode</a>
 *
 * DP:  Intuition version
 *
 * Topdown.
 *
 * no validate[] to mark.
 *
 */
public class Leetcode698PartitiontoKEqualSumSubsets2 {
    static boolean recursion(int r, int leftSum, int[] nums, int ave) {
        if (r == ((1 << nums.length) - 1)) return true;
        for (int i = 0; i < nums.length; i++) {
            int newr = (1 << i) | r;
            int remainder = (leftSum - 1) % ave + 1;
            if ((newr != r) && nums[i] <= remainder) {
                if (recursion(newr, leftSum - nums[i], nums, ave)) {
                    //                    System.out.println(
                    //                            String.format(
                    //                                    "r：%7s, sum:%3s, + nums[%s:%7s]:%s (remain
                    // %s)=> newr: %7s,leftsum:%3s",
                    //                                    Integer.toBinaryString(r),
                    //                                    leftSum,
                    //                                    i,
                    //                                    Integer.toBinaryString(1 << i),
                    //                                    nums[i],
                    //                                    remainder,
                    //                                    Integer.toBinaryString(newr),
                    //                                    leftSum - nums[i]));

                    return true;
                }
            }
        }
        return false;
    }

    public static boolean canPartitionKSubsets(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1 || k > nums.length) return false;
        Arrays.sort(nums);
        int sum = Arrays.stream(nums).sum();
        int ave = sum / k;
        if (sum % k > 0 || nums[nums.length - 1] > ave) return false;
        // System.out.println("array: " + Arrays.toString(nums) + ", k=" + k + ", ave=" + ave);
        return recursion(0, sum, nums, sum / k);
    }
}
