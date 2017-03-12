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

import java.util.HashMap;
import java.util.Map;

public class Leetcode230KthSmallestElementInaBST4 {

    // keep node's left subtree nodes number in hashmap
    static public int kthSmallest(TreeNode node, int k) {
        Map<TreeNode, Integer> leftNodesNumOf = new HashMap<>();
        init(node, leftNodesNumOf);

        int lnum;
        // o(H)
        while (true) {
            lnum = leftNodesNumOf.get(node);
            if (k <= lnum) {
                node = node.left;
            } else if (k == lnum + 1) {
                return node.val;
            } else {
                k -= lnum + 1;
                node = node.right;
            }
        }
    }

    // O(n)
    static private int init(TreeNode root, Map leftNodesNumOf) {
        if (root == null) {
            return 0;
        }
        int lnum = init(root.left, leftNodesNumOf);
        leftNodesNumOf.put(root, lnum);
        return lnum + 1 + init(root.right, leftNodesNumOf);
    }

    public static void main(String[] args) {
        TreeNode r = new TreeNode(4);
        r.left = new TreeNode(2);
        r.right = new TreeNode(5);

        r.left.left = new TreeNode(1);
        r.left.right = new TreeNode(3);

        r.left.left.left = new TreeNode(0);

        r.right.right = new TreeNode(6);
        r.right.right.right = new TreeNode(9);
        r.right.right.right.left = new TreeNode(8);
        r.right.right.right.left.left = new TreeNode(7);
        int v = kthSmallest(r, 8);
        System.out.println(v);
    }
}
