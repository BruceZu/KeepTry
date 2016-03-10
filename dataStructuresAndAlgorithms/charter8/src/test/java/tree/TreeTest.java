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
import tree.binarytree.BinaryTreeNode;
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
            public Iterator iterator() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Iterable<TreeNode> Nodes() {
                throw new UnsupportedOperationException();
            }
        };
        int h = t.height();
        assertEquals(h, 5);
        h = t.height(t.root());
        assertEquals(h, 5);

        BinaryTreeImplement bt = new BinaryTreeImplement();
        BinaryTreeNode r = bt.createRoot("root");
        BinaryTreeNode l = bt.createLeftFor(r, "left");
        BinaryTreeNode l2 = bt.createLeftFor(l, "left");
        BinaryTreeNode l3 = bt.createLeftFor(l2, "left");
        assertEquals(bt.size(), 4);
        assertEquals(bt.height(), 3);
        assertEquals(bt.height(r), 3);
    }
}
