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

package datastructure.stack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LeakyStack<T> implements IStack<T> {
    private int size = 0;
    protected T[] d;
    protected int head = 0;

    public LeakyStack(int capacity) {
        d = (T[]) new Object[capacity];
    }

    public LeakyStack() {
        d = (T[]) new Object[8];
    }

    @Override  // run in O(1) time
    public void push(T e) {
        if (size == 0) {
            d[head] = (T) e;
            size = 1;
            return;
        }
        head = (head + 1) % d.length;
        d[head] = (T) e;
        size = size + 1 >= d.length ? d.length : size + 1;
    }

    @Override // run in O(1) time
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return d[head];
    }

    @Override // run in O(1) time
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T r = d[head];
        d[head] = null; // dereference to help garbage collection
        if (size == 1) {
            size--;
            return r;
        }
        head = head == 0 ? d.length - 1 : head - 1;
        size--;
        return r;
    }

    @Override // run in O(1) time
    public int size() {
        return size;
    }

    @Override //  run in O(1) time
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {

        if (isEmpty()) {
            return "";
        }
        if (head + 1 == size) {
            T[] da = Arrays.copyOfRange(d, 0, size());
            // left is top
            List l = Arrays.asList(da);
            Collections.reverse(l);
            return Arrays.toString(l.toArray());
        }

        T[] r = (T[]) new Object[size()];
        System.arraycopy(d, d.length - (size - head - 1), r, 0, size - head - 1);
        System.arraycopy(d, 0, r, size - head - 1, head + 1);
        // left is top
        List l = Arrays.asList(r);
        Collections.reverse(l);
        return Arrays.toString(l.toArray());
    }
}
