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

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Leetcode103BinaryTreeZigzagLevelOrderTraversal {
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
  /*
   Given the root of a binary tree, return the zigzag level order traversal of its nodes' values.
   (i.e., from left to right, then right to left for the next level and alternate between).

    Input: root = [3,9,20,null,null,15,7]
    Output: [[3],[20,9],[15,7]]


    Input: root = [1]
    Output: [[1]]


    Input: root = []
    Output: []


    Constraints:
    The number of nodes in the tree is in the range [0, 2000].
    -100 <= Node.val <= 100

  */

  /*
  O(N) time, space
  */
  public List<List<Integer>> zigzagLevelOrder_(TreeNode root) {
    List<List<Integer>> ans = new LinkedList<>();
    if (root == null) return ans;

    Queue<TreeNode> q = new LinkedList<>();
    q.add(root);
    boolean even = true; // of level in q

    while (!q.isEmpty()) {
      LinkedList<Integer> level = new LinkedList<>();
      int n = q.size();
      while (n-- >= 1) {
        TreeNode node = q.poll(); // always keep next level from left to right
        if (node.left != null) q.offer(node.left);
        if (node.right != null) q.offer(node.right);

        if (even) level.add(node.val); // current level from q.
        else level.addFirst(node.val);
      }
      ans.add(level);
      even = !even;
    }

    return ans;
  }

  /* --------------------------------------------------------------------------
  DFS
  O(N) time
  O(H) space
  */
  public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    List<List<Integer>> ans = new LinkedList<>();
    if (root == null) return ans;
    DFS(root, 0, ans);
    return ans;
  }

  // Assume node is not null
  protected void DFS(TreeNode node, int level, List<List<Integer>> ans) {
    if (level >= ans.size()) {
      ans.add(new LinkedList<>());
    }
    if (level % 2 == 0) ans.get(level).add(node.val);
    else ans.get(level).add(0, node.val);

    if (node.left != null) DFS(node.left, level + 1, ans); // left first
    if (node.right != null) DFS(node.right, level + 1, ans);
  }
}
