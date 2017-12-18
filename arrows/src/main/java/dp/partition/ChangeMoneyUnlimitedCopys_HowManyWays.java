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

public class ChangeMoneyUnlimitedCopys_HowManyWays {

  /**
   * Change Money With Unlimited Coins. permutation
   * <pre>
   *      if each number has Unlimited Copies.
   *      pennies, nickels, dimes, quarters, loonies and toonies
   *      1, 5, 10, 25, 100 and 200 cents each.
   *
   *     T[x] is not boolean. Instead, store the number of ways we can make change for x cents. T[0] is 1.
   *     for each C[i]. either ignore C[i], or add C[i] to x.
   *     If before we had T[x] ways of making change for x,
   *     then now will have T[x] new ways of making change for x + C[i]
   *
   *     == concern
   *     combination the order does not matter
   *     permutation
   * permutation
   *  <pre>      [1 2 5] 8
   *
   *     0 1 2 3 4 5  6  7   8
   *     1
   *       1 1     1
   *         2 1      1
   *           3 2       2
   *             5 4         3
   *               9  6
   *                 15 11
   *                    26   18
   *                         44
   */

  // runtime O(nS)
  public static long howManyWays(int[] coins, int k) {
    // corner case check
    long[] N = new long[k + 1];
    N[0] = 1;

    for (int s = 0; s < N.length; s++) { // routine sum is the outer loop
      if (N[s] != 0) {
        for (int c : coins) { // start from left
          if (s + c < N.length) {
            N[s + c] += N[s];
          }
        }
      }
    }
    return N[k];
  }

  /**
   *  Change Money With Unlimited Coins.combination
   *   <pre>    [1 2 5] 8
   *
   *     0 1 2 3 4 5 6 7 8
   *     1
   *     1 1 1 1 1 1 1 1 1
   *     1 1 2 2 3 3 4 4 5
   *     1 1 2 2 3 4 5 6 7
   *
   *           [5 2 1] 8
   *
   *     0 1 2 3 4 5 6 7 8
   *     1
   *               1
   *         1   1   1 1 1
   *       1 2 2 3 4 5 6 7
   *
   */
  // runtime O(nS)
  public static int howManyWaysCombination(int[] coins, int S) {
    // corner case check
    if (coins == null || coins.length == 0 || S == 0) {
      return 0;
    }
    int[] T = new int[S + 1];
    T[0] = 1;
    for (int coin : coins) { // coins is outer loop
      for (int j = 0; j + coin <= S; j++) {
        if (T[j] != 0) {
          T[j + coin] += T[j];
        }
      }
    }
    return T[S];
  }

  public static void main(String[] args) {
    System.out.println(howManyWays(new int[] {1, 2, 5}, 3));
    System.out.println(howManyWays(new int[] {10, 25, 100, 200}, 500));
    System.out.println(howManyWaysCombination(new int[] {1, 2, 5}, 0));
    System.out.println(howManyWaysCombination(new int[] {}, 0));
    System.out.println(howManyWaysCombination(new int[] {1, 2, 5}, 3));
    System.out.println(howManyWaysCombination(new int[] {1, 2, 5}, 8));
    System.out.println(howManyWaysCombination(new int[] {5, 2, 1}, 8));
    System.out.println(howManyWaysCombination(new int[] {3, 5, 7}, 12));
    System.out.println(howManyWaysCombination(new int[] {10, 25, 100, 200}, 500));
    System.out.println(howManyWaysCombination(new int[] {1, 5, 10, 25, 100, 200}, 500));
  }
}
