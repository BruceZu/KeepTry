//  Copyright 2021 The KeepTry Open Source Project
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

package graph.directed_graphs.topological_sort;

import java.util.*;

/**
 * <pre>
 * Condition: Directed Acyclic Graph(DAG)
 * Only one result condition:
 *          If a topological sort has the property that all pairs of consecutive vertices in
 *          the sorted order  are connected by edges, then these edges form a directed
 *          Hamiltonian path in the DAG.
 *          If a Hamiltonian path exists, the topological sort order is unique;
 *
 * 1> Kahn's algorithm(BFS)
 *    See used in {@link Leetcode269AlienDictionary}
 *    Data structures:
 *      2 map<node, set<node>>: 'ins'(1:n), 'outs' (1:n)
 *                               ins can be map<node, Integer>. But this is not good choice as this need
 *                               to avoid duplicated operations to increase the Integer value
 *                               for 2 same relation or use map<node, list<ode> data structure for `out`
 *      set<node> all: all nodes in graph, this can be merged with indegree maps `ins`, but
 *                     not good as concept is not clean
 *      queue: nodes with no incoming edges.
 *      list/String (keep result)
 *
 *    Algorithm steps:
 *      initial `all` set
 *      find out all edge to initial `in` and `out`
 *      initial queue by all.removeAll(in.keySet())
 *      while(  queue  is not  empty) {
 *        BFS:
 *          if current node `k` has not out set `value`, provide a empty set, do not run into null pointer.
 *                                                       or initial out with an empty set during initialize the `all`
 *          remove key in ins if its value set is empty: no ins for this node
 *      }
 *      if  `ins` set is not empty
 *          return error (graph has at least one cycle)
 *
 *
 *    Algorithm  cons:
 *           need has ins;
 *           need find out all nodes(Kept in `all` set) and edges(kept in and out maps).
 *           need update the ins
 *
 *    Algorithm pros: can detect circle. do not need "visited" Set to check circle
 *    O(V+E) time and space
 *      todo A variation of Kahn's algorithm that breaks ties lexicographically forms a key component
 *      of the Coffman–Graham algorithm for parallel scheduling and layered graph drawing.
 *
 *  2> Depth-first search(Assume it is DAG. no circle, need
 *    - checking circle before running this algorithm)
 *    - or merge  checking circle with  topological sort order together see
 *      See used in {@link Leetcode269AlienDictionary}
 *
 *  data structure:
 *    Set: keep all nodes
 *    map: only outs
 *    stack:  to keep the result during on the back forward path
 *    Set: visited. avoid duplicated at merge point.
 *         Not be used to checking circle.
 *
 *   steps:  from each node to try DFS +  post order to keep result
 *
 *    cons:  need check circle firstly
 *    pros:  need not update the edge/connection relationship in indegree map, so no indegree map
 *
 *   O(V+E) time and space
 *  todo The topological order can also be used to quickly compute shortest paths through
 *  a weighted directed(negative wight is allowed) acyclic graph.
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
  // refer Leetcode210CourseScheduleii; Leetcode269AlienDictionary
  // 1> Kahn's algorithm  BFS -------------------------------------------------
  static class G {
    private List<Integer>[] out;
    private int[] in; // number of in degree nodes of the node whose ID is index of array in
    private int V; // number of Vertex which has ID from 0 to V-1

    public G(int V) {
      this.V = V;
      out = new List[V];
      for (int i = 0; i < V; i++) out[i] = new ArrayList();
      in = new int[V];
    }

    public G edge(int from, int to) {
      out[from].add(to);
      in[to]++;
      return this;
    }

    public List topologicalOrder() {
      Queue<Integer> q = new LinkedList();
      for (int i = 0; i < V; i++)
        if (in[i] == 0) q.add(i); // those nodes who have no in degree node(s)

      List<Integer> r = new ArrayList<>(V);
      // node Id
      while (!q.isEmpty()) {
        Integer n = q.poll();
        r.add(n);
        for (int o : out[n]) if (--in[o] == 0) q.add(o);
      }

      if (r.size() != V) return null; // there is circle
      return r;
    }
  }

  /*
  2> DFS -------------------------------------------------------------------
    DFS used only for DAG. So need check circle
    as do not care edge weight, make the data structure simple.
  */
  class Node {
    public char v; // unique ID
  }

  public String topologicalSortOrder(Map<Node, Set<Node>> g) {
    Map<Node, Boolean> v = new HashMap<>();
    StringBuilder r = new StringBuilder();
    for (Node o : g.keySet()) {
      if (dfs(o, v, r, g)) return null;
    }
    return r.reverse().toString();
  }

  //  find circle + calculate Topological Sort Order
  boolean dfs(Node n, Map<Node, Boolean> v, StringBuilder r, Map<Node, Set<Node>> g) {
    if (v.containsKey(n)) return v.get(n); // true: find circle
    else v.put(n, true);
    for (Node o : g.get(n)) {
      if (dfs(o, v, r, g)) return true; // find circle
    }
    v.put(n, false);

    r.append(n.v);
    return false;
  }
  // --------------------------------------------------------------------------
  public static void main(String[] args) {
    // graph is a case in algs4.cs.princeton.edu
    G g = new G(8);
    g.edge(0, 1)
        .edge(0, 7)
        .edge(0, 4)
        .edge(1, 3)
        .edge(1, 2)
        .edge(1, 7)
        .edge(4, 5)
        .edge(4, 6)
        .edge(4, 7)
        .edge(7, 2)
        .edge(7, 5)
        .edge(5, 2)
        .edge(5, 6)
        .edge(2, 3)
        .edge(2, 6)
        .edge(3, 6);
    System.out.println(g.topologicalOrder());
    // [0, 1, 4, 7, 5, 2, 3, 6]
  }

  // cases ====================================================================
  /**
   * Consider you have services that need to be started, but they have the following dependency
   * services which need to be started first
   *
   * <p>A -> [] B -> [C, F] C -> [] D -> [A, E] E -> [F] F -> []
   *
   * <p>Output: [A, C, F, B, E, D]
   */
  static class Solution {
    public char[] topolicalOrder1(Map<Character, List<Character>> g) {
      if (g == null) return null;
      Map<Character, Boolean> visit = new HashMap<>();
      StringBuilder sb = new StringBuilder();
      for (Character server : g.keySet()) {
        if (dfs(server, g, sb, visit)) return null;
      }

      return sb.toString()
          .toCharArray(); // do not need reverse as dependencies is recorded before current task
      // sb:  A C  F  B  E  D
    }

    private boolean dfs(
        Character server,
        Map<Character, List<Character>> g,
        StringBuilder r,
        Map<Character, Boolean> visit) {
      if (visit.containsKey(server)) return visit.get(server);
      visit.put(server, true);
      for (Character c : g.get(server)) {
        if (dfs(c, g, r, visit)) return true;
      }
      visit.put(server, false);

      r.append(server);
      return false;
    }

    /*
    Tasks 0~tasks-1
    each array in followingTask is:  [current task, following task1,  following task2]
    */
    public List<Integer> topolicalOrder2(Integer tasks, int[][] followingTasks) {
      if (followingTasks == null) return null;

      Map<Integer, Set<Integer>> g = new HashMap<>();

      for (int[] a : followingTasks) {
        int currentTask = a[0];
        for (int i = 1; i < a.length; i++) {
          g.putIfAbsent(currentTask, new HashSet<>());
          g.get(currentTask).add(a[i]);
        }
      }
      // g: current task - following task
      //  [0, 1], [1, 2], [2, 0]
      //  0->{1}
      //  1->{2}
      //  2->{0}

      Map<Integer, Boolean> visit = new HashMap<>();
      List<Integer> r = new ArrayList<>();

      // ‘N’ tasks, labeled from ‘0’ to ‘N-1’
      for (int t = 0; t < tasks; t++) {
        boolean findCircle = dfs(t, g, r, visit);
        if (findCircle) return null;
      }

      Collections.reverse(r); // required as following task are recorded before current one.
      return r;
      //     2, 1, 0 -> // 0 , 1 ,2
    }

    // return true if find circle
    private boolean dfs(
        int t, Map<Integer, Set<Integer>> g, List<Integer> r, Map<Integer, Boolean> visit) {
      if (visit.containsKey(t)) return visit.get(t);
      // if it is false, still return, as false means previous dfs visited it

      visit.put(t, true);

      if (g.get(t) == null) { // when some task has not following tasks
        g.putIfAbsent(t, new HashSet<>());
      }
      for (int c : g.get(t)) {
        if (dfs(c, g, r, visit)) return true; // find circle on the currentpath
      }
      visit.put(t, false);

      r.add(t);
      return false;
      // 2, 1, 0
    }

    public static void main(String[] args) {
      Solution t = new Solution();
      List<Integer> r = t.topolicalOrder2(3, new int[][] {{0, 1}, {1, 2}});
      System.out.println(r.toString());
    }
  }
}
