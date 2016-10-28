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

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }

    public static TreeNode testTree() {
        /**
         *  <pre>
         *                                  17
         *                     /
         *               11
         *            /       \
         *          9         13
         *        /   \      /       \
         *       8    10   12        15
         *                         /    \
         *                        14    16
         *
         * ------8  9 10 11 12 13 14 15 16 17---------------
         *
         */
        TreeNode root = new TreeNode(17);
        TreeNode n11 = new TreeNode(11);
        TreeNode n9 = new TreeNode(9);
        TreeNode n13 = new TreeNode(13);
        TreeNode n8 = new TreeNode(8);
        TreeNode n10 = new TreeNode(10);
        TreeNode n12 = new TreeNode(12);
        TreeNode n15 = new TreeNode(15);
        TreeNode n14 = new TreeNode(14);
        TreeNode n16 = new TreeNode(16);
        root.left = n11;
        n11.left = n9;
        n11.right = n13;
        n9.left = n8;
        n9.right = n10;
        n13.left = n12;
        n13.right = n15;
        n15.left = n14;
        n15.right = n16;
        return root;
    }
}
