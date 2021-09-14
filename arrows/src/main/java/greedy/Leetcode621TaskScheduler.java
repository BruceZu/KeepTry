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

package greedy;

public class Leetcode621TaskScheduler {

  /*
   621. Task Scheduler

    Given a characters array tasks, representing the tasks a CPU needs to do,
    where each letter represents a different task. Tasks could be done in any order.
    Each task is done in one unit of time. For each unit of time,
    the CPU could complete either one task or just be idle.

    However, there is a non-negative integer n that represents the cooldown
    period between two same tasks (the same letter in the array), that is that
    there must be at least n units of time between any two same tasks.

    Return the least number of units of times that the CPU will take to
    finish all the given tasks.


    Example 1:

    Input: tasks = ["A","A","A","B","B","B"], n = 2
    Output: 8
    Explanation:
    A -> B -> idle -> A -> B -> idle -> A -> B
    There is at least 2 units of time between any two same tasks.

    Example 2:

    Input: tasks = ["A","A","A","B","B","B"], n = 0
    Output: 6
    Explanation: On this case any permutation of size 6 would work since n = 0.
    ["A","A","A","B","B","B"]
    ["A","B","A","B","A","B"]
    ["B","B","B","A","A","A"]
    ...
    And so on.

    Example 3:

    Input: tasks = ["A","A","A","A","A","A","B","C","D","E","F","G"], n = 2
    Output: 16
    Explanation:
    One possible solution is
    A -> B -> C -> A -> D -> E -> A -> F -> G -> A -> idle -> idle -> A -> idle -> idle -> A


    Constraints:

        1 <= task.length <= 104
        tasks[i] is upper-case English letter.
        The integer n is in the range [0, 100].

  */
  /*
  Task Scheduler
  Watch and observe: need cool-down time unit or not

    O(N) time, Need not sort. Only need to know the max frequency and number of it
    O(1) space.
   */

  // return the least number of units of times that the CPU
  // will take to finish all the given tasks.
  public static int leastInterval(char[] tasks, int cooldownPeirods) {
    int[] f = new int[26];
    for (int t : tasks) f[t - 'A']++;
    int max = 0, max_n = 0; // Max frequency, number of tasks with max frequency
    for (int i = 25; i >= 0; i--) if (f[i] > max) max = f[i];
    for (int i = 25; i >= 0; i--) if (f[i] == max) max_n++;

    int N = tasks.length; // Total tasks
    if (max_n - 1 >= cooldownPeirods) return N;

    int is = max - 1; // intervals
    int holes = is * (cooldownPeirods - (max_n - 1)); // planned cooldown holes
    int otherTasks = N - max_n * max;
    if (holes <= otherTasks) return N; // otherTasks use out all planned cooldown  holes
    return holes - otherTasks + N; //
  }
}
