//  Copyright 2017 The keepTry Open Source Project
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
 * https://en.wikipedia.org/wiki/Skip_list
 * https://www.youtube.com/watch?v=2g9OSRKJuzM
 * https://www.cs.cmu.edu/~ckingsf/bioinfo-lectures/skiplists.pdf
 * https://www.csee.umbc.edu/courses/undergraduate/341/fall01/Lectures/SkipLists/skip_lists/skip_lists.html
 * https://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-046j-introduction-to-algorithms-sma-5503-fall-2005/readings/l12_skiplists.pdf
 *
 *
 * The most frequently used implementation of a binary search tree is a red-black tree.
 * The concurrent problems come in when the tree is modified it often needs to rebalance.
 * The rebalance operation can affect large portions of the tree,
 * which would require a mutex lock on many of the tree nodes.
 * Inserting a node into a skip list is far more localized,
 * only nodes directly linked to the affected node need to be locked.
 *
 * http://stackoverflow.com/questions/256511/skip-list-vs-binary-tree
 * https://en.wikipedia.org/wiki/Transactional_memory
 * https://en.wikipedia.org/wiki/Software_transactional_memory
 * http://ticki.github.io/blog/skip-lists-done-right/
 */

package skip_lists;