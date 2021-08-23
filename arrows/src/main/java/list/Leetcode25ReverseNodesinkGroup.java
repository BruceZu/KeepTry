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

package list;

public class Leetcode25ReverseNodesinkGroup {
  /*
     25. Reverse Nodes in k-Group

      a linked list, reverse the nodes of a linked list k at a time and return its modified list.
      k is a positive integer and is less than or equal to the length of the linked list.
      If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.

      You may not alter the values in the list's nodes, only nodes themselves may be changed.


   Input: head = [1,2,3,4,5], k = 2
   Output: [2,1,4,3,5]


   Input: head = [1,2,3,4,5], k = 3
   Output: [3,2,1,4,5]


   Input: head = [1,2,3,4,5], k = 1
   Output: [1,2,3,4,5]


   Input: head = [1], k = 1
   Output: [1]



   Constraints:

       The number of nodes in the list is in the range sz.
       1 <= sz <= 5000
       0 <= Node.val <= 1000
       1 <= k <= sz


   Follow-up: Can you solve the problem in O(1) extra memory space?
  */

  /* --------------------------------------------------------------------------
  Idea: boundary node relation-recursion
    watch the boundary node relation between different groups
    group inner is as reverse one by one


  before and after 1  reverse operation
   pre(null)       cur  -->  ?
   null       <--  pre      (cur,next)

  O(N) time.
  O(N/k) spaced, used by stack.
   */
  public ListNode reverseKGroup__(ListNode head, int k) {
    if (head == null) return null;
    ListNode kNode = head;
    int i = 1;
    while (kNode.next != null && i + 1 <= k) {
      i++;
      kNode = kNode.next;
    }
    if (i < k) return head;
    ListNode pre = reverseKGroup(kNode.next, k), cur = head;
    while (pre != kNode) {
      ListNode next = cur.next;
      cur.next = pre;
      pre = cur;
      cur = next;
    }
    return pre;
  }

  /* --------------------------------------------------------------------------
  Idea: boundary node relation-iterator
    returnNode need assign value only once.

    preTail->   cur(tail) ....kNode

    the cur/head will be tail after reverse operation
    after k time reverse operation, pre is at the kNode

    in case the left nodes number < k, need break the loop

  O(N) time.
  O(1)
   */
  public ListNode reverseKGroup(ListNode head, int k) {
    ListNode r = null, preTail = null, cur = head, tail = cur;
    while (cur != null) {
      // kNode
      int i = 1;
      ListNode kNode = cur;
      while (i + 1 <= k && kNode.next != null) {
        kNode = kNode.next;
        i++;
      }
      // less than k, Node break here
      if (i < k) {
        if (preTail != null) preTail.next = cur;
        return r;
      }

      ListNode pre = null;
      while (i > 0) {
        ListNode next = cur.next;
        cur.next = pre;
        pre = cur;
        cur = next;
        i--;
      }

      if (r == null) r = pre;
      if (preTail != null) preTail.next = pre;
      preTail = tail;
      tail = cur;
    }
    return r;
  }
  /* --------------------------------------------------------------------------
  Idea: boundary node relation-iterator
  use a dummy node ahead
  dummy node
   - initially it points to the head of lest nodes, when left nodes number <k, need not any further action.
   - during reverse operation it points to the node next reversed point will point to
  */
  public ListNode reverseKGroup3(ListNode head, int k) {
    ListNode dummy = new ListNode(-1);
    dummy.next = head;

    ListNode preTail = dummy, cur = head;

    int i = 1;
    while (cur != null) {
      if (i % k == 0) {
        preTail = reverse(preTail, cur.next);
        cur = preTail.next;
      }
      else cur = cur.next;
      i++;
    }
    return dummy.next;
  }

  private static ListNode reverse(ListNode preTail, ListNode nextCur) {
    ListNode tail = preTail.next, cur = tail.next, next;
    while (cur != nextCur) {
      next = cur.next;
      cur.next = preTail.next;
      preTail.next = cur;
      cur = next;
    }
    tail.next = nextCur;
    return tail;
  }
}
