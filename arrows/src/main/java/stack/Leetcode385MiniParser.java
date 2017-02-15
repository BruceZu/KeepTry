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

import java.util.*;

/**
 * This is the interface that allows for creating nested lists.
 * You should not implement it, or speculate about its implementation
 */
class NestedInteger {
    public NestedInteger() {
    }

    public NestedInteger(int value) {
    }

    public void add(NestedInteger ni) {
    }

    public List<NestedInteger> getList() {
        return null; // See LeetCode for detail
    }
}

/**
 * <a href="https://leetcode.com/problems/mini-parser/">LeetCode</a>
 * <pre>
 * Assume that the string is well-formed
 *  String is non-empty.
 *  String does not contain white spaces.
 *  String contains only digits 0-9, [, - ,, ].
 */
public class Leetcode385MiniParser {
    /**
     * test case
     * "321"
     * "[123,[456,[789]]]"
     * "[-1]"
     * "[-1,-2]"
     * "[0]"
     * "[]"
     * "[[88],98,200]"
     */
    //  using StingTokenizer to make the logic clean (O(N))
    public NestedInteger deserialize4(String s) {
        StringTokenizer tokenizer = new StringTokenizer(s, "[],", true);

        Stack<NestedInteger> stack = new Stack<>();
        stack.push(new NestedInteger()); // a dummy list to simplify init

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            switch (token) {
                case "[":
                    NestedInteger list = new NestedInteger();
                    stack.peek().add(list);
                    stack.push(list);
                    break;
                case "]":
                    stack.pop();
                    break;
                case ",":
                    break;
                default: // number
                    NestedInteger num = new NestedInteger(Integer.parseInt(token));
                    stack.peek().add(num);
            }
        }

        return stack.pop().getList().get(0);
    }

    // (O(N))
    public NestedInteger deserialize2(String s) {
        if (!s.startsWith("[")) {
            return new NestedInteger(Integer.valueOf(s));
        }
        Stack<NestedInteger> stack = new Stack<>();
        NestedInteger result = new NestedInteger();
        stack.push(result);
        int numberStart = 1;
        for (int i = 1; i < s.length(); i++) {
            char curChar = s.charAt(i);
            if (curChar == '[') {
                NestedInteger ni = new NestedInteger();

                stack.peek().add(ni);
                stack.push(ni);
                numberStart = i + 1;
            } else if (curChar == ',') {
                if (i > numberStart) { // case "[[88],98,200]"
                    stack.peek().add(new NestedInteger(Integer.valueOf(s.substring(numberStart, i))));
                }
                numberStart = i + 1;
            } else if (curChar == ']') {
                if (i > numberStart) { // case "[]"
                    stack.peek().add(new NestedInteger(Integer.valueOf(s.substring(numberStart, i))));
                }
                numberStart = i + 1;
                stack.pop();
            }
        }
        return result;
    }

    // (O(N))
    public NestedInteger deserialize3(String s) {
        if (s.startsWith("[")) {
            Stack<Object> stack = new Stack();
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '[') {
                    stack.push("[");
                } else if ('0' <= s.charAt(i) && s.charAt(i) <= '9' || s.charAt(i) == '-') {
                    int numberStart = i;
                    while ('0' <= s.charAt(i) && s.charAt(i) <= '9' || s.charAt(i) == '-') {
                        i++;
                    }
                    stack.push(new NestedInteger(Integer.valueOf(s.substring(numberStart, i))));
                    i--; //
                } else if (s.charAt(i) == ']') {
                    Stack<Object> tmpStack = new Stack();
                    while (stack.peek() != "[") {
                        tmpStack.push(stack.pop());
                    }
                    stack.pop(); // get out the '['

                    NestedInteger cur = new NestedInteger();
                    while (!tmpStack.isEmpty()) { // order is right now
                        NestedInteger ni = (NestedInteger) tmpStack.pop();
                        cur.add(ni);
                    }
                    stack.push(cur);
                }
                // ',' continue
            }
            return (NestedInteger) stack.peek();
        }
        return new NestedInteger(Integer.valueOf(s));
    }

    // recursion.  runtime complexity is: ??
    public NestedInteger deserialize(String s) {
        if (s.charAt(0) != '[') {
            return new NestedInteger(Integer.valueOf(s));
        }
        NestedInteger result = new NestedInteger();
        if (s.length() == 2) {// "[]"
            return result;
        }
        int startOfNumOrBrack = 1, i = 1, numberOfBracks = 0;
        while (i < s.length() - 1) {
            char curChar = s.charAt(i);
            if (curChar == '[') {
                numberOfBracks++;
            } else if (curChar == ']') {
                numberOfBracks--;
            } else if (curChar == ',' && numberOfBracks == 0) {
                result.add(deserialize(s.substring(startOfNumOrBrack, i)));
                startOfNumOrBrack = i + 1;
            }
            // number
            i++;
        }
        result.add(deserialize(s.substring(startOfNumOrBrack, i)));
        return result;
    }
}
