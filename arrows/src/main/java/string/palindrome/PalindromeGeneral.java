//  Copyright 2021 The KeepTry Open Source Project
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

package string.palindrome;

import java.util.regex.Pattern;

/**
 * <pre>
 * Given a string S, find the longest palindromic substring in S.
 * You may assume that the maximum length of S is 1000,
 * and there exists one unique longest palindromic substring.
 *
 * Assume palindrome do not care:
 * 1.  the difference of capital and lowercase
 * 2.  the '\W'
 * 3.  the words, care only word character.
 */
public class PalindromeGeneral {

    // Improvement
    private static boolean isValidPalindromeChar(char a) {
        // Assume the Palindrome content only care character,
        // \w	A word character: [a-zA-Z_0-9]
        return a != '_' && Pattern.compile("\\w").matcher(String.valueOf(a)).find();
    }

    private static boolean isPalindrome(String s, int l, int r) {
        String lowerAlphabet = cleanAndLowerCase(s.substring(l, r + 1));

        // String reversed = new StringBuilder(lowerAlphabet).reverse().toString();
        // return lowerAlphabet.equals(reversed);
        int i = 0, j = lowerAlphabet.length() - 1;
        while (i < j && lowerAlphabet.charAt(i) == lowerAlphabet.charAt(j)) {
            i++;
            j--;
        }
        if (i < j) {
            return false;
        }
        return true;
    }

    // find max palindrome from index start
    private static String maxPalindromeStart(String origal, int start) {
        String p = null;

        // ignore the difference capital and lowercase
        String lower = origal.toLowerCase();
        char c = lower.charAt(start);
        int lastIndex = lower.lastIndexOf(c, lower.length() - 1);

        while (start < lastIndex) {
            if (isPalindrome(lower, start, lastIndex)) {
                return origal.substring(start, lastIndex + 1);
            }

            lastIndex = lower.lastIndexOf(c, lastIndex - 1);
        }
        return p == null ? "" : p;
    }

    public static String cleanAndLowerCase(String in) {
        in = Pattern.compile("\\W").matcher(in.toLowerCase()).replaceAll("");
        return Pattern.compile("_").matcher(in.toLowerCase()).replaceAll("");
    }

    public static String findTheLongestPalindromicSubStrOf(String in) {
        if (in == null || in.length() <= 1) {
            return in;
        }

        String re = "";
        for (int i = 0; i < in.length() - re.length(); i++) {
            if (isValidPalindromeChar(in.charAt(i))) {// Note
                String p = maxPalindromeStart(in, i);
                re = p.length() > re.length() ? p : re;
            }
        }
        return re;
    }
}
