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

package bitmanipulation;

/**
 * <pre>
 * 318. Maximum Product of Word Lengths
 * Difficulty: Medium
 *
 * Given a string array words, find the maximum value of
 *
 *          length(word[i])   *   length(word[j])
 *
 * where the two words do not share <strong>common</strong> letters.
 * You may assume that
 *      each word will contain only <strong>lower case</strong>letters.
 *
 * If no such two words exist, return 0.
 *
 * Example 1:
 * Given        ["abcw", "baz", "foo", "bar", "xtfn", "abcdef"]
 * Return       16
 *
 * The two words can be "abcw", "xtfn".
 *
 * Example 2:
 * Given        ["a", "ab", "abc", "d", "cd", "bcd", "abcd"]
 * Return       4
 *
 * The two words can be "ab", "cd".
 *
 * Example 3:
 * Given        ["a", "aa", "aaa", "aaaa"]
 * Return       0
 *
 * No such pair of words.
 *
 * Company Tags Google
 * Tags Bit Manipulation
 * ===================================================================
 *
 */
public class Leetcode318MaximumProductofWordLengths {
    static private int[] map = new int[26];

    static {
        for (int c = 'a'; c <= 'z'; c++) {
            map[c - 'a'] = (int) Math.pow(2d, 1d * (c - 'a'));
        }
    }

    public static int maxProduct(String[] words) {
        int r = 0;
        int[] hashs = new int[words.length];
        for (int i = 0; i < hashs.length; i++) {
            char[] word = words[i].toCharArray();
            int wordHash = 0;
            for (char c : word) {
                wordHash |= map[c - 'a'];
            }
            hashs[i] = wordHash;
            for (int j = 0; j < i; j++) {
                if ((hashs[j] & wordHash) == 0) {
                    int p = words[j].length() * words[i].length();
                    if (p > r) {
                        r = p;
                    }
                }
            }
        }
        return r;
    }
}
