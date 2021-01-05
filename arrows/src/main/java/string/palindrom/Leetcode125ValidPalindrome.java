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

/**
 * <pre>
 * considering only alphanumeric characters and ignoring cases.
 * string might be empty? we define empty string as valid palindrome.
 *
 * Two Pointers
 * String
 *
 * @see <a href="https://leetcode.com/problems/valid-palindrome/">leetcode</a>
 */
public class Leetcode125ValidPalindrome {

    static public boolean isAlphanumeric(char c) {
        return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z' || '0' <= c && c <= '9';
    }

    // 11ms
    public static boolean isPalindrome(String s) {
        int l = -1, r = s.length();
        while (l <= r) {
            l++;
            while (l < r && !(isAlphanumeric(s.charAt(l)))) {
                l++;
            }

            if (l == r) {
                return true;
            }
            r--;
            while (l < r && !(isAlphanumeric(s.charAt(r)))) {
                r--;
            }
            if (r == l) {//odd
                return true;
            }
            if (Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r))) {
                return false;
            }
        }
        return true;
    }

    // 32 ms
    public static boolean isPalindrome2(String str) {
        String s = str.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
        String rev = new StringBuffer(s).reverse().toString();
        return s.equals(rev);
    }
}
