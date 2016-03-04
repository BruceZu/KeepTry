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

package datastructure.queue;

import java.util.Arrays;
import java.util.NoSuchElementException;

// prohibit the insertion of null elements
// capacity-restricted FIFO queue
public class ArrayFIFOQueue2<T> implements FIFOQueue<T> {
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    private int increaseBy = 128;

    private static int DEFAULT_CAPACITY = 2 << 2;

    protected T[] d;
    protected int head = 0; // the init position can be any position of d
    private int size = 0;

    public ArrayFIFOQueue2() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayFIFOQueue2(int capacity) {
        d = (T[]) new Object[capacity];
        increaseBy = (capacity >> 1) + 1;
    }

    @Override
    public boolean add(T t) {
        if (!offer(t)) {
            throw new IllegalStateException();
        }
        return true;
    }

    @Override
    public boolean offer(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        if (size == d.length) {
            if (d.length == MAX_ARRAY_SIZE) {
                return false;
            }
            int newLength = d.length + increaseBy;
            if (newLength < 0) {
                return false;
            }
            if (newLength >= MAX_ARRAY_SIZE) {
                newLength = MAX_ARRAY_SIZE;
            }

            if (head == 0) {
                d = Arrays.copyOf(d, newLength);
            } else {
                T[] r = (T[]) new Object[newLength];
                System.arraycopy(d, head, r, 0, d.length - head);
                System.arraycopy(d, 0, r, d.length - head, head);
                d = r;
                head = 0; // caution
            }
        }


        d[(head + size) % d.length] = t;
        size++;
        return true;
    }

    @Override
    public T remove() {
        T r = poll();
        if (r == null) {
            throw new NoSuchElementException();
        }
        return r;

    }

    @Override
    public T poll() {
        if (isEmpty()) {
            return null;
        }

        T r = d[head];

        d[head] = null;
        size--;

        head = (head + 1) % d.length;
        return r;
    }

    @Override
    public T element() {
        T r = peek();
        if (r == null) {
            throw new NoSuchElementException();
        }
        return r;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return d[head];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    public String toString() {
        if (isEmpty()) {
            return "";
        }
        int tail = (head + size - 1) % d.length;
        if (tail >= head) {
            return Arrays.toString(Arrays.copyOfRange(d, head, tail + 1));
        }
        T[] r = (T[]) new Object[size];
        System.arraycopy(d, head, r, 0, d.length - head);
        System.arraycopy(d, 0, r, d.length - head, tail + 1);
        return Arrays.toString(r);
    }

    public ArrayFIFOQueue2<T> clone() {
        ArrayFIFOQueue2<T> r = new ArrayFIFOQueue2();
        r.head = this.head;
        r.size = this.size;
        r.d = this.d.clone();
        return r;
    }
}
