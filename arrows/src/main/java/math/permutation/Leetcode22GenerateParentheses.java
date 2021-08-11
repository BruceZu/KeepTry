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

package math.permutation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Leetcode22GenerateParentheses {
  /*
   22. Generate Parentheses
       Given n pairs of parentheses,
       write a function to generate all combinations of well-formed parentheses.

    Input: n = 3
    Output: ["((()))","(()())","(())()","()(())","()()()"]

    Input: n = 1
    Output: ["()"]

    1 <= n <= 8

  */
  /*
    Understanding
     permutation with order restriction
     any ( is same, and any ) is same.
     So use Set<string> to avoid duplicated
     it affect the runtime.
    Idea:
    insert based on pre group
  */
  public List<String> generateParenthesis2(int n) {
    Set<String> r = new HashSet();
    r.add("()");
    int size = 1;
    while (size < n) {
      size++;
      Set<String> rr = new HashSet();
      for (String s : r) {
        List<Character> l = new ArrayList();
        for (char c : s.toCharArray()) l.add(c);
        for (int i = 0; i < l.size(); i++) {
          l.add(i, '(');
          for (int j = i + 1; j < l.size(); j++) {
            l.add(j, ')');
            rr.add(toStr(l));
            l.remove(j);
          }
          l.add(')');
          rr.add(toStr(l));
          l.remove(l.size() - 1);
          l.remove(i);
        }
      }
      r = rr;
    }
    return new ArrayList(r);
  }

  private static String toStr(List<Character> l) {
    StringBuilder b = new StringBuilder();
    for (char c : l) b.append(c);
    return b.toString();
  }
  /*
  any '(' is same, and any ')' is same. so the above solution use Set<String>
  Watch:
  E.g. 3 pair valid sequence
  '((()))'       # '((()))'
  '(()())'       # '(()())'
  '(())   ()'    # '(())()'
  '()     (())'  # '()(())'
  '()     ()()'  # '()()()'
  left closer is valid and pair number is 3,2,1.
  It is continues and valid, and the right part is valid

  remove the outlet closer
  '(())'
  '()()'
  '()     ()'
  '""     (())'
  '""     ()()'

  and now the left part is the sub question result, and valid
  related right part is untouched
  2 0 ( 2 rows)
  1 1
  0 2 ( 2 rows)

  So a 3 pair valid sequence can be calculated by
  adding a closer based on 2 pre results
  It is distinct and not need Set<String>
  */
  public List<String> generateParenthesis1(int n) {
    List<String> a = new ArrayList();
    if (n == 0) a.add("");
    else {
      for (int i = 0; i < n; ++i)
        for (String l : generateParenthesis1(i))
          for (String r : generateParenthesis1(n - 1 - i)) a.add("(" + l + ")" + r);
    }
    return a;
  }

  /*
   Backtracking
    during the backtracking progress, keep track of order restriction:
    the net number of `opening brackets - closing brackets`.
    If it falls below zero at any time, or doesn't end in zero, the sequence is invalid
    otherwise it is valid.

    We can generate all $2^{2n}$ sequences of `'('` and `')'` characters.
    `n` is pair number. For each  sequences, we need to  validate the sequence, which takes O(n)  work.

   Backtracking in his scenario make sure no duplicate result so use List<String> not Set<String>
   it is a binary tree, each node has 2 child, one is ( the other is ).
   Backtracking is dfs here.

   Note Java: StringBuilder function  deleteCharAt( index )

   O(4^N/sqr(N)) time and space https://en.wikipedia.org/wiki/Catalan_number
  */

  public List<String> generateParenthesis(int n) {
    List<String> r = new ArrayList();
    backtracking(new StringBuilder(), 0, 0, n, r);
    return r;
  }

  private void backtracking(StringBuilder b, int ln, int rn, int n, List<String> r) {
    if (b.length() == 2 * n) {
      r.add(b.toString());
      return;
    }
    if (ln < n) {
      b.append("(");
      backtracking(b, ln + 1, rn, n, r);
      b.deleteCharAt(b.length() - 1);
    }
    if (rn < ln) {
      b.append(")");
      backtracking(b, ln, rn + 1, n, r);
      b.deleteCharAt(b.length() - 1);
    }
  }
}
