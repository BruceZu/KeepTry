//  Copyright 2022 The KeepTry Open Source Project
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

package intervals;

import java.util.*;

public class Leetcode1235MaximumProfitinJobScheduling {
  /*
   1235. Maximum Profit in Job Scheduling


    We have n jobs, where every job is scheduled to be done from startTime[i] to endTime[i],
    obtaining a profit of profit[i].

    You're given the startTime, endTime and profit arrays, return the maximum profit you can
    take such that there are no two jobs in the subset with overlapping time range.

    If you choose a job that ends at time X you will be able to start another job that starts at time X.

    Input: startTime = [1,2,3,3], endTime = [3,4,5,6], profit = [50,10,40,70]
    Output: 120
    Explanation: The subset chosen is the first and fourth job.
    Time range [1-3]+[3-6] , we get profit of 120 = 50 + 70.

    Input: startTime = [1,2,3,4,6], endTime = [3,5,10,6,9], profit = [20,20,100,70,60]
    Output: 150
    Explanation: The subset chosen is the first, fourth and fifth job.
    Profit obtained 150 = 20 + 70 + 60.

    Input: startTime = [1,1,1], endTime = [2,3,4], profit = [5,6,4]
    Output: 6

    Constraints:

        1 <= startTime.length == endTime.length == profit.length <= 5 * 10^4
        1 <= startTime[i] < endTime[i] <= 109
        1 <= profit[i] <= 104

           [ 24, 24,  7, 2,  1, 13,  6, 14, 18, 24 ]
           [ 27, 27, 20, 7, 14, 22, 20, 24, 19, 27 ]
           [  6,  1,  4, 2,  3,  6,  5,  6,  9,  8 ]
          output: 20

          [ 15, 44, 15, 47, 11, 18,  5, 41, 38, 25, 19, 25 ]
          [ 33, 48, 20, 49, 37, 22, 32, 48, 39, 37, 38, 40 ]
          [ 18, 19, 16,  1,  5, 12, 17,  7, 19,  9, 18,  9 ]
          output: 63

          [ 1, 1, 1 ]
          [ 2, 3, 4 ]
          [ 5, 6, 4 ]
          output: 6

           [  1,  2,  3,  3]
           [  3,  4,  5,  6]
           [ 50, 10, 40, 70]
           output: 120

          [ 1,  2,   3,  4,  6]
          [ 3,  5,  10,  6,  9]
          [20, 20, 100, 70, 60]
          output: 150

      [6,  15,  7, 11,  1, 3, 16,  2]
      [19, 18, 19, 16, 10, 8, 19,  8]
      [2,   9,  1, 19,  5, 7,  3, 19]
      output: 41

      [  2   3]
      [  8,  8]
      [ 19,  7]
      output: 19
  */
  /*
    Idea:
       order call has 3 elements: [start time, end, volume]
       sort order call by start time, then loop:
       Each call has 2 options: accept or ignore.
       memory cache for avoid recalculating repeated subproblems.

   Code details
     - cache[i] maximum profit of jobs [i, N-1] are 50000
     - do not use Arrays.binarySearch() which'
      If the range contains multiple elements with the specified value,
      there is no guarantee which one will be found.'
     - next: next non-conflicting job index

     O(NlogN) time It is not (2^N) time
     O(N) space
  */

  int[] cache = new int[50001];

  private static int findNextJob(int[] S, int t) {
    int l = 0, r = S.length - 1;
    while (l <= r) {
      int m = (l + r) >>> 1;
      if (S[m] < t) l = m + 1;
      else r = m - 1;
    }
    return l;
  }
  // N*2LogN. O(NlogN) binary search O(logN) time
  private int findMaxProfit(List<List<Integer>> jobs, int[] S, int n, int i) {
    if (i == n) return 0;
    if (cache[i] != -1) return cache[i];

    int next = findNextJob(S, jobs.get(i).get(1));
    int a =
        Math.max(
            findMaxProfit(jobs, S, n, i + 1), jobs.get(i).get(2) + findMaxProfit(jobs, S, n, next));

    return cache[i] = a;
  }

  public int jobScheduling___(int[] S, int[] E, int[] P) {
    List<List<Integer>> jobs = new ArrayList<>();

    Arrays.fill(cache, -1);

    int N = P.length;
    for (int i = 0; i < N; i++) {
      ArrayList<Integer> j = new ArrayList<>();
      j.add(S[i]);
      j.add(E[i]);
      j.add(P[i]);
      jobs.add(j);
    }
    Collections.sort(jobs, Comparator.comparingInt(a -> a.get(0)));
    Arrays.sort(S);
    return findMaxProfit(jobs, S, N, 0);
  }

  /*
   To avoided recursion call stack
   use iterative manner, starts from lost job which is the base case
  */
  private int findMaxProfit(List<List<Integer>> jobs, int[] S) {
    int N = S.length;
    for (int i = N - 1; i >= 0; i--) {
      int next = findNextJob(S, jobs.get(i).get(1));
      int currProfit = jobs.get(i).get(2) + (next != N ? cache[next] : 0);
      cache[i] = (i == N - 1) ? currProfit : Math.max(currProfit, cache[i + 1]);
    }
    return cache[0];
  }

  public int jobScheduling__(int[] S, int[] E, int[] P) {
    List<List<Integer>> jobs = new ArrayList<>();
    int length = P.length;
    for (int i = 0; i < length; i++) {
      ArrayList<Integer> j = new ArrayList<>();
      j.add(S[i]);
      j.add(E[i]);
      j.add(P[i]);
      jobs.add(j);
    }

    jobs.sort(Comparator.comparingInt(a -> a.get(0)));
    Arrays.sort(S);
    return findMaxProfit(jobs, S);
  }

  /* ==========================================================================
  for each call:
    the end time volume value =max(current value, max{ each previous finished call volume + current volume})

  O(N^2) time Limit Exceeded
  because the above inner max{} has duplicated calculation
  How to avoid the duplication?
  */
  /*
  Watch the picture of https://leetcode.com/problems/maximum-profit-in-job-scheduling/:
    concept: Let the order call end time point keep the current possible max volume by the time point
             of all possible handled no-conflicting order calls chains.
    no overlap:
  end time point(max)           5(20)      7(40)        9(60)
           order call     ==20==      ==20==       ==20==

    overlap:
  end time point(max)           5    6(30)   7(20 ignore)      9(50)
           order call     ==30=======
                               ==20==========            ==20==

  end time point(max)           5    6(20)   7(30)          9(50)
           order call     ==20=======
                               ==30==========         ==20==

    Find:
      1.  keep all (end time, current max profit ty the end time) in end time ascending order in TreeMap `tree`
          for current call (s, e, p)
          now check  v=tree.floor(s)+p with
          - f = tree.floor(e)
          - tree.higher(e): if order call with end time. then tree has not tree.higher(e) and
                            the treeLastEntry(e) is the tree.floor(e)
          to decide ignore current call or add it in tree and used by handling follow call
          all tree element need keep:  end time 1<end time 2, max1<max2
          So:
            sort calls with end time
            if v > tree.treeLastEntry().getValue() then add (end time,v)


     Note: duplicated call with same end time
                        t(30)
                ===20===
                  =30===
            or
                  =30===
                ===20===
            or
                                        5(30)   7(20)  9(50)
                                                 x
                           ==30==========
              ==20==============================
                                                  ==20==

   O(NlogN) time, O(N)space
     */
  public static int jobScheduling_(int[] S, int[] E, int[] P) {
    int N = P.length;
    int[][] a = new int[N][3];
    for (int i = 0; i < N; i++) a[i] = new int[] {S[i], E[i], P[i]};
    Arrays.sort(a, Comparator.comparingInt(x -> x[1]));
    TreeMap<Integer, Integer> tree = new TreeMap<>();
    tree.put(a[0][1], a[0][2]);
    for (int i = 1; i < N; i++) {
      int start = a[i][0], end = a[i][1], max = a[i][2];
      Map.Entry<Integer, Integer> e = tree.floorEntry(start);
      if (e != null) max += e.getValue();
      if (tree.lastEntry().getValue() < max)
        tree.put(end, max); // may be lastEntry has the same end time
    }
    return tree.lastEntry().getValue();
  }
  /* --------------------------------------------------------------------------
    max{ each previous finished call volume + current volume}
    = max{ each previous finished call volume} + current volume
    If got and record the max value and use it in handling next call.
    The duplicated part can be avoided, -> O(NlogN) time with min heap<end time, profit> sorted by end time
    accessing chains that have earlier end times and removing them from the min heap and tracing the max profit

  the Question can be imagined as
  - startTime and endTime is a link in the chain
  - each link in the chain has a profit associated with it,
  - goal is to make the most profitable no-conflicting chain.

  similar with the Longest Increasing Subsequence, but now the goal is
  to maximize the profit instead of length.

  Code details
  q:  min heap having {endTime, profit} sorted by endTime
  max:  keep popping while the heap is not empty and tracing max
        calls are not conflicting
        max will also be used in handling next call

  O(NlogN) time O(N)space
  */

  public int jobScheduling(int[] S, int[] E, int[] P) {
    int N = P.length;
    int[][] a = new int[N][3];
    for (int i = 0; i < N; i++) a[i] = new int[] {S[i], E[i], P[i]};
    Arrays.sort(a, Comparator.comparingInt(x -> x[0]));

    int max = 0;
    Queue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(x -> x[0]));

    for (int i = 0; i < N; i++) {
      int start = a[i][0], end = a[i][1], v = a[i][2];
      while (!q.isEmpty() && q.peek()[0] <= start) max = Math.max(max, q.poll()[1]);
      q.add(new int[] {end, v + max});
    }
    while (!q.isEmpty()) max = Math.max(max, q.poll()[1]);
    return max;
  }
}
