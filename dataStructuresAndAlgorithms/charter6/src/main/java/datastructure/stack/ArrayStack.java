package datastructure.stack;//  Copyright 2016 The Sawdust Open Source Project
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayStack<T> implements IStack<T> {
    //Some VMs reserve some header words in an array.
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    private static int DEFAULT_CAPACITY = 2 << 8;
    private T[] d;
    private int currentIndex = -1;

    public ArrayStack(int capacity) {
        d = (T[]) (new Object[capacity]);
    }

    public ArrayStack() {
        this(DEFAULT_CAPACITY);
    }

    @Override // run in O(1) time
    public int size() {
        return currentIndex + 1;
    }

    @Override //  run in O(1) time
    public boolean isEmpty() {
        return currentIndex == -1;
    }

    @Override  // run in O(1) time
    public void push(T e) {
        if (size() + 1 > d.length) {
            if (d.length == MAX_ARRAY_SIZE) {
                throw new OutOfMemoryError("Stack is full");
            }
            int newLength = d.length + (d.length >> 1) + 1;
            if (newLength < 0) {
                throw new OutOfMemoryError("Stack is full");
            }
            if (newLength >= MAX_ARRAY_SIZE) {
                newLength = MAX_ARRAY_SIZE;
            }
            d = Arrays.copyOf(d, newLength);
        }
        d[++currentIndex] = (T) e;
    }

    @Override // run in O(1) time
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return d[currentIndex];
    }

    @Override // run in O(1) time
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T r = d[currentIndex];
        d[currentIndex--] = null; // dereference to help garbage collection
        return r;
    }

    @Override
    public String toString() {
        T[] da = Arrays.copyOfRange(d, 0, size());
        // left is top
        List l = Arrays.asList(da);
        Collections.reverse(l);
        return Arrays.toString(l.toArray());
    }
}
