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

package tree.tree_map;

import java.util.*;

public class Leetcode716MaxStack {}
/*
716. Max Stack

Design a max stack data structure that supports the
stack operations and supports finding the stack's maximum element.

Implement the MaxStack class:

    MaxStack() Initializes the stack object.
    void push(int x) Pushes element x onto the stack.
    int pop() Removes the element on top of the stack and returns it.
    int top() Gets the element on the top of the stack without removing it.
    int peekMax() Retrieves the maximum element in the stack without removing it.
    int popMax() Retrieves the maximum element in the stack and removes it.
                 If there is more than one maximum element, only remove the top-most one.


Input
["MaxStack", "push", "push", "push", "top", "popMax", "top", "peekMax", "pop", "top"]
[[], [5], [1], [5], [], [], [], [], [], []]
Output
[null, null, null, null, 5, 5, 1, 5, 1, 5]

Explanation
MaxStack stk = new MaxStack();
stk.push(5);   // [5] the top of the stack and the maximum number is 5.
stk.push(1);   // [5, 1] the top of the stack is 1, but the maximum is 5.
stk.push(5);   // [5, 1, 5] the top of the stack is 5, which is also the maximum, because it is the top most one.
stk.top();     // return 5, [5, 1, 5] the stack did not change.
stk.popMax();  // return 5, [5, 1] the stack is changed now, and the top is different from the max.
stk.top();     // return 1, [5, 1] the stack did not change.
stk.peekMax(); // return 5, [5, 1] the stack did not change.
stk.pop();     // return 1, [5] the top of the stack and the max element is now 5.
stk.top();     // return 5, [5] the stack did not change.



Constraints:
    -107 <= x <= 107
    At most 104 calls will be made to push, pop, top, peekMax, and popMax.
    There will be at least one element in the stack when pop, top, peekMax, or popMax is called.


Follow up: Could you come up with a solution that
 supports
 O(1) for each top call and
 O(logn) for each other call?
 */

/* ----------------------------------------------------------------------------
Idea
using a TreeSet<Node> to mock the stack
The is Node is a bound of `index, v`?
Note:

0 1 2 3
5 1 5 2
Now index is 4
and if now popMax()
0 1   3
5 1   2
If index is updated to be 3.
Now if push a new one, the new one will
not be added to stack as the stack implementation TreeSet is using
index as key and the Node<3,2> hold the key 3, new Node<3,x> will be discard.
So in this solution,
- need not update index for popMax()
- not keep index. its value is decided by
  `int index = setStack.isEmpty() ? 0 : setStack.last().index + 1;`

 O(1) for each top call and
 O(logN) for each other call

 */
class MaxStack_ {
  static class Node {
    int index, v;

    public Node(int index, int v) {
      this.index = index;
      this.v = v;
    }
  }
  // -----------------------------------------------------------------------------

  // value:  node(s) with duplicated value
  // idea comes from HashMap
  TreeMap<Integer, List<Node>> mapMax;
  TreeSet<Node> setStack;

  public MaxStack_() {
    mapMax = new TreeMap();
    setStack = new TreeSet<>(Comparator.comparingInt(a -> a.index));
  }

  public void push(int v) {
    int index = setStack.isEmpty() ? 0 : setStack.last().index + 1; // Note here
    Node n = new Node(index, v);
    setStack.add(n); // O(logN）

    mapMax.putIfAbsent(v, new ArrayList<>());
    mapMax.get(v).add(n); // O(1）
  }

  public int pop() {
    int v = setStack.pollLast().v; // O(logN）

    List<Node> l = mapMax.get(v);
    l.remove(l.size() - 1); // O(1）
    // 'If there is more than one maximum element, only remove the top-most one.'
    if (l.isEmpty()) mapMax.remove(v);

    return v;
  }

  public int top() {
    return setStack.last().v; // O(1）
  }

  public int peekMax() {
    return mapMax.lastKey(); // O(logN）
    // Note it is last key. natural ordered
  }

  public int popMax() {
    List<Node> l = mapMax.lastEntry().getValue(); // O(logN）
    Node n = l.remove(l.size() - 1);
    if (l.isEmpty()) mapMax.pollLastEntry();

    setStack.remove(n); // O(logN）

    return n.v;
  }
}

/*
 - Use  DoubleLinkedList to mock stack;
 push() pop() and top() operate at the tail of the DoubleLinkedList

 - Why not use LinkedList? when popMax(), no way to do unlink with LinkedList in O(1) time
 LinkedList has LinkedList.Node<E> first, last;
 Invariant: (first == null && last == null) || (first.prev == null && first.item != null)
            (first == null && last == null) || (last.next == null && last.item != null)
 but its inner node is not available

 - In LRU or LFU. LinkedList cannot be used there too because
 it not only used to trace the least recently visited item by operate the both side.
 but also when k-v is visited again k-v need be unlinked from the LinkedList and link to recently location.
 LinkedHashMap wrap HashMap and DoubledLinkedList together
*/
class MaxStack {
  TreeMap<Integer, List<Node>> maxMap; // value:  node(s) with duplicated value
  DoubleLinkedList stack;

  public MaxStack() {
    maxMap = new TreeMap();
    stack = new DoubleLinkedList();
  }

  public void push(int v) {
    Node node = stack.add(v);
    maxMap.putIfAbsent(v, new ArrayList<>());
    maxMap.get(v).add(node);
  }

  public int pop() {
    int v = stack.pop();
    List<Node> l = maxMap.get(v);
    l.remove(l.size() - 1);
    if (l.isEmpty()) maxMap.remove(v);
    return v;
  }

  public int top() {
    return stack.peek();
  }

  public int peekMax() {
    return maxMap.lastKey(); // Note it is last key. natural ordered
  }

  public int popMax() {
    int maxv = peekMax();
    List<Node> l = maxMap.get(maxv);
    Node node = l.remove(l.size() - 1);
    // 'If there is more than one maximum element, only remove the top-most one.'
    if (l.isEmpty()) maxMap.remove(maxv);

    stack.unlink(node);
    return maxv;
  }
  // -----------------------------------------------------------------------------
  static class Node {
    int v;
    Node pre, next;

    public Node(int v) {
      this.v = v;
    }
  }
  // -----------------------------------------------------------------------------
  static class DoubleLinkedList {
    Node head, tail;

    public DoubleLinkedList() {
      head = new Node(0);
      tail = new Node(0);
      head.next = tail;
      tail.pre = head;
    }

    public Node add(int v) {
      Node n = new Node(v);
      n.next = tail; // add to tail of double linked list to mock stack
      n.pre = tail.pre;
      tail.pre = tail.pre.next = n;
      return n;
    }

    public int pop() {
      return unlink(tail.pre).v;
    }

    public int peek() {
      return tail.pre.v;
    }

    public Node unlink(Node node) {
      node.pre.next = node.next;
      node.next.pre = node.pre;
      return node;
    }
  }
}
