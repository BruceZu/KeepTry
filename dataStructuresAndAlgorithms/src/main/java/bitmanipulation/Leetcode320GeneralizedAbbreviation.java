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
 * 320. Generalized Abbreviation
 * Difficulty: Medium
 * Write a function to generate the generalized abbreviations of a word.
 *
 * Example:
 *
 * Given word = "word", return the following list (order does not matter):
 *
 * [    "word",
 *      "1ord", "w1rd", "wo1d", "wor1",
 *      "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1",
 *      "1o2", "2r1", "3d", "w3",
 *      "4"     ]
 *
 * Company Tags Google
 *  Tags:
 *      Backtracking
 *      Bit Manipulation
 *
 *  Similar Problems
 *      (M) Subsets
 *      (E) Unique Word Abbreviation
 *  ====================================================================
 *  1> Bit Manipulation:
 *      same as subsets of size <strong>[0 ~ 2^word.length-1]</strong>
 *      assume world length is less than 32.
 *      replace StringBuilder with char[] can gain performance, but not easy to handle when n>=10.
 *
 *  2> backtracking
 * @see Leetcode320GeneralizedAbbreviation2
 */
public class Leetcode320GeneralizedAbbreviation {
    // runtime O(N*2^N)
    public static List<String> generateAbbreviations(String word) {
        List<String> r = new ArrayList<>();
        if (word == null) {
            return r;
        }
        char[] ar = word.toCharArray();
        int max = (1 << word.length()) - 1;
        for (int solution = 0; solution <= max; solution++) {
            int cur = solution;
            int i = 0;

            StringBuilder sb = new StringBuilder();
            while (cur != 0) {
                if ((cur & 1) == 1) {
                    int n = 0;
                    while ((cur & 1) == 1) {
                        n++;
                        cur >>>= 1;
                        i++;
                    }
                    sb.append(n);
                } else {
                    sb.append(ar[i]);
                    cur >>>= 1;
                    i++;
                }
            }
            if (i <= ar.length - 1) {
                sb.append(ar, i, ar.length - i);
            }
            r.add(sb.toString());
        }
        return r;
    }
}
