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
import graph.IGraphWithAdjacentNodesAbstractImp;
import graph.IVertex;
import graph.Node;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class IGraphWithAdjacentNodesDijkstraImp extends IGraphWithAdjacentNodesAbstractImp {
    Node end;

    public IGraphWithAdjacentNodesDijkstraImp(List<Node> nodes, Node start, Node end) {
        super(nodes, start);
        this.end = end;
    }

    @Override
    public Status currentStatus() {
        if (q.isEmpty()
                || q.peek().getShortestDistanceToI() == Integer.MAX_VALUE) {
            return Status.SP_NO_RESULT; // no way
        }

        if (q.peek() == end) {
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

    @Override
    public BiFunction<IVertex<Node>, IVertex<Node>, Integer> getDistCalculator() {
        return (i, neighbor) -> i.getShortestDistanceToI() + i.getNeighborWeighMap().get(neighbor);
    }
}
