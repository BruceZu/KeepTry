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
 * check 'Done?' firstly
 *  1 can save:
 *          if (word.length() == 0) { //
 *              r.add(word);
 *              return r;
 *          }
 *         As "" == new String("", 0, 0));
 *  2 check stop in one place, make code structure more clear.
 */
public class Leetcode320GeneralizedAbbreviation3_2 {
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
        abbrBy(0, 0);
        return r;
    }

    private static void abbrBy(final int fillACharTo, final int byCur) {
        if (byCur == chars.length) {
            r.add(new String(abbr, 0, fillACharTo));
            return;
        }

        abbr[fillACharTo] = chars[byCur];
        abbrBy(fillACharTo + 1, byCur + 1);

        int w10 = 1;
        while (w10 <= chars.length - byCur) {
            int cw = w10;
            int fillACharNumTo = fillACharTo;

            if (cw >= 10) {
                abbr[fillACharNumTo++] = (char) (cw / 10 + '0');
                cw = cw - cw / 10 * 10;
            }
            abbr[fillACharNumTo] = (char) (cw + '0');

            int nextFrom = byCur + w10;
            if (nextFrom == chars.length) {
                abbrBy(fillACharNumTo + 1, nextFrom);
            } else {
                abbr[fillACharNumTo + 1] = chars[nextFrom];
                abbrBy(fillACharNumTo + 2, nextFrom + 1);
            }
            w10++;
        }
    }
}
