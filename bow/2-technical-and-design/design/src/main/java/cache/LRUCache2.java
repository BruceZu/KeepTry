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

/*
Leetcode 146. LRU Cache
Idea comes from LinkedHashMap inner class Entry
also help understand LinkedHashMap

mock HashMap with `Entry[] bucks`;
new entry is always added as the fist entry at bucks[key%/bucks.length]

tracking the size of all entries in LRUCache, check size > capacity?
others parts are same as Leetcode146LRUCache
*/
public class LRUCache2 {
  // --------------------------------------------------------------------------
  class Entry<K, V> {
    Node<K, V> n; // shared with double linked list
    Entry<K, V> next;

    Entry(Node<K, V> n) {
      this.n = n;
    }
  }
  // --------------------------------------------------------------------------
  class MockedHashMap {
    private Entry[] bucks;

    public MockedHashMap(int capacity) {
      bucks = new Entry[capacity];
    }

    public Node get(int key) {
      Entry<Integer, Integer> e = bucks[key % bucks.length];
      if (e == null) return null;
      while (e != null) {
        if (e.n.k == key) return e.n;
        e = e.next;
      }
      return null;
    }

    public void putNew(Node<Integer, Integer> n) {
      Entry adde = new Entry(n);
      int i = n.k % bucks.length;
      Entry e = bucks[i];
      if (e != null) adde.next = e;
      bucks[i] = adde;
    }

    // Assume the entry  exists
    public void removeOldByKey(int key) {
      int index = key % bucks.length;
      Entry<Integer, Integer> e = bucks[index];
      if (e.n.k == key) {
        bucks[index] = e.next;
        return;
      }
      while (e.next.n.k != key) e = e.next;
      e.next = e.next.next;
    }
  }
  // --------------------------------------------------------------------------

  private int capacity;
  private MockedHashMap map;
  private IDoubleList<Integer, Integer> list; // tracking least Recent Visited
  private int size;

  public LRUCache2(int capacity) {
    this.capacity = capacity;
    map = new MockedHashMap(capacity);
    list = new IDoubleList();
    size = 0;
  }

  public Integer get(int key) {
    Node<Integer, Integer> n = map.get(key);
    if (n == null) return -1; // required by leetcode

    list.visit(n);
    return n.v;
  }

  public void put(int key, int value) {
    Node n = map.get(key);
    if (n != null) { // update existing one
      n.v = value;
      list.visit(n);
      return; // if not return then it will be removed if size==capacity
    }

    if (size == capacity) {
      map.removeOldByKey(list.removeOld().k);
      size--;
    }

    n = new Node(key, value);
    map.putNew(n);
    list.addNew(n);
    size++;
  }

  public static void main(String[] args) {
    // ["LRUCache","get","put","get","put","put","get","get"]
    // [[2],[2],[2,6],[1],[1,5],[1,2],[1],[2]]
    LRUCache2 t = new LRUCache2(2);
    t.put(2, 6);
    t.put(1, 5);
    t.put(1, 2);
    t.get(1);
    t.get(2);
  }
}
