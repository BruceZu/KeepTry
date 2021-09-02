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

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
155. Min Stack

Design a stack that supports push, pop, top, and
retrieving the minimum element in constant time.

Implement the MinStack class:

    MinStack() initializes the stack object.
    void push(val) pushes the element val onto the stack.
    void pop() removes the element on the top of the stack.
    int top() gets the top element of the stack.
    int getMin() retrieves the minimum element in the stack.

Input
["MinStack","push","push","push","getMin","pop","top","getMin"]
[[],[-2],[0],[-3],[],[],[],[]]

Output
[null,null,null,null,-3,null,0,-2]

Explanation
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin(); // return -3
minStack.pop();
minStack.top();    // return 0
minStack.getMin(); // return -2

Constraints:
    -2^31 <= val <= 2^31 - 1
    Methods pop, top and getMin operations will always be called on non-empty stacks.
    At most 3 * 104 calls will be made to push, pop, top, and getMin.
*/

/* ----------------------------------------------------------------------------
Use 1 list keep current min value, extend and shrink together with stack.
O(N) space,
O(1) time for all operations
*/
class MinStack___ {
  List<Integer> min;
  Stack<Integer> stk;

  public MinStack___() {
    min = new ArrayList();
    stk = new Stack<>();
  }

  public void push(int val) {
    stk.push(val);
    if (min.isEmpty() || val < min.get(min.size() - 1)) min.add(val);
    else min.add(min.get(min.size() - 1));
  }

  public void pop() {
    // pop, top and getMin operations will always be called on non-empty stacks. if(stk.isEmpty())
    stk.pop();
    min.remove(min.size() - 1);
  }

  public int top() {
    // pop, top and getMin operations will always be called on non-empty stacks. if(l.isEmpty())
    return stk.peek();
  }

  public int getMin() {
    // pop, top and getMin operations will always be called on non-empty stacks. if(s.isEmpty())
    return min.get(min.size() - 1);
  }
}

/* ============================================================================
 improve above solution where the list keeping current min has some duplicated value

 input number:  5 2 9 2 0 4 0
                | |     |
 current min:   5 2 2 2 0 0 0

if not use a list to keep current min
instead use one variable, it works for push()
but for pop() how to know previous min by current min and top?

*/

/* ----------------------------------------------------------------------------
Solution <2>
  keep the previous min also in stack when the new min appear,
  at this time the top is same as the min
Observe
  input number:  5 2 9 2 0 4 0
                 | |     |
 current min:    5 2 2 2 0 0 0

 stock:         MAX 5,  5 2,  9,  2 2,  2 0,  4,  0 0
 min      MAX       5     2       |       0         |

 - make initial min value as MAX to make sure the first number as the real first min
 - when number is same as the previous min, also put the min in the stack to make
   the code logic for `num < pre min` also apply to `num =pre min` for pop().
   cons:  if there are many number, same as the current min one,
          there'll be a lot of needless repetition on the stack.
          Fixed by  stack<int[min,frequency]> minfre variable
           stock:         MAX 5,   2,   9,   2,     0,    4,   0
           frequency:       [5,1],[2,1]    ->[2,2] [0,1]    ->[0,2]

O(1) time
O(N) space
*/
class MinStack__ {
  private Stack<Integer> stk = new Stack<>();
  private Stack<int[]> min = new Stack<>();

  public void push(int x) {
    stk.push(x);
    if (min.isEmpty() || x < min.peek()[0]) min.push(new int[] {x, 1});
    else if (x == min.peek()[0]) min.peek()[1]++;
  }

  public void pop() {
    if (stk.peek().equals(min.peek()[0])) min.peek()[1]--;
    if (min.peek()[1] == 0) min.pop();
    stk.pop();
  }

  public int top() {
    return stk.peek();
  }

  public int getMin() {
    return min.peek()[0];
  }
}
/* ----------------------------------------------------------------------------
Solution <1>
 limitation: does not apply to long type

    stack diff, with diff of top - previous to create relation between top and previous min
    with signal of diff to identify current min is v or previous min
    thus with 1 known value to get the other unknown value via the relation.

Observe
  input number:  5 2 9 2 0 4 0
                 | |     |
 current min:    5 2 2 2 0 0 0

 focus on the new min
 new min should always < previous min
 if current number v is new min
        v-pmin < 0
 else   v-pmin >= 0 v can not become a new min

 The sign of positive or negative show information
 if use stack to keep the difference of  v-premin

 input number:     5   2  9  2   0  4  0
 stack<difference> 0  -3  7  0  -2  4  0
                   |   |     |
 current min:      5   2  2  2   0  0  0

 Logic of pop()
   if top is >= 0, then min == pmin.
         v-x
      x  x
   so    v = min + top
   else top is < 0, number == min
          v-x
      x   v
   so pmin = min - top


    O(1) time,
    O(1) extra space, not taking account the stack
*/
class MinStack {
  long min; // default 0
  Stack<Long> dif;

  public MinStack() {
    dif = new Stack<>();
  }

  public void push(int v) {
    if (dif.isEmpty()) {
      dif.push(0L);
      min = v;
    } else {
      dif.push(v - min);
      if (v < min) min = v;
    }
  }

  public void pop() {
    long top = dif.pop();
    if (top < 0) min = min - top;
  }

  public int top() {
    long top = dif.peek();
    if (top > 0) return (int) (top + min);
    else return (int) (min);
  }

  public int getMin() {
    return (int) min;
  }
}

public class Leetcode155MinStack {}
