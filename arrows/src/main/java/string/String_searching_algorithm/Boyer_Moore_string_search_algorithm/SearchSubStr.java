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

package string.String_searching_algorithm.Boyer_Moore_string_search_algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
 * <a href= "https://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_string_search_algorithm">Boyer–Moore string search algorithm </a>
 * <a href="http://www.ruanyifeng.com/blog/2013/05/boyer-moore_string_search_algorithm.html">阮一峰</a>
 *
 * A shift is calculated by applying two rules: the bad character rule and the good suffix rule.
 * The actual shifting offset is the maximum of the shifts calculated by these rules.
 *
 * from right end of pattern to compare till
 * if all are matched: end
 * else not match found at index i of pattern:
 * bad character is in text:
 *     1> in pattern from 当前位置 to left search first occurrence index of the bad character.
 *        result is -1 when not found. 或非－1.
 *        bad character steps = current index i - found index
 *     2> if good-suffix exist:
 *           in pattern from 当前位置 to left search first occurrence index of the good-suffix str.
 *           if find : good-suffix steps = end index of sub str (or pattern length -1) - end index of found good-suffix
 *           else: do sub good-suffix.  but search from the left end
 *                 因为good-suffix 本身未被找到，这种情况下即使中间发现sub good match 和text 也不对等。
 *                 while find 非－1，or all are not found －1.
 *
 *                 good-suffix steps = end index of sub str (or pattern length -1) - end index of found sub good-suffix
 *           steps = max(bad character steps, good-suffix steps)
 *        else steps = bad character steps
 * <img src="../../../../resources/Boyer_Moore_string_search_algorithm.png" height="350" width="550">
 * It is possible to prepare a table in advance used to search the steps by good match
 * <img src="../../../../resources/good-match-sub-good-match-table.png" height="250" width="500">
 *
 *  Assume it is only 26 characters, need prepare the bad character table used to check the steps?
 *  It is possible. -1 means the bad char is not found in pattern and the steps will be:
 *   index of char in pattern which aligned with bad char  - (-1)
 *
 *  <img src="../../../../resources/badCharacterTable.png" height="650" width="500">
 *
 *  <a href="https://www.youtube.com/watch?v=izMKq3epJ-Q">link</a>
 */
public class SearchSubStr {
    // T[i] is steps to move when the good is subStr of pattern. start from index i (1~pattern.length-1)
    public static int[] goodMoveSteps(String pattern) {
        int[] steps = new int[pattern.length()];
        // possible good: always end by the last letter of pattern and start from goodLeftIndex which does not
        // includ the first one as that would be equal with (part of) Text and thus would never call this method any more.
        //
        // 1 find from the index before the goodLeftIndex and back forward towards left side.
        // 2 if it not found. then get sub-good and check its at the left side end only.
        // so in total there are 2 parts of logic and this means we can not calculate the current steps value using previous one.

        for (int goodLeftIndex = pattern.length() - 1; goodLeftIndex > 0; goodLeftIndex--) {
            /*
            x a a a
            a a a a
            */
            String currentGood = pattern.substring(goodLeftIndex);
            int indexFrom = pattern.lastIndexOf(currentGood, goodLeftIndex - 1);

            if (indexFrom != -1) {
                // good is found
                int foundLastIndex = indexFrom + currentGood.length() - 1;
                steps[goodLeftIndex] = pattern.length() - 1 - foundLastIndex;
                continue;
            }

            // sub-good
            int subGoodLeftIndex = goodLeftIndex + 1;
            while (subGoodLeftIndex <= pattern.length() - 1) {
                if (pattern.startsWith(pattern.substring(subGoodLeftIndex))) {
                    break;
                }
                subGoodLeftIndex++;
            }

            if (subGoodLeftIndex != pattern.length()) {
                // sub-good is found at the left side
                //   0 1 2 3 4 5 6 7 8 9
                //   o o d a b c g o o d
                //   |             |
                //             subGoodLeftIndex
                steps[goodLeftIndex] = subGoodLeftIndex;
            } else {
                // sub-good is not found or no sub-good
                steps[goodLeftIndex] = pattern.length();
            }
        }
        return steps;
    }

    // how to use this table:
    // check table moveSteps[bad char -'A'][index of pattern where che char align with the bad char]
    // assume only 26 capital case letters
    public static int[][] badCharMoveSteps(String pattern) {
        // assume only 26 capital-case letters
        // moveSteps[r][c] is the move steps number when char == r+'A' and
        // it is bad char in text align with char in pattern[c];
        // so the default assume is pattern[c] is not same as char == r+'A'
        int[][] moveSteps = new int[26][pattern.length()];
        // assume only 26 capital letters
        // row by row
        for (char bad = 'A'; bad <= 'Z'; bad++) {
            // row index is bad -'a'
            // column by column
            for (int alignedWithIndex = pattern.length() - 1; alignedWithIndex >= 0; alignedWithIndex--) {
                char alignWith = pattern.charAt(alignedWithIndex);
                if (alignWith == bad) {
                    // in this case bad char would not be bad
                    // we can set the value as -1 but this will never be called
                    moveSteps[bad - 'A'][alignedWithIndex] = -2;
                } else {
                    // This may be improved by reference existing value
                    int index;
                    for (index = alignedWithIndex - 1; index >= 0; index--) {
                        // in pattern find the bad char back forward from but
                        // not inclusive the aligned char
                        if (bad == pattern.charAt(index)) {
                            break;
                        }
                    }
                    moveSteps[bad - 'A'][alignedWithIndex] = alignedWithIndex - index;
                }
            }
        }
        return moveSteps;
    }

    // First index if there are more;
    public static int BM(String text, String pattern) {
        if (pattern.length() > text.length()) {
            return -1;
        }

        int[][] badMoveSteps = badCharMoveSteps(pattern);
        int[] goodMoveSteps = goodMoveSteps(pattern);
        int compareFromInText = pattern.length() - 1;
        int indexOfPattern = -1;
        while (compareFromInText < text.length()) {
            int stepsToEnd = 0;
            while (stepsToEnd <= pattern.length() - 1) {
                if (pattern.charAt(pattern.length() - 1 - stepsToEnd) != text.charAt(compareFromInText - stepsToEnd)) {
                    break;
                }
                stepsToEnd++;
            }

            if (stepsToEnd == pattern.length()) {
                // found
                break;
            }
            // not found so need move.
            // get the the bad
            int indexOfBadinText = compareFromInText - stepsToEnd;
            char bad = text.charAt(indexOfBadinText);
            int indexAlignedInPattern = pattern.length() - 1 - stepsToEnd;
            int moveSteps = badMoveSteps[bad - 'A'][indexAlignedInPattern];
            if (stepsToEnd > 0) {
                // have good
                int leftIndexOfGood = indexAlignedInPattern + 1; // take care it is + not -
                moveSteps = Math.max(moveSteps, goodMoveSteps[leftIndexOfGood]);
            }

            compareFromInText += moveSteps;
        }
        if (compareFromInText >= text.length()) {
            // not found in text
            return indexOfPattern;
        }
        return compareFromInText - (pattern.length() - 1);
    }

    public static void main(String[] args) {
        System.out.println(BM("ABCABD", "CA"));//2
        System.out.println(BM("ABAABAAC", "CA"));//-1
        System.out.println(BM("ABAABAAC", "ABAAC"));//3
        System.out.println(BM("ABYABYABYABZ", "ABYABYABZ"));//3
        System.out.println(BM("AAAAB", "AAAB"));//1
        System.out.println(BM("AAAAB", ""));//0
        System.out.println(BM("", ""));//0
        System.out.println(BM("", "AA"));//-1
        System.out.println(BM("ABCQABCDABQABCDABCDABDE", "ABCDABD"));//15
        System.out.println(BM("CGTGCCTACTTACTTACTTACTTACGCGAA", "CTTACTTAC"));//8
        System.out.println(BM("BBBBBBBBBB", "ABBBB"));//-1
    }
}
