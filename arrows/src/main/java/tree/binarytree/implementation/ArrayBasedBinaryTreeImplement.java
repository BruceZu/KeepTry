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

package tree.binarytree.implementation;

import tree.binarytree.AbstractBinaryTree;
import tree.binarytree.BinaryTree;
import tree.binarytree.BinaryTreeNode;

import java.util.ArrayList;
import java.util.List;

// Its space is fine for 'heaps'
// its fine for read operation
public class ArrayBasedBinaryTreeImplement<T extends BinaryTreeNode<T, E>, E>
        extends AbstractBinaryTree<T, E> {

    private static class NodeImplement<N extends NodeImplement<N, E>, E>
            implements BinaryTreeNode<N, E> {
        private E e;
        N[] array;
        int index;

        private NodeImplement(E e, N[] a, int i) {
            this.e = e;
            array = a;
            index = i;
        }

        private void setElement(E e) {
            this.e = e;
        }

        @Override
        public E getElement() {
            return e;
        }

        /**
         * <pre>
         * For every node n of T, let f (n) be the integer defined as follows.
         * • If n is the root of T, then f (n) = 0.
         * • If n is the  left child of n q, then f (n) = 2 f (q)+1.
         * • If n is the right child of n q, then f (n) = 2 f (q)+2.
         *
         *   f (p) ≤ 2^n−2.
         *
         * The numbering function f is known as a level numbering of the n in a
         * binary tree T, for it numbers the n on each level of T in increasing order
         * from left to right.
         */

        @Override
        public N getLeft() {
            return array[2 * index + 1];
        }

        @Override
        public N getRight() {
            return array[2 * index + 2];
        }

        @Override
        public boolean updateElement(E e) {
            this.setElement(e);
            return true;
        }

        @Override
        public N getParent() {
            if (index != 0) {
                return array[(index - 1) / 2];
            }
            return null;
        }

        @Override
        public int childrenSize() {
            int size = 0;
            if (getLeft() != null) {
                size++;
            }
            if (getRight() != null) {
                size++;
            }
            return size;
        }

        @Override
        public List<N> getChildren() {
            List<N> cs = new ArrayList<>(2);
            N l = getLeft();
            if (l != null) {
                cs.add(l);
            }
            N r = getRight();
            if (r != null) {
                cs.add(r);
            }
            return cs;
        }
    }

    // Todo: increase automatically
    // for a given tree with n nodes, f (p) ≤ 2^n−2, and the size of array can be 2^n-2
    private NodeImplement[] array = new NodeImplement[100];
    private int size;

    private void valid(BinaryTreeNode n) {
        if (n.getParent() == n) {
            throw new IllegalArgumentException("This node is no longer in the tree");
        }
    }

    /**
     * Factory method pattern, allowing us to later subclass our tree in order to use
     * a specialized node type
     */
    @Override
    public NodeImplement createRoot(Object e) {
        NodeImplement r = new NodeImplement(e, array, 0);
        array[0] = r;
        size++;
        return r;
    }

    @Override
    public T createLeftFor(T n, E e) {
        valid(n);
        if (n.getLeft() != null) {
            throw new IllegalArgumentException("n already has a left child");
        }
        int index = 2 * ((NodeImplement) n).index + 1;
        NodeImplement left = new NodeImplement(e, array, index);
        array[index] = left;
        size++;
        return (T) left;
    }

    @Override
    public T insertLeftFor(T n, E e) {
        // TODO: 7/3/16
        throw new UnsupportedOperationException();
    }

    @Override
    public void replaceByRightSubTree(T n) {
        // TODO: 7/3/16
        throw new UnsupportedOperationException();
    }

    public void replaceByLeftSubTree(T n) {
        // TODO: 7/3/16
        throw new UnsupportedOperationException();
    }

    @Override
    public void cutLeaf(T n) {
        // TODO: 7/3/16
        throw new UnsupportedOperationException();
    }

    @Override
    public T createRightFor(T n, E e) {
        valid(n);
        if (n.getRight() != null) {
            throw new IllegalArgumentException("n already has a right child");
        }
        int index = 2 * ((NodeImplement) n).index + 2;
        NodeImplement right = new NodeImplement(e, array, index);
        array[index] = right;
        size++;
        return (T) right;
    }

    private void moveToAnotherTree(T node,
                                   int i /* new index*/,
                                   T[] a /* array in new Tree */) {
        if (node != null) {
            moveToAnotherTree(node.getLeft(), 2 * i + 1, a);
            moveToAnotherTree(node.getRight(), 2 * i + 2, a);

            NodeImplement bn = ((NodeImplement) node);
            bn.array[bn.index] = null; // old tree

            // update itself, 'postorder traversal'
            bn.index = i;
            bn.array = (NodeImplement[]) a;

            a[i] = node; // new tree
        }
    }

    @Override
    public void attachLeftFor(T n, BinaryTree<? extends BinaryTreeNode<T, E>, E> tree) {

        valid(n);
        if (n.getLeft() != null) {
            throw new IllegalArgumentException("n already has a left child");
        }

        T r = (T) tree.root();
        moveToAnotherTree(r, 2 * ((NodeImplement) n).index + 1, (T[]) array);

        size += tree.size();
        tree.clean();
    }

    @Override
    public void attachRightFor(T n, BinaryTree<? extends BinaryTreeNode<T, E>, E> tree) {

        valid(n);
        if (n.getRight() != null) {
            throw new IllegalArgumentException("n already has a right child");
        }

        T r = (T) tree.root();
        moveToAnotherTree(r, 2 * ((NodeImplement) n).index + 2, (T[]) array);

        size += tree.size();
        tree.clean();
    }

    private void innerMove(T node, int i /* new index*/) {
        if (node != null) {
            innerMove(node.getLeft(), 2 * i + 1);
            innerMove(node.getRight(), 2 * i + 2);

            NodeImplement bn = ((NodeImplement) node);
            if (array[bn.index] == bn) {
                array[bn.index] = null; // old
            }

            // update itself
            bn.index = i;

            array[i] = (NodeImplement) node; // new
        }
    }

    @Override
    public void remove(T n) {
        valid(n);
        if (n.childrenSize() == 2) {
            throw new IllegalArgumentException("n has two children");
        }

        NodeImplement bn = (NodeImplement) n;
        T child = n.getLeft() == null ? n.getRight() : n.getLeft();
        if (child != null) {
            innerMove(child, bn.index);
        } else {
            array[bn.index] = null; // old
        }

        bn.setElement(null);
        size--;
        return;
    }

    @Override
    public void clean() {
        array = null;
        size = 0;
    }

    @Override
    public T root() {
        return (T) array[0];
    }

    @Override
    public int size() {
        return size;
    }
}
