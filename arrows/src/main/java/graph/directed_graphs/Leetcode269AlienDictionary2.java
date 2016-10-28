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

import java.util.*;

public class Leetcode269AlienDictionary2 {
    static class V implements Comparable {
        char i; // Assume it can be used as ID
        Set<V> outs;

        @Override
        public int compareTo(Object o) {
            return Character.compare(i, ((V) o).i);
        }

        public V(Character i) {
            this.i = i;
            outs = new HashSet(); // Default value
        }
    }

    /**
     * ------------------------------------------------------------------------------------------
     * <pre>
     * @param words Assume all letters are in lowercase.
     *              If the 'words' is not in "lexicographically" order throw Exception
     * @return graph, 1 may has one or more single vertex
     *                2 may have circle
     *
     *  O(sum of all words' length)
     */
    public static Collection<V> buildGraph(String[] words) {
        if (words == null || words.length == 0) {
            return Collections.emptySet();
        }
        Map<Character, V> graph = new HashMap(26);
        String first = words[0];
        for (int ci = 0; ci < first.length(); ci++) {
            // created Vertex here may become a single Vertex in graph.
            // single vertex should be kept in graph of this question.
            char curc = first.charAt(ci);
            graph.put(curc, new V(curc));
        }

        for (int wi = 1; wi < words.length; wi++) {
            String prew = words[wi - 1];
            String curw = words[wi];
            // step 1 valid input
            if (curw.length() < prew.length() && prew.startsWith(curw)) {
                throw new InputMismatchException("Avoid \"lexicographically\" order");
            }

            // step 2 keep new vertex
            for (int ci = 0; ci < curw.length(); ci++) {
                char curc = curw.charAt(ci);
                if (!graph.containsKey(curc)) {
                    graph.put(curc, new V(curc));
                }
            }
            // step 3 find edge
            int length = Math.min(curw.length(), prew.length());
            for (int ci = 0; ci < length; ci++) {
                char prec = prew.charAt(ci);
                char curc = curw.charAt(ci);

                if (curc != prec) {
                    graph.get(prec).outs.add(graph.get(curc));
                    break; // care
                }

            }
        }
        return graph.values();
    }

    /*------------------------------------------------------------------------------------------*/
    static boolean hasCircle(V v, Set<V> shadow) {
        if (shadow.add(v)) {
            for (V o : v.outs) {
                if (hasCircle(o, shadow)) {
                    return true;
                }
            }
            shadow.remove(v);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Assume graph
     * 1 may have single vertexes
     * 2 may have circle
     * <p>
     * Checking from each vertex using path shadow
     *
     * @param vertexes
     * @return false if the graph has circle.
     */
    static boolean noCircle(Collection<V> vertexes) {
        Set<V> visited = new HashSet();
        for (V v : vertexes) {
            if (hasCircle(v, visited)) {
                return false;
            }
        }
        return true;
    }

    /**
     * ------------------------------------------------------------------------------------------
     * It can start from any vertex.
     *
     * @param vertexes Assume the graph has no circle
     * @return topological sorted vertexes in String.
     */
    public static String topologicalSort(Collection<V> vertexes) {
        if (vertexes == null) {
            return "";
        }
        Set<V> visited = new HashSet<>(vertexes.size());
        StringBuilder r = new StringBuilder();

        for (V v : vertexes) {
            DFSNeighbors(v, visited, r);
        }

        return r.reverse().toString(); // care
    }

    /**
     * @param v       current vertex
     * @param visited all visited Vertexes of graph
     * @param r       used to keep current order line of Vertexes
     */
    public static void DFSNeighbors(V v, Set<V> visited, StringBuilder r) {
        if (visited.add(v)) { // care
            for (V neighbor : v.outs) {
                DFSNeighbors(neighbor, visited, r);
            }
            r.append(v.i); // like stack. deep first
        }
    }

    /* ------------------------------------------------------------------------------------------*/
    public static String alienOrder(String[] words) {
        try {
            Collection<V> graph = buildGraph(words);
            if (noCircle(graph)) {
                return topologicalSort(graph);
            }
            return "";
        } catch (InputMismatchException e) {
            return "";
        }
    }

    /*------------------------------------------------------------------------------------------------------*/
    public static void main(String[] args) {
        System.out.println(alienOrder(new String[]{
                "wrt",
                "wrf",
                "er",
                "ett",
                "rftt"}));
        // "wertf"


        System.out.println(alienOrder(new String[]{
                "wrtkj",
                "wrt"}));
        // ""
        // not lexicographically
        System.out.println(alienOrder(new String[]{
                "z",
                "z"}));
        // "z"
        System.out.println(alienOrder(new String[]{
                "a",
                "b",
                "a"}));
        // ""
        // circle
        System.out.println(alienOrder(new String[]{
                "a",
                "b",
                "ca",
                "cc"}));
        // "abc"
        System.out.println(alienOrder(new String[]{
                "ze",
                "yf",
                "xd",
                "wd",
                "vd",
                "ua",
                "tt",
                "sz",
                "rd",
                "qd",
                "pz",
                "op",
                "nw",
                "mt",
                "ln",
                "ko",
                "jm",
                "il",
                "ho",
                "gk",
                "fa",
                "ed",
                "dg",
                "ct",
                "bb",
                "ba"}));

        // "z y x w v u t s r q p o n m  l k j i h g f e d c b a "
        // the second column: only the b->a of the last 2 words is valid.

        System.out.println(alienOrder(new String[]{
                "dvpzu",
                "bq",
                "lwp",
                "akiljwjdu",
                "vnkauhh",
                "ogjgdsfk",
                "tnkmxnj",
                "uvwa",
                "zfe",
                "dvgghw",
                "yeyruhev",
                "xymbbvo",
                "m",
                "n"}));
        // ""
        // the fist column has circle and
        // this circle this is not in-came vertex, there is only outgoing vertexes
        // if this any one other single vertex the graph will be think of this is NOT circle.

        System.out.println(alienOrder(new String[]{
                "zy",
                "zx"}));
        // yxz or zyx
        // z should be kept
    }
}
