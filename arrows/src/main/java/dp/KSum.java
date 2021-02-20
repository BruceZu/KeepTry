//  Copyright 2017 The keepTry Open Source Project
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
 * from distinct and positive integer array find k number to make their sum is target, k < = length
 * ask the number of solutions.
 */
// bottom up
public class KSum {
  static int ksum(int[] arr, int K, int target) {
    int N = arr.length;
    int[][][] dp = new int[N + 1][K + 1][target + 1];

    for (int i = 0; i <= N; i++) { // index in dp, not in arr.
      for (int k = 0; k <= K; k++) {
        for (int t = 0; t <= target; t++) {
          if (k == 0 && t == 0) { // no matter i is 0 or not.
            dp[i][k][t] = 1;
          } else if (i != 0 && k != 0 && t != 0) {
            dp[i][k][t] = dp[i - 1][k][t];
            if (t - arr[i - 1] >= 0) {
              dp[i][k][t] += dp[i - 1][k - 1][t - arr[i - 1]];
            }
          }
        }
      }
    }

    return dp[N][K][target];
  }

  // bottom up O(NKT)
  static int ksum2(int[] arr, int K, int target) {
    int N = arr.length;
    int[][][] dp = new int[N + 1][K + 1][target + 1];
    // default dp[i][k][t] is 0
    // dp[x][0][0] is 1. the meaning comes from  top down idea. See ksum3.
    for (int i = 0; i <= N; i++) dp[i][0][0] = 1;

    for (int i = 1; i <= N; i++) {
      for (int k = 1; k <= K; k++) {
        for (int t = 1; t <= target; t++) {
          dp[i][k][t] = dp[i - 1][k][t];
          if (t - arr[i - 1] >= 0) {
            dp[i][k][t] += dp[i - 1][k - 1][t - arr[i - 1]];
          }
        }
      }
    }

    return dp[N][K][target];
  }

  // top - down idea
  static int ksum3(int[] arr, int l, int k, int target) {
    if (l < k) return 0;
    // now k <= l
    if (k == 0) {
      if (target == 0) return 1;
      return 0;
    }
    // k != 0, continue recursion convert
    return ksum3(arr, l - 1, k, target) + ksum3(arr, l - 1, k - 1, target - arr[l - 1]);
  }

  public static void main(String[] args) {
    System.out.println(ksum(new int[] {1, 2, 3, 4}, 2, 5) == 2);
    System.out.println(ksum2(new int[] {1, 2, 3, 4}, 2, 5) == 2);
    System.out.println(ksum3(new int[] {1, 2, 3, 4}, 4, 2, 5) == 2);

    System.out.println(ksum(new int[] {1, 2, 3, 4, 5}, 3, 6) == 1);
    System.out.println(ksum2(new int[] {1, 2, 3, 4, 5}, 3, 6) == 1);
    System.out.println(ksum3(new int[] {1, 2, 3, 4, 5}, 5, 3, 6) == 1);
  }
}
