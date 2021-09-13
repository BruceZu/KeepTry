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

package math.permutation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Leetcode1359CountAllValidPickupandDeliveryOptions {
  /*
   valid Pick And Delivery
   - one pi has only and must have one di to form a pair.
   - pi must be in advance of di
  */

  /*
   1359. Count All Valid Pickup and Delivery Options
   Given n orders, each order consist in pickup and delivery services.
   Count all valid pickup/delivery possible sequences such that
   delivery(i) is always after of pickup(i).

   Since the answer may be too large, return it modulo 10^9 + 7.

   Input: n = 1
   Output: 1
   Explanation: Unique order (P1, D1), Delivery 1 always is after of Pickup 1.



   Input: n = 2
   Output: 6
   Explanation: All possible orders:
                (P1,P2,D1,D2), (P1,P2,D2,D1),
                (P1,D1,P2,D2), (P2,P1,D1,D2),
                (P2,P1,D2,D1), (P2,D2,P1,D1).
   This is an invalid order (P1,D2,P2,D1) because Pickup 2 is after of Delivery 2.

   Input: n = 3
   Output: 90

   1 <= n <= 500
  */

  /*
    Idea:
    watch
        i  result
        ---------------------
        0   0     []
        1   1     [p1, d1]
        2   6     insert p2 into 3 possible place of [p1, d1] and has 3+2+1 =6 result
        3   90    6 *[insert p3 into 5 possible place of one of 2 order [p1 p2 d2 d3], and has
                      5+4+3+2+1=6*5*0.5 = 90
     So
     all possibility result of adding the nth order to a given n-1 order
       = p+(p-1) +...+1
       = (p+1)*p*0.5
       p = 2*(n-1)+1
       Because: n-1 order has 2*(n-1)+1 place to insert the Pickup of the nth order and
                Delivery x always is after of Pickup x.
     in total:
     f(n) = f(n-1) * (p+1)*p*0.5; p = 2*(n-1)+1 = 2n-1;
          = f(n-1) * n * (2*n-1)

  O(N) time, O(1) space
    */
  public static int countOrders(int n) {
    int m = 1000000007;
    long r = 1;
    for (int i = 2; i <= n; i++) r = r * i * (2 * i - 1) % m;
    return (int) r;
  }

  /*
  print all possible path for given n
  n=0. print  null
  n=1. print  p1->d1
  n=2, print  p1 p2 d1 d2; p1 p2 d2 d1; p2 p1 d1 d2; p2 p1 d2 d1; p1 d1 p2 d2; p2 d2 p1 d1
  */
  /*
  Idea:
  permutation:
     valid PickAndDelivery
     for each index of path, options is
     p1, p2, ... pn, d1, d2, ...dn
     for pi: select it if it has not been used.
     for di: select it if di has not been used and pi has been used.
  */
  public static void print(int n) {
    dfs(0, n, new HashSet<>(), new HashSet<>(), new ArrayList<>(2 * n));
  }
  // n is the current location inf path pi is printed pick, di is printed delivery,
  public static void dfs(int cur, int n, Set<Integer> pi, Set<Integer> di, List<String> path) {
    if (path.size() == n * 2) {
      System.out.println(String.join("->", path));
      return;
    }

    for (int i = 1; i <= 2 * n; i++) {
      if (i <= n) {
        if (!pi.contains(i)) {
          pi.add(i);
          String p = "P" + i;
          path.add(p);
          dfs(cur + 1, n, pi, di, path);
          path.remove(p);
          pi.remove(i);
        }
      } else {
        if (pi.contains(i - n) && !di.contains(i)) {
          di.add(i);
          String d = "D" + (i - n);
          path.add(d);
          dfs(cur + 1, n, pi, di, path);
          path.remove(d);
          di.remove(i);
        }
      }
    }
  }
}
