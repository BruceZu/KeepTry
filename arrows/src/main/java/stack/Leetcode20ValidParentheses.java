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
 *
 *  Tags:
 *          Stack
 *          String
 * ==================
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
 *      [( {(( ))} [()] )]  true
 *      [{(  )}]          true
 *      @see <a href="https://leetcode.com/problems/valid-parentheses/">leetcode</a>
 */
public class Leetcode20ValidParentheses {
    public static boolean isValid(String s) {
        char[] arr = s.toCharArray();
        Stack<Character> st = new Stack();
        for (char i : arr) {
            if (i == '{' || i == '[' || i == '(') {
                st.push(i);
                continue;
            }
            if (!st.isEmpty()) {
                char top = st.pop();
                if (top == '{' && i == '}' || top == '[' && i == ']' || top == '(' && i == ')') {
                    continue;
                }
                return false;
            }
            return false;
        }
        return st.isEmpty();
    }
}
