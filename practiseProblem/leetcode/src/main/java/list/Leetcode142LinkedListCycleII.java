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

// Given a linked list, return the node where the cycle begins.
// If there is no cycle, return null.
// Note: Do not modify the linked list.
// Follow up:
// Can you solve it without using extra space?
// https://leetcode.com/problems/linked-list-cycle-ii/
public class Leetcode142LinkedListCycleII {

    // beats 20.26% of java submissions.
    public static ListNode detectCycle(ListNode head) {
        ListNode f = head;
        ListNode l = head;
        while (!(f == null || f.next == null)) {
            f = f.next.next;
            l = l.next;
            if (f == l && f != null) {
                while (head != f) {
                    head = head.next;
                    f = f.next;
                }
                return f;
            }
        }
        return null;
    }

    // beats 20.26% of java submissions.
    // it depends on there is no value of Integer.MIN_VALUE existing
    public static ListNode detectCycle2(ListNode head) {
        while (null != head) {
            if (Integer.MIN_VALUE != head.val) {
                head.val = Integer.MIN_VALUE;
            } else {
                return head;
            }
            head = head.next;
        }
        return null;
    }
}
