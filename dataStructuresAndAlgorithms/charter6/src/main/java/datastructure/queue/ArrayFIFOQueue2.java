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

public class ArrayFIFOQueue2<T> implements FIFOQueue<T> {
    private static int DEFAULT_CAPACITY = 2 << 2;

    private int capacity;
    private T[] d;
    private int head = 0;
    private int size = 0;

    public ArrayFIFOQueue2() {
        capacity = DEFAULT_CAPACITY;
        d = (T[]) new Object[DEFAULT_CAPACITY];
    }

    public ArrayFIFOQueue2(int capacity) {
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
        if (size == d.length) {
            return false;
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
        return Arrays.toString(Arrays.copyOfRange(d, head, head + size));
    }
}
