//  Copyright 2017 The KeepTry Open Source Project
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

package tree.binarytree;

import java.util.HashMap;
import java.util.Map;

public class ValidBTwithNodeList {
  /**
   * <pre>
   * 1 need all left and right pointer point to the node inside the list
   * 2 no circle
   * 3 all nodes must be connected
   * Follow up:
   * is valid BST.
   */
  static class Node {
    Node l, r;
  }

  enum Status {
    PARENT,
    SON,
    CONNECT(),
    UNVALID;

    Status reverse() {
      return this == Status.PARENT ? Status.SON : this == Status.SON ? Status.PARENT : UNVALID;
    }
  }

  private static boolean successMark(Map<Node, Status> marks, Node n, Status s) {
    assert s == Status.PARENT || s == Status.SON;
    if (marks.containsKey(n)) {
      if (marks.get(n) == s.reverse()) {
        marks.put(n, Status.CONNECT);
      } else {
        return false;
      }
    } else {
      marks.put(n, s);
    }
    return true;
  }
  // O(N)
  public static boolean isValidBT(Node[] list) {
    Map<Node, Status> marks = new HashMap<>();
    for (Node n : list) {
      if (!(successMark(marks, n, Status.PARENT)
          && (n.l == null || n.l != null && successMark(marks, n.l, Status.SON))
          && (n.r == null || n.r != null && successMark(marks, n.r, Status.SON)))) {
        return false;
      }
    }
    int numberOfHead = 0;
    for (Map.Entry e : marks.entrySet()) {
      if (e.getValue() == Status.PARENT) numberOfHead++;
      if (numberOfHead > 1) return false;
    }
    return numberOfHead == 1;
  }

  public static void main(String[] args) {
    Node a = new Node();
    Node b = new Node();
    Node c = new Node();
    Node d = new Node();
    a.l = b;
    b.l = c;
    c.l = d;
    d.l = a; // circle without head
    System.out.println(isValidBT(new Node[] {a, b, c, d}));
    a.l = b;
    a.r = c;
    b.l = null;
    b.r = d;
    c.l = d;
    c.r = null;
    d.l = null;
    d.r = null; // d is son of b and c
    System.out.println(isValidBT(new Node[] {a, b, c, d}));
    a.l = null;
    a.r = b;
    b.l = null;
    b.r = null;
    c.l = d;
    c.r = null;
    d.l = null;
    d.r = null; // 2 Heads
    System.out.println(isValidBT(new Node[] {a, b, c, d}));
    a.l = null;
    a.r = b;
    b.l = c;
    b.r = null;
    c.l = null;
    c.r = d;
    d.l = null;
    d.r = null; // Z shape
    System.out.println(isValidBT(new Node[] {a, b, c, d}));
  }
}
