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

// https://leetcode.com/problems/binary-tree-maximum-path-sum/
// Given a binary tree, find the maximum path sum. A path is defined as any
// sequence of nodes from some starting node to any node in the tree along
// the parent-child connections. The path does not need to go through the root.
// For example:
//     1
//    / \
//   2   3
//  Return 6.


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

// not find the fast way yet.
public class Leetcode124BinaryTreeMaximumPathSum {
    private static int max = Integer.MIN_VALUE;

    public static int maxPathSum(TreeNode root) {
        int rd = dfs(root);
        return rd > max ? rd : max;
    }

    // close paths:
    // 1 left leaf
    // 2 right leaf
    // 3 left + root + right
    //
    // open paths:
    // 4 root
    // 5 left + root
    // 6 right + root
    private static int dfs(TreeNode root) {
        if (root.left == null && root.right == null) {
            return root.val;
        }
        if (root.left == null && root.right != null) {
            int rPathMax = dfs(root.right);
            max = rPathMax > max ? rPathMax : max;
            return Math.max(root.val, rPathMax + root.val);
        }
        if (root.right == null && root.left != null) {
            int lPathMax = dfs(root.left);
            max = lPathMax > max ? lPathMax : max;
            return Math.max(root.val, lPathMax + root.val);
        }
        int lPathMax = dfs(root.left);
        int rPathMax = dfs(root.right);
        int closePathMax = lPathMax + rPathMax + root.val;

        int innerMax = Math.max(closePathMax, Math.max(lPathMax, rPathMax));
        max = innerMax > max ? innerMax : max;
        return Math.max(root.val, Math.max(lPathMax + root.val, rPathMax + root.val));
    }

    public static int maxPathSum2(TreeNode root) {
        dfs2(root);
        return max;
    }

    private static int dfs2(TreeNode root) {
        if (root == null) {
            return 0;
            // thus without many 'if else' branches
            //     1
            //    / \
            //   2   3
            //  / \ / \
            // 0  0 0 0
        }
        int lpMax = dfs2(root.left);
        int rpMax = dfs2(root.right);
        if (lpMax + rpMax + root.val > max) {
            max = lpMax + rpMax + root.val;
        }

        // replace max with 0 when the max is negative.
        // Thus there is not negative value in the tree and need not
        // care about single nodes.
        // 1> As a result there only 3 lines left.
        // 2> It does not affect the result value.
        return Math.max(0, root.val + Math.max(lpMax, rpMax));
    }
}
