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

public class Leetcode43MultiplyStrings {
  /*
   43. Multiply Strings

  Given two non-negative integers num1 and num2 represented as strings,
  return the product of num1 and num2, also represented as a string.

  Note: You must not use any built-in BigInteger library or
       convert the inputs to integer directly.

  Input: num1 = "2", num2 = "3"  Output: "6"
  Input: num1 = "123", num2 = "456" Output: "56088"

  Constraints:

      1 <= num1.length, num2.length <= 200
      num1 and num2 consist of digits only.
      Both num1 and num2 do not contain any leading zero, except the number 0 itself.

  */
  /*
   Watch:
   1> index relation

          0  1  2 index
          9  9  9
             9  9
             0  1 index
   ==============
    0  1  2  3  4 index relation
    0  0  0  0  0
               81
    0  0  0  8  1
            81
    0  0  8  9  1
         81
    0  8  9  9  1
            81
    0  8  18 0 1
          81
    0 17   9  0 1
      81
    9  8   9  0 1
    0  1   2  3 4 index relation
                4= i=2 + j=1 + 1
              3= i=2 + j=1


   2> result length at most is M+N
   3> the leftmost number may be > 9

  */
  public String multiply(String num1, String num2) {
    int M = num1.length(), N = num2.length();
    int[] a = new int[M + N];

    for (int i = M - 1; i >= 0; i--) {
      for (int j = N - 1; j >= 0; j--) {
        int p = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');

        int sum = p + a[i + j + 1];
        a[i + j + 1] = (sum) % 10;
        a[i + j] += sum / 10;
      }
    }

    StringBuilder s = new StringBuilder();
    for (int i : a) { // "2" * "3" ="6" not '06'
      if ((s.length() == 0 && i == 0)) continue;
      s.append(i);
    }
    return s.length() == 0 ? "0" : s.toString();
  }
}
