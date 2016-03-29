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

package tree.binarytree;

import tree.Tree;

import java.util.Iterator;

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
//           nE = nI +1.
//
public interface BinaryTree<T extends BinaryTreeNode<T, E>, E> extends Tree<T, E> {
    T createRoot(E e);

    T createLeftFor(T n, E e);

    T createRightFor(T n, E e);

    void attachLeftFor(T n, BinaryTree<? extends BinaryTreeNode<T, E>, E> tree);

    void attachRightFor(T n, BinaryTree<? extends BinaryTreeNode<T, E>, E> tree);

    T sibling(T n);

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

    void remove(T n); // remove the node with only one child
}
