//  Copyright 2021 The KeepTry Open Source Project
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

package recursion;

import java.util.*;

public class TreeChangedNodes {
  /*
    diff nodes numbers

        At DoorDash, menus are updated daily even hourly to keep them up-to-date.
        Each menu can be regarded as a tree. When the merchant sends us the latest menu,
        can we calculate how many nodes has changed?

        Assume each Node structure is given
        Assume there are no duplicate nodes with the same key.
        Output: Return the number of changed nodes in the tree.

        Either value change or the active status change means the node has been changed.
        The new menu tree structure can be different from existing trees when some nodes are set to null.

      Example1:

      Existing tree
               a(1, T)
              /       \
           b(2, T)   c(3, T)
          /     \           \
      d(4, T) e(5, T)   f(6, T)

      a(1, T): a is the key, 1 is the value, T is True for active status

      New Menu sent by the Merchant:

              a(1, T)
                  \
                 c(3, F)
                     \
                     f(66, T)

       Expected Answer: 5
       Explanation: Node b, d, e are automatically set to inactive.
                    The active status of Node c and
                    the value of Node f changed as well.

       Example 2
       Existing tree
              a(1, T)
             /       \
          b(2, T)   c(3, T)
        /       \       \
      d(4, T) e(5, T)      g(7, T)

       New Menu sent by the Merchant:

                  a(1, T)
                /           \
            b(2, T)         c(3, T)
         /    |    \           \
      d(4, T) e(5, T) f(6, T)    g(7, F)

      Expected Answer: 2
      Explanation: Node f is a newly-added node.
                   Node g changed from Active to inactive

  */
  class Node {
    String key; // unique ID
    int value;
    boolean active;
    List<Node> children; // assume the order in the list do not matter
  }
  /*
  Watch:
    no duplicate nodes with the same key.

    calculate: Map<key, number of this tree> old, new;
    for each node
       itself: if either value change or the active status change means the node has been changed.
               diff++;
       number of children:
               compare in 2 tree: diff+=  deleted number + added number
               for each exiting child call this function again.

    O(N+M) time, N: old tree nodes number, M: new tree nodes number,
    O(M+N) space.
  */

  int diffNodesNumber(Node n, Node n2) { // Menu  node and Merchant Menu node with same key
    Map<String, Integer> num = new HashMap<>(), num2 = new HashMap<>(); // tree and its node number
    num(n, num);
    num(n2, num2);
    if (n == null && n2 == null) return 0;
    if (n == null || n2 == null)
      return (num.get(n.key) == null ? 0 : num.get(n.key))
          + (num2.get(n2.key) == null ? 0 : num2.get(n2.key));

    int r = 0; // result:  diff nodes number
    if (n.active != n2.active || n.value != n2.value) r++; // 1> themself

    Set<String> kset1 = new HashSet<>(); // child keys
    Map<String, Node> kmap2 = new HashMap<>(); // key -> node of Merchant
    n.children.forEach(node -> kset1.add(node.key));
    n2.children.forEach(node -> kmap2.put(node.key, node));

    for (Node c : n.children) {
      if (kmap2.containsKey(c.key)) r += diffNodesNumber(c, kmap2.get(c.key)); // recursion
      else r += num.get(c.key); // 2> deleted
    }
    for (Node c : n2.children) if (!kset1.contains(c.key)) r += num2.get(c.key); // 3> added
    return r;
  }

  private int num(Node n, Map<String, Integer> map) {
    if (n == null) return 0;
    int sum = 1;
    for (Node c : n.children) sum += num(c, map);
    map.put(n.key, sum);
    return sum;
  }
  /*---------------------------------------------------------------------------
    recursion once version
  */
  public int f(Node n1, Node n2) {
    int r = 0;
    if (n1 == null && n2 == null) return r;
    if (n1 == null || n2 == null || !equals(n2, n1)) r++;
    Map<String, Node> child1 = mapChildren(n1);
    Map<String, Node> child2 = mapChildren(n2);
    for (String k : child1.keySet()) {
      r += f(child1.get(k), child2.getOrDefault(k, null));
    }
    for (String k : child2.keySet()) {
      if (!child1.containsKey(k)) {
        r += f(null, child2.get(k));
      }
    }
    return r;
  }

  private static Map<String, Node> mapChildren(Node n) {
    Map<String, Node> map = new HashMap<>();
    if (n == null) return map;
    for (Node c : n.children) map.put(c.key, c);
    return map;
  }

  public boolean equals(Node n1, Node n2) {
    return n2.key.equals(n1.key) && n2.value == n1.value && n2.active == n1.active;
  }
}
