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

public class Leetcode131PalindromePartitioning {
  //  1 <= s.length <= 16
  public List<List<String>> partition(String s) {
    List<List<String>> res = new ArrayList<>();
    if (s == null || s.length() == 0) return res;
    boolean[][] dp = getIsPalindrome(s.toCharArray());
    dfs(res, new ArrayList<String>(), dp, s, 0);
    return res;
  }

  private boolean[][] getIsPalindrome(char[] s) {
    if (s == null || s.length == 0) return null;
    int N = s.length;
    boolean dp[][] = new boolean[N][N]; // default false
    for (int r = 0; r < N; r++) {
      for (int l = r; 0 <= l; l--) { // not backward is still okay
        //  's contains only lowercase English letters.'
        if (s[l] == s[r] && (r - l <= 2 || dp[l + 1][r - 1])) {
          dp[l][r] = true;
        }
      }
    }
    return dp;
  }

  private void dfs(
      List<List<String>> res,
      List<String> cuts,
      boolean[][] isPalindrome,
      String s,
      int l /*index right of last cut */) {
    if (l == s.length()) {
      res.add(new ArrayList<>(cuts));
      return;
    }
    for (int r = l; r < s.length(); r++) {
      if (isPalindrome[l][r]) {
        cuts.add(s.substring(l, r + 1));
        dfs(res, cuts, isPalindrome, s, r + 1);
        cuts.remove(cuts.size() - 1);
      }
    }
  }
}
