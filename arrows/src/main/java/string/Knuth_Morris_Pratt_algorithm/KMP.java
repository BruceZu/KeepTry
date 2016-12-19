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

package string.Knuth_Morris_Pratt_algorithm;

import com.sun.org.apache.regexp.internal.RE;

import java.util.Arrays;
import java.util.HashMap;

/**
 * <a href ="https://en.wikipedia.org/wiki/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm">wiki</a>
 * <pre>
 *   The most straightforward algorithm
 *   s[] length is K
 *   W[] length is n
 *    worst-case performance is O(k⋅n)
 *
 *   e.g.    s[] =  AAAAAA
 *           W[] =  AAB
 */
public class KMP {
    private static HashMap<String, int[]> strToPMT = new HashMap();

    /**
     * <pre>
     *  Objective is NOT move to the next same character as the first one.
     *  when there are more common parts, each of then have different length, select the one with max length
     *
     *  KMP spends a little time precomputing a table (on the order of the size of W[], O(n)),
     *  and then it uses that table to do an efficient search of the string in O(k).
     *  PMT indicates where we need to look for the start of a new match in the event that the current one ends in a mismatch.
     *  <img src="../../../resources/sub_string_KMP.png" height="450" width="300">
     *  Note here the arr related str is the one before the no match index of w.
     *  It is [0, not match index i -1]
     *  so when the w[0] is not match. moveSteps will be 1. i will be set to be 0;
     *  This is special case. precess it before call PMT
     */
    static int[] partialMatchTable(char[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Assume the pattern str is not null and empty");
        }
        int[] v = new int[arr.length];
        v[0] = 0;

        for (int vIndex = 1; vIndex < arr.length; vIndex++) {//vIndex: index before the not match index
            // longer substring first
            for (int subStrStartIndex = 1; subStrStartIndex <= vIndex; subStrStartIndex++) {
                // filter by both side chars
                if (arr[subStrStartIndex] == arr[0] && arr[vIndex] == arr[vIndex - subStrStartIndex]) {
                    // do not check middles chars, for the case arr = "AA", no middle part.
                    int j = subStrStartIndex;
                    while (j <= vIndex) {
                        if (arr[j] != arr[j - subStrStartIndex]) {
                            break;
                        }
                        j++;
                    }
                    // if current sub string match, do not check the following shorter substring
                    if (j == vIndex + 1) {
                        v[vIndex] = vIndex - subStrStartIndex + 1;
                        break;
                    }
                }
            }
        }
        return v;
    }

    static int valueOfPMT(String pattern, int indexBeforeNotMatch) {
        if (indexBeforeNotMatch == -1) {
            throw new IllegalArgumentException("so when the w[0] is not match. moveSteps will be 1. i will be set to be 0;   " +
                    " This is special case. precess it before call PMT");
        }
        int[] pmt = strToPMT.get(pattern);
        if (pmt == null) {
            strToPMT.put(pattern, partialMatchTable(pattern.toCharArray()));
        }
        return strToPMT.get(pattern)[indexBeforeNotMatch];
    }

    static int moveSteps(String pattern, int notMatchIndex) {
        return notMatchIndex - valueOfPMT(pattern, notMatchIndex - 1);
    }

    static int KMP(String s, String w) {
        if (s == null || w == null) {
            throw new IllegalArgumentException("Assume the S and W str is not null");
        }
        if (w == "") {
            return 0;
            // reference:
            //   String.indexOf(char[] source, int sourceOffset, int sourceCount,
            //                  char[] target, int targetOffset, int targetCount,
            //                  int fromIndex) {
            //
            //
            // if (targetCount == 0) {
            //     return fromIndex;
            // }
        }
        //m, the beginning index of the current match in S
        //i, the index of the current character in W
        int m = 0;
        int i = 0;
        while (true) {
            // -------------------
//            System.out.print("\n\n");
//            for (int index = 0; index < s.length(); index++) {
//                System.out.print(index % 10);
//            }
//            System.out.println("\n" + s);
//            for (int space = 0; space < m; space++) {
//                System.out.print(" ");
//            }
//            System.out.println(w);
//            for (int space = 0; space < m; space++) {
//                System.out.print(" ");
//            }
//            for (int index = 0; index < w.length(); index++) {
//                System.out.print(index % 10);
//            }
//            System.out.println();
            // -------------------
            if (i == w.length()) { // match completely
                return m;
            }
            if (m + i == s.length()) {
                return -1; // not found
            }

            if (s.charAt(m + i) == w.charAt(i)) {
                // System.out.println(String.format("w %d match s %d", i, m + i));
                i++;
            } else {
                //System.out.println(String.format("w %d Does Not match s %d", i, m + i));
                int value;
                int moveSteps;
                if (i == 0) {
                    // when the w[0] is not match. moveStep will be 1. i =0
                    m++;
                    //System.out.println("Move steps " + 1);
                } else {
                    value = valueOfPMT(w, i - 1);
                    moveSteps = i - value;

                    i = valueOfPMT(w, i - 1);
                    m += moveSteps;
                    //System.out.println("Move steps " + moveSteps);
                }
            }
        }
    }

    public static void main(String[] args) {
        // PMT
        System.out.println(Arrays.toString(partialMatchTable("A".toCharArray())));
        System.out.println(Arrays.toString(partialMatchTable("AA".toCharArray())));
        System.out.println(Arrays.toString(partialMatchTable("AB".toCharArray())));
        System.out.println(Arrays.toString(partialMatchTable("ABCAB".toCharArray())));
        // move steps
        System.out.println("----");
        System.out.println(moveSteps("AB", 1));
        System.out.println(moveSteps("AAB", 2));
        System.out.println(moveSteps("ABX", 2));
        System.out.println(moveSteps("ABCABN", 5));
        System.out.println("----");
        // KMP
        System.out.println(KMP("ABCABD", "CA"));
        System.out.println(KMP("ABAABAAC", "CA"));
        System.out.println(KMP("ABAABAAC", "ABAAC"));
        System.out.println(KMP("ABYABYABYABZ", "ABYABYABZ"));
        System.out.println(KMP("AAAAB", "AAAB"));
        System.out.println(KMP("AAAAB", ""));
        System.out.println(KMP("", ""));
        System.out.println(KMP("", "AA"));
        System.out.println(KMP("ABC ABCDAB ABCDABCDABDE", "ABCDABD"));
    }
}
