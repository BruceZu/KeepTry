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

package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 288. Unique Word Abbreviation
 * My Submissions QuestionEditorial Solution
 * Total Accepted: 8346 Total Submissions: 53256 Difficulty: Easy<pre>
 * An abbreviation of a word follows the form <first letter><number><last letter>.<pre>
 * Below are some examples of word abbreviations:<pre>
 * <p/>
 * a) it                      --> it    (no abbreviation)
 * <p/>
 * b) d|o|g                   --> d1g
 * <p/>
 * c) i|nternationalizatio|n  --> i18n
 * <p/>
 * d) l|ocalizatio|n          --> l10n<pre>
 * Assume you have a dictionary and given a word, find whether its abbreviation is unique in the dictionary. <pre>
 * A word's abbreviation is unique if no other word from the dictionary has the same abbreviation.
 * <p/>
 * Example:
 * Given dictionary = [ "deer", "door", "cake", "card" ]
 * <p/>
 * isUnique("dear") -> false
 * isUnique("cart") -> true
 * isUnique("cane") -> false
 * isUnique("make") -> true
 *  <pre>
 * Hide Company Tags Google
 * Hide Tags Hash Table Design
 * Hide Similar Problems (E) Two Sum III - Data structure design (M) Generalized Abbreviation
 */
public class LC288UniqueWordAbbreviation {
    public static void main(String[] args) {
        try {
            String[] dictionary = new String[]{"a"};
            ValidWordAbbr vwa = new ValidWordAbbr(dictionary);
            System.out.println(vwa.isUnique(""));

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    /**
     * beat 97.6
     * the fast one beat 99.8
     */
    static class ValidWordAbbr {

        //     Vother --- Kany  |  all is unique
        //-----------------------------------------------
        //  k1  --> abbr  v1    |    K1:  unique,  Kother: NO
        //  k2  \
        //       ->       v2    | K2,K3:  NO,      Kother: NO
        //  k3  /

        //HashMap permits null values and the null key and is unsynchronized.
        Map<String, String> map = new HashMap<>();

        public ValidWordAbbr(String[] dictionary) {
            // valid input
            if (dictionary == null || dictionary.length == 0) {
                return;
            }
            // start
            StringBuilder b;
            for (int i = 0; i < dictionary.length; i++) {
                //valid input
                String stri = dictionary[i];
                if (stri == null || stri.length() <= 2) {
                    // do not keep, as them will be unique
                    continue;
                }
                //start
                String abbr = abbr(stri);
                Object r = map.put(abbr, stri);
                if (r != null) {
                    map.put(abbr, "false");
                }
            }
        }

        private String abbr(String stri) {
            return new StringBuilder().append(stri.charAt(0))
                    .append(stri.length() - 2)
                    .append(stri.charAt(stri.length() - 1))
                    .toString();
        }

        public boolean isUnique(String word) {
            // invalid input
            String abbr;
            if (word != null && word.length() >= 3) {
                abbr = abbr(word);
            } else {
                return true;
            }
            if (!map.keySet().contains(abbr) || map.get(abbr).equals(word)) {
                return true;
            }
            return false;
        }
    }
}