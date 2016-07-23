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

public class TraversalBTWithStack {
    static class TreeNode {
        public int v;
        TreeNode left;
        TreeNode right;

        public TreeNode(int x) {
            v = x;
        }
    }

    /**
     * @see <a href="http://www.jiuzhang.com/solutions/binary-tree-preorder-traversal/">jiuzhang</a>
     */
    public static void printPreOrder(TreeNode n) {
        if (n == null) {
            return;
        }
        Stack<TreeNode> s = new Stack();

        System.out.println(n.v);
        s.push(n);
        while (!s.empty()) {
            if (n.left != null) {
                n = n.left;
                System.out.println(n.v);
                s.push(n);
                continue;
            }
            if (n.right != null) {
                n = n.right;
                System.out.println(n.v);
                s.push(n);
                continue;
            }
            TreeNode p;
            while (true) {
                n = s.pop();
                if (s.empty()) {
                    break  ;
                }
                p = s.peek();
                if (n == p.left && p.right != null) {
                    n = p.right;
                    System.out.println(n.v);
                    s.push(n);
                    break;
                }
            }
        }
    }

    /**
     * @see <a href="http://www.jiuzhang.com/solutions/binary-tree-postorder-traversal/">jiuzhang</a>
     */
    public static void printPostOrder(TreeNode n) {
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
     * @see <a href="http://www.jiuzhang.com/solutions/binary-tree-inorder-traversal/">jiuzhang</a>
     */
    public static void printInOrder(TreeNode n) {
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
            System.out.println(n.v);
            if (n.right != null) {
                s.push(n);
                n = n.right;
                continue;
            }

            TreeNode p;
            while (true) {
                p = s.peek();
                if (p.left == n) {
                    System.out.println(p.v);
                }
                if (n == p.left && p.right != null) {
                    n = p.right;
                    break;
                }
                n = s.pop();
                if (s.empty()) { // n is root
                    break loop;
                }
            }
        }
    }

    public static void main(String[] args) {
        /**
         * <pre> pre-order
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
         * <pre> post-order
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
         * <pre> in-order
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
