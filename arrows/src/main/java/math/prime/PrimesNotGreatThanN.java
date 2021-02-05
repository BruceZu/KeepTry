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

package math.prime;

import java.util.ArrayList;
import java.util.List;

public class PrimesNotGreatThanN {

  /**
   * @param N: 2< N < Integer.MAX_VALUE.
   *     <p>Runtime complexity: O(N)
   */
  public static List<Integer> getPrimesLessNotGreatThan(Integer N) {
    List<Integer> primes = new ArrayList<>();
    // This algorithm is restricted by the the max possible index Integer.MAX_VALUE
    boolean[] isNotPrime = new boolean[N + 1]; // marks, default false, 'it is prime'.
    for (int n = 2; n <= N; n++) { // each number be handle only once
      if (!isNotPrime[n]) {
        primes.add(n);
      }
      // No matter current n is prime or not,E.g.: 8 = 2 * 4
      // Use factor i, i is all primes <= minimum factor of current number n, to mark i * n as 'not
      // prime'
      for (int prime : primes) {
        if (prime * n > N) {
          break; // over flow the isNotPrime[]
        }
        isNotPrime[prime * n] = true; // each number only be mark only once
        if (n % prime == 0) {
          // current prime is the minimum factor of n and prime * n. E.g.: 8 = 2 * 4
          break;
        }
      }
    }
    return primes;
  }

  public static void main(String[] args) {
    // 46337 is the biggest prime < Math.sqrt(Integer.MAX_VALUE)
    int n = (int) Math.sqrt(Integer.MAX_VALUE);
    List<Integer> primes = getPrimesLessNotGreatThan((int) Math.sqrt(Integer.MAX_VALUE));
    System.out.println(primes.get(primes.size() - 1) == 46337);
  }
}
