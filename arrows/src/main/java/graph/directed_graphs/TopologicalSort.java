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

package graph.directed_graphs;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * <pre>
 * Condition:
 *          DAG：Directed Acyclic Graph
 * Aim:     a linear ordering of vertices such that for every directed edge uv, vertex u comes before v in the ordering.
 * Only one result condition:
 *          If a topological sort has the property that all pairs of consecutive vertices in the sorted order
 *          are connected by edges, then these edges form a directed Hamiltonian path in the DAG.
 *          If a Hamiltonian path exists, the topological sort order is unique;
 *          全序: DAG中的任意一对顶点还需要有明确的关系(反映在图中，就是单向连通的关系)
 *
 * 1> Kahn's algorithm:
 *      L:Empty list that will contain the sorted elements
 *      S:Set of all nodes with no incoming edges
 *      while S is non-empty do
 *          remove a node n from S (without special order, it can be set, queue, stack)
 *          insert n into L
 *          for each node m with an edge e from n to m do
 *              remove edge e from the graph
 *              if m has no other incoming edges then
 *                  insert m into S
 *      if graph has edges then, all nodes has incoming edge(s)
 *          return error (graph has at least onecycle)
 *      else
 *          return L (a topologically sortedorder)
 *
 * Note:
 *      - need get S firstly
 *      - the solution is not necessarily unique;
 *
 *      todo A variation of Kahn's algorithm that breaks ties lexicographically forms a key component
 *      of the Coffman–Graham algorithm for parallel scheduling and layered graph drawing.
 *
 *  2> Depth-first search (DFS) algorithm:
 *      At each vertex v, DFS “eagerly” switches to a neighbor of v (unlike BFS that switches only after
 *      having inspected all the neighbors of v)
 *
 *      vertex v is a neighbor of vertex u, if (u, v) ∈ E, namely, there is an edge from u to v.
 *      it doesn't fail if there's a circular dependency.
 *      Depth-first search takes O(|Vertex | + |E|) time
 *  todo The topological ordering can also be used to quickly compute shortest paths through
 *  a weighted directed acyclic graph.
 *
 *  @see <a href="https://en.wikipedia.org/wiki/Topological_sorting"> wiki </a>
 *  @see <a href="https://www.youtube.com/watch?v=ddTC4Zovtbc"> DFS from youtube </a>
 *  @see <a href="http://www.cse.cuhk.edu.hk/~taoyf/course/2100sum11/lec14.pdf"> DFS </a>
 */
public class TopologicalSort {
    //as do not care edge weight, make the data structure simple.
    class V {
        Set<V> incomings;
        Set<V> outgoings; // neighbors
    }

    public Stack<V> toposortDFS(Set<V> graph) {
        Stack<V> r = new Stack<>();
        Set<V> visited = new HashSet<>();
        for (V v : graph) {
            if (!visited.contains(v)) {
                toposortDFS(v, r, visited);
            }
        }
        return r;
    }

    private void toposortDFS(V vertex, Stack<V> r, Set<V> visited) {
        visited.add(vertex);
        for (V out : vertex.outgoings) {
            if (!visited.contains(out)) {
                toposortDFS(out, r, visited);
            }
        }
        r.push(vertex);
    }
}
