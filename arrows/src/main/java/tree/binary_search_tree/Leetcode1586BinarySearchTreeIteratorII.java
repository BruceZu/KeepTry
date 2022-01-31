//  Copyright 2022 The KeepTry Open Source Project
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

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Leetcode1586BinarySearchTreeIteratorII {
  /*
    Leetcode 1586. Binary Search Tree Iterator II

      Implement the BSTIterator class that represents an iterator over the in-order traversal
      of a binary search tree (BST):

      BSTIterator(TreeNode root) Initializes an object of the BSTIterator class.
      The root of the BST is given as part of the constructor.
      The pointer should be initialized to a non-existent number smaller than any element in the BST.

      - boolean hasNext() Returns true if there exists a number in the traversal to the right of the pointer,
        otherwise returns false.

      - int next() Moves the pointer to the right, then returns the number at the pointer.

      - boolean hasPrev() Returns true if there exists a number in the traversal to the left of the pointer,
        otherwise returns false.

      - int prev() Moves the pointer to the left, then returns the number at the pointer.

      Notice that by initializing the pointer to a non-existent smallest number,
      the first call to next() will return the smallest element in the BST.

      You may assume that next() and prev() calls will always be valid.
      That is, there will be at least a next/previous number in the in-order traversal
      when next()/prev() is called.


        Input
        ["BSTIterator", "next", "next", "prev", "next", "hasNext", "next", "next", "next", "hasNext", "hasPrev", "prev", "prev"]
        [[[7, 3, 15, null, null, 9, 20]], [null], [null], [null], [null], [null], [null], [null], [null], [null], [null], [null], [null]]
        Output
        [null, 3, 7, 3, 7, true, 9, 15, 20, false, true, 15, 9]

        Explanation
        // The underlined element is where the pointer currently is.
        BSTIterator bSTIterator = new BSTIterator([7, 3, 15, null, null, 9, 20]); // state is   [3, 7, 9, 15, 20]
        bSTIterator.next(); // state becomes [3, 7, 9, 15, 20], return 3
        bSTIterator.next(); // state becomes [3, 7, 9, 15, 20], return 7
        bSTIterator.prev(); // state becomes [3, 7, 9, 15, 20], return 3
        bSTIterator.next(); // state becomes [3, 7, 9, 15, 20], return 7
        bSTIterator.hasNext(); // return true
        bSTIterator.next(); // state becomes [3, 7, 9, 15, 20], return 9
        bSTIterator.next(); // state becomes [3, 7, 9, 15, 20], return 15
        bSTIterator.next(); // state becomes [3, 7, 9, 15, 20], return 20
        bSTIterator.hasNext(); // return false
        bSTIterator.hasPrev(); // return true
        bSTIterator.prev(); // state becomes [3, 7, 9, 15, 20], return 15
        bSTIterator.prev(); // state becomes [3, 7, 9, 15, 20], return 9


        Constraints:

        The number of nodes in the tree is in the range [1, 105].
        0 <= Node.val <= 106
        At most 105 calls will be made to hasNext, next, hasPrev, and prev.


        Follow up: Could you solve the problem without precalculating the values of the tree?

  */
  /*
   Idea:
     in BST, for a given node
     - it has next
       -1- if it has right sub-tree: the next one is the left most node of the right sub-tree
       -2- else if its parent exists and it is its parent's left node:
           the next is its parent node
           note: when a node has parent node, and it is parent' right node. parent will not be in stack
           see https://imgur.com/a/geSjfeq
       These 2 scenarios are actually 2 perspectives of the same thing, if take a give BST tree is also
       a right subtree of a virtual node.
     similar logic apply `it has pre`, while use a list can make the runtime to be O(1).
       all pre comes from all next() operation;
   Note:
     1. this is not one direct once time visit process. the cursor can step forward and step back forward.
     2 `initializing the pointer to a non-existent smallest number...` +
       ` 0 <= Node.val <= 106`
       So can start the cur node with a virtual node whos right subtree nood is the give root
       thus it is at the -1 index of pre array list, and as the left most Node

    3 `next() and prev() calls will always be valid...`
     So need not check hasNext() and hasPre() before call next() and prev();

   Code variable:
     parents:
             current node's parents in stack. stack keeps all parents not returned in next() so all are
             right side parents. https://imgur.com/a/geSjfeq
     pres:
              pre nodes in BST in visited order;
              once current node is located in next() then it is put into the pre list before returned as result of next();
              So cur node is identified only by index i
     rr:      cur node's right sub-tree root
      i:      cur node 0-based index in the pres array list, sometimes there are many back forward
              steps. current node is initialized as a virtual node whose right sub-tree root is the given root

  Time complexity
   O(1) for the constructor().  hasPrev(). prev().  hasNext().
   O(N) for next(). In the worst-case of the skewed tree one has to parse the entire tree, all N nodes.
       it's the worst-case time complexity. We only make such a call for the nodes which we've not yet parsed.
       We save all parsed nodes in a list, and then re-use them if we need to return next from the already
       parsed area of the tree.
       Thus, the amortized (average) time complexity for the next call would still be O(1).

  Space complexity: O(N).
  */
  static class BSTIterator {
    Stack<TreeNode> parents;
    List<Integer> pres;
    TreeNode rr;
    int i;

    public BSTIterator(TreeNode root) {
      rr = root;
      parents = new Stack();
      pres = new ArrayList();
      i = -1;
    }

    public boolean hasNext() {
      return rr != null || !parents.isEmpty() || i < pres.size() - 1;
      // the third condition:  after visiting all nodes then step back forward a few nodes
    }

    // Assume: next exists
    public int next() {
      i++;
      if (i == pres.size()) { // not visited
        while (rr != null) {
          parents.push(rr);
          rr = rr.left;
        }
        // last is null now, or cur node has not right sub-tree then its parent will be the new cur
        // node
        TreeNode lm = parents.pop();
        rr = lm.right;

        pres.add(lm.val);
      }

      return pres.get(i);
    }

    public boolean hasPrev() {
      return i > 0;
    }

    // Assume: prev exists
    public int prev() {
      i--;
      return pres.get(i);
    }
  }
}
