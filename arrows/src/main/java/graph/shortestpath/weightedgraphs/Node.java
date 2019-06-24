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

import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class Node implements Comparable<Node> {
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
    public int compareTo(@NotNull Node o) {
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
