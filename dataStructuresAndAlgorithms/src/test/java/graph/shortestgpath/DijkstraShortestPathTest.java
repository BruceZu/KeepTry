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

package graph.shortestgpath;

import graph.shortestpath.DijkstraShortestPath;
import graph.shortestpath.Node;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DijkstraShortestPathTest {
    Node start = new Node("a");
    Node end = new Node("d");
    String expectedPath;

    private void init() {
        Node b = new Node("b");
        Node c = new Node("c");
        Node e = new Node("e");
        Node f = new Node("f");

        /**
         * <pre>
         *        [ f ]  -- 9 -- [ e ]
         *        /   \             \
         *       /     \             \
         *     14       2            6
         *     /         \            \
         * [ a ] - 9 - [ c ] - 11 - [ d ]
         *   \           /           /
         *    7        10          15
         *      \      /          /
         *       \    /          /
         *          [   b   ]
         */
        Map<Node, Integer> nds = new HashMap();
        nds.put(b, 7);
        nds.put(c, 9);
        nds.put(f, 14);
        start.init(nds);

        nds = new HashMap();
        nds.put(start, 7);
        nds.put(c, 10);
        nds.put(end, 15);
        b.init(nds);

        nds = new HashMap();
        nds.put(start, 9);
        nds.put(b, 10);
        nds.put(end, 11);
        nds.put(f, 2);
        c.init(nds);

        nds = new HashMap();
        nds.put(start, 14);
        nds.put(c, 2);
        nds.put(e, 9);
        f.init(nds);

        nds = new HashMap();
        nds.put(f, 9);
        nds.put(end, 6);
        e.init(nds);


        nds = new HashMap();
        nds.put(e, 6);
        nds.put(c, 11);
        nds.put(b, 15);
        end.init(nds);

        start.setTd(0);
        expectedPath = "Shortest path is a -> c -> d";
    }

    private void init2() {
        //todo more test case
    }

    @Test(timeout = 10L, expected = Test.None.class)
    public void shortestPathTest() {
        init();
        Assert.assertEquals(expectedPath, DijkstraShortestPath.shortestPath(start, end, new HashSet<Node>()));
    }
}
