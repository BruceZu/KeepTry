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

import tree.AbstractTree;

import java.util.Iterator;

public abstract class AbstractBinaryTree<T extends BinaryTreeNode<T, E>, E>
        extends AbstractTree<T, E>
        implements BinaryTree<T, E> {

    @Override
    public T sibling(T n) {
        if (n == root()) {
            return null;
        }
        T p = n.getParent();
        T l = p.getLeft();
        return n == l ? p.getRight() : l;
    }

    /**
     * Only son: parent have only one child left or right.<p>
     * Last son: parent have 2 children, this is the right.<p>
     * Means: we all brothers are done.
     *
     * @param i i is node except root
     */
    private boolean isOnlyOrLastChild(T i) {
        T p = i.getParent();
        T r = p.getRight();
        return r == null || i == r;
    }

    /**
     * The inorder traversal visits p after all the positions in the left subtree of
     * p and before all the positions in the right subtree of p.
     * <p/>
     * Algorithm inorder(p):
     * if p has a left child lc then
     * inorder(lc) { recursively traverse the left subtree of p }
     * perform the “visit” action for position p
     * if p has a right child rc then
     * inorder(rc) { recursively traverse the right subtree of p }
     */
    @Override
    public Iterator<T> iteratorInOrder() {
        if (size() == 0) {
            return null;
        }
        // todo: fail fast
        return new Iterator<T>() {
            private T next;

            /**
             * Recursively traverse up to find the first parent not visited yet.
             * its the one whose sub child from which going through is its left child.
             *
             * @param node
             * @return The result may be null.
             */
            private T nextAncestorNoVisited(T node) {
                if (node == root()) {
                    return null;
                }
                T p = node.getParent();
                if (node == p.getLeft()) {
                    return p;
                }
                return nextAncestorNoVisited(p);
            }

            private boolean onlyHaveLeft(T i) {
                return i.childrenSize() == 1 && i.getLeft() != null;
            }

            /**
             * In right subtree recursively traverse down
             * to find the fist left node without left child
             *
             * @param n
             * @return
             */
            private T firstLeftNodeWithoutLeftChildIn(T n /* rightSubTree*/) {
                if (n.getLeft() == null) {
                    return n;
                }
                return firstLeftNodeWithoutLeftChildIn(n.getLeft());
            }

            /**
             * According to the inorder.
             * Start from the left first node (is leaf or node without left)
             * If node is leaf or node only have left child, next step is go up,
             * else node have right child, next step is step down on right.
             * @param node
             * @return if not found return null to end all
             */
            private T next(T node) {
                if (isLeaf(node) || onlyHaveLeft(node)) {
                    return nextAncestorNoVisited(node);
                }

                return firstLeftNodeWithoutLeftChildIn(node.getRight());
            }

            private Iterator<T> init() {
                next = firstLeftNodeWithoutLeftChildIn(root());
                return this;
            }

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public T next() {
                T r = next;
                next = next(next);
                return r;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }.init();
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        if (size() == 0) {
            return null;
        }
        return new Iterator<T>() {
            private T next;

            private Iterator<T> init() {
                next = root();
                return this;
            }

            /**
             * If this is the last children of parent
             * then recursive to find parent's next brother
             * return:
             * @param i
             * @return next brother in order. null when go to root, end all.
             */
            private T nextBrother(T i) {
                if (i == root()) {
                    return null;
                }
                if (isOnlyOrLastChild(i)) {
                    return nextBrother(i.getParent());
                }
                return i.getParent().getRight();
            }

            /**
             * According to preorder.
             * Start from root. then children from in order from left to right.
             *
             * If this is leaf, next step is next brother, if this is the last child of parent,
             * recursively go to parent's next brother till reach root.
             *
             * Else this node have children, go down to children.
             *
             * @param i
             * @return
             */
            private T next(T i) {
                if (isLeaf(i)) {
                    return nextBrother(i);
                }
                // has children
                T l = i.getLeft();
                return l == null ? i.getRight() : l;
            }

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public T next() {
                T r = next;
                next = next(next);
                return r;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }.init();
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        if (size() == 0) {
            return null;
        }
        return new Iterator<T>() {
            private T next;

            private T startLeafOf(T n /*subTree*/) {
                if (n.childrenSize() == 0) {
                    return n;
                }
                T c = n.getLeft();
                return startLeafOf(c == null ? n.getRight() : c);
            }

            private Iterator<T> init() {
                next = startLeafOf(root());
                return this;
            }

            /**
             * According to postorder.
             * Start from leaf.
             * If all brothers are done, next step is parent
             * Else continue next brother.
             * @param i
             * @return
             */
            private T next(T i) {
                if (i == root() || isOnlyOrLastChild(i)) {
                    // up
                    return i.getParent();
                }
                // continue next brother
                return startLeafOf(i.getParent().getRight());
            }

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public T next() {
                T r = next;
                next = next(next);
                return r;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }.init();
    }
}
