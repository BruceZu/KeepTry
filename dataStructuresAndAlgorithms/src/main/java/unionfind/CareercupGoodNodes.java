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

package unionfind;

import java.util.Set;

/**
 * <pre>
 * Google Interview Question for SDE1s
 * Idea:
 *  1. Find independent graphs ( each graph with at least/most one circle)
 *  2. Categorize independent graph into three:
 *      1). graph that doesn't contain node1
 *      2). graph that contain node 1, but doesn't have a node point to it
 *      3). graph that contain node 1, but do have a node point to it
 *  3. so the total number is: 1) + 2)
 *
 *  how to find a independent graph or how to describe a independent graph ?
 *     using a map 'graphs' to keep node -> visited node, any part of the graph circle.
 *     using a new 'visited' set. for each node to check:
 *           each node in 'graphs'.keySet()? pass : put it in 'visited'.
 *           check node's outgoing node, in 'graphs'.keySet()
 *                                       ? pass
 *                                       : in 'visited'
 *                                              ? put(node, outgoing node) to 'graphs'
 *                                              : put in 'visited'
 *  how to distinguish 2) and 3)? check V(tag=1).in ==null
 *
 *             2  3
 *             |  |
 *              ----> 4 -> 5 -> 6
 *                          ^    |
 *                          |___ 7          map(7 -> 5) or  map(5 -> 6) or  map(6 -> 7)
 *
 *                   9
 *                   |
 *             10 -----> 8 -> 1 ->
 *                            ^   |
 *                            |___|         map(1 -> 1)
 *
 * @see <a href="https://www.careercup.com/question?id=5840928073842688">career cup</a>
 */
public class CareercupGoodNodes {
    class V {
        int tag;
        Set<V> ins;
        V out;
    }
}
