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

package graph.shortestpath.weightedgraphs;

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
 * - <2> Based on <1>, provide `public boolean contains(Object o)` and `public void shiftUp(T n)`
 *
 * - <3> For simple:
 *
 *      -- require element in the heap implements Comparable interface.
 *
 *      -- use 1 based index in 'heap' array.
 *
 */
class IBinaryHeap<T extends Comparable<T>> extends AbstractQueue<T> {

    private int size;
    private Object[] heap;

    // track the element index in array,null value means the key is not in binary heap
    private Map<T, Integer> indexes;

    private void doubleSize() {
        if (heap.length == Integer.MAX_VALUE) throw new IllegalStateException();
        int newCapacity = heap.length << 1;
        if (newCapacity < 0) newCapacity = Integer.MAX_VALUE;
        heap = Arrays.copyOf(heap, newCapacity);
    }

    private void shiftDown(T n, int cur) {
        if (size == 0) return;
        while (cur <= (size >>> 1)) { // has child, not leaf node, same as   cur<<1  <= size
            int li = cur << 1, ri = li + 1, ci = li;
            T c = (T) heap[li];
            if (ri <= size && c.compareTo((T) heap[ri]) > 0) {
                c = (T) heap[ri];
                ci = ri;
            }

            if (c.compareTo(n) < 0) {
                heap[cur] = c;
                indexes.put(c, cur);
                cur = ci;
            } else {
                break;
            }
        }
        // when no element is left after poll: need not allocate the current n. return above
        // when 1 element is left ... need re-allocate ....
        // when more ... need re-allocate ....
        heap[cur] = n;
        indexes.put(n, cur);
    }

    private void shiftUp(T n, int cur) {
        while (cur > 1) {
            int pi = cur >>> 1;
            T p = (T) heap[pi];
            if (n.compareTo(p) < 0) {
                heap[cur] = p;
                indexes.put(p, cur);
                cur = pi;
            } else {
                // handle n out of while loop
                break;
            }
        }
        // - when n is new element, e.g.: the first element,
        //   need allocate n and need not go in loop.
        // - when n has already in heap and the updated value is not smaller enough to
        //   re-allocate n, the following 2 lines are redundant.
        heap[cur] = n;
        indexes.put(n, cur);
    }

    private Integer indexOf(T n) {
        return indexes.get(n);
    }

    public IBinaryHeap(int capacity) {
        // keep data from index 1: calculate parent with indexOf/2. child: 2＊k, 2＊k+1
        // (keep data from index 0: calculate parent with (indexOf-1)/2.child: 2＊k+1,2＊k+2)
        size = 0; // current size of nodes
        heap = new Object[capacity];
        indexes = new HashMap();
    }

    public void shiftUp(T n) {
        if (this.contains(n)) shiftUp(n, indexOf(n));
        else throw new IllegalStateException();
    }

    @Override
    public boolean offer(T n) {
        if (n == null) throw new NullPointerException();
        if (size == heap.length - 1) {
            doubleSize();
        }
        size++;
        shiftUp(n, size);
        return true;
    }

    @Override
    public T poll() {
        if (size == 0) {
            throw null;
        }
        T result = (T) heap[1];

        T n = (T) heap[size];
        heap[size] = null;
        indexes.remove(result);
        size--;

        if (size != 0) {
            shiftDown(n, 1);
        }
        return result;
    }

    @Override
    public T peek() {
        return (size == 0) ? null : (T) heap[1];
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
                return (T) heap[from++];
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
