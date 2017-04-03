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
 *
 *   0 :  boundary checking
 *        note as * can be 0 so: when only str is over not means it is false.
 *   1 before compare current char of each other.
 *    <strong>firstly checking the next char</strong> in 'p' to see if it is exist and it is *
 *            if so:
 *               when * means 0, it means match and continue next pair checking: char after * with cur char in str.
 *               if false:
 *                  checking cur char in str is same with cur char in pattern :
 *                  if same means  * can be 1 or 2 .... and it is match for current checking
 *                                 next pair from chur after * and next char in str.
 *                  else means * can not work any more without the support from str current char, as all possible
 *                  options are checked without success result, return false and backtracking is over for current *.
 *  2  from now on it is not case of * so need plus the boundary checking for str which is opened for * case.
 *  3  else check current char is '.' || current chars are same
 *            then continue
 *          else false;
 *
 *
 * Note: bounder check
 * Follow up:
 *
 *  the given string contains '.' or '*' ?
 *  can the '*'  be used independently?
 *
 *  cache the backtracking parts
 *  DP see {@link dp.Leetcode10RegularExpressionMatching2#isMatch(String, String)}
 * @see <a href="https://leetcode.com/problems/regular-expression-matching/">leetcode</a>
 */

public class Leetcode10RegularExpressionMatching {

    private static boolean compare_cur_char_backtrackingStarCase(char[] str, int index, char[] ptern, final int i) {
        // boundary checking plus
        if (i == ptern.length && index == str.length) {
            return true;
        }
        if (i == ptern.length) { // * may means 0. e.g. 'a', 'ab*'
            return false;
        }

        // check cur char pair
        char curCharInPattern = ptern[i];
        if (i + 1 < ptern.length && ptern[i + 1] == '*') {
            final int indexOfPostStarChar = i + 2;
            // when * means 0 , same and continue next pair
            if (compare_cur_char_backtrackingStarCase(str, index, ptern, indexOfPostStarChar)) {
                return true;
            } else {
                // backtracking happen
                while (index < str.length && (str[index] == curCharInPattern || curCharInPattern == '.')) {
                    ++index;
                    if (compare_cur_char_backtrackingStarCase(str, index, ptern, indexOfPostStarChar)) {
                        return true;
                    }
                }
                return false; // all possible options are checked
            }
        }

        if (index == str.length) { // plus boundary checking.
            return false;
        }

        if (str[index] == curCharInPattern || curCharInPattern == '.') {//else can be saved
            return compare_cur_char_backtrackingStarCase(str, index + 1, ptern, i + 1);
        } else {//else can be saved
            return false;
        }
    }

    public static boolean isMatch(String str, String p) {
        if (str == null) {
            return false;
        }
        //       performance improve only
//        if (str.equals(p) || p == ".*") {
//            return true;
//        }
        return compare_cur_char_backtrackingStarCase(str.toCharArray(), 0, p.toCharArray(), 0);
    }

    public static void main(String[] args) {
        System.out.println(isMatch("aa", "a*"));
        System.out.println(isMatch("aaab", "a*b"));
    }
}