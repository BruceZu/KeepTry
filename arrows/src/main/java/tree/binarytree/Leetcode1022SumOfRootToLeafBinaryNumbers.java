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
}
