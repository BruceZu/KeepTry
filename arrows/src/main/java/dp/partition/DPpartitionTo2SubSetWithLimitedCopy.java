//  Copyright 2017 The KeepTry Open Source Project
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

package dp.partition;

public class DPpartitionTo2SubSetWithLimitedCopy {

  /**
   * if each number has limited copies.
   * <pre>
   * limited supply of various coins.
   * is it possible to looking for a subset of the coins that make up K.
   * K = half the total value. or any other number.
   */
  // running time O(nS).
  public static boolean canChangeWithLimitedCoins(int[] v /*values*/, int[] c /*copys*/) {
    // corner case check
    int n = v.length;
    int allMoney = 0;
    for (int i = 0; i < n; i++) allMoney += v[i] * c[i];
    if ((allMoney % 2) != 0) return false;

    boolean[] T = new boolean[10240];
    T[0] = true;

    int r = 0;
    for (int i = 0; i < n; i++) { // each number
      for (int si = r; si >= 0; si--) {
        if (T[si]) { // from each valid sum
          for (int copy = 1; copy <= c[i] && si + copy * v[i] <= allMoney / 2; copy++) {
            if (T[si + copy * v[i]]) break; // => The total running time is then O(nS).
            T[si + copy * v[i]] = true;
            if (si + copy * v[i] == allMoney / 2) return true;
          }
        }
      }
      r = Math.min(allMoney / 2, r + v[i] * c[i]);
    }
    return T[allMoney / 2]; // false
  }

  public static void main(String[] args) {
    System.out.println(canChangeWithLimitedCoins(new int[] {1, 2}, new int[] {2, 2}));
    System.out.println(canChangeWithLimitedCoins(new int[] {1, 2}, new int[] {3, 2}));
  }
}
