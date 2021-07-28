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

package recursion;

import java.util.ArrayList;
import java.util.List;

public class Leetcode247StrobogrammaticNumberII {

  /*
  Given an integer n, return all the strobogrammatic numbers that
  are of length n. You may return the answer in any order.

   A strobogrammatic number is a number that looks the same when
   rotated 180 degrees (looked at upside down).

    1 <= n <= 14
    */

  /*
  Idea
  candidate number is 0,1,6,8,9
  watch: they must be used in symmetry locations
     0: should not be the first number if n>1
     6, and 9, one of them is used in the symmetry location of other's
     1,8 use themself on the symmetry location
  need a recursion to keep the intermedia status

  try each candidate number for location from 0 to (n-1)/2.
  O(5^N) time.
  O(N) space
  */
  public List<String> findStrobogrammatic(int n) {
    String[] cs = new String[] {"0", "1", "8", "6", "9"};
    String[] r = new String[n];
    List<String> a = new ArrayList();
    h(n, 0, cs, r, a);
    return a;
  }

  private void h(int n, int from, String[] cs, String[] cur, List<String> r) {
    if (from > n - 1 >>> 1) {
      r.add(String.join("", cur));
      return;
    }

    int map = n - 1 - from;
    for (String c : cs) {
      if (c == "0" && from == 0 && n > 1) continue;
      if ((c == "6" || c == "9") && map == from) continue;
      cur[from] = c;
      if (c == "6") cur[map] = "9";
      else if (c == "9") cur[map] = "6";
      else cur[map] = c;
      h(n, from + 1, cs, cur, r);
    }
  }
}
