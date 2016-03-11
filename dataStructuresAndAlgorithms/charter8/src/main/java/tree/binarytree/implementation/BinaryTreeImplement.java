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

import tree.TreeNode;
import tree.binarytree.AbstractBinaryTree;
import tree.binarytree.BinaryTree;
import tree.binarytree.BinaryTreeNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BinaryTreeImplement extends AbstractBinaryTree {
    private class BinaryTreeNodeImplement<T extends BinaryTreeNode<T, E>, E> implements BinaryTreeNode<T, E> {
        private E e;
        private T parent;

        private T left;
        private T right;

        private BinaryTreeNodeImplement(E e) {
            this.e = e;
        }

        private void setParent(T p) {
            this.parent = p;
        }

        private void setLeft(T left) {
            this.left = left;
        }

        private void setRight(T right) {
            this.right = right;
        }

        private void setElement(E e) {
            this.e = e;
        }

        @Override
        public E getElement() {
            return e;
        }

        @Override
        public T getLeft() {
            return this.left;
        }

        @Override
        public T getRight() {
            return this.right;
        }

        @Override
        public T getParent() {
            return parent;
        }

        @Override
        public int childrenSize() {
            int size = 0;
            if (left != null) {
                size++;
            }
            if (right != null) {
                size++;
            }
            return size;
        }

        @Override
        public Iterable<T> getChildren() {
            List<T> cs = new ArrayList<>(2);
            if (left != null) {
                cs.add(left);
            }
            if (right != null) {
                cs.add(right);
            }
            return cs;
        }
    }

    // ----

    private BinaryTreeNode root;
    private int size;

    private void valid(BinaryTreeNode n) {
        if (n.getParent() == n) {
            throw new IllegalArgumentException("This node is no longer in the tree");
        }
    }

    @Override
    public BinaryTreeNode createRoot(Object e) {
        this.root = new BinaryTreeNodeImplement(e);
        size++;
        return root;
    }

    @Override
    public BinaryTreeNode createLeftFor(BinaryTreeNode n, Object o) {
        valid(n);
        if (n.getLeft() != null) {
            throw new IllegalArgumentException("n already has a left child");
        }
        BinaryTreeNode left = new BinaryTreeNodeImplement(o);
        ((BinaryTreeNodeImplement) n).setLeft(left);
        ((BinaryTreeNodeImplement) left).setParent(n);
        size++;
        return left;
    }

    @Override
    public BinaryTreeNode createRightFor(BinaryTreeNode n, Object o) {
        valid(n);
        if (n.getRight() != null) {
            throw new IllegalArgumentException("n already has a right child");
        }
        BinaryTreeNode right = new BinaryTreeNodeImplement(o);
        ((BinaryTreeNodeImplement) n).setLeft(right);
        ((BinaryTreeNodeImplement) right).setParent(n);
        size++;
        return right;
    }

    @Override
    public void attachLeftFor(BinaryTreeNode n, BinaryTree tree) {
        valid(n);
        if (n.getLeft() != null) {
            throw new IllegalArgumentException("n already has a left child");
        }

        BinaryTreeNodeImplement r = (BinaryTreeNodeImplement) tree.root();
        if (r != null) {
            r.setParent(n);
            ((BinaryTreeNodeImplement) n).setLeft(r);
            ((BinaryTreeNodeImplement) r).setParent(n);
            size += tree.size();
            tree.clean();
        }
    }

    @Override
    public void attachRightFor(BinaryTreeNode n, BinaryTree tree) {
        valid(n);
        if (n.getRight() != null) {
            throw new IllegalArgumentException("n already has a right child");
        }

        BinaryTreeNodeImplement r = (BinaryTreeNodeImplement) tree.root();
        if (r != null) {
            r.setParent(n);
            ((BinaryTreeNodeImplement) n).setRight(r);
            ((BinaryTreeNodeImplement) r).setParent(n);
            size += tree.size();
            tree.clean();
        }
    }

    private void gc(BinaryTreeNode n) {
        BinaryTreeNodeImplement bn = ((BinaryTreeNodeImplement) n);
        bn.setRight(null);
        bn.setLeft(null);
        bn.setElement(null);
        bn.setParent(n);
        size--;
    }

    @Override
    public void remove(BinaryTreeNode n) {
        valid(n);
        if ( n.childrenSize() == 2) {
            throw new IllegalArgumentException(" n has two children");
        }

        // re-link
        BinaryTreeNodeImplement p = (BinaryTreeNodeImplement) n.getParent();
        BinaryTreeNode child = n.getLeft() == null ? n.getRight() : n.getLeft();

        if (child != null) {
            ((BinaryTreeNodeImplement) child).setParent(p);
        }

        if (n == root) {
            root = child;
        } else {
            if (p.getLeft() == n) {
                p.setLeft(child);
            } else {
                p.setRight(child);
            }
        }

        gc(n);
        return;
    }

    @Override
    public void clean() {
        root = null;
        size = 0;
    }

    @Override
    public TreeNode root() {
        return root;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable Nodes() {
        throw new UnsupportedOperationException();
    }
}
