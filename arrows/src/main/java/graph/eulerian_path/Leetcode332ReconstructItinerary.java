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

package graph.eulerian_path;

import java.util.*;

public class Leetcode332ReconstructItinerary {
  /*
     Leetcode 332. Reconstruct Itinerary
     You are given a list of airline tickets where tickets[i] = [fromi, toi]
     represent the departure and the arrival airports of one flight.
     Reconstruct the itinerary in order and return it.

     All of the tickets belong to a man who departs from "JFK", thus,
     the itinerary must begin with "JFK". If there are multiple valid itineraries
     , you should return the itinerary that has the smallest lexical order when read as a single string.

    For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
    You may assume all tickets form at least one valid itinerary.
    You must use all the tickets once and only once.


    Input: tickets = [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
    Output: ["JFK","MUC","LHR","SFO","SJC"]

    Input: tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
    Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
    Explanation: Another possible reconstruction is
            ["JFK","SFO","ATL","JFK","ATL","SFO"] but it is larger in lexical order.


    Constraints:

    1 <= tickets.length <= 300
    tickets[i].length == 2
    fromi.length == 3
    toi.length == 3
    fromi and toi consist of uppercase English letters.
    fromi != toi

  */
  /* Understanding
    This is Eulerian Path: is a trail in a finite graph that visits every edge exactly once
    (allowing for revisiting vertices). start point decide if there is a path/circle and what it is
    Eulerian circuit or Eulerian cycle: is an Eulerian trail that starts and ends on the same vertex.
    refer https://algorithms.discrete.ma.tum.de/
    Leetcode said "two steps:
       - It starts with a random node and then follows an arbitrary unvisited edge to a neighbor.
         This step is repeated until one returns to the starting node. This yields a first circle in the graph.
      -  If this circle covers all nodes it is an Eulerian cycle and the algorithm is finished. Otherwise,
         chooses another node among the cycles' nodes with unvisited edges and constructs another circle, called subtour."

           a
          / \
         b - c - d - e
                  \ /
                   f
         visited every edge only once
         start from c or d, able to get a Eulerian path, not circle.
         other can not get a Eulerian path
          a
          / \
         b - c  - e
               \ /
                f
         start from c able to get a Eulerian circle.

     case
       input  [["JFK","KUL"],["JFK","NRT"],["NRT","JFK"]]
       expected  ["JFK","NRT","JFK","KUL"]

     e.g. https://leetcode.com/problems/reconstruct-itinerary/Figures/332/332_graph.png
     - Must begin with "JFK" : the result is affected by the start city)
     - You may assume all tickets form at least one valid itinerary.
     - fromi != toi: graph is not DAG (Directed Acyclic Graph)  no edge with same node,
                     but there is circle and there is duplicated edge.
     not topological order where using `Map<Node,Boolean> visited`
     So go forward along smallest lexical `to` cities is wrong
      input  [["JFK","KUL"],["JFK","NRT"],["NRT","JFK"]]
      expected  ["JFK","NRT","JFK","KUL"]
      wrong: ["JFK","KUL"]

   from-to may have more than one same tickets, duplicate edges.
       It is wrong to keep them in Map  from:set<to>
         input: [
          ["JFK","ANU"],["JFK","TIA"],
          ["ANU","EZE"],["EZE","AXA"],["AXA","TIA"],
          ["TIA","ANU"],["TIA","ANU"],["TIA","JFK"], (there is duplicate ticket.
                                                      how to know which is visited)
          ["ANU","JFK"],["ANU","TIA"]]

  */
  /*---------------------------------------------------------------------------
  Idea: dfs, backtracking to find all possible routine then find the matched one
  backtracking to enumerate all possible solutions for a problem, in a trial-fail-and-fallback strategy.
    ( The HashMap permits null values and the null key.
      But it is easy to only control the count of `to` city, not add/delete map entry,
      thus without update the keySet in loop )

  The given input is guaranteed to have a solution.
  So  have one less issue to consider.
  As a result, need not check if there is a solution

    Time Limit Exceeded
   */

  public List<String> findItinerary___(List<List<String>> tickets) {
    // <from: <to:count>>
    Map<String, Map<String, Integer>> fts = new HashMap();
    for (List<String> t : tickets) {
      String from = t.get(0), to = t.get(1);
      fts.putIfAbsent(from, new HashMap());
      Map<String, Integer> tn = fts.get(from);
      tn.put(to, tn.getOrDefault(to, 0) + 1);
    }

    List<String> rs = new ArrayList(); // all possible routines

    String[] routine = new String[tickets.size() + 1];
    routine[0] = "JFK";

    dfs__("JFK", fts, routine, 1, rs);

    if (rs.size() == 1) return Arrays.asList(rs.get(0).split("-"));
    String r = null;
    for (String str : rs) {
      // selected the one has the smallest lexical order when read as a single string
      if (r == null || str.compareTo(r) < 0) r = str;
    }
    return Arrays.asList(r.split("-"));
  }
  // The HashMap permits null values and the null key.
  private void dfs__(
      String from,
      Map<String, Map<String, Integer>> fts,
      String[] routine,
      int size, // of array routine
      List<String> rs) {
    if (size == routine.length) { // this can helpful to check there is solution for this question
      rs.add(String.join("-", routine));
      return;
    }
    if (fts.get(from) != null) {
      Map<String, Integer> tn = fts.get(from);
      for (String t : tn.keySet()) {
        // only control the city `to` count, not add/delete map entry to change the keySet in loop
        if (tn.get(t) > 0) {
          routine[size] = t;
          tn.put(t, tn.get(t) - 1);
          dfs__(t, fts, routine, size + 1, rs);
          tn.put(t, tn.get(t) + 1);
        }
      }
    }
  }

  /*
    Idea: improvement use greedy

    generally greedy is any algorithm that follows the problem-solving heuristic of making locally
    optimal choice at each step, with the intent of reaching the global optimum at the end.
    greedy algorithm does not necessarily lead to a globally optimal solution, but rather a reasonable
    approximation in exchange for less computing time.
    Nonetheless, sometimes it is the way to produce a global optimum for certain problems.
    This is the case for this problem as well.

    Nonetheless, sometimes it is the way to produce a global optimum for certain problems.
    This is the case for this problem as well.

    At each airport, given a list of possible destinations, while backtracking, at each step
    we would pick the destination greedily in lexical order, the final solution would have the
    smallest lexical order, because all other solutions that have smaller lexical order have
    been trialed but failed during the process of backtracking.

    O(∣E∣^d) where ∣E∣ is the number of total flights and d is the maximum number of flights
            from an airport.
    O(∣V∣+∣E∣) space
  */

  public List<String> findItinerary__(List<List<String>> tickets) {
    Map<String, List<String>> g = new HashMap(); // graph
    for (List<String> t : tickets) {
      String f = t.get(0), to = t.get(1); // from -> to, 1:n
      g.computeIfAbsent(f, k -> new ArrayList<>()).add(to);
    }
    //  map<node, sorted list<out,visited>>
    Map<String, List<String[]>> gvisit =
        new HashMap(); // graph with sorted outs list and visited status
    for (Map.Entry<String, List<String>> e : g.entrySet()) {
      List<String> l = e.getValue();
      Collections.sort(l);
      List<String[]> v = new ArrayList<>(l.size());
      for (String to : l) v.add(new String[] {to, "0"}); // 1 or 0 visited or not
      gvisit.put(e.getKey(), v);
    }

    String[] routine = new String[tickets.size() + 1];
    routine[0] = "JFK"; // routine size is 1 now

    return dfs("JFK", gvisit, routine, 1);
  }

  private List<String> dfs(
      String from, Map<String, List<String[]>> gvisit, String[] routine, int size) {
    if (size == routine.length) return Arrays.asList(routine);
    if (gvisit.get(from) == null) return null; // failed to find a complete routine
    for (String[] ov :
        gvisit.get(
            from)) { // check each of sorted list: there is duplicate work here. improved in next
      // Hierholzer's algorithm
      if (ov[1].equalsIgnoreCase("0")) {
        routine[size] = ov[0];
        ov[1] = "1";
        List<String> r = dfs(ov[0], gvisit, routine, size + 1);
        if (r != null) return r;
        ov[1] = "0";
      }
    }
    return null;
  }

  /*
    Idea: improvement
    Hierholzer's algorithm for Eulerian Path
     - Assume there is at least one solution
     - start from the fixed starting vertex (airport 'JFK'),
     keep following the ordered and unused edges (flights)
     until get stuck at certain vertex where no more unvisited outgoing edges.
     The point is the last airport. And then backwards

    Update and only keep the unused edges, thus saved the visited status variable

    Alternative of priority queue is use list, then sort the list
     `g.forEach((key, value) -> Collections.sort(value));`

   Runtime:
    Hierholzer's algorithm runtime: O(E), E is edges number or number if flights ticket in this question
    But this question need sorting the out edges
     - In the worst case the graph is of star shape,
       the JFK airport would assume half of the flights' start city
       the sorting operation O(NlogN), N=E/2
     - In a less bad /average case, each node has the equal number of outgoing flights.
       each airport would have E/V of flights, all airports have O(V*NlogN),N=E/V
       =O(Elog(E/V))
   Space
      O(V+E) used by graph. The maximum depth of the recursion is E.

  Note: why it is while not if.
        while: to check if there is a circle
     Watch:
                        c
                        ||
           z(start)  -   m   -    a
                        ||
                        b

            z->m-> b->m->c->m->a
                   b is previous of c
                   but a is tail, see why use while, not if
           if a is replaced by q:  z->m-> b->m->c->m->q

           duplicated edges
           z(start)  -    m   -    a
                        ||||
                         b
    */
  public List<String> findItinerary(List<List<String>> tickets) {
    Map<String, PriorityQueue<String>> g = new HashMap<>();
    List<String> r = new LinkedList();
    for (List<String> ticket : tickets) {
      g.computeIfAbsent(ticket.get(0), k -> new PriorityQueue()).add(ticket.get(1));
    }

    dfs("JFK", g, r); // O(E) time. Not try all vertex like topological
    return r;
  }

  void dfs(String f, Map<String, PriorityQueue<String>> g, List<String> r) {
    while (g.containsKey(f) && !g.get(f).isEmpty()) {
      // it is while not if, to check if there is a circle
      String next = g.get(f).poll(); // keep following the ordered and unused edges (flights)
      dfs(next, g, r);
      // now: next is the tail without any out degree, or the end point of Eulerian path.
      //      f should be the start city with next
      //         if the while condition is true, there is circle from f and end f,
      //            so f is not ready to be a new tail end now.
      //            need finish the circle firstly before get it ready as start city of next
      //         else f is ready to be a new tail end or the start city with next
    }
    // no more unvisited outgoing edges.
    // ready to be the start city, so inserting it at index 0, with next.
    r.add(0, f);
  }

  /*
  Idea: recursion -> iterator with explicit stack, thus no back-tracking.
   but it does change the space complexity
  */
  public List<String> findItinerary_(List<List<String>> tickets) {
    Map<String, PriorityQueue<String>> g = new HashMap<>();
    for (List<String> t : tickets) {
      g.computeIfAbsent(t.get(0), k -> new PriorityQueue()).add(t.get(1));
    }

    Stack<String> s = new Stack<>();
    s.push("JFK");
    List<String> r = new LinkedList();
    while (!s.isEmpty()) {
      if (g.containsKey(s.peek()) && !g.get(s.peek()).isEmpty()) {
        s.push(g.get(s.peek()).poll());
      } else {
        r.add(0, s.pop());
      }
    }
    return r;
  }
}

class GreedySolution {
  // from -> to list
  HashMap<String, List<String>> g = new HashMap<>();
  HashMap<String, boolean[]> vist = new HashMap<>();
  int ticketsSize = 0;
  List<String> ans = null;

  public List<String> findItinerary(List<List<String>> tickets) {

    for (List<String> t : tickets)
      g.computeIfAbsent(t.get(0), k -> new LinkedList<>()).add(t.get(1));

    for (Map.Entry<String, List<String>> e : this.g.entrySet()) {
      Collections.sort(e.getValue());
      vist.put(e.getKey(), new boolean[e.getValue().size()]); // default false: never visited
    }

    ticketsSize = tickets.size();
    LinkedList<String> route = new LinkedList<String>();
    route.add("JFK");

    backtracking("JFK", route);
    return ans;
  }

  protected boolean backtracking(String f, LinkedList<String> route) {
    if (route.size() == ticketsSize + 1) {
      this.ans = (List<String>) route.clone();
      return true;
    }

    if (!g.containsKey(f)) return false;

    boolean[] vl = vist.get(f);
    int i = 0;
    for (String to : g.get(f)) {
      if (!vl[i]) {
        vl[i] = true;
        route.add(to);
        if (this.backtracking(to, route)) return true;
        route.pollLast();
        vl[i] = false;
      }
      i++;
    }
    return false;
  }
}

class HierholzerSolution {
  // from -> to list
  HashMap<String, LinkedList<String>> g = new HashMap<>();
  LinkedList<String> ans = null;

  public List<String> findItinerary(List<List<String>> tickets) {
    for (List<String> t : tickets) {
      g.computeIfAbsent(t.get(0), k -> new LinkedList<>()).add(t.get(1));
    }

    g.forEach((key, value) -> Collections.sort(value));

    ans = new LinkedList<>();
    DFS("JFK");
    return ans;
  }

  protected void DFS(String f) {
    if (this.g.containsKey(f)) {
      LinkedList<String> outs = g.get(f);
      while (!outs.isEmpty()) {
        String to = outs.pollFirst();
        DFS(to);
      }
    }
    ans.offerFirst(f);
  }
}
/* ----------------------------------------------------------------------------

  undirected graph, maybe there is circle by multiple nodes
  transmit a file from a given node, each node should be visited at lest once.
  E.g.

A -- B -- C
|    |
E -- D
stat node is A
one possible path is :a-b-c-b-d-e-a

`2 nodes are neighbor` means there are 2 directed edge between them to show
 neighbor relationship of each other.

neighbor permutation backtracking over more methods
*/

interface File {
  boolean sendTo(Handler n, StringBuilder path);
}

interface Handler {
  boolean receive(File f, StringBuilder path);

  String getName();
}

class FileImp implements File {
  private final int nodeNum;
  Set<Handler> set;

  public FileImp(int nodeNum) {
    this.nodeNum = nodeNum;
    set = new HashSet<>();
  }

  private boolean visitAllNodeNum() {
    return set.size() == nodeNum;
  }

  private void visited(Handler n) {
    set.add(n);
  }

  private void unVisited(Handler n) {
    set.remove(n);
  }

  @Override
  public boolean sendTo(Handler n, StringBuilder path) {
    path.append(n.getName());

    visited(n);
    if (visitAllNodeNum()) return true;

    if (n.receive(this, path)) return true;

    unVisited(n);
    path.deleteCharAt(path.length() - 1);
    return false;
  }
}

class Node implements Handler {
  // return true: if path via current node is right
  // backtracking neighbors' permutation, which available one should be tried  firstly matter
  @Override
  public boolean receive(File f, StringBuilder path) {
    int trys = 0;
    while (neighbors.size() > trys) {
      Handler e = neighbors.removeLast();
      if (f.sendTo(e, path)) return true;
      else {
        neighbors.offerFirst(e);
        trys++;
      }
    }
    return false;
  }

  public static void main(String[] args) {
    File f = new FileImp(5);
    Node a = new Node("A"),
        b = new Node("B"),
        c = new Node("C"),
        d = new Node("D"),
        e = new Node("E");
    a.addNeighbor(b, e);
    b.addNeighbor(a, d, c);
    c.addNeighbor(b);
    e.addNeighbor(a, d);
    d.addNeighbor(b, e);

    Node start = a;
    StringBuilder path = new StringBuilder();
    if (f.sendTo(start, path)) System.out.println(path);
    else System.out.println("No valid path");
  }

  @Override
  public String getName() {
    return name;
  }

  String name;
  Deque<Handler> neighbors;

  public void addNeighbor(Node... nodes) {
    for (Node n : nodes) neighbors.add(n);
  }

  public Node(String name) {
    this.name = name;
    this.neighbors = new LinkedList<>();
  }
}
