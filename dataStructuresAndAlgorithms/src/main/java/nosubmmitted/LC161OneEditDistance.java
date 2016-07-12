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
 * 161. One Edit Distance
 * https://leetcode.com/problems/one-edit-distance/
 * <pre>
 *     Difficulty: Medium
 * Given two strings S and T, determine if they are both one edit distance apart.
 *
 * Hide Company Tags Snapchat Uber Facebook Twitter
 * Hide Tags String
 * Hide Similar Problems (H) Edit Distance
 * Have you met this question in a real interview? Yes  No
 * Discuss
 *
 * </pre>
 */
public class LC161OneEditDistance {
    /**
     * the fast one currently
     * beat 93.69%
     */
    public boolean isOneEditDistance(String s, String t) {

        int s1 = -1, s2 = s.length(), t1 = -1, t2 = t.length();
        char[] sArr = s.toCharArray(), tArr = t.toCharArray();

        if (Math.abs(s2 - t2) > 1) {
            return false;
        }

        while (s1 < s2 - 1 && t1 < t2 - 1 && sArr[s1 + 1] == tArr[t1 + 1]) {
            s1++;
            t1++;
        }

        // equals
        if (s2 == t2 && s1 == s2 - 1) {
            return false;
        }

        while (s2 > 0 && t2 > 0 && sArr[s2 - 1] == tArr[t2 - 1]) {
            s2--;
            t2--;
        }

        return Math.max(s2 - s1, t2 - t1) < 3;
    }

    /**
     * same as above
     * I referenced https://leetcode.com/discuss/55496/simple-java-solution-o-n-time-%26-o-1-space but did some improvement:
     * Basic idea is as follows: Using 2 pointers to go through each string and compare if the characters are the same. When there is an disagreement, jump out of inner loop and increase one of the pointers by 1. (if s.length() < t.length(), increase the pointer of t, otherwise increase that of s)
     * <p/>
     * Return true if we reach the end of either of them, otherwise check if the disagreement occurs for the first time. If so make increment indicated above, otherwise return false. (since in this situation, the distance apart is more than 1)
     * <p/>
     * (1) do not use equals(), that add another O(n) burden.
     * <p/>
     * (2) define longer and shorter, so focus on shorter's end.
     * <p/>
     * (3) when shorter reaches end, cnt==0 still might be true situation. For example, "a" and "ab", thus refer to their difference in length.
     */
    public boolean isOneEditDistance2(String s, String t) {
        char[] longer = s.length() > t.length() ? s.toCharArray() : t.toCharArray();
        char[] shorter = s.length() > t.length() ? t.toCharArray() : s.toCharArray();

        if (longer.length - shorter.length > 1) return false;

        int cnt = 0;

        int i = 0, j = 0;
        while (true) {
            while (j < shorter.length && longer[i] == shorter[j]) {
                ++i;
                ++j;
            }

            if (j == shorter.length) return !(cnt == 0) || longer.length - shorter.length == 1;
            if (++cnt > 1) return false;

            // same length, replace
            if (longer.length == shorter.length) {
                ++i;
                ++j;
            } else { //delete from longer
                ++i;
            }
        }
    }

    /**
     * other idea scan from head of two strings until to index l where two characters are different.

     scan from tails of two strings until to index r1, r2 where two characters are different.

     y1 = r1 - l + 1, y2 = r2 - l + 1 are the different characters number

     y1, y2 should only be 1,0 or 0,1 or 1,1.

     I could have make the code shorter but I don't want to sacrifice clarity.
     \
     */
}
