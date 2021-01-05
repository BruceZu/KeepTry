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

package string.palindrom;

public class PalindromeSpecial {

    private static boolean isPalindrome(String s, int l, int r) {
        int i = l, j = r;
        while (i < j && s.charAt(i) == s.charAt(j)) {
            i++;
            j--;
        }
        if (i < j) {
            return false;
        }
        return true;
    }

    // find max palindrome from index start
    private static String maxPalindromeStart(String s, int start) {
        String p = null;
        char c = s.charAt(start);
        int lastIndex = s.lastIndexOf(c, s.length() - 1);

        while (start < lastIndex) {
            if (isPalindrome(s, start, lastIndex)) {
                return s.substring(start, lastIndex + 1);
            }

            lastIndex = s.lastIndexOf(c, lastIndex - 1);
        }
        return p == null ? "" : p;
    }


    public static String findTheLongestPalindromicSubStrOf(String in) {
        if (in == null || in.length() <= 1) {
            return in;
        }

        String re = "";
        for (int i = 0; i < in.length() - re.length(); i++) {
            String p = maxPalindromeStart(in, i);
            re = p.length() > re.length() ? p : re;
        }
        return re;
    }
}
