//  Copyright 2021 The KeepTry Open Source Project
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

package stack.monotonic_stack;

import list.ListNode;

import java.util.ArrayList;
import java.util.Stack;

public class Leetcode1019NextGreaterNodeInLinkedList {
  /*
    Each node may have a next larger value:
    for node_i, next_larger(node_i) is the node_j.val such that
     j > i, node_j.val > node_i.val, and
     j is the smallest possible choice.
     If such a j does not exist, the next larger value is 0.

    Return an array of integers answer, where answer[i] = next_larger(node_{i+1}).

        1 <= node.val <= 10^9 for each node in the linked list.
        The given list has length in the range [0, 10000].


      Input: [2,1,5]
      Output: [5,5,0]

      Input: [2,7,4,3,5]
      Output: [7,0,5,5,0]

      Input: [1,7,5,1,9,2,5,1]
      Output: [7,9,9,9,0,5,0,0]

  */

  /*
  go from last node to the first node
  with monotonic increasing stack to keep the `next_larger(node_i)`
  O(n) time, space, one element at most in and out the stock twice, 4 times
  */
  public int[] nextLargerNodes(ListNode head) {
    ArrayList<Integer> l = new ArrayList<>();
    for (ListNode node = head; node != null; node = node.next) l.add(node.val);

    int[] r = new int[l.size()];
    Stack<Integer> s = new Stack<>();
    s.push(l.get(l.size() - 1));
    r[l.size() - 1] = 0;
    for (int i = l.size() - 2; i >= 0; i--) {
      while (!s.isEmpty() && s.peek() <= l.get(i)) s.pop();
      if (s.isEmpty()) r[i] = 0;
      else r[i] = s.peek();
      s.push(l.get(i));
    }
    return r;
  }
}
