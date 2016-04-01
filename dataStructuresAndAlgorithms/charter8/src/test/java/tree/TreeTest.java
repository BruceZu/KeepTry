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

import java.util.ArrayList;
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
        public List<T> getChildren() {
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

    @Test(timeout = 60l, expected = Test.None.class)
    public void testBinaryTree() {
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
        BinaryTreeNode<? extends BinaryTreeNode, String> four = bt.createLeftFor(three, "4");
        BinaryTreeNode<? extends BinaryTreeNode, String> five = bt.createRightFor(three, "5");

        BinaryTreeNode<? extends BinaryTreeNode, String> a = bt.createRightFor(l, "a");
        BinaryTreeNode<? extends BinaryTreeNode, String> b = bt.createLeftFor(a, "b");
        BinaryTreeNode<? extends BinaryTreeNode, String> c = bt.createRightFor(a, "c");
        BinaryTreeNode<? extends BinaryTreeNode, String> b2 = bt.createLeftFor(c, "b2");
        BinaryTreeNode<? extends BinaryTreeNode, String> c2 = bt.createRightFor(b, "c2");
        BinaryTreeNode<? extends BinaryTreeNode, String> b3 = bt.createLeftFor(c2, "b3");
        BinaryTreeNode<? extends BinaryTreeNode, String> c3 = bt.createRightFor(b2, "c3");
        excuteBinaryTree(bt);
        excuteBinaryTreeHeightAndRemove(bt, r, l, l2, l3, l4, r4, one, two, three, four, five, a, b, c, b2, c2, b3, c3);

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
        four = abt.createLeftFor(three, "4");
        five = abt.createRightFor(three, "5");

        a = abt.createRightFor(l, "a");
        b = abt.createLeftFor(a, "b");
        c = abt.createRightFor(a, "c");
        b2 = abt.createLeftFor(c, "b2");
        c2 = abt.createRightFor(b, "c2");
        b3 = abt.createLeftFor(c2, "b3");
        c3 = abt.createRightFor(b2, "c3");
        // abt.drawing();
        excuteBinaryTree(abt);
        excuteBinaryTreeHeightAndRemove(abt, r, l, l2, l3, l4, r4, one, two, three, four, five, a, b, c, b2, c2, b3, c3);

        BinaryTree bt2 = new BinaryTreeImplement();
        BinaryTreeNode<? extends BinaryTreeNode, String> root = bt2.createRoot("-");

        BinaryTreeNode<? extends BinaryTreeNode, String> l1 = bt2.createLeftFor(root, "/");
        BinaryTreeNode<? extends BinaryTreeNode, String> r1 = bt2.createRightFor(root, "+");

        BinaryTreeNode<? extends BinaryTreeNode, String> dl2 = bt2.createLeftFor(l1, "x");
        BinaryTreeNode<? extends BinaryTreeNode, String> r2 = bt2.createRightFor(l1, "+");

        BinaryTreeNode<? extends BinaryTreeNode, String> dl3 = bt2.createLeftFor(r1, "x");
        BinaryTreeNode<? extends BinaryTreeNode, String> r3 = bt2.createRightFor(r1, "6");

        BinaryTreeNode<? extends BinaryTreeNode, String> dl4 = bt2.createLeftFor(dl2, "+");
        BinaryTreeNode<? extends BinaryTreeNode, String> dr4 = bt2.createRightFor(dl2, "3");

        BinaryTreeNode<? extends BinaryTreeNode, String> l5 = bt2.createLeftFor(r2, "-");
        BinaryTreeNode<? extends BinaryTreeNode, String> r5 = bt2.createRightFor(r2, "2");

        BinaryTreeNode<? extends BinaryTreeNode, String> l6 = bt2.createLeftFor(dl3, "3");
        BinaryTreeNode<? extends BinaryTreeNode, String> r6 = bt2.createRightFor(dl3, "-");

        BinaryTreeNode<? extends BinaryTreeNode, String> l7 = bt2.createLeftFor(dl4, "3");
        BinaryTreeNode<? extends BinaryTreeNode, String> r7 = bt2.createRightFor(dl4, "1");

        BinaryTreeNode<? extends BinaryTreeNode, String> l8 = bt2.createLeftFor(l5, "9");
        BinaryTreeNode<? extends BinaryTreeNode, String> r8 = bt2.createRightFor(l5, "5");

        BinaryTreeNode<? extends BinaryTreeNode, String> l9 = bt2.createLeftFor(r6, "7");
        BinaryTreeNode<? extends BinaryTreeNode, String> r9 = bt2.createRightFor(r6, "4");
        excuteBinaryTreeArithmeticExpression(bt2);

        BinaryTree abt2 = new ArrayBasedBinaryTreeImplement();
        root = abt2.createRoot("-");

        l1 = abt2.createLeftFor(root, "/");
        r1 = abt2.createRightFor(root, "+");

        dl2 = abt2.createLeftFor(l1, "x");
        r2 = abt2.createRightFor(l1, "+");

        dl3 = abt2.createLeftFor(r1, "x");
        r3 = abt2.createRightFor(r1, "6");

        dl4 = abt2.createLeftFor(dl2, "+");
        dr4 = abt2.createRightFor(dl2, "3");

        l5 = abt2.createLeftFor(r2, "-");
        r5 = abt2.createRightFor(r2, "2");

        l6 = abt2.createLeftFor(dl3, "3");
        r6 = abt2.createRightFor(dl3, "-");

        l7 = abt2.createLeftFor(dl4, "3");
        r7 = abt2.createRightFor(dl4, "1");

        l8 = abt2.createLeftFor(l5, "9");
        r8 = abt2.createRightFor(l5, "5");

        l9 = abt2.createLeftFor(r6, "7");
        r9 = abt2.createRightFor(r6, "4");
        // abt2.drawing();
        excuteBinaryTreeArithmeticExpression(abt2);
    }

    private void excuteBinaryTreeArithmeticExpression(BinaryTree bt) {
        List<String> result = new ArrayList<>(bt.size() * 2);
        bt.eulerTourTraversalArithmeticExpression((BinaryTreeNode) bt.root(), result);

        StringBuilder re = new StringBuilder();
        for (String v : result) {
            re.append(v);
        }
        assertEquals(re.toString(), "((((3+1)x3)/((9-5)+2))-((3x(7-4))+6))");
    }

    private void excuteBinaryTree(BinaryTree bt) {
        // test iteratorBreadthFirstOrder(), iteratorInOrder(), iteratorPreOrder(), iteratorPostOrder()
        Iterator<? extends TreeNode> ite = bt.iteratorBreadthFirstOrder();
        StringBuilder out = new StringBuilder();
        while (ite.hasNext()) {
            out.append(String.format("%s, ", ite.next().getElement().toString()));
        }
        assertEquals(out.toString(), "r, l, 1, l2, a, 2, 3, l3, b, c, 4, 5, l4, r4, c2, b2, b3, c3, ");

        ite = bt.iteratorInOrder();
        out = new StringBuilder();
        while (ite.hasNext()) {
            out.append(String.format("%s, ", ite.next().getElement().toString()));
        }
        assertEquals(out.toString(), "l4, l3, r4, l2, l, b, b3, c2, a, b2, c3, c, r, 2, 1, 4, 3, 5, ");

        ite = bt.iteratorPreOrder();
        out = new StringBuilder();
        while (ite.hasNext()) {
            out.append(String.format("%s, ", ite.next().getElement().toString()));
        }
        assertEquals(out.toString(), "r, l, l2, l3, l4, r4, a, b, c2, b3, c, b2, c3, 1, 2, 3, 4, 5, ");

        // test parentheticStringRepresentation(), eulerTourTraversal()
        StringBuilder re = new StringBuilder();
        bt.parentheticStringRepresentation(bt.root(), re);
        assertEquals(re.toString(), "r (l (l2 (l3 (l4, r4)), a (b (c2 (b3)), c (b2 (c3)))), 1 (2, 3 (4, 5)))");

        List<BinaryTreeNode<? extends BinaryTreeNode, String>> result = new ArrayList<>(bt.size());
        bt.eulerTourTraversal((BinaryTreeNode) bt.root(), result, true);
        re = new StringBuilder();
        for (BinaryTreeNode n : result) {
            re.append(String.format("%s, ", n.getElement()));
        }
        assertEquals(re.toString(), "r, l, l2, l3, l4, l3, r4, l3, l2, l, a, b, c2, b3, c2, b, a, c, b2, c3, b2, c, a, l, r, 1, 2, 1, 3, 4, 3, 5, 3, 1, r, ");

        result = new ArrayList<>(bt.size());
        bt.eulerTourTraversal((BinaryTreeNode) bt.root(), result, false);
        re = new StringBuilder();
        for (BinaryTreeNode n : result) {
            re.append(String.format("%s, ", n.getElement()));
        }
        assertEquals(re.toString(), "r, l, l2, l3, l4, r4, l3, l2, a, b, c2, b3, c2, b, c, b2, c3, b2, c, a, l, 1, 2, 3, 4, 5, 3, 1, r, ");

        //                 r
        //              /    \
        //            l        1
        //          /   \     /  \
        //       l2     a    2    3
        //       /     /  \      / \
        //    l3     b     c    4   5
        //    / \     \   /
        // l4  r4    c2   b2
        //           /    \
        //          b3    c3
        //

        ite = bt.iteratorPostOrder();
        out = new StringBuilder();
        while (ite.hasNext()) {
            out.append(String.format("%s, ", ite.next().getElement().toString()));
        }
        assertEquals(out.toString(), "l4, r4, l3, l2, b3, c2, b, c3, b2, c, a, l, 2, 4, 5, 3, 1, r, ");
    }

    private void excuteBinaryTreeHeightAndRemove(BinaryTree bt,
                                                 BinaryTreeNode<? extends BinaryTreeNode, String> r,
                                                 BinaryTreeNode<? extends BinaryTreeNode, String> l,
                                                 BinaryTreeNode<? extends BinaryTreeNode, String> l2,
                                                 BinaryTreeNode<? extends BinaryTreeNode, String> l3,
                                                 BinaryTreeNode<? extends BinaryTreeNode, String> l4,
                                                 BinaryTreeNode<? extends BinaryTreeNode, String> r4,
                                                 BinaryTreeNode<? extends BinaryTreeNode, String> one,
                                                 BinaryTreeNode<? extends BinaryTreeNode, String> two,
                                                 BinaryTreeNode<? extends BinaryTreeNode, String> three,
                                                 BinaryTreeNode<? extends BinaryTreeNode, String> four,
                                                 BinaryTreeNode<? extends BinaryTreeNode, String> five,
                                                 BinaryTreeNode<? extends BinaryTreeNode, String> a,
                                                 BinaryTreeNode<? extends BinaryTreeNode, String> b,
                                                 BinaryTreeNode<? extends BinaryTreeNode, String> c,
                                                 BinaryTreeNode<? extends BinaryTreeNode, String> b2,
                                                 BinaryTreeNode<? extends BinaryTreeNode, String> c2,
                                                 BinaryTreeNode<? extends BinaryTreeNode, String> b3,
                                                 BinaryTreeNode<? extends BinaryTreeNode, String> c3) {
        assertEquals(bt.size(), 18);
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
        assertEquals(bt.height(), 3);
        assertEquals(bt.height(r), 3);

        bt.remove(r4);
        bt.remove(l4);
        bt.remove(l3);
        assertEquals(bt.height(), 3);
        assertEquals(bt.height(r), 3);
        bt.remove(four);
        bt.remove(five);
        bt.remove(three);
        bt.remove(two);
        bt.remove(one);
        assertEquals(bt.height(), 0);
        assertEquals(bt.height(r), 0);
        bt.remove(r);
        assertEquals(bt.root(), null);
    }
}
