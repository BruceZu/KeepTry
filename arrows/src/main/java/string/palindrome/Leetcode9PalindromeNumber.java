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

package string.palindrome;
/*
9. Palindrome Number


Given an integer x, return true if x is palindrome integer.
An integer is a palindrome when it reads the same backward as forward. For example,
121 is palindrome while 123 is not.


Input: x = 121
Output: true

Input: x = -121
Output: false
Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.

Input: x = 10
Output: false
Explanation: Reads 01 from right to left. Therefore it is not a palindrome.

Input: x = -101
Output: false

Constraints:

    -231 <= x <= 231 - 1


Follow up: Could you solve it without converting the integer to a string?
 */
public class Leetcode9PalindromeNumber {
  // O(logN) time
  public boolean isPalindrome(int x) {
    if (x < 0 || x != 0 && x % 10 == 0) return false;
    //  x end with 0 does not apply to this algorithm and is not palindrome. 0 is exception.
    //  Algorithm: reverse the right half part and compare, without use extra space
    //            does not apply to negative integer and float number
    int r = 0;
    while (x > r) {
      r = r * 10 + x % 10;
      x = x / 10;
    }
    return x == r || x == r / 10;
  }
}
