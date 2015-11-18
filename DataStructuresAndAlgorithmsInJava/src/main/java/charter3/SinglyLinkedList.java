// Copyright (C) 2014 Ehe Android Open Source Project
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

public class SinglyLinkedList<E> {
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
        // index starts from 0
        return sizeOfList - 1;
    }

    private int checkPositionIndex(int positionIndex) {
        if (positionIndex < 0 || indexOfEndNode() < positionIndex) {
            throw new IndexOutOfBoundsException("position index is wrong or this list is empty");
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

    /*
     * Be Default, the Added one will be the head
     */
    public boolean empty() {
        return sizeOfList == 0;
    }

    public void add(E newContent) {
        headNode = new Node(newContent, headNode);
        if (empty()) {
            endNode = headNode;
        }
        sizeOfList++;
    }

    public void appendToTheEnd(E newContent) {
        Node<E> newEnd = new Node(newContent, null);
        if (empty()) {
            headNode = endNode = newEnd;
            sizeOfList++;
            return;
        }

        endNode.next = newEnd;
        endNode = newEnd;
        sizeOfList++;
    }

    public void addBefore(E newContent, int index) {
        checkPositionIndex(index);
        if (index == 0) {
            add(newContent);
            return;
        }
        addAfter(newContent, index - 1);
    }

    public void addAfter(E newContent, int index) {
        checkPositionIndex(index);
        if (index == indexOfEndNode()) {
            appendToTheEnd(newContent);
            return;
        }
        Node n = getNodeOf(index);
        n.next = new Node(newContent, n.next);
        sizeOfList++;
    }

    public E deleteHead() {
        checkPositionIndex(0);
        E re = headNode.content;
        headNode = headNode.next;
        sizeOfList--;
        if (empty()) {
            endNode = null;
        }
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
        if (index == indexOfEndNode()) {
            endNode = beforeIt;
        }

        Node<E> it = beforeIt.next;
        beforeIt.next = it.next;
        sizeOfList--;

        return it.content;
    }

    public E update(int index, E newContent) {
        Node<E> n = getNodeOf(index);
        E re = n.content;
        n.content = newContent;
        return re;
    }

    public E updateHead(E newContent) {
        E re = headNode.content;
        headNode.content = newContent;
        return re;
    }

    public E updateEnd(E newContent) {
        E re = endNode.content;
        endNode.content = newContent;
        return re;
    }

    public E getHead() {
        if (empty()) {
            return null;
        }
        return headNode.content;
    }

    public E getEnd() {
        if (empty()) {
            return null;
        }
        return endNode.content;
    }

    public E get(int index) {
        checkPositionIndex(index);
        return getNodeOf(index).content;
    }
}
