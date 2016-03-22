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

// Tree : a set of nodes having a parent-child relationship:
//  - If Tree is nonempty, it has a special node, called the root, that has no parent.
//  - Each node of Tree different from the root has a unique parent node;
//
// Define a tree recursively:
// Tree is either empty or
// consists of a node root, and a (possibly empty) set of subtrees.
//
// breadth-first traversal:
//    visit all the positions at depth d before we visit the positions at depth d+1.
//
public interface Tree<T extends TreeNode<T, E>, E> {
    boolean isLeaf(T n);

    int depth(T n);

    int height();

    int height(T root);

    T root();

    int size();

    Iterator<T> iteratorPreOrder();

    void printPreOrder(T root, int ind /* Start indentation */, String ln /* Label start number */);

    Iterator<T> iteratorPostOrder();

    Iterator<T> iteratorBreadthFirstOrder();

    void clean();

    // http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/collect/TreeTraverser.html
    //
    //          h
    //        / | \
    //       /  e  \
    //      d       g
    //     /|\      |
    //    / | \     f
    //   a  b  c
    //
    //  can be iterated over in
    //   preorder (hdabcegf),
    //   postorder (abcdefgh), or
    //   breadth-first order (hdegabcf).
}
