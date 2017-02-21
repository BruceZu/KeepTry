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

package tree_legend.binarytree;

import tree_legend.Tree;

import java.util.Iterator;
import java.util.List;

// A binary tree is an ordered tree
// Let T be a nonempty binary tree, and
//
// n: the number of nodes,
// nE: number of external nodes,
// nI: number of internal nodes,
// h:  height of T, respectively.
//
// depth                 level
//
// 0         o           2^0              o               o
//          / \                          / \             / \
// 1       o   o         2^1            o   o           o   o
//        / \ / \                      / \ /           / \
// 2     o  oo   o       2^2          o  oo           o  o
//
// The set of all nodes of a tree T at the same depth d: 'level' d of T
//
//        1. h+1 ≤ n ≤ 2^(h+1)−1
//        2. 1 ≤ nE ≤ 2^h
//        3. h ≤ nI ≤ 2^h−1
//        4. log(n+1)−1 ≤ h ≤ n−1
//
// if T is proper:
//        1. 2h+1 ≤ n ≤ 2^(h+1)−1
//        2. h+1 ≤ nE ≤ 2^h
//        3. h ≤ nI ≤ 2^h−1
//        4. log(n+1)−1 ≤ h ≤ (n−1)/2
//
// a nonempty proper binary tree T:
//
//           nE = nI +1.
//
// Define the internal path length, I(T), of a tree T to be the sum of the depths of
// all the internal positions in T. Likewise, define the external path length, E(T),
// of a tree T to be the sum of the depths of all the external positions in T. Show
// that if T is a proper binary tree with n positions, then
//
//          E(T) = I(T)+n−1.
//
public interface BinaryTree<T extends BinaryTreeNode<T, E>, E> extends Tree<T, E> {
    T createRoot(E e);

    T createLeftFor(T n, E e);

    /**
     * <pre>
     *      n              n
     *     /       =>     /
     *   old left        left(e)
     * @param n         /    \
     * @param e   old left   null
     * @return
     */
    T insertLeftFor(T n, E e);

    /**
     * <pre>
     * the n must has not right child or n is null.
     *  1
     *        p          p
     *       /          /
     *      n      => left
     *     /\
     *  left null
     *
     *  2
     *    p          p
     *     \          \
     *      n      => left
     *     /\
     *  left null
     *
     *  3
     *      p      p
     *     /   =>
     *    n
     *
     *  4
     *    p       p
     *     \  =>
     *      n
     * @param n
     * @return
     */
    void replaceByLeftSubTree(T n);

    void replaceByRightSubTree(T n);

    void cutLeaf(T n);

    T createRightFor(T n, E e);

    void attachLeftFor(T n, BinaryTree<? extends BinaryTreeNode<T, E>, E> tree);

    void attachRightFor(T n, BinaryTree<? extends BinaryTreeNode<T, E>, E> tree);

    T sibling(T n);

    /**
     * “walk” around T, viewing the edges of T as  being “walls” that we always keep to our left.
     * walk is O(n), for a tree with n nodes, because it progresses exactly two times along
     * each of the n−1 edges of the tree—once going downward along the edge, and later going
     * upward along the edge.
     * walk passes to the left then the right of a node, In between the “pre visit” and “post visit”
     * of a given node will be a recursive tour of each of its subtrees.
     */
    // Algorithm eulerTour(T, n):
    //
    //   perform the “pre visit” action for node n
    //
    //   for each child c in T.children(p) do
    //          eulerTour(T, c) { recursively tour the subtree rooted at c }
    //
    //   perform the “post visit” action for node n
    //
    //
    // For the case of a binary tree, we can customize the algorithm to include an explicit “in visit” action:
    // Algorithm eulerTourBinary(T, n):
    //
    //    perform the “pre visit” action for node n
    //
    //    if p has a left child lc then eulerTourBinary(T, lc) { recursively tour the left subtree of n }
    //    perform the “in visit” action for node n
    //    if p has a right child rc then eulerTourBinary(T, rc) { recursively tour the right subtree of n }
    //
    //    perform the “post visit” action for node n

    void eulerTourTraversal(T root, List<T> result, boolean withInVisit);

    void eulerTourTraversalArithmeticExpression(T root, List<String> result);

    /**
     * An important application of the inorder traversal algorithm arises when we store an
     * ordered sequence of elements in a binary tree, defining a structure we call a binary
     * search tree.<p>
     * <p/>
     * Let S be a set whose unique elements have an order relation. For
     * example, S could be a set of integers.<p>
     * <p/>
     * A binary search tree for S is a proper binary tree T such that,
     * for each internal position p of T:
     * <p/>
     * • Position p stores an element of S, denoted as e(p).<p>
     * • Elements stored in the left subtree of p (if any) are less than e(p).<p>
     * • Elements stored in the right subtree of p (if any) are greater than e(p).
     */
    Iterator<T> iteratorInOrder();

    void drawing();

    BinaryTree toBST(List<E> sortedList);

    void remove(T n); // remove the node with only one child
}
