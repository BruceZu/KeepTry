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

package bfs;

import java.util.LinkedList;
import java.util.Queue;

public class Leetcode1161MaximumLevelSumofaBinary {
  static class TreeNode {
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
    the level of binary tree root is 1
    Return the smallest level x such that the sum of all the values of nodes at level x is maximal.

   The number of nodes in the tree is in the range [1, 10^4].
   -10^5 <= Node.val <= 10^5

   Idea: BFS
   use a queue to keep each layer nodes
   Note: - The number of nodes in the tree is in the range [1, 10^4].
           so root is not null
         - -10^5 <= Node.val
           so initial value of max can be Integer.MIN_VALUE

   O(N) time, space
   max: result level node sum,
   l: level:  initial value is 0, not 1, l++ at the start of processing each layer nodes
              accordingly the initial value of max is Integer.MIN_VALUE
   r: result level
   n: current node
   sum: sum of current layer nodes' value
  */
  public int maxLevelSum(TreeNode root) {
    int l = 0, r = 0, max = Integer.MIN_VALUE;
    Queue<TreeNode> q = new LinkedList();
    q.offer(root);
    while (!q.isEmpty()) {
      l++;
      int sum = 0;
      int size = q.size();
      while (size-- > 0) {
        TreeNode n = q.poll();
        sum += n.val;
        if (n.left != null) q.offer(n.left);
        if (n.right != null) q.offer(n.right);
      }
      if (sum > max) {
        max = sum;
        r = l;
      }
    }
    return r;
  }
}
