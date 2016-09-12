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

package nosubmmitted;

import java.util.Stack;

/**
 * 255. Verify Preorder Sequence in Binary Search Tree<pre>
 * https://leetcode.com/problems/verify-preorder-sequence-in-binary-search-tree/
 * Difficulty: Medium
 * Given an array of numbers, verify whether it is the correct preorder
 * traversal sequence of a binary search tree.
 * <p/>
 * You may assume each number in the sequence is unique.
 * <p/>
 * Follow up:
 * Could you do it using only constant space complexity?
 * <p/>
 * Hide Company Tags Zenefits
 * Hide Tags: Tree, Stack
 * Hide Similar Problems (M) Binary Tree Preorder Traversal
 * </pre>
 */
public class LC255VerifyPreorderSequenceinBinarySearchTree {
    /**
     * beat 84.89 the fast one currently
     *
     * @param pre
     * @return
     */
    public boolean verifyPreorder(int[] pre) {
        int i, j, n = pre.length, minBd = Integer.MIN_VALUE;
        for (i = 1, j = 0; i < n && pre[i] > minBd; pre[++j] = pre[i++])
            for (; j >= 0 && pre[j] < pre[i]; minBd = pre[j--]) ;
        return i >= n;
    }

    /**
     * Anther O(1) space solution without abusing the given array:
     * beat 18% <pre>
     * <p/>
     * Each time when you encounter a preorder[i] > preorder[i - 1],
     * you know you are going to right branch, and you need to update
     * the min boundary for that right sub tree. The min boundary is
     * the local parent of that right sub tree, the value is indeed
     * equals to the largest preorder element among [0 ~ i - 1]
     * while smaller than preorder[i].
     */

    public boolean verifyPreorder4(int[] preorder) {
        int min = Integer.MIN_VALUE, n = preorder.length, j, i, k;
        for (i = 1; i < n && preorder[i] > min; i++)
            if (preorder[i - 1] < preorder[i]) {
                for (k = i - 1, j = i - 2; j >= 0; j--)
                    if (preorder[j] < preorder[i] && preorder[j] > preorder[k]) k = j;
                min = preorder[k];
            }
        return i >= n;
    }


    // reference

    /**
     * beat 62.21% <pre>
     * Kinda simulate the traversal, keeping a stack of nodes (just their values)
     * of which we're still in the left subtree. If the next number is smaller
     * than the last stack value, then we're still in the left subtree of all
     * stack nodes, so just push the new one onto the stack. But before that,
     * pop all smaller ancestor values, as we must now be in their right
     * subtrees (or even further, in the right subtree of an ancestor).
     * Also, use the popped values as a lower bound, since being in their
     * right subtree means we must never come across a smaller number anymore.
     */
    public boolean verifyPreorder2(int[] preorder) {
        int low = Integer.MIN_VALUE;
        Stack<Integer> path = new Stack();
        for (int p : preorder) {
            if (p < low)
                return false;
            while (!path.empty() && p > path.peek())
                low = path.pop();
            path.push(p);
        }
        return true;
    }

    /**
     * beat 84.89%  <pre>
     * Solution 2 ... O(1) extra space
     * <p/>
     * Same as above, but abusing the given array for the stack.
     */
    public boolean verifyPreorder3(int[] preorder) {
        int low = Integer.MIN_VALUE, i = -1;
        for (int p : preorder) {
            if (p < low)
                return false;
            while (i >= 0 && p > preorder[i])
                low = preorder[i--];
            preorder[++i] = p;
        }
        return true;
    }

    /**
     * ideas , can works but code is slow <pre>
     * Recursively examine every key in the array. For each BST node,
     * its key must be greater than all keys in left subtree and less than keys in right subtree.
     * <p/>
     * Since given preorder sequence, the first element is always the root.
     * Partition the array by the key of root, find the index of the first number greater than it.
     * <p/>
     * Base case:
     * <p/>
     * start index exceeds end index, the array to be checked is empty, return true;
     * root key is not within upper and lower boundaries, return false.å
     *
     * other ideas of c++
     * The idea is traversing the preorder list and using a stack to store all predecessors.
     * currp is a predecessor of current node and current node is in the right subtree of currp.
     * For example, for the following bst with preorder 6,3,1,2,5,4,7:
     *      6
     *    /  \
     *   3    7
     *  / \
     * 1  5
     * \  /
     * 2 4
     We push to stack before we see 2. So at 2 the stack is 6,3,1. For 2,
     we pop stack until we see 3 which is greater than 2 and currp is 1. 2 is in left subtree of 3 and is
     right child of 1. Stack is 6,3,2 now. Then we see 5, and we pop stack until 6 and currp is 3.
     Stack now is 6,5. Then we see 4 and push to stack. At 7, we pop stack until empty and curr_p is 6.
     */
}
