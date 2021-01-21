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

public class Leetcode9PalindromeNumber {
  // O(logN)
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
