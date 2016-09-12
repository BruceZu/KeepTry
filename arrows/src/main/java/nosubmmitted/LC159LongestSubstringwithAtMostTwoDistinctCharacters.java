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
 * 159. Longest Substring with At Most Two Distinct Characters
 * https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/
 * Difficulty: Hard <pre>
 * Given a string, find the length of the longest substring T that contains
 * at most 2 distinct characters.
 * <p/>
 * For example, Given s = “eceba”,
 * <p/>
 * T is "ece" which its length is 3.
 * <p/>
 * Hide Company Tags Google
 * Hide Tags: Hash Table, Two Pointers, String
 * Hide Similar Problems (M) Longest Substring Without Repeating Characters (H) Sliding Window Maximum (H) Longest Substring with At Most K Distinct Characters
 */
public class LC159LongestSubstringwithAtMostTwoDistinctCharacters {

    /**
     * the fast one currently beat 97.75%
     * Java Two pointers solution beat 100%
     */
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s.isEmpty()) return 0;
        int max = 1;
        int p1 = 0, p2 = 0;
        int last = 1;
        char[] chars = s.toCharArray();
        for (int i = 1; i < chars.length; i++) {
            if (p1 != p2 && chars[i] != chars[p1] && chars[i] != chars[p2]) {
                if (last > max) max = last;

                last = i - p1;
                p1 = p2;
                p2 = i;
            } else {
                if (chars[i] == chars[p1]) {
                    p1 = p1 == p2 ? i : p2;
                }
                last++;
                p2 = i;
            }
        }

        if (last > max) max = last;
        return max;
    }
    /**
     * second fast beat 94.89%
     */
    // Define p is the index of rightmost element which is distinct from s[i]
    // if s[p] == s[i] it means there is only one distinct element
    // Define len to be the instant max length of subtring with max-2-distinct ended with s[i]
    public int lengthOfLongestSubstringTwoDistinct2(String s) {
        if (s.isEmpty()) return 0;
        char[] c = s.toCharArray();
        int ans = 1;
        for (int p = 0, len = 1, i = 1; i < c.length; i++) {
            if (c[i] == c[p] || c[p] == c[i - 1]) {
                len++;
                p = i - 1;
            } else if (c[i] == c[i - 1]) len++; // no need to update p, increases len
            else {
                len = i - p; //find a new third distinct character, discard the first
                p = i - 1;
            }
            if (ans < len) ans = len;
        }
        return ans;
    }
}
