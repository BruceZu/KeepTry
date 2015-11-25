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

//TODO:  support multi threads access concurrently.
public class DoublyLinkedList2<E> implements MyLinkedList {
    private class Node<E> {
        private E content;
        private Node<E> prev;
        private Node<E> next;

        private Node(E content, Node<E> prev, Node<E> next) {
            this.content = content;
            this.prev = prev;
            this.next = next;
        }

        private Node(E content) {
            this.content = content;
        }

        private Node() {
        }
    }

    private final Node<E> endSentinel = new Node<E>();
    private final Node<E> headSentinel = new Node<E>();
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
            current = headSentinel.next;
            while (i != index) {
                current = current.next;
                i++;
            }
            return current;
        }
        int i = indexOfEndNode();
        current = endSentinel.prev;
        while (i != index) {
            current = current.prev;
            i--;
        }
        return current;
    }

    private void addBetween(E newContent, Node p, Node n) {
        Node<E> it = new Node<E>(newContent);
        p.next = it;
        it.next = n;
        n.prev = it;
        it.prev = p;
        sizeOfList++;
    }

    private void deleteBetween(Node p, Node n) {
        p.next = n;
        n.prev = p;
        sizeOfList--;
    }

    private E delete(int itsIndex, Node<E> it) {
        checkPositionIndex(itsIndex);
        deleteBetween(it.prev, it.next);
        return it.content;
    }

    public DoublyLinkedList2() {
        headSentinel.next = endSentinel;
        endSentinel.prev = headSentinel;
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

    public void add(Object newContent) {
        addBetween((E) newContent, headSentinel, headSentinel.next);
    }

    public void appendToTheEnd(Object newContent) {
        addBetween((E) newContent, endSentinel.prev, endSentinel);
    }

    public void addBefore(Object newContent, int index) {
        checkPositionIndex(index);
        Node<E> i = getNodeOf(index);
        addBetween((E) newContent, i.prev, i);
    }

    public void addAfter(Object newContent, int index) {
        checkPositionIndex(index);
        Node<E> i = getNodeOf(index);
        addBetween((E) newContent, i, i.next);
    }

    public E deleteHead() {
        return delete(0, headSentinel.next);
    }

    public E deleteEnd() {
        return delete(indexOfEndNode(), endSentinel.prev);
    }

    public E delete(int index) {
        Node<E> it = getNodeOf(index);
        return delete(index, it);
    }

    public E update(int index, Object newContent) {
        Node<E> n = getNodeOf(index);
        E re = n.content;
        n.content = (E) newContent;
        return re;
    }

    public E updateHead(Object newContent) {
        E re = headSentinel.next.content;
        headSentinel.next.content = (E) newContent;
        return re;
    }

    public E updateEnd(Object newContent) {
        E re = endSentinel.prev.content;
        endSentinel.prev.content = (E) newContent;
        return re;
    }

    public E getHead() {
        if (isEmpty()) {
            return null;
        }
        return headSentinel.next.content;
    }

    public E getEnd() {
        if (isEmpty()) {
            return null;
        }
        return endSentinel.prev.content;
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
        endSentinel.next = endSentinel;
        endSentinel.prev = headSentinel;
    }

    @Override
    public MyLinkedList clone() {
        return null;
    }

    @Override
    public boolean equal(Object withit) {
        return false;
    }
}
