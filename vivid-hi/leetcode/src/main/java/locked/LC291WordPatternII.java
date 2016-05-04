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

package locked;

/**
 * 291. Word Pattern II
 * https://leetcode.com/problems/word-pattern-ii/
 * <p/>
 * Given a pattern and a string str, find if str follows the same pattern.
 * <p/>
 * Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty substring in str.
 * <p/>
 * Examples:<pre>
 * pattern = "abab", str = "redblueredblue" should return true.
 * pattern = "aaaa", str = "asdasdasdasd" should return true.
 * pattern = "aabb", str = "xyzabcxzyabc" should return false.
 * Notes:
 * You may assume both pattern and str contains only lowercase letters.
 * <p/>
 * Hide Company Tags Dropbox Uber
 * Hide Tags Backtracking
 * Hide Similar Problems (E) Word Pattern
 */

// beat 100%
public class LC291WordPatternII {
    public boolean wordPatternMatch(String pattern, String str) {
        if ((pattern == null || pattern.length() == 0)
                && (str == null || str.length() == 0)) {
            return true;
        } else if (pattern == null
                || pattern.length() == 0
                || str == null
                || str.length() == 0) {
            return false;
        } else if (pattern.length() > str.length()) {
            return false;
        } else {
            return XXXXXX(pattern, str, new String[256], 0, 0);
        }
    }

    // back track
    private boolean XXXXXX(String pattern, String str, String[] map, int pIndex, int strIndex) {
        int pLength = pattern.length(), strLength = str.length();
        if (pIndex == pLength) {
            return strIndex == strLength;
        }

        char pCurrChar = pattern.charAt(pIndex);
        if (map[pCurrChar] != null) {
            String mappedStr = map[pCurrChar];

            if (mappedStr.length() + strIndex > strLength) {
                return false;
            }

            return str.startsWith(mappedStr, strIndex) ?
                    XXXXXX(pattern, str, map, pIndex + 1, strIndex + mappedStr.length()) // go ahead
                    : false;

        } else { // never mapped
            int maxStrLenToChek = maxValidStrLengthToTryMap(pattern, str, map, pIndex, strIndex);
            if (maxStrLenToChek < 1) {
                return false; // improve performance
            }

            for (int subStrEnd = strIndex + 1; subStrEnd < strIndex + maxStrLenToChek + 1; ++subStrEnd) {
                String subStr = str.substring(strIndex, subStrEnd);
                if (matchExists(pattern, map, pIndex, subStr)) {
                    continue; // improve performance
                }

                map[pCurrChar] = subStr;
                // go ahead
                if (XXXXXX(pattern, str, map, pIndex + 1, subStrEnd)) {
                    return true;
                }
            }

            map[pCurrChar] = null; // have to back track
            return false;
        }
    }

    private boolean matchExists(String pattern, String[] match, int pCurrentIndex, String s) {
        for (int i = 0; i < pCurrentIndex; ++i) {
            if (match[pattern.charAt(i)].equals(s)) {
                return true;
            }
        }
        return false;
    }

    private int maxValidStrLengthToTryMap(String pattern, String str, String[] map, int pIndex, int strIndex) {
        char pCurrChar = pattern.charAt(pIndex);
        int pLength = pattern.length(), strLength = str.length();
        int strLengthLeft = strLength - strIndex, sameCount = 1;

        for (int i = pIndex + 1; i < pLength; ++i) {
            char piChar = pattern.charAt(i);
            if (pCurrChar == piChar) {
                ++sameCount;
            } else {
                String mappedStrOfpiChar = map[piChar];
                strLengthLeft -= (mappedStrOfpiChar == null) ? 1 : mappedStrOfpiChar.length();
            }
        }
        return (int) (strLengthLeft / sameCount);
    }
}
