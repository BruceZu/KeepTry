//  Copyright 2019 The KeepTry Open Source Project
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

package dp;

/**
 * <pre>
 * Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on it represented by array nums. You are asked to burst all the balloons. If the you burst balloon i you will get nums[left] * nums[i] * nums[right] coins. Here left and right are adjacent indices of i. After the burst, the left and right then becomes adjacent.
 *
 * Find the maximum coins you can collect by bursting the balloons wisely.
 *
 * Note:
 *
 * You may imagine nums[-1] = nums[n] = 1. They are not real therefore you can not burst them.
 * 0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100
 * Example:
 *
 * Input: [3,1,5,8]
 * Output: 167
 * Explanation: nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
 * coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
 * <a href="https://leetcode.com/problems/burst-balloons/">leetcode 312</a>
 */
public class Leetcode312BurstBalloons {

    // DP bottom up runtime O(?)
    public int maxCoins2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int[] A = new int[nums.length + 2];
        A[0] = 1;
        A[nums.length + 1] = 1;
        System.arraycopy(nums, 0, A, 1, nums.length);

        int[][] DP = new int[nums.length + 2][nums.length + 2];

        for (int len = 1; len <= nums.length; len++) {
            for (int left = 1; left <= nums.length + 1 - len; left++) {
                int right = left + len - 1;
                for (int b /*lastBurst*/ = left; b <= right; b++) {
                    DP[left][right] =
                            Math.max(
                                    DP[left][right],
                                    A[left - 1] * A[b] * A[right + 1]
                                            + DP[left][b - 1]
                                            + DP[b + 1][right]);
                }
            }
        }

        return DP[1][nums.length];
    }

    // DP top down
    public static int maxCoins(int[] nums) {

        if (nums == null || nums.length == 0) return 0;

        int[] A = new int[nums.length + 2];
        A[0] = 1;
        A[nums.length + 1] = 1;
        System.arraycopy(nums, 0, A, 1, nums.length);

        int[][] cache = new int[nums.length + 2][nums.length + 2];

        return maxValue(1, nums.length, A, cache);
    }

    private static int maxValue(int left, int right, int[] A, int[][] cache) {

        if (!(left <= right)) return 0;
        if (cache[left][right] != 0) return cache[left][right];

        for (int b /*lastBurst*/ = left; b <= right; b++) {

            cache[left][right] =
                    Math.max(
                            cache[left][right],
                            A[left - 1] * A[b] * A[right + 1]
                                    + maxValue(left, b - 1, A, cache)
                                    + maxValue(b + 1, right, A, cache));
        }
        return cache[left][right];
    }

    public static void main(String[] args) {
        System.out.println(maxCoins(new int[] {3, 1, 5, 8}));
    }
}
