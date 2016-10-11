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

package arithmetic.factorial;

/**
 * <pre>
 * Idea B: doing factorials with a fourth of the multiplication.
 *
 * Note
 * ArithmeticException integer overflow
 * Prime factor
 * @see <a href="https://en.wikipedia.org/wiki/Factorial"> wiki </a>
 * @see <a href="https://sites.google.com/site/examath/research/factorials"> factorials </a>
 * @see <a href="http://www.luschny.de/math/factorial/conclusions.html"> conclusions </a>
 * @see <a href="http://www.luschny.de/math/factorial/FastFactorialFunctions.htm"> Fast Factorial Functions </a>
 * <br>other:</br>
 * @see <a href="http://mishadoff.com/blog/fast-factorial/"> other </a>
 * @see <a href="http://cs.stackexchange.com/questions/14456/factorial-algorithm-more-efficient-than-naive-multiplication"> other </a>
 * @see <a href="https://gmplib.org/manual/Factorial-Algorithm.html"> other </a>
 * @see <a href="https://people.eecs.berkeley.edu/~fateman/papers/factorial.pdf"> other </a>
 * @see <a href="http://re-factor.blogspot.com/2013/11/fast-factorial.html"> other </a>
 */
public class Factorials {

    /**
     * <pre>
     * Idea A: doing only half of the multiplication.
     * Todo:  addition is quicker than multiplication?
     * 8! = 1 * 2 * 3 * 4 * 5 * 6 * 7 * 8
     * 8! = (1 * 8) * (2 * 7) * (3 * 6)  * (4 * 5)
     * 8! = (0 + 8) * (8 + 6) * (14 + 4) * (18 + 2)
     *                14        18         20
     * 9! = 9 * 8!
     */
    public static Long factorialsOf(long i) {
        if (i <= 0) {
            return null;
        }
        if (i == 1) {
            return 1l;
        }
        long r = 1l;
        if ((i & 1) == 1) {
            r *= i;
            i--;
        }

        long left = 0, right = i;
        while (right > 0) { // care
            r *= (left + right);
            long nextL = left + right; // care
            long nextR = right - 2; // care
            left = nextL;
            right = nextR;
        }
        return r;
    }

    public static long recursion(long i) {
        if (i == 1) {
            return 1;
        } else {
            return i * recursion(i - 1);
        }
    }
}
