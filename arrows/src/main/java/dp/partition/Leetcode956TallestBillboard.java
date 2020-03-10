//  Copyright 2020 The KeepTry Open Source Project
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

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * You are installing a billboard and want it to have the largest height.  The billboard will have two steel supports, one on each side.  Each steel support must be an equal height.
 * <p>
 * You have a collection of rods which can be welded together.  For example, if you have rods of lengths 1, 2, and 3, you can weld them together to make a support of length 6.
 * <p>
 * Return the largest possible height of your billboard installation.  If you cannot support the billboard, return 0.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: [1,2,3,6]
 * Output: 6
 * Explanation: We have two disjoint subsets {1,2,3} and {6}, which have the same sum = 6.
 * Example 2:
 * <p>
 * Input: [1,2,3,4,5,6]
 * Output: 10
 * Explanation: We have two disjoint subsets {2,3,5} and {4,6}, which have the same sum = 10.
 * Example 3:
 * <p>
 * Input: [1,2]
 * Output: 0
 * Explanation: The billboard cannot be supported, so we return 0.
 * <p>
 * <p>
 * Note:
 * <p>
 * 0 <= rods.length <= 20
 * 1 <= rods[i] <= 1000
 * The sum of rods is at most 5000
 */
//DP: bottom up 1 D
public class Leetcode956TallestBillboard {
    public static int tallestBillboard(int[] rods) {
        int[] dp = new int[5001];
        //dp[x]: max common high of all possible poles composed with the diff of x of 2 poles
        for (int j = 1; j <= 5000; j++) {
            dp[j] = -1;
        }
        // System.out.println(Arrays.toString(dp));
        for (int v : rods) {
            int[] clone = dp.clone();
            for (int j = 0; j + v <= 5000; j++) {
                if (clone[j] != -1) {
                    int next = j + v;
                    dp[next] = Math.max(dp[next], clone[j]);
                    next = Math.abs(j - v);
                    dp[next] = Math.max(dp[next], clone[j] + Math.min(j, v));
                }
            }
            // System.out.println(Arrays.toString(dp));
        }
        return dp[0];
    }

    public static void main(String[] args) {
        int[] input = new int[]{1, 2, 3, 6};
        int[] input2 = new int[]{61, 45, 43, 54, 40, 53, 55, 47, 51, 59, 42};
        System.out.println(tallestBillboard(input) == 6);
        System.out.println(tallestBillboard(input2) == 275);
    }


    //DP:  bottom up 2D
    public static int tallestBillboard2(int[] rods) {
        int maxDiff = Arrays.stream(rods).sum();
        int[][] dp = new int[rods.length + 1][maxDiff + 1]; // 1-based index of rods
        for (int[] row : dp) {
            Arrays.fill(row, -1); // not calculated
        }
        // dp[i][j]: max common high of all possible poles composed by using rods index
        // from 0-i, and with the diff of j of 2 poles
        dp[0][0] = 0;
        for (int i = 1; i <= rods.length; i++) {
            int v = rods[i - 1];
            for (int j = 0; j <= maxDiff; j++) {
                if (dp[i - 1][j] != -1) {
                    // the v is not used, discarded
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
                    // the v is used on the longer side
                    dp[i][j + v] = Math.max(dp[i][j + v], dp[i - 1][j]);
                    // the v is used on the shorter side
                    dp[i][Math.abs(j - v)] = Math.max(dp[i][Math.abs(j - v)], dp[i - 1][j] + Math.min(j, v));
                }
            }
        }
        return dp[rods.length][0];
    }

    // bottom up DP
    public static int tallestBillboard3(int[] rods) {
        HashMap<Integer, Integer> dp = new HashMap<>();
        // dp(s): the max positive sum of all possible combination of
        // multiply of pods[j], 0<=j<=i with one of[1,-1,0],
        // and the sum of combination is s
        // the s can be negative
        dp.put(0, 0);
        for (int v : rods) {
            HashMap<Integer, Integer> clone = new HashMap<>(dp);
            for (Map.Entry<Integer, Integer> entry : clone.entrySet()) {
                Integer sum = entry.getKey();
                Integer positiveSum = entry.getValue();
                // 1
                dp.put(sum + v, Math.max(dp.getOrDefault(sum + v, Integer.MIN_VALUE), positiveSum + v));
                // -1
                dp.put(sum - v, Math.max(dp.getOrDefault(sum - v, Integer.MIN_VALUE), positiveSum));
                // 0
                dp.put(sum, Math.max(dp.getOrDefault(sum, Integer.MIN_VALUE), positiveSum));
            }
        }
        return dp.getOrDefault(0, Integer.MIN_VALUE) == Integer.MIN_VALUE ? 0 : dp.get(0);
    }

    // only think of left and right.
    public static int tallestBillboard4(int[] rods) {
        int N = rods.length;
        Map<Integer, Integer> Ldelta = make(Arrays.copyOfRange(rods, 0, N / 2));
        Map<Integer, Integer> Rdelta = make(Arrays.copyOfRange(rods, N / 2, N));

        int ans = 0;
        for (int d : Ldelta.keySet())
            if (Rdelta.containsKey(-d))
                // delta - delta = 0;
                // max height in total
                ans = Math.max(ans, Ldelta.get(d) + Rdelta.get(-d));
        return ans;
    }

    public static Map<Integer, Integer> make(int[] A) {
        // Note the dp length
        Point[] dp = new Point[(int) Math.pow(3, A.length)];
        int t = 0;
        dp[t++] = new Point(0, 0);
        for (int v : A) {
            int stop = t;
            for (int i = 0; i < stop; ++i) {
                Point p = dp[i];
                // put left or put right
                dp[t++] = new Point(p.x + v, p.y);
                dp[t++] = new Point(p.x, p.y + v);
            }
        }
        Map<Integer, Integer> ans = new HashMap<>();
        for (int i = 0; i < t; ++i) {
            int l = dp[i].x;
            int r = dp[i].y;
            // key: left-right: left is shorter than right
            // value: max left height in current half
            ans.put(l - r, Math.max(ans.getOrDefault(l - r, 0), l));
        }
        return ans;
    }
}
