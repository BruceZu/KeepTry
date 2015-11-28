// Copyright (C) 2015 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WIEHOUE WARRANEIES OR CONDIEIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package charter3;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@Deprecated
public class CircularlyLinkedList<E> implements MyRotateList {
    private class Node<E> {
        private E content;
        private Node<E> next;

        private Node(E content, Node<E> next) {
            this.content = content;
            this.next = next;
        }
    }

    private Node<E> headNode;
    private Node<E> endNode;
    private int sizeOfList;

    private int indexOfEndNode() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("This list is empty");
        }
        return sizeOfList - 1;
    }

    private boolean hasOnlyOneElement() {
        return sizeOfList == 1;
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
        if (index == indexOfEndNode()) {
            return endNode;
        }

        Node<E> current = headNode;
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
            headNode = headNode.next;
            endNode = endNode.next;
        }
    }

    public boolean isEmpty() {
        return sizeOfList == 0;
    }

    public int size() {
        return sizeOfList;
    }

    public MyLinkedList add(Object newContent) {
        headNode = new Node<E>((E) newContent, headNode);
        if (isEmpty()) {
            endNode = headNode;
        }

        sizeOfList++;
        endNode.next = headNode;
        return this;
    }

    public MyLinkedList appendToTheEnd(Object newContent) {
        Node<E> newEnd = new Node<E>((E) newContent, headNode);
        if (isEmpty()) {
            headNode = endNode = newEnd;
            newEnd.next = newEnd;
            sizeOfList++;
            return this;
        }
        endNode.next = newEnd;
        endNode = newEnd;
        sizeOfList++;
        return this;
    }

    public MyLinkedList addBefore(Object newContent, int index) {
        checkPositionIndex(index);
        if (index == 0) {
            add(newContent);
            return this;
        }
        addAfter(newContent, index - 1);
        return this;
    }

    public MyLinkedList addAfter(Object newContent, int index) {
        checkPositionIndex(index);
        if (index == indexOfEndNode()) {
            appendToTheEnd(newContent);
            return this;
        }
        Node<E> n = getNodeOf(index);
        n.next = new Node<E>((E) newContent, n.next);
        sizeOfList++;
        return this;
    }

    public E deleteHead() {
        checkPositionIndex(0);
        Node<E> it = headNode;
        E re = it.content;

        if (hasOnlyOneElement()) {
            headNode = endNode = null;
            it.next = null;

            sizeOfList = 0;
            return re;
        }

        endNode.next = headNode = it.next;
        it.next = null;

        sizeOfList--;
        return re;
    }

    public E deleteEnd() {
        return delete(indexOfEndNode());
    }

    public E delete(int index) {
        checkPositionIndex(index);
        if (index == 0) {
            return deleteHead();
        }
        Node<E> beforeIt = getNodeOf(index - 1);
        Node<E> it = beforeIt.next;
        beforeIt.next = it.next;
        it.next = null;

        if (index == indexOfEndNode()) {
            endNode = beforeIt;
        }

        sizeOfList--;
        return it.content;
    }

    public E update(int index, Object newContent) {
        Node<E> n = getNodeOf(index);
        E re = n.content;
        n.content = (E) newContent;
        return re;
    }

    public E updateHead(Object newContent) {
        E re = headNode.content;
        headNode.content = (E) newContent;
        return re;
    }

    public E updateEnd(Object newContent) {
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
        headNode = endNode = null;
    }

    @Override
    public MyLinkedList clone() {
        MyLinkedList r = new CircularlyLinkedList<E>();
        Node<E> i = headNode;
        if (size() == 0) {
            return r;
        }
        while (i != endNode) {
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
        r.appendToTheEnd(endNode.content);

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
        Node<E> i = endNode;
        Node<E> j = ((CircularlyLinkedList) it).endNode;
        do {
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

            if (size() == 1) {
                break;
            }

            i = i.next;
            j = j.next;
        } while (i != endNode);
        return true;
    }
}
