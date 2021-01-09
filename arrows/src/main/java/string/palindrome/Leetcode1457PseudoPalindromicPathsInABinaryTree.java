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

package string.palindrome;

public class Leetcode1457PseudoPalindromicPathsInABinaryTree {

  public class TreeNode {
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

  class Solution {
    private int r = 0, c = 0;

    public int pseudoPalindromicPaths(TreeNode root) {
      traversal(root);
      return r;
    }

    // Runtime is O(N) N is binary tree node number
    // Space O(H) H is binary tree height
    private void traversal(TreeNode node) {
      if (node == null) {
        return;
      }
      // node is not null
      c ^= 1 << node.val; // val is [1~9], do not forget '1<<'
      if (node.left == null && node.right == null) {
        // node is leaf
        r += (c & (c - 1)) == 0 ? 1 : 0;
      }
      traversal(node.left);
      traversal(node.right);
      c ^= 1 << node.val;
    }
  }
  // ------------------------------------------------------------------------
  // start calculate the number of seudo palindromic paths once traversal reach
  // the leaf and collect them bottom up and return it.
  public int pseudoPalindromicPaths2(TreeNode root) {
    return f(root, 0);
  }
  // f() return the number of seudo palindromic paths contributed via the specific node.
  int f(TreeNode n, int c) {
    if (n == null) return 0; // contribute nothing to path
    c ^= 1 << n.val;
    if (n.left == null && n.right == null) return (c & (c - 1)) == 0 ? 1 : 0; // leaf
    int r = f(n.left, c) + f(n.right, c);
    // this line can be saved. because c is local variable does not affect caller
    // layer.
    c ^= 1 << n.val;
    return r;
  }
}
