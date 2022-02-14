//  Copyright 2021 The KeepTry Open Source Project
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

package dp.knapsack;

import java.util.Arrays;

/**
 * Apply basic idea in BackPackMax to calculate boolean logic E.g. If it is possible to use existing
 * coin (type, counts) to exactly make number of price
 */
public class KnapsackBoolean {
  public static void knapsack(int value, int count, boolean dp[]) {
    int W = dp.length - 1; // dp length = 0 .. W;  W is the top limitation of the backpack
    if (count == 0) return; // it is initial value.
    if (count == 1) { // 01 backpack
      as01knapsack(value, dp);
    } else if (count * value >= W) { // complete backpack (n*value >= W)
      asCompleteKnapsack(value, dp);
    } else { // assume only 3 options: 01, complete and multiple backpack
      asMultipleKnapsack(value, count, dp);
    }
  }
  // boolean dp[]
  // initial: dp[0] = true;
  // dp[j] = false; 1<= j <= W;

  static void as01knapsack(int coinValue, boolean dp[]) {
    for (int price = dp.length - 1; price >= coinValue; price--) {
      dp[price] = dp[price] || dp[price - coinValue];
    }
  }
  // unbounded knapsack problem (UKP)
  static void asCompleteKnapsack(int coinValue, boolean dp[]) {
    for (int price = coinValue; price <= dp.length - 1; ++price) {
      dp[price] = dp[price] || dp[price - coinValue];
    }
  }

  /*
   refer KnapsackMax.asMultipleKnapsack
   diff: it is boolean, not max.
   boolean dp[j] = ||{ dp[ j - k*v]}
           v = coin Value

   window size is current coin count+1
  */
  // bounded knapsack problem (BKP)
  private static void asMultipleKnapsack(int coinValue, int count, boolean dp[]) {
    int L = dp.length;
    int W = L - 1;
    boolean[] v = new boolean[L];
    for (int g = 0; g < coinValue; g++) { // group by j%cost[i]
      // use 1 and 0 mapping to true and false then use sum of
      // slide window to represent the result of ||
      int sum = 0; // not deque.
      for (int j = g, cnt = 0; j <= W; cnt++, j += coinValue) {
        boolean rv = dp[j];
        // ======= "||" in slide window =======
        v[cnt] = rv;
        sum += rv ? 1 : 0;
        int l = cnt - (count + 1);
        if (l >= 0) sum -= (v[l] ? 1 : 0);
        // ======= "||" in slide window =======
        dp[j] = sum >= 1;
      }
    }
  }

  // ----- no comments version  --------------------------------------------------------------
  private static void asMultipleKnapsack2(int value, int count, boolean dp[]) {
    boolean[] q = new boolean[dp.length + 1];
    for (int d = 0; d < value; d++) {
      int i = -1;
      int possible = 0;
      for (int j = d; j <= dp.length - 1; j += value) {
        i++;
        boolean e = dp[j];
        q[i] = e;
        possible += e ? 1 : 0;
        int idx = i - (count + 1);
        if (idx >= 0) possible -= (q[idx] ? 1 : 0);
        dp[j] = possible > 0;
      }
    }
  }

  /*
    POJ 1742 Coins
    Description

    People in Silverland use coins.
    They have coins of value A1,A2,A3...An Silverland dollar.
    One day Tony opened his money-box and found there were some coins.
    He decided to buy a very nice watch in a nearby shop.
    He wanted to pay the exact price(without change) and he knows the price would not more than m.
    But he didn't know the exact price of the watch.
    You are to write a program which reads
    n,m,  // two integers n(1<=n<=100),m(m<=100000)
    A1,A2,A3...An C1,C2,C3...Cn // 2n integers, denoting A1,A2,A3...An,C1,C2,C3...Cn (1<=Ai<=100000,1<=Ci<=1000)
                                // the number of Tony's coins of value
    A1,A2,A3...An

    then calculate how many prices(form 1 to m)
    Tony can pay use these coins.
  */

  /* Multiple backpack  O(N*P) time.
    see https://imgur.com/H1o1kLC
    dp[i]=-1: for price i if no exactly solution with coins in box
    dp[i]>=0 : there is exactly solution with coins in box without change
               now the dp[i] is the current coin left counts
  */
  // coin value, counts, price P>=1;
  static int multipleKnapsackInt(int[] V, int[] C, int P) {
    int N = V.length;
    int dp[] = new int[P + 1];
    Arrays.fill(dp, -1);
    dp[0] = 0;

    // Note: how to control the quantity limitation of each coin type.
    // Thus apply complete backpack algorithm to multiple backpack.
    for (int i = 0; i < N; i++) {
      int count = C[i], v = V[i]; // coin count and value
      for (int p = 0; p <= P; p++) { // price
        if (dp[p] >= 0) dp[p] = count;
        if (dp[p] < 0 && p - v >= 0 && dp[p - v] >= 1) {
          dp[p] = dp[p - v] - 1;
        }
      }
    }
    int sum = 0; // Number of price
    for (int i = 1; i <= P; i++) { // P=0 is not be taken account in
      if (dp[i] >= 0) sum++;
    }
    return sum;
  }
  // Multiple backpack  O(N*P) time.
  static int multipleKnapsack2(int[] coinsValue, int[] coinCounts, int P) {
    int N = coinsValue.length;
    int dp[] = new int[P + 1];
    Arrays.fill(dp, -1);
    dp[0] = 0;
    for (int i = 0; i < N; i++) {
      for (int p = 0; p <= P; p++) {
        if (dp[p] >= 0) dp[p] = coinCounts[i];
        if (dp[p] < 0 && p - coinsValue[i] >= 0 && dp[p - coinsValue[i]] >= 1) {
          dp[p] = dp[p - coinsValue[i]] - 1;
        }
      }
    }
    int sum = 0;
    for (int i = 1; i <= P; i++) {
      if (dp[i] >= 0) sum++;
    }
    return sum;
  }
  // ------------------------- test ----------------------------------------------------------
  public static void main(String[] args) {
    // First test case of POJ 1742 Coins with asMultiplePack()
    // the value[i] = cost[i]
    int items[][] =
        new int[][] {
          {1, 1, 2},
          {2, 2, 1},
        };
    int W = 4;
    boolean[] dp = new boolean[W + 1];
    // initial with false for index j [1, W]. Because it is required to just make number j, no
    // more no less.
    dp[0] = true;
    for (int[] item : items) {
      int cost = item[0], value = item[1], counts = item[2];
      asMultipleKnapsack(value, counts, dp);
    }
    int sum = 0;
    for (int i = 1; i <= W; i++) {
      if (dp[i]) sum++;
    }
    System.out.println(sum == 4);
    // First  test case of  POJ 1742 Coins with pack()
    dp = new boolean[W + 1];
    dp[0] = true;
    for (int[] i : items) {
      int v = i[1], n = i[2];
      knapsack(v, n, dp);
    }
    sum = 0;
    for (int i = 1; i <= W; i++) {
      if (dp[i]) sum++;
    }
    System.out.println(sum == 4);
    // Second test case of  POJ 1742 Coins with asMultiplePack()
    items =
        new int[][] {
          {1, 1, 2}, {2, 2, 1}, {4, 4, 1},
        };
    W = 10;
    dp = new boolean[W + 1];
    dp[0] = true;
    for (int[] item : items) {
      int value = item[1], counts = item[2];
      asMultipleKnapsack(value, counts, dp);
    }
    sum = 0;
    for (int i = 1; i <= W; i++) {
      if (dp[i]) sum++;
    }
    System.out.println(sum == 8);
    Arrays.fill(dp, false);
    // Second test case of POJ 1742 Coins with pack()
    dp = new boolean[W + 1];
    dp[0] = true;
    for (int[] item : items) {
      int value = item[1], counts = item[2];
      knapsack(value, counts, dp);
    }
    sum = 0;
    for (int i = 1; i <= W; i++) {
      if (dp[i]) sum++;
    }
    System.out.println(sum == 8);

    // 2  test case of POJ 1742 Coins with multipleBackpack()
    System.out.println(multipleKnapsackInt(new int[] {1, 2}, new int[] {2, 1}, 5) == 4);
    System.out.println(multipleKnapsackInt(new int[] {1, 2, 4}, new int[] {2, 1, 1}, 10) == 8);
  }
}
