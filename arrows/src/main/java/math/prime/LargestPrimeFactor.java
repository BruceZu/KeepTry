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
    // d start from 2
    public static int recursion(long num, int divide) {
        while (num % divide != 0) {
            divide++;
        }
        // 1. found one factor: current divide value

        while (num % divide == 0) {
            num = num / divide;
        }
        // 2. case 3 * 3 * 7 * 11; swipe out all current factor 3 -> left  7 * 11

        if (num == 1) {
            return divide;
        }
        // 3. left 1, it is end

        return recursion(num, divide + 1);
    }

    public static int LargestPrime_Iterator(long num) {
        int r = 2;
        int divide = 2;

        while (num > 1) {
            while (num % divide == 0) {
                r = divide; //Math.max(r, divide);
                num /= divide;
            }
            divide++;
        }
        return r;
    }

    public static void main(String[] args) {
        System.out.println(LargestPrime_Iterator(600851475143l));
        System.out.println(recursion(600851475143l, 2));
        System.out.println(recursion(3 * 3 * 7 * 11, 2));
    }
}
