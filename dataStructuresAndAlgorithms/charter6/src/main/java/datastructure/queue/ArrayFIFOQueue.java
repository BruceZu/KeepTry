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
public class ArrayFIFOQueue<T> implements FIFOQueue<T> {
    private static int DEFAULT_CAPACITY = 2 << 2;


    private T[] d;
    // the initial position: can be any place in d ,only make sure
    // the head and tail are same
    private int head = 1;
    private int tail = 1;

    public ArrayFIFOQueue() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayFIFOQueue(int capacity) {
        d = (T[]) new Object[capacity];
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
        if (size() == d.length) {
            return false;
        }
        if (size() == 0) {
            d[head] = t;
            return true;
        }
        tail = (tail + 1) % d.length;
        d[tail] = t;
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
        if (head == tail) {
            return r;
        }

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
        return head == tail && d[head] == null;
    }

    @Override
    public int size() {
        if (isEmpty()) {
            return 0;
        }
        return tail >= head ? tail - head + 1 : d.length - head + tail + 1;
    }

    public String toString() {
        if (isEmpty()) {
            return "";
        }
        if (tail >= head) {
            return Arrays.toString(Arrays.copyOfRange(d, head, tail + 1));
        }
        T[] r = (T[]) new Object[size()];
        System.arraycopy(d, head, r, 0, d.length - head);
        System.arraycopy(d, 0, r, d.length - head, tail + 1);
        return Arrays.toString(r);
    }

    public ArrayFIFOQueue<T> clone() {
        ArrayFIFOQueue<T> r = new ArrayFIFOQueue();
        r.head = this.head;
        r.tail = this.tail;
        r.d = this.d.clone();
        return r;
    }
}
