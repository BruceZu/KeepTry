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

import java.util.Set;
import java.util.function.BiFunction;

/**
 * This is designed for calculating SP with Dijkstra's algorithm and MST with Prim's algorithm
 *
 * @param <V>
 * @param <E>
 */
public interface IGraph<V extends IVertex, E extends Edge> {
    enum Status {
        SP_NO_RESULT, // only for shortest path.
        ING,
        DONE
    }

    void initVertexDistanceStatus();

    Status currentStatus();

    void selectCurrentVertex();

    void updateCutWithCurrentVertex(BiFunction<V, V, Integer> distCalculator);

    Set<E> getMstOrSp();

    BiFunction<V, V, Integer> getDistCalculator();
}
