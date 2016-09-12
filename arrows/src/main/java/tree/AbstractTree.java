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

package tree;

import java.util.Iterator;
import java.util.List;

import static java.lang.Math.max;

public abstract class AbstractTree<T extends TreeNode<T, E>, E> implements Tree<T, E> {

    /**
     * @param n
     * @param dd default depth or the depth for leaf children of n
     * @return max depth of n's children
     */
    private int childrenMaxDepth(T n, int dd) {

        int maxChildDepth = 0;
        for (T c : n.<E>getChildren()) {
            if (!isLeaf(c)) {
                maxChildDepth = max(maxChildDepth, childrenMaxDepth(c, dd + 1));
            }
            // skip leaf as it is not the child with max depth
        }

        return maxChildDepth == 0 ? dd : maxChildDepth;
    }

    @Override
    public boolean isLeaf(T n) {
        return n.getChildren() == null || n.childrenSize() == 0;
    }

    // Tree' height :
    //  - if the tree is empty or a leaf: 0
    //  - the maximum of the depth of its nodes
    // O(N)
    @Override
    public int height() {
        T r = root();
        if (r == null || isLeaf(r)) {
            return 0;
        }
        return childrenMaxDepth(r, 1);
    }

    // Tree' height :
    //   - if root is empty or a leaf: 0
    //   - else: 1 + max(the height of subtrees).
    // O(N)
    @Override
    public int height(T n) {
        if (n == null || isLeaf(n)) {
            return 0;
        }
        int subTreeHeight = 0;
        for (T c : n.<E>getChildren()) {
            if (!isLeaf(c)) {
                subTreeHeight = max(subTreeHeight, height(c));
            }
            // skip leaf as it is not the child with max depth
        }
        return 1 + subTreeHeight;
    }

    // The depth of a node: the number of ancestors of node, other than node itself.
    @Override
    public int depth(T n) {
        if (n.getParent() == null) {
            return 0;
        }
        return 1 + depth(n.getParent());
    }

    /**
     * Traverse a tree so that we visit all the positions at depth d
     * before we visit the positions at depth d+1. Such an algorithm
     * is known as a breadth-first traversal.
     * <p/>
     * application in game: a computer may be unable to explore a
     * complete game tree in a limited amount of time.
     * <p/>
     * Algorithm breadthfirst() with a queue:
     * Initialize queue Q to contain root( )
     * while Q not empty do
     * p = Q.dequeue( ) { p is the oldest entry in the queue }
     * perform the “visit” action for position p
     * for each child c in children(p) do
     * Q.enqueue(c) { add p’s children to the end of the queue for later visits }
     * <p/>
     * running time is O(n)
     */
    @Override
    public Iterator<T> iteratorBreadthFirstOrder() {
        return new Iterator<T>() {
            // todo: fail fast
            private Object[] children;
            private int cursor;
            private int insertIndex;

            // or using a queue without cast class type. Easy and heavy.
            // private Queue<T> cs;

            private Iterator<T> init() {
                int size = size();

                children = new Object[size];
                children[0] = root();
                cursor = -1;
                insertIndex = 1;

                // cs = new LinkedList();
                // cs.offer(root());
                return this;
            }

            @Override
            public boolean hasNext() {
                return size() != 0
                        && cursor != size() - 1; // check by size
                // && cs.peek() != null;
            }

            @Override
            public T next() {
                T r = (T) children[++cursor];
                // cs.poll();
                Iterator<T> ite = r.getChildren().iterator();
                while (ite.hasNext()) {
                    children[insertIndex++] = ite.next();
                    // cs.offer(ite.next());
                }
                return r;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }.init();
    }

    /**
     * Algorithm preorder(p):
     * perform the “visit” action for position p { this happens before any recursion }
     * for each child c in children(p) do
     * preorder(c) { recursively traverse the subtree rooted at c }
     */
    @Override
    public Iterator<T> iteratorPreOrder() {
        // take care when the children are not unique
        throw new UnsupportedOperationException();
    }

    /**
     * Algorithm postorder(p):
     * for each child c in children(p) do
     * postorder(c) { recursively traverse the subtree rooted at c }
     * perform the “visit” action for position p { this happens after any recursion }
     * <p/>
     * Application: calculate some thing.
     */
    @Override
    public Iterator<T> iteratorPostOrder() {
        // take care when the children are not unique
        throw new UnsupportedOperationException();
    }

    @Override
    public void printPreOrder(T root, int ind, String ln) {
        for (int i = 0; i < ind; i++) {
            System.out.print(" ");
        }
        if (ln == "0") {
            ln = " ";
        }
        System.out.println(String.format("%s %s", ln, root.getElement().toString()));
        int i = 1;
        for (T c : root.getChildren()) {
            printPreOrder(c, ind + 1, (ln == " " ? ln + " " : ln + ".") + i++);
        }
    }

    /**
     * Assume parent have more than one children
     * and check if n is not the last one.
     */
    private boolean isNotLastChild(T n) {
        T p = n.getParent();
        return n != root()
                && p.childrenSize() != 1
                && p.getChildren().get(p.childrenSize() - 1) != n;
    }

    //@Override
    public void parentheticStringRepresentation2(T n, StringBuilder r) {
        r.append(n.getElement());
        if (this.isLeaf(n) && isNotLastChild(n)) {
            r.append(", ");
        }
        if (!this.isLeaf(n)) {
            r.append(" ");
        }

        if (n.childrenSize() != 0) {
            r.append("(");
            for (T c : n.getChildren()) {
                parentheticStringRepresentation2(c, r);
            }
            r.append(")");

            if (isNotLastChild(n)) {
                r.append(", ");
            }
        }
    }

    // O(N)
    @Override
    public void parentheticStringRepresentation(T n, StringBuilder r) {
        r.append(n.getElement());
        if (!isLeaf(n)) {
            boolean firstTime = true;
            for (T c : n.getChildren()) {
                r.append((firstTime ? " (" : ", "));
                firstTime = false;
                parentheticStringRepresentation(c, r);
            }
            r.append(")");
        }
    }

    private void leaves(T n, int[] r /* result */) {
        if (n.childrenSize() == 0) {
            r[0]++;
            return;
        }
        List<T> cs = n.getChildren();
        for (T c : cs) {
            leaves(c, r);
        }
    }

    @Override
    public int leaves() {
        T root = root();
        if (root == null) {
            return 0;
        }
        if (isLeaf(root)) {
            return 1;
        }
        int[] r = new int[1];
        leaves(root, r);
        return r[0];
    }
}
