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

/** <a href="https://leetcode.com/problems/course-schedule/?tab=Description">LeetCode</a> */
public class Leetcode207CourseSchedule {

  /*
  Leetcode  207. Course Schedule

    There are a total of numCourses courses you have to take,
    labeled from 0 to numCourses - 1.

    You are given an array prerequisites where prerequisites[i] = [ai, bi]
    indicates that you must take course bi first if you want to take course ai.

    For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.

    Return true if you can finish all courses. Otherwise, return false.

    Input: numCourses = 2, prerequisites = [[1,0]]
    Output: true
    Explanation: There are a total of 2 courses to take.
    To take course 1 you should have finished course 0. So it is possible.


    Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
    Output: false
    Explanation: There are a total of 2 courses to take.
    To take course 1 you should have finished course 0,
    and to take course 0 you should also have finished course 1. So it is impossible.


    Constraints:

    1 <= numCourses <= 105
    0 <= prerequisites.length <= 5000
    prerequisites[i].length == 2
    0 <= ai, bi < numCourses
    All the pairs prerequisites[i] are unique.
  */

  // O(V + E)
  public static boolean canFinish__BFS_V_E(int numCourses, int[][] prerequisites) {
    List<Integer>[] iToFollowings = new List[numCourses];
    int[] preNumbersOf = new int[numCourses];
    // O(E)
    for (int[] iToPre : prerequisites) {
      int preCourse = iToPre[1];
      int course = iToPre[0];
      List<Integer> followings = iToFollowings[preCourse];
      if (followings == null) {
        followings = new LinkedList<>();
        iToFollowings[preCourse] = followings;
      }
      followings.add(course);
      preNumbersOf[course]++;
    }

    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
      if (preNumbersOf[i] == 0) queue.offer(i);
    }
    int count = 0;
    // O(V+E)
    while (!queue.isEmpty()) {
      int course = queue.poll();
      count++;
      List<Integer> followings = iToFollowings[course];
      if (followings == null) continue;
      for (int follow : followings) {
        preNumbersOf[follow]--;
        if (preNumbersOf[follow] == 0) queue.offer(follow);
      }
    }
    return count == numCourses;
  }

  // ----------------------------------------------------------------------------------------------------------------
  // prerequisites: many array of [i, pre]
  public static boolean canFinish_DFS(int numCourses, int[][] prerequisites) {
    List<Integer>[] out = new ArrayList[numCourses];
    for (int i = 0; i < numCourses; i++) { // O(V)
      out[i] = new ArrayList();
    }

    for (int[] iToPre : prerequisites) { // O(E)
      out[iToPre[1]].add(iToPre[0]); // out:  pre->i
    }

    boolean[] vis = new boolean[numCourses];
    // try from each course to see if there is circle
    // when a loop start from a course is over the vis is back to its original status
    // so need NOT re-initialize it
    for (int c = 0; c < numCourses; c++) { // DFS  O(V+E)
      if (hasCircle(c, out, vis)) {
        return false;
      }
    }
    return true;
  }

  // i is course ID
  private static boolean hasCircle(int i, List<Integer>[] out, boolean[] visited) {
    if (visited[i]) return true;
    visited[i] = true;
    for (int o : out[i]) {
      if (hasCircle(o, out, visited)) return true;
    }
    visited[i] = false;
    return false;
  }

  // ----------------------------------------------------------------------------------------------------------------
  public static void main(String[] args) {
    int numCourse = 4;
    int[][] pres = new int[][] {{1, 0}, {2, 1}, {1, 3}, {0, 2}};
    System.out.println(canFinish_DFS(numCourse, pres));

    numCourse = 4;
    pres = new int[][] {{0, 1}, {1, 2}, {2, 3}, {3, 1}};
    System.out.println(canFinish_DFS(numCourse, pres));

    numCourse = 2;
    pres = new int[][] {{0, 1}, {1, 0}};
    System.out.println(canFinish_DFS(numCourse, pres));
  }
}
