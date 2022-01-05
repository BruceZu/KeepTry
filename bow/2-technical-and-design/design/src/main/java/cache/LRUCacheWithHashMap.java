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

package cache;

import java.util.HashMap;
import java.util.Map;

public class LRUCacheWithHashMap<K, V> {
  static class Entry<K, V> {
    Entry<K, V> pre, next;
    K k;
    V v;
    // used only for sentinel head and tail
    Entry() {}

    public Entry(K key, V value) {
      this.k = key;
      this.v = value;
    }
  }

  Entry head, tail; // Head side keep the least recently used entry;
  int capacity;
  Map<K, Entry<K, V>> map;

  public LRUCacheWithHashMap(int capacity) {
    this.capacity = capacity;
    map = new HashMap<>();
    head = new Entry();
    tail = new Entry();
    head.next = tail;
    tail.pre = head;
  }

  public V get(K k) {
    if (!map.containsKey(k)) return null;
    Entry<K, V> e = map.get(k);
    unlink(e);
    putToTail(e);

    return e.v;
  }

  public void put(K k, V v) {
    if (map.containsKey(k)) {
      Entry<K, V> e = map.get(k);
      e.v = v;
      putToTail(e);
      return;
    }
    if (map.size() == capacity) {
      Entry<K, V> removed = head.next;
      unlink(removed);
      map.remove(removed.k); // also remove it from map
    }

    Entry<K, V> e = new Entry<>(k, v);
    map.put(k, e);
    putToTail(e);
  }

  private void putToTail(Entry<K, V> e) {
    Entry<K, V> oldpre = tail.pre;
    oldpre.next = e;
    tail.pre = e;

    e.next = tail;
    e.pre = oldpre;
  }

  private void unlink(Entry<K, V> e) {
    Entry<K, V> pre = e.pre, next = e.next;
    pre.next = next;
    next.pre = pre;

    e.pre = null;
    e.next = null;
  }
}
