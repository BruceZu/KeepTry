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

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

class TestTreeNode<T> implements TreeNode<T> {
    T e;
    TreeNode<T> p;
    private List<TreeNode<T>> children;

    public TestTreeNode(T e) {
        this.e = e;
    }

    @Override
    public void setElement(T e) {
        this.e = e;
    }

    @Override
    public void setParent(TreeNode p) {
        this.p = p;
    }

    @Override
    public TreeNode<T> getParent() {
        return p;
    }

    @Override
    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }

    @Override
    public Iterable<TreeNode<T>> getChildren() {
        return children;
    }

    @Override
    public int childrenSize() {
        return children.size();
    }
}

public class TreeTest {
    private TreeNode root;

    @Before
    public void data() {

        root = new TestTreeNode(1);

        TreeNode n2 = new TestTreeNode(2);
        TreeNode n3 = new TestTreeNode(3);

        TreeNode n4 = new TestTreeNode(4);
        TreeNode n5 = new TestTreeNode(5);

        TreeNode n6 = new TestTreeNode(6);
        TreeNode n7 = new TestTreeNode(7);

        TreeNode n8 = new TestTreeNode(8);
        TreeNode n9 = new TestTreeNode(9);

        TreeNode n10 = new TestTreeNode(10);
        TreeNode n11 = new TestTreeNode(11);
        //
        root.setChildren(Arrays.asList(new TreeNode[]{n2, n3}));
        n3.setChildren(Arrays.asList(new TreeNode[]{n4, n5}));
        n5.setChildren(Arrays.asList(new TreeNode[]{n6, n7}));
        n6.setChildren(Arrays.asList(new TreeNode[]{n8, n9, n10}));
        n8.setChildren(Arrays.asList(new TreeNode[]{n11}));

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
            public TreeNode<Integer> root() {
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
    }
}
