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

package dp.knapsack;

import java.util.*;

public class KnapsackMax {
  // initial: dp[0] =0;
  // dp[j] = Integer.MAX_value; 1<= j <= W;
  // for scenario where the best solution's cost == W
  //
  // initial: dp[j]= 0; 0<= j <= W;
  // for scenario where the best solution's cost <= W

  private static void as01Knapsack(int cost, int value, int dp[]) {
    int W = dp.length - 1; // dp length = 0 .. W;
    for (int i = W; i >= cost; i--) {
      dp[i] = Math.max(dp[i], dp[i - cost] + value);
    }
  }

  private static void asCompleteKnapsack(int cost, int value, int dp[]) {
    int W = dp.length - 1; // dp length = 0 .. W;
    for (int i = cost; i <= W; ++i) {
      dp[i] = Math.max(dp[i], dp[i - cost] + value);
    }
  }

  /*  Multiple Knapsack:
      each product: Pi_quantity*Pi_space < total space
      Scenario:
                package size has limitation, each item has
                   - size / per
                   - value / per
                   - quantity/count
                how many item should be taken in package to get max value.
                try best to put more items and get more value. not exactly

                problem: to try spent all money

       dp[i][j]: means [0, i] items achieved max value in j size space

       dp[i][j] = max{ dp[i-1][ j - k*cost] + k*value }
       0 <= j <= W,
       j - k*cost >= 0; so 0<=k<=j/cost

       The elements in the max{},not dp[i-1][j], is cut into groups, groups number = cost,
       Each group start from dp[i-1][0],dp[i-1][1],dp[i-1][2],...dp[i-1][cost-1]

       watch: it is possible to use 1 dimensional array dp[j]
       watch group i, start from j=gi the process:
           gi,     gi+cost,        gi + 2*cost,     gi+ 3* cost,....,  gi+ n* cost< W

        dp[gi]     dp[gi+cost]     dp[gi+2*cost]     dp[gi+3*cost]
                   dp[gi]+v        dp[gi+cost]+v     dp[gi+2*cost]+v
                                   dp[gi]+2v         dp[gi+cost]+2v

          (1)       (2)            (3)               (4)

        if current product quantity is 2
        Watch (3)
        seems to get new dp[gi + 2*cost]:
          dp[gi+2*cost] -v
          dp[gi+cost]    (reuse the item in (2))
          dp[gi]+v       (reuse the item in (2))
        once find the max, let max+v
        Watch (2)
        seems to get new dp[gi + cost]:
          dp[gi+ cost] -v
          dp[gi]    (reuse the item in (1))
        once find the max, let max+v
        watch back on (3) find the generay way:
          dp[gi+2*cost] -2v
          dp[gi+cost]-v    (reuse the item in new (2))
          dp[gi]           (reuse the item in new (2))
        once find the max, let max+2v

       So avoid duplicated calculation use the queue keep elements:
         dp[gi], dp[gi+cost]-v,dp[gi+2*cost] -2v, dp[gi+3*cost] -3v, ...

       dp index: 0 ... W;
       O(W) W is the cost/pack‘s volume
       cost: current object type's cost,
       value: current object type's value,
       count: current object type's quantity

  W is dp.length here, space limitation, W is split in groups,
  use no-increasing queue: a slide window, its size <= count +1 which is comparable solution number
  use 2 queue: keep value of queue element and keep sequence of queue element

  dp[i] is calculated in groups and there is no conflict between any 2 groups
  O(W) time and space for current product.
  */
  private static void asMultipleKnapsack(int cost, int value, int count, int dp[]) {
    int W = dp.length;
    int[] vq = new int[dp.length];
    Deque<Integer> sq;
    for (int g = 0; g < cost; g++) {
      sq = new LinkedList<>();
      for (int j = g, n = 0; j < W; j += cost, n++) {
        int e = dp[j] - n * value; // queue element
        // =======  [calculate the max in slide window of queue] START =======
        vq[n] = e;
        while (!sq.isEmpty() && vq[sq.peekLast()] < e) sq.removeLast();
        sq.addLast(n); // keep index not value
        // index of element just out of window
        int idx = n - (count + 1); // window size is count+1
        if (idx >= 0 && sq.peekFirst() == idx) sq.removeFirst();
        int max = vq[sq.peekFirst()];
        // =======  [calculate the max in slide window of queue] END =======
        dp[j] = max + n * value;
      }
    }
  }

  public static void knapsack(int cost, int value, int count, int dp[]) {
    int W = dp.length - 1; // dp length = 0 .. W;  W is the top limitation of the backpack
    if (count == 0 || cost == 0) return; // it is initial value.
    if (count == 1) { // 01 backpack
      as01Knapsack(cost, value, dp);
    } else if (count * cost >= W) { // complete backpack (n*cost >= W)
      asCompleteKnapsack(cost, value, dp);
    } else { // assume only 3 options: 01, complete and multiple backpack
      asMultipleKnapsack(cost, value, count, dp);
    }
  }
  // ----- no comments version  --------------------------------------------------------------
  // O(W) time
  private static void asMultipleKnapsack2(int cost, int value, int count, int dp[]) {
    int W = dp.length - 1;
    int[] q = new int[W + 1];
    Deque<Integer> imq;
    for (int d = 0; d < cost; d++) {
      int i = -1;
      imq = new LinkedList<>(); //
      for (int j = d, cnt = 0; j <= W; j += cost, cnt++) {
        i++;
        int e = dp[j] - cnt * value;
        q[i] = e;
        while (!imq.isEmpty() && q[imq.peekLast()] < e) imq.removeLast();
        imq.addLast(i);
        int idx = i - (count + 1);
        if (idx >= 0 && imq.peekFirst() == idx) imq.removeFirst();
        int max = q[imq.peekFirst()];
        dp[j] = max + cnt * value;
      }
    }
  }

  // ------------------------- test ----------------------------------------------------------
  public static void main(String[] args) {
    // Test my notebook case with multipleBackpack()
    int[][] items =
        new int[][] {
          new int[] {7, 9, 9},
          new int[] {4, 5, 9},
          new int[] {3, 3, 9},
          new int[] {2, 1, 9},
        };
    int W = 10;
    int[] dp = new int[W + 1]; // initial with 0 means
    for (int[] item : items) {
      int cost = item[0], value = item[1], counts = item[2];
      asMultipleKnapsack(cost, value, counts, dp);
      System.out.println(Arrays.toString(dp));
    }
    // Test my note book case with with pack()
    dp = new int[W + 1]; // initial with 0 means
    for (int[] item : items) {
      int cost = item[0], value = item[1], counts = item[2];
      knapsack(cost, value, counts, dp);
      System.out.println(Arrays.toString(dp));
    }
    /*
    [0, 0, 0, 0, 0, 0, 0, 9,  9,  9, 9]
    [0, 0, 0, 0, 5, 5, 5, 9, 10, 10, 10]
    [0, 0, 0, 3, 5, 5, 6, 9, 10, 10, 12]
    [0, 0, 1, 3, 5, 5, 6, 9, 10, 10, 12]
    */
  }
}
