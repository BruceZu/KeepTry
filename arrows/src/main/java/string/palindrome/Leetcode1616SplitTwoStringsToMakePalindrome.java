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

public class Leetcode1616SplitTwoStringsToMakePalindrome {
  public boolean checkPalindromeFormation(String a, String b) {
    if (a == null || b == null) return false;
    // from left side of a, right side of b to check chars are same or not
    // when chars are not, then check in b left scope to see if it is a palindrome
    // as if the split line is just at the left side of l.
    // then check left side of b, right side of a ...
    return help(a, b) || help(b, a);
  }

  private boolean help(String a, String b) {
    int l = 0, r = b.length() - 1;
    while (l < r && a.charAt(l) == b.charAt(r)) {
      l++;
      r--;
    }
    // possible l>=r or l==0 now
    // Check if aprefix + bsuffix or bprefix + asuffix forms a palindrome
    return isPalindrome(b, l, r) || isPalindrome(a, l, r);
  }

  private boolean isPalindrome(String s, int l, int r) {
    while (l < r && s.charAt(l) == s.charAt(r)) {
      l++;
      r--;
    }
    return l >= r; // even: l>r, odd: l==r
  }
}
