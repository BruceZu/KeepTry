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

public class Leetcode322CoinChange5 {

  // Time out
  // runtime complexity
  // space complexity: coins length
  public static int coinChange(int[] coins, int A) {
    int[] r = new int[] {Integer.MAX_VALUE};
    int[] aSolution = new int[coins.length];
    recursion(coins, A, 0, r, aSolution);
    return r[0] == Integer.MAX_VALUE ? -1 : r[0];
  }

  private static void recursion(int[] coins, int left, int i, int[] re, int[] numOfCoin) {
    if (left == 0) {
      int result = count(numOfCoin);
      re[0] = Math.min(re[0], result);
    }
    if (i == coins.length) {
      return;
    }

    int n = 0, maxNum = left / coins[i];
    while (n <= maxNum) {
      numOfCoin[i] = n;
      recursion(coins, left - n * coins[i], i + 1, re, numOfCoin);
      n++;
    }
  }

  private static int count(int[] arr) {
    int result = 0;
    for (int i : arr) {
      result += i;
    }
    return result;
  }
  //-----------------------------------------------------------------------------
  public static void main(String[] args) {
    System.out.println("result: " + coinChange(new int[] {1}, 0));
    System.out.println("result: " + coinChange(new int[] {1, 2, 4, 5}, 8));
    System.out.println("result: " + coinChange(new int[] {2}, 3));
    System.out.println("result: " + coinChange(new int[] {1, 2, 5}, 11));
    System.out.println("result: " + coinChange(new int[] {1}, 2));
    System.out.println("result: " + coinChange(new int[] {470, 35, 120, 81, 121}, 9825));
    System.out.println("result: " + coinChange(new int[] {346, 29, 395, 188, 155, 109}, 9401));
    System.out.println("result: " + coinChange(new int[] {1, 2147483647}, 2));
  }
}
