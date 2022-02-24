//  Copyright 2017 The keepTry Open Source Project
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

package celebrity;

public class Leetcode277FindtheCelebrity {
  /*
  Leetcode 77. Find the Celebrity
  Suppose you are at a party with n people (labeled from 0 to n - 1),
  and among them, there may exist one celebrity.
  The definition of a celebrity is that
   - all the other n - 1 people know him/her,
   - but he/she does not know any of them.

  Now you want to find out who the celebrity is or verify that there is not one.
  The only thing you are allowed to do is to ask questions like:
  "Hi, A. Do you know B?" to get information about whether A knows B.
  You need to find out the celebrity (or verify there is not one) by
  asking as few questions as possible (in the asymptotic sense).

  You are given a helper function bool knows(a, b) which
  tells you whether A knows B. Implement a function int findCelebrity(n).
  There will be exactly one celebrity if he/she is in the party.
  Return the celebrity's label if there is a celebrity in the party.
  If there is no celebrity, return -1.

  Input: graph = [[1,1,0],[0,1,0],[1,1,1]]
  Output: 1
  Explanation: There are three persons labeled with 0,
   1 and 2. graph[i][j] = 1 means person i knows person j,
    otherwise graph[i][j] = 0 means person i does not know person j.
    The celebrity is the person labeled as 1
    because both 0 and 2 know him but 1 does not know anybody.

  Input: graph = [[1,0,1],[1,1,0],[0,1,1]]
  Output: -1
  Explanation: There is no celebrity.

  Constraints:
      n == graph.length
      n == graph[i].length
      2 <= n <= 100
      graph[i][j] is 0 or 1.
      graph[i][i] == 1


  Follow up:
  If the maximum number of allowed calls to the API knows is 3 * n,
  could you find a solution without exceeding the maximum number of calls?
  There will be exactly one celebrity if he/she is in the party.
   */
  boolean knows(int a, int b) {
    return true;
  }
  /*---------------------------------------------------------------------------
    graph, where a directed edge going from person A to person B if confirmed that A knows B.
    a celebrity is a person who has exactly n - 1 directed edges going in (everybody knows them)
    and 0 edges going out (they know nobody).
    At the start, we only know the nodes of the graph. The edges are all hidden
    we need to ask now is... was it actually necessary to uncover all of them?
    answer is in working through an example
    where you decide which edges you want to ask for, and then draw them as you go.
    need to know what the full graph behind your example is, or at least
    the important aspects of it, but also need to focus on what information
    you've "uncovered" by using the knows(...) API. ...


   Watch
   1> The definition of a celebrity is that
  - all the other n - 1 people know him/her,
  - but he/she does not know any of them.

   anybody knows him but celebrity does not know anybody.
   2> There will be exactly one celebrity if he/she is in the party.

   Observe
    each call to knows(...), we can conclusively determine that
    exactly 1 of the people is NOT a celebrity:
    if (I know you) then
        I am NOT celebrity
    else (I do not know you) then
        you are NOT celebrity: because there is at most only one celebrity
        if there would be 2 celebrities then this logic will not right.
    if

    So loop the nodes will kick off n-1 nodes and only one node left in unknown status.
    this need N API call
    if the left `I` node is celebrity need match
      I know nobody and anybody knows me
    this need 2*N API call

    So in total need 3*N API call
    O(N) time and O(1) space
  */
  public int findCelebrity(int n) {
    int i = 0;
    for (int y = 1; y < n; y++) {
      if (knows(i, y)) i = y; // I am not celebrity
      // else you are not the celebrity
    }
    // now:
    //  - n-1 people is determined to be not the celebrity
    //  - i is the only candidate now.
    for (int y = 0; y < n; y++) {
      if (i != y && (!knows(y, i) || knows(i, y))) return -1;
      // celebrity known none, every body know celebrity.
    }
    return i;
  }
}
