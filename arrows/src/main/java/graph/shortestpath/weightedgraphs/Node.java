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

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Node implements Comparable {
    // assume this is id and be distinguish
    // when label it as digit from 0 to n-1, the Node can be
    // translated to 3 array
    String name;
    Map<Node, Integer> distanceToAdjacentNode; //immediate neighbors

    int tentativeShortestDistanceFromStart;
    Node predecessorNode;

    public Node(String name, Map<Node, Integer> distanceToAdjacentNode) {
        this.name = name;
        this.distanceToAdjacentNode = distanceToAdjacentNode;
        this.tentativeShortestDistanceFromStart = Integer.MAX_VALUE;
        this.predecessorNode = null;
    }

    public Node(String name, Map<Node, Integer> distanceToAdjacentNode, int startNodeTentativeShortestDistanceFromStart) {
        this.name = name;
        this.distanceToAdjacentNode = distanceToAdjacentNode;
        this.tentativeShortestDistanceFromStart = startNodeTentativeShortestDistanceFromStart;
        this.predecessorNode = null;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        return this.tentativeShortestDistanceFromStart - ((Node) o).tentativeShortestDistanceFromStart;
    }
}