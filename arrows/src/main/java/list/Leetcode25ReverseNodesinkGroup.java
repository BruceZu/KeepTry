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

  The number of nodes in the list is n.
  1 <= k <= n <= 5000
  0 <= Node.val <= 1000


     Follow-up: Can you solve the problem in O(1) extra memory space?
    */

  /* --------------------------------------------------------------------------
  recursion
    list 1,2,3,4,5
    reverse two nodes at a time.
    reverse the first two nodes thus getting 2,1.
    assume the following will be 4,3,5.
    Now, need hookup 1-> 4, let 4 is the pre


   O(N) time.
   O(N/k) spaced, used by stack.
    */
  public ListNode reverseKGroup__(ListNode h, int size) {
    if (h == null) return h; // this is possible
    ListNode tail = h; // group head h and tail
    int i = 1;
    while (i < size && tail.next != null) {
      i++;
      tail = tail.next;
    }
    if (i < size) return h;
    else {
      ListNode pre = reverseKGroup__(tail.next, size), cur = h;
      while (pre != tail) {
        ListNode next = cur.next;
        cur.next = pre;
        pre = cur;
        cur = next;
      }
      return pre;
    }
  }

  /* --------------------------------------------------------------------------
  Watch:  https://imgur.com/a/0i4d9m8
  before and after reverse group  https://imgur.com/a/DivXjba

  keep
     - `preTail` node: keep the last group node used to connect current reversed group head node( `pre` )
     - `resultHead` node: keep the result list head node
     group head h and tail:
     - `tail` at the group end node or last not null node
     - input parameter `h` always is the head of group first node
   They can be global variables

   local variable:  `pr`e=null, `cur`=`h`, `next` are local variables used to reverse current group.
  O(N) time.
  O(1) space
  */
  public ListNode reverseKGroup_(ListNode h, int size) {
    ListNode resultHead = null, preTail = null, tail = h;
    // while (true) will be in endless loop  e.g. size is 1 and from cur and h, tail is null
    while (h != null) {
      int i = 1;
      while (i < size && tail.next != null) {
        i++;
        tail = tail.next;
      }
      // tail always is not null
      if (i != size) {
        preTail.next = h; // need not: if(preTail !=null), as 1 <= k <= n <= 5000
        break;
      }
      ListNode pre = null, cur = h;
      // if tail.next is null, then while(cur!=tail.next)  always true;
      // instead use  while (pre != tail) or use i-->0
      while (pre != tail) {
        ListNode next = cur.next;
        cur.next = pre;
        pre = cur;
        cur = next;
      }
      if (resultHead == null) resultHead = pre;
      if (preTail != null) preTail.next = pre;
      preTail = h;
      // do not use h = tail.next because tail has been reversed
      h = cur;
      tail = cur;
    }
    return resultHead;
  }
  /* --------------------------------------------------------------------------
   abstract out the group reverse in a method
  */
  public ListNode reverseKGroup(ListNode h, int size) {
    ListNode preTail = null, resultHead = null, tail = h;
    int i = 1;
    while (tail != null) {
      if (i % size == 0) {
        if (resultHead == null) resultHead = tail;
        if (preTail != null) preTail.next = tail; // it is tail not h
        preTail = h;

        h = reverseGroupPointer(h, tail);
        tail = h;
      } else {
        tail = tail.next;
      }
      i++;
    }
    preTail.next = h; // left part
    return resultHead;
  }
  // see https://imgur.com/3lTkqRW   return next group head
  private ListNode reverseGroupPointer(ListNode h, ListNode tail) {
    ListNode pre = null, cur = h;
    while (pre != tail) {
      ListNode next = cur.next;
      cur.next = pre;
      pre = cur;
      cur = next;
    }
    return cur;
  }
}
