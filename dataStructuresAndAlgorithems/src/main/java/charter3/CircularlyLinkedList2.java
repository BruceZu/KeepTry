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
public class CircularlyLinkedList2<E> implements MyRotateList {
    private class Node<E> {
        private E content;
        private Node<E> next;

        private Node(E content, Node<E> next) {
            this.content = content;
            this.next = next;
        }
    }

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

    @Override
    public MyLinkedList clone() {
        return null;
    }

    @Override
    public boolean equals(Object it) {
        return false;
    }

    private Node<E> getNodeOf(int index) {
        checkPositionIndex(index);
        if (index == indexOfEndNode()) {
            return endNode;
        }

        Node<E> current = endNode.next;
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
            endNode = endNode.next;
        }
    }

    public boolean isEmpty() {
        return sizeOfList == 0;
    }

    public int size() {
        return sizeOfList;
    }

    public void add(Object newContent) {
        Node<E> newNode = new Node<E>((E) newContent, null);
        if (isEmpty()) {
            endNode = newNode;
            newNode.next = newNode;
            sizeOfList++;
            return;
        }

        newNode.next = endNode.next;
        endNode.next = newNode;
        sizeOfList++;
    }

    public void appendToTheEnd(Object newContent) {
        add(newContent);
        endNode = endNode.next;
    }

    public void addBefore(Object newContent, int index) {
        checkPositionIndex(index);
        if (index == 0) {
            add(newContent);
            return;
        }
        addAfter(newContent, index - 1);
    }

    public void addAfter(Object newContent, int index) {
        checkPositionIndex(index);
        if (index == indexOfEndNode()) {
            appendToTheEnd(newContent);
            return;
        }
        Node<E> n = getNodeOf(index);
        n.next = new Node<E>((E) newContent, n.next);
        sizeOfList++;
    }

    public E deleteHead() {
        checkPositionIndex(0);
        Node<E> it;
        if (hasOnlyOneElement()) {
            it = endNode;
            it.next = null;
            endNode = null;

            sizeOfList = 0;
            return it.content;
        }
        it = endNode.next;
        endNode.next = it.next;
        it.next = null;

        sizeOfList--;
        return it.content;
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
        E re = endNode.next.content;
        endNode.next.content = (E) newContent;
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
        return endNode.next.content;
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
        endNode = null;
    }
}
