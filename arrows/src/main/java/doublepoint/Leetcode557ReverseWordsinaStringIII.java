//  Copyright 2017 The keepTry Open Source Project
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

package doublepoint;

/**
 * <a href="https://leetcode.com/problems/reverse-words-in-a-string-iii/#/description">Leetcode</a>
 */
public class Leetcode557ReverseWordsinaStringIII {
    // Given a string, you need to reverse the order of characters in each word within a sentence
    // while still preserving whitespace and initial word order.

    // In the string, each word is separated by single space
    // and there will not be any extra space in the string.
    static public String reverseWords(String s) {
        if (s == null || s.length() <= 1) return s;
        char[] chars = s.toCharArray();
        int r = 0;
        while (r < chars.length && r == ' ') {
            r++;
        }
        int l = r;
        while (r < chars.length) {
            while (r < chars.length && chars[r] != ' ') r++;
            swap(chars, l, r - 1);
            l = r + 1;
            r++;
        }
        return new String(chars);
    }

    static private void swap(char[] chars, int l, int r) {
        while (l < r) {
            if (chars[l] != chars[r]) {
                chars[l] ^= chars[r];
                chars[r] ^= chars[l];
                chars[l] ^= chars[r];
            }
            l++;
            r--;
        }
    }

    public static void main(String[] args) {
        System.out.println(reverseWords("Let's take LeetCode contest"));
    }
}
