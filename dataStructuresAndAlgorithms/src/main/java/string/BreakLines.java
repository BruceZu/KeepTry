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
 * <pre>
 * Please submit solutions to the following Java programming questions.
 * Feel free to use standard Java APIs in your solutions and also to compile/test
 * your code. However, the solutions should represent your own original work.
 *
 * We will be examining your solutions for correctness, efficiency, and
 * good programming practice.
 *
 * QUESTION #3:
 *
 * Write a Java method with the following method signature that takes a String and
 * returns a String formatted so that it satisfies the requirements below.  It may
 * need to insert newlines and/or delete spaces.
 *
 * Method Signature:
 *
 *      public static String wrapText(String text, int maxCharsPerLine)
 *
 * Definitions and Assumptions:
 *    A word is a nonempty sequence of characters that contains no spaces and no newlines.
 *    Lines in the return String are separated by the newline character, '\n'.
 *    Words on each line are separated by spaces. Assume that the String argument does
 *    not contain any whitespace characters other than spaces and newlines.
 *
 * Requirements:
 *
 * Requirement 1. Newlines in the String argument are preserved.
 *
 * Requirement 2. Words in the return String are separated by either a single space or by one or
 *                more newlines.
 *
 * Requirement 3. Lines in the return String do not start or end with any spaces.
 *
 * Requirement 4. When constructing the return String from the String argument, each word in the
 *                String argument with at most maxCharsPerLine characters should not be broken up.
 *                Each word in the String argument with more than maxCharsPerLine characters should
 *                be broken up so that all of the other requirements are satisfied.
 *
 * Requirement 5.  The String argument may contain lines longer than maxCharsPerLine. Newlines
 *                should be added so that each line in the return String has at most maxCharsPerLine
 *                characters. To determine where newlines should be added, try to fit as many words
 *                as possible on a line (while keeping line length at most maxCharsPerLine and
 *                satisfying the other requirements) before starting a new line.
 */
public class BreakLines {
    private static final char SPACE = ' ';
    private static final char NEW_LINE = '\n';

    private static boolean isOfWorld(char c) {
        return c != SPACE & c != NEW_LINE;
    }

    private static boolean isNotWorld(char c) {
        return !isOfWorld(c);
    }

    public static String wrapText(String text, int maxCharsPerLine) {
        if (text == null || text.length() < 1) {
            return text;
        }

        StringBuilder r = new StringBuilder(); // keep result
        int cur = 0;
        while (isNotWorld(text.charAt(cur))) { // Requirement 3
            if (text.charAt(cur) == NEW_LINE) {
                r.append(NEW_LINE); // Requirement 1, 2
            }
            cur++;
            if (cur == text.length()) {// text with only space or "\n".
                return r.toString();
            }
        }

        int lSize = 0; // current new line size
        while (true) {
            //  word
            int iWorld = cur;  // left index of world, inclusive.
            while (cur < text.length() && isOfWorld(text.charAt(cur))) {
                cur++;
            }

            if (cur == text.length()) {
                int worldLength = text.length() - iWorld;
                if (lSize + worldLength <= maxCharsPerLine) { // Requirement 5.
                    r.append(text.substring(iWorld, text.length()));
                    return r.toString();
                } else {
                    while (r.charAt(r.length() - 1) == SPACE) { // Requirement 3.
                        r.deleteCharAt(r.length() - 1);
                    }
                    r.append(NEW_LINE).append(text.substring(iWorld, text.length()));
                    return r.toString();
                }
            } else {
                // now cur is the first no world char

                int wl = cur - iWorld;   // word length
                if (lSize + wl < maxCharsPerLine) { // Requirement 5.
                    r.append(text.substring(iWorld, cur));
                    lSize += wl;
                } else if (lSize + wl == maxCharsPerLine) {
                    r.append(text.substring(iWorld, cur));
                    r.append(NEW_LINE);
                    lSize = 0;
                } else { // Requirement 4.
                    while (r.charAt(r.length() - 1) == SPACE) { // Requirement 3.
                        r.deleteCharAt(r.length() - 1);
                    }
                    r.append(NEW_LINE);
                    if (wl > maxCharsPerLine) {
                        throw new IllegalArgumentException("The max line length is too short: " + maxCharsPerLine);
                    }
                    lSize = wl;
                    r.append(text.substring(iWorld, cur));
                }
            }

            // space or '\n'
            int iSpNl = cur; // left index of space or new line '\n', inclusive.

            while (cur < text.length() && isNotWorld(text.charAt(cur))) {
                cur++;
            }
            // now cur is the index of the fist char of next world or the text length
            int indexOfNewLine = text.indexOf(NEW_LINE, iSpNl);
            boolean haveNewLine = false;
            while (iSpNl <= indexOfNewLine && indexOfNewLine < cur) {
                r.append(NEW_LINE); // Requirement 1, 3
                lSize = 0;
                haveNewLine = true;
                indexOfNewLine = text.indexOf(NEW_LINE, indexOfNewLine + 1);
            }

            if (cur == text.length()) {
                return r.toString();
            } else if (!haveNewLine) {
                int spaceOrNewLineLength = cur - iSpNl;
                if (lSize + spaceOrNewLineLength >= maxCharsPerLine) { // Requirement 4.
                    r.append(NEW_LINE);
                    lSize = 0;
                } else if (lSize != 0) { // Requirement 3, 5.
                    lSize += spaceOrNewLineLength;
                    r.append(text.substring(iSpNl, cur));
                }
            }
        }
    }
}
