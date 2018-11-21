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

package array;

/**
 * Given this string of numbers, find the four sequential single digits with the biggest product.
 * Note that line breaks are for clarity, consider this input as a single string.
 * <pre>
 * Find the terms and the product.
 * E.g.:
 * First 4 sequential digits are: 7 3 1 6
 * Second 4 sequential digits are: 3 1 6 7
 */
public class FourDigitsWithBiggestProduct {
  /**
   * @param input
   * @param l left index included
   * @param r right index included
   * @return product
   */
  private static short calculate(char[] input, int l, int r) {
    short p = 1;
    for (int i = l; i <= r; i++) {
      //p *= Integer.valueOf("" + input[i]); // Note: use the value of char variable input[i], not itself related int value.
      p *= input[i] - '0'; // '0' int value is 48
    }

    return p;
  }

  /**
   * @param testStr
   * @return The first digit index and product.
   */
  public static int[] find(String testStr) {
    char[] input = testStr.toCharArray();

    short max =
        Short.MIN_VALUE; // Note: 1> use short is enough;  Note: 2> use MIN_VALUE.  do not use 0.
    int indexFrom = 0;
    for (int i = 0; i <= input.length - 4; i++) {
      short p = calculate(input, i, i + 3);
      if (max < p) {
        max = p;
        indexFrom = i;
      }
    }
    return new int[] {indexFrom, max};
  }
}
