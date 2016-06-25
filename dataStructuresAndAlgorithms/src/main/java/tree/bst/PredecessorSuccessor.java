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

package tree.bst;

/**
 * <pre>
 * There is BST given with root node with key part as integer only. The structure of each node is as follows:
 * You need to find the inorder successor and predecessor of a given key.
 * In case the given key is not found in BST, then return the two values within which this key will lie.
 *
 *    binary search tree:
 *       if the given key is found at Node n then
 *           if n has left child,  then the predecessor will be the rightest child of n's left sub binary search tree
 *           if n has right child, the the success will be the left child of n's right sub bianry serach tree.
 *           e.g. key is 13 -> result is [12, 14]
 *       if the given key is not found, then
 *           this sure will be confirmed at one leaf child of the given binary search tree.
 *           when starting binary search from the root, just keep the last left bound when go right,
 *           or last right bound when go left till reach this leaf node.
 *           e.g. key is 13.5 -. result is[13, 14]
 *
 *   Also the give key may be not lie in the scope of BST.
 *
 *
 *                              17
 *                     /
 *               11
 *            /       \
 *          9         13
 *        /   \      /     \
 *       8    10   12      15
 *                        /  \
 *                      14   16
 *
 *  @see <a href ="http://www.geeksforgeeks.org/inorder-predecessor-successor-given-key-bst/">geeksforgeeks</a>
 */

public class PredecessorSuccessor {
    static class Node {
        float key;
        Node left, right;

        @Override
        public String toString() {
            return "key = " + key;
        }
    }

    // assume n != null
    private static Node rightMostChild(Node n) {
        if (n.right == null) {
            return n;
        }
        return rightMostChild(n.right);
    }

    // assume n != null
    private static Node leftMostChild(Node n) {
        if (n.left == null) {
            return n;
        }
        return leftMostChild(n.left);
    }

    /**
     * @param re re[0] kee the predecessor and re[1] keep the successor
     */
    private static void call(float k, Node n, Node[] re) {
        if (n == null) {
            return;
        }

        if (k < n.key) {
            re[1] = n;
            call(k, n.left, re);
            return; // note: do not forget return!
        }

        if (n.key < k) {
            re[0] = n;
            call(k, n.right, re);
            return; // note
        }

        // found
        if (n.left != null) {
            re[0] = rightMostChild(n.left);
        }
        if (n.right != null) {
            re[1] = leftMostChild(n.right);
        }
    }

    public static Node[] go(float k, Node root) {
        // input check
        if (root == null) {
            return null;
        }
        Node[] re = new Node[2];
        call(k, root, re);
        return re;
    }
}
