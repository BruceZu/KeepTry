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

package cache;

import java.util.*;

public class RetainBestCache<K, V extends Rankable> {
  class Node<K, V extends Rankable> { // Note Rankable
    public K k;
    public V v;

    public Node(K k, V v) {
      this.k = k;
      this.v = v;
    }
  }
  // Map<K, Node<K, Rankable>> map; // can be   Map<K, Rankable>
  private Map<K, Rankable> map; // Note Rankable
  // alternative is TreeMap<Long rank, List<K>> map. need clean the entry when the list is empty
  private TreeSet<Node<K, Rankable>> rankSet; // Note Rankable
  private DataSource<K, V> ds;
  private int capacity;

  public RetainBestCache(DataSource<K, V> ds, int capacity) {
    this.ds = ds;
    this.capacity = capacity;
    map = new HashMap<>();
    // Note the type (Comparator<Node<K, Rankable>>) before (a,b)->(int) (a.v.getRank() -
    // b.v.getRank())
    rankSet =
        new TreeSet<>(
            (Comparator<Node<K, Rankable>>)
                (a, b) -> (int) (a.v.getRank() - b.v.getRank())); // Note int
  }

  /*
  O(1): time. If target exists in cache
  worst: O(logN) time. If target exists in cache and cache space is full. not take in account the loading time
  space: O(N)
  N is capacity.
  */
  public Rankable get(K key) { // Note Rankable
    if (map.containsKey(key)) return map.get(key); // Note containsKey()
    else {
      Rankable v = ds.get(key);
      if (map.size() == capacity) {
        map.remove(rankSet.first().k); // Note TreeSet.first()
        rankSet.pollFirst(); // Note TreeSet.pollFirst()
      }

      Node<K, Rankable> n = new Node(key, v); // Note Rankable
      map.put(key, v);
      rankSet.add(n); // Note TreeSet.add(Object)
      return v;
    }
  }
}

interface Rankable {
  long getRank();
}

interface DataSource<K, V extends Rankable> {
  V get(K key);
}

/*
 No way to use only one TreeMap.
 TreeMap accept customized comparator object only in its constructor,
 So no way to transmit TreeMap object to the comparator


*/
