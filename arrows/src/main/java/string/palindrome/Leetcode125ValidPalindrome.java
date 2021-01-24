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

public class Leetcode125ValidPalindrome {
  // O(N)
  public boolean isPalindrome(String s) {
    // 'string might be empty? we define empty string as valid palindrome.'
    if (s.isEmpty() || s.length() == 1) {
      return true;
    }
    int l = 0, r = s.length() - 1;
    char cl, cr;
    while (true) {
      // 'considering only alphanumeric characters and ignoring cases.'
      while (!Character.isLetterOrDigit(s.charAt(l)) && l < r) l++;
      while (!Character.isLetterOrDigit(s.charAt(r)) && l < r) r--;
      if (l >= r) return true;
      // 'considering only alphanumeric characters and ignoring cases.'
      if (Character.toLowerCase(s.charAt(l)) == Character.toLowerCase(s.charAt(r))) {
        l++;
        r--;
      } else {
        return false;
      }
    }
  }

  // 32 ms
  public static boolean isPalindrome2(String str) {
    String s = str.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
    String rev = new StringBuffer(s).reverse().toString();
    return s.equals(rev);
  }
}
