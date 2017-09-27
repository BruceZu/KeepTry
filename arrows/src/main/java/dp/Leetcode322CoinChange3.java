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

package dp;

import java.util.HashMap;
import java.util.Map;

/** @see <a href="https://leetcode.com/problems/coin-change/description/">Leetcode </a> */
public class Leetcode322CoinChange3 {
    private static Map<Integer, Integer> cache;
    // ---------------------------------------------------------------------------
    // DP
    public static int coinChange(int[] coins, int A) {
        if (A == 0) return 0; // just for pass test in leetcode.
        if (coins == null || coins.length == 0 || A < 1) return -1;
        cache = new HashMap<>();
        cache.put(0, 0);
        for (int i = 0; i < coins.length; i++) {
            cache.put(coins[i], 1);
        }
        return minSumNumberOfCoinsFor(coins, A);
    }

    public static int minSumNumberOfCoinsFor(int[] coins, int A) {
        if (cache.get(A) != null) return cache.get(A);
        int r = Integer.MAX_VALUE;
        for (int i = 0; i < coins.length; i++) {
            if (A - coins[i] < 0) continue;

            int cur = minSumNumberOfCoinsFor(coins, A - coins[i]);
            if (cur != -1) {
                r = Math.min(cur + 1, r);
            }
        }
        cache.put(A, r == Integer.MAX_VALUE ? -1 : r);
        return cache.get(A);
    }

    //-----------------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("result: " + coinChange(new int[] {1}, 0));
        System.out.println("result: " + coinChange(new int[] {1, 2, 4, 5}, 8));
        System.out.println("result: " + coinChange(new int[] {2}, 3));
        System.out.println("result: " + coinChange(new int[] {1, 2, 5}, 11));
        System.out.println("result: " + coinChange(new int[] {1}, 2));
        System.out.println("result: " + coinChange(new int[] {470, 35, 120, 81, 121}, 9825));
    }
}
