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

import java.util.Stack;

// The TreeNode has no parent pointer
// root node of BST is provided only.
// Descending order or ascending order.
// This is a Descending order case.
class SolutionA {
    private Stack<TreeNode> nodesStack; // Descending order top down

    public SolutionA(TreeNode root) {
        nodesStack = new Stack<>();
        TreeNode node = root;
        while (node != null) { // care
            nodesStack.push(node);
            node = node.right;
        }
    }

    // whether we have a next biggest number
    public boolean hasNext() {
        return !nodesStack.isEmpty();
    }

    //the next biggest number
    public int next() {

        if (!hasNext()) {
            return -1; // ???
        }

        TreeNode node = nodesStack.pop();
        int r = node.val;

        node = node.left;
        while (node != null) {
            nodesStack.push(node);
            node = node.right;
        }
        return r;
    }

    /*---------------------------------------------------------------------------------------------------------*/
    public static void main(String[] args) {
        TreeNode root = TreeNode.testTree();
        SolutionA i = new SolutionA(root);
        while (i.hasNext()) {
            System.out.println(i.next());
        }
    }
}

// The ThreeNode has not parent pointer
// The root Node of BST is provided
// get next Node of a given Node in ascending order
class SolutionB {
    public static TreeNode nextLargestNodeOf(TreeNode node, TreeNode root) {
        // the node with minimum value in right subtree of given node.
        if (node.right != null) {
            TreeNode n = node.right;
            while (n.left != null) {
                n = n.left;
            }
            return n;
        }

        TreeNode successorParentCandidator = null;
        // Assume the give node is sure exists in the given BST
        while (node.val != root.val) {
            if (node.val > root.val) {
                // before change to the left child. keep cur node as parent candidator.
                successorParentCandidator = root;
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return successorParentCandidator;
    }
}

public class SuccessorInBST {
    /**
     * when the TreeNode has parent pointer
     * <pre>
     *
     * Complexity:
     *  The effort is proportional to the height of the tree,
     * 1 balanced tree (such as red-black, 2-3-4 and AVL): O(log N)
     *   since the height has a logN relationship to the number of items.
     * 2 non-balanced trees, worst case:  O(n)
     */
    public static TreeNodeWithP nextLargestNode(TreeNodeWithP given) {
        //First:
        // to check if there is the node with minimum value in right subtree of given node.
        if (given.right != null) {
            TreeNodeWithP n = given.right;
            while (n.left != null) {
                n = n.left;
            }
            return n;
        }
        // At last:
        // if there exists a parent node whose left child is the place from where we reach out in recursively
        // as the code.
        // Note: when recursion upside need check if going out of root.
        while (given.parent != null) {
            if (given == given.parent.left) {
                return given.parent;
            }
            given = given.parent;
        }
        return null;
    }
}
