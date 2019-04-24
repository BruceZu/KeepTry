//  Copyright 2017 The keepTry Open Source Project
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

package graph.shortestpath.weightedgraphs;

import java.util.AbstractQueue;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class Node implements Comparable {
    // assume this is id and be distinguish
    // when label it as digit from 0 to n-1, the Node can be
    // translated to 3 array
    String name;
    Map<Node, Integer> neighborDistance; // immediate neighbors

    Integer shortDisFromStart;
    Node pre; // predecessor Node

    public Node(String name, Map<Node, Integer> distanceToAdjacentNode) {
        this.name = name;
        this.neighborDistance = distanceToAdjacentNode;
        this.shortDisFromStart = Integer.MAX_VALUE;
        this.pre = null;
    }

    public Node(
            String name,
            Map<Node, Integer> distanceToAdjacentNode,
            int startNodeTentativeShortestDistanceFromStart) {
        this.name = name;
        this.neighborDistance = distanceToAdjacentNode;
        this.shortDisFromStart = startNodeTentativeShortestDistanceFromStart;
        this.pre = null;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        Node with = (Node) o;
        return this.equals(with) ? 0 : this.shortDisFromStart.compareTo(with.shortDisFromStart);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Node with = (Node) obj;
        return this.name.equals(with.name);
    }
}

class IBinaryHeap extends AbstractQueue<Node> implements java.io.Serializable {

    int size;
    Node[] heap;
    private Map<Node, Integer>
            indexes; // position in binary heap, null means it is not is binary heap

    private void doubleSize() {
        Node[] old = heap;
        heap = new Node[heap.length * 2];

        System.arraycopy(old, 1, heap, 1, size);
    }

    private void percolatingDown(Node n, int pos) {
        int child;
        for (; 2 * pos <= size; pos = child) {
            child = 2 * pos;
            Node c = heap[child];
            if (child + 1 <= size && c.compareTo(heap[child + 1]) > 0) {
                c = heap[child = child + 1];
            }

            if (n.compareTo(c) < 0) {
                break;
            }
            heap[pos] = c;
            indexes.put(c, pos);
        }
        heap[pos] = n;
        indexes.put(n, pos);
    }

    public IBinaryHeap(int capacity) {
        // keep data from index 1: calculate parent with index/2. child: 2＊k, 2＊k+1
        // (keep data from index 0: calculate parent with (index-1)/2.child: 2＊k+1,2＊k+2)
        size = 0; // current size of nodes
        heap = new Node[capacity];
        indexes = new HashMap();
    }

    public Integer index(Node n) {
        return indexes.get(n);
    }

    public void shiftUp(Node n, int pos) {
        for (; pos > 1 && n.compareTo(heap[pos / 2]) < 0; pos = pos / 2) {
            heap[pos] = heap[pos / 2];
            indexes.put(heap[pos], pos);
        }
        heap[pos] = n;
        indexes.put(n, pos);
    }

    public boolean offer(Node n) {
        if (n == null) throw new NullPointerException();
        if (size == heap.length - 1) {
            doubleSize();
        }
        int pos = ++size;
        if (pos == 1) {
            heap[pos] = n;
            indexes.put(n, pos);

        } else {
            shiftUp(n, pos);
        }
        return true;
    }

    public Node poll() {
        if (size == 0) {
            throw null;
        }
        Node min = heap[1];
        int s = size--;

        Node n = heap[s];
        heap[s] = null;

        if (s != 1) {
            percolatingDown(n, 1);
        }
        return min;
    }

    @Override
    public Iterator<Node> iterator() {
        throw new UnsupportedOperationException("iterator");
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Node peek() {
        return (size == 0) ? null : heap[1];
    }
}
