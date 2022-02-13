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

package tree.binarytree;

import java.util.Stack;

public class Leetcode236LowestCommonAncestorofaBinaryTree {
  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }
  /*
   Leetcode  236. Lowest Common Ancestor of a Binary Tree

   Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

   According to the definition of LCA on Wikipedia:
   “The lowest common ancestor is defined between two nodes p and q as the lowest node in T
    that has both p and q as descendants (where we allow a node to be a descendant of itself).”

   Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
   Output: 3
   Explanation: The LCA of nodes 5 and 1 is 3.

   Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
   Output: 5
   Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
   Example 3:

   Input: root = [1,2], p = 1, q = 2
   Output: 1


   Constraints:

   The number of nodes in the tree is in the range [2, 10^5].
   -10^9 <= Node.val <= 10^9
   All Node.val are unique.
   p != q
   p and q will exist in the tree.
  */

  /* --------------------------------------------------------------------------
  DFS recursion backtracking
  Note: there is only answer in this question.
  O(N) time and space.
  */
  TreeNode ans = null;

  public TreeNode lowestCommonAncestor_(TreeNode root, TreeNode p, TreeNode q) {
    dfs(root, p, q);
    return ans;
  }
  // find p or q from sub tree.
  public boolean dfs(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null) return false;
    boolean l = dfs(root.left, p, q), r = dfs(root.right, p, q), me = root == q || root == p;
    if (l && r || l && me || r && me) ans = root;
    if (l || r || me) return true;
    return false;
  }
  /*
  Followup
    if there is not the   p != q?
  */

  /*---------------------------------------------------------------------------
  Alternative of recursion is explicitly using stack in iterative
  mock the status keeping in recursion with a stack and Entry.
     going down
     going up with status: found node number and visited children

  Assume: only one LCA. once find then exit the loop.
          If we have parent pointers for each node we can traverse back from p and q to
          get their ancestors.
          The first common node we get during this traversal would be the LCA node.

  Time and space O(N)
   */
  class Entry {
    TreeNode node;
    int visN; // visited children sub-tree number
    int foundN; // number of found nodes in current subtree with current node at root

    public Entry(TreeNode n) {
      this.node = n;
    }
  }

  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    TreeNode LCA = null;
    Stack<Entry> s = new Stack<>(); // stack explicitly used
    s.push(new Entry(root));
    while (!s.isEmpty() && LCA == null) {
      Entry peek = s.peek();
      if (s.peek().visN == 0) {
        if (peek.node.left != null) s.push(new Entry(peek.node.left));
        else peek.visN++;
      } else if (peek.visN == 1) {
        if (peek.node.right != null) s.push(new Entry(peek.node.right));
        else peek.visN++;
      } else if (peek.visN == 2) {
        Entry me = s.pop(); // can be empty now
        if (me.node == p || me.node == q) me.foundN++;
        if (me.foundN == 2) LCA = me.node;
        // rise up the status:  found node number, visited children number
        if (me.foundN == 1) if (!s.isEmpty()) s.peek().foundN++;
        if (!s.isEmpty()) s.peek().visN++; // I am left or right child of parent
      }
    }
    return LCA;
  }
}
