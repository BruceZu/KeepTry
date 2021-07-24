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

package sort;

import java.util.*;

public class Leetcode1169InvalidTransactions {

  /*

    invalid transaction
    -  the amount exceeds $1000, or;
    -  if it occurs within (and including) 60 minutes of another transaction with the same name in a different city.

    array of strings transaction
    transactions[i] consists of comma-separated values representing the name, time (in minutes), amount, and city of the transaction.
    Return a list of transactions that are possibly invalid. You may return the answer in any order.


    transactions.length <= 1000
    Each transactions[i] takes the form
      "{name},{time},{amount},{city}"
    Each {name} and {city} consist of lowercase English letters, and have lengths between 1 and 10.
    Each {time} consist of digits, and represent an integer between 0 and 1000.
    Each {amount} consist of digits, and represent an integer between 0 and 2000.

  */
  /*
  Understand:
    the second condition meaning:
    for the case:
      ["alice,20,800,mtv","alice,50,100,mtv","alice,51,100,frankfurt"]
    The first transaction is also invalid
    So It is O(N^2) time

   Note:
    need convert to int when compare time
    do not delete invalid items in the t[][]

  O(N^2) time
  O(N) space
  */
  public static List<String> invalidTransactions_(String[] tr) {
    if (tr == null || tr.length == 0) return null;
    int N = tr.length;
    String[][] t = new String[N][5];
    for (int i = 0; i < N; i++) {
      t[i] = new String[5];
      System.arraycopy(tr[i].split(","), 0, t[i], 0, 4);
      t[i][4] = tr[i];
    }
    Arrays.sort(
        t,
        (a, b) -> {
          if (a[0].equals(b[0])) return Integer.valueOf(a[1]) - Integer.valueOf(b[1]);
          return a[0].compareTo(b[0]);
        });
    boolean[] b = new boolean[N];
    for (int i = 0; i < N; i++) {
      if (Integer.valueOf(t[i][2]) > 1000) b[i] = true;
      for (int j = i - 1; j >= 0; j--) {
        if (t[i][0].equalsIgnoreCase(t[j][0])
            && Integer.valueOf(t[i][1]) - Integer.valueOf(t[j][1]) <= 60) {
          if (!t[i][3].equals(t[j][3])) {
            b[i] = true;
            b[j] = true;
          }
        } else break;
      }
    }
    List<String> r = new LinkedList();
    for (int i = 0; i < N; i++) if (b[i]) r.add(t[i][4]);
    return r;
  }
}
