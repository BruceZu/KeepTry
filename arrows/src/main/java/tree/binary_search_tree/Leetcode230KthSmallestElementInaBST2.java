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

public class Leetcode230KthSmallestElementInaBST2 {
    // O(n)  if it is balancing BST
    static public int kthSmallest(TreeNode root, int kth) {
        int[] leftTarget_Value= new int[]{kth,-1};
        EnumerateInOrderBST(root,leftTarget_Value);
        return leftTarget_Value[1];
    }

    static public void EnumerateInOrderBST(TreeNode n, int leftTarget_Value[]) {
        if (n.left != null) {
            EnumerateInOrderBST(n.left,leftTarget_Value);
        }
        // if (leftTarget_Value[0] != 0) { // performance
        leftTarget_Value[0]--;
        if (leftTarget_Value[0] == 0) {
            leftTarget_Value[1] = n.val;
            return;
        }
        if (n.right != null) { // count != 0
            EnumerateInOrderBST(n.right,leftTarget_Value);
        }
        // }
    }
}
