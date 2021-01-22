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

public class Leetcode647PalindromicSubstrings {
  public static int countSubstrings(String s) {
    if (s == null || s.length() == 0) return 0;
    int N = 2 * s.length() + 1;

    // Manacher's algorithm to calculate the radius of palindrome centered on i in virtual string T.
    int r[] = new int[N];
    int c = 0;
    for (int i = 1; i < N; i++) {
      int rMost = c + r[c];
      if (i <= rMost) {
        int mirri = c - (i - c); // mirror of i
        r[i] = Math.min(r[mirri], rMost - i); // reuse in all 3 scenario
        if (i + r[mirri] != rMost) {
          // do not calculate the sum here and only calculate radius, just only do Mancher's
          // algorithm need to do.
          continue;
        }
      }
      // explore
      int l = i - r[i] - 1, e = i + r[i] + 1;
      // virtual T and S: index relation. even index in T is division character, si=ti/2
      while (0 <= l && e < N && ((l & 1) == 0 || s.charAt(l / 2) == s.charAt(e / 2))) {
        l--;
        e++;
        r[i]++;
      }
      if (i + r[i] > c + r[c]) {
        c = i;
      }
    }
    // Manacher's algorithm end
    int sum = 0;
    for (int i = 0; i < N; i++) {
      if (r[i] != 0) {
        int w = r[i];
        int root = (w + 1) / 2;
        sum += root;
      }
    }
    return sum;
  }
}
