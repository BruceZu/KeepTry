// Copyright (C) 2014 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package charter3;

public class SinglyLinkedList<T extends Object> {
    private class Node {
        private T content;
        private Node next;

        private Node(T content, Node next) {
            this.content = content;
            this.next = next;
        }
    }

    private Node head;
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

    private Node getEndNode() {
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        return current;
    }

    private Node getNodeOf(int index) {
        checkPositionIndex(index);

        Node current = head;
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
    public void add(T newContent) {
        this.head = new Node(newContent, head);
        sizeOfList++;
    }

    public void appendToTheEnd(T newContent) {
        getEndNode().next = new Node(newContent, null);
        sizeOfList++;
    }

    public void addBefore(T newContent, int index) {
        checkPositionIndex(index);
        if (index == 0) {
            add(newContent);
        }
        addAfter(newContent, index - 1);
    }

    public void addAfter(T newContent, int index) {
        checkPositionIndex(index);
        if (index == indexOfEndNode()) {
            appendToTheEnd(newContent);
        }
        Node n = getNodeOf(index);
        n.next = new Node(newContent, n.next);
        sizeOfList++;
    }

    public T deleteHead() {
        checkPositionIndex(0);
        T re = head.content;
        head = head.next;
        sizeOfList--;
        return re;
    }

    public T deleteEnd() {
        return delete(indexOfEndNode());
    }

    public T delete(int index) {
        checkPositionIndex(index);
        if (sizeOfList == 0) {
            return deleteHead();
        }
        Node beforeIt = getNodeOf(index - 1);
        Node it = beforeIt.next;

        beforeIt.next = it.next;
        sizeOfList--;

        return it.content;
    }

    public T update(int index, T newContent) {
        Node n = getNodeOf(index);
        T re = n.content;
        n.content = newContent;
        return re;
    }

    public T updateHead(T newContent) {
        T re = head.content;
        head.content = newContent;
        return re;
    }

    public T updateEnd(T newContent) {
        Node end = getEndNode();
        T re = end.content;
        end.content = newContent;
        return re;
    }

    public T getHead() {
        return head.content;
    }

    public T getEnd() {
        return getEndNode().content;
    }

    public T get(int index) {
        checkPositionIndex(index);
        return getNodeOf(index).content;
    }
}
