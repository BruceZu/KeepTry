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

package datastructure.list;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;

//TODO:  support multi threads access concurrently.
public class CircularlyLinkedList2<E> implements MyRotateList {
    private class Node<E> {
        private E e;
        private Node<E> n;

        private Node(E e, Node<E> n) {
            this.e = e;
            this.n = n;
        }
    }

    private Node<E> end;
    private int size;

    private int endNodeIndex() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("This list is empty");
        }
        return size - 1;
    }

    private boolean isOnlyOne() {
        return size == 1;
    }

    private int checkPositionIndex(int positionIndex) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("This list is empty");
        }
        if (positionIndex < 0 || endNodeIndex() < positionIndex) {
            throw new IndexOutOfBoundsException("Index is wrong ");
        }
        return positionIndex;
    }

    private Node<E> nodeOf(int index) {
        checkPositionIndex(index);
        if (index == endNodeIndex()) {
            return end;
        }

        Node<E> current = end.n;
        int i = 0;
        while (i != index) {
            current = current.n;
            i++;
        }
        return current;
    }

    @Override
    public void rotate() {
        if (size() > 1) {
            end = end.n;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Deprecated
    public int size2() {
        if (end == null) {
            return 0;
        }
        if (end == end.n) {
            return 1;
        }
        int size = 1;
        Node<E> i = end.n;
        while (i != end) {
            size++;
            i = i.n;
        }
        return size;
    }

    public MyLinkedList addBeforeHead(Object e) {
        requireNonNull(e);
        Node<E> newNode = new Node<E>((E) e, null);
        if (isEmpty()) {
            end = newNode;
            newNode.n = newNode;
            size++;
            return this;
        }

        newNode.n = end.n;
        end.n = newNode;
        size++;
        return this;
    }

    public MyLinkedList appendAfterEnd(Object e) {
        requireNonNull(e);
        addBeforeHead(e);
        end = end.n;
        return this;
    }

    public MyLinkedList addBefore(Object e, int index) {
        requireNonNull(e);
        checkPositionIndex(index);
        if (index == 0) {
            addBeforeHead(e);
            return this;
        }
        addAfter(e, index - 1);
        return this;
    }

    public MyLinkedList addAfter(Object e, int index) {
        requireNonNull(e);
        checkPositionIndex(index);
        if (index == endNodeIndex()) {
            appendAfterEnd(e);
            return this;
        }
        Node<E> n = nodeOf(index);
        n.n = new Node<E>((E) e, n.n);
        size++;
        return this;
    }

    public E deleteHead() {
        checkPositionIndex(0);
        Node<E> it = end.n;
        if (isOnlyOne()) {
            end = null;
            it.n = null;

            size = 0;
            return it.e;
        }

        end.n = it.n;
        it.n = null;

        size--;
        return it.e;
    }

    public E deleteEnd() {
        return delete(endNodeIndex());
    }

    public E delete(int index) {
        checkPositionIndex(index);
        if (index == 0) {
            return deleteHead();
        }
        Node<E> beforeIt = nodeOf(index - 1);
        Node<E> it = beforeIt.n;
        beforeIt.n = it.n;
        it.n = null;

        if (index == endNodeIndex()) {
            end = beforeIt;
        }

        size--;
        return it.e;
    }

    public E update(int index, Object e) {
        requireNonNull(e);
        Node<E> n = nodeOf(index);
        E re = n.e;
        n.e = (E) e;
        return re;
    }

    public E updateHead(Object e) {
        requireNonNull(e);
        E re = end.n.e;
        end.n.e = (E) e;
        return re;
    }

    public E updateEnd(Object e) {
        requireNonNull(e);
        E re = end.e;
        end.e = (E) e;
        return re;
    }

    public E getHead() {
        if (isEmpty()) {
            return null;
        }
        return end.n.e;
    }

    public E getEnd() {
        if (isEmpty()) {
            return null;
        }
        return end.e;
    }

    public E get(int index) {
        checkPositionIndex(index);
        return nodeOf(index).e;
    }

    @Override
    public void clean() {
        if (size() == 0) {
            return;
        }
        size = 0;
        end = null;
    }

    @Override
    public MyLinkedList clone() {
        MyLinkedList r = new CircularlyLinkedList2<E>();
        if (size() == 0) {
            return r;
        }
        Node<E> i = end.n;
        while (i != end) {
            E e = i.e;
            E eClone;
            if (e instanceof Cloneable) {
                try {
                    if (e instanceof Object[])
                        eClone = (E) ((Object[]) e).clone();
                    else if (e instanceof byte[])
                        eClone = (E) ((byte[]) e).clone();
                    else if (e instanceof short[])
                        eClone = (E) ((short[]) e).clone();
                    else if (e instanceof int[])
                        eClone = (E) ((int[]) e).clone();
                    else if (e instanceof long[])
                        eClone = (E) ((long[]) e).clone();
                    else if (e instanceof char[])
                        eClone = (E) ((char[]) e).clone();
                    else if (e instanceof float[])
                        eClone = (E) ((float[]) e).clone();
                    else if (e instanceof double[])
                        eClone = (E) ((double[]) e).clone();
                    else if (e instanceof boolean[])
                        eClone = (E) ((boolean[]) e).clone();
                    else
                        eClone = (E) e.getClass().getDeclaredMethod("clone").invoke(e);
                    r.appendAfterEnd(eClone);
                } catch (NoSuchMethodException |
                        IllegalAccessException |
                        InvocationTargetException ex) {
                    r.appendAfterEnd(e);
                }
            } else {
                r.appendAfterEnd(e);
            }
            i = i.n;
        }
        r.appendAfterEnd(end.e);

        return r;
    }

    @Override
    public boolean equals(Object it) {
        if (it == null) {
            return false;
        }
        if (this == it) {
            return true;
        }
        if (!(it instanceof CircularlyLinkedList2) || ((CircularlyLinkedList2) it).size() != this.size()) {
            return false;
        }
        if (size() == ((CircularlyLinkedList2) it).size() && size() == 0) {
            return true;
        }
        Node<E> i = end;
        Node<E> j = ((CircularlyLinkedList2) it).end;
        do {
            E e1 = i.e;
            E e2 = j.e;

            if (e1 == e2) {
                i = i.n;
                j = j.n;
                continue;
            }
            if (e1 == null) {
                return false;
            }

            boolean eq;

            if (e1 instanceof Object[] && e2 instanceof Object[])
                eq = Arrays.deepEquals((Object[]) e1, (Object[]) e2);
            else if (e1 instanceof byte[] && e2 instanceof byte[])
                eq = Arrays.equals((byte[]) e1, (byte[]) e2);
            else if (e1 instanceof short[] && e2 instanceof short[])
                eq = Arrays.equals((short[]) e1, (short[]) e2);
            else if (e1 instanceof int[] && e2 instanceof int[])
                eq = Arrays.equals((int[]) e1, (int[]) e2);
            else if (e1 instanceof long[] && e2 instanceof long[])
                eq = Arrays.equals((long[]) e1, (long[]) e2);
            else if (e1 instanceof char[] && e2 instanceof char[])
                eq = Arrays.equals((char[]) e1, (char[]) e2);
            else if (e1 instanceof float[] && e2 instanceof float[])
                eq = Arrays.equals((float[]) e1, (float[]) e2);
            else if (e1 instanceof double[] && e2 instanceof double[])
                eq = Arrays.equals((double[]) e1, (double[]) e2);
            else if (e1 instanceof boolean[] && e2 instanceof boolean[])
                eq = Arrays.equals((boolean[]) e1, (boolean[]) e2);
            else
                eq = e1.equals(e2);

            if (!eq)
                return false;

            if (size() == 1) {
                break;
            }
            i = i.n;
            j = j.n;
        } while (i != end);

        return true;
    }
}
