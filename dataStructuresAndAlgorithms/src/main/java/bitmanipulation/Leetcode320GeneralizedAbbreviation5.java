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
 * <pre> loop implementation with DP
 * time complexity:  O(2^n)
 * space complexity: O(2^n)
 *
 * Case to show idea:
 *     index         0        1        2        3       4        5        6       7      ...
 * index length
 *       0 ""     -> ""
 * 0     1 "a"    -> "a",     "1"
 * 1     2 "aw"   -> "aw",    "1w",    "a1",    "2",
 * 2     3 "awo"  -> "awo",   "1wo",   "a1o"    "2o",   "aw1",   "1w1"    "a2",   "3",
 * 3     4 "awor" -> "awor",  "1wor",  "a1or"   "2or",  "aw1r",  "1w1r"   "a2r",  "3r",
 *                   "awo1",  "1wo1",  "a1o1"   "2o1",  "aw2",   "1w2"    "a3",   "4",
 *
 *  Note: assume word length or abbreviate number <= 99
 *        corner cases: code lines with '//'
 */
public class Leetcode320GeneralizedAbbreviation5 {
    // runtime,  i*2^(i-1); i:1~N;
    public static List<String> generateAbbreviations(String word) {
        if (word == null) {
            return new ArrayList<>(0);
        }
        List<String> r = new ArrayList<>(1 << word.length());
        if (word.equals("")) {//
            r = new ArrayList<>(1);
            r.add("");
            return r;
        }
        char[] chars = word.toCharArray();
        char[][] abbrs = new char[1 << chars.length - 1][chars.length];
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            for (int old = 0; old < (1 << i); old++) {
                if (i != chars.length - 1) {
                    int newIndex = old + (1 << i);
                    int k = 0;
                    for (; abbrs[old][k] != '\0'; k++) {
                        abbrs[newIndex][k] = abbrs[old][k];
                    }
                    abbrs[old][k] = c;
                    plusAbbreNumber1(abbrs[old], k, abbrs[newIndex]);
                } else {
                    int k = 0;
                    while (abbrs[old][k] != '\0') {
                        k++;
                    }
                    abbrs[old][k] = c;
                    r.add(new String(abbrs[old], 0, k + 1));
                    int size = plusAbbreNumber1(abbrs[old], k);
                    r.add(new String(abbrs[old], 0, size));
                }
            }
        }

        return r;
    }

    private static void plusAbbreNumber1(char[] preChars, int size, char[] newChars) {
        boolean noPreChar = size == 0; //
        if (noPreChar || '9' < preChars[size - 1] || preChars[size - 1] < '0') {
            newChars[size] = '1';
        } else {
            char last = preChars[size - 1];
            boolean has2pre = size - 2 >= 0; //
            boolean num2 = has2pre && '0' <= preChars[size - 2] && preChars[size - 2] <= '9';
            int num = num2 ? (preChars[size - 2] - '0') * 10 + last - '0' : last - '0';

            num++;
            int fillNto = num2 ? size - 2 : size - 1;
            if (num > 9) {
                newChars[fillNto++] = (char) ('0' + num / 10);
                newChars[fillNto++] = (char) ('0' + num - num / 10 * 10); //
            } else {
                newChars[fillNto++] = (char) ('0' + num);
            }
        }
    }

    private static int plusAbbreNumber1(char[] preChars, int size) {
        boolean noPreChar = size == 0; //
        if (noPreChar || '9' < preChars[size - 1] || preChars[size - 1] < '0') {
            preChars[size++] = '1';
            return size;
        } else {
            char last = preChars[size - 1];
            boolean has2pre = size - 2 >= 0; //
            boolean num2 = has2pre && '0' <= preChars[size - 2] && preChars[size - 2] <= '9';
            int num = num2 ? (preChars[size - 2] - '0') * 10 + last - '0' : last - '0';

            num++;
            int fillNto = num2 ? size - 2 : size - 1;
            if (num > 9) {
                preChars[fillNto++] = (char) ('0' + num / 10);
                preChars[fillNto++] = (char) ('0' + num - num / 10 * 10); //
            } else {
                preChars[fillNto++] = (char) ('0' + num);
            }
            return fillNto;
        }
    }
}
