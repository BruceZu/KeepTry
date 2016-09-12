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

package queue;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

/**
 * <pre>
 * Pros: easy to understand. As have a layer of API of Stack between Queue and basic core Java data structures.
 *       separate the add and remove operation to balance their performance.
 * Cons: maintenance 2 stacks in space. and need move data
 *
 * Assume:
 *      1 null is not allowed.
 *      2 no limit on the class of element, but all elements need be of same class.
 *
 *      1 no limit on the property of element.
 *      2 repeated element is allowed.
 *      3 there is not size limited.
 *
 * Design:
 *      move data: when, who and how many.
 *      depends on special scenarios
 *      1  more offer/add operations
 *      2  more poll/remove, peek/element operations.
 *      3  balance them
 *
 *
 *      before or after offer/add.
 *      before or after poll/remove.
 *      another daemon on scheduled time.
 *
 *      care: size of in, out is empty or not, size of data to move.
 *
 * To make it simple:
 *      before poll or peek. check out, if it is empty,
 *      check in, if it is not empty then move all data from in.
 */
public class QueueWith2Stacks<E> implements Queue, FIFOQueue {
    private Stack<E> in;
    private Stack<E> out;


    public QueueWith2Stacks() {
        in = new Stack<>();
        out = new Stack<>();
    }

    @Override
    public boolean add(Object o) {
        return offer(o);
    }

    @Override
    public boolean offer(Object o) {
        if (o == null) {
            throw new NullPointerException("Null is not allowed");
        }
        in.push((E) o);
        return true;
    }

    @Override
    public Object remove() {
        Object r = poll();
        if (r == null) {
            throw new NoSuchElementException();
        }
        return r;
    }

    @Override
    public Object poll() {
        synchronized (out) {
            if (out.size() != 0) {
                return out.pop();
            }
            synchronized (in) {
                if (in.size() == 0) {
                    return null;
                }
                while (!in.empty()) {
                    out.push(in.pop());
                }
            }
            return out.pop();
        }
    }

    @Override
    public Object element() {
        Object r = poll();
        if (r == null) {
            throw new NoSuchElementException();
        }
        return r;
    }

    @Override
    public Object peek() {
        synchronized (out) {
            if (out.size() != 0) {
                return out.peek();
            }
            synchronized (in) {
                if (in.size() == 0) {
                    return null;
                }
                while (!in.empty()) {
                    out.push(in.pop());
                }
            }
            return out.peek();
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        synchronized (out) {
            synchronized (in) {
                return in.size() + out.size();
            }
        }
    }

    // interfaces of  Collection
    @Override
    public boolean contains(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public boolean remove(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public Iterator iterator() {
        throw new NotImplementedException();
    }

    @Override
    public Object[] toArray() {
        throw new NotImplementedException();
    }

    @Override
    public Object[] toArray(Object[] a) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addAll(Collection c) {
        throw new NotImplementedException();
    }

    @Override
    public void clear() {
        throw new NotImplementedException();
    }

    @Override
    public boolean retainAll(Collection c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean removeAll(Collection c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean containsAll(Collection c) {
        throw new NotImplementedException();
    }

    private void readTopToDown(Stack<E> s, StringBuilder sb) {
        if (!s.empty()) {
            E cur = s.pop();
            sb.append(cur.toString());
            if (s.size() != 0) {
                sb.append(", ");
            }

            readTopToDown(s, sb);
            s.push(cur);
        }
    }

    private void readDownToTop(Stack<E> s, StringBuilder sb, int size) {
        if (!s.empty()) {
            E cur = s.pop();
            readDownToTop(s, sb, size);

            s.push(cur);
            sb.append(cur.toString());
            if (s.size() != size) {
                sb.append(", ");
            }
        }
    }

    public String toString2() {
        synchronized (out) {
            synchronized (in) {
                StringBuilder sb = new StringBuilder();
                sb.append("[");

                readTopToDown(out, sb);
                if (!out.empty() && !in.empty()) {
                    sb.append(", ");
                }
                readDownToTop(in, sb, in.size());
                return sb.append("]").toString();
            }
        }
    }

    /**
     * <pre>
     * in       1 2 3 4 <-
     * out      <-
     *
     * in       5 6 7 8 <-
     * out      4 3 2 1 <-
     *
     * @return
     */
    @Override
    public String toString() {
        synchronized (out) {
            synchronized (in) {
                StringBuilder sb = new StringBuilder();
                sb.append("[");

                boolean first = true;
                E[] o = (E[]) out.toArray();
                for (int i = o.length - 1; i >= 0; i--) {
                    if (first) {
                        sb.append(o[i].toString());
                        first = false;
                        continue;
                    }
                    sb.append(", ").append(o[i].toString());
                }

                if (!out.empty() && !in.empty()) {
                    sb.append(", ");
                }

                E[] inArr = (E[]) in.toArray();
                first = true;
                for (int i = 0; i < inArr.length; i++) {
                    if (first) {
                        sb.append(inArr[i].toString());
                        first = false;
                        continue;
                    }
                    sb.append(", ").append(inArr[i].toString());
                }
                return sb.append("]").toString();
            }
        }
    }
}
