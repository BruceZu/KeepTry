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

public class Leetcode20ValidParentheses2 {
    public static boolean isValid(String s) {
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
            if (top == '(' && c == ')' || top == '{' && c == '}' || top == '[' && c == ']') {
                continue;
            }
            return false;

        }
        return ls.empty();
    }
}
