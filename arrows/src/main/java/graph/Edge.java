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

import org.jetbrains.annotations.NotNull;

class Edge implements Comparable<Edge> {
    int from, to, w;

    public Edge(int vertex1Id, int vertex2Id, int weight) {
        this.from = vertex1Id;
        this.to = vertex2Id;
        this.w = weight;
    }

    @Override
    public int compareTo(@NotNull Edge o) {
        return this.w - o.w;
    }

    @Override
    public String toString() {
        return from + "-" + to + ", weight: " + w;
    }
}
