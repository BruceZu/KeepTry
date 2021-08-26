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

package list;

public class Leetcode708InsertintoaSortedCircularLinkedList {
  static class Node {
    public int val;
    public Node next;

    public Node() {}

    public Node(int _val) {
      val = _val;
    }

    public Node(int _val, Node _next) {
      val = _val;
      next = _next;
    }
  }
  /*
  708. Insert into a Sorted Circular Linked List

   Given a Circular Linked List node, which is sorted in ascending order,
   write a function to insert a value insertVal into the list such that it remains
   a sorted circular list. The given node can be a reference to any single node
   in the list and may not necessarily be the smallest value in the circular list.

   If there are multiple suitable places for insertion, you may choose any
   place to insert the new value. After the insertion,
   the circular list should remain sorted.

   If the list is empty (i.e., the given node is null), you should create
   a new single circular list and return the reference to that single node.
   Otherwise, you should return the originally given node.

   Input: head = [3,4,1], insertVal = 2
   Output: [3,4,1,2]

   Input: head = [], insertVal = 1
   Output: [1]

   Input: head = [1], insertVal = 0
   Output: [1,0]

   Input: head = [2,2], insertVal = 0
   Output: [2,2,0]

   Constraints:

       0 <= Number of Nodes <= 5 * 10^4
       -10^6 <= Node.val, insertVal <= 10^6
  */

  /* -------------------------------------------------------------------
   The circular list elements can be same， like [2,2]
   1. find the node with max value.
      then next one is the min.
   2. compare the insertVal with `min`

   Note: in step 1 and 2, avoid endless loop.
         in step 2 use n.next.val, not n.val
   O(N) time, O(1) space
  */
  public Node insert(Node head, int insertVal) {
    Node in = new Node(insertVal);
    if (head == null) {
      in.next = in;
      return in;
    }
    if (head.next == head) { // this block can be saved
      head.next = in;
      in.next = head;
      return head;
    }
    // min=max can be same like[1], or [2,2]
    Node n = head;
    while (n.next != head && n.val <= n.next.val) n = n.next;
    Node max = n;
    Node min = n.next;
    if (insertVal <= min.val) {
      max.next = in;
      in.next = min;
      return head;
    } else {
      n = min;
      while (n.next != min && n.next.val < insertVal) n = n.next;
      in.next = n.next; // in.next, not in
      n.next = in;
      return head;
    }
  }
}
