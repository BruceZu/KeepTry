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

// TODO: 7/22/16  need improve;

public class TraversalBTusingStack {
    static class TreeNode {
        public int v;
        TreeNode left;
        TreeNode right;

        public TreeNode(int x) {
            v = x;
        }
    }

    /**
     * Data structure:
     * Stack
     * what is the stack for?
     * keep sub trees
     *
     * @see <a href="http://www.jiuzhang.com/solutions/binary-tree-preorder-traversal/">jiuzhang</a>
     */
    public static void printPreOrder(TreeNode n) {
        if (n == null) {
            return;
        }
        Stack<TreeNode> subtrees = new Stack();
        subtrees.push(n);
        while (!subtrees.empty()) {
            n = subtrees.pop();
            System.out.println(n.v);
            if (n.right != null) {
                subtrees.push(n.right);
            }
            if (n.left != null) {
                subtrees.push(n.left);
            }
        }
    }

    /**
     * 下黄泉,上穷碧落<pre>
     * step 1: down to bottom without not breaking where is the start place
     * step 2: find bottom, process it
     * step 3: next one:
     *          according to post order:
     *          if bottom have parent:
     *               if bottom is right child, continue up
     *               if bottom is left child and no brother, continue up
     *               if bottom is left child and have right brother. then go to brother.
     *          else: end up
     * <p>
     * what is stack for?
     *   keep track of parents only.
     *   @see <a href="http://www.jiuzhang.com/solutions/binary-tree-postorder-traversal/">jiuzhang</a>
     */
    public static void printPostOrder(TreeNode n) {
        if (n == null) {
            return;
        }
        Stack<TreeNode> parents = new Stack();
        while (true) {
            // 1
            parents.push(n);
            while (true) {
                if (n.left != null) {
                    n = n.left;
                } else if (n.right != null) {
                    n = n.right;
                } else {
                    break; //n.left == null &&  n.right == null
                }
                parents.push(n);
            }
            // 2
            parents.pop();
            System.out.println(n.v);

            // 3
            while (!parents.empty() &&
                    (n == parents.peek().right
                            || n == parents.peek().left && parents.peek().right == null
                    )) {
                n = parents.pop();
                System.out.println(n.v);
            }

            if (parents.empty()) {
                break;
            }
            n = parents.peek().right;
        }
    }

    public static void printPostOrder2(TreeNode n) {
        if (n == null) {
            return;
        }
        Stack<TreeNode> s = new Stack();
        loop:
        while (true) {
            if (n.left != null) {
                s.push(n);
                n = n.left;
                continue;
            }
            if (n.right != null) {
                s.push(n);
                n = n.right;
                continue;
            }
            System.out.println(n.v);

            TreeNode p;
            while (true) {
                p = s.peek();
                if (n == p.left && p.right != null) {
                    n = p.right;
                    break;
                }
                n = s.pop();
                System.out.println(n.v);
                if (s.empty()) {
                    break loop;
                }
            }
        }
    }

    /**
     * <pre>
     * Special:  It is useful for Binary search tree
     *
     * how to check all case of BT:
     *     use the fish head
     *
     * Data Structure:
     *   stack and current Node pointer <Strong>which is not got from the top of stack</Strong>.
     *
     * How to handle the stack?
     *   easy push in, hard to handle when and how to pop out.
     *   Firstly need know the meanings of stack for.
     * what is the stack for?
     *    save left branches.
     *    keep track from top to down of left branch
     *    till the end where left sub tree is null.
     *    then according to in order print or process the current node,
     *    then its right subtree if the right subtree is not null
     *
     *    Any tree can be understand as composited by left branches,
     *    sometimes the left branch is only one node.
     *
     * How to handle stack:
     *    1 step   track left branch from top to down till the end
     *    2 step   it is branch end, so back up and process the end one
     *             here need check if end up all
     *    3 step   see if continue down to sub right tree, continue track left branch from there
     */
    public static void printInOrder(TreeNode n) {
        Stack<TreeNode> leftBranches = new Stack();
        while (true) {
            while (n != null) {//1
                leftBranches.push(n);
                n = n.left;
            }

            if (leftBranches.empty()) {//2
                break;// end up all
            }
            n = leftBranches.pop();
            System.out.println(n.v);

            n = n.right;//3
        }
    }

    public static void main(String[] args) {
        /**
         * <pre> pre-order: top is first
         *        1
         *      /  \
         *     2   6
         *    /     \
         *   3      7
         *    \    /
         *     4  8
         *    /    |
         *   5     9
         */
        System.out.println("\n pre order\n");
        TreeNode r = new TreeNode(1);
        r.left = new TreeNode(2);
        r.left.left = new TreeNode(3);
        r.left.left.right = new TreeNode(4);
        r.left.left.right.left = new TreeNode(5);
        r.right = new TreeNode(6);
        r.right.right = new TreeNode(7);
        r.right.right.left = new TreeNode(8);
        r.right.right.left.right = new TreeNode(9);
        printPreOrder(r);

        /**
         * <pre> post-order : down is first
         *        9
         *      /  \
         *     4   8
         *    /     \
         *   3      7
         *    \    /
         *     2  6
         *    /    |
         *   1     5
         */
        System.out.println("\n post order\n");
        r = new TreeNode(9);
        r.left = new TreeNode(4);
        r.left.left = new TreeNode(3);
        r.left.left.right = new TreeNode(2);
        r.left.left.right.left = new TreeNode(1);
        r.right = new TreeNode(8);
        r.right.right = new TreeNode(7);
        r.right.right.left = new TreeNode(6);
        r.right.right.left.right = new TreeNode(5);
        printPostOrder(r);

        /**
         * <pre> in-order: left first, binary search tree
         *        5
         *      /  \
         *     4   6
         *    /     \
         *   1      9
         *    \    /
         *     3  7
         *    /    |
         *   2     8
         */
        System.out.println("\n in order\n");
        r = new TreeNode(5);
        r.left = new TreeNode(4);
        r.left.left = new TreeNode(1);
        r.left.left.right = new TreeNode(3);
        r.left.left.right.left = new TreeNode(2);
        r.right = new TreeNode(6);
        r.right.right = new TreeNode(9);
        r.right.right.left = new TreeNode(7);
        r.right.right.left.right = new TreeNode(8);
        printInOrder(r);
    }
}
