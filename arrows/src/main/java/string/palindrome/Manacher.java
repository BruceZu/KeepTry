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

public class Manacher {
  /*
  Manacher's algorithm
  */
  public static int[] getRadiusOfVirtualTranslatedStringOf(String str) {
    if (str == null || str.length() == 0) return null;
    int N = 2 * str.length() + 1;
    int[] v = new int[N];

    int c = 0;
    for (int i = 1; i < N; i++) {
      int r = c + v[c];
      if (i <= r) {
        v[i] = Math.min(v[c - (i - c)], r - i);
        // if (i + mv != r) continue; not change O(N) time, but make algorithm not easy
      }
      // i>r or i+ v[mirror i]== right most, try expand
      // use left and end index, because it is a virtual array.
      int l = i - v[i] - 1, e = i + v[i] + 1;
      while (0 <= l && e < N && ((l & 1) == 0 || str.charAt(l / 2) == str.charAt(e / 2))) {
        v[i]++;
        l--;
        e++;
      }
      if (i + v[i] > r) c = i;
    }
    return v;
  }
}
