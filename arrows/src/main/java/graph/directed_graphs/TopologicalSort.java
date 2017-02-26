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
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * <pre>
 * Condition:
 *          DAG：Directed Acyclic Graph
 * Aim:     a linear ordering of vertexes such that for every directed edge uv, vertex u comes before v in the ordering.
 * the solution is not necessarily unique;
 * Only one result condition:
 *          If a topological sort has the property that all pairs of consecutive vertices in the sorted order
 *          are connected by edges, then these edges form a directed Hamiltonian path in the DAG.
 *          If a Hamiltonian path exists, the topological sort order is unique;
 *          全序: DAG中的任意一对顶点还需要有明确的关系(反映在图中，就是单向连通的关系)
 *
 * 1> Kahn's algorithm:
 *    Data structures:
 *      The node class should have 'ins' and 'outs'
 *      ALL: graph nodes
 *      L:  list that will contain the result. initialized by an empty list.
 *      S: container (it can be set, queue, stack) of all nodes with no incoming edges. find out all of them firstly.
 *
 *    Algorithm: BFS
 *      while S is non-empty do
 *          remove a node n from S.
 *          insert n into L, and remove each edge e from n to this outs, if a give out of n has no ins anymore, then
 *          push this out in S.
 *
 *      if S is empty and graph still has edges left
 *          return error (graph has at least one cycle)
 *      else
 *          return L (a topologically sorted order)
 *
 *     cons: V need has ins;
 *           need find out all S firstly.
 *           need update the V
 *
 *     pros: can detect circle. do not need "visited" Set to check circle
 *
 *      todo A variation of Kahn's algorithm that breaks ties lexicographically forms a key component
 *      of the Coffman–Graham algorithm for parallel scheduling and layered graph drawing.
 *
 *  2> Depth-first search
 *
 *  data structure:
 *    Set<V> all nodes
 *    V class:  only outs
 *    Stack:  to keep the result 倒退时放入
 *    Set: visited. avoid duplicated at merge point.
 *         不是用来checking circle
 *         (using isInCurVisitedPath checking circle see
 *         {@link graph.directed_graphs.Leetcode207CourseSchedule#hasCircle(int, List[], boolean[])}
 *
 *    algorithm:
 *      post order + DFS
 *
 *    cons:  need check circle firstly
 *    pros:  from any node to start
 *
 *  todo The topological ordering can also be used to quickly compute shortest paths through
 *  a weighted directed acyclic graph.
 *
 *   <a href="https://en.wikipedia.org/wiki/Topological_sorting"> wiki </a>
 *   <a href="https://www.youtube.com/watch?v=ddTC4Zovtbc"> DFS from youtube </a>
 *   <a href="http://www.cse.cuhk.edu.hk/~taoyf/course/2100sum11/lec14.pdf"> DFS </a>
 *
 *  --- circle ---
 *   o
 *    \
 *     o -o
 *     |  |
 *     o- o
 *    /
 *   o
 *
 *  --- no circle ---
 *    o
 *     \
 * o    o
 *  \ / \
 *   o   o
 *   |   |
 *   o   o
 *    \ /
 *     o
 */
public class TopologicalSort {
    //as do not care edge weight, make the data structure simple.
    class V {
        Set<V> outgoings; // neighbors
    }

    // Assume this is not circle
    public static Stack<V> topoSort(Set<V> graph) {
        Stack<V> r = new Stack();
        Set<V> visited = new HashSet<>();

        for (V v : graph) {
            dfsPosterOrder(v, r, visited);
        }
        return r;
    }

    private static void dfsPosterOrder(V vertex, Stack<V> r, Set<V> visited) {
        if (!visited.contains(vertex)) {
            visited.add(vertex);
            for (V out : vertex.outgoings) {
                dfsPosterOrder(out, r, visited);
            }
            r.push(vertex); // post order
        }
    }
}
