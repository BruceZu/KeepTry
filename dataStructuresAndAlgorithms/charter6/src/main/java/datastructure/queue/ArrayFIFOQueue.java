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

import java.util.NoSuchElementException;

public class ArrayFIFOQueue<T> implements FIFOQueue<T> {
    private static int DEFAULT_CAPACITY = 2 << 2;

    private int capacity;
    private T[] d;
    private int head = 0;
    private int tail = 0;

    public ArrayFIFOQueue() {
        capacity = DEFAULT_CAPACITY;
        d = (T[]) new Object[DEFAULT_CAPACITY];
    }

    public ArrayFIFOQueue(int capacity) {
        this.capacity = capacity;
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
        int nextTailIndex = tail + 1;
        if (nextTailIndex == 0) {
            tail = 0;
            head = 0;
            d[0] = t;
            return true;
        }

        // wrap around.
        if (nextTailIndex == capacity && d[0] == null) {
            tail = 0;
            d[0] = t;
            return true;
        }

        if (d[nextTailIndex] == null) {
            d[++tail] = t;
            return true;
        }
        return false;
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
            head = -1;
            tail = -1;
            return r;
        }

        if (head + 1 == capacity) {
            head = 0;
            return r;
        }

        head++;
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
        return head == -1;
    }

    @Override
    public int size() {
        if (head == -1) {
            return 0;
        }
        return tail >= head ? tail - head + 1 : capacity - head + tail + 1;
    }
}
