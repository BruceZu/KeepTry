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

import java.util.HashMap;
import java.util.Map;

public class Leetcode133CloneGraph_ShiftVersion_1 {
    public static Node cloneGraph(Node node) {
        if (node == null) { // care
            return null;
        }
        // map: used to from circle later and keep visited nodes  
        return cloneOf(node, new HashMap<>());
    }

    // deep clone by deep fist search
    private static Node cloneOf(Node cur, Map<Node, Node> map) {
        if (!map.containsKey(cur)) {
            Node curClone = new Node();
            map.put(cur, curClone);

            for (Node curNext : cur.neighbors) {
                curClone.neighbors.add(cloneOf(curNext, map));
            }
        }
        return map.get(cur);
    }
}
