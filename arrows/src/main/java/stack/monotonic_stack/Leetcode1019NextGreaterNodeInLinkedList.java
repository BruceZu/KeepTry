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
import java.util.Arrays;
import java.util.Stack;

public class Leetcode1019NextGreaterNodeInLinkedList {
  // The given list has length in the range [0, 10000].
  public int[] nextLargerNodes(ListNode head) {
    ArrayList<Integer> vl = new ArrayList<>(); // value list
    for (ListNode node = head; node != null; node = node.next) {
      vl.add(node.val);
    }
    // 1 <= node.val <= 10^9 for each node in the linked list.
    int[] r = new int[vl.size()]; /* result */
    Stack<Integer> ist = new Stack<>(); /* index monotonic decreasing stack */
    for (int i = 0; i < vl.size(); ++i) {
      while (!ist.isEmpty() && vl.get(ist.peek()) < vl.get(i)) {
        r[ist.pop()] = vl.get(i);
      }
      ist.push(i);
    }
    return r;
  }
}
