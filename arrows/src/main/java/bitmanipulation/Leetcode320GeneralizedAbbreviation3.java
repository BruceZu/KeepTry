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
 * <pre>
 * backtracking. e.g.: 'word'
 *
 *     abbreviation
 *     w  o  r  d,
 *     w  o  r  1,
 *
 *     w  o  1  d,
 *     w  o  2,
 *
 *     w  1  r  d,
 *     w  1  r  1,
 *     w  2  d,
 *     w  3,
 *
 *     1  o (r  d),          e.g.:  1  o (r  d),
 *     1  o (r  1),                 |
 *     1  o (1  d),              abbrBy()
 *     1  o  2,
 *     2  r  d,
 *     2  r  1,
 *     3  d,
 *     4
 *
 * data structures:
 *     chars
 *     abbr
 *
 * recursion:
 *          abbreviate from each element.
 *          abbreviate number: 0, 1 ~ chars length - 'from'.
 *                             0 is special as there will not abbr number '0' in result.
 *                             it means no abbreviation number on 'from'
 *          after abbreviation, stop if abbreviate + 2 is not exist
 *                                      or
 *                                      abbreviate + 1 is not exist
 *                                   else from abbreviate + 2 to start next recursion
 * detail:
 *        why fill: to get the 0 width abbreviation on 'from'
 *
 *            Done: 1    0 abbreviation, no char left. Done
 *                  2 after abbreviation no char left. Done
 *                  3 after abbreviation one char left, fill it
 *                              ---> no char left. Done
 *   Note:
 *   1  when the abbreviation number is more than 9,  the number will need more than one char.
 *      assume n <= 99
 *   2  corner cases:
 *                       abbreviation
 *      ""                ""
 *      "a"              "a", "1"
 *   see improved version {@link Leetcode320GeneralizedAbbreviation3_2}
 */
public class Leetcode320GeneralizedAbbreviation3 {
    private static List<String> r;
    private static char[] chars;

    private static char[] abbr;

    // runtime:
    public static List<String> generateAbbreviations(String word) {
        r = new ArrayList();
        if (word == null) {
            return r;
        }
        if (word.length() == 0) { //
            r.add(word);
            return r;
        }
        chars = word.toCharArray();
        abbr = new char[word.length()];
        abbrBy(0, 0);
        return r;
    }

    private static void abbrBy(final int fillACharTo, final int from) {
        // w10 = 0, no abbreviation
        abbr[fillACharTo] = chars[from];
        if (from != chars.length - 1) {
            abbrBy(fillACharTo + 1, from + 1);
        } else {
            r.add(new String(abbr, 0, fillACharTo + 1));
        }
        //w10: 1~chars.length - from
        int w10 = 1;
        while (w10 <= chars.length - from) {
            int cw = w10;
            int fillACharNumTo = fillACharTo;

            if (cw >= 10) {
                abbr[fillACharNumTo++] = (char) (cw / 10 + '0');
                cw = cw - cw / 10 * 10;
            }
            abbr[fillACharNumTo] = (char) (cw + '0');

            int nextFrom = from + w10;
            if (nextFrom == chars.length) {
                r.add(new String(abbr, 0, fillACharNumTo + 1));
            } else {
                abbr[fillACharNumTo + 1] = chars[nextFrom];
                if (nextFrom == chars.length - 1) {
                    r.add(new String(abbr, 0, fillACharNumTo + 2));
                } else {
                    abbrBy(fillACharNumTo + 2, nextFrom + 1);
                }
            }
            w10++;
        }
    }
}
