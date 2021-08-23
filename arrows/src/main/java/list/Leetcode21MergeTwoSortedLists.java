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

public class Leetcode21MergeTwoSortedLists {
  /*
       21. Merge Two Sorted Lists


   Merge two sorted linked lists and return it as a sorted list.
   The list should be made by splicing together the nodes of the first two lists.

   Input: l1 = [1,2,4], l2 = [1,3,4]
   Output: [1,1,2,3,4,4]

   Input: l1 = [], l2 = []
   Output: []

   Input: l1 = [], l2 = [0]
   Output: [0]

   Constraints:

       The number of nodes in both lists is in the range [0, 50].
       -100 <= Node.val <= 100
       Both l1 and l2 are sorted in non-decreasing order.

  */
  /*
  Iterator
  O(N) time O(1) space
  */
  public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode();
    ListNode cur = dummy;
    while (l1 != null && l2 != null) {
      if (l1.val < l2.val) {
        cur.next = l1;
        l1 = l1.next;
      } else {
        cur.next = l2;
        l2 = l2.next;
      }
      cur = cur.next;
    }
    cur.next = l1 == null ? l2 : l1;
    return dummy.next;
  }
  // --------------------------------------------------------------------------
  class ListNode {
    int val;
    ListNode next;

    ListNode() {}

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }
  }
}
