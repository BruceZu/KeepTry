//  Copyright 2016 The Sawdust Open Source Project
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

package nosubmmitted;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Leetcode281ZigzagIterator {
  /*
    Leetcode 281. Zigzag Iterator

    Given two vectors of integers v1 and v2, implement an iterator to return their elements alternately.

    Implement the ZigzagIterator class:

    ZigzagIterator(List<int> v1, List<int> v2) initializes the object with the two vectors v1 and v2.
    boolean hasNext() returns true if the iterator still has elements, and false otherwise.
    int next() returns the current element of the iterator and moves the iterator to the next element.

    Input: v1 = [1,2],
           v2 = [3,4,5,6]
    Output: [1,3,2,4,5,6]
    Explanation: By calling next repeatedly until hasNext returns false,
    the order of elements returned by next should be: [1,3,2,4,5,6].


    Input: v1 = [1],
           v2 = []
    Output: [1]


    Input: v1 = [],
           v2 = [1]
    Output: [1]


    Constraints:

    0 <= v1.length, v2.length <= 1000
    1 <= v1.length + v2.length <= 2000
    -231 <= v1[i], v2[i] <= 231 - 1


    Follow up: What if you are given k vectors? How well can your code be extended to such cases?

    Clarification for the follow-up question:

    The "Zigzag" order is not clearly defined and is ambiguous for k > 2 cases.
    If "Zigzag" does not look right to you, replace "Zigzag" with "Cyclic".

    Follow-up Example:

    Input: v1 = [1,2,3],
           v2 = [4,5,6,7],
           v3 = [8,9]
    Output: [1,4,8,2,5,9,3,6,7]
  */

  /*
  Your ZigzagIterator object will be instantiated and called as such: ZigzagIterator i = new
  ZigzagIterator(v1, v2); while (i.hasNext()) v[f()] = i.next();
  */
  class ZigzagIterator {
    private Iterator<Integer> b, a, tmp;

    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
      a = v1.iterator();
      b = v2.iterator();
    }

    public int next() {
      if (a.hasNext()) {
        tmp = a;
        a = b;
        b = tmp;
      }
      return b.next();
    }

    public boolean hasNext() {
      return b.hasNext() || a.hasNext();
    }
  }
}

class ZigzagIterator__ {
  /*
  K is the number of input lists
   next():  O(K) time
   hasNext():  O(1) time

  O(K) space
  */
  private List<List<Integer>> lists = new ArrayList<>();
  // pointer to list, and element
  private int pLists = 0, pElem = 0;
  private int total = 0, o = 0; // output counts

  public ZigzagIterator__(List<Integer> v1, List<Integer> v2) {
    lists.add(v1);
    lists.add(v2);
    for (List<Integer> l : this.lists) {
      total += l.size();
    }
  }

  public int next() {
    if (!hasNext()) throw new RuntimeException("No data anymore");
    Integer next = null;

    int visited = 0;
    while (visited < lists.size()) {
      List<Integer> il = lists.get(pLists);
      if (pElem < il.size()) {
        next = il.get(pElem);
        this.o += 1;
      }

      pLists = (pLists + 1) % lists.size();
      if (pLists == 0) pElem++;
      if (next != null) return next;

      visited++;
    }
    // visited all lists and not next.
    return 0; // never come here
  }

  public boolean hasNext() {
    return this.o < this.total;
  }
}

class ZigzagIterator_ {

  private List<List<Integer>> lists = new ArrayList<>();
  private LinkedList<int[]> dq = new LinkedList<>();

  public ZigzagIterator_(List<Integer> v1, List<Integer> v2) {
    lists.add(v1);
    lists.add(v2);

    int i = 0;
    for (List<Integer> l : lists) {
      if (l.size() > 0)
        //  index_to_list, index_to_element_within_list
        dq.add(new int[] {i, 0});
      i++;
    }
  }

  public int next() {
    // if (this.queue.size() == 0)
    // throw new Exception("Out of elements!");

    //  index_to_list, index_to_element_within_list
    int[] is = dq.removeFirst();
    Integer li = is[0];
    Integer ei = is[1];
    Integer ei_ = ei + 1;
    // append
    if (ei_ < lists.get(li).size()) dq.addLast(new int[] {li, ei_});

    return lists.get(li).get(ei);
  }

  public boolean hasNext() {
    return dq.size() > 0;
  }
}

/*
 Dequeue + Iterator
*/
class ZigzagIterator {
  private LinkedList<Iterator<Integer>> dq = new LinkedList<>();

  public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
    if (v1.size() > 0) dq.add(v1.iterator());
    if (v2.size() > 0) dq.add(v2.iterator());
  }

  public int next() {
    Iterator<Integer> i = dq.removeFirst();
    int v = i.next();
    if (i.hasNext()) dq.addLast(i);
    return v;
  }

  public boolean hasNext() {
    return dq.size() > 0;
  }
}
