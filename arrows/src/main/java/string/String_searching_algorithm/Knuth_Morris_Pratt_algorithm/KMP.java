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
     * get Partial Match Table of pattern w
     * assume w length is at least 1.
     * assume w does not contain supplementary character
     * <img src="../../../../resources/KMP_partial_T.png" height="450" width="500">
     * O(k), where k is the length of pattern W
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
     * get the value of "Partial match" table of pattern 'W' at index of 'indexBeforeNotMatch':
     * PMT[indexBeforeNotMatch]
     * where 'indexBeforeNotMatch' should not be -1:
     * when the w[0] is not match. moveSteps will be 1.
     * This is special case. precess it before using PMT
     */
    static int[] getPartialMatchTable(String pattern) {
        char[] w = pattern.toCharArray();
        int[] PMT = new int[w.length];

        PMT[0] = 0;
        for (int i = 1; i < w.length; i++) {
            int indexTryToRenew = PMT[i - 1]; // length of partial_match(i-1)
            while (true) {
                if (w[i] == w[indexTryToRenew]) { // try renew
                    PMT[i] = indexTryToRenew + 1;
                    break;
                }
                if (indexTryToRenew == 0) { // can not go back
                    PMT[i] = 0;
                    break;
                }
                indexTryToRenew = PMT[indexTryToRenew - 1];
            }
        }
        return PMT;
    }


    // get the index of s where find the first match of give w string.
    // O(n), where n is the length of S
    static int KMP(String s, String w) {
        if (s == null || w == null) {
            throw new IllegalArgumentException("Assume the S and W str is not null");
        }
        if (w == "") {
            return 0;
        }

        int[] PMT = getPartialMatchTable(w);
        int startInS = 0; // in S, the beginning index to compare with W
        int wi = 0; // in W, the index of the current character
        while (true) { // the loop executes at most 2 * S.length() times
            if (wi == w.length()) { // match completely
                return startInS;
            }
            if (startInS + wi == s.length()) {
                return -1; // not found
            }
            // continue compare
            if (s.charAt(startInS + wi) == w.charAt(wi)) {
                wi++;
            } else { // Does Not match, need move
                if (wi == 0) { // do not use "Partial match" table
                    startInS++;
                } else {
                    int indexCompareFromW = PMT[wi - 1];
                    int moveSteps = wi - indexCompareFromW;

                    wi = indexCompareFromW;
                    startInS += moveSteps;
                }
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------
    static int moveSteps(String w, int notMatchIndex) {
        return notMatchIndex - getPartialMatchTable(w)[notMatchIndex - 1];
    }

    public static void main(String[] args) {
        // PMT
        System.out.println(Arrays.toString(getPartialMatchTable("A"))); // [0]
        System.out.println(Arrays.toString(getPartialMatchTable("AA"))); //  [0, 1]
        System.out.println(Arrays.toString(getPartialMatchTable("AB"))); // [0, 0]
        System.out.println(Arrays.toString(getPartialMatchTable("ABCAB"))); //  [0, 0, 0, 1, 2]
        // move steps
        System.out.println("----");
        System.out.println(moveSteps("AB", 1)); // 1
        System.out.println(moveSteps("AAB", 2)); // 1
        System.out.println(moveSteps("ABX", 2)); // 2
        System.out.println(moveSteps("ABCABN", 5)); // 3
        System.out.println("----");
        // KMP
        System.out.println(KMP("ABCABD", "CA")); // 2
        System.out.println(KMP("ABAABAAC", "CA")); // -1
        System.out.println(KMP("ABAABAAC", "ABAAC")); // 3
        System.out.println(KMP("ABYABYABYABZ", "ABYABYABZ")); // 3
        System.out.println(KMP("AAAAB", "AAAB")); // 1
        System.out.println(KMP("AAAAB", "")); // 0
        System.out.println(KMP("", "")); // 0
        System.out.println(KMP("", "AA")); // -1
        System.out.println(KMP("ABC ABCDAB ABCDABCDABDE", "ABCDABD")); // 15
        System.out.println(KMP("CGTGCCTACTTACTTACTTACTTACGCGAA", "CTTACTTAC"));//8
        System.out.println(KMP("BBBBBBBBBB", "ABBBB"));//-1
        System.out.println(index_slowSolution("dddddddddd", "ddddds"));
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
    public static int index_slowSolution(String haystack, String needle) {
        // check corner cases
        if (haystack == null || needle == null) {
            return -1;
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
