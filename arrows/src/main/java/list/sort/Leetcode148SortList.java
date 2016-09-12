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

package list.sort;

/**
 * <pre>
 * Difficulty: Medium
 * Sort a linked list in O(n log n) time using constant space complexity.
 *
 * Subscribe to see which companies asked this question
 *
 * Hide Tags Linked List Sort
 * Hide Similar Problems (E) Merge Two Sorted Lists (M) Sort Colors (M) Insertion Sort List
 */

public class Leetcode148SortList {

}

/**
 * Definition for singly-linked list.
 */
class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

class Solution {

    /**
     * Merge 2 sorted sub list and hook the result to node pre
     * <pre>
     * Test case:
     *  A>
     * pre ->null;
     * 2 -> 6 ->  7 -> null;
     * 3 -> 5 ->  9 -> null
     *
     * => pre -> 2 -> 3 -> 5-> 6 -> 7 -> 9 -> null
     *                                   |
     *                                  pre'
     *  B>
     * pre ->null;
     * 2 -> 6 -> 7 -> null;
     * 3 -> 5 -> null
     *
     * => pre -> 2 -> 3 -> 5 -> 6 -> 7 -> null
     *                               |
     *                              pre'
     *
     * @param pre pre node point to null
     * @param l1  head of sub list, end with null
     * @param l2  head of sub list. end with null
     * @return the last node. e.g. the pre' in the result: pre-> node ->..... -> pre'->null
     */

    static private ListNode merge(ListNode pre, ListNode l1, ListNode l2) {
        while (true) {
            if (l1 == null && l2 == null) {
                pre.next = null;
                return pre;
            } else if (l1 == null) {

                pre.next = l2;
                while (pre.next != null) {
                    pre = pre.next;
                }
                return pre;

            } else if (l2 == null) {
                pre.next = l1;
                while (pre.next != null) {
                    pre = pre.next;
                }
                return pre;
            }
            // either sub list has no-null node
            if (l1.val < l2.val) {
                pre.next = l1;
                pre = pre.next;

                l1 = l1.next;
                continue;
            }
            pre.next = l2;
            pre = pre.next;

            l2 = l2.next;
        }
    }

    /**
     * <pre>
     * Cut 2 sub lists with length len from node cur.
     * If can not cut out the second sub list then return null;
     *
     * @param cur node from where to start cut
     * @param len   sub list length
     * @param re    result keep
     * @return node -> head of left list, if failed to cut out pairs, then return cur.
     *
     * Test case, assume len = 3
     *  3-> 8                             => x
     *  3-> 8 -> 5                        => x
     *  3-> 8 -> 5-> 6
     *  =>
     *     3 -> 8 -> 5 -> null;
     *     6 -> null;
     *     r -> null
     *
     *  3-> 8 -> 5-> 6 -> 9 -> 3 -> 0
     *  =>
     *     3 -> 8 -> 5 -> null;
     *     6 -> 9 -> 3-> null;
     *     r->null
     *
     *  3-> 8 -> 5-> 6 -> 9 -> 3 -> 11 -> 0
     *  =>
     *     3 -> 8 -> 5 -> null;
     *     6 -> 9 -> 3 -> null;
     *     r -> 11
     */
    private static ListNode cut(ListNode head, int len, ListNode[] re) {
        ListNode cur = head;
        if (cur == null || cur.next == null) {
            re[0] = null;
            re[1] = null;
            return head;
        }
        re[0] = cur;
        int leftNodes = len - 1;

        while (0 != leftNodes && cur.next != null) {
            cur = cur.next;
            leftNodes--;
        }
        if (leftNodes != 0 // can not cut out the first sub list
                || cur.next == null) {// no node left for the second sub list
            re[0] = null;
            re[1] = null;
            return head;
        }

        re[1] = cur.next;
        cur.next = null; // end the first sub list
        cur = re[1];

        leftNodes = len - 1;
        while (leftNodes != 0 && cur.next != null) {
            cur = cur.next;
            leftNodes--;
        }

        ListNode r = cur.next;
        cur.next = null; // end up the second sub list
        return r;
    }

    static public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dumb = new ListNode(-1);
        dumb.next = head;

        ListNode pre;
        ListNode cur;


        int len = 1; // length of sub list
        int pairsCutBylen = 0;
        ListNode[] pairs = new ListNode[2];

        do {
            head = dumb.next;
            cur = head;

            pre = dumb;
            pre.next = null;

            pairsCutBylen = 0;

            do { // cut and merge from head using len
                cur = cut(cur, len, pairs);
                if (pairs[0] == null) {
                    pre.next = cur;
                    break;
                }
                pairsCutBylen++;
                pre = merge(pre, pairs[0], pairs[1]);
                if (cur == null) {
                    break;
                }
                // still have node left, continue cut and merge.
            } while (true);

            len = len << 1;
        } while (pairsCutBylen != 0);

        return head;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(3);
        head.next = new ListNode(2);
        head.next.next = new ListNode(4);

        head = sortList(sortList(head));
        System.out.println(head);

    }
}
