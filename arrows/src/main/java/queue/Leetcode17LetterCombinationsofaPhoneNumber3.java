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

package queue;

import java.util.*;

/*
Leetcode 17. Letter Combinations of a Phone Number

Given a string containing digits from 2-9 inclusive,
return all possible letter combinations that the number could represent. Return the answer in any order.

A mapping of digit to letters (just like on the telephone buttons)
is given below.
Note that 1 does not map to any letters.

Example 1:

Input: digits = "23"
Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
Example 2:

Input: digits = ""
Output: []
Example 3:

Input: digits = "2"
Output: ["a","b","c"]


Constraints:

0 <= digits.length <= 4
digits[i] is a digit in the range ['2', '9'].

 */
/*
all digit mapped letter will take part in the result.
each result' length is same as the input's length.
Using queue to replace backtracking

(?) time and space
*/
public class Leetcode17LetterCombinationsofaPhoneNumber3 {
  // assume the input 'digits' is composed by char '0'~'9'
  public static List<String> letterCombinations(String digits) {
    // the 0 and 1 maps to no letters, so using empty string here
    String[] map = new String[] {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    LinkedList<String> q = new LinkedList<>();
    if (digits == null || digits.length() == 0) return q; // []

    // input is "0", "1" or "01", expected answer is [] too
    for (int i = 0; i < digits.length(); i++) {
      String cs = map[digits.charAt(i) - '0'];
      if (cs.length() == 0) continue; // letters of digit 0 or 1, no contribution to result.

      if (q.size() == 0) {
        for (int j = 0; j < cs.length(); j++) {
          q.addLast(cs.charAt(j) + "");
          // append to the end. for the first one, e.g."abc" will be "a", "b", "c" 3 strings
        }
      } else {
        int size = q.size();
        while (size-- >= 1) {
          String s = q.remove(); // remove first. queue method
          for (int j = 0; j < cs.length(); j++) {
            q.add(s + cs.charAt(j)); // append to the end
          }
        }
      }
    }
    return q;
  }
  /*
  backtracking
   time
  O (1) space
   */

  private List<String> a = new ArrayList<>();
  private Map<Character, String> map = new HashMap<>();
  private String digits;

  public List<String> letterCombinations_backtracking(String digits) {
    if (digits.length() == 0) return a;

    this.digits = digits;
    map.put('2', "abc");
    map.put('3', "def");
    map.put('4', "ghi");
    map.put('5', "jkl");
    map.put('6', "mno");
    map.put('7', "pqrs");
    map.put('8', "tuv");
    map.put('9', "wxyz");
    backtrack(0, new StringBuilder());
    return a;
  }

  private void backtrack(int i, StringBuilder path) {
    if (path.length() == digits.length()) {
      a.add(path.toString());
      return;
    }

    // Get the letters that the current digit maps to, and loop through them
    String cs = map.get(digits.charAt(i));
    for (char c : cs.toCharArray()) {
      path.append(c);
      backtrack(i + 1, path);
      path.deleteCharAt(path.length() - 1);
    }
  }
}
