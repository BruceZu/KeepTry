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
    int r[] = Manacher.getRadiusOfVirtualTranslatedStringOf(s);
    int sum = 0;
    int N = 2 * s.length() + 1;
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
