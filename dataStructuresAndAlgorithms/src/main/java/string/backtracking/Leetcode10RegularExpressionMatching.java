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

package string.backtracking;

/**
 * Difficulty: Hard
 * Implement regular expression matching with support for '.' and '*'.
 * <pre>
 * '.' Matches any single character.
 * '*' Matches zero or more of the preceding element.
 * The matching should cover the entire input string (not partial).
 * The function prototype should be:
 * bool isMatch(const char *s, const char *p)
 * Some examples:
 * isMatch("aa","a") → false
 * isMatch("aa","aa") → true
 * isMatch("aaa","aa") → false
 * isMatch("aa", "a*") → true
 * isMatch("aa", ".*") → true
 * isMatch("ab", ".*") → true
 * isMatch("aab", "c*a*b") → true
 *
 * Tags: Dynamic Programming, Backtracking, String
 */

public class Leetcode10RegularExpressionMatching {
}

class Solution {
    /**
     * <pre>
     * need back-tracing when it is
     * ".*" or  x*.
     *          |
     *         a char same as the given char
     *
     * @param s
     * @param is current index of string s.
     * @param p
     * @param ip current index of pattern p.
     * @return
     */
    public boolean byBackTracing(char[] s, int is, char[] p, int ip) {
        if (cache[is][ip] != null) {
            return cache[is][ip];
        }
        // index valid check
        if (is == s.length && ip == p.length) { // both end
            return true;
        }

        if (ip + 1 < p.length && p[ip + 1] == '*') {
            while (ip < p.length && is < s.length
                    && p[ip + 1] == '*' && p[ip] == '.' ? true : is < s.length && p[ip] == s[is]) {
                // back-tracing and cache
                if (cache[is][ip + 2] = byBackTracing(s, is, p, ip + 2)) {
                    return true;
                }
                is++;
            }
            return byBackTracing(s, is, p, ip + 2); // take it as no existing, * is zero

        } else if (ip < p.length && p[ip] == '.'
                || ip < p.length && is < s.length && p[ip] == s[is]) {
            return byBackTracing(s, is + 1, p, ip + 1);
        }

        return false;
    }

    Boolean[][] cache;

    public boolean isMatch(String s, String p) {
        if (p.equals(s) || p.equals(".*")) {
            return true;
        }
        cache = new Boolean[s.length() + 3][p.length() + 3];
        return byBackTracing(s.toCharArray(), 0, p.toCharArray(), 0);
    }
}