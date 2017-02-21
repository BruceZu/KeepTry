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

package tree_legend.binarytree.bst;


import tree_legend.TreeNode;
import tree_legend.binarytree.BinaryTree;
import tree_legend.binarytree.BinaryTreeNode;
import tree_legend.binarytree.implementation.BinaryTreeImplement;

import java.util.Iterator;
import java.util.List;

public class BinarySearchTreeImplement<T extends BinaryTreeNode<T, E>, E extends Comparable<E>> implements BinarySearchTree {
    private BinaryTree<T, E> tree;

    public BinarySearchTreeImplement() {
        tree = new BinaryTreeImplement<>();
    }

    public BinarySearchTreeImplement(boolean arrayBased) {
        if (arrayBased) {
            throw new UnsupportedOperationException();
        } else {
            tree = new BinaryTreeImplement<>();
        }
    }

    @Override
    public BinaryTreeNode createRoot(Object e) {
        return tree.createRoot((E) e);
    }

    @Override
    public BinaryTreeNode createLeftFor(BinaryTreeNode n, Object o) {
        return tree.createLeftFor((T) n, (E) o);
    }

    @Override
    public BinaryTreeNode insertLeftFor(BinaryTreeNode n, Object o) {
        return tree.insertLeftFor((T) n, (E) o);
    }

    @Override
    public void replaceByLeftSubTree(BinaryTreeNode n) {
        tree.replaceByLeftSubTree((T) n);
    }

    @Override
    public void replaceByRightSubTree(BinaryTreeNode n) {
        tree.replaceByRightSubTree((T) n);
    }

    @Override
    public void cutLeaf(BinaryTreeNode n) {
        tree.cutLeaf((T) n);
    }

    @Override
    public BinaryTreeNode createRightFor(BinaryTreeNode n, Object o) {
        return tree.createRightFor((T) n, (E) o);
    }

    @Override
    public BinaryTreeNode sibling(BinaryTreeNode n) {
        return tree.sibling((T) n);
    }

    @Override
    public void eulerTourTraversal(BinaryTreeNode root, List result, boolean withInVisit) {
        tree.eulerTourTraversal((T) root, result, withInVisit);
    }

    @Override
    public Iterator iteratorInOrder() {
        return tree.iteratorInOrder();
    }

    @Override
    public void drawing() {
        tree.drawing();
    }

    @Override
    public BinaryTree toBST(List sortedList) {
        return tree.toBST(sortedList);
    }

    @Override
    public void remove(BinaryTreeNode n) {
        tree.remove((T) n);
    }

    @Override
    public void eulerTourTraversalArithmeticExpression(BinaryTreeNode root, List result) {
        tree.eulerTourTraversalArithmeticExpression((T) root, result);
    }

    @Override
    public void attachRightFor(BinaryTreeNode n, BinaryTree tree) {
        this.tree.attachRightFor((T) n, tree);
    }

    @Override
    public void attachLeftFor(BinaryTreeNode n, BinaryTree tree) {
        this.tree.attachLeftFor((T) n, tree);
    }

    @Override
    public boolean isLeaf(TreeNode n) {
        return tree.isLeaf((T) n);
    }

    @Override
    public int leaves() {
        return tree.leaves();
    }

    @Override
    public int depth(TreeNode n) {
        return tree.depth((T) n);
    }

    @Override
    public int height() {
        return tree.height();
    }

    @Override
    public int height(TreeNode root) {
        return tree.height((T) root);
    }

    @Override
    public TreeNode root() {
        return tree.root();
    }

    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public Iterator iteratorPreOrder() {
        return tree.iteratorPreOrder();
    }

    @Override
    public void printPreOrder(TreeNode root, int ind, String ln) {
        tree.printPreOrder((T) root, ind, ln);
    }

    @Override
    public void parentheticStringRepresentation(TreeNode root, StringBuilder result) {
        tree.parentheticStringRepresentation((T) root, result);
    }

    @Override
    public Iterator iteratorPostOrder() {
        return tree.iteratorPostOrder();
    }

    @Override
    public Iterator iteratorBreadthFirstOrder() {
        return tree.iteratorBreadthFirstOrder();
    }

    @Override
    public void clean() {
        tree.clean();
    }

    /**
     * <pre>
     *     Idea:
     *     Assume node is not null;
     *     Find the right place of e in pre-order and add it there.
     *     1> If the added one does not exist, sure will be a new leaf.
     *     2> If it exist already and not is a leaf. Make a rule. e.g.
     *            left children <= parent;  right children > parent.
     *            then insert
     *
     *                        p(it)
     *                         /
     *                        it
     *                      /   \
     *                old left    null
     *                   /\
     *
     *             if, then again add it, then again
     *
     *                          p(it)
     *                           /
     *                          it
     *                         /  \
     *                       it    null
     *                      /   \
     *                old left    null
     *                   /\
     *
     *      Note: must return after each branch is done, stop continue recursion on other sub tree.
     */
    private void add(T n, E e) {
        if (e.compareTo(n.getElement()) < 0) {
            T left = n.getLeft();
            if (left == null) {
                tree.createLeftFor(n, e);
                return;
            }
            add(left, e);
            return;
        }
        if (e.compareTo(n.getElement()) == 0) {
            tree.insertLeftFor(n, e);
            return;
        }
        T r = n.getRight();
        if (r == null) {
            tree.createRightFor(n, e);
            return;
        }
        add(r, e);
    }

    @Override
    public BinaryTreeNode add(Comparable e) {
        if (tree.root() == null) {
            tree.createRoot((E) e);
            return tree.root();
        }

        T r = tree.root();
        add(r, (E) e);
        return r;
    }

    /**
     * <pre>
     *  Idea:
     *     Assume node is not null;
     *
     *     Find e in pre-order:
     *         e is not leaf, replace it by
     *              if it has left sub-tree:
     *                   using the left sub-tree's rightest node
     *              else
     *                   use the right sub-tree's leftest node.
     *         e is a leaf
     *              cut it directly
     *     Do not found it.
     *       return;
     *
     * @param n
     * @param e
     */
    private void delete(T n, E e) {
        if (n.getElement().compareTo(e) > 0) {
            if (n.getLeft() != null) {
                delete(n.getLeft(), e);
            } else {
                return;
            }
        } else if (n.getElement().compareTo(e) < 0) {
            if (n.getRight() != null) {
                delete(n.getRight(), e);
            } else {
                return;
            }
        } else {
            T toReplace = n;
            if (n.getLeft() != null) {
                T cur = n.getLeft();
                while (cur.getRight() != null) {
                    cur = cur.getRight();
                }
                tree.replaceByLeftSubTree(cur);
                toReplace.updateElement(cur.getElement());
            } else if (n.getRight() != null) {
                T cur = n.getRight();
                while (cur.getLeft() != null) {
                    cur = cur.getLeft();
                }
                tree.replaceByRightSubTree(cur);
                toReplace.updateElement(cur.getElement());
            } else {
                tree.cutLeaf(n);
            }
        }
    }

    @Override
    public BinaryTreeNode delete(Comparable e) {
        if (tree.root() == null) {
            return null;
        }

        delete(tree.root(), (E) e);
        return tree.root(); // Note: get the latest root.
    }
}
