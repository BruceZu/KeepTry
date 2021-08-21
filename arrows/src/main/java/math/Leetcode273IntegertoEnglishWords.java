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

import java.util.HashMap;
import java.util.Map;

public class Leetcode273IntegertoEnglishWords {
  /*
   273. Integer to English Words

   Convert a non-negative integer num to its English words representation.

   Input: num = 123
   Output: "One Hundred Twenty Three"

   Input: num = 12，345
   Output: "Twelve Thousand Three Hundred Forty Five"

   Input: num = 1，234，567
   Output: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"

   Input: num = 1，234，567，891
   Output: "One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety One"

   Input: num = 0
   Output:"Zero"

   Input: num = 1000
   Output:"One Thousand"

   Input: num = 1，000，010
   Output:"One Million Ten"

   Constraints:
       0 <= num <= 2^31 - 1
  */

  /*
  Idea:
  divide and conquer: separate number by 3 length from right to left

  max  2,147,483,647
  Note the 0, and " ",

  O(N) time
  O(1) space is convert the map to function
  */

  public String numberToWords(int num) {
    if (num == 0) return "Zero";
    String sb = "";
    int l = 0;
    while (true) {
      String cur = parse3len(num % 1000, l);
      if (cur != "") sb = cur + (sb.length() > 0 ? (" " + sb) : sb);
      num /= 1000;
      l++;
      if (num == 0) return sb;
    }
  }

  /*
  0~20
  21~99
  100~999
  0 is special
  */
  private String parse3len(int num, int l) {
    if (num == 0) return "";
    StringBuilder r = new StringBuilder();
    if (num > 99) {
      r.append(map.get(num / 100)).append(" ").append("Hundred");
      num %= 100;
    }

    if (num >= 20) {
      int t = num / 10;
      if (r.length() > 0) r.append(" ");
      r.append(map.get(t * 10));
      num %= 10; // 0~9
    }
    // 0~19 or 0~9
    if (num != 0) {
      if (r.length() > 0) r.append(" ");
      r.append(map.get(num));
    }
    if (l != 0) r.append(" " + level.get(l)); // decided by num !=0 and l!=0
    return r.toString();
  }

  static Map<Integer, String> level = new HashMap();

  static {
    level.put(1, "Thousand");
    level.put(2, "Million");
    level.put(3, "Billion");
  }

  static Map<Integer, String> map = new HashMap();

  static {
    map.put(0, "Zero");
    map.put(1, "One");
    map.put(2, "Two");
    map.put(3, "Three");
    map.put(4, "Four");
    map.put(5, "Five");
    map.put(6, "Six");
    map.put(7, "Seven");
    map.put(8, "Eight");
    map.put(9, "Nine");
    map.put(10, "Ten");
    map.put(11, "Eleven");
    map.put(12, "Twelve");
    map.put(13, "Thirteen");
    map.put(14, "Fourteen");
    map.put(15, "Fifteen");
    map.put(16, "Sixteen");
    map.put(17, "Seventeen");
    map.put(18, "Eighteen");
    map.put(19, "Nineteen");

    map.put(20, "Twenty");
    map.put(30, "Thirty");
    map.put(40, "Forty");
    map.put(50, "Fifty");
    map.put(60, "Sixty");
    map.put(70, "Seventy");
    map.put(80, "Eighty");
    map.put(90, "Ninety");
  }
}
