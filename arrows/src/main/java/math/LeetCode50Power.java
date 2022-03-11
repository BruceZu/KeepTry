//  Copyright 2017 The keepTry Open Source Project
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

public class LeetCode50Power {
  /*
  Implement pow(x, n), which calculates x raised to the power n (i.e., x^n).

  Input: x = 2.00000, n = 10
  Output: 1024.00000


  Input: x = 2.10000, n = 3
  Output: 9.26100


  Input: x = 2.00000, n = -2
  Output: 0.25000
  Explanation: 2-2 = 1/22 = 1/4 = 0.25


  Constraints:

  -100.0 < x < 100.0
  -2^31 <= n <= 2^31-1
  -10^4 <= x^n <= 10^4
    */
  /*
  Test cases:
    b=0;
    p=0;
    b=1;
    p=1;
    b is negative integer
    p is Integer.MIN_VALUE
  */

  /*
   recursion version
   O(logN) time and space, N is the pow number
  */
  public static double pow_(double b, int N) {
    long p = N;
    if (b == 0) return 0;
    if (p == 0) return 1;
    if (p < 0) {
      b = 1 / b;
      p = -p;
    }

    double r = pow_(b, (int) p >>> 1);
    r *= r;
    if ((p & 1) == 1) r *= b;
    return r;
  }

  /*
  Iterative version
    https://imgur.com/uzJSz9M.png
    https://en.wikipedia.org/wiki/Exponentiation_by_squaring

     O(log^N) time and O(1) space

   Note:
    1
      In Java for the Integer type the Integer.MIN_VALUE = -2147483648
      -(-2147483648) is still -2147483648.
      Because Integer.MAX_VALUE is 2147483647
      2147483648=2147483647+1 is -2147483648 not 2147483648
      `p = -p; ` does not apply to Integer.MIN_VALUE
      this is why `long p=n` at start;
    2
       must use >>> not >> thus
       Integer.MIN_VALUE>>1 is -1073741824
       Integer.MIN_VALUE>>>1 is 1073741824 it is same result of
       2147483648/2
  */

  public double pow__(double b, int n) {
    long p = n; // for line 74 p = -p;
    if (b == 0) return 0;
    if (p == 0) return 1; // base != 0
    if (b == 1) return 1; // base != 0, pow != 0

    if (p < 0) {
      b = 1 / b;
      p = -p; // take care
    }
    double r = 1, c = b; // c is b^x, x is the number of current b^pow in current layer
    for (long i = p; i >= 1; i >>>= 1) {
      if ((i & 1) == 1) r *= c;
      c *= c;
    }
    return r;
  }
}
