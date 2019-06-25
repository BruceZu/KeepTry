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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import unionfind.UnionFind;
import unionfind.UnionFindImp;

/**
 *
 *  <pre>
 *    Kruskal's Spanning Tree Algorithm. O(|E|log|V|).
 *      1 - sort edges in increasing order of weight.
 *      2 - Add the edge which has the least weight, until there are (V-1)
 *          edges in the spanning tree.
 *          keep checking that the spanning properties remain intact.
 *          e.g. ignore/avoid all edges that create a circuit.
 */
public class MinimumSpanningTreeKruskal {

    /**
     * @param N = |V|, M = |E|
     * @param edges graph represented by edges array
     */
    public static Collection<Edge> kruskal(int N, Edge[] edges) {
        List<Edge> r = new ArrayList();

        Arrays.sort(edges); // O(MlogM). M <= N(N-1)<N^2, O(MlogM)-> O(MlogN)

        UnionFind<Integer> uf =
                new UnionFindImp(IntStream.range(0, N).boxed().collect(Collectors.toSet()));

        int ei = 0; // index of edges
        Edge e; // current edge
        do { //  O(M)
            e = edges[ei];
            int v1Root = uf.find(e.from), v2Root = uf.find(e.to);
            if (v1Root != v2Root) {
                // skip when v1Root == v2Root, else it will create circle
                r.add(e);
                uf.union(v1Root, v2Root);
            }
            ei++;
        } while (r.size() < N - 1);
        return r;
    }
}
