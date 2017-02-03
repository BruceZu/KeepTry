//  Copyright 2017 The keepTry Open Source Project
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

package backtracing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * all digit mapped letter will take part in the result. each result' length is same as the input's length.
 * duplicated letters may be in result. e.g. input can be '22'. so use List. not Set
 * recursion:  can keep status of current letter of current letters of current digit.
 *             but the downside is this get performance slow.
 *
 * Permutations are for lists (order matters) and combinations are for groups (order doesn't matter)
 *
 * <a href='https://leetcode.com/problems/letter-combinations-of-a-phone-number/'>LeetCode</a>
 */
public class Leetcode17LetterCombinationsofaPhoneNumber {
    private static Map<Character, String> digitToLetters = new HashMap();

    static {
        digitToLetters.put('1', ""); // not letter
        digitToLetters.put('2', "abc");
        digitToLetters.put('3', "def");
        digitToLetters.put('4', "ghi");
        digitToLetters.put('5', "jkl");
        digitToLetters.put('6', "mno");
        digitToLetters.put('7', "pqrs");
        digitToLetters.put('8', "tuv");
        digitToLetters.put('9', "wxyz");
        digitToLetters.put('0', ""); // not letter
    }

    private static void f(String digits, int curDigitIndex, List<Character> r, List<String> rs) {
        if (curDigitIndex == digits.length()) {
            StringBuilder sb = new StringBuilder();
            for (Character c : r) {
                sb.append(c);
            }
            rs.add(sb.toString());
            return;// care
        }

        String letters = digitToLetters.get(digits.charAt(curDigitIndex));
        if (letters.length() == 0) {
            f(digits, curDigitIndex + 1, r, rs);
        } else {
            for (int j = 0; j < letters.length(); j++) {
                Character curLetter = letters.charAt(j);
                r.add(curLetter);
                f(digits, curDigitIndex + 1, r, rs);
                r.remove(r.size() - 1);
            }
        }
    }

    // assume the digits is String of 0~9 only
    public static List<String> letterCombinations(String digits) {
        List<String> rs = new ArrayList();
        if (digits == null || digits.length() == 0) {
            return rs; // confirm with user
        }

        List<Character> r = new ArrayList<>();
        f(digits, 0, r, rs);
        return rs;
    }

    //--------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        List<String> rs = letterCombinations("22");
        for (String r : rs) {
            System.out.println(r);
        }
    }
}
