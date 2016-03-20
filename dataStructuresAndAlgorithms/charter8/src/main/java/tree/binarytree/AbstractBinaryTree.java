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
        throw new UnsupportedOperationException();
    }
}
