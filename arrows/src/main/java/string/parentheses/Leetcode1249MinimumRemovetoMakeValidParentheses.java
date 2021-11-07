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

package string.parentheses;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Leetcode1249MinimumRemovetoMakeValidParentheses {
  /*
      Leetcode 1249. Minimum Remove to Make Valid Parentheses

      Given a string s of '(' , ')' and lowercase English characters.
      Your task is to remove the minimum number of parentheses  '(' or ')', in any positions
      so that the resulting parentheses string is valid and return any valid string.

      Formally, a parentheses string is valid if and only if:
      It is the empty string, contains only lowercase characters, or
      It can be written as AB (A concatenated with B), where A and B are valid strings, or
      It can be written as (A), where A is a valid string.

      Input: s = "lee(t(c)o)de)"
      Output: "lee(t(c)o)de"
      Explanation: "lee(t(co)de)" , "lee(t(c)ode)" would also be accepted.

      Input: s = "a)b(c)d"
      Output: "ab(c)d"


      Input: s = "))(("
      Output: ""
      Explanation: An empty string is also valid.

      Input: s = "(a(b(c)d)"
      Output: "a(b(c)d)"


      Constraints:
      1 <= s.length <= 105
      s[i] is either'(' , ')', or lowercase English letter.
  */
  /*
  Observe:
    - before meeting (,  any )  is invalid, need remove it
    - any ) left without paired ( is invalid, need remove it
    use Stack to keep invalid parentheses index, need to know its location in string

  minimum: means only delete invalid ones
  O(N) Time and space
  */
  public String minRemoveToMakeValid(String str) {
    Stack<Integer> stack = new Stack();
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == ')') {
        if (!stack.isEmpty() && str.charAt(stack.peek()) == '(') stack.pop();
        else stack.push(i);
      } else if (str.charAt(i) == '(') stack.push(i);
    }
    StringBuilder r = new StringBuilder();
    for (int i = str.length() - 1; i >= 0; i--) {
      if (!stack.isEmpty() && stack.peek() == i) stack.pop();
      else r.append(str.charAt(i));
    }
    return r.reverse().toString();
  }
}
