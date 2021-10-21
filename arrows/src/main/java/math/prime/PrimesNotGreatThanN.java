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
  /*
    N: 2< N < Integer.MAX_VALUE.
    This algorithm is restricted by  the max possible index Integer.MAX_VALUE
    With a boolean array to mark all number to be prime by default
    For each number 2~N.
      - if it is still marked as prime, then collect it in a list, we care the order of them.
      - use the current number mark all `product=cur number * some collected prime<=N` as false
        it is some not each collected prime: the key skill here to make sure
        each number only be mark only once, thus the
        algorithm is O(N) time.
        -- current number n may not be prime.
        -- some means: those prime <= the minor factor of current number n, not including 1
           if current number is prime, that means it will use all current prime list including itself
           else if current number not prime it will not use all current prime list
           E.g. n is 4:  2 * 4 = 8
                n is 6:  2 * 6 = 12, not by 3 * 4, 12=2*2*3
                n is 9:  2 * 9 = 18, not by 3 * 6, 18=2*3*3
         each number only be marked by its minimum factor, thus marked only once.
                12 minimum factor is 2, 2*(2*3)
                18 minimum factor is 2, 2*(3*3)
         So any current number n, n can only mark product which is p*n, and p<= minor factor of n, thus p
         is also the minor factor of the product number.
   O(N) time and space
  */
  public static List<Integer> getPrimesLessNotGreatThan(Integer N) {
    if (N == Integer.MAX_VALUE) throw new RuntimeException("No support:" + N);
    List<Integer> primes = new ArrayList<>();
    //
    boolean[] isNotPrime = new boolean[N + 1]; // default false, 'it is prime'.
    for (int n = 2; n <= N; n++) {
      if (!isNotPrime[n]) {
        primes.add(n);
      }
      // for all p <= minimum factor of current number n
      for (int p : primes) {
        if (n * p > N) {
          break; // overflow
        }
        isNotPrime[p * n] = true; // each number only be mark only once
        if (n % p == 0) {
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
