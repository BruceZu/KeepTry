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

   a list of airline tickets where tickets[i] = [fromi, toi] represent the departure and the arrival airports
   of one flight.

   Reconstruct the itinerary in order and return it.
   - Must begin with "JFK".
   - If there are multiple valid itineraries, you should return the
     itinerary that has the smallest lexical order when read as a single string.



   You may assume all tickets form at least one valid itinerary.
   You must use all the tickets once and only once.

    1 <= tickets.length <= 300
    tickets[i].length == 2
    fromi.length == 3
    toi.length == 3
    fromi and toi consist of uppercase English letters.
    fromi != toi

  */

  /*
    Understanding
     1. some city can form a circle, the whole graph is not DAG
        So go forward along smallest lexical `to` cities is wrong
      input  [["JFK","KUL"],["JFK","NRT"],["NRT","JFK"]]
      expected  ["JFK","NRT","JFK","KUL"]
      wrong: ["JFK","KUL"]

    2. from-to may have more than one same tickets, duplicate edges.
       It is wrong to keep them in Map  from:set<to>
         input: [["JFK","ANU"],["JFK","TIA"],
         ["ANU","EZE"],["EZE","AXA"],["AXA","TIA"],
         ["TIA","ANU"],["TIA","ANU"],["TIA","JFK"],
         ["ANU","JFK"],["ANU","TIA"]]
    3 all tickets should be used out.
  */
  /*---------------------------------------------------------------------------
  Idea: dfs, backtracking
    ( The HashMap permits null values and the null key. but
      it is easy to only control the number, not add/delete entry, thus without update the keySet in loop )
    Time Limit Exceeded
   */

  public List<String> findItinerary___(List<List<String>> tickets) {
    Map<String, Map<String, Integer>> ft = new HashMap();
    for (List<String> ticket : tickets) {
      String f = ticket.get(0), t = ticket.get(1);
      ft.putIfAbsent(f, new HashMap());
      Map<String, Integer> tn = ft.get(f);
      tn.put(t, tn.getOrDefault(t, 0) + 1);
    }
    List<String> rs = new ArrayList();

    String[] tmp = new String[tickets.size() + 1];
    tmp[0] = "JFK";

    dfs__("JFK", ft, tmp, 1, rs);

    if (rs.size() == 1) return Arrays.asList(rs.get(0).split("-"));
    String r = null;
    for (String str : rs) {
      if (r == null || str.compareTo(r) < 0) r = str;
    }
    return Arrays.asList(r.split("-"));
  }
  // The HashMap permits null values and the null key.
  private void dfs__(
      String from, Map<String, Map<String, Integer>> ft, String[] tmp, int size, List<String> rs) {
    if (size == tmp.length) {
      rs.add(String.join("-", tmp));
      return;
    }
    if (ft.get(from) != null) {
      Map<String, Integer> tn = ft.get(from);
      for (String t : tn.keySet()) {
        // only control the number not add/delete entry to change the keySet in loop
        if (tn.get(t) > 0) {
          tmp[size] = t;
          tn.put(t, tn.get(t) - 1);
          dfs__(t, ft, tmp, size + 1, rs);
          tn.put(t, tn.get(t) + 1);
        }
      }
    }
  }

  /*
    Idea: improvement use greedy

    greedy algorithm does not necessarily lead to a globally optimal solution,
    but rather a reasonable approximation in exchange of less computing time.

    Nonetheless, sometimes it is the way to produce a global optimum for certain problems.
    This is the case for this problem as well.

    At each airport, given a list of possible destinations, while backtracking, at each step
    we would pick the destination greedily in lexical order, the final solution would have the
    smallest lexical order, because all other solutions that have smaller lexical order have
    been trialed and failed during the process of backtracking.

    O(∣E∣^d) where ∣E∣ is the number of total flights and d is the maximum number of flights
            from an airport.
    O(∣V∣+∣E∣) space
  */

  public List<String> findItinerary__(List<List<String>> tickets) {
    Map<String, List<String>> g = new HashMap(); // graph
    for (List<String> ticket : tickets) {
      String f = ticket.get(0), t = ticket.get(1); // from -> to, 1:n
      g.putIfAbsent(f, new ArrayList<>());
      g.get(f).add(t);
    }
    Map<String, List<String[]>> ft = new HashMap();
    for (Map.Entry<String, List<String>> e : g.entrySet()) {
      List<String> l = e.getValue();
      Collections.sort(l);
      List<String[]> tos = new ArrayList<>(l.size());
      for (String to : l) tos.add(new String[] {to, "0"}); // 1 or 0 visited or not
      ft.put(e.getKey(), tos);
    }

    String[] tmp = new String[tickets.size() + 1];
    tmp[0] = "JFK";

    return dfs("JFK", ft, tmp, 1);
  }

  private List<String> dfs(String from, Map<String, List<String[]>> ft, String[] tmp, int size) {
    if (size == tmp.length) return Arrays.asList(tmp);
    if (ft.get(from) == null) return null;
    for (String[] t : ft.get(from)) {
      if (t[1].equalsIgnoreCase("0")) {
        tmp[size] = t[0];
        t[1] = "1";
        List<String> r = dfs(t[0], ft, tmp, size + 1);
        if (r != null) return r;
        t[1] = "0";
      }
    }
    return null;
  }

  /*
  Idea: improvement
  Focus on the part said  above 'all other solutions that have smaller lexical order have
  been trialed and failed during the process of backtracking.'
  with the case
       input  [["JFK","KUL"],["JFK","NRT"],["NRT","JFK"]]
       expected  ["JFK","NRT","JFK","KUL"]

  this part "JFK","KUL" is the last part.
  "You may assume all tickets form at least one valid itinerary.
   You must use all the tickets once and only once."
  So this part is
  - topological sort variant
  - the postorder DFS (Depth-First Search) in a directed graph, from a fixed starting point.

  thus removed visit variable

  This is Hierholzer's algorithm for Eulerian Path

   More importantly, the given input is guaranteed to have a solution,
   Therefore, given a list of flights (i.e. edges in graph),  should find
   an order to use each flight once and only once.

   Before adding an airport into the final itinerary, must have visited
   all its outgoing neighbor vertex.

   start from the fixed starting vertex (airport 'JFK'),
   keep following the ordered and unused edges (flights)
   until get stuck at certain vertex where no more unvisited outgoing edges.
   The point is the last airport. And then backwards

  Alternative of priority queue is use list, then sort the list
   `g.forEach((key, value) -> Collections.sort(value));`

   Time:
   Hierholzer's algorithm is O(E), E is edges number
   But this question need sorting the out edges
   - In the worst case the graph is of star shape,
     the JFK airport would assume half of the flights
     the sorting operation O(NlogN), N=E/2
   - In a less bad /average case, each node has the equal number of outgoing flights.
     each airport would have E/V of flights, all airports have O(V*NlogN),N=E/V
     =O(Elog(E/V))
   Space
    O(V+E) used by graph. The maximum depth of the recursion is E.
  */
  public List<String> findItinerary(List<List<String>> tickets) {
    Map<String, PriorityQueue<String>> g = new HashMap<>();
    List<String> r = new LinkedList();
    for (List<String> ticket : tickets) //  O(ElogE) time? sorting depends on the structure of the input graph.
    g.computeIfAbsent(ticket.get(0), k -> new PriorityQueue()).add(ticket.get(1));
    visit("JFK", g, r); // O(E) time
    return r;
  }

  void visit(String f, Map<String, PriorityQueue<String>> g, List<String> r) {
    while (g.containsKey(f) && !g.get(f).isEmpty()) visit(g.get(f).poll(), g, r);
    r.add(0, f);
  }

  /*
  Idea: improvement
   as no back-tracking. recursion -> iterator with stack,
   but it does not save space
  */
  public List<String> findItinerary_(List<List<String>> tickets) {
    Map<String, PriorityQueue<String>> g = new HashMap<>();
    List<String> r = new LinkedList();
    for (List<String> ticket : tickets) //  O(ElogE) time
    g.computeIfAbsent(ticket.get(0), k -> new PriorityQueue()).add(ticket.get(1));
    Stack<String> s = new Stack<>();
    s.push("JFK");
    while (!s.isEmpty()) {
      while (g.containsKey(s.peek()) && !g.get(s.peek()).isEmpty()) {
        s.push(g.get(s.peek()).poll());
      }
      r.add(0, s.pop());
    }
    return r;
  }
}
