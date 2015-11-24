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

    @Override
    public MyLinkedList clone() {
        return null;
    }

    @Override
    public boolean equal(Object withit) {
        return false;
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

    public void add(Object newContent) {
        Node<E> newNode = new Node<E>((E) newContent, null, null);
        if (isEmpty()) {
            headNode = endNode = newNode;
            sizeOfList++;
            return;
        }

        newNode.next = headNode;
        headNode = newNode;
        headNode.next.prev = headNode;
        sizeOfList++;
    }

    public void appendToTheEnd(Object newContent) {
        Node<E> newNode = new Node<E>((E) newContent, null, null);
        if (isEmpty()) {
            headNode = endNode = newNode;
            sizeOfList++;
            return;
        }

        newNode.prev = endNode;
        endNode = newNode;
        endNode.prev.next = newNode;
        sizeOfList++;
    }

    public void addBefore(Object newContent, int index) {
        checkPositionIndex(index);
        if (index == 0) {
            add(newContent);
            return;
        }
        Node<E> i = getNodeOf(index);
        Node<E> newNode = new Node<E>((E) newContent, i.prev, i);
        i.prev.next = newNode;
        i.prev = newNode;
        sizeOfList++;
    }

    public void addAfter(Object newContent, int index) {
        checkPositionIndex(index);
        if (index == indexOfEndNode()) {
            appendToTheEnd(newContent);
            return;
        }
        Node<E> i = getNodeOf(index);
        Node<E> newNode = new Node<E>((E) newContent, i, i.next);
        i.next.prev = newNode;
        i.next = newNode;
        sizeOfList++;
    }

    public E deleteHead() {
        checkPositionIndex(0);
        E re = headNode.content;
        if (hasOnlyOneElement()) {
            headNode = endNode = null;
            sizeOfList = 0;
            return re;
        }

        headNode = headNode.next;
        headNode.prev = null;
        sizeOfList--;
        return re;
    }

    public E deleteEnd() {
        checkPositionIndex(indexOfEndNode());
        E re = endNode.content;
        if (hasOnlyOneElement()) {
            headNode = endNode = null;
            sizeOfList = 0;
            return re;
        }

        endNode = endNode.prev;
        endNode.next = null;
        sizeOfList--;
        return re;
    }

    public E delete(int index) {
        checkPositionIndex(index);
        if (index == 0) {
            return deleteHead();
        }
        if (index == indexOfEndNode()) {
            return deleteEnd();
        }

        Node<E> beforeIt = getNodeOf(index - 1);
        Node<E> it = beforeIt.next;
        beforeIt.next = it.next;
        it.next.prev = beforeIt;
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
        endNode = headNode = null;
    }
}
