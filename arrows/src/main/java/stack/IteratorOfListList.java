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

package stack;

import java.util.*;

public class IteratorOfListList {
  public static List<List<Integer>> getInput() {
    List<List<Integer>> list = new ArrayList<>();
    list.add(new ArrayList<>());
    list.add(new ArrayList<>(Arrays.asList(1, 2, 3)));
    list.add(new ArrayList<>(Arrays.asList(4, 5)));
    list.add(new ArrayList<>());
    list.add(new ArrayList<>());
    list.add(new ArrayList<>(Collections.singletonList(6)));
    list.add(new ArrayList<>(Arrays.asList(7, 8)));
    list.add(new ArrayList<>());
    list.add(new ArrayList<>(Collections.singletonList(9)));
    list.add(new ArrayList<>(Collections.singletonList(10)));
    list.add(new ArrayList<>());

    return list;
  }
}
/*
Given a list of lists, implement an iterator class to allow the client
to traverse and remove elements in the list.
This iterator should provide three public class member functions

The code should be well structured, and robust enough to handle any access pattern.
Additionally, write code to demonstrate that the class can be used for
the following basic scenarios:

        Print elements

        Given: [[],[1,2,3],[4,5],[],[],[6],[7,8],[],[9],[10],[]]
        Print: 1 2 3 4 5 6 7 8 9 10
        Remove even elements

        Given: [[],[1,2,3],[4,5],[],[],[6],[7,8],[],[9],[10],[]]
        Should result in: [[],[1,3],[5],[],[],[],[7],[],[9],[],[]]
        Print: 1 3 5 7 9

input is List<List<Integer>>
design a class implement Iterator interface
boolean hasNext()
return true if there is another element in the whole structure

int next()
return the value of the next element in the structure

void remove()
remove the last element returned by the iterator.
That is, remove the element that the previous next() returned

This method can be called only once per call to next(),
otherwise an exception will be thrown.
*/

/*
 without use Iterator
*/
class Solution_ {
  List<List<Integer>> data; // Can not consumed in memory
  int li, i; // point to next element but not know if it is valid
  List<Integer> l;

  // for remove. keep tracking of the last returned element
  int _i;
  List<Integer> _l;
  boolean called = false;

  public Solution_(List<List<Integer>> input) {
    this.data = input;
    li = 0;
    i = 0;
    _i = -1;
  }

  boolean hasNext() {
    // possible to move to valid li and i? maybe could not find valid element so not change the li
    // and i;
    // check li, skip all empty list
    int t = li;
    while (t < data.size() && data.get(t).size() == 0) t++;
    if (t == data.size()) return false;
    // now t index list is a no empty list;
    if (t == li) {
      if (i < l.size()) return true;
      else { // cur list is used out
        t++;
        while (t < data.size() && data.get(t).size() == 0) t++;
        if (t == data.size()) return false;
        return true; // again now t index list is a no empty list;
      }
    }
    return true;
  }

  int next() {
    if (!hasNext()) throw new RuntimeException("No next");
    // sure next element exists
    int t = li;
    while (t < data.size() && data.get(t).size() == 0) t++;

    if (t == li) {
      if (i >= l.size()) {
        t++;
        while (t < data.size() && data.get(t).size() == 0) t++;
        // sure next element exists
        li = t;
        l = data.get(li);
        i = 0;
      } // else current index i is valid
    } else {
      li = t;
      i = 0;
      l = data.get(li);
    }

    _l = l;
    _i = i;
    called = false;
    return l.get(i++);
  }

  /*
  Removes from the underlying collection the last element returned by this iterator (optional operation). This method can be called only once per call to next.
  The behavior of an iterator is unspecified if the underlying collection is modified while the iteration is in progress in any way other than by calling this method, unless an overriding class has specified a concurrent modification policy.
  The behavior of an iterator is unspecified if this method is called after a call to the forEachRemaining method.
  Throws:
  UnsupportedOperationException – if the remove operation is not supported by this iterator
  IllegalStateException – if the next method has not yet been called, or the remove method has already been called after the last call to the next method
  Implementation Requirements:
  The default implementation throws an instance of UnsupportedOperationException and performs no other action.
     */
  void remove() {
    if (_l != null && _i != -1 && !called) {
      _l.remove(_i);
      i--;
      called = true;
    }
  }

  public static void main(String[] args) throws Exception {
    List<List<Integer>> input = IteratorOfListList.getInput();
    Solution s = new Solution(input);
    while (s.hasNext()) {
      int v = s.next();
      System.out.println(v);
      if ((v & 1) == 1) s.remove();
    }

    System.out.println("Toatal lists: " + input.size()); // 11
    for (List<Integer> l : input) {
      System.out.println(l);
    }
  }
}

/*
use Iterator
*/
class Solution {
  Iterator<List<Integer>> is; // Iterator(s)
  Iterator<Integer> i; // null or non-empty Iterator

  public Solution(List<List<Integer>> input) {
    is = input.iterator();
  }

  private void findNonEmptyI() {
    if (i == null || !i.hasNext()) {
      while (is.hasNext()) {
        List<Integer> t = is.next();
        if (t.size() == 0) continue; // skip empty list

        i = t.iterator();
        break;
      }
    }
    // not found
  }

  boolean hasNext() {
    findNonEmptyI();
    return i != null && i.hasNext();
  }

  int next() {
    if (!hasNext()) throw new RuntimeException("has not next");
    return i.next();
  }

  // Use iterator
  void remove() {
    i.remove();
  }
  // test ---------------------------------------------------------------------
  public static void main(String[] args) throws Exception {
    List<List<Integer>> input = IteratorOfListList.getInput();
    Solution s = new Solution(input);
    while (s.hasNext()) {
      int v = s.next();
      System.out.println(v);
      if ((v & 1) == 1) s.remove();
    }

    System.out.println(input.size() == 11);
    for (List<Integer> l : input) {
      System.out.println(l);
    }
  }
}
