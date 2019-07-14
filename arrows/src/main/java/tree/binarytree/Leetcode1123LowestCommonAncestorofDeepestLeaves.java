//  Copyright 2019 The KeepTry Open Source Project
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

/**
 * <pre>
 * Given a rooted binary tree, return the lowest common ancestor of its deepest leaves.
 *
 * Recall that:
 *
 * The node of a binary tree is a leaf if and only if it has no children
 * The depth of the root of the tree is 0, and if the depth of a node is d, the depth of each of its children is d+1.
 * The lowest common ancestor of a set S of nodes is the node A with the largest depth such that every node in S is in the subtree with root A.
 * Example 1:
 *
 * Input: root = [1,2,3]
 * Output: [1,2,3]
 * Explanation:
 * The deepest leaves are the nodes with values 2 and 3.
 * The lowest common ancestor of these leaves is the node with value 1.
 * The answer returned is a TreeNode object (not an array) with serialization "[1,2,3]".
 * Example 2:
 *
 * Input: root = [1,2,3,4]
 * Output: [4]
 * Example 3:
 *
 * Input: root = [1,2,3,4,5]
 * Output: [2,4,5]
 *
 *
 * Constraints:
 *
 * The given tree will have between 1 and 1000 nodes.
 * Each node of the tree will have a distinct value between 1 and 1000.
 */
public class Leetcode1123LowestCommonAncestorofDeepestLeaves {
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        return help(root, 0).n;
    }

    Result help(TreeNode root, int depth) {
        if (root == null) return new Result(depth, null);
        if (root.left == root.right && root.left == null) return new Result(depth, root);

        if (root.left == null) return help(root.right, depth + 1);
        if (root.right == null) return help(root.left, depth + 1);

        Result lr = help(root.left, depth + 1);
        Result rr = help(root.right, depth + 1);
        if (lr.depth == rr.depth) return new Result(lr.depth, root);
        if (lr.depth < rr.depth) return rr;
        return lr;
    }

    static class Result {
        int depth;
        TreeNode n;

        public Result(int depth, TreeNode n) {
            this.depth = depth;
            this.n = n;
        }
    }
}
