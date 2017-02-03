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

package queue;

import java.util.LinkedList;
import java.util.List;

/**
 * all digit mapped letter will take part in the result. each result' length is same as the input's length.
 * Using queue to replace backtracking
 */
public class Leetcode17LetterCombinationsofaPhoneNumber3 {
    // assume the input 'digits' is composed by char '0'~'9'
    public static List<String> letterCombinations(String digits) {
        String[] digitToLetters = new String[]{
                // the 0 and 1 maps to no letters, so using empty string here
                "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
        };
        LinkedList<String> resultQueue = new LinkedList<>(); // queue and list
        if (digits == null || digits.length() == 0) {
            return resultQueue; // []
        }
        // input is "0", "1" or "01", expected answer is [] too

        for (int i = 0; i < digits.length(); i++) {
            String curLetters = digitToLetters[digits.charAt(i) - '0'];
            if (curLetters.length() == 0) {
                // letters of digit 0 or 1, no contribution to result.
                continue;
            }
            if (resultQueue.size() == 0) {
                for (int letterIndex = 0; letterIndex < curLetters.length(); letterIndex++) {
                    resultQueue.add(curLetters.charAt(letterIndex) + ""); // append to the end
                }
            } else {
                int leftNumbers = resultQueue.size();
                while (leftNumbers-- > 0) {
                    String head = resultQueue.remove(); // queue method
                    for (int letterIndex = 0; letterIndex < curLetters.length(); letterIndex++) {
                        resultQueue.add(head + curLetters.charAt(letterIndex)); // append to the end
                    }
                }
            }
        }
        return resultQueue;
    }
}
