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

package datastructure.deque;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIDeque<T> implements IDeque<T> {
    private static int DEFAULT_CAPACITY = 2 << 2;

    private T[] d;
    private int head = 1;
    private int tail = 1;

    public ArrayIDeque() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayIDeque(int capacity) {
        d = (T[]) new Object[capacity];
    }

    @Override
    public void addLast(T e) {
        if (!offerLast(e)) {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean offerLast(T t) {
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
    public T removeFirst() {
        T r = pollFirst();
        if (r == null) {
            throw new NoSuchElementException();
        }
        return r;
    }

    @Override
    public T pollFirst() {
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
    public T getFirst() {
        T r = peekFirst();
        if (r == null) {
            throw new NoSuchElementException();
        }
        return r;
    }

    @Override
    public T peekFirst() {
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

    //----------------------------------------------------------------
    @Override
    public void addFirst(T t) {
        if (!offerFirst(t)) {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean offerFirst(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        if (size() == d.length) {
            return false;
        }

        if (isEmpty()) {
            d[head] = t;
            return true;
        }
        head = head == 0 ? d.length - 1 : head - 1;
        d[head] = t;
        return true;
    }

    @Override
    public T removeLast() {
        T r = pollLast();
        if (r == null) {
            throw new NoSuchElementException();
        }
        return r;
    }

    @Override
    public T pollLast() {
        if (isEmpty()) {
            return null;
        }
        T r = d[tail];
        d[tail] = null;
        if (head == tail) {
            return r;
        }
        tail = tail == 0 ? d.length - 1 : tail - 1;
        return r;
    }

    @Override
    public T getLast() {
        T r = peekLast();
        if (r == null) {
            throw new NoSuchElementException();
        }
        return r;
    }

    @Override
    public T peekLast() {
        if (isEmpty()) {
            return null;
        }
        return d[tail];
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        // To-do
        throw new NotImplementedException();
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        // To-do
        throw new NotImplementedException();
    }

    @Override
    public Iterator<T> iterator() {
        if (isEmpty()) {
            return null;
        }
        return new Iterator<T>() {
            // no threads security implementation
            private int currentIndex = head - 1;

            @Override
            public boolean hasNext() {
                if (isEmpty()) {
                    return false;
                }
                return !(d[(currentIndex + 1) % d.length] == null);
            }

            @Override
            public T next() {
                if (hasNext()) {
                    currentIndex = (currentIndex + 1) % d.length;
                    return d[currentIndex];
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public Iterator<T> descendingIterator() {
        return new Iterator<T>() {
            // no threads security implementation
            private int currentIndex = tail + 1;

            @Override
            public boolean hasNext() {
                if (isEmpty()) {
                    return false;
                }
                return !(d[currentIndex == 0 ? d.length - 1 : currentIndex - 1] == null);
            }

            @Override
            public T next() {
                if (hasNext()) {
                    currentIndex = currentIndex == 0 ? d.length - 1 : currentIndex - 1;
                    return d[currentIndex];
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
