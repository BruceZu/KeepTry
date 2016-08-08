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

package treesimple.binarytree.bst;

import java.util.Stack;

/**
 * <pre>
 * BST Serialize and Deserialize
 * To construct a BST you need only one (not in-order) traversal.  Can't create a tree with only the in order
 * traversal because it is in fact a sorted array and can not decide where is the root.
 *  if  arr[i] < curNode;
 *      it is curNode's left
 *  else
 *      arr[i] is a new right node according to BST, need find the right [lower boarder node, high boarder node]
 *      where lower boarder node < it < high boarder node, then as the right child of lower boarder node.
 *      according to pre order, and take the root's parent null as MAX_VALUE
 *      as to [lower boarder node, high boarder node], according to BST
 *      if lower boarder node is a left node, then its high boarder node is its parent node,
 *      if lower boarder node is a right node,then its high boarder node is:
 *                  while (cur!=root && cur is cur.parent.right){
 *                      cur = cur.parent
 *                  }
 *                  if(cur == root) then high boarder node MAX_VALUE
 *                  else high boarder node is cur.parent.
 *      cases:
 *     [7, 4, 2, 1, 3, 5, 6, 22, 16, 11, 18, 17, 19, 19, 27, 26, 29, 29]
 *                         |           |       |    |       |
 *
 *                               7
 *                      /                          \
 *                     4                            22
 *                  /    \              /                  \
 *                 2      5            16                  27
 *                /  \     \         /     \              /   \
 *               1   3     6        11     18            26    29
 *                                       /   \                 /
 *                                      17    19              29
 *                                            /
 *                                           19
 *
 *        -----  1 2 3 4 5 6    7  11 16 17 18 19  22   26 27 29   -------
 *                                             19             29
 *
 *                                                         right node
 *
 *          when curNode is 4,  a left node, arr[i] may  4<5<7, 7<22< MAX_VALUE. that are all.
 *                                                       |   |  |       |
 *          when curNode is 17, a left node, arr[i] may  17<17.5<18, 18<19<22, 22<27< MAX_VALUE. that are all.
 *                                                       |        |   |     |   |      |
 *          when curNode is 27, a right node, arr[i] may be   27<29< MAX_VALUE. that are all.
 *                                                            |           |
 *
 *   O(n) time
 *
 *   If we insert the elements of preorder traversal of a BST one by one, it will generate the BST correctly.
 *   But the problem is that it takes O(Nlog N) time, in the worst case height will be N, and takes O(N^2)
 *
 * @see <a href="http://www.geeksforgeeks.org/construct-bst-from-given-preorder-traversal-set-2/"> geeksforgeeks</a>
 */
public class ConstructBSTByPreOrderTraversal {
    static class Node {
        int v;
        Node left;
        Node right;

        public Node(int v) {
            this.v = v;
        }
    }

    public static void printInorder(Node node) {
        if (node == null) {
            return;
        }
        printInorder(node.left);
        System.out.print(node.v + " ");
        printInorder(node.right);
    }

    public static void main(String[] args) {
        printInorder(toBST(new int[]{7, 4, 2, 1, 3, 5, 6, 22, 16, 11, 18, 17, 19, 19, 27, 26, 29, 29}));
    }
    //----------------------------------------------------------------------------------------

    public static Node toBST(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        if (arr.length == 1) {
            return new Node(arr[0]);
        }

        Stack<Node> descendings = new Stack();
        Node root = new Node(arr[0]);
        descendings.push(root);

        Node lastTop = null;
        for (int i = 1; i < arr.length; i++) {
            int cur = arr[i];
            if (cur <= descendings.peek().v) { // same one as left of the first
                Node l = new Node(cur);
                descendings.peek().left = l;
                descendings.push(l);
                continue;
            }

            while (!descendings.empty() && descendings.peek().v < cur) {
                lastTop = descendings.pop();
            }
            lastTop.right = new Node(cur);
            descendings.push(lastTop.right);
        }
        return root;
    }
}
