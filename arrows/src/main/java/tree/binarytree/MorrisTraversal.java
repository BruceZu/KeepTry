//  Copyright 2019 The KeepTry Open Source Project
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

/**
 * <pre>
 *
 * Require space complexity is O(1).
 * - No use stack and recursion.
 * - not modify tree structure and Node data structure.
 *
 */
public class MorrisTraversal {
  static class Node {
    String name;
    Node l, r;

    public Node() {}

    public Node(String name) {
      this.name = name;
    }
  }

  /**
   * <pre>
   *   true: created thread
   *   false: deleted thread
   *
   */
  private static boolean thread(Node n) {
    if (n.l != null) {
      Node most = n.l; // left sub tree right most node

      while (most.r != null && most.r != n) {
        most = most.r;
      }
      if (most.r == null) {
        most.r = n;
        return true;
      } else {
        most.r = null;
        return false;
      }
    }
    return false;
  }

  public static String inOrder(Node root) {
    StringBuilder r = new StringBuilder();
    Node n = root;
    while (n != null) {
      if (n.l != null && thread(n)) {
        n = n.l;
        continue;
      }
      r.append(n.name);
      n = n.r;
    }
    return r.toString();
  }

  public static String inOrder2(Node root) {
    StringBuilder r = new StringBuilder();
    Node n = root;
    while (n != null) { // root | right most node.
      // create / delete thread before handler left subtree
      if (n.l != null) {
        boolean isCreaded = thread(n);
        if (isCreaded) {
          n = n.l;
          continue;
        }
      }
      // left most end | left sub stree has been processed
      r.append(n.name); // in order
      n = n.r; // right sub tree ( maybe threaded root)
    }
    return r.toString();
  }

  public static String preOrder(Node root) {
    StringBuilder r = new StringBuilder();
    Node n = root;
    while (n != null) {
      if (n.l != null) {
        boolean isCreated = thread(n);
        if (isCreated) {
          r.append(n.name); // pre order
          n = n.l;
        } else {
          n = n.r;
        }
      } else {
        r.append(n.name); // pre order
        n = n.r;
      }
    }
    return r.toString();
  }

  public static String postOrder(Node root) {
    StringBuilder r = new StringBuilder();
    Node n = root;
    while (n != null) {
      if (n.l != null) {
        boolean isCreated = thread(n);
        if (isCreated) {
          n = n.l;
        } else {
          printRihtLineBottomUp(n.l, r);
          n = n.r;
        }
      } else {
        n = n.r;
      }
    }
    printRihtLineBottomUp(root, r);
    return r.toString();
  }

  private static void printRihtLineBottomUp(Node top, StringBuilder result) {
    Node end = null; // right most end
    while (top != end) {
      Node from = top;
      while (from.r != end) {
        from = from.r;
      }
      result.append(from.name);
      end = from;
    }
  }

  // --------------------------------------------------------
  public static void main(String[] args) {

    // null
    System.out.println(inOrder(null).equals(""));
    System.out.println(preOrder(null).equals(""));
    System.out.println(postOrder(null).equals(""));
    // one node
    Node root = new Node("A");
    System.out.println(inOrder(root).equals("A"));
    System.out.println(preOrder(root).equals("A"));
    System.out.println(postOrder(root).equals("A"));

    Node l = new Node("L");
    Node r = new Node("R");

    // no right subtree
    root.l = l;
    System.out.println(inOrder(root).equals("LA"));
    System.out.println(preOrder(root).equals("AL")); //
    System.out.println(postOrder(root).equals("LA"));
    // no left subtree
    root.l = null;
    root.r = r;

    System.out.println(inOrder(root).equals("AR"));
    System.out.println(preOrder(root).equals("AR"));
    System.out.println(postOrder(root).equals("RA"));
    // has both left and right subtree
    root.l = l;
    System.out.println(inOrder(root).equals("LAR"));
    System.out.println(preOrder(root).equals("ALR")); //
    System.out.println(postOrder(root).equals("LRA"));

    // other case.
    Node n1 = new Node("1");
    Node n2 = new Node("2");
    Node n3 = new Node("3");
    Node n4 = new Node("4");
    Node n5 = new Node("5");
    Node n6 = new Node("6");
    Node n7 = new Node("7");
    Node n8 = new Node("8");
    Node n9 = new Node("9");
    n1.l = n2;
    n1.r = n7;
    n2.l = n3;
    n2.r = n4;
    n4.l = n5;
    n4.r = n6;
    n7.r = n8;
    n8.l = n9;
    System.out.println(inOrder(n1).equals("325461798"));
    System.out.println(preOrder(n1).equals("123456789"));
    System.out.println(postOrder(n1).equals("356429871"));
  }
}
