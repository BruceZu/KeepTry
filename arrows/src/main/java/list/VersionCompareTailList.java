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

package list;

import java.util.Stack;

public class VersionCompareTailList {
  /*
  Question 1
  compare 2 version in string
    "2.3.0" == "2.3"
    "2.3.1"  > "2.3"
  Need append zero to make same length which can be applied in loop
  */
  public int compare(String v1, String v2) {
    if (v1 == null && v2 == null) return 1;
    if (v1 == null) return -1;
    if (v2 == null) return 1;

    String[] p1 = v1.split("\\.", -1);
    String[] p2 = v2.split("\\.", -1);
    for (int i = 0; i < p1.length || i < p2.length; i++) {
      int n1 = i < p1.length ? Integer.valueOf(p1[i]) : 0;
      int n2 = i < p2.length ? Integer.valueOf(p2[i]) : 0;
      if (n1 < n2) return -1;
      if (n1 > n2) return 1;
    }
    return 0;
  }

  /*---------------------------------------------------------------------------*/
  static class Node {
    public Node next;
    public int v;

    public Node(int v, Node next) {
      this.v = v;
      this.next = next;
    }
  }
  /*
   Question 2
      1-> 2 -> 3
      5-> 8 -> 1->2->3
      output 2, the o-based index of one list in the another list as the suffix
      1-> 2 -> 3
      5-> 8 -> 1->2->3 -> 8
      output null: one list must be tail of another list

   Need append zero to make same length which can be applied in loop

  */
  // O(N) space and time
  public static Integer oneIsSuffix(Node a, Node b) {
    if (a == null || b == null) return null;
    Stack<Integer> as = new Stack<>(), bs = new Stack<>();
    int la = 0, lb = 0;
    while (a != null || b != null) {
      if (a != null) {
        as.push(a.v);
        a = a.next;
        la++;
      }
      if (b != null) {
        bs.push(b.v);
        b = b.next;
        lb++;
      }
    }
    while (!as.isEmpty() && !bs.isEmpty() && as.peek() == bs.peek()) {
      as.pop();
      bs.pop();
      la--;
      lb--;
    }
    if (as.isEmpty() || bs.isEmpty()) {
      return as.isEmpty() ? lb : la;
    }
    return null;
  }
}
