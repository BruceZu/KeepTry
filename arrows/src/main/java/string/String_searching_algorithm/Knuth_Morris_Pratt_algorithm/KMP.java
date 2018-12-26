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
 *   O(m+n)  s[] length is n, W[] length is m
 */
public class KMP {
    /**
     * <pre>
     * Partial Match Table("failure function") of W
     * - Assume W length is at least 1.
     * - Assume W does not contain supplementary character
     * <img src="../../../../resources/KMP_partial_T.png" height="450" width="500">
     * O(m), where m is the length of W
     *
     * The PMT determines the distance and how to decide the wi and si.
     * The goal of the table is: not to let any character of S match successfully more than once .
     *
     * Meaning of value of T[i] is:
     * - The length of LP/S of substring with index [0, i-1]
     * - The index of W to which try to compare the current S[si] if current W[i] is ame same as S[si].
     *
     * When index is 0 and 1 there is no proper suffix and prefix of the given substring.
     * when the W[0] is not match. move W 1 index it is same as si++.
     * This is the meaning of -1 of T[0]; This is special case handled before using PMT.
     *
     * When the W[1] is not match the only choice is move one step to try to compare W[0] with current S[si]
     * This is the meaning of 0 of T[0].
     * The performance improvement here is if W[0] is equal to w[1], means
     * W[0] is also not same as current S[si], so T[1]=T[0]=-1.
     * In every calculating step this performance improvement is applied.
     */
    static int[] newIndexToTry(String word) {
        if (word == null || word.isEmpty()) return null;
        char[] W = word.toCharArray();
        int[] T = new int[W.length];

        int i = 0; // index in T[]
        int l = -1; // length of the LP/S of string end up at W[i-1].
        T[i] = l;
        // T[0]=-1; if S[si] is not same as W[0], S[si] need align with -1 of W to let
        // S[si+1] compare W[0]

        l++;
        i++;
        while (i < W.length) {
            T[i] = l;
            char c = W[i];
            if (W[l] == c) { // So w[l] is also not same as current s[si].
                T[i] = T[l]; // * Performance feature. T[i] can use the value of T[l]. Not affect l
            }
            // W[i] != W[l]. W[l] has the possibility it is same with s[si]. Need try.
            // Done
            // Prepare LP/S of substr [0~i] for calculating T[i+1] via
            // looking for the index of the end char of LP of LP/S of string [0~i], W[index] == c
            while (l >= 0 && W[l] != c) l = T[l];
            // 1> W[l] == c, need not fall back to look for index matching W[index]==c.
            // 2> Found. At least at index=0. in this case if S[si] is not same as W[i+1]. S[si]
            // will compare with W[1] later.
            // 3> Not found the index where W[index]==c. Now l is -1. For substring [0~i] the
            // length of LP/S is 0. If S[si] is not same as W[i+1]. S[si] will compare with W[0]
            // later.

            i++;
            l++; // The index of last char of LP, c, becomes the length of LP now.
        }

        return T;
    }
    // Get the index in S of the first match of give W string.
    // O(n+m), n is the length of S, m is the length of W
    static int firstMatchPosition(String S, String W) {
        if (W == null || S == null || W.length() > S.length()) {
            return -1;
        }

        if (W.isEmpty()) {
            return 0;
        }
        if (S.isEmpty()) return -1;

        int NI[] = newIndexToTry(W);
        int si = 0;
        int wi = 0;
        while (si < S.length()) { // when si is S.length; firstly check wi, then check si.
            if (W.charAt(wi) == S.charAt(si)) {
                // both wi and si go ahead 1 step.
                si++;
                wi++;
                if (wi == W.length()) {
                    return si - W.length();
                }
            } else {
                if (NI[wi] == -1) si++; //  wi is 0 now, si go ahead 1 step
                else
                    wi = NI[wi]; // fantastic. si is still, wi is backtracking try, at most wi steps
            }
        }
        return -1;
    }

    // ---------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        System.out.println(KMP.firstMatchPosition("ABCABD", "CA"));
        System.out.println(KMP.firstMatchPosition("ABAABAAC", "CA"));

        System.out.println(Arrays.toString(KMP.newIndexToTry("ABCDABD")));
        System.out.println(Arrays.toString(KMP.newIndexToTry("ABACABABC")));
        System.out.println(Arrays.toString(KMP.newIndexToTry("PARTICIPATE IN PARACHUTE")));
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
    public static int forceWay /*used for test*/(String haystack, String needle) {
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
