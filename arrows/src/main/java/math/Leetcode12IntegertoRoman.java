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

package math;

public class Leetcode12IntegertoRoman {
  /*
   Leetcode 12. Integer to Roman

   Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.

   Symbol       Value
   I             1
   V             5
   X             10
   L             50
   C             100
   D             500
   M             1000
   For example, 2 is written as II in Roman numeral, just two one's added together. 12 is written as XII, which is simply X + II. The number 27 is written as XXVII, which is XX + V + II.

   Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:

   I can be placed before V (5) and X (10) to make 4 and 9.
   X can be placed before L (50) and C (100) to make 40 and 90.
   C can be placed before D (500) and M (1000) to make 400 and 900.
   Given an integer, convert it to a roman numeral.



   Example 1:

   Input: num = 3
   Output: "III"
   Example 2:

   Input: num = 4
   Output: "IV"
   Example 3:

   Input: num = 9
   Output: "IX"
   Example 4:

   Input: num = 58
   Output: "LVIII"
   Explanation: L = 50, V = 5, III = 3.
   Example 5:

   Input: num = 1994
   Output: "MCMXCIV"
   Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.


   Constraints:

   1 <= num <= 3999
  */

  /*
  Note   1 <= num <= 3999
   */
  // skill is here 1
  private static final int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
  private static final String[] symbols = {
    "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
  };

  public String intToRoman(int num) {
    StringBuilder sb = new StringBuilder();
    // Loop through each symbol, stopping if num becomes 0.
    for (int i = 0; i < values.length && num > 0; i++) {
      while (values[i] <= num) {
        num -= values[i]; // skill is here 2
        sb.append(symbols[i]);
      }
    }
    return sb.toString();
  }

  // or
  public String intToRoman2(int num) {
    String M[] = {"", "M", "MM", "MMM"}; //   1 <= num <= 3999 .   unit 1000
    String C[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"}; // unit 100
    String X[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"}; // unit 10
    String I[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"}; // unit 1
    return M[num / 1000] + C[(num % 1000) / 100] + X[(num % 100) / 10] + I[num % 10];
  }
}
