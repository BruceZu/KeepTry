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

package dfs;

import java.util.*;

public class ReadingBook {

  /*
  As you are reading the book multiple times, you notice that you always get bad endings.
  You start to suspect there is no way to get to the good endings, so you decide to find out.

  Good and bad endings will be separate lists of page numbers, like this:

  good_endings = [10, 15, 25, 34]
  bad_endings =  [21, 30, 40]

  Given lists of good endings, bad endings, and a list of the choices along with their options,
  return a collection of all the reachable good endings, if any.

  Examples:

  choices1 = [[3, 16, 24]]
  find_good_endings(good_endings, bad_endings, choices1)
    Start: 1 -> 2 -> 3(choice) -> 16 -> 17... -> 21(bad ending)
                     |
                     -> 24 -> 25(good ending)
  Thus it is possible to reach the good ending at 25 but no others, so we return [25].

  choices2 = [[3, 16, 20]]
  find_good_endings(good_endings, bad_endings, choices2)
    Start: 1 -> 2 -> 3(choice) -> 16 -> 17... -> 21(bad ending)
                     |
                     > 20 -> 21(bad ending)
  No good ending is reachable, so return [].


  Complexity Variable:
  n = number of pages
  (endings and choices are bound by the number of pages)

  */

  /*
  Runtime O(n)
  Space(M+N+n) M is used for endings,  N is used for choices.  n is pages, for visited page
  */
  static Set<Integer> ans;

  public static Set<Integer> find_good_endings(
      int[] goodEndings, int[] badEndings, int[][] choices) {
    if (goodEndings == null || goodEndings.length == 0) return new HashSet<>();
    // assume choices can be empty, not null
    ans = new HashSet<>();
    Map<Integer, Boolean> vis = new HashMap<>();

    Set<Integer> good = new HashSet<>();
    for (int end : goodEndings) good.add(end);

    Set<Integer> bad = new HashSet<>();
    for (int end : badEndings) bad.add(end);

    Map<Integer, int[]> map = new HashMap<>();
    for (int[] c : choices) map.put(c[0], c);

    dfs(1, vis, good, bad, map);
    System.out.println(ans);
    return ans;
  }
  // return -1 if find a loop
  private static void dfs(
      int p,
      Map<Integer, Boolean> vis,
      Set<Integer> good,
      Set<Integer> bad,
      Map<Integer, int[]> map) {
    if (good.contains(p)) {
      ans.add(p);
      return;
    }
    if (bad.contains(p)) return;
    if (vis.containsKey(p)) return; // visited or find a loop: stop continue

    vis.put(p, true); // ---
    if (map.containsKey(p)) { // check both choice
      dfs(map.get(p)[1], vis, good, bad, map);
      dfs(map.get(p)[2], vis, good, bad, map);
    } else {
      dfs(p + 1, vis, good, bad, map);
    }
    vis.put(p, false); // --- always restore
  }

  public static void main(String[] argv) {
    /*
    All Test Cases - snake_case:
    find_good_endings(good_endings, bad_endings, choices1) => [25]
    find_good_endings(good_endings, bad_endings, choices2) => []
    find_good_endings(good_endings, bad_endings, choices3) => [34]
    find_good_endings(good_endings, bad_endings, choices4) => [10]
    find_good_endings(good_endings, bad_endings, choices5) => [15, 34]
    find_good_endings(good_endings, bad_endings, choices6) => [15, 25, 34]
    find_good_endings(good_endings, bad_endings, choices7) => [10]
    */
    int[] good_endings = {10, 15, 25, 34};
    int[] bad_endings = {21, 30, 40};

    int[][] choices1 = {{3, 16, 24}};
    int[][] choices2 = {{3, 16, 20}};
    int[][] choices3 = {{3, 2, 19}, {20, 21, 34}};
    int[][] choices4 = {};
    int[][] choices5 = {{9, 16, 26}, {14, 16, 13}, {27, 29, 28}, {28, 15, 34}, {29, 30, 38}};
    int[][] choices6 = {{9, 16, 26}, {13, 31, 14}, {14, 16, 13}, {27, 12, 24}, {32, 34, 15}};
    int[][] choices7 = {{3, 9, 10}};

    find_good_endings(good_endings, bad_endings, choices1);
    find_good_endings(good_endings, bad_endings, choices2);
    find_good_endings(good_endings, bad_endings, choices3);
    find_good_endings(good_endings, bad_endings, choices4);
    find_good_endings(good_endings, bad_endings, choices5);
    find_good_endings(good_endings, bad_endings, choices6);
    find_good_endings(good_endings, bad_endings, choices7);
  }
}
