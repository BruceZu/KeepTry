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

package dp.withstatecompression;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// https://leetcode.com/problems/number-of-ways-to-wear-different-hats-to-each-other/

/**
 * There are n people and 40 types of hats labeled from 1 to 40.
 * <p>
 * Given a list of list of integers hats, where hats[i] is a list of all hats preferred by the i-th person.
 * <p>
 * Return the number of ways that the n people wear different hats to each other.
 * <p>
 * Since the answer may be too large, return it modulo 10^9 + 7.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: hats = [[3,4],[4,5],[5]]
 * Output: 1
 * Explanation: There is only one way to choose hats given the conditions.
 * First person choose hat 3, Second person choose hat 4 and last one hat 5.
 * <p>
 * Example 2:
 * <p>
 * Input: hats = [[3,5,1],[3,5]]
 * Output: 4
 * Explanation: There are 4 ways to choose hats
 * (3,5), (5,3), (1,3) and (1,5)
 * <p>
 * Example 3:
 * <p>
 * Input: hats = [[1,2,3,4],[1,2,3,4],[1,2,3,4],[1,2,3,4]]
 * Output: 24
 * Explanation: Each person can choose hats labeled from 1 to 4.
 * Number of Permutations of (1,2,3,4) = 24.
 * <p>
 * Example 4:
 * <p>
 * Input: hats = [[1,2,3],[2,3,5,6],[1,3,7,9],[1,8,9],[2,5,7]]
 * Output: 111
 * <p>
 * <p>
 * <p>
 * Constraints:
 * <p>
 * n == hats.length
 * 1 <= n <= 10
 * 1 <= hats[i].length <= 40
 * 1 <= hats[i][j] <= 40
 * hats[i] contains a list of unique integers.
 */
public class Leetcode1434NumberofWaystoWearDifferentHatstoEachOther {
    public static int numberWays2(List<List<Integer>> hats) {
        if (hats == null) return 1;
        int n = hats.size();
        int X = (int) Math.pow(2, n); // double
        int[][] dp = new int[X][41];

        //get mapping frm hat to men
        Map<Integer, List<Integer>> hToMen = new HashMap();
        for (int i = 0; i < hats.size(); i++) {
            List<Integer> ihats = hats.get(i);
            for (int h : ihats) {
                List<Integer> men = hToMen.get(h);
                if (men == null) {
                    men = new ArrayList();
                    hToMen.put(h, men);
                }
                men.add(i);
            }
        }

        for (int h = 0; h <= 40; h++) {
            dp[0][h] = 1;
        }
//        for (int m = 1; m < X; m++) {
//            dp[m][0] = 0;
//        }
        int MOD = ((int) Math.pow(10, 9) + 7);
        for (int h = 1; h <= 40; h++) { // O(41)
            for (int m = X - 1; m >= 1; m--) {//O(2^N)
                dp[m][h] = dp[m][h - 1];
                if (hToMen.get(h) != null) {
                    List<Integer> men = hToMen.get(h);
                    for (Integer man : men) { // O(N)
                        if ((m & (1 << man)) != 0) {
                            dp[m][h] += dp[m ^ (1 << man)][h - 1];
                            dp[m][h] = dp[m][h] % MOD; //double
                        }
                    }
                }
            }
        }
        return dp[X - 1][40];
    }

    //---------------------------------------------------------------
    // space O(2^N)
    public static int numberWays(List<List<Integer>> hats) {
        if (hats == null) return 1;
        int n = hats.size();
        int X = (int) Math.pow(2, n); // double
        int[] dp = new int[X];

        //get mapping frm hat to men
        Map<Integer, List<Integer>> hToMen = new HashMap();
        for (int i = 0; i < hats.size(); i++) {
            List<Integer> ihats = hats.get(i);
            for (int h : ihats) {
                List<Integer> men = hToMen.get(h);
                if (men == null) {
                    men = new ArrayList();
                    hToMen.put(h, men);
                }
                men.add(i);
            }
        }

        dp[0] = 1;
        int MOD = ((int) Math.pow(10, 9) + 7);
        for (int h = 1; h <= 40; h++) { // O(41)
            for (int m = X - 1; m >= 1; m--) {//O(2^N)
                if (hToMen.get(h) != null) {
                    List<Integer> men = hToMen.get(h);
                    for (Integer man : men) { // O(N)
                        if ((m & (1 << man)) != 0) {
                            dp[m] += dp[m ^ (1 << man)];
                            dp[m] = dp[m] % MOD; //double
                        }
                    }
                }
            }
        }
        return dp[X - 1];
    }


    public static void main(String[] args) {
        List<List<Integer>> hats = new ArrayList<>();
        List<Integer> man0 = new ArrayList<>();
        man0.add(3);
        man0.add(4);

        List<Integer> man1 = new ArrayList<>();
        man1.add(4);
        man1.add(5);

        List<Integer> man2 = new ArrayList<>();
        man2.add(5);
        hats.add(man0);
        hats.add(man1);
        hats.add(man2);

        System.out.println(numberWays(hats) == 1);
    }
}
