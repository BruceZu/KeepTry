//  Copyright 2022 The KeepTry Open Source Project
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

public class Leetcode227BasicCalculatorII {
  // string only contains `+`, `x` and digit
  // assume the str is in valid format
  public static int evaluate(String str) {
    if (str == null || str.length() == 0) return 0;
    int ans = 0, preN = 0;
    boolean preIsMul = false;
    int n = 0; // current number
    for (char c : str.toCharArray()) {
      if (Character.isDigit(c)) {
        n = n * 10 + c - '0';
      } else if (c == 'x' || c == '+') {
        if (preIsMul) {
          ans = ans - preN + preN * n;
          preN = preN * n;
        } else {
          ans = ans + n;
          preN = n;
        }
        preIsMul = c == 'x' ? true : false;
        n = 0;
      }
    }
    if (preIsMul) {
      ans = ans - preN + preN * n;
    } else {
      ans = ans + n;
    }

    return ans;
  }

  /* --------------------------------------------------------------------------
     Leetcode 227. Basic Calculator II
     Given a string s which represents an expression,
     evaluate this expression and return its value.

     The integer division should truncate toward zero.

     You may assume that the given expression is always valid.
     All intermediate results will be in the range of [-2^31, 2^31 - 1].

     Note: You are not allowed to use any built-in function which
           evaluates strings as mathematical expressions, such as eval().


     Input: s = "3+2*2"
     Output: 7


     Input: s = " 3/2 "
     Output: 1


     Input: s = " 3+5 / 2 "
     Output: 5


     Constraints:

     1 <= s.length <= 3 * 10^5
     s consists of integers and operators ('+', '-', '*', '/') separated by some number of spaces.
     s represents a valid expression.
     All the integers in the expression are non-negative integers in the range [0, 231 - 1].
     The answer is guaranteed to fit in a 32-bit integer.
  */

  /* --------------------------------------------------------------------------
  original idea uses a stack keep the partial evaluated numbers
      - negative  -a
      - product of a*b
      - positive number itself
     They have sum relationship
     it will be O(N) space

   use result(=sum+preN), preN replace the stack:

     sum + preN |  preOp | n | (current operation, index is here or at array.length)
        = result|
    Note:
     - it is + relation in sum + preN
     - preN is included in result

    Initial:
       r==0
       preN=0
       preOp is `+`

      sum + 0 `+` firstN
        r = 0

    O(N) time
    O(1) space
  */
  public int calculate__(String s) {
    if (s == null || s.isEmpty()) return 0;
    int N = s.length();
    int r = 0, preN = 0, n = 0;
    char preOp = '+';
    for (int i = 0; i < N; i++) {
      char c = s.charAt(i);
      if (Character.isDigit(c)) {
        n = n * 10 + (c - '0');
      } else if (c == '+' || c == '-' || c == '*' || c == '/') {
        if (preOp == '*') {
          r = r - preN + preN * n;
          preN = preN * n;
        } else if (preOp == '/') {
          r = r - preN + preN / n;
          preN = preN / n;
        } else if (preOp == '+') {
          r += n;
          preN = n;
        } else if (preOp == '-') {
          r -= n;
          preN = -n; // note here. it is + relation: x + preN= result
        }
        preOp = c;
        n = 0;
      }
    }
    if (preOp == '*') {
      r = r - preN + preN * n;
    } else if (preOp == '/') {
      r = r - preN + preN / n;
    } else if (preOp == '+') {
      r += n;
    } else if (preOp == '-') {
      r -= n;
    }

    return r;
  }
  // reduce the duplicate code
  public int calculate_(String s) {
    if (s == null || s.isEmpty()) return 0;
    int N = s.length();
    int r = 0, preN = 0, n = 0;
    char preOp = '+';
    for (int i = 0; i < N; i++) {
      char c = s.charAt(i);
      if (Character.isDigit(c)) {
        n = n * 10 + (c - '0');
      }
      if (c == '+' || c == '-' || c == '*' || c == '/' || i == N - 1) {
        if (preOp == '*') {
          r = r - preN + preN * n;
          preN = preN * n;
        } else if (preOp == '/') {
          r = r - preN + preN / n;
          preN = preN / n;
        } else if (preOp == '+') {
          r += n;
          preN = n;
        } else if (preOp == '-') {
          r -= n;
          preN = -n; // note here
        }
        preOp = c;
        n = 0;
      }
    }
    return r;
  }

  /* --------------------------------------------------------------------------
    not tracking result, instead tracking sum.
    https://imgur.com/XeLw0qC

    sum + preN |  preOp | n | (current operation, index is here or at array.length)
    Initial:
         sum==0
         preN=0
         preOp is `+`

         sum + 0 `+` firstN


   O(N) time
   O(1) space
  */
  public int calculate(String s) {
    if (s == null || s.isEmpty()) return 0;

    int N = s.length();
    int sum = 0, preN = 0, n = 0;
    char preOp = '+';
    for (int i = 0; i < N; i++) {
      char c = s.charAt(i);
      if (Character.isDigit(c)) {
        n = n * 10 + (c - '0');
      }

      if (c == '+' || c == '-' || c == '*' || c == '/' || i == N - 1) {
        // `x` or `/` : merge current n with preN
        if (preOp == '*') {
          preN = preN * n;
        } else if (preOp == '/') {
          preN = preN / n;
        } else if (preOp == '+' || preOp == '-') {
          sum += preN; // `+` or `-`: merge preN with ans, update preN using n and preOp
          preN = (preOp == '+') ? n : -n;
        }
        preOp = c;
        n = 0;
      }
    }
    sum += preN;
    return sum;
  }

  // ---------------------------------------------------------------------------
  public static void main(String[] args) {
    System.out.println(evaluate("1+2") == 3);
    System.out.println(evaluate("1x2+3+4") == 9);
    System.out.println(evaluate("1x2+3+4+6x6") == 45);
    System.out.println(evaluate(null) == 0);
    System.out.println(evaluate("5") == 5);
    System.out.println(evaluate("5x2x10") == 100);
  }
}
