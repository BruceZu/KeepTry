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

package graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public abstract class IGraphWithAdjacentNodesAbstractImp implements IGraph {
    protected List<Node> nodes;
    protected IBinaryHeap<Node> evaluating;
    protected Node cur;
    protected Set<Node> evaluated;

    public IGraphWithAdjacentNodesAbstractImp(List<Node> nodes, Node start) {
        this.cur = start;
        this.nodes = nodes;
        evaluating = new IBinaryHeap<>(nodes.size());
        evaluated = new HashSet<>(nodes.size());
    }

    @Override
    public void initVertexDistanceStatus() {
        nodes.stream().forEach(e -> e.setShortestDistanceToI(Integer.MAX_VALUE));
        cur.setShortestDistanceToI(0);
        evaluating.offer(cur);
    }

    @Override
    public void selectCurrentVertex() {
        cur = evaluating.poll();
        evaluated.add(cur);
    }

    @Override
    public void updateCutWithCurrentVertex(BiFunction distCalculator) {
        for (Node neighbor : cur.getNeighborWeighMap().keySet()) {
            if (!evaluated.contains(neighbor)) {
                int viaCur = (Integer) distCalculator.apply(cur, neighbor);

                if (viaCur < 0) { // distance may be MAX_VALUE
                    viaCur = Integer.MAX_VALUE;
                }
                if (viaCur < neighbor.getShortestDistanceToI()) {
                    neighbor.setShortestDistanceToI(viaCur);
                    neighbor.setVertexToI(cur);
                    if (evaluating.contains(neighbor)) {
                        evaluating.shiftUp(neighbor); // O(logN)
                    }
                }
                if (!evaluating.contains(neighbor)) {
                    evaluating.offer(neighbor); // O(logN)
                }
            }
        }
    }
}
