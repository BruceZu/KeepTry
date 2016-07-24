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
 *       left ready parts,
 *       start from 'from', current 'to' ('from' ~ length-1), number of abbr:
 *             - 0 is special
 *             - if pre char is number, so do not abbre from 'from'
 *               else: abbr, the number is:  current 'to' - 'from' + 1;
 *       left parts: 'from' = to+1;
 *       e.g. 'word':
 *       from = index 1 and current 'to' is index 2:
 *
 *             ____________________
 *            |       _____________
 *            |      |      _______
 *            |      |     |
 *            |      |     |       |
 *            w      o     r       d
 *   from:    0      1     2       3
 *                  from  to      to +1
 *
 *   left ready parts: 'w' +  abbre number: to-from+1
 *                         =  'w2', next from is to+1=3
 *
 *  Result:
 *      word  wor1;  wo1d, wo2;  w1rd, w1r1, w2d, w3,
 *      1ord, 1or1,  1o1d, 1o2,
 *      2rd, 2r1,
 *      3d,
 *      4
 */
public class Leetcode320GeneralizedAbbreviation2 {
    private static String word;
    private static List<String> r;

    public static List<String> generateAbbreviations(String w) {
        if (w == null) {
            return new ArrayList<>();
        }

        word = w;
        r = new ArrayList();

        abbr(0, false, "");
        return r;
    }

    public static void abbr(int f, boolean prevNum, String abbrReadyParts) {
        if (f == word.length()) {
            r.add(abbrReadyParts);
            return;
        }
        for (int to = f; to < word.length(); to++) {
            if (to == f) {
                abbr(to + 1, false, abbrReadyParts + word.charAt(to));
            }
            if (!prevNum) {
                abbr(to + 1, true, abbrReadyParts + Integer.toString(to - f + 1));
            }
        }
    }
}
