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

package stack;

import java.util.Stack;

public class Leetcode20ValidParentheses {
  /*
    20. Valid Parentheses

    Given a string s containing just the characters
     '(', ')', '{', '}', '[' and ']',
     determine if the input string is valid.

    An input string is valid if:
        Open brackets must be closed by the same type of brackets.
        Open brackets must be closed in the correct order.

    Input: s = "()" Output: true
    Input: s = "()[]{}" Output: true
    Input: s = "(]"  Output: false
    Input: s = "([)]" Output: false
    Input: s = "{[]}" Output: true
    Input: s = "(([]){})" Output: true
    Input: s = "}" Output: false

    Constraints:
        1 <= s.length <= 104
        s consists of parentheses only '()[]{}'.
  */
  /*
  Observer:
  If
     a is sum of ( as 1, ) as -1;
     b is sum of { as 1, } as -1;
     c is sum of [ as 1, ] as -1;
  Then for valid parentheses string, loop the string
  find
  -  at any char the a,b,c should >=0;
     and at last a,b,c should be 0
  -  if use any of ), },] to delete the (,{,[, then next char of any ), },]
     will also has  (,{,[t to delete. need a stack

  O(N) time and space
  */
  public boolean isValid(String s) {
    int a = 0, b = 0, c = 0;
    Stack<Character> l = new Stack<>();
    for (int r = 0; r < s.length(); r++) {
      char cur = s.charAt(r);
      if (cur == '(') {
        if (++a < 0) return false;
        l.push(cur);
      } else if (cur == '{') {
        if (++b < 0) return false;
        l.push(cur);
      } else if (cur == '[') {
        if (++c < 0) return false;
        l.push(cur);
      } else if (cur == ')') {
        if (--a < 0) return false;
        if (l.isEmpty() || l.peek() != '(') return false;
        l.pop();
      } else if (cur == '}') {
        if (--b < 0) return false;
        if (l.isEmpty() || l.peek() != '{') return false;
        l.pop();
      } else if (cur == ']') {
        if (--c < 0) return false;
        if (l.isEmpty() || l.peek() != '[') return false;
        l.pop();
      }
    }
    return a == 0 && b == 0 && c == 0;
  }

  /*
   Actually one stack is enough
   stack, subgroup, level idea, recursion, are using same rule
   O(N) time and space
  */
  public static boolean isValid2(String s) {
    char[] a = s.toCharArray();
    Stack<Character> l = new Stack();
    for (char i : a) {
      if (i == '{' || i == '[' || i == '(') l.push(i);
      else {
        if (l.isEmpty()) return false;
        char top = l.pop();
        if (!(top == '{' && i == '}' || top == '[' && i == ']' || top == '(' && i == ')'))
          return false;
      }
    }
    return l.isEmpty();
  }
}
