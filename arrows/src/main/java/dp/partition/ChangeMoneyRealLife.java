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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChangeMoneyRealLife {
  // Combination. With unLimited Coins.
  // runtime O(?)
  public static int[] allSmallestChanges(int[] coins, int k) {
    //Todo: corner case check
    //Todo:
    return null;
  }

  // Combination. With Limited Coins.
  // runtime O(n^2*S)
  // concern: for sum 's', there are more than one subsets.
  // these subsets are not same as from the combination view
  public static Set<Map<Integer, Integer>> smallestChangesLimited(int[] coins, int[] copys, int S) {
    //Todo: corner case check
    boolean[] T = new boolean[S + 1];
    T[0] = true;
    Set<Map<Integer, Integer>>[] r = new HashSet[S + 1];

    int R = 0; // out of all loop
    for (int i = 0; i < coins.length; i++) {
      for (int s = R; s >= 0; s--) {
        if (T[s]) {
          markTargetSumStatusAndChanges(s, i, coins, copys, T, r);
        }
      }
      R = Math.min(R + coins[i] * copys[i], S);
    }
    return r[S];
  }

  private static void markTargetSumStatusAndChanges(
      int s, int i, int[] coins, int[] copys, boolean[] T, Set<Map<Integer, Integer>>[] r) {
    for (int curCopyNum = 1;
        curCopyNum <= copys[i] && (s + coins[i] * curCopyNum < T.length);
        curCopyNum++) {
      int targetSum = s + coins[i] * curCopyNum;
      T[targetSum] = true;

      // calculate r[s + coins[i] * n]
      int targetSCN = 0; // current target sum's coin number
      if (r[targetSum] != null) {
        targetSCN =
            r[targetSum].stream().findAny().get().values().parallelStream().reduce(0, Integer::sum);
      }
      //
      if (r[targetSum] == null) {
        r[targetSum] = new HashSet<>();
      }

      //
      int SCN = curCopyNum; // coin number of changes of current sum 's'.
      if (s == 0) {
        if (targetSCN != 0 && SCN > targetSCN) continue;
        Map<Integer, Integer> changes = new HashMap<>();
        // changes for target sum with smallest coins
        changes.put(coins[i], curCopyNum);
        needUpdaeTargetSumChanges(targetSCN, targetSum, curCopyNum, changes, r);
      } else {
        SCN += r[s].stream().findAny().get().values().parallelStream().reduce(0, Integer::sum);
        if (targetSCN != 0 && SCN > targetSCN) continue;
        for (Map<Integer, Integer> change : r[s]) {
          Map<Integer, Integer> clone = new HashMap<>(change);
          clone.put(coins[i], curCopyNum); //  changes for target sum with smallest coins
          needUpdaeTargetSumChanges(targetSCN, targetSum, SCN, clone, r);
        }
      }
    }
  }

  private static void needUpdaeTargetSumChanges(
      int targetSCN,
      int targetSum,
      int SCN,
      Map<Integer, Integer> change,
      Set<Map<Integer, Integer>>[] r) {
    if (targetSCN == 0) {
      r[targetSum].add(change);
    } else {
      if (SCN == targetSCN) {
        r[targetSum].add(change);
      } else if (SCN < targetSCN) {
        r[targetSum] = new HashSet<>();
        r[targetSum].add(change);
      }
    }
  }

  public static void main(String[] args) {
    int[] coins = new int[] {4, 3, 6, 5, 7, 1};
    int[] copys = new int[] {3, 4, 2, 1, 1, 1};
    int S = 13;
    print(coins, copys, S);

    coins = new int[] {2, 4, 3, 5, 6, 7, 8, 9};
    copys = new int[] {1, 1, 1, 2, 1, 1, 1, 1};
    S = 10;
    print(coins, copys, S);

    coins = new int[] {2, 3, 5, 7};
    copys = new int[] {1, 1, 1, 2};
    S = 11;
    print(coins, copys, S);

    coins = new int[] {2, 3, 5, 7};
    copys = new int[] {7, 7, 7, 7};
    S = 13;
    print(coins, copys, S);

    coins = new int[] {2, 4, 6, 8};
    copys = new int[] {7, 7, 7, 7};
    S = 12;
    print(coins, copys, S);
  }

  private static void print(int[] coins, int[] copys, int S) {
    Set<Map<Integer, Integer>> r = smallestChangesLimited(coins, copys, S);
    if (r == null) {
      System.out.print(
          "(Combination) Changes with smallest number of coins of " + S + " : no solution\n");
      return;
    }
    for (Map<Integer, Integer> combination : r) {
      System.out.print("(Combination) Changes with smallest number of coins of " + S + " :");
      for (Map.Entry kv : combination.entrySet()) {
        System.out.print("  " + kv.getKey() + ":" + kv.getValue() + ", ");
      }
      System.out.println();
    }
  }
}
