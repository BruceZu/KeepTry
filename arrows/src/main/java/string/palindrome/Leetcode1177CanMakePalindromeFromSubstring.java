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

import java.util.ArrayList;
import java.util.List;

public class Leetcode1177CanMakePalindromeFromSubstring {
  public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
    //  1 <= s.length, queries.length <= 10^5. so
    if (s == null || s.length() == 0) return null;
    //  s only contains lower case English letters.
    //  Integer is enough
    int n = s.length();
    int c = 0;
    int[] cStatus = new int[n];
    for (int i = 0; i < s.length(); i++) {
      c ^= 1 << s.charAt(i) - 'a';
      cStatus[i] = c;
    }

    List<Boolean> r = new ArrayList<Boolean>(queries.length);
    for (int i = 0; i < queries.length; i++) {
      // 0 <= queries[i][0] <= queries[i][1] < s.length
      // 0 <= queries[i][2] <= s.length
      r.add(f(cStatus, queries[i][0], queries[i][1], queries[i][2]));
    }
    return r;
  }
  // 0<=l<=r<=s.length=counts.length
  // analyze each char, it obey the ^ bitwise operation
  private static boolean f(int[] cStatus, int l, int r, int k) {
    int c = l == 0 ? cStatus[r] : (cStatus[l - 1] ^ cStatus[r]); // note: l-1 and ()
    int oddSum = 0;
    while (c != 0) {
      c &= (c - 1);
      oddSum++;
    }
    // 'up to k' logic;
    return oddSum / 2 <= k;
    /*
    // just k logic:
    if (k < oddSum / 2) return false;
    // oddSum / 2 <= k
    if (((r - l + 1) & 1) == 0) return oddSum / 2 <= k && k <= oddSum || k > oddSum && (k & 1) == 0;
    return true;
    */
  }
}
