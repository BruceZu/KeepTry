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

public class Leetcode284PeekingIterator {}

/*
 284. Peeking Iterator

  Design an iterator that supports the peek operation on an existing iterator
  in addition to the hasNext and the next operations.

  Implement the PeekingIterator class:

  PeekingIterator(Iterator<int> nums) Initializes the object with the given integer iterator iterator.
  int next() Returns the next element in the array and moves the pointer to the next element.
  boolean hasNext() Returns true if there are still elements in the array.
  int peek() Returns the next element in the array without moving the pointer.
  Note: Each language may have a different implementation of the constructor and Iterator,
  but they all support the int next() and boolean hasNext() functions.


  Input
  ["PeekingIterator", "next", "peek", "next", "next", "hasNext"]
  [[[1, 2, 3]], [], [], [], [], []]
  Output
  [null, 1, 2, 2, 3, false]

  Explanation
  PeekingIterator peekingIterator = new PeekingIterator([1, 2, 3]); // [1,2,3]
  peekingIterator.next();    // return 1, the pointer moves to the next element [1,2,3].
  peekingIterator.peek();    // return 2, the pointer does not move [1,2,3].
  peekingIterator.next();    // return 2, the pointer moves to the next element [1,2,3]
  peekingIterator.next();    // return 3, the pointer moves to the next element [1,2,3]
  peekingIterator.hasNext(); // return False


  Constraints:

  1 <= nums.length <= 1000
  1 <= nums[i] <= 1000
  All the calls to next and peek are valid.
  At most 1000 calls will be made to next, hasNext, and peek.


  Follow up: How would you extend your design to be generic and work with all types, not just integer?
*/
// Java Iterator interface reference:
// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html

class PeekingIterator implements Iterator<Integer> {
  Iterator<Integer> it;
  Integer peeked;

  public PeekingIterator(Iterator<Integer> iterator) {
    it = iterator;
    peeked = null;
  }

  // Returns the next element in the iteration without advancing the iterator.
  public Integer peek() {
    if (peeked != null) return peeked;
    if (it.hasNext()) {
      peeked = it.next();
    }
    return peeked;
  }

  // hasNext() and next() should behave the same as in the Iterator interface.
  // Override them if needed.
  @Override
  public Integer next() {
    if (hasNext()) {
      Integer r = peek();
      peeked = null;
      return r;
    }
    return null;
  }

  @Override
  public boolean hasNext() {
    return peek() != null;
  }
}

/*
Followup MergingIterator

       constructor - O(K*logK) - where K - number of lists(iterators)
       hasNext() - O(1) speed
       next() - O(logK) - where K - number of lists(iterators)

       Space O(n), map and queue for storing iterators.
*/

class MergingIterator implements Iterator<Integer> {
  private Map<Iterator<Integer>, Integer> map;
  private Queue<Iterator<Integer>> q;

  public MergingIterator(List<Iterator<Integer>> iterators) {
    map = new HashMap<>();
    q = new PriorityQueue<>(Comparator.comparingInt(map::get));
    for (Iterator<Integer> i : iterators) {
      if (i.hasNext()) {
        map.put(i, i.next());
        q.add(i);
      }
    }
  }

  public boolean hasNext() {
    return !q.isEmpty();
  }

  public Integer next() {
    if (!hasNext()) {
      throw new NoSuchElementException("Iterator is empty");
    }
    Iterator<Integer> i = q.poll();
    Integer ans = map.get(i);
    if (i.hasNext()) {
      map.put(i, i.next());
      q.add(i);
    }
    return ans;
  }
}
