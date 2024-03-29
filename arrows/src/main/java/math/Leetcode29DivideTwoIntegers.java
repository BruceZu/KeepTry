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

package math;

public class Leetcode29DivideTwoIntegers {
  /*
      29. Divide Two Integers

    Given two integers dividend and divisor,
    divide two integers without using multiplication, division, and mod operator.

    Return the quotient after dividing dividend by divisor.

    The integer division should truncate toward zero, which means losing
    its fractional part. For example, truncate(8.345) = 8 and truncate(-2.7335) = -2.

    Note: Assume we are dealing with an environment that could only
    store integers within the 32-bit signed integer range: [−231, 231 − 1].
    For this problem, assume that your function returns 231 − 1 when the division
    result overflows.


    Input: dividend = 10, divisor = 3
    Output: 3
    Explanation: 10/3 = truncate(3.33333..) = 3.

    Input: dividend = 7, divisor = -3
    Output: -2
    Explanation: 7/-3 = truncate(-2.33333..) = -2.

    Input: dividend = 0, divisor = 1
    Output: 0

    Input: dividend = 1, divisor = 1
    Output: 1

    Constraints:

        -231 <= dividend, divisor <= 231 - 1
        divisor != 0

  */
  /* Understanding
    "without using multiplication, division, and mod operator.
    Assume we are dealing with an environment that could only store
    integers within the 32-bit signed integer range: [−2^31, 2^31 − 1]"
    So
     - do not use long type and do not use overflow
     - do not use abs which also cause overflow
   Note 'divisor != 0'
  */

  /*
   test cases:
   defend:
   1.  divisor: 0
   special:
   1.  one of dividend and divisor is positive, another is negative
   2.  dividend is 0
   3.  -2^31/-1
   4.  -2^31/1,
   5.  -1/-2^31
   general: 3/9,  9/7.2
  */

  /*
  Idea:
  1 check result division/quotient sign
  2 process special cases: dividend or divisor is 0
  3 process Integer.MIN_VALUE
    - divisor is Integer.MIN_VALUE which is always is negative.
    - dividend is Integer.MIN_VALUE:
         consider the value of divisor:  = or > Integer.MIN_VALUE
         return MAX when the divisor is -1 (special case and special requirement).
         return MIN when the divisor is 1 (special case, but the following algorithm
                                          can handle it. The cumulative time is kept
                                          in an int type variable which can keep -2^31.
                                          MAX+1 is MIN and -MIN is still MIN, but this
                                          logic is not intuitive)
         Now the times variable `r`  can work, Algorithm is just:
          - let divisor be a negative number if it is not.
          - cumulate divisor one by one when divisor is a negative number dividend< divisor.
            else check if it == dividend
            the valid times' value.
  4 left cases where dividend or divisor is not Integer.MIN_VALUE, and it is possible
    to convert both values to be negative, thus reuse the above code and logic

  O(dividend/divisor) time and O(1) space
  Improvement:
     - cumulate divisor one by one 'cum=cum+divisor` => cum+=cum
       O(O(logd)*O(log_dr^d)) time and O(1) space, dr is Divisor, d is dividend
     - avoid duplicated calculation of cumulated dr and times by step back
       from the reached largest value of them with binary wise operation/Bit-Shifting
       O(log_dr^d)) time and O(1) space.

  */
  public int divide_(int dividend, int divisor) {
    int d = dividend, dr = divisor;
    boolean isNegtive = false;
    if ((d >>> 31) + (dr >>> 31) == 1) isNegtive = true;
    // 1. 0
    if (dr == 0) return Integer.MAX_VALUE; // 'divisor != 0'
    if (d == 0) return 0; // covered
    // 2. dividend is Integer.MIN_VALUE and divisor is -1, returns 2^31 − 1
    if (dr == Integer.MIN_VALUE) return d == Integer.MIN_VALUE ? 1 : 0; // covered
    if (d == Integer.MIN_VALUE && dr == -1) return Integer.MAX_VALUE;
    if (d == Integer.MIN_VALUE && dr == 1) return Integer.MIN_VALUE; // covered  but overflow

    // 3. convert dividend and divisor to be negative number.
    //    Integer.MIN_VALUE which is always negative
    d = d > 0 ? -d : d;
    dr = dr > 0 ? -dr : dr;
    if (d == dr) return isNegtive ? -1 : 1;
    if (dr < d) return 0;
    // d < dr
    int r = 0; // division/quotient
    int t = 1, c = dr; // times and value of cumulated dr
    while (d <= (c << 1) && (c << 1) < 0) {
      // both are negative, note if d==dr still need run once e.g. d=-3, dr=-1
      t <<= 1; //   O(logN)
      c <<= 1;
    }
    while (d <= dr) { // both are negative.  O(logN)
      r += -t; // use negative value thus r can cover MIN
      d = d - c;
      while (c < d && d <= dr) {
        t >>>= 1;
        c >>= 1; // do not use >>>, instead use >> to take the sign
      }
    }
    return isNegtive ? r : -r;
  }

  public int divide_NoComment(int d, int dr) {
    boolean isNegtive = false;
    if ((d >>> 31) + (dr >>> 31) == 1) isNegtive = true;
    if (dr == 0) return Integer.MAX_VALUE;
    if (d == Integer.MIN_VALUE && dr == -1) return Integer.MAX_VALUE;
    d = d > 0 ? -d : d;
    dr = dr > 0 ? -dr : dr;
    if (d == dr) return isNegtive ? -1 : 1;
    if (dr < d) return 0;
    int r = 0;
    int t = 1, c = dr;
    while (d <= (c << 1) && (c << 1) < 0) {
      t <<= 1;
      c <<= 1;
    }
    while (d <= dr) { // both are negative.  O(logN)
      if (d <= c) {
        r += -t; // use negative value thus r can cover MIN
        d = d - c;
      }
      t >>>= 1;
      c >>= 1; // do not use >>>, instead use >> to take the sign as c is negative
    }
    return isNegtive ? r : -r;
  }

  /*
  Dividend ÷ divisor = quotient
  Idea:
      Binary Long Division
       10000b      a(16)
          11b      b(3)
       1> b -> 1100b; m=2
          10000b   a
           1100b   b
       2> r=0; r=1<<m=100b
          a=a-b=100b a
                110b <=1100 b  m=1
                 11b <=110  b  m=0
          r+= 1<<m=101b

          a=a-b=  1b a
                  1b <= 11b b m=-1 stop
         so r=101b(5)=16/3

      O(log_b^a)) time and O(1) space. b is Divisor, a is dividend
  */

  public static int divide(int a, int b) { // a/b=?
    if (b == 0) return Integer.MAX_VALUE; // 'divisor != 0'
    // Special cases: overflow.
    if (a == Integer.MIN_VALUE && b == -1) return Integer.MAX_VALUE;
    if (a == Integer.MIN_VALUE && b == 1) return Integer.MIN_VALUE;

    boolean isNegtive = false; // result sign
    if ((a >>> 31) + (b >>> 31) == 1) isNegtive = true;

    if (a > 0) a = -a;
    if (b > 0) b = -b;

    int m = 0;
    while (a <= (b << 1) && (b << 1) < 0) {
      m++;
      b <<= 1;
    }
    // Binary Long Division
    int r = 0; // quotient=Dividend ÷ divisor
    while (m >= 0) {
      if (a <= b) {
        r += 1 << m;
        a -= b;
      }
      b >>= 1;
      m--;
    }
    return isNegtive ? -r : r;
  }
}
