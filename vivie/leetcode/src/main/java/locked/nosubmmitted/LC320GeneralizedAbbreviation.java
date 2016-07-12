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

package locked.nosubmmitted;

import java.util.ArrayList;
import java.util.List;

/**
 * 320. Generalized Abbreviation
 * https://leetcode.com/problems/generalized-abbreviation/
 * <p/>
 * Difficulty: Medium
 * Write a function to generate the generalized abbreviations of a word.
 * <p/>
 * Example:
 * Given word = "word", return the following list (order does not matter):
 * ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
 * Hide Company Tags Google
 * Hide Tags Backtracking, Bit Manipulation
 * Hide Similar Problems (M) Subsets (E) Unique Word Abbreviation
 */
public class LC320GeneralizedAbbreviation {

    /**
     * beat 99%
     * The idea is simple:
     * for each character of the "word", we can either abbreviate it or not abbreviate it.
     * if we abbreviate, we can abbreviate one character, or two characters,...,or all characters of the remaining string.
     * if we abbreviate the first k characters, the k + 1 character must not be abbreviated; otherwise, we should abbreviate these k + 1 characters rather than just the first k
     * After each phase, deal with the remaining string using the same way
     * parse the length of abbreviation number by yourself can be a little faster than using java lib.
     *
     * @param word
     * @return
     */
    public List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList<String>();
        if (word.length() == 0) {
            res.add(word);
            return res;
        }
        char[] c = word.toCharArray();
        char[] aux = new char[word.length()]; //auxiliary array for generating abbreviation
        generateAbbreviations(aux, 0, c, 0, res);
        return res;
    }

    private void generateAbbreviations(char[] aux, int p1, char[] c, int p2, List<String> res) {
        // not abbr
        aux[p1] = c[p2];
        if (p2 == c.length - 1) res.add(new String(aux, 0, p1 + 1));
        else generateAbbreviations(aux, p1 + 1, c, p2 + 1, res);
        // abbr
        for (int i = 1; i <= c.length - p2; i++) {
            int l = i;//length of abbreviation
            int p = p1;
            while (l >= 10) {
                aux[p++] = (char) (l / 10 + '0');
                l = l - l / 10 * 10;
            }
            aux[p++] = (char) (l + '0');
            if (p2 + i == c.length) res.add(new String(aux, 0, p)); // abbr all the remaining
            else if (p2 + i == c.length - 1) { // abbr all the remaining except the last character
                aux[p++] = c[p2 + i];// the following character cannot be abbreviated
                res.add(new String(aux, 0, p));
            } else {
                aux[p++] = c[p2 + i];// the following character cannot be abbreviated
                generateAbbreviations(aux, p, c, p2 + i + 1, res);
            }
        }
    }
}