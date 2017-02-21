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

package tree_legend.binarytree;

import tree_legend.TreeNode;

/**
 * A binary tree is proper if each node has either
 * zero or two children.<p>
 * Some people also refer to such trees as being full binary trees.<p>
 * Thus, in a proper binary tree, every internal node has exactly two children.<p>
 * A binary tree that is not proper is improper.
 */
public interface BinaryTreeNode<T extends BinaryTreeNode<T, E>, E> extends TreeNode<T, E> {
    T getLeft();

    T getRight();
}
