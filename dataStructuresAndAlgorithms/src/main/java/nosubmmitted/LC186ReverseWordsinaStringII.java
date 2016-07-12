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

package nosubmmitted;

/**
 * 186. Reverse Words in a String II
 * https://leetcode.com/problems/reverse-words-in-a-string-ii/
 * <pre>
 *     Difficulty: Medium
 * Given an input string, reverse the string word by word.
 * A word is defined as a sequence of non-space characters.
 *
 * The input string does not contain leading or trailing spaces and the words
 * are always separated by a single space.
 *
 * For example,
 * Given s = "the sky is blue",
 * return "blue is sky the".
 *
 * Could you do it in-place without allocating extra space?
 *
 * Related problem: Rotate Array
 *
 * Hide Company Tags: Amazon Microsoft Uber
 * Hide Tags: String
 * Hide Similar Problems (M) Reverse Words in a String (E) Rotate Array
 *
 * </pre>
 */
public class LC186ReverseWordsinaStringII {

    // beat 99%

    /**
     * beat 52.03%
     *
     * @param s
     */
    public void reverseWords2(char[] s) {
        reverse2(s, 0, s.length - 1);  // reverse the whole string first
        int r = 0;
        while (r < s.length) {
            int l = r;
            while (r < s.length && s[r] != ' ')
                r++;
            reverse2(s, l, r - 1);  // reverse words one by one
            r++;
        }
    }

    public void reverse2(char[] s, int l, int r) {
        while (l < r) {
            char tmp = s[l];
            s[l++] = s[r];
            s[r--] = tmp;
        }
    }

    // same speed
    public static void reverse(char[] s, int start, int end) {
        for (int i = start, j = end; i < j; i++, j--) {
            char tmp = s[i];
            s[i] = s[j];
            s[j] = tmp;
        }
    }

    public static void reverseWords(char[] s) {
        int len = s.length, i, j;
        reverse(s, 0, len - 1);
        for (i = 0, j = 0; j < len; j++) {
            if (s[j] == ' ') {
                reverse(s, i, j - 1);
                i = j + 1;
            }
        }
        reverse(s, i, len - 1);
    }
}
