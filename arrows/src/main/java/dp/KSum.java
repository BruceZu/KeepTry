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
public class KSum {
    // Get count of solution
    static int ksum(int[] arr, int k, int target) {
        int[][][] dp = new int[arr.length + 1][k + 1][target + 1];

        for (int i = 0; i <= arr.length; i++) {
            for (int j = 0; j <= k; j++) {
                for (int t = 0; t <= target; t++) {
                    if (j == 0 && t == 0) {
                        // i j k
                        // x 0 0
                        // 0 0 0
                        dp[i][j][t] = 1;
                    } else if (i != 0 && j != 0 && t != 0) {
                        // i j t
                        // x x x
                        dp[i][j][t] = dp[i - 1][j][t];
                        if (t - arr[i - 1] >= 0) {
                            dp[i][j][t] += dp[i - 1][j - 1][t - arr[i - 1]];
                        }
                    }
                    // i, j, t
                    // 0  x  x
                    // x  0  x
                    // x  x  0
                    // 0  0  x
                    // 0  x  0

                }
            }
        }

        return dp[arr.length][k][target];
    }

    static int ksum2(int[] arr, int k, int target) {
        int[][][] dp = new int[arr.length + 1][k + 1][target + 1];
        for (int i = 0; i <= arr.length; i++) dp[i][0][0] = 1;

        for (int i = 1; i <= arr.length; i++) {
            for (int j = 1; j <= k; j++) {
                for (int t = 1; t <= target; t++) {
                    dp[i][j][t] = dp[i - 1][j][t];
                    if (t - arr[i - 1] >= 0) {
                        dp[i][j][t] += dp[i - 1][j - 1][t - arr[i - 1]];
                    }
                }
            }
        }

        return dp[arr.length][k][target];
    }

    static int ksum3(int[] arr, int i, int j, int k) {
        if (j > i) return 0;
        // now j <= i
        if (j == 0) {
            if (k == 0) return 1;
            return 0;
        }
        // j!=0
        return ksum3(arr, i - 1, j, k) + ksum3(arr, i - 1, j - 1, k - arr[i - 1]);
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
