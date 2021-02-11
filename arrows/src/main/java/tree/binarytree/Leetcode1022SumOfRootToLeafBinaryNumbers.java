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

package tree.binarytree;

public class Leetcode1022SumOfRootToLeafBinaryNumbers {
  // O(N) time and space
  public int sumRootToLeaf2(TreeNode<Integer> root) {
    /// null node can judge if his parent is leaf node or not
    if (root == null) return 0;
    // Now root is not null
    return help2(root, 0);
  }
  // assume node 'n' is not null
  // v: node n's parent path value
  // 'The answer is guaranteed to fit in a 32-bits integer'.
  public int help2(TreeNode<Integer> n, int v) {
    // 'each node has a value 0 or 1'
    // 'Each root-to-leaf path represents a binary number
    // starting with the most significant bit'
    v = (v << 1) + n.v;
    if (n.left == null && n.right == null) {
      // leaf
      return v;
    }
    if (n.left != null && n.right != null) {
      return help(n.left, v) + help(n.right, v);
    }

    TreeNode child = n.left != null ? n.left : n.right;
    return help(child, v);
  }
  // -------------------------------------------------------
  // O(N) time and space
  public int sumRootToLeaf(TreeNode<Integer> root) {
    return help(root, 0);
  }
  // null node can judge if his parent is leaf node or not
  // return 0 for null node and let parent to decide its value.
  // v: node n's parent path value
  // 'The answer is guaranteed to fit in a 32-bits integer'.
  public int help(TreeNode<Integer> n, int v) {
    if (n == null) return 0;
    // 'each node has a value 0 or 1'
    // 'Each root-to-leaf path represents a binary number
    // starting with the most significant bit'
    v = (v << 1) + n.v;
    //  leaf
    if (n.left == null && n.right == null) return v;
    // not leaf, at most one child is null or at least one child is not null
    // x r
    // l x
    // l r
    return help(n.left, v) + help(n.right, v);
  }
  // ---------- Morris preorder traversal: O(N) time O(1) space----------------
  //  Think
  //  1> how to use {@link MorrisTraversal#isRightLinked(MorrisTraversal.Node)}
  //  2> how to know current node is leaf. Need break the right link if it is there.
  //  3> how to know current node related current path value when the left subtree is done?

  // ----- 1 easy understand version
  static int steps; // used from current node to step to predecessor
  static TreeNode<Integer> pre; // predecessor or current node

  public static boolean isRightLinked(TreeNode<Integer> n) {
    if (n.left == null) return false;
    // left sub tree right most node, node 'n''s predecessor child in-order
    TreeNode<Integer> pred = n.left;
    steps = 1;

    while (pred.right != null && pred.right != n) {
      pred = pred.right;
      steps++;
    }
    pre = pred;

    if (pred.right == null) {
      pred.right = n;
      return true;
    } else {
      pred.right = null;
      return false;
    }
  }

  public int sumRootToLeaf5(TreeNode<Integer> node) {
    int sum = 0, cur = 0;
    while (node != null) {
      // 1> Switch to left subtree
      if (isRightLinked(node)) {
        cur = (cur << 1) | node.v;
        node = node.left;
        continue;
      }
      // 2> Switch right subtree
      // 2-1> the left subtree has been visited and link is broken
      // before switch current node to its right, do:
      // - predecessor is leaf? as its right link is broken now
      // - keep cur path value to match current node
      if (node.left != null) {
        if (pre.left == null) sum += cur; // the cur is still matching the predecessor
        for (int i = 0; i < steps; ++i) cur >>= 1;
      }

      // 2-2> the left node is null,
      // before switch to right subtree do:
      // - visit current node,
      // - check if current node is leaf, this is for the binary tree
      // right most node only.
      if (node.left == null) {
        cur = (cur << 1) | node.v;
        if (node.right == null) sum += cur;
      }
      node = node.right;
    }
    return sum;
  }

  // ---------- Morris preorder traversal: O(N) time O(1) space----------------
  // ----- 2  merge into one version
  public int sumRootToLeaf3(TreeNode<Integer> node) {
    int sum = 0, cur = 0;
    while (node != null) {
      // node is not null
      if (node.left != null) {
        // 1> Current node has left subtree
        TreeNode<Integer> pre = node.left; // predecessor child node
        int steps = 1; // used to keep path value matching current node when left subtree is done
        while (pre.right != null && pre.right != node) {
          pre = pre.right;
          steps++;
        }
        if (pre.right == null) { // and the left subtree has not handled.
          cur = (cur << 1) | node.v; // a>  handle current node value
          pre.right = node;
          node = node.left;
          continue;
        }
        // 2> The left subtree has been handled, break the link.
        pre.right = null;

        // predecessor iss a leaf, update the sum
        if (pre.left == null) {
          sum += cur;
        }

        // backtrack the path value to match node and switch to its right subtree
        for (int i = 0; i < steps; ++i) {
          cur >>= 1;
        }
      } else { // 3> Current node has not left subtree. It may be leaf or has only right subtree.
        cur = (cur << 1) | node.v; // b> handle current node value
        if (node.right == null) {
          // Checking the rightmost node of the tree only. Because other leafs' right
          // child is right linking to its successor, only when the link is broken after the current
          // node is switched to the successor, it is possible to check whether the predecessor is a
          // leaf
          sum += cur;
        }
      }
      node = node.right;
    }
    return sum;
  }

  // ---------- Morris preorder traversal: O(N) time O(1) space----------------
  // ----- 3 merge into one version No comments version
  public int sumRootToLeaf4(TreeNode<Integer> node) {
    int sum = 0, cur = 0;
    while (node != null) {
      if (node.left != null) {
        TreeNode<Integer> pre = node.left;
        int steps = 1;
        while (pre.right != null && pre.right != node) {
          pre = pre.right;
          steps++;
        }
        if (pre.right == null) {
          cur = (cur << 1) | node.v;
          pre.right = node;
          node = node.left;
          continue;
        }
        pre.right = null;
        if (pre.left == null) sum += cur;
        for (int i = 0; i < steps; ++i) cur >>= 1;
      } else {
        cur = (cur << 1) | node.v;
        if (node.right == null) sum += cur;
      }
      node = node.right;
    }
    return sum;
  }
}
