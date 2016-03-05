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

@Deprecated
public class CircularlyLinkedList<E> implements MyRotateList {
    private class Node<E> {
        private E e;
        private Node<E> next;

        private Node(E e, Node<E> next) {
            this.e = e;
            this.next = next;
        }
    }

    private Node<E> head;
    private Node<E> end;
    private int size;

    private int endIndex() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("This list is empty");
        }
        return size - 1;
    }

    private boolean isOnlyOneNode() {
        return size == 1;
    }

    private int checkPositionIndex(int positionIndex) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("This list is empty");
        }
        if (positionIndex < 0 || endIndex() < positionIndex) {
            throw new IndexOutOfBoundsException("Index is wrong ");
        }
        return positionIndex;
    }

    private Node<E> nodeOf(int index) {
        checkPositionIndex(index);
        if (index == endIndex()) {
            return end;
        }

        Node<E> current = head;
        int i = 0;
        while (i != index) {
            current = current.next;
            i++;
        }
        return current;
    }

    @Override
    public void rotate() {
        if (size() > 1) {
            head = head.next;
            end = end.next;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public int size2() {
        return size();//TODO
    }

    public MyLinkedList addBeforeHead(Object newContent) {
        requireNonNull(newContent);
        head = new Node<E>((E) newContent, head);
        if (isEmpty()) {
            end = head;
        }

        size++;
        end.next = head;
        return this;
    }

    public MyLinkedList appendAfterEnd(Object newContent) {
        requireNonNull(newContent);
        Node<E> newEnd = new Node<E>((E) newContent, head);
        if (isEmpty()) {
            head = end = newEnd;
            newEnd.next = newEnd;
            size++;
            return this;
        }
        end.next = newEnd;
        end = newEnd;
        size++;
        return this;
    }

    public MyLinkedList addBefore(Object newContent, int index) {
        requireNonNull(newContent);
        checkPositionIndex(index);
        if (index == 0) {
            addBeforeHead(newContent);
            return this;
        }
        addAfter(newContent, index - 1);
        return this;
    }

    public MyLinkedList addAfter(Object newContent, int index) {
        requireNonNull(newContent);
        checkPositionIndex(index);
        if (index == endIndex()) {
            appendAfterEnd(newContent);
            return this;
        }
        Node<E> n = nodeOf(index);
        n.next = new Node<E>((E) newContent, n.next);
        size++;
        return this;
    }

    public E deleteHead() {
        checkPositionIndex(0);
        Node<E> it = head;
        E re = it.e;

        if (isOnlyOneNode()) {
            head = end = null;
            it.next = null;

            size = 0;
            return re;
        }

        end.next = head = it.next;
        it.next = null;

        size--;
        return re;
    }

    public E deleteEnd() {
        return delete(endIndex());
    }

    public E delete(int index) {
        checkPositionIndex(index);
        if (index == 0) {
            return deleteHead();
        }
        Node<E> beforeIt = nodeOf(index - 1);
        Node<E> it = beforeIt.next;
        beforeIt.next = it.next;
        it.next = null;

        if (index == endIndex()) {
            end = beforeIt;
        }

        size--;
        return it.e;
    }

    public E update(int index, Object newContent) {
        requireNonNull(newContent);
        Node<E> n = nodeOf(index);
        E re = n.e;
        n.e = (E) newContent;
        return re;
    }

    public E updateHead(Object newContent) {
        requireNonNull(newContent);
        E re = head.e;
        head.e = (E) newContent;
        return re;
    }

    public E updateEnd(Object newContent) {
        requireNonNull(newContent);
        E re = end.e;
        end.e = (E) newContent;
        return re;
    }

    public E getHead() {
        if (isEmpty()) {
            return null;
        }
        return head.e;
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
        head = end = null;
    }

    @Override
    public MyLinkedList clone() {
        MyLinkedList r = new CircularlyLinkedList<E>();
        Node<E> i = head;
        if (size() == 0) {
            return r;
        }
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
            i = i.next;
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
        if (!(it instanceof CircularlyLinkedList) || ((CircularlyLinkedList) it).size() != this.size()) {
            return false;
        }
        if (size() == ((CircularlyLinkedList) it).size() && size() == 0) {
            return true;
        }
        Node<E> i = end;
        Node<E> j = ((CircularlyLinkedList) it).end;
        do {
            E e1 = i.e;
            E e2 = j.e;

            if (e1 == e2) {
                i = i.next;
                j = j.next;
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

            i = i.next;
            j = j.next;
        } while (i != end);
        return true;
    }
}
