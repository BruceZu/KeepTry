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

import java.util.Stack;

/*
Leetcode 173. Binary Search Tree Iterator

Implement the BSTIterator class that represents an iterator over the in-order traversal of a binary search tree (BST):

BSTIterator(TreeNode root) Initializes an object of the BSTIterator class.
The root of the BST is given as part of the constructor.
The pointer should be initialized to a non-existent number smaller than any element in the BST.

boolean hasNext() Returns true if there exists a number in the traversal to the right of the pointer,
                 otherwise returns false.

int next() Moves the pointer to the right, then returns the number at the pointer.

Notice that by initializing the pointer to a non-existent smallest number,
the first call to next() will return the smallest element in the BST.

You may assume that next() calls will always be valid.
That is, there will be at least a next number in the in-order traversal when next() is called.



Input
["BSTIterator", "next", "next", "hasNext", "next", "hasNext", "next", "hasNext", "next", "hasNext"]
[[[7, 3, 15, null, null, 9, 20]], [], [], [], [], [], [], [], [], []]
Output
[null, 3, 7, true, 9, true, 15, true, 20, false]

Explanation
BSTIterator bSTIterator = new BSTIterator([7, 3, 15, null, null, 9, 20]);
bSTIterator.next();    // return 3
bSTIterator.next();    // return 7
bSTIterator.hasNext(); // return True
bSTIterator.next();    // return 9
bSTIterator.hasNext(); // return True
bSTIterator.next();    // return 15
bSTIterator.hasNext(); // return True
bSTIterator.next();    // return 20
bSTIterator.hasNext(); // return False


Constraints:

The number of nodes in the tree is in the range [1, 105].
0 <= Node.val <= 106
At most 105 calls will be made to hasNext, and next.


Follow up:

 Could you implement next() and hasNext()
 to run in average O(1) time and use O(h) memory,
 where h is the height of the tree?
 */
public class Leetcode173BinarySearchTreeIterator {
  /** Definition for binary tree */
  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }

  /*  -------------------------------------------------------------------------
   Watch:
     Only one direction operation, never step back forward.

  space complexity is O(H)
  average time O(1)  for BSTIterator(), hasNext(), next();
  */
  class BSTIterator {
    // keek the peak of stack as the next()
    // top -> down is in ascending order, keep only right side parents.
    private Stack<TreeNode> s = new Stack();

    // O(N) in the worse case
    public BSTIterator(TreeNode n) {
      while (n != null) {
        s.push(n);
        n = n.left;
      }
    }

    public boolean hasNext() {
      return !s.empty();
    }

    // the amortized (average) time complexity: O(1). O(H) in the worst case.
    public int next() {
      if (!hasNext()) return -1;

      TreeNode n = s.pop(); // care: not peek
      int v = n.val;

      if (n.right != null) {
        n = n.right;
        while (n != null) {
          s.push(n);
          n = n.left;
        }
      }
      return v;
    }
  }
}
