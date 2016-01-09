//  Copyright 2016 The Minorminor Open Source Project
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

import java.util.regex.Pattern;

/**
 * Given a string S, find the longest palindromic substring in S.
 * You may assume that the maximum length of S is 1000,
 * and there exists one unique longest palindromic substring.
 */
public class Palindrome {

    private static boolean isValidPalindromeChar(char a) {
        // Assume the Palindrome content take account of word character only
        // \w	A word character: [a-zA-Z0-9]
        return a != '_' && Pattern.compile("\\w").matcher(String.valueOf(a)).find();
    }

    private static boolean isPalindrome(String in) {
        in = Pattern.compile("\\W").matcher(in.toLowerCase()).replaceAll("");
        in = Pattern.compile("_").matcher(in.toLowerCase()).replaceAll("");
        String reversed = new StringBuffer(in).reverse().toString();
        return in.equalsIgnoreCase(reversed);
    }

    private static String theLongerStr(String a, String b) {
        int max = Math.max(a.length(), b.length());
        return a.length() == max ? a : b;
    }

    public static String findTheLongestPalindromicSubStrOf(String in) {
        String lowerIn = in.toLowerCase();
        String re = "";
        for (int i = 0; i < in.length(); i++) {
            char a = lowerIn.charAt(i);
            if (isValidPalindromeChar(a)) {
                int indexFrom = in.length();
                while (indexFrom > i) {
                    int indexOfFound = lowerIn.lastIndexOf(a, indexFrom);

                    if (indexOfFound == -1 || indexOfFound == i) {
                        break;
                    }

                    String subStr = in.substring(i, indexOfFound + 1);
                    if (isPalindrome(subStr)) {
                        re = theLongerStr(re, subStr);
                        break;
                    }

                    indexFrom = indexOfFound - 1;
                }
            }
        }
        return re;
    }
}
