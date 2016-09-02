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

/**
 * <pre>
 *
 *   data structure
 *                      node --> node -->
 *                      node --> node --> node-->
 *                      next
 *
 * result: dumy node -> head --> node -->
 *
 * @see <a href="https://leetcode.com/problems/add-two-numbers/">leetcode </a>
 */
public class Leetcode2AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        int carry = 0;

        ListNode sentinel = new ListNode(0);
        ListNode r = sentinel;
        while (l1 != null && l2 != null) {
            int cur = l1.val + l2.val + carry;
            if (cur > 9) {
                carry = 1;
                cur = cur - 10;
            } else {
                carry = 0;
            }

            r.next = new ListNode(cur);
            r = r.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        if (l1 == null && l2 == null) {
            if (carry != 0) {
                r.next = new ListNode(carry);

            }
            return sentinel.next;

        }
        ListNode left;
        if (l1 == null) {
            left = l2;
        } else {
            left = l1;
        }
        while (left != null) {
            int cur = left.val + carry;
            if (cur > 9) {
                carry = 1;
                cur = cur - 10;
            } else {
                carry = 0;
            }
            r.next = new ListNode(cur);
            r = r.next;

            left = left.next;
        }
        if (carry != 0) {
            r.next = new ListNode(carry);

        }
        return sentinel.next;
    }

    public ListNode addTwoNumbersSimple(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        int carry = 0;

        ListNode sentinel = new ListNode(0);
        ListNode r = sentinel;
        while (l1 != null || l2 != null) {
            int cur = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val) + carry;
            if (cur > 9) {
                carry = 1;
                cur = cur - 10;
            } else {
                carry = 0;
            }

            r.next = new ListNode(cur);
            r = r.next;
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }

        if (carry != 0) {
            r.next = new ListNode(carry);

        }
        return sentinel.next;
    }
}
