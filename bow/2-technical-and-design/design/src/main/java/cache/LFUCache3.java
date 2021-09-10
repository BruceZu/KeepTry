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

import java.util.HashMap;
import java.util.LinkedHashSet;

/*
Leetcode 460 LFU Cache
 compared with LFUCache2.java:
 replace LFU node and list with a map<f, LRU>
 pros: the LFU node and list is saved
 cons: cost is need to racking min `f`

 key is distinct and is located in one LFU contained LRU list.

 LFU node and list:
   - As container or bucket to keep LRU list and let the LRU list be searchable by k->f->LFU->LRU list
     Alternative: Map<k, LFU> and let LFU keep the `f`.
     If replace LFU by a Map<f, LRU>, then need another Map<k,f>
     the `f` is kept in 3 Maps as k and value.
     the `f` cannot be saved, we need know f+1 and related LRU list
   - find the to-be-removed by smaller->next LFU in O(1)
     Alternative: tracing the min `f` with idea same as that tacing the min frequency of char
     in one side extending sliding window.
 LRU node and list:
   - keep the insert order of same `f`
   - unlink a given visited LRU node in O(1) from current `f` related LFU

 Summay:
   - 3 Map: Map<k,v>, Map<k,f> Map<f,LinkedHashSet<k>>
   - minf
 */
public class LFUCache3 { // about 46 lines
  HashMap<Integer, Integer> map;
  HashMap<Integer, Integer> kf;
  HashMap<Integer, LinkedHashSet<Integer>> LFU;
  int cap;
  int minf = 0;

  public LFUCache3(int capacity) {
    cap = capacity;
    map = new HashMap<>();
    kf = new HashMap<>();
    LFU = new HashMap<>();
  }

  public int get(int k) {
    if (!map.containsKey(k)) return -1;

    int f = kf.get(k);
    LFU.get(f).remove(k);
    if(minf==f && LFU.get(f).isEmpty()) minf++;

    LFU.putIfAbsent(f+1,new LinkedHashSet<>());
    LFU.get(f+1).add(k);
    kf.put(k, f + 1);

    return map.get(k);
  }

  public void put(int k, int v) {
    if (cap <= 0) return;
    if (map.containsKey(k)) {
      map.put(k, v);
      get(k);
      return;
    }
    if (map.size() >= cap) {
      int removeK = LFU.get(minf).iterator().next();
      LFU.get(minf).remove(removeK);
      map.remove(removeK);
      //kf.remove(removeK);
    }

    minf = 1;
    map.put(k, v);
    kf.put(k, 1);
    LFU.putIfAbsent(1,new LinkedHashSet<>());
    LFU.get(1).add(k) ;
  }
}
