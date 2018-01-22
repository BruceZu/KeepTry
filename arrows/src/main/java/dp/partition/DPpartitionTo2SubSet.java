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

/**
 * Dynamic Programming: Partition.
 * for a given nums, is it possible to partition it to 2 subsets with the same sum
 * if the sum of all the numbers is at most sum = 10000.
 * cons: sum < 2^64
 * sum can be as DP routine
 *
 * @see <a href="https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes">Sieve_of_Eratosthenes</a>
 * @see <a href="http://www.cs.cornell.edu/~wdtseng/icpc/notes/dp3.pdf">DP3</a>
 *
 * <pre>
 *  <A>
 *     T[x] means: if there is a subset of the numbers that has sum x then T[x]=true.
 *     If T[N/2] is true, then there is a subset that adds up to half the total sum.
 *
 *     set T[0] to true.– we can always build 0 by taking an empty set.
 *     If we have no numbers in C, then we are done!
 *
 *     T[] should have T[0] and T[C[0]] set to true.
 *     current number plus the sum of all possible subset of processed number and update the sum array
 *     Note one important detail in the j loop. It goes through the table from right to left. to avoid
 *     double­counting C[i].
 *
 * ---- concern:
 * each number should not be used more than once
 * if there are duplicated numbers?
 *
 *
 *
 *  2 3 4 1
 *
 *  0  1  2   3  4  5  6  7  8  9  10
 *  *
 *  1     *
 *  1     1   *     *
 *  1     1   1  *  1  *  *     *
 *  1  *  1   1  1  1  1  1  *  1  *
 */
public class DPpartitionTo2SubSet {
  // runtime complexity: O(nS)
  public static boolean can(int[] nums) {
    //Todo: corner case check
    int sum = Arrays.stream(nums).sum();
    if (sum % 2 != 0) return false;
    sum /= 2;
    boolean[] T = new boolean[sum + 1];
    T[0] = true;

    for (int i : nums)
      for (int j = sum - i; j >= 0; j--) {
        if (T[j]) T[j + i] = true;
      }
    return T[sum];
  }

  public static void main(String[] args) {
    System.out.println(can(new int[] {4, 6, 1}));
    System.out.println(can(new int[] {5, 3, 2, 4}));
    System.out.println(can(new int[] {7, 1}));
  }
}
