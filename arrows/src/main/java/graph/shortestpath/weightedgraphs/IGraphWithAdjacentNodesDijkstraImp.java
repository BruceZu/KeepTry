//  Copyright 2019 The KeepTry Open Source Project
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

import graph.Edge;
import graph.IBinaryHeap;
import graph.IGraphWithAdjacentNodesAbstractImp;
import graph.IVertex;
import graph.Node;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IGraphWithAdjacentNodesDijkstraImp extends IGraphWithAdjacentNodesAbstractImp {
    Node end;

    public IGraphWithAdjacentNodesDijkstraImp(List<Node> nodes, Node start, Node end) {
        this.cur = start;
        this.end = end;
        this.nodes = nodes;
        evaluating = new IBinaryHeap<>(nodes.size());
        evaluated = new HashSet<>(nodes.size());
    }

    @Override
    public Status currentStatus() {
        if (evaluating.isEmpty()
                || evaluating.peek().getShortestDistanceToI() == Integer.MAX_VALUE) {
            return Status.SP_NO_RESULT; // no way
        }

        if (evaluating.peek() == end) {
            return Status.DONE;
        }
        return Status.ING;
    }

    @Override
    public Set getMstOrSp() {
        Set<Edge> r = new HashSet<>();
        IVertex i = end;
        while (i.getVertexToI() != null) {
            r.add(
                    new Edge(
                            i.getName(),
                            i.getVertexToI().getName(),
                            (Integer) i.getVertexToI().getNeighborWeighMap().get(i)));
            i = i.getVertexToI();
        }

        return r;
    }
}
