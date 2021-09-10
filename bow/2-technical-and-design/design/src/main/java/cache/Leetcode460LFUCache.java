//  Copyright 2017 The keepTry Open Source Project
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

import java.util.*;

public class Leetcode460LFUCache {
  // Solution: LFUCache-> LFUCache2->LFUCache3 ( best )
}
/*
460. LFU Cache

Design and implement a data structure for a Least Frequently Used (LFU) cache.

Implement the LFUCache class:

    LFUCache(int capacity)
       Initializes the object with the capacity of the data structure.
    int get(int key)
       Gets the value of the key if the key exists in the cache.
       Otherwise, returns -1.
    void put(int key, int value)
       Update the value of the key if present,
       or inserts the key if not already present.
       When the cache reaches its capacity,
       it should invalidate and remove the
       LFU (least frequently used) key before inserting a new item. For this problem,
       when there is a tie (i.e., two or more keys with the same frequency), the
       LRU (least recently used) key would be invalidated.

To determine the least frequently used key, a use counter is maintained for each key in the cache.
The key with the smallest use counter is the least frequently used key.

When a key is first inserted into the cache, its use counter is set to 1 (due to the put operation).
The use counter for a key in the cache is incremented either a get or put operation is called on it.

The functions get and put must each run in O(1) average time complexity.


Input
["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]
Output
[null, null, null, 1, null, -1, 3, null, -1, 3, 4]

Explanation
// cnt(x) = the use counter for key x
// cache=[] will show the last used order for tiebreakers (leftmost element is  most recent)
LFUCache lfu = new LFUCache(2);
lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
lfu.get(1);      // return 1
                 // cache=[1,2], cnt(2)=1, cnt(1)=2
lfu.put(3, 3);   // 2 is the LFU key because cnt(2)=1 is the smallest, invalidate 2.
                 // cache=[3,1], cnt(3)=1, cnt(1)=2
lfu.get(2);      // return -1 (not found)
lfu.get(3);      // return 3
                 // cache=[3,1], cnt(3)=2, cnt(1)=2
lfu.put(4, 4);   // Both 1 and 3 have the same cnt, but 1 is LRU, invalidate 1.
                 // cache=[4,3], cnt(4)=1, cnt(3)=2
lfu.get(1);      // return -1 (not found)
lfu.get(3);      // return 3
                 // cache=[3,4], cnt(4)=1, cnt(3)=3
lfu.get(4);      // return 4
                 // cache=[3,4], cnt(4)=2, cnt(3)=3

Constraints:

    0 <= capacity <= 104
    0 <= key <= 105
    0 <= value <= 109
    At most 2 * 105 calls will be made to get and put.

 */
/*
Understanding
  'The use counter for a key in the cache is incremented either a get or put operation is called on it.'
  This means if put(k,v) is to update the existing value of k-v. The use counter of k should be increased.
  The use counter is decided by the key not k-v
*/

/*
== Data structure Design  ============================
1. Map: keep k-v, for get() in O(1) time
2. data structure: trace the k-v use counter or visited frequency in 3 scenarios:
   <1> when a new k-v is put, it should be put in 0 frequency LFU node's LRU list in O(1)
   <2> when k-v is visited more than one time, need to increase the frequency:
      - unlink it in O(1) from current LRU list. This requires from the k-v get LRU node.
      - relink it in O(1) into next LFU node LRU list. use k-v or the LRU get LFU node
   <3> when capacity is full need
        - find LRU node in the 0-frequency LFU node's LRU list in O(1).
        - unlink it in O(1) from its LRU list and delete it from MAP.
        This requires the node in LRU keep the key of k-v.
   So the data structure should be a double linked list of LFU node which should contains
      a LRU node double linked list:
      LFU node list [smallest, ... , biggest]
      LRU node list [recent, ... , oldest] ( LinkedHashMap/LinkedHashSet use
                                             [oldest, ..., recent] double linked list)

3. LRU node: should contains key, used to delete k-v in map in <3>
             for<2> unlink: need get the LRU node via k-v Map.
              - Map<k, LRU node> ; this requires LRU node keep k and v. v is used by map
              - Map<k,v>, Map<k,LRU node>
             for<2> unlink relink part:
              - need to know current k visited frequency `f`,
                then check `f+1' LFU node existing,(prepare it if it does not exist).
                This requires to keep `f` somewhere: k->f->LFU node
                - keep f in LFU node + Map<k, LFU>
                - keep f in LRU node + keep LFU reference in LRU node
                - keep f in LRU node + Map<f, LFU>


   use 'keep f in LRU node + keep LFU reference in LRU node' to make less maintain work
Summary:
    - LRU Node<k,v,f> also keep LFU node reference
    - LFU node: pre, next; LRU: [recent,...,old];
    - based on LRU and LFU Node:
          Map<k, LRU Node<k,v,f>>
          Double linked list of LFU node: LFU: [smallest,... ,biggest];

== Operation Design ============================
 - unlink LFU node if it is empty after unlink its LRU node to make sure O(1) time
  to find the one to delete in LRU of LFU
   E.g. if the smaller frequency is not 4, not 0, and fNode from 0~3 is empty.
        need check one by one, it is not O(1) time
 - prepare next LFU node if it is not existing
     F nextFNode = n.c.next!=b && n.c.next.recent.next.f
       == n.f + 1 ? n.c.next : new F(n.c, n.c.next);
     or
     if (s.next==b || s.next.recent.next.f != 0) new F(s, s.next);
 - Leetcode require `Get the value (will always be positive) of the key if
   the key exists in the cache, otherwise
                     return -1.`
 - new Node default visited frequency is 0, update the frequency when unlink and
   relink happen
 - remove() return the key used to remove k-v in map
 */

class LFUCache { // about 138 line
  private F s, b;
  private Map<Integer, N> map;
  private int capacity;

  public LFUCache(int capacity) {
    this.capacity = capacity;

    s = new F();
    b = new F();
    s.next = b;
    b.pre = s;

    map = new HashMap<>();
  }

  private void unlink(F n) {
    F pre = n.pre;
    F next = n.next;
    pre.next = next;
    next.pre = pre;
    n.pre = n.next = null;
  }

  public int get(int k) {
    if (capacity == 0 || map.get(k) == null) return -1;
    N n = map.get(k);
    increaseFrequency(n);
    return n.v;
  }

  private void increaseFrequency(N n) {
    F c = n.c;
    F nextContainer = c.next != b && c.next.recent.next.f == n.f + 1 ? c.next : new F(c, c.next);

    unlink(n);
    // clean old fNode if it is empty now
    if (c.recent.next == c.old) unlink(c);

    // relink
    n.f++;
    n.c = nextContainer;
    nextContainer.add(n);
  }

  private void unlink(N n) {
    // unlink n
    N pre = n.pre;
    N next = n.next;

    pre.next = next;
    next.pre = pre;

    n.pre = n.next = null;
    n.c = null;
  }

  public void put(int k, int v) {
    if (capacity == 0) return;
    N n = map.get(k);

    if (n != null) {
      n.v = v;
      increaseFrequency(n); // or get(k)
      return;
    }

    if (map.size() == this.capacity) {
      int f = s.next.recent.next.f;
      map.remove(s.next.remove());
      if (s.next.recent.next == s.next.old) unlink(s.next);
    }
    if (s.next == b || s.next.recent.next.f != 0) new F(s, s.next);
    n = new N(k, v, s.next);
    s.next.add(n);
    map.put(k, n);
  }
}

class F {
  F pre, next;
  N recent, old; // [recent ,..., old]

  public F(F pre, F next) {
    this.pre = pre;
    this.next = next;
    next.pre = this;
    pre.next = this;

    recent = new N();
    old = new N();
    recent.next = old;
    old.pre = recent;
  }
  // for sentinel only
  public F() {}
  // add new node at the recent side
  public N add(N n) {
    N next = recent.next;

    recent.next = n;
    n.next = next;

    next.pre = n;
    n.pre = recent;

    return n;
  }

  public int remove() {
    N n = old.pre, p = n.pre;

    p.next = old;
    old.pre = p;

    n.pre = n.next = null;
    n.c = null;

    return n.k;
  }
}

class N {
  int k, v, f;
  N pre, next;
  F c; // container

  public N(int key, int val, F c) {
    this.k = key;
    this.v = val;
    this.f = 0; // default value at the creation time
    this.c = c;
  }

  public N() {
    f = -1; // for sentinel only
  }
}
