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

package stack;

import java.util.Stack;

public class Leetcode856ScoreofParentheses {
  /* 856. Score of Parentheses
  Given a balanced parentheses string s, return the score of the string.

  The score of a balanced parentheses string is based on the following rule:

      "()" has score 1.
      AB has score A + B, where A and B are balanced parentheses strings.
      (A) has score 2 * A, where A is a balanced parentheses string.


  Input: s = "()"
  Output: 1

  Input: s = "(())"
  Output: 2

  Input: s = "()()"
  Output: 2

  Input: s = "(()(()))"
  Output: 6

  Constraints:
      2 <= s.length <= 50
      s consists of only '(' and ')'.
      s is a balanced parentheses string.
  */

  /*
    As s is a balanced parentheses string.  s consists of only '(' and ')'.
   Idea:
    integer stack

    let ( is -1
    if current char is (, push it in stack
    else current is ), stack top can be
     - (        : pop ( ,           now the current is number 1
     - (, number: pop number and (, now the current is 2*number
    then, now the stack top can be
     - empty  : push the number
     - (      : push the number
     - (, num : remove the num and push in num+current num

  O(N) time, space
   */
  public int scoreOfParentheses3(String str) {
    Stack<Integer> s = new Stack();
    for (int c : str.toCharArray()) {
      if (c == '(') s.push(0); // '(' is 0
      else { // ')'
        int score = 0;
        if (s.peek() == 0) { // '('
          s.pop();
          score = 1;
        } else { // number
          int inner = s.pop();
          s.pop();
          score = 2 * inner;
        }

        if (s.isEmpty() || s.peek() == 0) s.push(score);
        else s.push(s.pop() + score);
      }
    }
    return s.peek();
  }

  /*
  current layer score = current inner score*2 + previous score of this layer which  by default is 0.
  O(N) time, space
  */
  public int scoreOfParentheses2(String S) {
    Stack<Integer> s = new Stack<>(); // score
    s.push(0); // default score of current layer
    for (char c : S.toCharArray()) {
      if (c == '(') {
        s.push(0); // default score of current layer
      } else {
        int in = s.pop(); // inner
        int cur = s.pop(); // current layer
        s.push(cur + Math.max(in * 2, 1));
      }
    }
    return s.peek();
  }

  /*
   Idea:
    (pre+cur)*2 = pre*2+cur*2
    O(N) time,O(1)space
  */
  public int scoreOfParentheses(String S) {
    int r = 0, d = 0;
    char[] s = S.toCharArray();
    for (int i = 0; i < s.length; i++) {
      if (s[i] == '(') {
        d++;
      } else {
        d--;
        if (s[i - 1] == '(') {
          r += (int) Math.pow(2, d);
        }
      }
    }
    return r;
  }
}
