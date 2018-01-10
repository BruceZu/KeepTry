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

package dp.partition;

import java.util.Arrays;

public class ChangeMoneyLimitedCopys_kSubset {
  // subset is k, sum is m
  /**
   * There are lots of different variations of this problem. Sometimes, instead of a sum, we
   * are interested in the product, and our coins are prime numbers. Then this algorithm is somewhat
   * similar to the sieve of Erastosthenes.
   *
   * <p>Other times, we might want to find a subset of a particular size k, whose sum is m. In this
   * case, we might want to make the table two­dimensional, k­by­n, and use each row to generate the
   * next one.
   */

  // best of 2-d dp table is it can tracing the trail and with it to find all the result not only true or false
  // O(Sum*N)
  // assume nums is positive integer. how about negative integer
  public static void printAllSubsetsWithgivenSum(int[] nums, int S) {
    if (Arrays.stream(nums).sum() < S) {
      System.out.println("no solution");
      return;
    }
    if (S <= 0) {
      System.out.println("0"); // ????
      return;
    }
    if (nums == null || nums.length == 0) {
      System.out.println("no solution");
    }
    // Todo: core cases checking end
    boolean[][] dp = new boolean[nums.length + 1][S + 1];
    dp[0][0] = true;
    int R = 0;
    //O(Sum*N)
    for (int di = 0; di < nums.length; di++) { // from 0 row
      int curn = nums[di];
      for (int si = R; si >= 0; si--) {
        if (dp[di][si]) {
          if (si + curn <= S) {
            dp[di + 1][si + curn] = true;
          }
          dp[di + 1][si] = true;
        }
      }
      R = Math.min(S, R + curn);
    }

    if (dp[nums.length][S]) {
      // tracing back all solutions
      System.out.println("tracing back all solutions");
      deepFirstTracing(nums, dp, nums.length, S, new int[nums.length], 0);
    } else {
      System.out.println("no solution");
    }
  }

  // O(Sum*N)
  public static void deepFirstTracing(
      int[] nums, boolean[][] dp, int i, int j, int[] cur, int size) {
    if (i == 0 && j == 0) {
      // if (size != 3) return; // less than size 3
      for (int k = 0; k < size; k++) {
        System.out.print(cur[k] + ",");
      }
      System.out.println();
      return;
    }

    // i - 1 >= 0
    if (dp[i - 1][j]) {
      deepFirstTracing(nums, dp, i - 1, j, cur, size);
    }
    // if (size == 3) return; //more than size 3

    int num = nums[i - 1];
    if (j - num >= 0 && dp[i - 1][j - num]) {
      cur[size] = num;
      //size++;
      deepFirstTracing(nums, dp, i - 1, j - num, cur, size + 1);
      //size--;
    }
  }

  public static void main(String[] args) {
    //    1, 0, 0, 0, 0, 0
    //    1, 0, 1, 0, 0, 0
    //    1, 0, 1, 1, 0, 1
    printAllSubsetsWithgivenSum(new int[] {2, 3}, 5);

    printAllSubsetsWithgivenSum(new int[] {1, 2, 3, 4, 5}, 10);
    //    1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    //    1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0
    //    1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0
    //    1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1
    //    1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1
    //    1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1
    //    1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1
    printAllSubsetsWithgivenSum(new int[] {2, 3, 5, 6, 8, 10}, 10);
  }
}
