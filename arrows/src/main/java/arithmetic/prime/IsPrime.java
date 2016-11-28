//  Copyright 2016 The Sawdust Open Source Project
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

package arithmetic.prime;

import java.util.Arrays;

/**
 * <a href="http://www.geeksforgeeks.org/sieve-of-eratosthenes/"> Sieve of Eratosthenes </a><br>
 * <a href="https://www.mkyong.com/java/how-to-determine-a-prime-number-in-java/"> primeSieve </a>
 */
public class IsPrime {
    boolean isPrime(int n) {
        //check if n is a multiple of 2
        if (n % 2 == 0) {
            return false;
        }
        //if not, then just check the odds
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    boolean[] isPrimes = new boolean[10000];

    public void fillSieve() {
        Arrays.fill(isPrimes, true);
        isPrimes[0] = isPrimes[1] = false;       // 0 and 1 are NOT prime.
        for (int num = 2; num < isPrimes.length; num++) {
            //if the number is prime, then go through all its multiples and make their values false.
            if (isPrimes[num]) {
                for (int multiple = 2; num * multiple < isPrimes.length; multiple++) {
                    isPrimes[num * multiple] = false;
                }
            }
        }
    }

    public boolean primeSieve(int n) {
        return isPrimes[n];
    }
}
