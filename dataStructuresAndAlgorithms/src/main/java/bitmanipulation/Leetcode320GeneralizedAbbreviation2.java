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
 *          where to start abbreviate: from each element of chars to start the first abbreviate.
 *    how many elements to abbreviate: 1 ~ chars length - cur.
 *                       stop or next: after each first abbreviation, from abbreviate + 2 to start next recursion
 *                                     or stop if abbreviate + 2 is not exist
 *                                                or
 *                                                abbreviate + 1 is not exist
 * detail:
 *        why fill: fill a char from chars to abbr.
 *                  cover the pre content
 *                  to get the 0 width abbreviation
 *
 *        Done: 1    0 abbreviation, no char left. Done
 *              2 after abbreviation no char left. Done
 *              3 after abbreviation one char left, fill it
 *                              ---> no char left. Done
 *
 *        check 'Done?' firstly can save:
 *          if (word.length() == 0) { //
 *              r.add(word);
 *              return r;
 *          }
 *         As "" == new String("", 0, 0));
 *
 *   Note:
 *   1  when the abbreviation number is more than 9,  the number will need more than one char.
 *   2  corner cases:
 *                       abbreviation
 *      ""                ""
 *      "a"              "a", "1"
 */
public class Leetcode320GeneralizedAbbreviation2 {
    public static List<String> generateAbbreviations(String word) {
        List<String> r = new ArrayList();
        if (word == null) {
            return r;
        }

        char[] chars = word.toCharArray();
        char[] abbr = new char[word.length()];
        abbrBy(abbr, 0, chars, 0, r);
        return r;
    }

    private static void abbrBy(char[] abbr, final int fillACharTo, char[] chars, final int byCur, List<String> r) {
        if (byCur == chars.length) {
            r.add(new String(abbr, 0, fillACharTo));
            return;
        }

        abbr[fillACharTo] = chars[byCur];
        abbrBy(abbr, fillACharTo + 1, chars, byCur + 1, r);

        int w10 = 1;
        while (w10 <= chars.length - byCur) {
            int cw = w10;
            int fillACharNumTo = fillACharTo;

            if (cw >= 10) { // assume n <= 99
                abbr[fillACharNumTo++] = (char) (cw / 10 + '0');
                cw = cw - cw / 10 * 10;
            }
            abbr[fillACharNumTo] = (char) (cw + '0');

            int afterNum = byCur + w10;
            if (afterNum == chars.length) {
                abbrBy(abbr, fillACharNumTo + 1, chars, afterNum, r);
            } else {
                abbr[fillACharNumTo + 1] = chars[afterNum];
                abbrBy(abbr, fillACharNumTo + 2, chars, afterNum + 1, r);
            }
            w10++;
        }
    }
}
