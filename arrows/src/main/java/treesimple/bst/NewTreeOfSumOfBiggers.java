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

package treesimple.bst;

import java.util.Stack;

// assume the TreeNode has no parent pointer
public class NewTreeOfSumOfBiggers {
    static class IteratorWrapper {
        private Stack<TreeNode> nodesStack; // ascending order top down

        public IteratorWrapper(TreeNode root) {
            nodesStack = new Stack<>();
            TreeNode node = root;
            while (node != null) { // care
                nodesStack.push(node);
                node = node.left;
            }
        }

        // whether we have a next biggest number
        public boolean hasNext() {
            return !nodesStack.isEmpty();
        }

        // the next biggest number
        public TreeNode next() {
            if (!hasNext()) {
                return null; // ???
            }

            TreeNode next = nodesStack.pop();

            TreeNode node = next.right;
            while (node != null) {
                nodesStack.push(node);
                node = node.left;
            }

            return next;
        }
    }

    // get a new tree from a given BST, each node value will be sum of all nodes great than it
    // Assume te TreeNode has not parent pointer
    public static void newTreeOf(TreeNode root) {
        if (root == null) {
            return;
        }
        IteratorWrapper it = new IteratorWrapper(root);
        recursion(it.next(), it);
    }

    // create new BST
    public static void recursion(TreeNode node, IteratorWrapper it) {
        if (it.hasNext()) {
            TreeNode next = it.next();
            int currentNextVal = next.val;
            recursion(next, it);
            node.val = currentNextVal + next.val;
        } else {
            node.val = 0; // the last biggest Node value will be 0
        }
    }
}
