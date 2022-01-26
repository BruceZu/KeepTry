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

public class Leetcode341FlattenNestedListIterator {}

/*
  Leetcode 341. Flatten Nested List Iterator

  You are given a nested list of integers nestedList.
  Each element is either an integer or a list whose elements may also be integers
  or other lists. Implement an iterator to flatten it.

  Implement the NestedIterator class:

  NestedIterator(List<NestedInteger> nestedList) Initializes the iterator with the nested list nestedList.
  int next() Returns the next integer in the nested list.
  boolean hasNext() Returns true if there are still some integers in the nested list and false otherwise.
  Your code will be tested with the following pseudocode:

  initialize iterator with nestedList
  res = []
  while iterator.hasNext()
      append iterator.next() to the end of res
  return res
  If res matches the expected flattened list, then your code will be judged as correct.


  Input: nestedList = [[1,1],2,[1,1]]
  Output: [1,1,2,1,1]
  Explanation: By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,1,2,1,1].

  Input: nestedList = [1,[4,[6]]]
  Output: [1,4,6]
  Explanation: By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,4,6].


  Constraints:

  1 <= nestedList.length <= 500
  The values of the integers in the nested list is in the range [-106, 106].
*/

/*
Stack<List<NestedInteger>>
it needs another stack to tracking index
*/

class NestedIterator_ implements Iterator<Integer> {
  public interface NestedInteger {

    boolean isInteger();
    /*
    @return the single integer that this NestedInteger holds, if it holds a single integer.
     Return null if this NestedInteger holds a nested list
     */
    Integer getInteger();

    /* @return the nested list that this NestedInteger holds, if it holds a nested list.
    Return empty list if this NestedInteger holds a single integer
    */
    List<NestedInteger> getList();
  }

  private Stack<List<NestedInteger>> s = new Stack<>();
  private Stack<Integer> index = new Stack<>();

  public NestedIterator_(List<NestedInteger> nestedList) {
    s.push(nestedList);
    index.push(0); // peek index is peek list current integer index
  }

  @Override
  public Integer next() {
    if (!hasNext()) throw new NoSuchElementException();
    int i = index.pop();
    index.push(i + 1);
    return s.peek().get(i).getInteger();
  }

  @Override
  public boolean hasNext() {
    findIntegerIndex();
    return !index.isEmpty();
  }

  private void findIntegerIndex() {
    while (!index.isEmpty()) {
      if (index.peek() >= s.peek().size()) { // use out
        index.pop();
        s.pop();
        continue;
      }

      if (s.peek().get(index.peek()).isInteger()) {
        break;
      }

      // a list
      s.push(s.peek().get(index.peek()).getList());
      index.push(index.pop() + 1); // point to next unknow
      index.push(0);
    }
  }
}

/*
Idea: use Java Iterator
use Stock<Iterator> not Stock<List>

N be the total number of integers within the nested list
L be the total number of lists within the nested list
D be the maximum nesting depth (maximum number of lists inside each other).


 Constructor  O(1) time
 next() / hasNext(): O( L/N ) or O(1)  time
 O(D) Space
*/

class NestedIterator implements Iterator<Integer> {
  public interface NestedInteger {

    boolean isInteger();
    /*
    @return the single integer that this NestedInteger holds, if it holds a single integer.
     Return null if this NestedInteger holds a nested list
     */
    Integer getInteger();

    /* @return the nested list that this NestedInteger holds, if it holds a nested list.
    Return empty list if this NestedInteger holds a single integer
    */
    List<NestedInteger> getList();
  }

  private Stack<Iterator<NestedInteger>> stack = new Stack<>();
  private Integer v = null;
  boolean removed = false;

  public NestedIterator(List<NestedInteger> input) {
    // creating a list iterator is an O(1) operation.
    stack.push(input.iterator());
  }

  private void setNext() {
    if (v != null) return; // next value

    while (!stack.isEmpty()) {
      if (!stack.peek().hasNext()) {
        stack.pop();
        continue;
      }
      // peek has next
      NestedInteger out = stack.peek().next();

      // If it's an integer, set peeked to it and return as we're done.
      if (out.isInteger()) {
        v = out.getInteger();
        return;
      }

      stack.push(out.getList().iterator());
    }
  }

  @Override
  public Integer next() {
    if (!hasNext()) throw new NoSuchElementException();
    Integer ans = v;
    v = null; // it is turned
    removed = false;
    return ans;
  }

  @Override
  public boolean hasNext() {
    setNext();
    return v != null;
  }

  /*
   only after hasNext()-> next() then know it is integer or list
   so must call next() to get the value out; if hasnext() is true
   no way to keep the value in its list.
   But remove() is just after next() that means the value's iterator is the peek in stack
  */
  @Override
  public void remove() {
    if (!removed) {
      stack.peek().remove();
      removed = true;
    }
  }
}
