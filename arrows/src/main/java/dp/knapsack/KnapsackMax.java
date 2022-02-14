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

/*
https://segmentfault.com/a/1190000006325321
 */
public class KnapsackMax {
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
  /*
  initial: dp[0] =0;
  dp[j] = Integer.MAX_value; 1<= j <= W;
  for scenario where the best solution's cost == W

  initial: dp[j]= 0; 0<= j <= W;
  for scenario where the best solution's cost <= W
  */
  static void as01Knapsack(int cost, int value, int dp[]) {
    int W = dp.length - 1; // dp length = 0 .. W;
    for (int i = W; i >= cost; i--) {
      dp[i] = Math.max(dp[i], dp[i - cost] + value);
    }
  }
  /*
  Duplicated work can be see: https://imgur.com/tm7ASop
  That is why calculate from left to right
  */
  static void asCompleteKnapsack(int cost, int value, int dp[]) {
    int W = dp.length - 1; // dp length = 0 .. W;
    for (int i = cost; i <= W; i++) {
      dp[i] = Math.max(dp[i], dp[i - cost] + value);
    }
  }

  /*  Multiple Knapsack:
      each item: item_i_quantity * item_i_space < total space
      Scenario:
                package size has limitation, each item has
                   - size
                   - value
                   - count K
                how many item should be taken in package to get max value.
                try best to put more items and get more value. not exactly

                problem: to try spent all money

       dp[i][j]: means [0, i] items achieved max value in j size space

       dp[i][j] = max{ dp[i-1][ j - k*cost] + k*value }
       0 <= j <= W,
       j - k*cost >= 0;
       k=0, 1, 2, ..., K

       for item i, dp[i][j] depends on dp[i-1] only
       so it is possible to use 1 dimensional array dp[j]
       calculate row by row, for [0, i-1] then [0, i]

       Watch the item size/cost, value, count:   the size/cost can be > 1
       dp[j] can be calculated in groups. each group start from
       dp[0],dp[1],dp[2],...dp[cost-1]

       assume the item has count = 2, then watch group i, start from j=gi the process:
        gi,           gi+cost,        gi + 2*cost,     gi+ 3* cost,....,  gi+ n* cost< W
       new value of
       dp[gi]=       dp[gi+cost]=     dp[gi+2*cost]=   dp[gi+3*cost]=      ...
       max{          max{             max{             max{
          dp[gi]      dp[gi+cost]      dp[gi+2*cost]    dp[gi+3*cost]
                      dp[gi]+v         dp[gi+cost]+v    dp[gi+2*cost]+v
                                       dp[gi]+2v        dp[gi+cost]+2v
       }             }                }                }


      deduplicate calculation with a general way:
       max{          max{             max{             max{
          dp[gi]      dp[gi+cost]-v   dp[gi+2*cost]-2v    dp[gi+3*cost] -3v
                      dp[gi]          dp[gi+cost]-v       dp[gi+2*cost] -2v
                                      dp[gi]              dp[gi+cost] -v
       }             } +v             } +2v               } + 3v


       keep a sliding window, size <= cnt+1.
       with
       - a index dequeue, keep it in no-increasing order
       - a value array can calcualte the max of sliding window in O(1)

  O(W) time and space for current product.
  */
  private static void asMultipleKnapsack(int cost, int value, int count, int dp[]) {
    int W = dp.length - 1;
    int[] v = new int[dp.length]; // keep value
    Deque<Integer>
        dq; // keep index. descending order dequeue, calculate max of sliding window in O(1)

    for (int g = 0; g < cost; g++) {
      dq = new LinkedList<>(); // for each group
      for (int j = g, cnt = 0; j <= W; j += cost, cnt++) {
        int rv = dp[j] - cnt * value; // value, step in window at right side
        // =======  max in slide window =======
        v[cnt] = rv;
        while (!dq.isEmpty() && v[dq.peekLast()] < rv) dq.removeLast();
        dq.addLast(cnt);
        int l = cnt - (count + 1); // index, out of window at left side, window size is count+1
        if (l >= 0 && dq.peekFirst() == l) dq.removeFirst();
        int max = v[dq.peekFirst()];
        // =======  max in slide window =======
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
