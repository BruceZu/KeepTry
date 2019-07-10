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

package graph.minimum_spanning_tree;

import graph.Edge;
import graph.IGraphWithAdjacentNodesAbstractImp;
import graph.IVertex;
import graph.Node;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class IGraphWithAdjacentNodesPrimImp extends IGraphWithAdjacentNodesAbstractImp {

    @Override
    public BiFunction<IVertex<Node>, IVertex<Node>, Integer> getDistCalculator() {
        return (i, neighbor) -> i.getNeighborWeighMap().get(neighbor);
    }

    public IGraphWithAdjacentNodesPrimImp(List<Node> nodes, Node start) {
        super(nodes, start);
    }

    private boolean isStartNode(IVertex v) {
        return v.getVertexToI() == null;
    }

    @Override
    public Status currentStatus() {
        return evaluated.size() < nodes.size() ? Status.ING : Status.DONE;
    }

    @Override
    public Set getMstOrSp() {

        Set<Edge> r = new HashSet<>(nodes.size());
        // Note the start node should not be taken in account

        for (IVertex n : nodes) {
            if (!isStartNode(n)) {
                String i = n.getName();
                String from = n.getVertexToI().getName();
                int w = (Integer) n.getVertexToI().getNeighborWeighMap().get(n);
                r.add(new Edge(i, from, w));
            }
        }

        return r;
    }
}
