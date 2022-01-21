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

package set;

import java.util.function.Consumer;

/*
A set: No duplicates, order doesn't matter
Assume the value range is [0, N-1]
Require:
clear() should not allocate new memory
iterate() is O(size) runtime
insert(), remove(), lookup(), clear() is O(1) runtime

https://imgur.com/jaVze0v
*/
public class ValueRangeSet {
  int[] vi, iv;
  int i;

  public ValueRangeSet(int N) {
    vi = new int[N]; // Value To Index
    iv = new int[N]; // index To Value
    i = 0;
  }

  void insert(int x) { // Runtime O(1)
    if (!lookup(x)) {
      vi[x] = i;
      iv[i] = x;
      i++;
    }
  }

  void remove(int x) { // Runtime O(1)
    if (lookup(x)) {
      int xi = vi[x];

      i--; // swap with the last one
      int lastV = iv[i];

      vi[lastV] = xi;
      iv[xi] = lastV;
    }
  }

  boolean lookup(int x) { // Runtime O(1)
    return vi[x] < i && iv[vi[x]] == x;
  }

  void clear() { // Runtime O(1)
    i = 0;
  }

  void iterate(Consumer<Integer> f) { // Runtime O(count)
    for (int i = 0; i < this.i; i++) {
      f.accept(iv[i]);
    }
  }

  // test ----------------------------------------------------------------------
  public static void main(String[] args) {
    ValueRangeSet s = new ValueRangeSet(10);
    s.insert(5);
    s.insert(6);
    s.insert(7);
    s.remove(6);
    s.insert(9);
    s.iterate(v -> System.out.println(v));
    s.clear();
    s.insert(5);
    s.remove(5);
    s.insert(9);
    s.iterate(v -> System.out.println(v));
  }
}
