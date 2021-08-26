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

import java.util.HashMap;
import java.util.Map;

public class Leetcode138CopyListwithRandomPointer {
  class Node {
    int val;
    public Node next;
    public Node random;

    public Node(int val) {
      this.val = val;
      this.next = null;
      this.random = null;
    }
  }
  /*
       138. Copy List with Random Pointer
       A linked list of length n is given such that each node contains
       an additional random pointer, which could point to any node in the list, or null.

       Construct a deep copy of the list.
       The deep copy should consist of exactly n brand new nodes,
       Return the head of the copied linked list.

       Each node is represented as a pair of [val, random_index] where:
         val: an integer representing Node.val
         random_index: the index of the node (range from 0 to n-1)
         that the random pointer points to, or null if it does not point to any node.

       Your code will only be given the head of the original linked list.

     Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
     Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]

     Input: head = [[1,1],[2,1]]
     Output: [[1,1],[2,1]]

     Input: head = [[3,null],[3,0],[3,null]]
     Output: [[3,null],[3,0],[3,null]]

     Input: head = []
     Output: []
     Explanation: The given linked list is empty (null pointer), so return null.

     Constraints:
         0 <= n <= 1000
         -10000 <= Node.val <= 10000
         Node.random is null or is pointing to some node in the linked list.
  */

  /* --------------------------------------------------------------------------
  Idea recursion(DFS)
  with a map to keep tracking of the cloned nodes and mapping relation
  to avoid possible cycles made by random pointer
  Take it as a graph
  O(N) time, space
  */
  Map<Node, Node> map = new HashMap();

  public Node copyRandomList__(Node head) {
    if (head == null) return null;
    if (map.containsKey(head)) return map.get(head);
    Node clo = new Node(head.val);
    map.put(head, clo); // kept before next recursion, to stop endless circle

    clo.next = copyRandomList__(head.next);
    clo.random = copyRandomList__(head.random);
    return clo;
  }

  /* --------------------------------------------------------------------------
   Iterator
   focus on the list character, node connected by the next pointer and at last end at null
   go along the list, node.next route with a map(node, clone)
   O(N) time, O(N) space
  */
  public Node copyRandomList_(Node head) {
    if (head == null) return null;
    Node n = head;

    Node o = new Node(n.val);
    map.put(n, o);
    while (n != null) {
      o.random = clone(n.random);
      o.next = clone(n.next);

      n = n.next;
      o = o.next;
    }

    return map.get(head);
  }

  public Node clone(Node node) {
    if (node == null) return null;
    if (this.map.containsKey(node)) return this.map.get(node);
    this.map.put(node, new Node(node.val));
    return this.map.get(node);
  }

  /* --------------------------------------------------------------------------
   Iterator
   focus on the list character again
   if keep a node clone at its next, the map can be saved
   thus the O(N) space can be improved to be O(1) space
   1> just take it as a normal list and make each node's clone as its next
   2> add the random to node's clone
   3> unlink the clone from the list.

   To achieve O(1) space and have to keep the map relation, solution always comes
   with updating the existing data struct during the process and restore it to its
   original status at last like Morris travel.

  Note:
  - each step from head to do
  - random can be null
  - at the step 3, the `nextn` is null at last step.

   O(N) time, O(1) extra space
  */
  public Node copyRandomList(Node head) {
    if (head == null) return null;
    // clone node and as node's next
    Node n = head;
    while (n != null) {
      Node o = new Node(n.val);

      o.next = n.next;
      n.next = o;

      n = o.next;
    }
    // complete the clone random
    n = head; // note from head, not n, random can be null
    while (n != null) {
      if (n.random != null) n.next.random = n.random.next;
      n = n.next.next;
    }

    // unlink clone from list
    n = head; // note from head, not n; next n can be null
    Node o = head.next;
    Node r = o;
    while (n != null) {
      n.next = o.next;
      o.next = (o.next != null) ? o.next.next : null;
      n = n.next;
      o = o.next;
    }
    return r;
  }
}
