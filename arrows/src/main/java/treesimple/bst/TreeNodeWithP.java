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

package treesimple.bst;

/**
 * <pre>
 *
 * The next largest value comes from one of two places:
 * First, if the current node has a right child.
 *              1 move to that right child then,
 *              2 as long as you can see a left child, move to it.
 *
 *              In other words (S and D are the source and destination):
 *
 *                 S2
 *               /               \
 *             1                  7
 *                           /       \
 *                           5       8
 *                       /    \
 *                     D3      6
 *                       \
 *                        4
 *
 *  Otherwise (the current node has no right child).
 *         move up to the parent continuously (so nodes need a right, left and parent pointer)
 *         until the node you moved from was a left child.
 *
 *         if reach root and you still haven't moved up from a left child, your original node was already the highest
 *
 *                1
 *                          \
 *                                   D6
 *                       /             \
 *                    2                  7
 *                          \
 *                           4
 *                         /   \
 *                        3     S5
 */
public class TreeNodeWithP {
    int v;
    TreeNodeWithP parent;
    TreeNodeWithP left;
    TreeNodeWithP right;
}
