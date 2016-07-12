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

package locked.nosubmmitted;

/**
 * 265. Paint House II
 * https://leetcode.com/problems/paint-house-ii/
 * <p/>
 * Difficulty: Hard
 * There are a row of n houses, each house can be painted with one of the k colors.
 * The cost of painting each house with a certain color is different.
 * You have to paint all the houses such that no two adjacent houses have the same color.
 * <p/>
 * The cost of painting each house with a certain color is represented
 * by a n x k cost matrix. For example, costs[0][0] is the cost of
 * painting house 0 with color 0; costs[1][2] is the cost of painting house
 * 1 with color 2, and so on... Find the minimum cost to paint all houses.
 * <p/>
 * Note:
 * All costs are positive integers.
 * <p/>
 * Follow up:
 * Could you solve it in O(nk) runtime?
 * <p/>
 * Hide Company Tags Facebook
 * Hide Tags: Dynamic Programming
 * Hide Similar Problems
 * (M) Product of Array Except Self
 * (H) Sliding Window Maximum
 * (M) Paint House
 * (E) Paint Fence
 */
public class LC265PaintHouseII {
    /**
     * fast one currently
     * beat 97.9%
     * This is a DP (Dynamic Programming) solution. It has a greedy method to calculate state transfer.
     */
    public int minCostII3(int[][] costs) {
        if (costs.length < 1) return 0;
        int n = costs.length, k = costs[0].length, min = 0, iMin = 0, min2nd = 0;
        for (int i = 0; i < n; i++) {
            int m1 = Integer.MAX_VALUE, m2 = m1, im1 = -1;
            for (int j = 0; j < k; j++) {
                int cost = costs[i][j] + (j == iMin ? min2nd : min);
                if (cost < m1) {
                    m2 = m1;
                    m1 = cost;
                    im1 = j;
                } else if (cost < m2)
                    m2 = cost;
            }
            min = m1;
            iMin = im1;
            min2nd = m2;
        }
        return min;
    }

    /**
     * beat 85%
     * <p/>
     * The idea is basically the same as other solutions.
     * The point of this one is that I use a minExclude function which returns
     * the min value excluding current position.
     */
    public static int minCostII(int[][] costs) {
        if (costs == null || costs.length < 1 || costs[0].length < 1) return 0;
        int n = costs.length;

        int[] minCosts = new int[costs[0].length];
        for (int i = 0; i < n - 1; i++) {
            arrayAdd(minCosts, costs[i]);
            minCosts = minExclude(minCosts);
        }
        arrayAdd(minCosts, costs[n - 1]);

        int min = minCosts[0];
        for (int c : minCosts) if (c < min) min = c;
        return min;
    }

    public static void arrayAdd(int[] arr1, int[] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] += arr2[i];
        }
    }

    // min excluding item i = min(0, i-1) + min(i+1,n)
    public static int[] minExclude(int[] nums) {
        int[] mins = new int[nums.length];
        mins[0] = Integer.MAX_VALUE;
        for (int i = 1; i < nums.length; i++) {
            mins[i] = Math.min(nums[i - 1], mins[i - 1]);
        }

        int minRight = Integer.MAX_VALUE;
        for (int i = nums.length - 2; i >= 0; i--) {
            minRight = Math.min(minRight, nums[i + 1]);
            mins[i] = Math.min(minRight, mins[i]);
        }

        return mins;
    }

    /**
     * save speed as above
     * <p/>
     * If there's no constraint, we choose min cost for each house
     * Since no same colors for adjacent houses, just select 2nd min cost color for i - 1
     * Current row only relies on last row to: (1) Get current min1, min2 (2) Avoid same color
     * So O(1) space is enough
     */
    public int minCostII2(int[][] costs) {
        if (costs == null || costs.length == 0) return 0;
        int m = costs.length, n = costs[0].length;
        int min1 = 0, min2 = 0, idMin1 = -1;

        for (int i = 0; i < m; i++) {
            int m1 = Integer.MAX_VALUE, m2 = m1, idm1 = -1;

            for (int j = 0; j < n; j++) {
                // If same color as j - 1, we can only extend from 2nd min of j - 1
                int cost = costs[i][j] + (j == idMin1 ? min2 : min1);

                // Update m1 m2 if cost is smaller than any of them
                if (cost < m1) {
                    m2 = m1;
                    m1 = cost;
                    idm1 = j;
                } else if (cost < m2) {
                    m2 = cost;
                }
            }
            min1 = m1;
            idMin1 = idm1;
            min2 = m2;
        }
        return min1;
    }

    /**
     * same speed as above
     * Explanation: dp[i][j] represents the min paint cost from house 0 to house i when house i use color j;
     * The formula will be dp[i][j] = Math.min(any k!= j| dp[i-1][k]) + costs[i][j].
     * <p/>
     * Take a closer look at the formula, we don't need an array to represent dp[i][j],
     * we only need to know the min cost to the previous house of any color and
     * if the color j is used on previous house to get prev min cost,
     * use the second min cost that are not using color j on the previous house.
     * So I have three variable to record: prevMin, prevMinColor, prevSecondMin.
     * and the above formula will be translated into:
     * <p/>
     * dp[currentHouse][currentColor] =
     * (currentColor == prevMinColor? prevSecondMin: prevMin) + costs[currentHouse][currentColor].
     */
    public class Solution {
        public int minCostII(int[][] costs) {
            if (costs == null || costs.length == 0 || costs[0].length == 0) return 0;

            int n = costs.length, k = costs[0].length;
            if (k == 1) return (n == 1 ? costs[0][0] : -1);

            int prevMin = 0, prevMinInd = -1, prevSecMin = 0;//prevSecMin always >= prevMin
            for (int i = 0; i < n; i++) {
                int min = Integer.MAX_VALUE, minInd = -1, secMin = Integer.MAX_VALUE;
                for (int j = 0; j < k; j++) {
                    int val = costs[i][j] + (j == prevMinInd ? prevSecMin : prevMin);
                    if (minInd < 0) { //   can be removed and still make the code working
                        min = val;
                        minInd = j;
                    }//when min isn't initialized
                    else if (val < min) {//when val < min,
                        secMin = min;
                        min = val;
                        minInd = j;
                    } else if (val < secMin) { //when min<=val< secMin
                        secMin = val;
                    }
                }
                prevMin = min;
                prevMinInd = minInd;
                prevSecMin = secMin;
            }
            return prevMin;
        }
        /**
         * other idea
         * The idea is similar to the problem Paint House I,
         * for each house and each color, the minimum cost of painting the
         * house with that color should be the minimum cost of painting
         * previous houses, and make sure the previous house doesn't
         * paint with the same color.
         * We can use min1 and min2 to track the indices of the 1st and
         * 2nd smallest cost till previous house, if the current color's
         * index is same as min1, then we have to go with min2, otherwise
         * we can safely go with min1.
         *
         * The code below modifies the value of costs[][] so we don't need extra space.
         *
         * ====
         * This problem can be solved using dynamic programming:
         *   let d[i][k] be the minimum cost of painting houses from 0 to i,
         *   assuming ith house is painted in color k. That means:
         *   1) d[0][j] = costs[0][j] for all j = 0 to k-1
         *   2) d[i][j] = ( min(d[i-1][c]), c = 0 to k-1, c!=j ) + costs[i][j].
         *   The result is min ( d[n-1][j] ), j = 0 to k-1.
         */
    }
}
