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

package backtracing;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

/**
 * Follow up:
 * The input are distinguished unsorted numbers, value from 1 to 250, but only one number is missed.
 * Figure out the missed number.
 * The input is string containing only numbers.
 */
public class Leetcode268MissingNumber2 {
    static int STR_NUMBER = 249;

    private static int[] createTestNumbers() {
        Set<Integer> s = new HashSet<>(STR_NUMBER);
        Random r = new Random(System.currentTimeMillis());
        while (s.size() != STR_NUMBER) {
            int i = r.nextInt(STR_NUMBER + 2);
            if (i != 0) { // note
                s.add(i);
            }
        }

        int[] re = new int[STR_NUMBER];
        Iterator<Integer> ite = s.iterator();
        int i = 0;
        while (ite.hasNext()) {
            re[i++] = ite.next();
        }
        return re;
    }

    private static String toString(int[] s) {
        StringBuilder re = new StringBuilder();
        for (int i = 0; i < s.length; i++) {
            re.append(s[i]);
        }

        return re.toString();
    }

    private static int[] toInt(Set<String> strSet) {
        String[] s = new String[STR_NUMBER];
        strSet.toArray(s);

        int[] r = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            r[i] = Integer.valueOf(s[i]);
        }

        return r;
    }

    /**
     * Parse the String s into 249 numbers exactly.
     * <pre>
     * Currently start from the index of startIndex to parse a number out.
     *
     * Tag: Back trace
     *
     * @param s          Assume there are only numbers in the string, and each member should be  1<= number <=250
     * @param startIndex
     * @param parsedStrs Parsed result
     * @return
     */
    private static boolean parse(String s, int startIndex, Set<String> parsedStrs) {
        // todo input check

        int maxIndex = s.length() - (STR_NUMBER - parsedStrs.size() - 1); // note
        for (int i = startIndex; i < maxIndex; i++) {

            String cur = s.substring(startIndex, i + 1);
            int v = Integer.valueOf(cur);
            // exists already or not
            if (parsedStrs.contains(cur)) {
                continue;
            }

            // validation
            if (v > STR_NUMBER + 1  // need not continue try, back trace
                    || v == 0) { // avoid the result like ',5, 049,'
                return false;
            }

            parsedStrs.add(cur); // take it in account

            // post added check:  continue recursion or not
            if (parsedStrs.size() == STR_NUMBER && i == s.length() - 1) {
                // 1  perfect. Done
                return true;
            } else if (parsedStrs.size() == STR_NUMBER && i != s.length() - 1 ||
                    parsedStrs.size() != STR_NUMBER && i == s.length() - 1) {
                // 2 exactly failed
                parsedStrs.remove(cur); // Note: remove it.
                return false;

            }
            // 3 continue as now the status is: result.size()!=249 && i!=s.length()-1.
            // And check result
            if (parse(s, i + 1, parsedStrs)) {
                return true;
            } // 'else' again parse current next possible number
            parsedStrs.remove(cur); // Note: remove it
        }
        // Tried all without success
        return false;
    }

    //Using n ^ n = 0 and n & 0 = 0
    public static int missingNumber(int[] in) {
        int tmp = in[0];
        for (int i = 1; i < in.length; i++) {
            tmp ^= in[i] ^ i;   // in[1] ~ in[248], i: 1 ~ 248
        }
        tmp ^= in.length ^ in.length + 1;  // 249, 250
        return tmp;
    }

    // Using sum to check the missed one number
    public static int missingNumber2(int[] in) {
        int missedSum = 0;

        for (int i = 0; i < in.length; i++) {
            missedSum += in[i];
        }

        return (int) ((1 + in.length + 1) * (in.length + 1) / 2.0) - missedSum;
        // note: when in.length +1 is a odd number, need float or double
    }

    /**
     * Using boolean or byte array. with value as index.
     * Can check more missed numbers.
     */
    public static int missingNumber3(int[] in) {
        byte[] h = new byte[in.length + 2]; // note:  +2 not +1. value as index 0 ~ 250
        for (int i = 0; i < in.length; i++) {
            h[in[i]] = 1;
        }

        for (int i = 1; i < h.length; i++) { // note start from 1. value index 1 ~ 250
            if (h[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = createTestNumbers();

        String s = toString(nums);
        System.out.println("Intput:" + s);

        Set<String> parsedStrs = new LinkedHashSet<>(STR_NUMBER);
        parse(s, 0, parsedStrs);
        System.out.println("Parsed numbers       : " + Arrays.toString(parsedStrs.toArray()));

        int[] parsedNums = toInt(parsedStrs);
        int missedNumParsed = missingNumber(parsedNums);

        Arrays.sort(parsedNums);
        System.out.println("Sorted Parsed numbers: " + Arrays.toString(parsedNums));

        System.out.println("Expected  : " + missingNumber(nums));
        System.out.println("Calculated: " + missedNumParsed);
    }
}
