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

package arithmetic;

/**
 * <pre>
 * try to divided by from 2, (each denoted by 1), up to sqrt(n),
 * till you find one, divide out and start again.
 * In the end you will have a number that you cannot do this process to.
 * This will be the largest prime factor.
 * The left is 1.
 * test case:
 *
 * 600851475143l -> 6857
 *
 * @see <a href="https://en.wikipedia.org/wiki/Integer_factorization#Factoring_algorithms">wiki</a>
 */
public class LargestPrimeFactor {
    public static int recursion(long n, int d) {
        while (n % d != 0) {
            d++;
        }
        while (n % d == 0) {
            n = n / d;
        }
        if (n == 1) {
            return d;
        }
        return recursion(n, d + 1);
    }

    public static int iterator(long n) {
        int max = 2;
        int d = 2;
        while (n > 1) {
            while (n % d == 0) {
                max = Math.max(max, d);
                n /= d;
            }
            d++;
        }
        return max;
    }
}
