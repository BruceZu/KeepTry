//  Copyright 2019 The KeepTry Open Source Project
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

package graph;

import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <pre>
 * Used for Dijkstra shortest path and Prim MST in
 * `ø((|E|+|V|)log|V|)`
 * time, in the worst case for connected graphs this time bound can be simplified to
 * `ø(|E|log|V|)`"
 *
 * - <1> Provide `private index(T node)` in O(1) runtime
 *       with a Map to track the Node and its index in Node array.
 *
 * - <2> Based on <1>, provide
 *      -  public boolean contains(Object o)
 *      -  public void shiftUp(T n)
 *
 * - <3> For simple implementation:
 *      -- require element in the heap implements Comparable interface.
 *      -- use 1 based index in 'heap' array.
 *
 */
public class IBinaryHeap<T extends Comparable<T>> extends AbstractQueue<T> {

  private int size;
  private Object[] array;

  // track the element index in array,null value means the key is not in binary heap
  private Map<T, Integer> map;

  private void doubleSize() {
    if (array.length == Integer.MAX_VALUE) throw new IllegalStateException();
    int newCapacity = array.length << 1;
    if (newCapacity < 0) newCapacity = Integer.MAX_VALUE;
    array = Arrays.copyOf(array, newCapacity);
  }

  private void shiftDown(T n, int cur) {
    if (size == 0) return;
    while (cur <= (size >>> 1)) { // has child, not leaf node, same as   cur<<1  <= size
      int li = cur << 1, ri = li + 1, ci = li;
      T c = (T) array[li];
      if (ri <= size && c.compareTo((T) array[ri]) > 0) {
        c = (T) array[ri];
        ci = ri;
      }

      if (c.compareTo(n) < 0) {
        array[cur] = c;
        map.put(c, cur);
        cur = ci;
      } else {
        break;
      }
    }
    // when no element is left after poll: need not allocate the current n. return above
    // when 1 element is left ... need re-allocate ....
    // when more ... need re-allocate ....
    array[cur] = n;
    map.put(n, cur);
  }

  // i is index of n in current status
  private void shiftUp(T n, int i) {
    while (i > 1) {
      int pi = i >>> 1;
      T p = (T) array[pi];
      if (n.compareTo(p) < 0) {
        array[i] = p;
        map.put(p, i);
        i = pi;
      } else {
        // handle n out of while loop
        break;
      }
    }
    // - when n is new element, e.g.: the first element,
    //   need allocate n and need not go in loop.
    // - when n has already in heap and the updated value is not smaller enough to
    //   re-allocate n, the following 2 lines are redundant.
    array[i] = n;
    map.put(n, i);
  }

  private Integer indexOf(T n) {
    return map.get(n);
  }

  public IBinaryHeap(int capacity) {
    //  1-based index:
    //  - parent index i=> child index:  i << 1 , i << 1 |1
    //  - child index  i=> parent index: i >>> 1;

    size = 0; // current size of nodes
    array = new Object[capacity];
    map = new HashMap();
  }

  public void shiftUp(T n) {
    if (this.contains(n)) shiftUp(n, indexOf(n));
    else throw new IllegalStateException();
  }

  @Override
  public boolean offer(T n) {
    if (n == null) throw new NullPointerException();
    if (size == array.length - 1) doubleSize();
    size++;
    shiftUp(n, size);
    return true;
  }

  @Override
  public T poll() {
    if (size == 0) throw null;
    T result = (T) array[1];
    T n = (T) array[size];
    array[size] = null;
    map.remove(result);
    size--;
    if (size != 0) shiftDown(n, 1);
    return result;
  }

  @Override
  public T peek() {
    return (size == 0) ? null : (T) array[1];
  }

  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      int from = 1;

      @Override
      public boolean hasNext() {
        return from <= size;
      }

      @Override
      public T next() {
        return (T) array[from++];
      }
    };
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean contains(Object o) {
    return indexOf((T) o) != null;
  }
}
