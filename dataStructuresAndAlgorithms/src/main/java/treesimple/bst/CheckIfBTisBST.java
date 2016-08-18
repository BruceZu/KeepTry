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

package treesimple.bst;

/**
 * Time Complexity: O(n)
 *
 * @see <a href = "http://www.geeksforgeeks.org/a-program-to-check-if-a-binary-tree-is-bst-or-not/" > geeksforgeeks</a>
 */
public class CheckIfBTisBST {
    static class Node {
        int data;
        Node left, right;

        public Node(int item) {
            data = item;
            left = right = null;
        }
    }

    public static void main(String args[]) {
        /**
         * <pre>
         *            4
         *          /   \
         *         2     5
         *       /  \
         *      1    3
         *     /
         *    1
         */
        Node root = new Node(4);
        root.left = new Node(2);
        root.right = new Node(5);
        //root.right.left = new Node(6);
        root.left.left = new Node(1);
        root.left.left.left = new Node(1);
        root.left.right = new Node(3);

        if (isBST(root))
            System.out.println("IS BST");
        else
            System.out.println("Not a BST");
    }
    //--------------------------------------------

    public static boolean isBST(Node root) {
        return check(root, Integer.MIN_VALUE,
                Integer.MAX_VALUE);
    }

    public static boolean check(Node node, int min, int max) {
        if (node == null)
            return true;

        if (min < node.data && node.data <= max) { // same value as left child of the same node
            return (check(node.left, min, node.data) &&
                    check(node.right, node.data, max));
        }
        return false;
    }
}
