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

import java.util.Stack;

public class BlueScape {
    public static void main(String[] args) {
        System.out.println(solution("13 DUP 4 POP 5 DUP + DUP + -")); // 7
    }

    /**
     * <a href="https://codility.com" > codility</a>
     * Assume the S is valid operations
     * Stack size will be valid [1~math.pow(2,20)-1] else it would be error and return -1.
     */
    public static int solution(String S) {
        final double LIMIT = Math.pow(2, 20) - 1;
        Stack<Integer> stack = new Stack<>();
        String[] operations = S.split(" ");
        for (int i = 0; i < operations.length; i++) {
            switch (operations[i]) {
                case "POP":
                    if (stack.isEmpty()) {
                        return -1;
                    }
                    stack.pop();
                    break;
                case "DUP":
                    if (stack.size() == LIMIT) {
                        return -1;
                    }
                    if (stack.isEmpty()) {
                        return -1;
                    }
                    int top = stack.peek();
                    stack.push(top);
                    break;
                case "+":
                    if (stack.isEmpty()) {
                        return -1;
                    }
                    int head = stack.pop();

                    if (stack.isEmpty()) {
                        return -1;
                    }
                    int following = stack.pop();
                    stack.push(head + following);

                    break;
                case "-":
                    if (stack.isEmpty()) {
                        return -1;
                    }
                    int toBeSub = stack.pop();

                    if (stack.isEmpty()) {
                        return -1;
                    }
                    int sub = stack.pop();
                    stack.push(toBeSub - sub);
                    break;
                default:
                    if (stack.size() == LIMIT) {
                        return -1;
                    }
                    stack.push(Integer.valueOf(operations[i]));
            }
        }
        if (stack.isEmpty()) {
            return -1;
        }
        return stack.peek();
    }
}
