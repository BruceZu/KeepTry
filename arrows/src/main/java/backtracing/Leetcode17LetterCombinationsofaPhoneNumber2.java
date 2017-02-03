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

import java.util.*;

/**
 * Using String array to map digit to letters
 */
public class Leetcode17LetterCombinationsofaPhoneNumber2 {
    private static String[] digitToLetters = new String[]{
            "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
    };

    private static void f(String digits, int curDigitIndex, List<Character> r, List<String> rs) {
        if (curDigitIndex == digits.length()) {
            // for digit = '01'
            if (r.size() == 0) {
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (Character c : r) {
                sb.append(c);
            }
            rs.add(sb.toString());
            return;// care
        }

        String letters = digitToLetters[digits.charAt(curDigitIndex) - '0'];
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
            return rs; // confirm with user. iuput: "" or "01", the Expected answer: [].
        }

        List<Character> r = new ArrayList<>();
        f(digits, 0, r, rs);
        return rs;
    }

    //--------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        List<String> rs = letterCombinations("01");
        for (String r : rs) {
            System.out.println(r);
        }
    }
}
