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

package stack;

import java.util.Stack;

public class Leetcode224BasicCalculator {
  /*
    Leetcode 224. Basic Calculator

    Given a string s representing a valid expression,
    implement a basic calculator to evaluate it,
    and return the result of the evaluation.

    Note: You are not allowed to use any built-in function which
    evaluates strings as mathematical expressions, such as eval().


    Input: s = "1 + 1"
    Output: 2


    Input: s = " 2-1 + 2 "
    Output: 3


    Input: s = "(1+(4+5+2)-3)+(6+8)"
    Output: 23


    Constraints:

    1 <= s.length <= 3 * 105
    s consists of digits, '+', '-', '(', ')', and ' '.
    s represents a valid expression.
    '+' is not used as a unary operation (i.e., "+1" and "+(2 + 3)" is invalid).
    '-' could be used as a unary operation (i.e., "-1" and "-(2 + 3)" is valid).
    There will be no two consecutive operators in the input.
    Every number and running calculation will fit in a signed 32-bit integer.
  */

  /*
  input is valid format
  each time handle one char

  */
  public int calculate(String s) {
    Stack<Integer> stack = new Stack<>();
    int num = 0;
    int ans = 0;
    int sign = 1; // 1: positive, -1: negative.

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i); // current char
      if (Character.isDigit(c)) {
        num = 10 * num + (c - '0');
      } else if (c == '+') {
        ans += sign * num; // num is ended
        //
        sign = 1;
        num = 0;

      } else if (c == '-') {
        ans += sign * num; // num is ended
        //
        sign = -1;
        num = 0;

      } else if (c == '(') {
        stack.push(ans);
        stack.push(sign);
        //
        sign = 1;
        ans = 0;
        num = 0;

      } else if (c == ')') {
        ans += sign * num; // in current ();

        ans *= stack.pop(); // previous sign
        ans += stack.pop();

        // Reset the operand
        num = 0;
      }
      // else is " ", skip it
    }
    return ans + (sign * num);
  }
}
