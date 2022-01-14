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

/*
 Cache contains key, value, value is Rankable
 always kick off the entry with smaller rank value when capacity is full and need load in new entry

 Gets some data. If possible, retrieves it from cache to be fast. If the data is not cached,
 retrieves it from the data source. If the cache is full, attempt to cache the returned data,
 evicting the T with lowest rank among the ones that it has available
 If there is a tie, the cache may choose any T with lowest rank to evict.




1. Map<K, Node<K, Rankable>> map  =>  Map<K, Rankable>
2. TreeSet<Node<K, Rankable>> rank; does not work. TreeSet does not cantain 2 Nodes with the same key
      the key here is the rank value of v in k-v
      key1, v1, rank-x
      key2, v2, rank-x
      if above k-v pair have the same rank-x value, then when the later one is added in the TreeSet.
      it will: 'If this set already contains the element, the call leaves the set unchanged and returns false'

3. No way to use only one TreeMap.
   TreeMap accept customized comparator object only in its constructor,
   So no way to transmit TreeMap object to the comparator
   Even somehow it works still there is the problem: TreeMap does not contain 2 node with the same key.
   the key here is the rank value of v in k-v.
4. Alternative:
  - TreeMap<Long rank, List<K>> map. need clean the entry when the list is empty
  - use Priority<Node<K, Rankable>> to replace the TreeSet<Node<K, Rankable>> rank
 */

class RetainBestCache_<K, V extends Rankable> {
  // ============================================================================
  // TreeSet/TeeMap vs PriorityQueue

  /*  ============================================================================
  solution with PriorityMap<rank,List<key>>
  O(1): time. If target exists in cache
  worst: O(logN) time. If target exists in cache and cache space is full. not take in account the loading time
  space: O(N)
  N is capacity.
  */
  private Map<K, Rankable> map;
  private PriorityQueue<Node<K, Rankable>> rankq;
  private DataSource<K, V> ds;
  private int capacity;

  public void RetainBestCache(DataSource<K, V> ds, int capacity) {
    this.ds = ds;
    this.capacity = capacity;
    map = new HashMap<>();
    rankq =
        new PriorityQueue<>(
            (a, b) -> {
              if (a.v.getRank() < b.v.getRank()) return -1;
              else if (a.v.getRank() == b.v.getRank()) return 0;
              else return 1;
            });
  }

  public Rankable get(K key) {
    if (map.containsKey(key)) return map.get(key);
    else {
      Rankable v = ds.get(key);
      if (map.size() == capacity) map.remove(rankq.poll().k);

      Node<K, Rankable> n = new Node(key, v);
      map.put(key, v);
      rankq.offer(n);
      return v;
    }
  }
  // Node class
  static class Node<T, V extends Rankable> {
    public T k;
    public V v;

    public Node(T k, V v) {
      this.k = k;
      this.v = v;
    }
  }

  public static void main(String[] args) {
    Node<Integer, Rankable> n1 = new Node(3, () -> 1l);
    Node<Integer, Rankable> n2 = new Node(4, () -> 1l);
    TreeSet<Node<Integer, Rankable>> rank =
        new TreeSet<>((a, b) -> (int) (a.v.getRank() - b.v.getRank()));

    rank.add(n1);
    rank.add(n2);
    System.out.println(rank.size() == 1); // true
    System.out.println(rank.contains(n2) == true); // true
    System.out.println(rank.first().k == 3); // true
    //
    PriorityQueue<Node<Integer, Rankable>> rank2 =
        new PriorityQueue<>((a, b) -> (int) (a.v.getRank() - b.v.getRank()));
    rank2.offer(n1);
    rank2.offer(n2);
    System.out.println(rank2.peek().k == 3); // true
    rank2.poll();
    System.out.println(rank2.peek().k == 4); // true
  }
}

/*  ============================================================================
solution with TreeMap<Long, List<K>>
*/
public class RetainBestCache<K, V extends Rankable> {
  private Map<K, Rankable> map;
  // rank:  key list
  //   1 :  03, 01, 04
  //   2 :  02, 05
  //   3 :  06
  private TreeMap<Long, List<K>> rankMap;

  private DataSource<K, V> ds;
  private int capacity;

  public RetainBestCache(DataSource<K, V> ds, int capacity) {
    this.ds = ds;
    this.capacity = capacity;
    map = new HashMap<>();
    rankMap =
        new TreeMap<>(
            (a, b) -> {
              if (a < b) return -1;
              else if (a == b) return 0;
              else return 1;
            });
  }

  public Rankable get(K k) {
    if (map.containsKey(k)) return map.get(k);
    else {
      Rankable v = ds.get(k);
      if (map.size() == capacity) {
        List<K> ks = rankMap.firstEntry().getValue();
        map.remove(ks.get(ks.size() - 1));
        ks.remove(ks.size() - 1);
        if (ks.isEmpty()) rankMap.pollFirstEntry();
      }

      map.put(k, v);
      rankMap.putIfAbsent(v.getRank(), new ArrayList<>());
      rankMap.get(v.getRank()).add(k);

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
