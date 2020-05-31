//  Copyright 2020 The KeepTry Open Source Project
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

package loadbalance;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

// Jump consistent hash:
// https://ai.googleblog.com/2017/04/consistent-hashing-with-bounded-loads.html
// https://arxiv.org/pdf/1406.2294.pdf "jump consistent hash, a fast, minimal memory, consistent
// hash algorithm that can  be expressed in about 5 lines of code."
// don’t need the concept of virtual nodes anymore
// There is one limitation though. The nodes must be numbered sequentially. the shard index is based
// on the total number of shards. Therefore, jump consistent hash can be useful in the context of
// sharding but not load balancing.
//
// Multi-probe consistent hashing
// https://arxiv.org/abs/1505.00062
//
// consistent hashing algorithmic trade offs
// https://medium.com/@dgryski/consistent-hashing-algorithmic-tradeoffs-ef6b8e2fcae8

/** Basic idea of Ring consistent hash */
public class RingConsistentHash<T> {
  private final HashFunction hashFunction;
  private final int numberOfReplicas;
  private final SortedMap<HashCode, T> circle = new TreeMap<HashCode, T>(); // need a lot of memory

  public RingConsistentHash(HashFunction hashFunction, int numberOfReplicas, Collection<T> nodes) {
    this.hashFunction = hashFunction;
    this.numberOfReplicas = numberOfReplicas;

    for (T node : nodes) {
      add(node);
    }
  }

  public void add(T node) {
    // alternative: define three different hashing functions. Each node is hashed three times to get
    // three different hashcode
    // refer https://itnext.io/introducing-consistent-hashing-9a289769052e
    for (int i = 0; i < numberOfReplicas; i++) {
      circle.put(hashFunction.hashString(node.toString() + i, Charset.defaultCharset()), node);
    }
  }

  public void remove(T node) {
    for (int i = 0; i < numberOfReplicas; i++) {
      circle.remove(hashFunction.hashString(node.toString() + i, Charset.defaultCharset()));
    }
  }

  public T getSever(Object reqKey) {
    if (circle.isEmpty()) {
      return null;
    }
    HashCode vnode = hashFunction.hashString(reqKey.toString(), Charset.defaultCharset());
    if (!circle.containsKey(vnode)) {
      SortedMap<HashCode, T> tailMap = circle.tailMap(vnode); // O(1)
      vnode = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey(); // O(logN)
    }
    return circle.get(vnode);
  }
}
