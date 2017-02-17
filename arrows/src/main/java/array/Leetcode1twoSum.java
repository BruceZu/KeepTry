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

package array;

/**
 * <pre>
 * 1. Two Sum
 * Difficulty: Easy
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 *
 * You may assume that each input would have <strong>exactly one</strong>solution.
 *
 * Example:
 * Given nums = [2, 7, 11, 15], target = 9,
 *
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 * The return format is zero-based indices.
 *
 * tags Array Hash Table
 * Similar Problems (M)
 *      3Sum (M)
 *      4Sum (M)
 *      Two Sum II - Input array is sorted (E)
 *      Two Sum III - Data structure design
 *   ===============================================================================================
 *     Idea: 1 HashTable {@link Leetcode1twoSum2 }  O(N)
 *           2 same like, using a array. assume the value is integer, value maybe <0.
 *           3 O(nlogn)  {@link Leetcode1twoSum3}
 *               sort (O(nlogn)). then using 2 pointer or binary search
 *               map to original index, (Cons: this is not always right. can not work for all pairs)
 *               [1,8,2,8,4,8], 16
 *               after sort:
 *               [1,2,4,8,8,8]
 *
 *    performance improvement
 *       -A: replace Integer[] with int[].
 *         this will require make sure map[nums[0]]!=0.
 *         bit manipulation is better than +/- operation according to leetcode test data.
 *       -B: sacrificing the 'other' and 'key' variables;
 *       -C: add 'target -= mi;' before the loop.
 *
 *      cons:  A and B will make the code less readable.
 *
 *   @see <a href="https://leetcode.com/problems/two-sum/">leetcode </a>
 *   @see <a href="https://discuss.leetcode.com/topic/50160/does-anybody-know-the-fastest-java-solution-2ms/6">discuss </a>
 */
public class Leetcode1twoSum {
    // runtime O(N), space depends on value of min, max and target
    // wrong solutions
    public static int[] twoSum(int[] nums, int target) {
        int max = nums[0], min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > max) {
                max = nums[i];
            }
            if (nums[i] < min) {
                min = nums[i];
            }
        }

        int mi = Math.min(min, target - max);
        int ma = Math.max(max, target - min);

        Integer[] map = new Integer[ma - mi + 1];
        target = target - mi;
        for (int i = 0; i < nums.length; i++) {
            int key = target - nums[i];
            if (map[key] != null) {
                // assume that each input would have <strong>exactly one</strong>solution.
                // assume the array is not sorted
                return new int[]{map[key], i};
            } else {
                map[nums[i] - mi] = i;
            }
        }
        return new int[]{-1, -1}; // -1 as not found, for test
    }
}
