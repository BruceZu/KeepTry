//  Copyright 2019 The KeepTry Open Source Project
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

package graph;

import graph.IGraph.Status;
import java.util.Collection;

/**
 * <pre>
 * Prim's Minimum Spanning Tree Algorithm.
 *   TODO improvement to ø(|E|log|V|), ø((|E|+|V|)log|V|), O(|E|logN + log|V|!)
 *   - 1 customize a priority queue (with a binary heap and an inner array mapping the index(vertex Id)
 *   to its index in binary heap. O(1)).
 *   implements only
 *    - decreaseAt(int index)
 *    - pop()
 *   This requires updating the array every time moving an item.
 *   decreaseAt(int index) runtime complexity is O(logSize). In fact the binary heap size
 *   will be N....1 during the loop.
 *
 *   - 2 Using adjacent nodes structure to represent graph.
 *
 *   Todo: improve to O(|E|+|V|log|V|) using Fibonacci heap and adjacent nodes
 *   Todo: for Fibonacci heap the deCrease(key) need firstly find the target's key.
 */
public class MinimumSpanningTreePrimShortestPathDijkstra {

    public static Collection<Edge> mstOrSp(IGraph<IVertex, Edge> g) {
        // SP: need to know the cur Node and Fnd Node
        // MST: need to know the cur Node.

        g.initVertexDistanceStatus();
        while (g.currentStatus().equals(Status.ING)) {
            g.selectCurrentVertex();
            // Update cut with the relax vertex.
            g.updateCutWithCurrentVertex(g.getDistCalculator());
        }
        if (g.currentStatus().equals(Status.DONE)) {
            // for sp, now the end is still at the top of the evaluating heap.
            // and will not assign to cur.
            return g.getMstOrSp();
        }
        return null;
    }
}
