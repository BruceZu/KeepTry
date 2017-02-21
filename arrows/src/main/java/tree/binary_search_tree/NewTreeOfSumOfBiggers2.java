//  Copyright 2017 The keepTry Open Source Project
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

// assume the TreeNode has parent pointer
public class NewTreeOfSumOfBiggers2 {
    // get a new tree from a given BST, each node value will be sum of all nodes great than it
    public static void newTreeOf(TreeNodeWithP root) {
        if (root == null) {
            return;
        }

        // care find the smallest one firstly
        TreeNodeWithP smallerst = root;
        while (smallerst.left != null) {
            smallerst = smallerst.left;
        }

        recursionFromSmallerst(smallerst);
    }

    // create new BST. precess from the smallest one
    public static void recursionFromSmallerst(TreeNodeWithP fromNode) {
        TreeNodeWithP next = nextNode(fromNode);
        if (next != null) {
            int currentNextVal = next.v;
            recursionFromSmallerst(next);
            fromNode.v = currentNextVal + next.v;
        } else {
            fromNode.v = 0; // the last biggest Node value will be 0
        }
    }

    //First:
    // to check if there is the node with minimum value in right subtree of given node.
    // At last:
    // if there exists a parent node whose left child is the place from where we reach out in recursively
    // as the code.
    // Note: when recursionFromSmallerst upside need check if going out of root.
    public static TreeNodeWithP nextNode(TreeNodeWithP node) {

        if (node.right != null) {
            TreeNodeWithP n = node.right;
            while (n.left != null) {
                n = n.left;
            }
            return n;
        }
        while (node.parent != null) {
            if (node == node.parent.left) {
                return node.parent;
            }
            node = node.parent;
        }
        return null;
    }
    //-----------------------------------------------------------------------------------
    public static void main(String[] args) {
        TreeNodeWithP root = TreeNodeWithP.testTreeNodeWithP();

        TreeNodeWithP smallerest = root;
        while (smallerest.left != null) {
            smallerest = smallerest.left;
        }
        //before
        System.out.println(smallerest.v);
        TreeNodeWithP next =nextNode(smallerest);
        while(next!=null){
            System.out.println(next.v);
            next =nextNode(next);
        }

        // update
        newTreeOf(root);

        // after
        System.out.println(smallerest.v);
        next =nextNode(smallerest);
        while(next!=null){
            System.out.println(next.v);
            next =nextNode(next);
        }
    }
}
