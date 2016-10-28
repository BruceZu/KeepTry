//  Copyright 2016 The Sawdust Open Source Project
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

package graph.directed_graphs;

import java.util.Collections;
import java.util.List;

class Vertex {
    String value; // assume the value is distinguish. as ID of Vertex.
    List<Vertex> outgoings = Collections.emptyList(); // neighbors. Default value is empty, as end vertex.

    public Vertex(String v, List<Vertex> outgoings) {
        this.value = v;
        this.outgoings = outgoings;
    }

    public Vertex(String v) {
        this.value = v;
    }

    public void setOutgoings(List<Vertex> outgoings) {
        this.outgoings = outgoings;
    }
}
