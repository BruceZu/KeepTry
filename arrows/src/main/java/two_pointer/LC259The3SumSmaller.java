//  Copyright 2017 The keepTry Open Source Project
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

package two_pointer;

import java.util.Arrays;

/**
 * <pre> <a href="https://leetcode.com/problems/3sum-smaller/">LeetCode</a>
 * 259. 3Sum Smaller
 * https://leetcode.com/problems/3sum-smaller/
 * Difficulty: Medium <pre>
 * Given an array of n integers nums and a target,
 * find the number of index triplets i, j, k
 * with 0 <= i < j < k < n that satisfy the condition nums[i] + nums[j] + nums[k] < target.
 *
 * For example, given nums = [-2, 0, 1, 3], and target = 2.
 *
 * Return 2. Because there are two triplets which sums are less than 2:
 *
 * [-2, 0, 1]
 * [-2, 0, 3]
 * Follow up:
 * Could you solve it in O(n2) runtime?
 *
 * Hide Company Tags Google
 * Hide Tags: Array, Two Pointers
 * Hide Similar Problems: (M) 3Sum (M) 3Sum Closest
 */
public class LC259The3SumSmaller {

    // As it is the number of index triplets i, j, k
    // with 0 <= i < j < k < n that satisfy the condition nums[i] + nums[j] + nums[k] < target.
    // ==> do not care about duplicate cases
    public int threeSumSmaller2(int[] nums, int target) {
        int result = 0;
        Arrays.sort(nums);
        for (int i = 0; i <= nums.length - 3; i++) {
            if (nums[i] * 3 >= target) break; // improvement
            int l = i + 1;
            int r = nums.length - 1;
            while (l < r) {
                if (nums[i] + nums[l] + nums[r] < target) {
                    result += r - l;
                    l++;
                } else {
                    r--;
                }
            }
        }
        return result;
    }
}
