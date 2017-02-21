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
 * a red-black tree is a binary search tree with nodes
 * colored red and black in a way that satisfies the following properties:
 *
 *      Root Property:     The root is black.
 *      External Property: Every external node is black.
 *      Red Property:      The children of a red node are black. implies that on any path from the root to a leaf,
 *                         red nodes must not be adjacent.However, any number of black nodes may appear in a sequence.
 *      Depth Property:    All external nodes have the same black depth, defined as the
 *                         number of proper ancestors that are black.
 *
 *
 *  @see <a href="https://www.cs.auckland.ac.nz/software/AlgAnim/red_black.html">red-black tree</a>
 *  @see <a href="https://www.cs.auckland.ac.nz/software/AlgAnim/red_black.html">wiki</a>
 */
package tree.binary_search_tree.self_balancing_tree.redblacktree;