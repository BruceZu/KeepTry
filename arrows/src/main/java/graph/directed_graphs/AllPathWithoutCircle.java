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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static common_lib.Common.LinesOf;
import static common_lib.Common.getLocalFile;

/**
 * <pre>
 * All paths from A to E
 *
 * <img src="../../../resources/cycle_directed_g.png" height="150" width="300">
 * <a href ="http://cs.stackexchange.com/questions/4883/the-path-between-any-two-nodes-in-cyclic-directed-graph">cyclic directed graph</a>
 * <a href ="http://discuss.joelonsoftware.com/default.asp?design.4.249152.10">todo: related algorithms</a>
 */
public class AllPathWithoutCircle {
    // note: if there's a circular
    public static List<String> allPathsOf(Vertex start, Vertex target) {
        List<String> r = new ArrayList();
        StringBuilder path = new StringBuilder();
        Set<Vertex> cur_visited = new HashSet<>();

        DFSBacktracking(start, target, path, cur_visited, r);
        return r;
    }

    public static void DFSBacktracking(Vertex cur, Vertex target,
                                       StringBuilder path, Set cur_visited, List<String> r) {
        if (cur_visited.contains(cur)) { // avoid circle
            return;
        }

        if (cur.value.equals(target.value)) {
            path.append(cur.value); // add this Vertex before add the path to result

            r.add(path.toString()); // find a path

            path.deleteCharAt(path.length() - 1);
            return;
        }

        path.append(cur.value);
        cur_visited.add(cur);

        for (Vertex v : cur.outgoings) {
            DFSBacktracking(v, target, path, cur_visited, r);
        }
        cur_visited.remove(cur);
        path.deleteCharAt(path.length() - 1);
    }

    /*-------------------------------------------------------------------------------------------------*/
    public static void main(String[] args) {
        testUsingLocalFile();
        test();
    }

    private static void test() {
        /** <pre>
         *         +->B-+ -> E
         *         |    v
         * S-->T-->A <- C -> F
         *         |    ^
         *         +->D-+ -> H
         */
        Vertex E = new Vertex("E");
        Vertex F = new Vertex("F");
        Vertex H = new Vertex("H");

        Vertex A = new Vertex("A");
        Vertex C = new Vertex("C", Arrays.asList(A, F));
        Vertex B = new Vertex("B", Arrays.asList(C, E));
        Vertex D = new Vertex("D", Arrays.asList(C, H));
        A.setOutgoings(Arrays.asList(B, D));

        Vertex T = new Vertex("T", Arrays.asList(A));

        Vertex S = new Vertex("S", Arrays.asList(T));

        List<String> r = allPathsOf(S, A);
        for (String path : r) {
            System.out.println(path);
        }
    }

    private static void testUsingLocalFile() {
        /**
         * <pre>
         * All paths from A to E
         * Given file:
         * ---------
         * A E
         * A:B C D
         * B:C
         * C:E
         * D:B
         * ---------
         * A________________ C __ E
         * |_________|B
         * |___D_|
         *
         */

        File f = getLocalFile("file.txt", AllPathWithoutCircle.class);
        if (!(f.exists() && f.canRead())) {
            //
        }
        Vertex[] fromAndTo = parseLinesToGraph(LinesOf(f));
        Vertex from = fromAndTo[0], to = fromAndTo[1];
        List<String> r = allPathsOf(from, to);

        for (String path : r) {
            System.out.println(path);
        }
    }

    /**
     * Assume the lines parsed from a the file with expected format
     * According to the given lines build directed graph and return
     * parsed 'from' vertex and 'to' vertex
     */
    public static Vertex[] parseLinesToGraph(List<String> lines) {
        String[] ls = new String[lines.size()];
        lines.toArray(ls);

        String curLine = ls[0];
        String[] fromTo = curLine.split(" ");

        String fromName = fromTo[0];
        String toName = fromTo[1];

        Vertex from = new Vertex(fromName);
        Vertex to = new Vertex(toName);

        Map<String, Vertex> allv = new HashMap();

        allv.put(fromName, from);
        allv.put(toName, to);

        for (int i = 1; i < ls.length; i++) {
            curLine = ls[i];
            String[] vAndOutgoings = curLine.split(":");
            // current vertex
            String vName = vAndOutgoings[0].trim();
            Vertex v = allv.get(vName);
            if (v == null) {
                v = new Vertex(vName);
                allv.put(vName, v);
            }
            // vertex' outgoings
            String[] outNames = vAndOutgoings[1].trim().split(" ");
            List<Vertex> outs = new ArrayList(outNames.length);
            for (String outName : outNames) {
                Vertex out = allv.get(outName);
                if (out == null) {
                    out = new Vertex(outName);
                    allv.put(outName, out);
                }
                outs.add(out);
            }
            v.setOutgoings(outs);
        }
        return new Vertex[]{from, to};
    }
}
