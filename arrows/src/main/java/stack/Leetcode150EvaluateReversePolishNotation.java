//  Copyright 2017 The keepTry Open Source Project
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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * https://leetcode.com/problems/evaluate-reverse-polish-notation/#/description
 */
public class Leetcode150EvaluateReversePolishNotation {
    // O(N)
    public int evalRPN(String[] strs) {
        Deque<Integer> stack = new ArrayDeque<>();
        int a, b;
        for (String cur : strs) {
            // if (cur != "+" && cur != "-" && cur != "*" && cur != "/") {
            if (cur.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if (cur.equals("-")) {
                b = stack.pop();
                a = stack.pop();
                stack.push(a - b);
            } else if (cur.equals("*")) {

                stack.push(stack.pop() * stack.pop());
            } else if (cur.equals("/")) {
                b = stack.pop();
                a = stack.pop();
                stack.push(a / b);
            } else {
                stack.push(Integer.parseInt(cur));
            }
        }
        return stack.peek();
    }

    public int evalRPN2(String[] strs) {
        Deque<Integer> stack = new ArrayDeque<>();
        int a, b;
        for (String cur : strs) {
            switch (cur) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    b = stack.pop();
                    a = stack.pop();
                    stack.push(a - b);
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    b = stack.pop();
                    a = stack.pop();
                    stack.push(a / b);
                    break;
                default:
                    stack.push(Integer.parseInt(cur));
            }
        }
        return stack.peek();
    }
}
