package graph.directed_graphs;

import java.util.*;

public class Leetcode269AlienDictionary2 {
    static class V implements Comparable {
        char i; // assume it can be used as ID

        Set<V> outs = new HashSet();

        @Override
        public int compareTo(Object o) {
            return Character.compare(i, ((V) o).i);
        }

        public V(Character i) {
            this.i = i;
        }

        public V addOutGoingNeighbor(V neighbor) {
            outs.add(neighbor);
            return this;
        }
    }

    /**
     * ------------------------------------------------------------------------------------------
     * <pre>
     * @param words Assume all letters are in lowercase.
     *              If the 'words' is not in "lexicographically" order throw Exception
     * @return graph, 1 may has one or more single vertex
     *                2 may have circle
     */
    public static Collection<V> buildGraph(String[] words) {
        if (words == null || words.length == 0) {
            return Collections.emptySet();
        }

        int maxLength = words[0].length();
        for (int i = 1; i < words.length; i++) { // o(N)
            maxLength = Math.max(maxLength, words[i].length());
        }

        int curPosition = 0;
        Map<Character, V> graph = new HashMap(26);
        while (curPosition < maxLength) { //O(N*maxLength)
            Character preChar = null;

            for (int i = 0; i < words.length; i++) {
                String curStr = words[i];
                // cur char: no
                Character curChar = null; // default
                if (curPosition < curStr.length()) {
                    // cur char: have
                    curChar = curStr.charAt(curPosition);
                    // created Vertex here may become a single Vertex in graph.
                    // single vertex should be kept in graph of this question.
                    if (!graph.containsKey(curChar)) {
                        graph.put(curChar, new V(curChar));
                    }

                    if (preChar != null && preChar != curChar
                            && (curPosition == 0 || words[i - 1].substring(0, curPosition).equals(curStr.substring(0, curPosition)))) {
                        graph.get(preChar).addOutGoingNeighbor(graph.get(curChar));
                    }
                } else {// current position out of current word:  curChar = null; // default
                    if (i > 0) {
                        String preStr = words[i - 1];
                        //if it is invalid "lexicographically" order
                        if (curStr.length() < preStr.length() && preStr.startsWith(curStr)) {
                            throw new InputMismatchException("avoid \"lexicographically\" order");
                        }
                    }
                }
                preChar = curChar; // care
            }
            curPosition++;
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
     *
     * Checking from each vertex using path shadow
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
     * @param vertexes  Assume the graph has no circle
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
