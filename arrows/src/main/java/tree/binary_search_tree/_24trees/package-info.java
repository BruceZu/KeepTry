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

/**
 * <pre>
 * One form of a multiway search tree that keeps the tree balanced while using small
 * secondary data structures at each node is the (2,4) tree, also known as a 2-4 tree
 * or 2-3-4 tree. This data structure achieves these goals by maintaining two simple
 * properties (see Figure 11.23):
 * Size Property: Every internal node has at most four children.
 * Depth Property: All the external nodes have the same depth.
 *
 *  Two nodes that are children of the same parent are siblings.
 *  A node v is external if v has no children.
 *  A node v is internal if it has one or more children.
 *  External nodes are also known as leaves.
 */
package tree.binary_search_tree._24trees;