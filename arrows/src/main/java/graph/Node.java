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

import java.util.Map;
import org.jetbrains.annotations.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Node implements Comparable<Node>, IVertex {
    // assume this is id and be distinguish
    // when label it as digit from 0 to n-1, the Node can be
    // translated to 3 array
    private String name;
    private Map<Node, Integer> neighDistMap; // immediate neighbors

    private Integer shortestDisToI;
    private IVertex vertexToI; // predecessor Node

    public Node(String name, Map<Node, Integer> map) {
        this.name = name;
        this.neighDistMap = map;
    }

    @Override
    public int compareTo(@NotNull Node o) {
        Node with = o;
        return this.equals(with) ? 0 : this.shortestDisToI.compareTo(with.shortestDisToI);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Node with = (Node) obj;
        return this.name.equals(with.getName());
    }

    @Override
    public Map<Node, Integer> getNeighborWeighMap() {
        return neighDistMap;
    }

    @Override
    public int getShortestDistanceToI() {
        return this.shortestDisToI;
    }

    @Override
    public void setShortestDistanceToI(int v) {
        this.shortestDisToI = v;
    }

    @Override
    public IVertex getVertexToI() {
        return this.vertexToI;
    }

    @Override
    public void setVertexToI(IVertex v) {
        this.vertexToI = v;
    }

    @Override
    public int getId() {
        throw new NotImplementedException();
    }

    @Override
    public String getName() {
        return this.name;
    }
}
