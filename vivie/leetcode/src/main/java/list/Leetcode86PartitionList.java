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

/*
Given a linked list and a value x, partition it such that all nodes less than x
come before nodes greater than or equal to x.
You should preserve the original relative order of the nodes in each of the two partitions.
For example,
Given 1->4->3->2->5->2 and x = 3,
return 1->2->2->4->3->5.
 */
// https://leetcode.com/problems/partition-list/
public class Leetcode86PartitionList {
    public class Solution {
        // beats 5.06% of java submissions on Feb 10, 2016
        private void process(ListNode node, int x, ListNode[] tNodes) {
            if (node != null) {
                if (node.val < x) {
                    tNodes[0].next = node;
                    tNodes[0] = tNodes[0].next;
                } else {
                    tNodes[1].next = node;
                    tNodes[1] = tNodes[1].next;
                }
                process(node.next, x, tNodes);
            }
        }

        // recursion
        public ListNode partition(ListNode head, int x) {
            ListNode smalsDumyHead = new ListNode(0);
            ListNode smalsTail = smalsDumyHead;
            ListNode bigsDumyHead = new ListNode(0);
            ListNode bigsTail = bigsDumyHead;

            ListNode[] tailNodes = new ListNode[]{smalsTail, bigsTail};
            process(head, x, tailNodes);

            tailNodes[0].next = bigsDumyHead.next;
            tailNodes[1].next = null; // note
            return smalsDumyHead.next;
        }

        // beats 5.06% of java submissions on Feb 10, 2016
        // loop
        public ListNode partition2(ListNode head, int x) {
            ListNode smalsDumyHead = new ListNode(0);
            ListNode smalsTail = smalsDumyHead;
            ListNode bigsDumyHead = new ListNode(0);
            ListNode bigsTail = bigsDumyHead;

            // divide it into 2 list
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

            // append the bigger list to smaller list
            smalsTail.next = bigsDumyHead.next;
            bigsTail.next = null; // note
            return smalsDumyHead.next;
        }
    }
}
