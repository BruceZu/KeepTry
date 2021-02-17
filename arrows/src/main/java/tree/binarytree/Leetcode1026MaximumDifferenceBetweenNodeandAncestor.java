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

public class Leetcode1026MaximumDifferenceBetweenNodeandAncestor {
  // The number of nodes in the tree is in the range [2, 5000].
  // 0 <= Node.val <= 10^5
  public int maxAncestorDiff(TreeNode root) {
    return help(root, 0, Integer.MAX_VALUE);
  }

  int help1(TreeNode root, int max, int min) {
    if (root == null) return 0;
    max = Math.max(max, (Integer) root.v);
    min = Math.min(min, (Integer) root.v);

    if (root.left == null && root.right == null) {
      return max - min;
    }

    if (root.left != null && root.right != null) {
      int l = help1(root.left, max, min);
      int r = help1(root.right, max, min);
      return Math.max(l, r);
    }
    TreeNode child = root.left == null ? root.right : root.left;
    return help1(child, max, min);
  }

  int help(TreeNode root, int max, int min) {
    if (root == null) return max - min;
    max = Math.max(max, (Integer) root.v);
    min = Math.min(min, (Integer) root.v);
    return Math.max(help(root.left, max, min), help(root.right, max, min));
  }
}
