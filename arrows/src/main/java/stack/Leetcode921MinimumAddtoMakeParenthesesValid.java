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

public class Leetcode921MinimumAddtoMakeParenthesesValid {
  /*
    921. Minimum Add to Make Parentheses Valid
    A parentheses string is valid if and only if:

        empty string,
        It can be written as AB (A concatenated with B), where A and B are valid strings, or
        It can be written as (A), where A is a valid string.

    You are given a parentheses string s.
    In one move, you can insert a parenthesis at any position of the string.
    For example, if s = "()))", you can insert an opening parenthesis to be
    "(()))" or a closing parenthesis to be "())))".

    Return the minimum number of moves required to make s valid.


    Input: s = "())"
    Output: 1

    Input: s = "((("
    Output: 3


    Input: s = "()"
    Output: 0


    Input: s = "()))(("
    Output: 4

    Constraints:
        1 <= s.length <= 1000
        s[i] is either '(' or ')'.
  */

  /*
   Idea:
     who firstly appear?
      -')': required to make valid
      -'(': next neighbor ')' can pair it.
            next neighbor '(' can increase the number of it, but can be redundant.
  */
  public int minAddToMakeValid(String S) {
    int r = 0, l = 0;
    for (int i = 0; i < S.length(); ++i) {
      if (S.charAt(i) == '(') l++;
      else { // ')'
        if (l > 0) l--;
        else r++;
      }
    }

    return l + r;
  }
}
