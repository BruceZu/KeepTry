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

package nosubmmitted;

/**
 * Difficulty: Medium <pre>
 * Given a binary tree, find the length of the longest consecutive sequence path.
 * <p/>
 * The path refers to any sequence of nodes from some starting node to any node
 * in the tree along the parent-child connections. The longest consecutive path
 * need to be from parent to child (cannot be the reverse).
 * <p/>
 * For example,
 * <p/>
 *     1
 *     \
 *     3
 *   /  \
 *  2    4
 *        \
 *        5
 * <p/>
 * Longest consecutive sequence path is 3-4-5, so return 3.
 * <p/>
 * <p/>
 *    2
 *     \
 *     3
 *    /
 *   2
 *  /
 * 1
 * <p/>
 * <p/>
 * Longest consecutive sequence path is 2-3,not3-2-1, so return 2.
 * <p/>
 * Hide Company Tags Google
 * Hide Tags Tree
 * Hide Similar Problems (H) Longest Consecutive Sequence
 */

/**
 * Definition for a binary tree node.
 */


public class LC298BinaryTreeLongestConsecutiveSequence {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    public int longestConsecutive(TreeNode root) {
        return 0;
    }

    // beat 80% , the fast one currently without any votes.
    int max;

    public int longestConsecutive2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        max = 0;
        helper(root, root, 0);
        return max;

    }

    public void helper(TreeNode root, TreeNode pre, int count) {
        if (root == null) {
            return;
        }
        if (pre.val + 1 == root.val) {
            count += 1;
        } else {
            count = 1;
        }
        if (count > max) {
            max = count;
        }
        helper(root.left, root, count);
        helper(root.right, root, count);
    }
    //
}

