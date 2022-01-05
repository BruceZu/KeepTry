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

package list;

public class Leetcode142LinkedListCycleII {
  /*
  Leetcode 142. Linked List Cycle II

  Given the head of a linked list, return the node where the cycle begins.
  If there is no cycle, return null.

  There is a cycle in a linked list if there is some node in the list that can be reached
  again by continuously following the next pointer. Internally, pos is used to
  denote the index of the node that tail's next pointer is connected to (0-indexed).
  It is -1 if there is no cycle. Note that pos is not passed as a parameter.

  Do not modify the linked list.


  Input: head = [3,2,0,-4], pos = 1
  Output: tail connects to node index 1
  Explanation: There is a cycle in the linked list, where tail connects to the second node.



  Input: head = [1,2], pos = 0
  Output: tail connects to node index 0
  Explanation: There is a cycle in the linked list, where tail connects to the first node.



  Input: head = [1], pos = -1
  Output: no cycle
  Explanation: There is no cycle in the linked list.


  Constraints:

  The number of the nodes in the list is in the range [0, 104].
  -105 <= Node.val <= 105
  pos is -1 or a valid index in the linked-list.

  Follow up: Can you solve it using O(1) (i.e. constant) memory?
   */
  /*
  Floyd's Tortoise and Hare

  Floyd's algorithm is separated into two distinct phases.
   - it determines whether a cycle is present in the list.
     If no cycle is present, it returns null immediately
  -  Otherwise, it uses the located "intersection node" to
     find the entrance to the cycle.

   */
  public static ListNode detectCycle(ListNode head) {
    ListNode h = head; // hare
    ListNode t = head; // tortoise
    while (!(h == null || h.next == null)) {
      h = h.next.next;
      t = t.next;
      if (h == t && h != null) {
        while (head != t) {
          head = head.next;
          t = t.next;
        }
        return head;
      }
    }
    return null;
  }
}
