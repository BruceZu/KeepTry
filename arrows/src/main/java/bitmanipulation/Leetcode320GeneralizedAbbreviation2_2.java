//  Copyright 2016 The Sawdust Open Source Project
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

package bitmanipulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Can use StringBuilder to replace String
 * while it it better to use char[]
 * Improvement of {@link Leetcode320GeneralizedAbbreviation2}
 */
public class Leetcode320GeneralizedAbbreviation2_2 {
    // runtime:
    private static List<String> r;
    private static char[] chars;
    private static char[] abbr;

    public static List<String> generateAbbreviations(String word) {
        r = new ArrayList();
        if (word == null) {
            return r;
        }

        chars = word.toCharArray();
        abbr = new char[word.length()];

        abbrfrom(0, 0, false);
        return r;
    }

    private static void abbrfrom(final int fillACharTo, final int f, boolean preCharIsNum) {
        if (f == chars.length) {
            r.add(new String(abbr, 0, fillACharTo));
            return;
        }

        for (int to = f; to < chars.length; to++) {
            if (to == f) {
                abbr[fillACharTo] = chars[to];
                abbrfrom(fillACharTo + 1, to + 1, false);
            }
            if (!preCharIsNum) {
                int cw = to - f + 1;
                int fillNumTo = fillACharTo;
                if (cw >= 10) { // assume n <= 99
                    abbr[fillNumTo++] = (char) (cw / 10 + '0');
                    cw = cw - cw / 10 * 10;
                }
                abbr[fillNumTo] = (char) (cw + '0');

                abbrfrom(fillNumTo + 1, to + 1, true);
            }
        }
    }
}
