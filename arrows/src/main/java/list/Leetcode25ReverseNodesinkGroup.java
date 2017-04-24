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

/**
 * <a href="https://leetcode.com/problems/reverse-nodes-in-k-group/#/description">Leetcode</a>
 * Only constant memory is allowed.
 * <pre>
 * For example,
 * Given this linked list: 1->2->3->4->5
 *
 * For k = 2, you should return: 2->1->4->3->5
 *
 * For k = 3, you should return: 3->2->1->4->5
 */
public class Leetcode25ReverseNodesinkGroup {
    // O(?)
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode nextHead = head;
        int count = 0;
        while (nextHead != null && count != k) { // find the k+1 node
            nextHead = nextHead.next;
            count++;
        }
        if (count == k) { // if k+1 node is found
            ListNode pre = reverseKGroup(nextHead, k); // pre is the updated nextHead too
            ListNode next;
            while (head != nextHead) {
                next = head.next;
                head.next = pre;
                pre = head;
                head = next;
            }
            head = pre; // updated head now
        }
        return head;
    }

    public static void main(String[] args) {
        // null, [], length <k, =k,>k ...
    }
}
