package graph.directed_graphs;

import array.logParse.OnlineParse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
         * A : B C D
         * B : C
         * C : E
         * D : B
         * ---------
         * A________________ C __ E
         * |_________|B
         * |___D_|
         *
         */

        File f = OnlineParse.getLocalFile("file.txt", AllPathWithoutCircle.class);
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

    // Assume the file exists and readable
    private static List<String> LinesOf(File f) {
        List<String> lines = new ArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String curLine;
            while ((curLine = br.readLine()) != null) {
                lines.add(curLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
