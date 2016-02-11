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
/*
Given a linked list and a value x, partition it such that all nodes less than x
come before nodes greater than or equal to x.
You should preserve the original relative order of the nodes in each of the two partitions.
For example,
Given 1->4->3->2->5->2 and x = 3,
return 1->2->2->4->3->5.
 */

public class Leetcode86PartitionList {
    public class Solution {
        private ListNode[] process(ListNode node, int x, ListNode[] ts) {
            if (node != null) {
                if (node.val < x) {
                    ts[0].next = node;
                    ts[0] =ts[0].next;
                } else {
                    ts[1].next = node;
                    ts[1] = ts[1].next;
                }
               return process(node.next, x, ts);
            }
            return ts;
        }

        public ListNode partition(ListNode head, int x) {
            ListNode smalsDumyHead = new ListNode(0);
            ListNode smalsTail = smalsDumyHead;
            ListNode bigsDumyHead = new ListNode(0);
            ListNode bigsTail = bigsDumyHead;

            ListNode [] ts = new ListNode []{smalsTail,bigsTail};
            ts= process( head, x, ts);

            ts[0].next = bigsDumyHead.next;
            ts[1].next = null; // note
            return smalsDumyHead.next;
        }

        // beats 5.06% of java submissions.
        public ListNode partition2(ListNode head, int x) {
            ListNode smalsDumyHead = new ListNode(0);
            ListNode smalsTail = smalsDumyHead;
            ListNode bigsDumyHead = new ListNode(0);
            ListNode bigsTail = bigsDumyHead;

            ListNode cur = head;
            while (cur != null) {
                if (cur.val < x) {
                    smalsTail.next = cur;
                    smalsTail = smalsTail.next;
                } else {
                    bigsTail.next = cur;
                    bigsTail = bigsTail.next;
                }
                cur = cur.next;
            }

            smalsTail.next = bigsDumyHead.next;
            bigsTail.next = null; // note
            return smalsDumyHead.next;
        }
    }
}
