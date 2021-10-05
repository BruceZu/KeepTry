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

package tree.binary_search_tree;

public class Leetcode99RecoverBinarySearchTree {
  /*
  Leetcode  99. Recover Binary Search Tree

  You are given the root of a binary search tree (BST),
  where the values of exactly two nodes of the tree were swapped by mistake.
  Recover the tree without changing its structure.


  Input: root = [1,3,null,null,2]
  Output: [3,1,null,null,2]
  Explanation: 3 cannot be a left child of 1 because 3 > 1. Swapping 1 and 3 makes the BST valid.
  Example 2:


  Input: root = [3,1,4,null,null,2]
  Output: [2,1,4,null,null,3]
  Explanation: 2 cannot be in the right subtree of 3 because 2 < 3. Swapping 2 and 3 makes the BST valid.


  Constraints:

  The number of nodes in the tree is in the range [2, 1000].
  -2^31 <= Node.val <= 2^31 - 1


  Follow up: A solution using O(n) space is pretty straight-forward. Could you devise a constant O(1) space solution?
   */

  /*
                4
            3       5
        6              2
     1                    7
     1  6   3   4   5  2  7
      v   x   v   v   x

     left is node 6
     right is node 2

   Exactly one pair, they must not a neighborhood
   they call 2 wrong relation

   O(N) time and space used by call stack
  */
  TreeNode left = null, right = null, pre = null;

  public void swap(TreeNode a, TreeNode b) {
    int tmp = a.val;
    a.val = b.val;
    b.val = tmp;
  }

  public void findTwoSwapped(TreeNode n) {
    if (n == null) return;
    findTwoSwapped(n.left);
    if (pre != null && n.val < pre.val) {
      right = n;
      if (left == null) left = pre;
      else return;
    }
    pre = n;
    findTwoSwapped(n.right);
  }

  public void recoverTree(TreeNode root) {
    findTwoSwapped(root);
    swap(left, right);
  }
  /*
  how to improve the space?
  Morris inorder travel
  O(N) time since we visit each node up to two times.???
  O(1) space
   */

  /*
   The tree rightmost node.r is null. other non-null node under processing always has right child
   and can always switch to right subtree/linked parent.
   {@link MorrisTraversal#isRightLinked(MorrisTraversal.Node)} will check whether current.l is null.
   Code in loop will switch current node to it's left subtree only when the left subtree is not
   null.
  */
  public void recoverTree_Morris(TreeNode n) {
    while (n != null) {
      while (linkAndContinue(n)) {
        n = n.left;
      }
      // logic start
      if (pre != null && n.val < pre.val) {
        right = n;
        if (left == null) left = pre;
      }
      // logic end
      pre = n;
      n = n.right;
    }
    swap(left, right);
  }

  public boolean linkAndContinue(TreeNode n) {
    if (n.left == null) return false;
    TreeNode x = n.left; // left sub tree right most node, node 'n''s predecessor child in-order
    while (x.right != null && x.right != n) {
      x = x.right;
    }
    if (x.right == null) {
      x.right = n;
      return true;
    } else {
      x.right = null;
      return false;
    }
  }
}
