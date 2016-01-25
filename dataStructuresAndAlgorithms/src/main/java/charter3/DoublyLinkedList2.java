//  Copyright 2016 The Minorminor Open Source Project
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

package charter3;


import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

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

    private void addBetween(@NotNull(value = "") E newContent,
                            @NotNull(value = "") Node p,
                            @NotNull(value = "") Node n) {
        Node<E> it = new Node<E>(newContent);
        p.next = it;
        it.next = n;
        n.prev = it;
        it.prev = p;
        sizeOfList++;
    }

    private void deleteBetween(@NotNull(value = "") Node p,
                               @NotNull(value = "") Node n) {
        p.next = n;
        n.prev = p;
        sizeOfList--;
    }

    private E delete(int itsIndex,
                     @NotNull(value = "") Node<E> it) {
        checkPositionIndex(itsIndex);
        deleteBetween(it.prev, it.next);
        it.prev = it.next = null;
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

    @Deprecated
    public int size2() {
        int size = 0;
        Node<E> i = headSentinel;
        while (i.next != endSentinel) {
            size++;
            i = i.next;
        }
        return size;
    }

    public boolean hasOnlyOneElement() {
        return sizeOfList == 1;
    }

    public MyLinkedList addToTheHead(@NotNull(value = "") Object newContent) {
        addBetween((E) newContent, headSentinel, headSentinel.next);
        return this;
    }

    public DoublyLinkedList2 appendToTheEnd(@NotNull(value = "") Object newContent) {
        addBetween((E) newContent, endSentinel.prev, endSentinel);
        return this;
    }

    public DoublyLinkedList2 addBefore(@NotNull(value = "") Object newContent, int index) {
        checkPositionIndex(index);
        Node<E> i = getNodeOf(index);
        addBetween((E) newContent, i.prev, i);
        return this;
    }

    public DoublyLinkedList2 addAfter(@NotNull(value = "") Object newContent, int index) {
        checkPositionIndex(index);
        Node<E> i = getNodeOf(index);
        addBetween((E) newContent, i, i.next);
        return this;
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

    public E update(int index, @NotNull(value = "") Object newContent) {
        Node<E> n = getNodeOf(index);
        E re = n.content;
        n.content = (E) newContent;
        return re;
    }

    public E updateHead(@NotNull(value = "") Object newContent) {
        E re = headSentinel.next.content;
        headSentinel.next.content = (E) newContent;
        return re;
    }

    public E updateEnd(@NotNull(value = "") Object newContent) {
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

    /**
     * Finding the middle node with header and trailer sentinels by “link hopping,”
     * and without relying on explicit knowledge of the size of the list.
     * In the case of an even number of nodes, report the node slightly left of
     * center as the “middle.”
     *
     * @return
     */
    @Deprecated
    public E getMiddle() {
        Node<E> l = headSentinel, r = endSentinel;
        while (true) {
            if (l.next == r) {
                return l.content;
            }
            l = l.next;
            if (l.next == r) {
                return l.content;
            }
            r = r.prev;
            if (l.next == r) {
                return l.content;
            }
        }
    }

    public E getMiddle2() {
        int size = size();
        Node<E> iNode = headSentinel.next;
        for (int i = 0; i < (size % 2 == 0 ? size / 2 - 1 : size / 2); i++) {
            iNode = iNode.next;
        }
        return iNode.content;
    }

    @Override
    public void clean() {
        if (size() == 0) {
            return;
        }
        sizeOfList = 0;
        headSentinel.next = endSentinel;
        endSentinel.prev = headSentinel;
    }

    @Override
    public MyLinkedList clone() {
        DoublyLinkedList2 r = new DoublyLinkedList2();
        Node<E> i = headSentinel.next;
        while (i != endSentinel) {
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
        if (it == this) {
            return true;
        }
        if (!(it instanceof DoublyLinkedList2) || this.size() != ((DoublyLinkedList2) it).size()) {
            return false;
        }
        if (size() == ((DoublyLinkedList2) it).size() && size() == 0) {
            return true;
        }
        Node<E> i = this.headSentinel.next;
        Node<E> j = ((DoublyLinkedList2<E>) it).headSentinel.next;
        while (i != this.endSentinel) {
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
