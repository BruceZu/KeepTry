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
 * <pre>
 * Difficulty: Hard
 *
 * Implement regular expression matching with support for '.' and '*'.
 *
 *   '.' Matches any single character.
 *   '*' Matches zero or more of the preceding element.
 *
 * The matching should cover the entire input string (not partial).
 *
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
 * ===========================================================
 * TDD
 * s= "aa", p="a"
 * s= "a",  p="ab*"
 * s= "ab", p= ".*c"
 * s= "a",  p= "ab*"
 * s= "aa", p= "a*" or "a*aa"
 * s= "aaa",  p= "a*a"
 * s= "",     p= ".*"
 * s= "aaab", p="a*b"
 *
 *   1 before compare current char of each other.
 *    <strong>firstly checking the next char</strong> in 'p' to see if it is exist and it is *
 *           is so:
 *              check if it is representable:
 *                        .*
 *                        or
 *                        both current chars in 'p' and 's' are same
 *                  if so: means backtracking all possible:
 *                      for .* :  represent 0, 1, ... till the last member.
 *                      for [any char]*  represent 0, 1, ... till the last [any char].
 *                  after try all possible, if still failed. e.g.
 *                  //  aaab,  a*b
 *                         |     |
 *                  try next one after [any char] in s. by pi+2 in p.
 *             else no representable, take the * in .* or [any char]* as zero.
 *                 e.g. // cba, a*.ba
 *  2  else check current char is '.' || current chars are same
 *            then continue
 *  3  else false;
 *
 *
 * Note: bounder check
 * Follow up:
 *
 *  the given string contains '.' or '*' ?
 *  can the '*'  be used independently?
 *
 *  cache the backtracking parts
 *
 * @see <a href="https://leetcode.com/problems/regular-expression-matching/">leetcode</a>
 */

public class Leetcode10RegularExpressionMatching {

    private static boolean backtracking2(char[] s, int si, char[] p, final int pi) {
        if (si == s.length && pi == p.length) {
            return true;
        }

        if (pi + 1 < p.length && p[pi + 1] == '*') {
            final int nextPIndex = pi + 2;
            while (si < s.length && (p[pi] == '.' || p[pi] == s[si])) {
                if (backtracking2(s, si, p, nextPIndex)) {
                    return true;
                } else {
                    si++; //care
                }
            }
            // all possible represent (== relation) is tried
            return backtracking2(s, si, p, pi + 2); // care

        } else if (pi < p.length && p[pi] == '.'
                || pi < p.length && si < s.length && p[pi] == s[si]) {
            return backtracking2(s, si + 1, p, pi + 1);
        } else {
            return false;
        }
    }

    public static boolean isMatch(String s, String p) {
        if (s == null) {
            return false;
        }
        if (s.equals(p) || p == ".*") {
            return true;
        }
        return backtracking2(s.toCharArray(), 0, p.toCharArray(), 0);
    }

    public static void main(String[] args) {
        System.out.println(isMatch("aaab", "a*b"));
    }
}