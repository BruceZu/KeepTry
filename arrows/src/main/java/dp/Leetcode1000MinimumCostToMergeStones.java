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
 *
 * There are N piles of stones arranged in a row.  The i-th pile has stones[i] stones.
 *
 * A move consists of merging exactly K consecutive piles into one pile, and the cost
 * of this move is equal to the total number of stones in these K piles.
 *
 * Find the minimum cost to merge all piles of stones into one pile.
 * If it is impossible, return -1.
 *
 * Example 1:
 *
 * Input: stones = [3,2,4,1], K = 2
 * Output: 20
 * Explanation:
 * We start with [3, 2, 4, 1].
 * We merge [3, 2] for a cost of 5, and we are left with [5, 4, 1].
 * We merge [4, 1] for a cost of 5, and we are left with [5, 5].
 * We merge [5, 5] for a cost of 10, and we are left with [10].
 * The total cost was 20, and this is the minimum possible.
 *
 * Example 2:
 *
 * Input: stones = [3,2,4,1], K = 3
 * Output: -1
 * Explanation: After any merge operation, there are 2 piles left,
 * and we can't merge anymore.  So the task is impossible.
 *
 * Example 3:
 *
 * Input: stones = [3,5,1,2,6], K = 3
 * Output: 25
 * Explanation:
 * We start with [3, 5, 1, 2, 6].
 * We merge [5, 1, 2] for a cost of 8, and we are left with [3, 8, 6].
 * We merge [3, 8, 6] for a cost of 17, and we are left with [17].
 * The total cost was 25, and this is the minimum possible.
 *
 *
 * Note:
 *
 * 1 <= stones.length <= 30
 * 2 <= K <= 30
 * 1 <= stones[i] <= 100
 *
 * <a href="https://leetcode.com/problems/minimum-cost-to-merge-stones/">leetcode</a>
 */
public class Leetcode1000MinimumCostToMergeStones {

    // bottom up O(N^2/K)
    public static int mergeStones2(int[] stones, int K) {
        if (stones == null || K < 1) return -1;
        if (K == 1 || K > stones.length) return 0;

        if ((stones.length - 1) % (K - 1) != 0) return -1;
        int l = stones.length;

        int[][] dp = new int[l][l];
        int[] preSum = new int[l];

        for (int i = 0; i < l; i++) preSum[i] = ((i == 0 ? 0 : preSum[i - 1]) + stones[i]);

        for (int len = K; len <= l; len++) {
            for (int i = 0; i + len <= l; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;
                for (int cr /*first child's range's right end index*/ = i; cr < j; cr += K - 1) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][cr] + dp[cr + 1][j]);
                }
                if ((len - 1) % (K - 1) == 0) dp[i][j] += preSum[j] - (i == 0 ? 0 : preSum[i - 1]);
                // System.out.println("dp[" + i + "][" + j + "]=" + dp[i][j]);
            }
        }
        return dp[0][l - 1];
    }

    // Top down
    public static int mergeStones(int[] stones, int K) {
        if (stones == null || K < 1) return -1;
        if (K == 1 || K > stones.length) return 0;

        if ((stones.length - 1) % (K - 1) != 0) return -1;
        int l = stones.length;

        int[][] dp = new int[l][l];
        int[] preSum = new int[l];

        for (int i = 0; i < l; i++) preSum[i] = ((i == 0 ? 0 : preSum[i - 1]) + stones[i]);

        return minCostToMerge(0, stones.length - 1, K, preSum, dp);
    }

    /**
     * <pre>
     * @param i left index of range [i, j] of node in k-nary tree
     * @param j right index of range [i, j] of node in k-nary tree
     *
     * In the  k-ary or k-way tree each node has no more that K children
     * see <a href="https://en.wikipedia.org/wiki/M-ary_tree">k-ary tree</a>
     */
    private static int minCostToMerge(int i, int j, int K, int[] preSum, int[][] dp) {
        if (!(i <= j)) return 0;
        int length = j - i + 1;
        if (length < K) return 0;
        if (length == K) return preSum[j] - (i == 0 ? 0 : preSum[i - 1]);

        if (dp[i][j] != 0) return dp[i][j]; // cached
        dp[i][j] = Integer.MAX_VALUE;
        for (int c = i; c < j; c += K - 1) {
            // cut after the right end index c of the first child node range to split current node
            // range into 2 parts: the first node range and other k-1 nodes's range

            dp[i][j] =
                    Math.min(
                            dp[i][j],
                            minCostToMerge(i, c, K, preSum, dp)
                                    + minCostToMerge(c + 1, j, K, preSum, dp));
        }
        if ((length - 1) % (K - 1) == 0) dp[i][j] += preSum[j] - (i == 0 ? 0 : preSum[i - 1]);
        // System.out.println("dp[" + i + "][" + j + "]=" + dp[i][j]);
        return dp[i][j];
    }

    public static void main(String[] args) {
        System.out.println(0 == mergeStones(new int[] {1}, 2));
        System.out.println(20 == mergeStones(new int[] {3, 2, 4, 1}, 2));
        System.out.println(0 == mergeStones2(new int[] {1}, 2));
        System.out.println(20 == mergeStones2(new int[] {3, 2, 4, 1}, 2));
        System.out.println(36 == mergeStones2(new int[] {3, 2, 4, 1, 3, 3, 4}, 3));
        System.out.println(36 == mergeStones(new int[] {3, 2, 4, 1, 3, 3, 4}, 3));
    }
}
