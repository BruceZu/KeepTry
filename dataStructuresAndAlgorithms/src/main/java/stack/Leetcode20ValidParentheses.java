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

package stack;

import java.util.Stack;

/**
 * <pre>
 * 20. Valid Parentheses
 * Difficulty: Easy
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']',
 * determine if the input string is valid.
 *
 * The brackets must close in the correct order, "()" and "()[]{}" are all valid but "(]" and "([)]" are not.
 *
 *  Company Tags
 *  Google Airbnb Facebook Twitter Zenefits Amazon Microsoft Bloomberg
 *  Tags:
 *          Stack
 *          String
 *  Similar Problems
 *  (M) Generate Parentheses
 *  (H) Longest Valid Parentheses
 *  (H) Remove Invalid Parentheses
 *  ===================================================================
 *  Note:
 *  Stack peek() will throw EmptyStackException if the stack is empty.
 *  This is not same as Queue.peek() which return null if the queue is empty
 *  So always check Stack.isEmpty().
 *
 *    left half: push
 *    right half and match: pop
 *    right half not match; return false
 *
 * Cases:
 *      [({(())}[()])]  true
 *      [{()}]          true
 *
 *    @see <a herf="http://docs.oracle.com/javase/tutorial/essential/regex/literals.html">metacharacter</a>
 *      <([{\^-=$!|]})?*+.>
 */
public class Leetcode20ValidParentheses {
    public boolean isValid(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        if (s.length() % 2 != 0) {
            return false;
        }
        char[] stack = new char[s.length()];
        int size = 0;
        for (int i = 0, j = s.length(); i < j; i++) {
            char c = s.charAt(i);
            if (c == '[' || c == '(' || c == '{') {
                stack[size++] = c;
                continue;
            }
            if (size == 0) {
                return false;
            }

            char poped = stack[--size];
            if (poped == '[' && c == ']' || poped == '(' && c == ')' || poped == '{' && c == '}') {
                continue;
            } else {
                return false;
            }
        }
        return size == 0;
    }

    public static boolean isValid2(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        if (s.length() % 2 != 0) {
            return false;
        }
        char[] arr = s.toCharArray();
        Stack<Character> ls = new Stack();
        ls.push(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            char c = arr[i];
            if (c == '(' || c == '{' || c == '[') { //  left half
                ls.push(c);
                continue;
            }
            if (ls.empty()) {
                return false;
            }
            char top = ls.pop();
            switch (top) {
                case '(':
                    if (c == ')') {
                        continue;
                    }
                    return false;
                case '{':
                    if (c == '}') {
                        continue;
                    }
                    return false;
                case '[':
                    if (c == ']') {
                        continue;
                    }
                    return false;
            }
        }
        return ls.empty();
    }

    // very slow
    public static boolean isValid3(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        if (s.length() % 2 != 0) {
            return false;
        }
        boolean done = false;
        while (!done) {
            done = true;
            if (s.contains("{}") || s.contains("[]") || s.contains("()")) {
                done = false;
                s = s.replaceAll("(\\Q{\\E})?", "");
                s = s.replaceAll("(\\[\\])?", "");
                s = s.replaceAll("(\\(\\))?", "");
            } else {
                continue;
            }
        }
        return s.length() == 0;
    }
}
