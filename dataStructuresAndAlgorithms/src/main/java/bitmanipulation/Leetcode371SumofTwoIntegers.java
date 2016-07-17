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

package bitmanipulation;

/**
 * <pre>
 * Calculate the sum of two integers a and b, but you are not allowed to use the operator + and -.
 *
 * Example:
 * Given a = 1 and b = 2, return 3.
 *
 * Company Tags Hulu
 * Tags Bit Manipulation
 * Similar Problems
 * =============================================================================================
 * 1> see numbers in 2D map of columns from 31 to 0.
 *
 * sum on each column of both numbers:
 *                         carry
 *   when         means    1   0
 *
 * & both == 1:   2 '1'    3   2
 * | both == 1:   1 '1'    2   1
 * | both == 0:   0 '1'    1   0
 *
 * Note: <strong>update carry in loop;</strong>
 *
 * 2>  watch <strong>each bit column</strong> of numbers directly in binary:
 *          0  1  0  1
 *          1  1  0  0
 *          -----------
 * sum       1  0  0  1
 * carry      <-1
 *
 *      0  1  0  0   1
 *      0  1
 *      -----------
 * sum  0  0  0  0   1
 * carry  <-1
 *
 * sum  1  0  0  0   1
 * carry 0  0  0  0   0
 *
 *    on each column:
 *        ^ both -> result on sum on this column
 *        & both -> value of carry, from this column to left column.
 *
 * 3> subtract  a  - b  =  a + (~b +1)
 *              a  + b  =  a - (-b)
 *     watch <strong>each bit column</strong> of numbers directly in binary:
 *
 *     a -b.  each bit column: if the value of a and b are same, the result is 0; got this by a^b.
 *                         else, the result is 1. but not sure who need borrow 1 bit from left column.
 *
 *        a      1  1  0  0
 *        b      0  1  0  1
 *        -----------------
 *     a^b       1  0  0  1
 *               |        |
 * need borrow:  no       yes
 *
 *     can not get the borrow on each bit column directly by ^,&,|.
 *     the borrow should be
 *               0  0  0  1.
 *
 *        a      1  1  0  0
 *        b      0  1  0  1
 *
 *     need translate:
 *        ~a     0  0  1  1 works by borrow = ~a & b
 *        b      0  1  0  1
 *
 *        a      1  1  0  0
 *        ~b     1  0  1  0
 *
 *        a-1    1  0  1  1 works but when it is a'
 *        b      0  1  0  1
 *
 *        a'     0  0  1  1
 *        b      0  1  0  1
 *                  |
 *
 *        a'-1   0  0  1  0
 *        b      0  1  0  1
 *                  |
 *
 *        ~a'    1  1  0  0 works.
 *        b      0  1  0  1
 *
 *
 *       so till b !=0
 *         next a = a^b;
 *              b = (~a & b) <<1;
 */
public class Leetcode371SumofTwoIntegers {
    public static int getSum2(int a, int b) {
        int carry = 0;
        int sum = 0;
        for (int i = 0; i <= 31; i++) {
            int ca = a >>> i & 1; // column bit of a
            int cb = b >>> i & 1;
            if ((ca & cb) == 1 && carry == 1) {
                sum |= 1 << i;
            } else if ((ca & cb) == 1 && carry == 0 || (ca | cb) == 1 && carry == 1) {
                carry = 1;
            } else if ((ca | cb) == 1 && carry == 0 || (ca | cb) == 0 && carry == 1) {
                sum |= 1 << i;
                carry = 0;
            }
        }
        return sum;
    }

    public int getSum(int a, int b) {
        while (b != 0) {
            int nextA = a ^ b;
            int carry = a & b;

            a = nextA;
            b = carry << 1;
        }
        return a;
    }

    public int getSubtract(int a, int b) {
        while (b != 0) {
            int nexta = a ^ b;
            int borrow = (~a) & b;

            a = nexta;
            b = borrow << 1;
        }
        return a;
    }

    public int getProduct(int a, int b) {
        if (a == 0) return 0;
        int result = 0;
        while (b != 0) {
            if ((b & 1) != 0) {// b is odd
                result = getSum(a, result);
            }
            // b is even, axb = ax2xb/2
            a <<= 1;
            b >>>= 1;
        }
        return result;
    }
}
