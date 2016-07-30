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
 * <pre>
 *                   r
 *                /      \
 *
 *     ---
 *              l         r
 *            /   |     /    |
 *           ll    lr  rl     rr
 *
 *     ---
 *          /  \  / \  /  \  /  \
 *        5    4 3  2 2   3 4   5
 *
 *       no problem to push null in to stack
 *       push/pop pairs of nodes that should match together
 */

public class Leetcode101SymmetricTree2 {
    //iteratively.
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    // space complexity: ?
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        Stack<TreeNode> s = new Stack();
        s.push(root.left);
        s.push(root.right);

        while (!s.empty()) {
            TreeNode l = s.pop();
            TreeNode r = s.pop();

            if (l == null && r == null) {
                continue;
            }
            if (r == null || l == null || l.val != r.val) {
                return false;
            }

            s.push(l.right);
            s.push(r.left);

            s.push(l.left);
            s.push(r.right);
        }
        return true;
    }
}
