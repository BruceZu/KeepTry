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

package backtracing;

import java.util.ArrayList;
import java.util.List;

public class Leetcode282ExpressionAddOperators {
  /*
   Leetcode 282. Expression Add Operators

   Given a string num that contains only digits and an integer target,
   return all possibilities to insert the binary operators '+', '-', and/or '*' between
   the digits of num so that the resultant expression evaluates to the target value.

   Note that operands in the returned expressions should not contain leading zeros.


   Input: num = "123", target = 6
   Output: ["1*2*3","1+2+3"]
   Explanation: Both "1*2*3" and "1+2+3" evaluate to 6.
   Example 2:

   Input: num = "232", target = 8
   Output: ["2*3+2","2+3*2"]
   Explanation: Both "2*3+2" and "2+3*2" evaluate to 8.
   Example 3:

   Input: num = "3456237490", target = 9191
   Output: []
   Explanation: There are no expressions that can be created from "3456237490" to evaluate to 9191.


   Constraints:

   1 <= num.length <= 10
   num consists of only digits.
   -2^31 <= target <= 2^31 - 1
  */

  public ArrayList<String> ans;
  public String str;
  public int T;

  public List<String> addOperators(String num, int target) {
    ans = new ArrayList<>();
    if (num.length() == 0) return ans;
    T = target;
    str = num;
    recurse(0, 0, 0, 0, new ArrayList<>());
    return ans;
  }

  /*
    i: index of current one char/number
    o: current operand
    pre: pre operand in exp. with sign +/-
  */
  private void recurse(int i, long pre, long o, long exp, ArrayList<String> tmp) {
    if (i == str.length()) {
      if (exp == T && o == 0) { // if o!=0 that mean exp does not cover all nums.
        StringBuilder sb = new StringBuilder();
        tmp.subList(1, tmp.size()).forEach(v -> sb.append(v));
        ans.add(sb.toString());
      }
      return;
    }

    // extending o by current one digit
    o = o * 10 + Character.getNumericValue(str.charAt(i));
    String oStr = Long.toString(o);

    // cases 1 + 05 or 1 * 05,   05 won't be a valid operand.
    if (o != 0) { // NO OP recursion
      recurse(i + 1, pre, o, exp, tmp);
    }

    // +
    tmp.add("+");
    tmp.add(oStr);
    recurse(i + 1, o, 0, exp + o, tmp);
    tmp.remove(tmp.size() - 1);
    tmp.remove(tmp.size() - 1);

    if (tmp.size() > 0) { // only insert -/* between number
      // -
      tmp.add("-");
      tmp.add(oStr);
      recurse(i + 1, -o, 0, exp - o, tmp);
      tmp.remove(tmp.size() - 1);
      tmp.remove(tmp.size() - 1);

      // *
      tmp.add("*");
      tmp.add(oStr);
      recurse(i + 1, o * pre, 0, exp - pre + (o * pre), tmp);
      tmp.remove(tmp.size() - 1);
      tmp.remove(tmp.size() - 1);
    }
  }
}
