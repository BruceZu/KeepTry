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

import java.util.Arrays;

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
public class MinimumSpanningTreePrim {

    /**
     * <pre>
     *  O(N^2)
     * @return
     */
    public static int[] mstPrim(int graph[][]) {
        int V = graph.length;

        int vTo[] = new int[V]; // the vertex in MST from where the shortest edge exits
        Arrays.fill(vTo, -1);

        int distTo[] = new int[V]; // the shortest edge length from MST
        Arrays.fill(distTo, Integer.MAX_VALUE);

        boolean relax[] = new boolean[V];
        Arrays.fill(relax, false);

        distTo[0] = 0;
        for (int i = 0; i < V; i++) {
            int min = Integer.MAX_VALUE;
            int v = -1;

            // select out the shortest edge in the cut, relax a vertex.
            for (int vi = 0; vi < V; vi++) {
                if (!relax[vi] && distTo[vi] < min) {
                    min = distTo[vi];
                    v = vi;
                }
            }

            relax[v] = true;

            // Update cut with the relax vertex.
            for (int vi = 0; vi < V; vi++) {
                if (!relax[vi] /* is in cut set */
                        && graph[v][vi] != 0 /* there is edge */
                        && graph[v][vi] < distTo[vi] /* now the v contributes
                      the shortest edge from MST to the vi which is not in MST */) {
                    distTo[vi] = graph[v][vi];
                    vTo[vi] = v;
                }
            }
        }
        return vTo;
    }
}
