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

package graph.undirected_graph;

import java.util.*;

public class Leetcode261GraphValidTree {
  /*
   261. Graph Valid Tree
  You have a graph of n nodes labeled from 0 to n - 1.
  You are given an integer n and a list of edges where edges[i] = [ai, bi]
  indicates that there is an undirected edge between nodes ai and bi in the graph.

  Return true if the edges of the given graph make up a valid tree, and false otherwise.

   Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.
   Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.
   Given n = 4 and edges = [[2,3],[1,2],[1,3]],  Expected: false.
   Given n = 5 and edges = [[2,3],[1,2],[1,3],[0,4]], Expected: false.

      1 <= 2000 <= n
      0 <= edges.length <= 5000
      edges[i].length == 2
      0 <= ai, bi < n
      ai != bi
      There are no self-loops or repeated edges.

  Hint:
  According to the definition of tree on Wikipedia:
  “a tree is an undirected graph in which any two vertices are connected by exactly one path.
  In other words, any connected graph without simple cycles is a tree.”
  Tree:  There is a path, and  only one path between them each pair of nodes in a undirected graph
  if the graph is fully connected and contains exactly n - 1 edges,
  it can't possibly contain a cycle, and therefore must be a tree!
  */

  /*
  Understanding:
   n >=1
   Tree: undirected graph
    - connected
    - no circle

    "ai != bi
    There are no self-loops or repeated edges."
    So if there is circle it should include at least 3 nodes or 3 undirected edges

   If it is tree then node number-1 == edges number, reverse is not right except there is no circle.
   one node is also a tree.
   if the graph is fully connected and contains exactly n - 1 edges,
   it can't possibly contain a cycle, and therefore must be a tree!

   Question is about how to check circle and connection in a undirected graph?
  */
  /*---------------------------------------------------------------------------
  Idea DFS recursion with call stack
      use a Map node: neighbors on edges,1:n,to describe undirected graph
      circle checking with DFS recursion:
         use `from`, initial with -1, to track previous node, with it to make sure go
         ahead like that in directed graph. detect circle in undirected graph
         from which node to start?
            dfs from any node has the same result, so just from node 0.
            not all node, including node 0,always has edge.4

            E.g.: node 0 has not edge
              Given n = 4 and edges = [[2,3],[1,2],[1,3]],
              Expected: false.

         do not need a `pathVisited` variable because the accessing order.
     connection checking
         use `Set<Integer> visited` to calculate all visited node in all DFS path
         at last check its size with all nodes
         DFS circle checking only make sure handled part has no circle. E.g.:
         Given n = 5 and edges = [[2,3],[1,2],[1,3],[0,4]], Expected: false.
         So still need check visited nodes number == all nodes number to make sure
         connection.
   O(V+E) time, space
  */
  public boolean validTree_____(int n, int[][] edges) {
    if (n == 1) return true;
    if (edges == null || edges.length != n - 1) return false;
    Map<Integer, Set<Integer>> g = new HashMap();
    for (int[] e : edges) {
      g.putIfAbsent(e[0], new HashSet());
      g.get(e[0]).add(e[1]);
      g.putIfAbsent(e[1], new HashSet());
      g.get(e[1]).add(e[0]);
    }

    Set<Integer> v = new HashSet();

    if (g.get(0) == null) return false; // current node 0 has not edge

    v.add(0);
    if (dfs(0, v, -1, g)) return false;
    return v.size() == n;
  }

  // find circle?
  private boolean dfs(int cur, Set<Integer> v, int from, Map<Integer, Set<Integer>> g) {
    for (int n : g.get(cur)) {
      if (n == from) continue;

      if (v.contains(n)) return true;
      v.add(n);

      if (dfs(n, v, cur, g)) return true;
    }
    return false;
  }

  /*---------------------------------------------------------------------------
    Idea DFS with stack to mack the call stack. no keeping `from` node info
     - mocked stack  keep the right node access order
     - without `from` variable used in recursion version, instead use the same skill in BFS:
       remove the cur node from neighbor node's neighbors，thus keep going forward like in directed graph.
    still need a visited variable
   O(V+E) time, space
  */
  public boolean validTree____(int N, int[][] edges) {
    if (N == 1) return true;
    if (edges == null || edges.length != N - 1) return false;
    Map<Integer, Set<Integer>> g = new HashMap();
    for (int[] e : edges) {
      g.putIfAbsent(e[0], new HashSet());
      g.get(e[0]).add(e[1]);
      g.putIfAbsent(e[1], new HashSet());
      g.get(e[1]).add(e[0]);
    }
    // circle checking
    Stack<Integer> s = new Stack<>();
    Set<Integer> v = new HashSet<>();

    if (g.get(0) == null) return false; // current node 0 has not edge

    v.add(0);
    s.push(0);
    while (!s.isEmpty()) {
      int cur = s.pop();
      for (int n : g.get(cur)) {
        if (v.contains(n)) return false;
        v.add(n);

        s.push(n);
        g.get(n).remove(cur);
      }
    }

    // connection checking
    return v.size() == N;
  }
  /*---------------------------------------------------------------------------
    Idea DFS with stack to mack the call stack. Keep from node info in map variable `visited: from`
     - mocked stack  keep the right node access order
     - `Map<integer,Integer> vistedAndFrom` keep from in visited variable
     This skill can also be used in BFS
   O(V+E) time, space
  */
  public boolean validTree___(int N, int[][] edges) {
    if (N == 1) return true;
    if (edges == null || edges.length != N - 1) return false;
    Map<Integer, Set<Integer>> g = new HashMap();
    for (int[] e : edges) {
      g.putIfAbsent(e[0], new HashSet());
      g.get(e[0]).add(e[1]);
      g.putIfAbsent(e[1], new HashSet());
      g.get(e[1]).add(e[0]);
    }
    // circle checking
    Stack<Integer> s = new Stack<>();
    Map<Integer, Integer> vf = new HashMap<>();

    if (g.get(0) == null) return false; // current node 0 has not edge

    vf.put(0, -1);
    s.push(0);
    while (!s.isEmpty()) {
      int cur = s.pop();
      for (int n : g.get(cur)) {
        if (vf.get(cur) == n) continue;

        if (vf.containsKey(n)) return false;
        vf.put(n, cur);

        s.push(n);
      }
    }

    // connection checking
    return vf.size() == N;
  }
  /*---------------------------------------------------------------------------
   Idea: BFS without `from`
      Circle checking:
        if there is circle, one node on the circle will be firstly visited and put in queue.

        once a node is pushed in queue, this node is marked as visited.
        so any node in queue is also be visited

       once a node is polled out from the queue, he should not visit his parent again, for this objective
       remove the `parent node-this node edge` relation from this node's adjacent nodes set.

       at last
        - if the circle has even number nodes: the last node has 2 parent who is push in queue in one patch
          when the last parent is polled out and before push the last node in the queue, the last node
          is found visited. and circle is found.
       - if the circle has odd number node: the last will be push into queue in the same batch layer
          but before the last node is pushed into the queue it is found visited, then circle is found

    Connection checking
       BFS circle checking only make sure handled part has no circle. E.g.:
       Given n = 5 and edges = [[2,3],[1,2],[1,3],[0,4]], Expected: false.
       So still need check visited nodes number == all nodes number to make sure
       connection.

   O(V+E) time, space
  */
  public static boolean validTree__(int N, int[][] edges) {
    if (N == 1) return true;
    if (edges == null || edges.length != N - 1) return false;

    Set<Integer>[] g = new HashSet[N];
    for (int i = 0; i < N; i++)
      g[i] = new HashSet<>(); // if start node has not edge, here it will be an empty set
    for (int[] e : edges) {
      g[e[0]].add(e[1]);
      g[e[1]].add(e[0]);
    }

    boolean[] v = new boolean[N];
    Queue<Integer> q = new LinkedList<>();

    if (g[0].isEmpty()) return false;

    q.offer(0);
    v[0] = true;
    while (!q.isEmpty()) {
      int cur = q.poll();
      for (int n : g[cur]) {
        if (v[n]) return false;
        v[n] = true;

        q.offer(n);
        g[n].remove(cur);
      }
    }
    // connection checking
    for (boolean vi : v) if (!vi) return false;
    return true;
  }
  /*---------------------------------------------------------------------------
  Idea: BFS keep `from` in map visited
  O(V+E) time, space
  */
  public static boolean validTree_(int N, int[][] edges) {
    if (N == 1) return true;
    if (edges == null || edges.length != N - 1) return false;

    Set<Integer>[] g = new HashSet[N];
    for (int i = 0; i < N; i++)
      g[i] = new HashSet<>(); // if start node has not edge, here it will be an empty set
    for (int[] e : edges) {
      g[e[0]].add(e[1]);
      g[e[1]].add(e[0]);
    }

    Map<Integer, Integer> vf = new HashMap<>();
    Queue<Integer> q = new LinkedList<>();

    if (g[0].isEmpty()) return false;

    vf.put(0, -1);
    q.offer(0);
    while (!q.isEmpty()) {
      int cur = q.poll();
      for (int n : g[cur]) {
        if (vf.get(cur) == n) continue;

        if (vf.containsKey(n)) return false;
        vf.put(n, cur);

        q.offer(n);
      }
    }
    // connection checking
    return vf.size() == N;
  }
  /*---------------------------------------------------------------------------
  Idea:
    Check n - 1 edges +  fully connected.
    Return true if it is else false

    need to use a visited set variable to prevent infinite loop
    BFS
    O(V) time and space E=V-1
   */
  public static boolean validTree2(int N, int[][] edges) {
    if (N == 1) return true;
    if (edges == null || edges.length != N - 1) return false;

    Set<Integer>[] g = new HashSet[N];
    for (int i = 0; i < N; i++)
      g[i] = new HashSet<>(); // if start node has not edge, here it will be an empty set
    for (int[] e : edges) {
      g[e[0]].add(e[1]);
      g[e[1]].add(e[0]);
    }
    // calculate all connected nodes
    Set<Integer> v = new HashSet<>();
    Queue<Integer> q = new LinkedList<>();

    if (g[0].isEmpty()) return false;

    v.add(0);
    q.offer(0);
    while (!q.isEmpty()) {
      int cur = q.poll();
      for (int n : g[cur]) {
        if (v.contains(n)) continue;
        v.add(n);
        q.offer(n);
      }
    }
    // connection number checking
    return v.size() == N;
  }
  /*---------------------------------------------------------------------------
  Idea:
    Check n - 1 edges +  fully connected.
    Return true if it is else false

    need to use a visited set variable to prevent infinite loop
   DFS with stack
   O(V) time and space
   */
  public static boolean validTree1(int N, int[][] edges) {
    if (N == 1) return true;
    if (edges == null || edges.length != N - 1) return false;
    Map<Integer, Set<Integer>> g = new HashMap();
    for (int[] e : edges) {
      g.putIfAbsent(e[0], new HashSet());
      g.get(e[0]).add(e[1]);
      g.putIfAbsent(e[1], new HashSet());
      g.get(e[1]).add(e[0]);
    }
    // calculate all connected nodes
    Stack<Integer> s = new Stack<>();
    Set<Integer> v = new HashSet<>();

    if (g.get(0) == null) return false; // current node 0 has not edge

    v.add(0);
    s.push(0);
    while (!s.isEmpty()) {
      int cur = s.pop();
      for (int n : g.get(cur)) {
        if (v.contains(n)) continue;
        v.add(n);
        s.push(n);
      }
    }

    // connection number checking
    return v.size() == N;
  }

  /*---------------------------------------------------------------------------
    Idea: Union found:
     if it is tree, after union all edges it will become a tree with hight == 1.
      - union: find circle
      - at last check how many group to check connection

     once parsing all edges with union function, return true
     because if edges.len==n-1 && union found find there is no loop everywhere, then, must be valid.
     so need the following code to check root numbers
      //    int roots = 0;
      //    for (int i = 0; i < n; i++) {
      //      if (root[i] == i) roots++;
      //      if (roots > 1) return false;
      //    }
      //    return true;

    O(V⋅α(V)) time,  E=V-1,
    https://en.wikipedia.org/wiki/Ackermann_function#Inverse
    find(...) amortizes to O(α(N)), where α is the Inverse Ackermann Function which grows
    so slowly that N will never go higher than 4, So in "practice" it is effectively O(1).
    in "theory" it is not.
    union find's operations can be taken as O(1)

    O(V) space
     */
  public boolean validTree(int n, int[][] edges) {
    if (n == 1) return true;
    if (edges.length != n - 1) return false;

    int[] root = new int[n];
    for (int i = 0; i < n; i++) root[i] = i;
    int[] rank = new int[n];
    for (int[] e : edges) if (union(e[0], e[1], root, rank)) return false;
    return true;
  }
  /* if node a and b has not same root then union their root as usual,
  else return true. find circle*/
  private boolean union(int a, int b, int[] root, int rank[]) {
    int ra = find(root, a);
    int rb = find(root, b);
    if (ra == rb) return true;
    if (rank[ra] < rank[rb]) root[ra] = rb; // it is ra not a
    else if (rank[ra] > rank[rb]) root[rb] = ra; // it is rb not b
    else {
      root[ra] = rb; // it is ra not a
      rank[rb]++; // it is rb not a and ra
    }
    return false;
  }

  private int find(int[] root, int i) {
    if (root[i] == i) return i;
    else {
      int r = find(root, root[i]);
      root[i] = r;
      return r;
    }
  }
  // iterator version find
  public int find2(int[] root, int i) {
    int x = i;
    while (root[i] != i) {
      i = root[i];
    }
    // i is result now, before returning it update all path
    while (root[x] != i) {
      int n = root[x];
      root[x] = i;
      x = n;
    }
    return i;
  }
}
