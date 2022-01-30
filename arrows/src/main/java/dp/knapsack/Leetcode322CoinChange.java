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

import java.util.Arrays;

public class Leetcode322CoinChange {
  /*
    Leetcode 322. Coin Change

    You are given an integer array coins representing coins of different denominations and
    an integer amount representing a total amount of money.

    Return the fewest number of coins that you need to make up that amount.
    If that amount of money cannot be made up by any combination of the coins, return -1.

    You may assume that you have an infinite number of each kind of coin.


    Input: coins = [1,2,5], amount = 11
    Output: 3
    Explanation: 11 = 5 + 5 + 1


    Input: coins = [2], amount = 3
    Output: -1


    Input: coins = [1], amount = 0
    Output: 0


    Constraints:

    1 <= coins.length <= 12
    1 <= coins[i] <= 231 - 1
    0 <= amount <= 104
  */
  /*
   infinite number of each kind of coin
   greedy does not work in this case.
   backtracking all possible way is polynomial time
   dp[t] = min{ dp[t-vi] | vi in given v[] and vi<=t}
   dp[0]=0, other default is MAX

  */
  /*Time Limit Exceeded -------------------------------------------------------
   reduce the coins choice scope
   without cache which should be 2 dimension cache: target value and choice scope[j, length-1]
  */
  public int coinChange_____(int[] coins, int T) {
    return bt(0, coins, T);
  }

  private int bt(int i, int[] coins, int T) {
    if (T == 0) return 0;
    if (i < coins.length && T > 0) {
      int maxn = T / coins[i];
      int minCost = Integer.MAX_VALUE;
      for (int n = 0; n <= maxn; n++) {
        if (T >= n * coins[i]) {
          int res = bt(i + 1, coins, T - n * coins[i]);
          if (res != -1) minCost = Math.min(minCost, res + n);
        }
      }
      return (minCost == Integer.MAX_VALUE) ? -1 : minCost;
    }
    return -1;
  }
  /* Time Limit Exceeded -------------------------------------------------------
     reduce the target and not change the coins choice scope
     without cache which is one dimension: target value
  */
  private int min;

  public int coinChange____(int[] coins, int T) {
    min = T + 1;
    if (T < 1) return 0;
    bt(coins, T, 0);
    return min == T + 1 ? -1 : min;
  }

  private void bt(int[] coins, int T, int count) {
    if (T == 0) {
      min = Math.min(min, count);
      return;
    }
    // continue
    for (int v : coins) {
      if (T - v >= 0) {
        bt(coins, T - v, count + 1);
      }
    }
  }

  /* top down  ----------------------------------------------------------------
  Watch the above solution process https://imgur.com/3Jk18aZ
  Observer:
  There is repeated work when current target t changed in different layer.
  So, for keeping the repeated work: cache current target t and related min counts
  and the comparison will be moved from the end of backtracking into inner loop
  check all possible way top down layer by layer but with a cache
  */
  public int coinChange___(int[] coins, int T) {
    if (T < 1) return 0;
    return bt(coins, T, new int[T + 1]);
  }

  private int bt(int[] coins, int T, int[] cache) {
    if (T == 0) return 0;
    if (cache[T] != 0) return cache[T];
    //
    int min = Integer.MAX_VALUE;
    for (int v : coins) {
      if (T - v >= 0) {
        int count = bt(coins, T - v, cache);
        if (count != -1 && count + 1 < min) min = count + 1; // 1 is one coin, the current coin.
      }
    }
    // -1 means no solution, required by 'If that amount of money cannot be made up by any
    // combination of the coins, return -1.'

    cache[T] = (min == Integer.MAX_VALUE) ? -1 : min;
    //
    return cache[T];
  }

  /* --------------------------------------------------------------------------
  Runtime complexity O(T*N)
  bottom up and using the already calculated previous value
  */
  public static int coinChange__(int[] coins, int T) {
    if (T < 1) return 0;
    int[] dp = new int[T + 1];
    // Arrays.sort(coins);
    dp[0] = 0; // dp[i]:  min counts of coins with sum value as i
    // bottom up
    for (int t = 1; t <= T; t++) {
      dp[t] = Integer.MAX_VALUE;
      for (int v : coins) {
        if (v <= t && dp[t - v] != Integer.MAX_VALUE) {
          dp[t] = Math.min(dp[t], dp[t - v] + 1);
        }
        // if (v > t) break;
      }
    }
    return dp[T] == Integer.MAX_VALUE ? -1 : dp[T];
  }
  // do not use Integer.MAX_VALUE
  public int coinChange_(int[] coins, int T) {
    int MAX = T + 1;
    int[] dp = new int[T + 1];
    Arrays.fill(dp, MAX);
    dp[0] = 0;
    for (int t = 1; t <= T; t++) {
      for (int v : coins) {
        if (t - v >= 0) {
          dp[t] = Math.min(dp[t], dp[t - v] + 1);
        }
      }
    }
    return dp[T] == MAX ? -1 : dp[T];
  }
  // bottom up: from current valid value to extend ---------------------------
  public static int coinChange(int[] coins, int T) {
    if (T < 1) return 0;
    int MAX = T + 1;
    int[] dp = new int[MAX];
    for (int i = 1; i <= T; i++) {
      dp[i] = MAX;
    }
    // dp[0] is 0;

    Arrays.sort(coins); // when logN < T
    // bottom up from valid dp[t]
    for (int t = 0; t < T; t++) {
      if (dp[t] == MAX) continue;
      for (int v : coins) {
        if (0 <= t + v && t + v <= T) {
          dp[t + v] = Math.min(dp[t + v], dp[t] + 1);
        } else break; // benefit from sorted coin values
      }
    }
    return dp[T] == MAX ? -1 : dp[T];
  }

  /* Complete Knapsack => minimum count of coins with target value ------------

   Runtime O(T*N), Space O(N) N is coins type number. T is target value
   dp[i] is minimum count of coins with target value i with the coins type from
         coins[0] to coins[current index]
  */
  public static int coinChangeCK(int[] coins, int T) {
    if (T < 1) return 0;
    int MAX = T + 1;
    int[] dp = new int[MAX];
    for (int i = 1; i <= T; i++) {
      dp[i] = MAX;
    }
    // dp[0] is 0;

    Arrays.sort(coins); // when logN < T
    for (int i = 0; i < coins.length; i++) {
      int v = coins[i];
      for (int t = 1; t <= T; t++) {
        if (0 <= t - v && dp[t - v] != MAX) {
          dp[t] = Math.min(dp[t], dp[t - v] + 1);
        }
      }
    }
    return dp[T] == MAX ? -1 : dp[T];
  }
  // -----------------------------------------------------------------------------
  public static void main(String[] args) {
    System.out.println("result: " + coinChange(new int[] {1}, 0));
    System.out.println("result: " + coinChange(new int[] {1, 2, 4, 5}, 8));
    System.out.println("result: " + coinChange(new int[] {2}, 3));
    System.out.println("result: " + coinChange(new int[] {1, 2, 5}, 11));
    System.out.println("result: " + coinChange(new int[] {1}, 2));
    System.out.println("result: " + coinChange(new int[] {470, 35, 120, 81, 121}, 9825));
    System.out.println("result: " + coinChange(new int[] {1, 2147483647}, 2));
  }
}
