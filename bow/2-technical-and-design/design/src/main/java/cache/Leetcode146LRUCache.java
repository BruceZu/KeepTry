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

package cache;

import java.util.HashMap;

/*
 Leetcode 146. LRU Cache

Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.

Implement the LRUCache class:

    LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
    int get(int key) Return the value of the key if the key exists, otherwise return -1.
    void put(int key, int value) Update the value of the key if the key exists.
                                 Otherwise, add the key-value pair to the cache.
                                 If the number of keys exceeds the capacity from this operation,
                                 evict the least recently used key.

The functions get and put must each run in O(1) average time complexity.

Input
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
Output
[null, null, null, 1, null, -1, null, -1, 3, 4]

Explanation
LRUCache lRUCache = new LRUCache(2);
lRUCache.put(1, 1); // cache is {1=1}
lRUCache.put(2, 2); // cache is {1=1, 2=2}
lRUCache.get(1);    // return 1
lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
lRUCache.get(2);    // returns -1 (not found)
lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
lRUCache.get(1);    // return -1 (not found)
lRUCache.get(3);    // return 3
lRUCache.get(4);    // return 4

["LRUCache","get","put","get","put","put","get","get"]
[[2],        [2],  [2,6],[1],  [1,5],[1,2], [1], [2]]
[null,        -1,   null, -1,   null,  null, 2,   6]

Constraints:

    1 <= capacity <= 3000
    0 <= key <= 104
    0 <= value <= 105
    At most 2 * 105 calls will be made to get and put.
 */

public class Leetcode146LRUCache {
  // Solution: LRUCache-> LRU_LinkedHashMap or LRU_LinkedHashMap2 ( best )
}

/*
Understand:
   'get(key) - Get the value (will always be positive) of the key if the key exists in the cache,
    otherwise return -1.'
    This means the value of k-v should not be -1.
*/
/*
Idea
 with HashMap and a customized Double Linked List
 Double Linked List design and Node design
 - used to trace the least recently used k-v
   when capacity is full delete the least recently used k-v, also need
   delete it from HashMap. so need keep key in customized Double Linked List
   So at lest it looks like
   IDoubleList<Node<k>>
   HashMap<k,v>

 - when a k-v is visited more than once
   need from HashMap get the Node of customized Double Linked List to unlink it in O(1)
   and link it to recent location.
   And HashMap need know the v of k-v
   so the Node need keep key and Value and used by HashMap and customized IDoubleList
      IDoubleList<Node<k,v>>
      HashMap<k,Node<k,v>>

  -  Like LinkedList the IDoubleList has sentinel/dumb head and tail [old,...recent]
     to make operations on the both sides, addNew(), removeOld(), in O(1) time

  - the key and value of node
    make sure the input does not have null key and value, the null be used as
    'not found' in searchedBy
       'get(key) - Get the value (will always be positive) of the key if the key
       exists in the cache, otherwise return -1.'
    This means the value of k-v should not be -1.


 By the way, this kind of data structure are most implemented in LinkedHashMap.
 LinkedHashMap has a
    static class Entry<K,V> extends HashMap.Node<K,V> {
        Entry<K,V> before, after;
        Entry(int hash, K key, V value, Node<K,V> next) {
            super(hash, key, value, next);
        }
    }

Refer to the implementation based on  LinkedHashMap in Leetcode146LRU_LinkedHashMap.java
 */
class LRUCache {
  private int capacity;
  private HashMap<Integer, Node> map;
  private IDoubleList<Integer, Integer> list;

  public LRUCache(int capacity) {
    this.capacity = capacity;
    map = new HashMap();
    list = new IDoubleList();
  }

  public int get(int key) {
    if (!map.containsKey(key)) return -1; // required by Leetcode

    Node<Integer, Integer> n = map.get(key);
    list.visit(n);
    return n.v;
  }

  public void put(int key, int value) {
    if (map.containsKey(key)) {
      map.get(key).v = value;
      list.visit(map.get(key));
      return; // must return
    }
    if (map.size() == capacity) map.remove(list.removeOld().k);

    Node newNode = new Node(key, value);
    map.put(key, newNode);
    list.addNew(newNode);
  }
}
/*
double linked list, with it to know which should be removed
[old, ..., recent]
*/
class IDoubleList<K, V> {

  private Node<K, V> old;
  private Node<K, V> recent;

  private void remove(Node<K, V> n) {
    Node<K, V> pre = n.pre;
    Node<K, V> next = n.next;

    pre.next = next;
    next.pre = pre;
  }

  public IDoubleList() {
    old = new Node<>();
    recent = new Node<>();

    old.next = recent;
    recent.pre = old;
  }

  Node<K, V> removeOld() {
    Node<K, V> removed = old.next;
    remove(removed);
    return removed;
  }

  void addNew(Node<K, V> n) {
    Node<K, V> pre = recent.pre;
    Node<K, V> next = recent;

    pre.next = n;
    n.next = next;

    next.pre = n;
    n.pre = pre;
  }

  void visit(Node<K, V> n) {
    remove(n);
    addNew(n);
  }
}

class Node<K, V> {
  Node pre;
  Node next;
  K k;
  V v;
  // used only for sentinel head and tail
  Node() {}

  public Node(K key, V value) {
    this.k = key;
    this.v = value;
    this.pre = null;
    this.next = null;
  }
}
