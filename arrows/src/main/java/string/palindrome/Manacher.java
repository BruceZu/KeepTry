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
    /**
     *  Manacher's algorithm is based on virtual translated string `T` whose
     *  length is 2 * N +1, not 2*N.
     *  N is the length of given string `s`.
     *  Assume 2 * N +1 < Integer.MAX_VALUE.
     *  T virtual string T and S index relation:
     *   - even index in T is division character
     *   - index in s = index in T / 2
     *
     *  Manacher's algorithm calculate array 'int[] r'.
     *  r[i], 0<= i <= 2*N +1, is the value of radius of the longest palindrome centered on index i.
     *  The concept 'radius' does not include the center index.
     *  It is value 'v' range: if((i & 1) == 1) 1 <= v; else 0 <= v
     *
     *  Manacher's algorithm keep tracking the index of current center/axis with `c`
     *  with this center/axis the related longest palindrome has the rightmost
     *  touched/boundary character in T by far.
     *  when `c` is 0, T[c] is the virtual division character.
     *  r[0] = 0, In Java it is default value
     *
     *  Only when i <= current rightmost index value, i has mirror index based on
     *  current `c`, and possible to reuse precalculated radius and the reused
     *  value is the  the minus one of
     *   -  right most index - i
     *   -  radius[mirror index of i]
     *  reuse in all 3 scenario
     *   -  i + r[mirror index of i] < right most
     *   -  i + r[mirror index of i] > right most
     *   -  i + r[mirror index of i] = right most (need explore)
     *
     * @param s
     * @return
     */
    static public int[] getRadiusOfVirtualTranslatedStringOf(String s){
        if (s == null || s.length() == 0) return null;
        int N = 2 * s.length() + 1;
        int[] r = new int[N];

        int c = 0;
        for (int i = 1; i < N; i++) {
            int rm = c + r[c];
            if (i <= rm) {
                int mi = c - (i - c), reused = Math.min( r[mi],rm - i);
                r[i] = reused;
                if (i + r[mi] != rm) {
                    // need not update current c
                    continue;
                }
            }
            // r<i or i+ r[mirror]== right most, try expand
            for (int l = i - r[i] - 1, e = i + r[i] + 1;
                 0 <= l && e < N && ((l & 1) == 0 || s.charAt(l / 2) == s.charAt(e / 2));
                 l--, e++) {
                r[i]++;
            }
            if (i + r[i] > rm) c = i;
        }
        return r;
    }
}
