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

public interface IVertex<N extends IVertex> {
    Map<N, Integer> getNeighborWeighMap();

    int getShortestDistanceToI(); // from MST or from cur point for calculating SP

    void setShortestDistanceToI(int v);

    N getVertexToI();

    void setVertexToI(IVertex v);

    int getId();

    String getName();
}
