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

package set.treeset;

import java.util.*;

public class Leetcode895MaximumFrequencyStack {
  /*
   Leetcode 895. Maximum Frequency Stack

   Design a stack-like data structure to push elements to the stack and pop the most frequent element from the stack.

   Implement the FreqStack class:

   FreqStack() constructs an empty frequency stack.
   void push(int val) pushes an integer val onto the top of the stack.
   int pop() removes and returns the most frequent element in the stack.
   If there is a tie for the most frequent element, the element closest to the stack's top is removed and returned.

   Input
   ["FreqStack", "push", "push", "push", "push", "push", "push", "pop", "pop", "pop", "pop"]
   [[], [5], [7], [5], [7], [4], [5], [], [], [], []]
   Output
   [null, null, null, null, null, null, null, 5, 7, 5, 4]

   Explanation
   FreqStack freqStack = new FreqStack();
   freqStack.push(5); // The stack is [5]
   freqStack.push(7); // The stack is [5,7]
   freqStack.push(5); // The stack is [5,7,5]
   freqStack.push(7); // The stack is [5,7,5,7]
   freqStack.push(4); // The stack is [5,7,5,7,4]
   freqStack.push(5); // The stack is [5,7,5,7,4,5]
   freqStack.pop();   // return 5, as 5 is the most frequent. The stack becomes [5,7,5,7,4].
   freqStack.pop();   // return 7, as 5 and 7 is the most frequent, but 7 is closest to the top. The stack becomes [5,7,5,4].
   freqStack.pop();   // return 5, as 5 is the most frequent. The stack becomes [5,7,4].
   freqStack.pop();   // return 4, as 4, 5 and 7 is the most frequent, but 4 is closest to the top. The stack becomes [5,7].


   Constraints:

   0 <= val <= 10^9
   At most 2 * 10^4 calls will be made to push and pop.
   It is guaranteed that there will be at least one element in the stack before calling pop.
  */

  /*
  Note Constraints:
    It is guaranteed that there will be at least one element in the stack before calling pop.

  Why not use PriorityQueue:
    update need firstly find it out, PriorityQueue need O(N) time to find a element
  What is the usage of map:
    map value to Entry
  TreeSet sort Entry by order list size and the order list last order value


  O(logN) time, N is distinct value number
  O(p) space, p is push times
  */
  class FreqStack {
    class Entry {
      public int v;
      public List<Integer> orderList = new ArrayList();

      public Entry(int v, int order) {
        this.v = v;
        orderList.add(order);
      }
    }

    TreeSet<Entry> treeSet;
    int order; // current Order
    Map<Integer, Entry> map;

    public FreqStack() {
      treeSet =
          new TreeSet<>(
              (a, b) -> {
                if (a.orderList.size() == b.orderList.size()) {
                  return b.orderList.get(b.orderList.size() - 1)
                      - a.orderList.get(a.orderList.size() - 1);
                }
                return b.orderList.size() - a.orderList.size();
              });
      order = 0;
      map = new HashMap();
    }

    /*
     O(logN) time used by treeSet.remove(e) and add(e);
    */
    public void push(int val) {
      if (map.containsKey(val)) {
        Entry e = map.get(val);
        treeSet.remove(e);
        e.orderList.add(order);
        treeSet.add(e);
      } else {
        Entry e = new Entry(val, order);
        treeSet.add(e);
        map.put(val, e);
      }
      order++;
    }

    /*
    It is guaranteed that there will be at least one element in the stack before calling pop.
    O(logN) time used by treeSet.add(e);
    */
    public int pop() {
      Entry e = treeSet.pollFirst();
      int a = e.v;
      e.orderList.remove(e.orderList.size() - 1);
      if (e.orderList.size() > 0) treeSet.add(e);
      else map.remove(e.v);
      return a;
    }
  }
}

/*
improve above to be O(1) time and O(N) space
      0    1    2    3    4    5
[[], [5], [7], [5], [7], [4], [5], now, [], [], [], []]
-----------
now:
value: index list, a stack
5: 0, 2, 5
7: 1, 3
4: 4

frequency: value list in index order, a stack.
1: 5, 7
2: 5, 7
3: 5
-----------
Need not remove v when its frequency is increased
Need not keep (v: order list),  just (v: frequency number) is enough
need not remove v from (frequency: value list), just keep them there

need a current max frequency variable
need not clean the map entry when the value is 0 or empty stack
*/

class FreqStack {
  Map<Integer, Integer> fre;
  Map<Integer, Stack<Integer>> order;
  int maxFre;

  public FreqStack() {
    fre = new HashMap<>();
    order = new HashMap<>();
    maxFre = 0;
  }

  // O(1) time
  public void push(int val) {
    int f = fre.getOrDefault(val, 0) + 1;
    fre.put(val, f);
    if (f > maxFre) maxFre = f;
    order.computeIfAbsent(f, v -> new Stack()).push(val);
  }

  public int pop() {
    int a = order.get(maxFre).pop();
    if (order.get(maxFre).size() == 0) {
      // order.remove(maxFre);
      maxFre--;
    }
    fre.put(a, fre.get(a) - 1);
    // if (fre.get(a) == 0) fre.remove(a);
    return a;
  }
}
