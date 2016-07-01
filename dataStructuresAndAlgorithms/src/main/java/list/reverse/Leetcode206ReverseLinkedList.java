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

package list.reverse;

import list.ListNode;

/**
 * <pre>
 * Difficulty: Easy
 * Reverse a singly linked list.
 *
 * click to show more hints.
 *
 * Hint:
 *      A linked list can be reversed either iteratively or recursively. Could you implement both?
 *
 * Tags Linked List
 */
public class Leetcode206ReverseLinkedList {

    /**
     * <pre>
     * before loop :
     *
     *         pre cur next
     *          |   |   |
     *   null <-n <-n   ?
     *
     * The right steps in order is 1 2 3,  from left to right.
     *
     *    pre = cur
     *    cur = next
     *    next = next.next
     *
     *             pre cur next
     *              |   |   |
     *   null <-n <-n   n ->?
     *
     *   before next loop it is, step 4: cur.next = pre;
     *             pre cur next
     *              |   |   |
     *   null <-n <-n <-n   ?
     *
     *  Error:
     *      2 before 1
     *      3 before 2
     */
    public class Solution {
        public ListNode reverseList(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }
            ListNode pre = head;
            ListNode cur = head.next;
            ListNode next = head.next.next;
            pre.next = null;
            cur.next = pre;
            while (next != null) {
                pre = cur;  // 1
                cur = next; // 2
                next = next.next; // 3

                cur.next = pre; //4
            }
            return cur;
        }
    }
}
