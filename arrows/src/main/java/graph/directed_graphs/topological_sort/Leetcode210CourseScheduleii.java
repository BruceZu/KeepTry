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

/*
210. Course Schedule II

 a total of numCourses courses, labeled from 0 to numCourses - 1.
 an array prerequisites where prerequisites[i] = [ai, bi] indicates
 must take course bi first if you want to take course ai.

 Return the ordering of courses you should take to finish all courses.
 If there are many valid answers, return any of them.
 If it is impossible to finish all courses, return an empty array.


Input: numCourses = 2, prerequisites = [[1,0]]
Output: [0,1]

Input: numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
Output: [0,2,1,3]

Input: numCourses = 1, prerequisites = []
Output: [0]

Constraints:

    1 <= numCourses <= 2000
    0 <= prerequisites.length <= numCourses * (numCourses - 1)
    prerequisites[i].length == 2
    0 <= ai, bi < numCourses
    ai != bi
    All the pairs [ai, bi] are distinct.
 */
public class Leetcode210CourseScheduleii {
  // DFS ----------------------------------------------------------------------
  public static int[] findOrder_(int numCourses, int[][] prerequisites) {
    List<Integer>[] out = new ArrayList[numCourses];
    for (int i = 0; i < numCourses; i++) out[i] = new ArrayList();
    for (int[] e : prerequisites) out[e[1]].add(e[0]);

    List<Integer> r = new LinkedList();
    Map<Integer, Boolean> v = new HashMap<>();
    // start from any node
    for (int n = 0; n < numCourses; n++) if (dfs(n, out, v, r)) return new int[0];

    int[] a = new int[numCourses];
    for (int i = 0; i < numCourses; i++) a[i] = r.get(i);
    return a;
  }

  // calculate topological order from node n, return true if circle is found
  private static boolean dfs(int n, List<Integer>[] out, Map<Integer, Boolean> v, List<Integer> r) {
    if (v.containsKey(n)) return v.get(n);
    v.put(n, true);
    for (int follow : out[n]) if (dfs(follow, out, v, r)) return true;
    v.put(n, false);
    r.add(0, n);
    return false;
  }

  // BFS --------------------------------------------------------------
  public static int[] findOrder(int numCourses, int[][] prerequisites) {
    List<Integer>[] out = new ArrayList[numCourses];
    int[] in = new int[numCourses];
    for (int i = 0; i < numCourses; i++) out[i] = new ArrayList();
    for (int[] e : prerequisites) {
      out[e[1]].add(e[0]);
      in[e[0]]++;
    }

    Queue<Integer> q = new LinkedList();
    for (int i = 0; i < numCourses; i++) if (in[i] == 0) q.offer(i);

    int[] r = new int[numCourses];
    int size = 0;

    while (!q.isEmpty()) {
      int n = q.poll();
      r[size++] = n;
      for (int o : out[n]) if (--in[o] == 0) q.offer(o);
    }
    return size != numCourses ? new int[0] : r;
  }
}
