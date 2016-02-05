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
public class DoublyLinkedList<E> implements MyLinkedList {
    private class Node<E> {
        private E content;
        private Node<E> prev;
        private Node<E> next;

        private Node(E content, Node<E> prev, Node<E> next) {
            this.content = content;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<E> endNode;
    private Node<E> headNode;
    private int sizeOfList;

    private int indexOfEndNode() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("This list is empty");
        }
        return sizeOfList - 1;
    }

    private int checkPositionIndex(int positionIndex) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("This list is empty");
        }
        if (positionIndex < 0 || indexOfEndNode() < positionIndex) {
            throw new IndexOutOfBoundsException("Index is wrong ");
        }
        return positionIndex;
    }

    private Node<E> getNodeOf(int index) {
        checkPositionIndex(index);
        Node<E> current;
        if (index < size() / 2) {
            int i = 0;
            current = headNode;
            while (i != index) {
                current = current.next;
                i++;
            }
            return current;
        }
        int i = indexOfEndNode();
        current = endNode;
        while (i != index) {
            current = current.prev;
            i--;
        }
        return current;
    }

    public boolean isEmpty() {
        return sizeOfList == 0;
    }

    public int size() {
        return sizeOfList;
    }

    public boolean hasOnlyOneElement() {
        return sizeOfList == 1;
    }

    public MyLinkedList addToTheHead(Object newContent) {
        requireNonNull(newContent);
        Node<E> newNode = new Node<E>((E) newContent, null, null);
        if (isEmpty()) {
            headNode = endNode = newNode;
            sizeOfList++;
            return this;
        }

        newNode.next = headNode;
        headNode = newNode;
        headNode.next.prev = headNode;
        sizeOfList++;
        return this;
    }

    public MyLinkedList appendToTheEnd(Object newContent) {
        requireNonNull(newContent);
        Node<E> newNode = new Node<E>((E) newContent, null, null);
        if (isEmpty()) {
            headNode = endNode = newNode;
            sizeOfList++;
            return this;
        }

        newNode.prev = endNode;
        endNode = newNode;
        endNode.prev.next = newNode;
        sizeOfList++;
        return this;
    }

    public MyLinkedList addBefore(Object newContent, int index) {
        requireNonNull(newContent);
        checkPositionIndex(index);
        if (index == 0) {
            addToTheHead(newContent);
            return this;
        }
        Node<E> i = getNodeOf(index);
        Node<E> newNode = new Node<E>((E) newContent, i.prev, i);
        i.prev.next = newNode;
        i.prev = newNode;
        sizeOfList++;
        return this;
    }

    public MyLinkedList addAfter(Object newContent, int index) {
        requireNonNull(newContent);
        checkPositionIndex(index);
        if (index == indexOfEndNode()) {
            appendToTheEnd(newContent);
            return this;
        }
        Node<E> i = getNodeOf(index);
        Node<E> newNode = new Node<E>((E) newContent, i, i.next);
        i.next.prev = newNode;
        i.next = newNode;
        sizeOfList++;
        return this;
    }

    private E deleteTheOnlyOne() {
        E re = headNode.content;
        headNode = endNode = null;
        sizeOfList = 0;
        return re;
    }

    public E deleteHead() {
        checkPositionIndex(0);
        if (hasOnlyOneElement()) {
            return deleteTheOnlyOne();
        }
        Node<E> pre = null;
        Node<E> it = headNode;
        Node<E> next = it.next;

        headNode = next;

        next.prev = pre;
        it.next = null;
        sizeOfList--;
        return it.content;
    }

    public E deleteEnd() {
        checkPositionIndex(indexOfEndNode());
        if (hasOnlyOneElement()) {
            return deleteTheOnlyOne();
        }

        Node<E> pre = endNode.prev;
        Node<E> it = endNode;
        Node<E> next = null;

        endNode = pre;

        pre.next = next;
        it.prev = null;
        sizeOfList--;
        return it.content;
    }

    @Override
    public int size2() {
        return size();//TODO
    }

    public E delete(int index) {
        checkPositionIndex(index);
        if (index == 0) {
            return deleteHead();
        }
        if (index == indexOfEndNode()) {
            return deleteEnd();
        }

        Node<E> pre = getNodeOf(index - 1);
        Node<E> it = pre.next;
        Node<E> next = it.next;

        pre.next = next;
        next.prev = pre;
        it.next = it.prev = null;

        sizeOfList--;
        return it.content;
    }

    public E update(int index, Object newContent) {
        requireNonNull(newContent);
        Node<E> n = getNodeOf(index);
        E re = n.content;
        n.content = (E) newContent;
        return re;
    }

    public E updateHead(Object newContent) {
        requireNonNull(newContent);
        E re = headNode.content;
        headNode.content = (E) newContent;
        return re;
    }

    public E updateEnd(Object newContent) {
        requireNonNull(newContent);
        E re = endNode.content;
        endNode.content = (E) newContent;
        return re;
    }

    public E getHead() {
        if (isEmpty()) {
            return null;
        }
        return headNode.content;
    }

    public E getEnd() {
        if (isEmpty()) {
            return null;
        }
        return endNode.content;
    }

    public E get(int index) {
        checkPositionIndex(index);
        return getNodeOf(index).content;
    }

    @Override
    public void clean() {
        if (size() == 0) {
            return;
        }
        sizeOfList = 0;
        endNode = headNode = null;
    }

    @Override
    public MyLinkedList clone() {
        MyLinkedList r = new DoublyLinkedList<E>();
        Node<E> i = headNode;
        while (i != null) {
            E e = i.content;
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
                    r.appendToTheEnd(eClone);
                } catch (NoSuchMethodException |
                        IllegalAccessException |
                        InvocationTargetException ex) {
                    r.appendToTheEnd(e);
                }
            } else {
                r.appendToTheEnd(e);
            }
            i = i.next;
        }
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
        if (!(it instanceof DoublyLinkedList) || ((DoublyLinkedList) it).size() != this.size()) {
            return false;
        }
        if (size() == ((DoublyLinkedList) it).size() && size() == 0) {
            return true;
        }
        Node<E> i = headNode;
        Node<E> j = ((DoublyLinkedList) it).headNode;
        while (i != null) {
            E e1 = i.content;
            E e2 = j.content;

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
            i = i.next;
            j = j.next;
        }
        return true;
    }
}
