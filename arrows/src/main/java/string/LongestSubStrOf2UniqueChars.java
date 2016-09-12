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

package string;

/**
 * https://www.interviewzen.com/interview/3qSHDcT
 *
 * Given a string, find the longest substring that contains only two unique characters.  For example,
 * given "abcbbbbcccbdddadacb", the longest substring that contains 2 unique character is "bcbbbbcccb".
 */
public class LongestSubStrOf2UniqueChars {
    /**
     * @param s given string
     * @return longest substring that contains 2 unique character or null when not found.
     */
    public static String LongestSubstrOf2UniqueChars(String s) {
        // Input check
        if (null == s || s.length() <= 1) {
            return null;
        }

        char c1, c2; // unique chars of current sub string
        int index1, index2; // index1 is the index of last char c1 appears before current char in loop
        String subStr = ""; // keep the result sub string
        boolean findC2 = false;
        int startIndex = 0; // start index of current sub string

        c1 = s.charAt(0);
        c2 = '\0'; //  the init value has no meaning
        index1 = 0;
        index2 = -1; // the init value has no meaning


        for (int i = 1; i < s.length(); i++) {
            char cur = s.charAt(i);
            if (cur == c1) {
                index1 = i;
                continue;
            }
            if (!findC2) {
                c2 = cur;
                findC2 = true;
                index2 = i;
                continue;
            }
            if (cur == c2) {
                index2 = i;
                continue;
            }

            // Found a third char
            String newSubStr = s.substring(startIndex, i);
            if (newSubStr.length() > subStr.length()) {
                subStr = newSubStr;
            }

            char justChar = s.charAt(i - 1);
            if (justChar == c1) {
                startIndex = index2 + 1;
                c2 = cur;
                index2 = i;
            } else {
                startIndex = index1 + 1;
                c1 = cur;
                index1 = i;
            }
        }
        return subStr == "" ? null : subStr;
    }
}
