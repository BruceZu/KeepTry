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

package tree.b_tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Leetcode545BoundaryofBinaryTree {

  public static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}

    TreeNode(int val) {
      this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }
  }
  /*
  Given a binary tree,
  print boundary nodes of the binary tree
  Anti-Clockwise starting from the root.
  The boundary includes left boundary, leaves, and right boundary
  in order
  without duplicate nodes. (The values of the nodes may still be duplicates.)

  The left boundary is defined as the path from the root to the left-most node.
  The right boundary is defined as the path from the root to the right-most node.
  If the root does’t have left subtree or right subtree, then the root itself is left boundary or right boundary.
  Note this definition *only* applies to the input binary tree, and not apply to any subtrees.

  The left-most node is defined as a *leaf* node you could reach when you always firstly
  travel to the left subtree if it exists. If not, travel to the right subtree. Repeat
  *until* you reach a leaf node.

  The right-most node is also defined in the same way with left and right exchanged.

       -------------1 ----------
      /                          \
     2                            3
       \                         / \
        5                       6   12
       / \                    /  \
     7    8                  9    10
    /
   11
   output [1  2  5  7 11 8  9  10 12  3 ]

         1
           \
             3
             /\
            6   7

  output is [ 1, 6, 7 ,3]
  */

  /*
  Solution A (wrong)
   one loop is hard. divided it into 3 parts
   left injection, start from root
   right injection, start from root
   leaf nodes between the lower layer node of left and right injects
   O(N) time, space
  The idea is wrong with the order: left boundary,leaf, right boundary
  Do not need BFS, as at each level, the both sides are the only concerned part
  for a level there is in total only one node, no way to distinguish it is of left boundary or right boundary


  key: understanding the definition of
       - `left most node`, it is a leaf node, it is not the left most node in binary search tree
       - `The left boundary` and The right boundary`:
           `only applies to the input binary tree, and not apply to any subtrees.`

  Solution B(works)
  Observation:
   - hard to do it in one loop.  separate it into 3 parts, left boudnary, leaf and right boundary
   - The boundary of boundary: once reach a leaf node, has found `the left most node` which is defined as not a
     leaf in this case. then stop all recursion as it is leaf field
     so before reach a leaf node, current node is not leaf, it has at least one child.
     so need not keep a ever reached the deepest level variable
   - the root can be a leaf node.

  Steps:
    1   according to the definition of boundary: from the root to find all left boundary nodes,
        left subtree firstly, once reach a leaf node , then stop all recursion as it is leaf field
    2   find all leaf nodes, use left sub-tree and right sub-tree.
    3   find all right boundary nodes, right subtree firstly,
        once reach a leaf node , then stop all recursion as it is leaf field
        with a stack to keep the order.
   O(N) time, space
   */
  public List<Integer> boundaryOfBinaryTree__(TreeNode root) {
    List<Integer> a = new LinkedList<>();
    if (root == null) return a;
    a.add(root.val);
    lBoundary(root.left, a);
    leaf(root.left, a);
    leaf(root.right, a);

    Stack<Integer> s = new Stack<>();
    rBoundary(root.right, s);
    while (!s.isEmpty()) a.add(s.pop());
    return a;
  }

  // Assume n is not root which is at level 0
  public boolean lBoundary(TreeNode n, List<Integer> a) {
    if (n == null) return true;
    if (n.left == null && n.right == null)
      return false; // reach the leaf boundary, stop all recursion

    a.add(n.val); // not leaf node and not null
    boolean go = lBoundary(n.left, a); // left subtree first
    if (go) return lBoundary(n.right, a);
    return go;
  }
  // middle order keep
  public void leaf(TreeNode n, List<Integer> a) {
    if (n == null) return;
    leaf(n.left, a);
    if (n.left == null && n.right == null) a.add(n.val);
    leaf(n.right, a);
  }

  // Assume n is not root which is at level 0
  public boolean rBoundary(TreeNode n, Stack<Integer> s) {
    if (n == null) return true;
    if (n.left == null && n.right == null)
      return false; // reach the leaf boundary, stop all recursion
    s.add(n.val);
    boolean go = rBoundary(n.right, s); // right subtree first
    if (go) return rBoundary(n.left, s);
    return go;
  }
  // save the stack
  public boolean rBoundary(TreeNode n, List<Integer> s) {
    if (n == null) return true;
    if (n.left == null && n.right == null)
      return false; // reach the leaf boundary, stop all recursion
    boolean go = rBoundary(n.right, s); // right subtree first
    if (go) {
      boolean tf = rBoundary(n.left, s);
      s.add(n.val); // on the backward road
      return tf;
    } else {
      s.add(n.val);
      return go; // on the backward road
    }
  }
  /* --------------------------------------------------------------------------
   no return value, void recursion function
  */
  public List<Integer> boundaryOfBinaryTree_(TreeNode node) {
    List<Integer> a = new LinkedList<>();
    if (node == null) return a;

    a.add(node.val);
    lb(node.left, a);

    leafNodes(node.left, a);
    leafNodes(node.right, a);

    rb(node.right, a);
    return a;
  }

  public void leafNodes(TreeNode node, List<Integer> a) {
    if (node == null) return;
    leafNodes(node.left, a);
    if (node.left == null && node.right == null) a.add(node.val);
    leafNodes(node.right, a);
  }

  public void lb(TreeNode node, List<Integer> a) {
    if (node == null) return;
    if (node.left != null) { // left first
      a.add(node.val);
      lb(node.left, a);
    } else if (node.right != null) {
      a.add(node.val);
      lb(node.right, a);
    }
    // stop all when node is a leaf
  }

  public void rb(TreeNode node, List<Integer> a) {
    if (node == null) return;
    if (node.right != null) { // right first
      rb(node.right, a);
      a.add(node.val); // on the backward road
    } else if (node.left != null) {
      rb(node.left, a);
      a.add(node.val); // on the backward road
    }
    // stop all when node is a leaf
  }
  /* --------------------------------------------------------------------------
  iterator replaces recursion function
  */
  public List<Integer> boundaryOfBinaryTree(TreeNode node) {
    List<Integer> a = new ArrayList<>();
    if (node == null) return a;
    if (!(node.left == null && node.right == null)) a.add(node.val);

    TreeNode t = node.left;
    while (t != null) {
      if (!(t.left == null && t.right == null)) a.add(t.val);
      t = t.left != null ? t.left : t.right;
    }

    addLeaves(a, node);

    Stack<Integer> s = new Stack<>();
    t = node.right;
    while (t != null) {
      if (!(t.left == null && t.right == null)) s.push(t.val);
      t = t.right != null ? t.right : t.left;
    }

    while (!s.empty()) a.add(s.pop());
    return a;
  }
  // Assume node is not null
  public void addLeaves(List<Integer> res, TreeNode node) {
    if (node.left == null && node.right == null) { // isLeaf
      res.add(node.val);
      return;
    }
    if (node.left != null) addLeaves(res, node.left);
    if (node.right != null) addLeaves(res, node.right);
  }

  /* --------------------------------------------------------------------------
  status conversion
  Observation:
   Preorder + filter out middle nodes + stack for the right boundary  => possible in one loop
   status transaction: current node decided by
       - parent node status
       - current node is left of right node
       - sibling node's existence
  flag:
      0: root,
      1: left boundary
      2: right boundary
      3: other(leaf and middle not of root/left/right boundary)
  O(N) time, space
  */
  public List<Integer> boundaryOfBinaryTree___(TreeNode root) {
    List<Integer> L = new LinkedList<>(), R = new LinkedList<>(), leaves = new LinkedList<>();

    preorder(root, L, R, leaves, 0);
    L.addAll(leaves);
    L.addAll(R);
    return L;
  }

  public void preorder(
      TreeNode n, List<Integer> LB, List<Integer> RB, List<Integer> leaves, int nflag) {
    if (n == null) return;
    if (isRB(nflag)) RB.add(0, n.val);
    else if (isLB(nflag) || isRoot(nflag)) LB.add(n.val);
    else if (isLeaf(n)) leaves.add(n.val);

    preorder(n.left, LB, RB, leaves, leftChildFlag(n, nflag));
    preorder(n.right, LB, RB, leaves, rightChildFlag(n, nflag));
  }
  // critical logic is here to figure out the left child is of 1|2|3
  public int leftChildFlag(TreeNode p, int pflag) {
    if (isLB(pflag) || isRoot(pflag)) return 1;
    else if (isRB(pflag) && p.right == null) return 2;
    else return 3;
  }
  // ... right child is of 1|2|3
  public int rightChildFlag(TreeNode p, int pflag) {
    if (isLB(pflag) && p.left == null) return 1;
    else if (isRB(pflag) || isRoot(pflag)) return 2;
    else return 3;
  }

  public boolean isLeaf(TreeNode cur) {
    return (cur.left == null && cur.right == null);
  }

  public boolean isRB(int flag) {
    return (flag == 2);
  }

  public boolean isLB(int flag) {
    return (flag == 1);
  }

  public boolean isRoot(int flag) {
    return (flag == 0);
  }

  // --------------------------------------------------------------------------
  public static void main(String[] args) {
    /*
            1
              \
                3
                /\
               6   7

     output is [1, 6, 7 ,3]
    */
    TreeNode n1 = new TreeNode(1), n3 = new TreeNode(3), n6 = new TreeNode(6), n7 = new TreeNode(7);
    n3.left = n6;
    n3.right = n7;
    n1.right = n3;
    Leetcode545BoundaryofBinaryTree s = new Leetcode545BoundaryofBinaryTree();
    System.out.println(s.boundaryOfBinaryTree(n1));
    /*
             -------------1 ----------
        /                          \
       2                            3
         \                         / \
          5                       6   12
         / \                    /  \
       7    8                  9    10
      /
     11
     output [1, 2, 5, 7, 11, 8, 9, 10, 12, 3]
    */
    n1 = new TreeNode(1);
    n3 = new TreeNode(3);
    n6 = new TreeNode(6);
    n7 = new TreeNode(7);
    TreeNode n2 = new TreeNode(2),
        n5 = new TreeNode(5),
        n8 = new TreeNode(8),
        n9 = new TreeNode(9),
        n10 = new TreeNode(10),
        n11 = new TreeNode(11),
        n12 = new TreeNode(12);

    n7.left = n11;
    n5.left = n7;
    n5.right = n8;
    n2.right = n5;
    n1.left = n2;
    n1.right = n3;
    n3.left = n6;
    n3.right = n12;
    n6.left = n9;
    n6.right = n10;
    s = new Leetcode545BoundaryofBinaryTree();
    System.out.println(s.boundaryOfBinaryTree(n1));
  }
}
