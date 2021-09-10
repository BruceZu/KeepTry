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

import java.util.HashMap;
import java.util.LinkedHashSet;

/*
Leetcode 460 LFU Cache
 compared with Leetcode460LFUCache.java:
   use LinkedHashSet to keep LRU in frequency node
   pros: the LRU Node and list is saved,
 LinkedHashSet<key>
 - with accessOrder = false; use insertion-order.
 - use the default removeEldestEntry() to never delete the set member:
           protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
              return false;
           }
 LinkedHashSet is based on LinkedHashTreeMap.
 it has inner
   - HashMap.entry<key,PRESENT>[],  as HashMap
   - HashMap.entry<key,PRESENT> head, tail, as inner double linked list to keep
     key in insert order.
   so
   for scenario <2> when key is visited again and need increase the use counter or
   visited frequency:
    LinkedHashSet<key> need delete the key, it is O(1) by the inner HashMap and unlink
    it from the inner double linked list, then relink it, add it to next LFU node's
    LinkedHashSet<key>, the add() is O(1) too. by inner HashMap and append the entry
    on the tail side
   for scenario <1>: add() is O(1)
   for scenario <3>: the entry to be removed is the one next to the
                    `head`(oldest) LRU of the 0-frequency LFU node.
                     The entry's key value can be got by
                          LinkedHashSet.iterator().next() in O(1)
                     with the key to delete the entry from LinkedHashSet
                       - from inner HashMap in O(1)
                       - unlink the entry from inner double linked list in O(1)
                       unlink the LFU node if now its LRU  LinkedHashSet is empty
                     with the key to delete the entry from map(k,v)

 This requires other data structure to support 3 scenarios in O(1) time
   - Map<k,v> + Map<k,LFU node>: k-> LFU-> LinkedHashSet<key>-> entry<key,PRESENT>
   - keep `f` in LFU node.
 */
public class LFUCache2 { // about 105 line
  static class F { // LFU node ------------------------------
    public int f = 0;
    public LinkedHashSet<Integer> LRU = null; // LRU
    public F prev = null, next = null;

    public F(int f, F pre, F next) {
      this.f = f;
      LRU = new LinkedHashSet();

      this.prev = pre;
      this.next = next;

      pre.next = this;
      next.prev = this;
    }

    private F() { // for sentinel only
      this.f = -1;
    }
  }
  // LFUCache implementation ------------------------------
  private int capacity;

  // LFU list
  private HashMap<Integer, F> fMap = null;
  private F s; // sentinel Smaller Side
  private F b;

  // k-v Map
  private HashMap<Integer, Integer> map = null;

  public LFUCache2(int capacity) {
    this.capacity = capacity;

    // LFU node list
    fMap = new HashMap();
    s = new F();
    b = new F();
    s.next = b;
    b.prev = s;

    // k-v map
    map = new HashMap();
  }

  public int get(int key) {
    Integer v = map.get(key);
    if (v == null) return -1;
    increaseFrequency(key);
    return v;
  }

  private void increaseFrequency(int k) {
    F f = fMap.get(k);
    if (f.next.f != f.f + 1) new F(f.f + 1, f, f.next);

    // maintain the LFU node list and fMap.
    f.LRU.remove(k);
    f.next.LRU.add(k);
    fMap.put(k, f.next);
    if (f.LRU.isEmpty()) unlink(f);
  }

  private void unlink(F node) {
    F p = node.prev;
    F n = node.next;

    p.next = n;
    n.prev = p;

    node.prev = node.next = null;
  }

  public void put(int k, int v) {
    if (capacity == 0) return;

    if (map.get(k) != null) { // update value
      map.put(k, v);
      increaseFrequency(k); //  get(k)
      return;
    }

    if (map.size() == this.capacity) remove();
    insert(k, v);
  }

  private void remove() {
    int k = s.next.LRU.iterator().next(); // to be removed
    map.remove(k);

    s.next.LRU.remove(k);
    if (s.next.LRU.isEmpty()) unlink(s.next);
    fMap.remove(k);
  }

  private void insert(int k, int v) {
    map.put(k, v);

    if (s.next.f != 0) {
      new F(0, s, s.next);
    }
    s.next.LRU.add(k);
    fMap.put(k, s.next);
  }
}
