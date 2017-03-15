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

import java.util.Stack;

/**
 * <pre>
 * stack. its pop, push, getMin ‘algorithm complexity is O1
 * implement it with 2 stack, one maintenance min
 * http://articles.leetcode.com/2010/11/stack-that-supports-push-pop-and-getmin.html
 *
 * @see <a href="https://leetcode.com/problems/min-stack/">leetcode</a>
 */
public class Leetcode155MinStack {

    public class MinStack {
        long min;
        Stack<Long> stack; //   Integer.MAX_VALUE-Integer.MIN_VALUE;

        public MinStack() {
            stack = new Stack<>();
        }

        public void push(int cur) {
            if (stack.isEmpty()) {
                stack.push(0L);
                min = cur;
            } else {
                stack.push(cur - min);//Could be negative if min value needs to change
                if (cur < min) {
                    min = cur;
                }
            }
        }

        public void pop() {
            if (stack.isEmpty()) {
                return;
            }
            long sub = stack.pop();

            if (sub < 0) {
                min = min - sub;
            }
        }

        public int top() {
            long sub = stack.peek();
            if (sub > 0) { // pre min is just current min
                return (int) (sub + min);
            } else {
                //   < 0 : cur less than the pre min and cur is just the cur min;
                //   = 0 : cur is pre min and is cur min.
                return (int) (min);
            }
        }
        public int getMin() {
            return (int)min;
        }
    }

    // ---------------- space need more only the code is concise
    int min = Integer.MAX_VALUE;
    Stack<Integer> stack = new Stack< >();
    public void push(int cur) {
        if(cur <= min){
            stack.push(min);
            min=cur;
        }
        stack.push(cur);
    }

    public void pop() {
        if(stack.pop() == min) {min=stack.pop();}
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return min;
    }
}

