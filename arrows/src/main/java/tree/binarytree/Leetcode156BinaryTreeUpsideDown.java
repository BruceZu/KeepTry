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

import java.util.Stack;

public class Leetcode156BinaryTreeUpsideDown {

  /*
  Idea:
  Assume a node has 0 or 2 child, right child always is leaf.
  O(N) time and space.
  tree high =N/2, N is tree node number
   */
  public TreeNode upsideDownBinaryTree(TreeNode root) {
    if (root == null || root.left == null) return root;
    Stack<TreeNode> s = new Stack();
    while (root.left != null) {
      s.push(root);
      root = root.left;
    }
    TreeNode r = root;
    while (!s.isEmpty()) {
      root.left = s.peek().right;
      root.right = s.peek();
      root = s.pop();
    }
    // make the original root as leaf
    root.left = null;
    root.right = null;
    return r;
  }

  /*
  Iterative solution
   topdown to keep nodes whose child will be changed.
   initially the root is taken as a left node and its root is null and sibling right node is null
   O(1) space
   */

  public TreeNode upsideDownBinaryTree3(TreeNode root) {
    TreeNode curL = root, curR = null, curRoot = null, nextL, nextR;
    while (curL != null) {
      nextL = curL.left;
      nextR = curL.right;

      curL.right = curRoot;
      curL.left = curR;

      curRoot = curL;
      curL = nextL;
      curR = nextR;
    }
    return curRoot;
  }
}
