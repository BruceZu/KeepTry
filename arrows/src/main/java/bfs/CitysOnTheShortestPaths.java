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

package bfs;

import java.util.*;

public class CitysOnTheShortestPaths {
  /*
   A dasher sometimes travels between cities.
   To avoid delays, the dasher first checks for the shortest routes.
   Given a map of the cities and their bidirectional roads represented by
   a graph of nodes and edges,
   determine which given roads are along any shortest path.
   Return an array of strings, one for each road in order, where the value
   is YES if the road is along one shortest path
   or NO if it is not.
   The roads or cities are named using their 1-based index within the input arrays.

   Example
   given a map of g_nodes = 5 nodes, the starting nodes, ending nodes and road lengths are:
   Road from/to/weight
   1   (1,  2,  1)
   2   (2,  3,  1)
   3   (3,  4,  1)
   4   (4,  5,  1)
   5   (5,  1,  3)
   6   (1,  3,  2)
   7   (5,  3,  1)

   Example Input: (5, [1, 2, 3, 4, 5, 1, 5],
                      [2, 3, 4, 5, 1, 3, 3],
                      [1, 1, 1, 1, 3, 2, 1])
   The traveller must travel from city 1 to city g_nodes, so from node 1 to node 5 in this case.
   The shortest path is 3 units long and there are three paths of that length:
    1 → 5, 1 → 2 → 3 → 5, and 1 → 3 → 5.
   Return an array of strings, one for each road in order, where
   the value is YES if a road is along a shortest path or NO if it is not.
   In this case, the resulting array is
   ['YES', 'YES', 'NO', 'NO', 'YES', 'YES', 'YES'].
   The third and fourth roads connect nodes (3, 4) and (4, 5) respectively.
   They are not on a shortest path, i.e. one with a length of 3 in this case.
  */

  /*
  Assume road weight is positive

  Maybe there are more than one shortest path
  graph:  Map<from_city, Set<[ to_city, weight, road_id] >>
  1 Dijkstra's algorithm get the length of the shortest path
     queue<[city_id, shortest_weigh from start city to here]
     visited set<city_id>
  2 DFS try all possible roads, tracking the Set< road_id>
    if it is one of the shortest paths then make roads[road_id]="YES"

  O(V+E) time and space
  */

  public static boolean[] shortestPath(int cityNum, int[] fromCitys, int[] toCitys, int[] weights) {
    int start = 1, end = cityNum, roads = fromCitys.length;

    Map<Integer, List<int[]>> gMap = new HashMap<>();
    for (int i = 0; i < roads; i++) {
      int roadId = i + 1;
      int from = fromCitys[i];
      int to = toCitys[i];
      int weight = weights[i];

      gMap.computeIfAbsent(from, k -> new ArrayList<>()).add(new int[] {to, weight, roadId});
      gMap.computeIfAbsent(to, k -> new ArrayList<>()).add(new int[] {from, weight, roadId});
    }

    // [city ID, start->current city weight sum]
    Queue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
    Set<Integer> visit = new HashSet<>();
    visit.add(start);
    q.offer(new int[] {start, 0});

    int L = 0; // shortest Path Weigh
    while (!q.isEmpty()) {
      int[] cur = q.poll();
      if (cur[0] == end) {
        L = cur[1];
        break;
      }
      for (int[] next : gMap.get(cur[0])) {
        if (!visit.contains(next[0])) {
          visit.add(next[0]);
          q.offer(new int[] {next[0], cur[1] + next[1]});
        }
      }
    }

    boolean[] res = new boolean[roads];
    dfs(start, end, gMap, L, res, new HashSet<>());
    return res;
  }

  private static void dfs(
      int city, int end, Map<Integer, List<int[]>> g, int L, boolean[] res, Set<Integer> roadIDs) {
    if (L < 0) return;
    if (city == end && L == 0) {
      for (int rid : roadIDs) res[rid - 1] = true;
      // The roads or cities are named using their 1-based index
      return;
    }

    for (int[] to : g.get(city)) {
      if (!roadIDs.contains(to[2])) {
        roadIDs.add(to[2]);
        dfs(to[0], end, g, L - to[1], res, roadIDs);
        roadIDs.remove(to[2]);
      }
    }
  }
  // ----------------------------------------------------------------
  public static void main(String[] args) {
    // ['YES', 'YES', 'NO', 'NO', 'YES', 'YES', 'YES'].
    System.out.println(
        Arrays.toString(
            shortestPath(
                5,
                new int[] {1, 2, 3, 4, 5, 1, 5},
                new int[] {2, 3, 4, 5, 1, 3, 3},
                new int[] {1, 1, 1, 1, 3, 2, 1})));
  }
}
