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

package tree_legend.binarytree.implementation;

import tree_legend.binarytree.AbstractBinaryTree;
import tree_legend.binarytree.BinaryTree;
import tree_legend.binarytree.BinaryTreeNode;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreeImplement<T extends BinaryTreeNode<T, E>, E> extends AbstractBinaryTree<T, E> {
    private static class BinaryTreeNodeImplement<N extends BinaryTreeNodeImplement<N, E>, E> implements BinaryTreeNode<N, E> {
        private E e;
        private N parent;

        private N left;
        private N right;

        private BinaryTreeNodeImplement(E e) {
            this.e = e;
        }

        private void setParent(N p) {
            this.parent = p;
        }

        private void setLeft(N left) {
            this.left = left;
        }

        private void setRight(N right) {
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
        public N getLeft() {
            return this.left;
        }

        @Override
        public N getRight() {
            return this.right;
        }

        @Override
        public boolean updateElement(E e) {
            this.setElement(e);
            return true;
        }

        @Override
        public N getParent() {
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
        public List<N> getChildren() {
            List<N> cs = new ArrayList<>(2);
            if (left != null) {
                cs.add(left);
            }
            if (right != null) {
                cs.add(right);
            }
            return cs;
        }
    }

    private T root;
    private int size;

    private void valid(T n) {
        if (n.getParent() == n) {
            throw new IllegalArgumentException("This node is no longer in the tree");
        }
    }

    @Override
    public T createRoot(E e) {
        this.root = (T) new BinaryTreeNodeImplement(e);
        size++;
        return root;
    }

    @Override
    public T createLeftFor(T n, E e) {
        valid(n);
        if (n.getLeft() != null) {
            throw new IllegalArgumentException("n already has a left child");
        }
        BinaryTreeNodeImplement left = new BinaryTreeNodeImplement(e);
        ((BinaryTreeNodeImplement) n).setLeft(left);
        left.setParent((BinaryTreeNodeImplement) n);
        size++;
        return (T) left;
    }

    @Override
    public T insertLeftFor(T n, E in) {
        valid(n);
        T left = n.getLeft();
        BinaryTreeNodeImplement newLeft = new BinaryTreeNodeImplement(in);
        ((BinaryTreeNodeImplement) n).setLeft(newLeft);
        newLeft.setParent((BinaryTreeNodeImplement) n);
        if (left != null) {
            newLeft.setLeft((BinaryTreeNodeImplement) left);
            ((BinaryTreeNodeImplement) left).setParent(newLeft);
        }
        size++;
        return (T) newLeft;
    }

    @Override
    public void replaceByRightSubTree(T n) {
        valid(n);
        if (n.getLeft() != null) {
            throw new IllegalArgumentException("The node n should has not left children");
        }
        T right = n.getRight();
        BinaryTreeNodeImplement p = (BinaryTreeNodeImplement) n.getParent();

        if (right != null) {
            ((BinaryTreeNodeImplement) right).setParent(p);
        }
        if (n != root) {
            if (p.getLeft() == n) {
                p.setLeft((BinaryTreeNodeImplement) right);
            } else {
                p.setRight((BinaryTreeNodeImplement) right);
            }
        } else {
            root = right;
        }

        ((BinaryTreeNodeImplement) n).setParent(null);
        ((BinaryTreeNodeImplement) n).setLeft(null);
        size--;
    }

    public void replaceByLeftSubTree(T n) {
        valid(n);
        if (n.getRight() != null) {
            throw new IllegalArgumentException("The node n should has not right children");
        }
        T left = n.getLeft();
        BinaryTreeNodeImplement p = (BinaryTreeNodeImplement) n.getParent();

        if (left != null) {
            ((BinaryTreeNodeImplement) left).setParent(p);
        }
        if (n != root) {
            if (p.getLeft() == n) {
                p.setLeft((BinaryTreeNodeImplement) left);
            } else {
                p.setRight((BinaryTreeNodeImplement) left);
            }
        } else {
            root = left;
        }

        ((BinaryTreeNodeImplement) n).setParent(null);
        ((BinaryTreeNodeImplement) n).setLeft(null);
        size--;
    }

    @Override
    public void cutLeaf(T n) {
        if (n.getLeft() != null || n.getRight() != null) {
            throw new IllegalArgumentException("n is assumed as a leaf");
        }
        T p = n.getParent();
        if (n == root) {
            root = null;
        } else {
            if (p.getLeft() == n) {
                ((BinaryTreeNodeImplement) p).setLeft(null);
            } else {
                ((BinaryTreeNodeImplement) p).setRight(null);
            }
        }
        size--;
    }

    @Override
    public T createRightFor(T n, E o) {
        valid(n);
        if (n.getRight() != null) {
            throw new IllegalArgumentException("n already has a right child");
        }
        BinaryTreeNodeImplement right = new BinaryTreeNodeImplement(o);
        ((BinaryTreeNodeImplement) n).setRight(right);
        right.setParent((BinaryTreeNodeImplement) n);
        size++;
        return (T) right;
    }

    @Override
    public void attachLeftFor(T n, BinaryTree<? extends BinaryTreeNode<T, E>, E> tree) {
        valid(n);
        if (n.getLeft() != null) {
            throw new IllegalArgumentException("n already has a left child");
        }

        BinaryTreeNodeImplement r = (BinaryTreeNodeImplement) tree.root();
        if (r != null) {
            r.setParent((BinaryTreeNodeImplement) n);
            ((BinaryTreeNodeImplement) n).setLeft(r);
            r.setParent((BinaryTreeNodeImplement) n);
            size += tree.size();
            tree.clean();
        }
    }

    @Override
    public void attachRightFor(T n, BinaryTree<? extends BinaryTreeNode<T, E>, E> tree) {
        valid(n);
        if (n.getRight() != null) {
            throw new IllegalArgumentException("n already has a right child");
        }

        BinaryTreeNodeImplement r = (BinaryTreeNodeImplement) tree.root();
        if (r != null) {
            r.setParent((BinaryTreeNodeImplement) n);
            ((BinaryTreeNodeImplement) n).setRight(r);
            r.setParent((BinaryTreeNodeImplement) n);
            size += tree.size();
            tree.clean();
        }
    }

    private void gc(T n) {
        BinaryTreeNodeImplement bn = ((BinaryTreeNodeImplement) n);
        bn.setRight(null);
        bn.setLeft(null);
        bn.setElement(null);
        bn.setParent(bn);
        size--;
    }

    @Override
    public void remove(T n) {
        valid(n);
        if (n.childrenSize() == 2) {
            throw new IllegalArgumentException(" n has two children");
        }

        // re-link
        BinaryTreeNodeImplement p = (BinaryTreeNodeImplement) n.getParent();
        T child = n.getLeft() == null ? n.getRight() : n.getLeft();

        if (child != null) {
            ((BinaryTreeNodeImplement) child).setParent(p); // is null when n is root
        }

        if (n == root) {
            root = child;
        } else {
            if (p.getLeft() == n) {
                p.setLeft((BinaryTreeNodeImplement) child);
            } else {
                p.setRight((BinaryTreeNodeImplement) child);
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
    public T root() {
        return root;
    }

    @Override
    public int size() {
        return size;
    }
}
