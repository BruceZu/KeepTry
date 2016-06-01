// Copyright 2016 The Sawdust Open Source Project
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

// C-5.25 Describe a fast recursive algorithm for reversing a singly linked list L, so that the
// ordering of the nodes becomes opposite of what it was before.

import list.ListNode;

public class C525ReverseList {

    private static ListNode reverse(ListNode pre, ListNode cur) {
        if (cur == null) {
            return pre;
        }
        ListNode next = cur.next;
        cur.next = pre;
        pre = cur;
        cur = next;
        return reverse(pre, cur);
    }

    public static ListNode reverse(ListNode head) {
        if (head == null) {
            return head;
        }

        ListNode next = head.next;
        head.next = null;
        return reverse(head, next);
    }

    public static ListNode reverse2(ListNode head) {
        if (head == null) {
            return head;
        }

        ListNode cur = head.next;
        head.next = null;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = head;
            head = cur;
            cur = next;
        }
        return head;
    }
}
