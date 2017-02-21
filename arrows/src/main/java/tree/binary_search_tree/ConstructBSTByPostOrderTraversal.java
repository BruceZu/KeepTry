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

package tree.binary_search_tree;

/**
 * <pre>
 * To construct a BST you need only one (not in-order) traversal.
 * Can't create a tree with only the in order traversal because it is in fact a sorted array and
 * can not decide where is the root.
 *
 * For post order array, the last node will definitely be in range, so create root node.
 *
 * Go array from right to left:
 * To construct the left subtree, set the range as {INT_MIN, root.data}.
 * If a values is in the range { MIN, root.data}, the values is part part of left subtree.
 *
 * To construct the right subtree, set the range as {root.data, MAX}.
 *
 * O(n) time
 *
 * This idea can apply Construct BST By Pre Order Traversal, Just Go array from left to right.
 *
 * =========================================================
 * post order array
 * [1, 6, 7, 6, 8, 5, 46, 45, 55, 55, 50, 40, 10]
 *
 *             10
 *        /       \
 *     5            40
 *   /      \              \
 *  1        8                50
 *         /                /     \
 *        6              45        55
 *       / \              \       /
 *      6   7             46     55
 *
 *
 * it is okay but not efficient to scan the array from the right and
 * just do an insert() into a Binary Search Tree
 * @see <a href="http://www.geeksforgeeks.org/construct-bst-from-given-preorder-traversa/"> Method 2 ( O(n) time complexity ) geeksforgeeks</a>
 */
public class ConstructBSTByPostOrderTraversal {
    static class Node {
        int data;
        Node left, right;

        Node(int data) {
            this.data = data;
            left = right = null;
        }
    }

    public static void printInorder(Node node) {
        if (node == null)
            return;
        printInorder(node.left);
        System.out.print(node.data + " ");
        printInorder(node.right);
    }

    public static void main(String[] args) {
        printInorder(constructTree(new int[]{1, 6, 7, 6, 8, 5, 46, 45, 55, 55, 50, 40, 10}));
    }

    //----------------------------------------------------------------------------------------

    public static Node toTree(int min, int max) {
        Node curNode = null;
        int curv = arr[index];
        if (min < curv && curv <= max) { // same value as left child of the same node
            curNode = new Node(curv);
            index--;
            if (index >= 0) {
                curNode.right = toTree(curv, max);
                curNode.left = toTree(min, curv);
            }
        }
        return curNode;
    }

    private static int index;
    private static int[] arr;

    public static Node constructTree(int[] array) {
        arr = array;
        index = arr.length - 1;
        return toTree(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}
