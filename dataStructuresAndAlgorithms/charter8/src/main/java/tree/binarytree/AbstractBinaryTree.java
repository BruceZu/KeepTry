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
        T p = n.getParent();
        if (p == null) {
            return null;
        }
        if (n == p.getLeft()) {
            return p.getRight();
        }
        return p.getLeft();
    }

    @Override
    public Iterator<T> iteratorInOrder() {
        // todo: fail fast
        return new Iterator<T>() {
            private T next;

            /**
             * In right subtree recursively traverse down
             * to find the fist left node without left child
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
             * If node is leaf or node only have left child, according to the inorder, next step is go up,
             * else node have right child, according to the inorder, next step is step down on right.
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
}
