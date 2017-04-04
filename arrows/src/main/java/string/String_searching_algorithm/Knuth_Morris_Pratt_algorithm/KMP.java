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

package string.String_searching_algorithm.Knuth_Morris_Pratt_algorithm;

import java.util.Arrays;

/**
 * <a href ="https://en.wikipedia.org/wiki/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm">wiki</a>
 * <pre>
 *   worst-case performance is O(k⋅n)
 *   s[] length is n
 *   W[] length is k
 *   e.g.    s[] =  AAAAAA
 *           W[] =  AAB
 */
public class KMP {
    /**
     * <pre>
     * get Partial Match Table of pattern str
     * assume str length is at least 1.
     * assume str does not contain supplementary character
     * <img src="../../../../resources/KMP_partial_T.png" height="450" width="500">
     * O(k), where k is the length of pattern str
     *
     *
     * "Partial match" table (also known as "failure function")
     * when mismatch happen The PMT determines how much KMP will increase (variable si) and
     * where it will resume testing (variable wi). The goal of the table is to allow the algorithm not to
     * match any character of S more than once. 
     *
     * meaning of value of w[i] is in the array start with index 0 and end up with index of i (including)
     * the length of partial_match(i) which is the longest prefix start with the w[0],
     * it is also the length of the longest suffix end up with w[i] (including).
     *
     * Use of "Partial match" table:
     * get the value of "Partial match" table of pattern 'str' at index of 'indexBeforeNotMatch':
     * PMT[indexBeforeNotMatch]
     * where 'indexBeforeNotMatch' should not be -1:
     * when the str[0] is not match. moveSteps will be 1.
     * This is special case. precess it before using PMT
     */
    static int[] getPartialMatchTable(String str) {
        char[] chars = str.toCharArray();
        int[] table = new int[chars.length];

        table[0] = 0;
        for (int i = 1; i < chars.length; i++) {
            int len = table[i - 1]; // length of the previous longest prefix suffix before i.
            while (true) {// compare
                if (chars[i] == chars[len]) { // try to renew
                    table[i] = len + 1;
                    break;
                }
                if (len == 0) { // can not go back
                    table[i] = 0;
                    break;
                }
                len = table[len - 1]; // loop: go back
            }
        }
        return table;
    }

    static int[] computePartialMatchTable(String str) {
        char[] chars = str.toCharArray();
        int[] table = new int[chars.length];

        table[0] = 0;
        int i = 1;
        int len = 0;  // length of the previous longest prefix suffix
        while (i < chars.length) {
            if (chars[i] == chars[len]) {
                table[i] = len + 1;
                i++;
                len++;
            } else { // (pat[i] != pat[len])
                if (len == 0) { // if (len == 0)
                    table[i] = 0;
                    i++;
                } else {
                    len = table[len - 1];
                }
            }
        }
        return table;
    }

    // get the index of str where find the first match of give str string.
    // O(n), where n is the length of text
    static int KMP(String text, String str) {
        if (str == null || text == null) {
            throw new IllegalArgumentException("Assume the S and W str is not null");
        }
        if (str.isEmpty()) {
            return 0;
        }

        int PMTable[] = computePartialMatchTable(str);
        int i = 0; // in text
        int j = 0; // in str
        while (true) {
            if (j == str.length()) {
                return i - j;
            }
            if (i == text.length()) {
                return -1;
            }
            if (str.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            } else {
                if (j == 0) i++;
                else j = PMTable[j - 1];
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------

    static int legend_KMP(String text, String str) {
        if (text == null || str == null) {
            throw new IllegalArgumentException("Assume the S and W str is not null");
        }
        if (str.isEmpty()) {
            return 0;
        }

        int[] PMT = getPartialMatchTable(str);
        int start = 0; // in text
        int j = 0; // in str
        while (true) { // the loop executes at most 2 * str.length() times
            if (j == str.length()) { // match completely
                return start;
            }
            if (start + j == text.length()) {
                return -1; // not found
            }
            //compare
            if (text.charAt(start + j) == str.charAt(j)) {
                j++;
            } else {
                if (j == 0) { // do not use "Partial match" table
                    start++;
                } else {
                    int indexCompareFromW = PMT[j - 1];
                    int moveSteps = j - indexCompareFromW;

                    j = indexCompareFromW;
                    start += moveSteps;
                }
            }
        }
    }

    /**
     * <pre>
     * haystack = "hello world"
     * needle = "world"
     * return 6
     *
     * haystack length is m
     * needle length is n
     * O(n*(m-n))
     *
     * Give a worse case
     *
     * @param haystack
     * @param needle
     * @return index of the char from where the needle appears for the fist time.
     */
    public static int forceWay(String haystack, String needle) {
        // check corner cases
        if (haystack == null || needle == null) {
            return -1;
        }
        if (needle.isEmpty()) {
            return 0;
        }
        //
        int max = haystack.length() - needle.length();
        for (int i = 0; i <= max; i++) { // care it is <= not <
            char curC = haystack.charAt(i);
            if (curC == needle.charAt(0)) {

                boolean found = true;
                for (int j = 1; j < needle.length(); j++) {
                    if (needle.charAt(j) != haystack.charAt(i + j)) {
                        found = false;
                        break;
                    }
                }

                if (found) {
                    return i;
                }
            }
        }
        return -1;
    }
}
