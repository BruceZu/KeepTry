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

package treesimple.binarytree;

import java.util.Stack;

/**
 * <a href="https://leetcode.com/problems/sum-root-to-leaf-numbers/">leetcode</a>
 */
public class Leetcode129SumRoottoLeafNumbers {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public class Solution {
        public int sumNumbers(TreeNode root) {
            return sumSubTree(root, 0);
        }

        private int sumSubTree(TreeNode n, int sumBefore) {
            // subtree is null
            if (n == null) {
                return 0;
            }
            sumBefore = sumBefore * 10 + n.val;
            // subtree is leaf
            if (n.left == null && n.right == null) {
                return sumBefore;
            }
            // other cases
            return sumSubTree(n.left, sumBefore) + sumSubTree(n.right, sumBefore);
        }
    }
}
