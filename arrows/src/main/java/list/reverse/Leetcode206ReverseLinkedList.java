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
class Node {
  Integer v;
  Node next;

  Node(int v) {
    this.v = v;
  }
}

public class Leetcode206ReverseLinkedList {

  public static Node reverse(Node head) {
    if (head == null || head.next == null) {
      return head;
    }
    Node pre = null;
    Node cur = head;
    Node next;
    while (cur != null) {
      next = cur.next;
      cur.next = pre;
      pre = cur;
      cur = next;
    }
    return pre;
  }

  // Return entryNode if there is a loop else return null
  private static Node entryNode(Node node) {
    if (node == null || node.next == null) {
      return null;
    }
    Node slow = node.next, fast = node.next.next;
    while (fast != null && fast.next != null && fast != slow) {
      slow = slow.next;
      fast = fast.next.next;
    }
    if (slow == fast) {
      fast = node;
      while (slow != fast) {
        slow = slow.next;
        fast = fast.next;
      }
      return fast;
    }
    return null;
  }

  private static Node reverseSingleListWithLoop(Node head) {
    if (head == null || head.next == null) return head;
    Node entryNode = entryNode(head);

    int metEntryNodeNumber = 0;
    Node pre = null, cur = head, next;

    while (cur != null) {
      if (cur == entryNode) {
        metEntryNodeNumber++;
        if (metEntryNodeNumber == 2 && head != entryNode) {
          break;
        }
      }

      next = cur.next;
      cur.next = pre;
      pre = cur;
      cur = next;
    }
    return pre;
  }

  public static void main(String[] args) {
    Node one = new Node(1);
    Node n2 = new Node(2);
    Node n3 = new Node(3);
    Node n4 = new Node(4);
    Node n5 = new Node(5);
    StringBuilder sb = new StringBuilder();

    one.next = n2;
    n2.next = n3;
    sb.append(one.v).append("->");
    sb.append(one.next.v).append("->");
    sb.append(one.next.next.v).append("->");
    sb.append(one.next.next.next == null ? " null" : "Wrong");
    System.out.print(sb.toString());
    Node reversedHead = reverse(one);
    sb = new StringBuilder();
    sb.append(" => ");
    sb.append(reversedHead.v).append("->");
    sb.append(reversedHead.next.v).append("->");
    sb.append(reversedHead.next.next.v).append("->");
    sb.append(reversedHead.next.next.next == null ? " null" : "Wrong");
    System.out.println(sb.toString());

    one.next = n2;
    n2.next = n3;
    n3.next = one;
    sb = new StringBuilder();
    sb.append(one.v).append("->");
    sb.append(one.next.v).append("->");
    sb.append(one.next.next.v).append("->");
    sb.append(one.next.next.next.v);
    System.out.print(sb.toString());
    reversedHead = reverse(one);
    sb = new StringBuilder();
    sb.append(" => ");
    sb.append(reversedHead.v).append("->");
    sb.append(reversedHead.next.v).append("->");
    sb.append(reversedHead.next.next.v).append("->");
    sb.append(reversedHead.next.next.next.v);
    System.out.println(sb.toString());

    one.next = n2;
    n2.next = n3;
    n3.next = n4;
    n4.next = n5;
    n5.next = n2;
    sb = new StringBuilder();
    sb.append(one.v).append("->");
    sb.append(one.next.v).append("->");
    sb.append(one.next.next.v).append("->");
    sb.append(one.next.next.next.v).append("->");
    sb.append(one.next.next.next.next.v).append("->");
    sb.append(one.next.next.next.next.next.v);
    System.out.print(sb.toString());
    reversedHead = reverse(one);
    sb = new StringBuilder();
    sb.append(" => ");
    sb.append(reversedHead.v).append("->");
    sb.append(reversedHead.next.v).append("->");
    sb.append(reversedHead.next.next.v).append("->");
    sb.append(reversedHead.next.next.next.v).append("->");
    sb.append(reversedHead.next.next.next.next.v).append("->");
    sb.append(reversedHead.next.next.next.next.next.v);
    System.out.print(sb.toString());
    System.out.println(" This is wrong ");

    one.next = n2;
    n2.next = n3;
    n3.next = n4;
    n4.next = n5;
    n5.next = n2;
    sb = new StringBuilder();
    sb.append(one.v).append("->");
    sb.append(one.next.v).append("->");
    sb.append(one.next.next.v).append("->");
    sb.append(one.next.next.next.v).append("->");
    sb.append(one.next.next.next.next.v).append("->");
    sb.append(one.next.next.next.next.next.v);
    System.out.print(sb.toString());
    reversedHead = reverseSingleListWithLoop(one);
    sb = new StringBuilder();
    sb.append(" => ");
    sb.append(reversedHead.v).append("->");
    sb.append(reversedHead.next.v).append("->");
    sb.append(reversedHead.next.next.v).append("->");
    sb.append(reversedHead.next.next.next.v).append("->");
    sb.append(reversedHead.next.next.next.next.v).append("->");
    sb.append(reversedHead.next.next.next.next.next == null ? " null" : "Wrong");
    System.out.print(sb.toString());
  }
}
