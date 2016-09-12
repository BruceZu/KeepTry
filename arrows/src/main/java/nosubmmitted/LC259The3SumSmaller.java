//  Copyright 2016 The Sawdust Open Source Project
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

package nosubmmitted;

import java.util.Arrays;

/**
 * 259. 3Sum Smaller
 * https://leetcode.com/problems/3sum-smaller/
 * Difficulty: Medium <pre>
 * Given an array of n integers nums and a target,
 * find the number of index triplets i, j, k with 0 <= i < j < k < n
 * that satisfy the condition nums[i] + nums[j] + nums[k] < target.
 * <p/>
 * For example, given nums = [-2, 0, 1, 3], and target = 2.
 * <p/>
 * Return 2. Because there are two triplets which sums are less than 2:
 * <p/>
 * [-2, 0, 1]
 * [-2, 0, 3]
 * Follow up:
 * Could you solve it in O(n2) runtime?
 * <p/>
 * Hide Company Tags Google
 * Hide Tags: Array, Two Pointers
 * Hide Similar Problems: (M) 3Sum (M) 3Sum Closest
 */
public class LC259The3SumSmaller {
    /**
     * beat 74,87  or 92.46%  not stable
     */
    public int threeSumSmaller(int[] nums, int target) {
        if (nums.length < 3) {
            return 0;
        }
        int count = 0;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] * 3 >= target) {
                break;
            }
            count += find(nums, target - nums[i], i + 1, nums.length - 1);
        }
        return count;
    }

    //find number of pair that sum up smaller than target from given part of array
    public int find(int[] nums, int target, int start, int end) {
        int count = 0;
        while (start < end) {
            if (nums[start] + nums[end] >= target) {
                end--;
            } else {
                count += end - start;
                start++;
            }
        }
        return count;
    }

    /**
     * other idea, not the fast <pre>
     * We sort the array first. Then, for each element, we use the
     * two pointer approach to find the number of triplets that meet the requirements.
     * <p/>
     * Let me illustrate how the two pointer technique works with an example:
     * <p/>
     * target = 2
     * <p/>
     * i  lo    hi
     * [-2, 0, 1, 3]
     * We use a for loop (index i) to iterate through each element of the array.
     * For each i, we create two pointers, lo and hi, where lo is initialized as
     * the next element of i, and hi is initialized at the end of the array.
     * If we know that nums[i] + nums[lo] + nums[hi] < target,
     * then we know that since the array is sorted,
     * we can replace hi with any element from lo+1 to nums.length-1,
     * and the requirements will still be met.
     * Just like in the example above, we know that since -2 + 0 + 3 < 2,
     * we can replace hi (3) with 1, and it would still work.
     * Therefore, we can just add hi - lo to the triplet count.
     */
    public int threeSumSmaller2(int[] nums, int target) {
        int result = 0;
        Arrays.sort(nums);
        for (int i = 0; i <= nums.length - 3; i++) {
            int lo = i + 1;
            int hi = nums.length - 1;
            while (lo < hi) {
                if (nums[i] + nums[lo] + nums[hi] < target) {
                    result += hi - lo;
                    lo++;
                } else {
                    hi--;
                }
            }
        }
        return result;
    }
}
