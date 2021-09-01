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

package string;

import java.util.*;

public class Leetcode65ValidNumber {
  /*
  65. Valid Number
     A valid number can be split up into these components (in order):
          decimal|integer [ 'e' |'E' integer]

     A decimal number can be split up into these components (in order):
         ['+' | '-'] [digit(s) '.' | digit(s) '.' digit(s) | '.' digit(s)]

     An integer can be split up into these components (in order):
        ['+' | '-'] [digit(s)]

     For example, all the following are valid numbers:
      ["2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789"],
      while the following are not valid numbers:
      ["abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53"].

     Given a string s, return true if s is a valid number.

     Input: s = "0" Output: true
     Input: s = "e" Output: false
     Input: s = "." Output: false
     Input: s = ".1" Output: true
     Input: s = 1e4.5 Output: false
     Input: s =  .9. Output: false

     Constraints:
         1 <= s.length <= 20
         s consists of only English letters (both uppercase and lowercase), digits (0-9), plus '+', minus '-', or dot '.'.
   */

  /*---------------------------------------------------------------------------
  Idea:
   Observe:
   For any valid current char check status:
   1. validate neighbor
   2. condition:
   0-9: at least one digit in the input
   +|-: need right valid neighbor exist, +|- can also appear after 'e|E'.
   .:   only before e|E, e.g.: `1e4.5`;  need at least one neighbor is digit, e.g. '.'.
   e|E: require both valid neighbor exist,  left side must has digit sign

   3. count limitation:
    - '.'  : [0,1] e.g.: `.9.` false
    - '+|-': [0,2]
    - 'E|e': [1]   e.g.: `10e3E5` false

    O(N) time O(1) space
   */
  public static boolean isNumber__(String s) {
    boolean fE = false; // has found 'E|e'
    int dots = 0, signs = 0, es = 0; // number of '.', '+|-', 'E|e'
    boolean l, r;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c == '+' || c == '-') {
        l = true;
        r = false;
        if (i != 0) {
          l = false;
          char p = s.charAt(i - 1); // previous char
          if (p == 'e' || p == 'E') l = true;
        }
        if (i + 1 < s.length()) {
          char f = s.charAt(i + 1); // following char
          if ('0' <= f && f <= '9' || f == '.') r = true;
        }
        if (!l || !r) return false;
        if (++signs > 2) return false;

      } else if (c == '.') {
        if (fE) return false;
        l = true;
        r = true;
        boolean dl = false, dr = false; // is left and right neighbor digit?
        if (i > 0) {
          l = false;
          char p = s.charAt(i - 1);
          if ('0' <= p && p <= '9' || p == '-' || p == '+') l = true;
          if ('0' <= p && p <= '9') dl = true;
        }
        if (i + 1 < s.length()) {
          r = false;
          char f = s.charAt(i + 1);
          if ('0' <= f && f <= '9' || f == 'e' || f == 'E') r = true;
          if ('0' <= f && f <= '9') dr = true;
        }
        if (!l || !r) return false;
        if (++dots > 1) return false;
        if (!dl && !dr) return false;

      } else if ('0' <= c && c <= '9') {
        l = true;
        r = true;
        if (i > 0) {
          l = false;
          char p = s.charAt(i - 1);
          if (p == '+' || p == '-' || p == 'e' || p == 'E' || p == '.') l = true;
        }
        while (i + 1 < s.length() && '0' <= s.charAt(i + 1) && s.charAt(i + 1) <= '9') i++;
        if (i + 1 < s.length()) {
          r = false;
          char f = s.charAt(i + 1);
          if (f == '.' || f == 'e' || f == 'E') r = true;
        }
        if (!l || !r) return false;
      } else if (c == 'e' || c == 'E') {
        fE = true;
        l = false;
        r = false;
        if (i > 0) {
          char p = s.charAt(i - 1);
          if (p == '.' || '0' <= p && p <= '9') l = true;
        }
        if (i + 1 < s.length()) {
          char f = s.charAt(i + 1);
          if (f == '-' || f == '+' || f >= '0' && f <= '9') {
            r = true;
          }
        }
        if (!l || !r) return false;
        if (++es == 2) return false;
      } else return false;
    }
    return true;
  }
  /*---------------------------------------------------------------------------
   Concise version
   The less condition set
   - '.'   : at most one, must before e|E.
             need `findExp` and `findDot`
   - '+|-' : if not the first char, then must follow e|E. e.g.:  8e-.
   - 0-9   : mark find Digit
   - 'e|E' : both side should have digit.
             need `findDigit`
   - At least there is one digit.

   4 group of valid char. 3 boolean variable( not need 'findSign`).
   O(N) time O(1) space
  */
  public boolean isNumber_(String s) {
    boolean digit = false, ex = false, dot = false;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (Character.isDigit(c)) {
        digit = true;
      } else if (c == '+' || c == '-') {
        if (i > 0 && s.charAt(i - 1) != 'e' && s.charAt(i - 1) != 'E') return false;
        // number of sign controlled by exponent
      } else if (c == 'e' || c == 'E') {
        if (ex || !digit) return false;
        ex = true;
        digit = false; // reset
      } else if (c == '.') {
        if (dot || ex) return false;
        dot = true;
      } else return false;
    }
    return digit;
  }

  /*---------------------------------------------------------------------------
       Deterministic Finite Automaton (DFA)
       - build the state graph:  initial statue (node), via possible **valid** options,
         is translated (**edge**) to a new statue, continue this operation. If the statue space is finite.  Then it works.
       - loop input along the state graph like Trie searching.
       - Once the loop end check the last state to decide the answer.

       Details:
       initial state is 0 with empty string "".
       Any next char is not valid or be any of
       - sign : `+/1`
       - digit: `0-9`
       - dot  : `.`
       - exponent: 'e|E'
       If it does not break the rule of valid number to append the next char, then current
       state is translated to another state.
       the state number is finite caused by the rule restriction of valid number.

       For any input string, loop each char along the states graph like trie searching.
       After that if the last state is of valid number state, then the answer is true.
       Not all state is of valid number state.


       Note The number of state 8 is not decided by the number of variables:
       hasExponent, hasDigit, hasDot, 2^3=8. So the state cannot be identified
       by the composition of 3 variables. 2 statues may have the same variable
       value. e.g. the following state 6 and 7

       - digit: `0-9`
       - sign : `+/-`
       - dot  : `.`
       - exponent: 'e|E'

       String    state    try 1/4 type   translated state
       ""         0        digit: `0-9`      `0-9`  1( integer)
       ""         0        sign : `+/-`      `+/-`  2
       ""         0        dot  : `.`        `.`    3
       ""         0        exponent: 'e|E'    x(String starts with e|E is invalid number)

       `0-9`      1        digit: `0-9`      `0-9`     1( integer)
       `0-9`      1        sign : `+/-`       x (`+/-` only at index 0 or after `e|E`.)
       `0-9`      1        dot  : `.`        `9.`      4 (decimal)
       `0-9`      1        exponent: 'e|E'   1->`9e`   5

        `+/-`     2        digit: `0-9`       0->`9`，2->`+/-9`  1( integer)
        `+/-`     2        sign : `+/-`       x (`+/-` only at index 0 or after `e|E`.)
        `+/-`     2        dot  : `.`         0->`.`,2->`+/-.`      3
        `+/-`     2        exponent: 'e|E'    x ('e|E' need a digit(decimal or int) left to it)

        `.`       3        digit: `0-9`       1->`9.`,3->`.9`   4 (decimal)
        `.`       3        sign : `+/-`       x (`+/-` only at index 0 or after `e|E`.)
        `.`       3        dot  : `.`         x ('.' at most 1)
        `.`       3        exponent: 'e|E'    x ('e|E' need a digit(decimal or int) left to it)


   1->`9.`,3->`.9` 4      digit: `0-9`       4->`9.9`,`.99`          4 (decimal)
   1->`9.`,3->`.9` 4      sign : `+/-`       x (`+/-` only at index 0 or after `e|E`.)
   1->`9.`,3->`.9` 4      dot  : `.`         x ('.' at most 1)
   1->`9.`,3->`.9` 4      exponent: 'e|E'    4->`9.E`,`.9E`,1->`9e`  5

   4->`9.E`,`.9E`,1->`9e`  5    digit: `0-9`       5->`9.E9`,`.9E9`,`9E9`   7
   4->`9.E`,`.9E`,1->`9e`  5    sign : `+/-`       5->`9.E-`,`.9E-`,`9E-`   6
   4->`9.E`,`.9E`,1->`9e`  5    dot  : `.`         x ('.' only before e|E)
   4->`9.E`,`.9E`,1->`9e`  5    exponent: 'e|E'    x ('e|E' at most 1)

   5->`9.E-`,`.9E-`,`9E-`   6     digit: `0-9`      6->`9.E-9`,`.9E-9`,`9E-9`,  5->`9.E9`,`.9E9`,`9E9`   7
   5->`9.E-`,`.9E-`,`9E-`   6     sign : `+/-`      x (`+/-` only at index 0 or after `e|E`.)
   5->`9.E-`,`.9E-`,`9E-`   6     dot  : `.`        x ('.' only before e|E)
   5->`9.E-`,`.9E-`,`9E-`   6     exponent: 'e|E'   x ('e|E' at most 1)

   6->`9.E-9`,`.9E-9`,`9E-9`,  5->`9.E9`,`.9E9`,`9E9`   7     digit: `0-9`      7->`9.E-99`,`.9E-99`,`9E-99`,`9.E90`,`.9E90`,`9E90`   7
   6->`9.E-9`,`.9E-9`,`9E-9`,  5->`9.E9`,`.9E9`,`9E9`   7     sign : `+/-`      x (`+/-` only at index 0 or after `e|E`.)
   6->`9.E-9`,`.9E-9`,`9E-9`,  5->`9.E9`,`.9E9`,`9E9`   7     dot  : `.`        x ('.' only before e|E)
   6->`9.E-9`,`.9E-9`,`9E-9`,  5->`9.E9`,`.9E9`,`9E9`   7     exponent: 'e|E'   x ('e|E' at most 1)

   observe: 7 `9.E-99`,`.9E-99`,`9E-99`,`9.E90`,`.9E90`,`9E90`,`-9.E-99`,`-.9E-99`,`-9E-99`,`-9.E90`,`-.9E90`,`-9E90`
            6 `9.E-`,`.9E-`,`9E-`,`-9.E-`,`.-9E-`,`-9E-`
            5 `9.E`,`.9E`,`9e`,`-9.E`,`-.9E`,`-9e`
            4 `9.9`,`.99`,`-9.9`,`-.99` (decimal)
            3 `.`, `+/-.`
            2  `+/-`
            1  `9` (int)
            0  ''
   Only state 1, 4 ,7 represent a valid number

   any state eventually reach state 7 and can not extend new state from there
   So Deterministic Finite Automaton (DFA) works in this problem
   Summary of above state translation
    state   via=>out
    0      digit: `0-9`=>   1, sign : `+/-`=> 2, dot: `.`=> 3
    1      digit: `0-9`=>   1, dot  : `.`=> 4,  exponent: 'e|E'=> 5
    2      digit: `0-9` =>  1, dot  : `.`=> 3
    3      digit: `0-9`=>   4
    4      digit: `0-9`=>   4, exponent: 'e|E'=> 5
    5      digit: `0-9`=>   7, sign : `+/-`=> 6
    6      digit: `0-9`=>   7
    7      digit: `0-9`=>   7

    O(N) time O(1) space
  */
  private static final List<Map<String, Integer>> dfa = new ArrayList<>();
  private static final Set<Integer> expected;

  static {
    expected = new HashSet<>();
    expected.addAll(Arrays.asList(1, 4, 7));

    dfa.add(0, new HashMap());
    dfa.get(0).put("digit", 1);
    dfa.get(0).put("sign", 2);
    dfa.get(0).put("dot", 3);
    dfa.add(1, new HashMap());
    dfa.get(1).put("digit", 1);
    dfa.get(1).put("dot", 4);
    dfa.get(1).put("exponent", 5);
    dfa.add(2, new HashMap());
    dfa.get(2).put("digit", 1);
    dfa.get(2).put("dot", 3);

    dfa.add(3, new HashMap());
    dfa.get(3).put("digit", 4);

    dfa.add(4, new HashMap());
    dfa.get(4).put("digit", 4);
    dfa.get(4).put("exponent", 5);

    dfa.add(5, new HashMap());
    dfa.get(5).put("sign", 6);
    dfa.get(5).put("digit", 7);

    dfa.add(6, new HashMap());
    dfa.get(6).put("digit", 7);

    dfa.add(7, new HashMap());
    dfa.get(7).put("digit", 7);
  }

  public boolean isNumber___(String s) {
    int state = 0;
    String t = "";

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (Character.isDigit(c)) t = "digit";
      else if (c == '+' || c == '-') t = "sign";
      else if (c == 'e' || c == 'E') t = "exponent";
      else if (c == '.') t = "dot";
      else return false;

      if (!dfa.get(state).containsKey(t)) return false;
      state = dfa.get(state).get(t);
    }
    return expected.contains(state);
  }

  /*---------------------------------------------------------------------------
  Watch:
  valid number:     decimal|integer [ 'e' |'E' integer]
  decimal number:   ['+' | '-'] [digit(s) '.' | digit(s) '.' digit(s) | '.' digit(s)]
  integer:          ['+' | '-'] [digit(s)]

  Note
  - 'e123e123e'  and '123e123'
  - '+'
  - decimal and integer must contain at least one digit.
  - optional sign: if one is present, must be the first character.
  - exponent ("e" or "E") should not on any side
  - A decimal number should only contain one dot.

  O(n) time O(n) space
  */
  public boolean isNumber(String s) {
    s = s.toLowerCase();
    if (s.startsWith("e") || s.endsWith("e")) return false;
    String[] half = s.split("e");
    if (half.length > 2 || half.length < 1) return false;
    boolean l = isInt(half[0]) || isDeci(half[0]);
    if (half.length == 1) return l;
    if (!l) return false;
    return isInt(half[1]);
  }

  private boolean isDeci(String s) {
    char[] a = s.toCharArray();
    int dots = 0;
    boolean hasDigit = false;
    int l = s.charAt(0) == '+' || s.charAt(0) == '-' ? 1 : 0;
    for (; l < s.length(); l++) {
      if (a[l] == '.') dots++;
      else if (Character.isDigit(a[l])) hasDigit = true;
      else return false;
    }
    return dots == 1 && hasDigit;
  }

  private boolean isInt(String s) {
    char[] a = s.toCharArray();
    boolean hasDigit = false;
    int l = s.charAt(0) == '+' || s.charAt(0) == '-' ? 1 : 0;
    for (; l < s.length(); l++) {
      if (Character.isDigit(a[l])) hasDigit = true;
      else return false;
    }
    return hasDigit;
  }
}
