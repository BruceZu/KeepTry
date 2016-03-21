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

import org.junit.Before;
import org.junit.Test;
import tree.binarytree.BinaryTree;
import tree.binarytree.BinaryTreeNode;
import tree.binarytree.implementation.ArrayBasedBinaryTreeImplement;
import tree.binarytree.implementation.BinaryTreeImplement;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TreeTest {
    private static class TestTreeNodeImplement<T extends TreeNode<T, E>, E>
            implements TreeNode<T, E> {
        private E e;
        private T p;
        private List<T> children;

        private void setElement(E e) {
            this.e = e;
        }

        private void setParent(T p) {
            this.p = p;
        }

        public TestTreeNodeImplement(E e) {
            this.e = e;
        }

        @Override
        public E getElement() {
            return e;
        }

        @Override
        public T getParent() {
            return p;
        }

        @Override
        public int childrenSize() {
            return children.size();
        }

        @Override
        public Iterable<T> getChildren() {
            return children;
        }


        public void setChildren(List<T> children) {
            this.children = children;
        }
    }

    private TestTreeNodeImplement root;

    @Before
    public void data() {

        root = new TestTreeNodeImplement(1);

        TreeNode n2 = new TestTreeNodeImplement("2");
        TestTreeNodeImplement n3 = new TestTreeNodeImplement(Boolean.TRUE);

        TreeNode n4 = new TestTreeNodeImplement('4');
        TestTreeNodeImplement n5 = new TestTreeNodeImplement(5d);

        TestTreeNodeImplement n6 = new TestTreeNodeImplement(6);
        TreeNode n7 = new TestTreeNodeImplement(7);

        TestTreeNodeImplement n8 = new TestTreeNodeImplement(8);
        TreeNode n9 = new TestTreeNodeImplement(9);

        TreeNode n10 = new TestTreeNodeImplement(10);
        TreeNode n11 = new TestTreeNodeImplement(11);
        //
        root.setChildren(Arrays.asList(n2, n3));
        n3.setChildren(Arrays.asList(n4, n5));
        n5.setChildren(Arrays.asList(n6, n7));
        n6.setChildren(Arrays.asList(n8, n9, n10));
        n8.setChildren(Arrays.asList(n11));

        //               1
        //              /  \
        //             2     3
        //            /    /   \
        //          null  4    5
        //               /   /   \
        //             null  6    7
        //                 / | \   \
        //                8  9 10  null
        //               /   |  \
        //              11 null null
        //             /
        //           null
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void testHeight() {
        Tree t = new AbstractTree() {
            @Override
            public TreeNode root() {
                return root;
            }

            @Override
            public int size() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Iterator iteratorPreOrder() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Iterator iteratorPostOrder() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Iterator iteratorBreadthFirstOrder() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void clean() {
                throw new UnsupportedOperationException();
            }
        };
        int h = t.height();
        assertEquals(h, 5);
        h = t.height(t.root());
        assertEquals(h, 5);
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void testBinaryTree() {
        //                 r
        //              /    \
        //            l        1
        //          /   \     /  \
        //       l2     a    2    3
        //       /     /  \
        //    l3     b     c
        //    / \     \   /
        // l4  r4    c2   b2
        //           /    \
        //          b3    c3

        BinaryTree bt = new BinaryTreeImplement();
        BinaryTreeNode<? extends BinaryTreeNode, String> r = bt.createRoot("r");
        BinaryTreeNode<? extends BinaryTreeNode, String> l = bt.createLeftFor(r, "l");
        BinaryTreeNode<? extends BinaryTreeNode, String> l2 = bt.createLeftFor(l, "l2");
        BinaryTreeNode<? extends BinaryTreeNode, String> l3 = bt.createLeftFor(l2, "l3");
        BinaryTreeNode<? extends BinaryTreeNode, String> l4 = bt.createLeftFor(l3, "l4");
        BinaryTreeNode<? extends BinaryTreeNode, String> r4 = bt.createRightFor(l3, "r4");


        BinaryTreeNode<? extends BinaryTreeNode, String> one = bt.createRightFor(r, "1");
        BinaryTreeNode<? extends BinaryTreeNode, String> two = bt.createLeftFor(one, "2");
        BinaryTreeNode<? extends BinaryTreeNode, String> three = bt.createRightFor(one, "3");

        BinaryTreeNode<? extends BinaryTreeNode, String> a = bt.createRightFor(l, "a");
        BinaryTreeNode<? extends BinaryTreeNode, String> b = bt.createLeftFor(a, "b");
        BinaryTreeNode<? extends BinaryTreeNode, String> c = bt.createRightFor(a, "c");
        BinaryTreeNode<? extends BinaryTreeNode, String> b2 = bt.createLeftFor(c, "b2");
        BinaryTreeNode<? extends BinaryTreeNode, String> c2 = bt.createRightFor(b, "c2");
        BinaryTreeNode<? extends BinaryTreeNode, String> b3 = bt.createLeftFor(c2, "b3");
        BinaryTreeNode<? extends BinaryTreeNode, String> c3 = bt.createRightFor(b2, "c3");

        // test iteratorBreadthFirstOrder(), iteratorInOrder()
        Iterator<? extends TreeNode> ite = bt.iteratorBreadthFirstOrder();
        StringBuilder out = new StringBuilder();
        while (ite.hasNext()) {
            out.append(String.format("%s, ", ite.next().getElement().toString()));
        }
        assertEquals(out.toString(), "r, l, 1, l2, a, 2, 3, l3, b, c, l4, r4, c2, b2, b3, c3, ");

        ite = bt.iteratorInOrder();
        out = new StringBuilder();
        while (ite.hasNext()) {
            out.append(String.format("%s, ", ite.next().getElement().toString()));
        }
        assertEquals(out.toString(), "l4, l3, r4, l2, l, b, b3, c2, a, b2, c3, c, r, 2, 1, 3, ");

        // test height() and remove()
        assertEquals(bt.size(), 16);
        assertEquals(bt.height(), 5);
        assertEquals(bt.height(r), 5);

        bt.remove(l2);
        bt.remove(b3);
        bt.remove(c3);
        assertEquals(bt.height(), 4);
        assertEquals(bt.height(r), 4);

        bt.remove(c2);
        bt.remove(b2);
        bt.remove(b);
        bt.remove(c);
        bt.remove(a);
        bt.remove(l);
        assertEquals(bt.height(), 2);
        assertEquals(bt.height(r), 2);

        bt.remove(r4);
        bt.remove(l4);
        bt.remove(l3);
        assertEquals(bt.height(), 2);
        assertEquals(bt.height(r), 2);
        bt.remove(three);
        bt.remove(two);
        bt.remove(one);
        assertEquals(bt.height(), 0);
        assertEquals(bt.height(r), 0);
        bt.remove(r);
        assertEquals(bt.root(), null);

        //--
        BinaryTree abt = new ArrayBasedBinaryTreeImplement();
        r = abt.createRoot("r");
        l = abt.createLeftFor(r, "l");
        l2 = abt.createLeftFor(l, "l2");
        l3 = abt.createLeftFor(l2, "l3");
        l4 = abt.createLeftFor(l3, "l4");
        r4 = abt.createRightFor(l3, "r4");

        one = abt.createRightFor(r, "1");
        two = abt.createLeftFor(one, "2");
        three = abt.createRightFor(one, "3");

        a = abt.createRightFor(l, "a");
        b = abt.createLeftFor(a, "b");
        c = abt.createRightFor(a, "c");
        b2 = abt.createLeftFor(c, "b2");
        c2 = abt.createRightFor(b, "c2");
        b3 = abt.createLeftFor(c2, "b3");
        c3 = abt.createRightFor(b2, "c3");

        // test iteratorBreadthFirstOrder(), iteratorInOrder()
        ite = abt.iteratorBreadthFirstOrder();
        out = new StringBuilder();
        while (ite.hasNext()) {
            out.append(String.format("%s, ", ite.next().getElement().toString()));
        }
        assertEquals(out.toString(), "r, l, 1, l2, a, 2, 3, l3, b, c, l4, r4, c2, b2, b3, c3, ");

        ite = abt.iteratorInOrder();
        out = new StringBuilder();
        while (ite.hasNext()) {
            out.append(String.format("%s, ", ite.next().getElement().toString()));
        }
        assertEquals(out.toString(), "l4, l3, r4, l2, l, b, b3, c2, a, b2, c3, c, r, 2, 1, 3, ");

        // test height() and remove()
        assertEquals(abt.size(), 16);
        assertEquals(abt.height(), 5);
        assertEquals(abt.height(r), 5);

        abt.remove(l2);
        abt.remove(b3);
        abt.remove(c3);
        assertEquals(abt.height(), 4);
        assertEquals(abt.height(r), 4);

        abt.remove(c2);
        abt.remove(b2);
        abt.remove(b);
        abt.remove(c);
        abt.remove(a);
        abt.remove(l);

        assertEquals(abt.height(), 2);
        assertEquals(abt.height(r), 2);

        abt.remove(r4);
        abt.remove(l4);
        abt.remove(l3);
        assertEquals(abt.height(), 2);
        assertEquals(abt.height(r), 2);
        abt.remove(three);
        abt.remove(two);
        abt.remove(one);
        assertEquals(abt.height(), 0);
        assertEquals(abt.height(r), 0);
        abt.remove(r);
        assertEquals(abt.root(), null);
    }
}
