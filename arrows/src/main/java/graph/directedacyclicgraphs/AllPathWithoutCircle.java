package graph.directedacyclicgraphs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * want all pathes from A to E
 * Given:
 * A : B C D
 * B : C
 * C : E
 * D : B
 *
 * A----D ---
 * |_________|___  B ___
 * |
 * |___C_________________|______E
 *
 * <img src="../../../resources/cycle_directed_g.png" height="150" width="300">
 * <a href ="http://cs.stackexchange.com/questions/4883/the-path-between-any-two-nodes-in-cyclic-directed-graph">cyclic directed graph</a>
 * <a href ="http://discuss.joelonsoftware.com/default.asp?design.4.249152.10">todo: related algorithms</a>
 */
public class AllPathWithoutCircle {
    static class V {
        String value; // assume the value is distinguish. as ID of V.
        List<V> outgoings = Collections.emptyList(); // neighbors. Default value is empty, as end vertex.

        public V(String v, List<V> outgoings) {
            this.value = v;
            this.outgoings = outgoings;
        }

        public V(String v) {
            this.value = v;
        }

        public void setOutgoings(List<V> outgoings) {
            this.outgoings = outgoings;
        }
    }

    /*-------------------------------------------------------------------------------------------------*/
    // note: if there's a circular
    public static Set<String> allPaths(V start, V target) {
        Set<String> r = new HashSet<>();
        StringBuilder path = new StringBuilder();
        Set<V> cur_visited = new HashSet<>();
        DFSBacktracking(start, target, path, cur_visited, r);
        return r;
    }

    private static void DFSBacktracking(V cur, V target, StringBuilder path, Set cur_visited, Set<String> r) {
        if (cur_visited.contains(cur)) { // avoid circle
            return;
        }

        if (cur.value.equals(target.value)) {
            path.append(cur.value); // add this V before add the path to result

            r.add(path.toString()); // find a path

            path.deleteCharAt(path.length() - 1);
            return;
        }

        path.append(cur.value);
        cur_visited.add(cur);

        for (V v : cur.outgoings) {
            DFSBacktracking(v, target, path, cur_visited, r);
        }
        cur_visited.remove(cur);
        path.deleteCharAt(path.length() - 1);
    }

    /*-------------------------------------------------------------------------------------------------*/
    public static void test1() {
        /** <pre>
         *         +->B-+ -> E
         *         |    v
         * S-->T-->A <- C -> F
         *         |    ^
         *         +->D-+ -> H
         */
        V E = new V("E");
        V F = new V("F");
        V H = new V("H");

        V A = new V("A");
        V C = new V("C", Arrays.asList(A, F));
        V B = new V("B", Arrays.asList(C, E));
        V D = new V("D", Arrays.asList(C, H));
        A.setOutgoings(Arrays.asList(B, D));

        V T = new V("T", Arrays.asList(A));

        V S = new V("S", Arrays.asList(T));

        Set<String> r = allPaths(S, A);
        for (String path : r) {
            System.out.println(path);
        }
    }

    /**
     * <pre>
     * <a href="http://stackoverflow.com/questions/3153337/get-current-working-directory-in-java">current working directory in Java </a>
     */
    public static void main(String[] args) {
        try {
            String current = AllPathWithoutCircle.class.getName();
            current = current.substring(0, current.lastIndexOf(".", current.length() - 1)).replace(".", "/");
            String location = AllPathWithoutCircle.class.getProtectionDomain().getCodeSource()
                    .getLocation().getFile();
            File f = new File(location + current + "/file.txt");
            if (f.exists() && f.canRead()) {
                int lines = 0;
                BufferedReader br = new BufferedReader(new FileReader(f));
                while (true) {
                    String curLine = br.readLine();
                    if (curLine == null) {
                        break;
                    }
                    lines++;
                    if (lines == 1) {
                        String[] fromTo = curLine.split(" ");
                        System.out.println(String.format("from %s to %s", fromTo[0], fromTo[1]));
                    } else {
                        String[] vAndOutgoings = curLine.split(":");
                        System.out.print("current V is " + vAndOutgoings[0].trim());
                        String[] outgoings = vAndOutgoings[1].trim().split(" ");
                        System.out.println(" and its outgoings are: " + Arrays.toString(outgoings));
                    }
                }
            }
        } catch (Exception e) {
            //
        }

        System.out.println("-----");
        test1();
        test2();
    }

    public static void test2() {
        /**
         * <pre>
         * want all pathes from A to E
         * Given:
         * A : B C D
         * B : C
         * C : E
         * D : B
         *
         * A____ D __
         * |_________|___  B ___
         * |
         * |___C_________________|______E
         */
        V E = new V("E");
        V B = new V("B", Arrays.asList(E));
        V C = new V("C", Arrays.asList(E));

        V D = new V("D", Arrays.asList(B));
        V A = new V("A", Arrays.asList(B, D, C));

        Set<String> r = allPaths(A, E);
        for (String path : r) {
            System.out.println(path);
        }
    }
}
