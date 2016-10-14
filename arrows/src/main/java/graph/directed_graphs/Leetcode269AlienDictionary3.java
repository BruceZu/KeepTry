package graph.directed_graphs;

import java.util.*;

/**
 * Use 2 HashMap to form Graph
 * Use Kahn's Algorithm Topological Sorting
 */
public class Leetcode269AlienDictionary3 {
    public static String alienOrder(String[] words) {
        if (words == null || words.length == 0) {
            return "";
        }
        Map<Character, Set<Character>> outs = new HashMap();
        Map<Character, Integer> inNum = new HashMap();
        try {
            buildGraph(words, outs, inNum);
        } catch (InputMismatchException e) {
            return "";
        }

        return KahnAlgorithmTopologicalSorting(outs, inNum);
    }

    /**
     * @param words
     * @param outs  empty map from v to its outgoings
     * @param inNum empty map from v to its incoming num
     */
    public static void buildGraph(String[] words,
                                  Map<Character, Set<Character>> outs,
                                  Map<Character, Integer> inNum) {
        for (String s : words) {
            for (char c : s.toCharArray()) {
                inNum.put(c, 0); //default value 0, just keep all vertexes
                outs.put(c, new HashSet()); //default value empty set, just make it easy to handle in following
            }
        }
        // find edges and update incomes num for vertex
        for (int wi = 0; wi < words.length - 1; wi++) {
            String cur = words[wi];
            String next = words[wi + 1];
            int length = Math.min(cur.length(), next.length());
            boolean notFoundEdge = true;
            for (int ci = 0; ci < length; ci++) {

                char curChar = cur.charAt(ci);
                char nextChar = next.charAt(ci);

                if (curChar != nextChar) {
                    notFoundEdge = false;

                    if (!outs.get(curChar).contains(nextChar)) {
                        // it has have default value 0;
                        inNum.put(nextChar, inNum.get(nextChar) + 1);
                    }

                    outs.get(curChar).add(nextChar);

                    break;
                }
            }
            if (notFoundEdge && length < cur.length()) {
                throw new InputMismatchException("avoid \"lexicographically\" order");
            }
        }
    }

    /**
     * Upside: It can check if there is circle. Do not require checking circle before call it
     * Downside: At first it need provided all vertexes without incoming edges of the graph
     */
    public static String KahnAlgorithmTopologicalSorting(
            Map<Character, Set<Character>> outs,
            Map<Character, Integer> inNum) {
        // initial vertexes without incoming edge
        Queue<Character> startsVertex = new ArrayDeque<>();
        for (char c : inNum.keySet()) {
            if (inNum.get(c) == 0) {
                startsVertex.add(c);
            }
        }
        // topological sorting
        StringBuilder result = new StringBuilder();
        while (!startsVertex.isEmpty()) {
            char start = startsVertex.remove();
            result.append(start);
            for (char out : outs.get(start)) {
                inNum.put(out, inNum.get(out) - 1);
                if (inNum.get(out) == 0) {
                    startsVertex.add(out);
                }
            }
        }
        if (result.length() != inNum.size()) {
            return "";
        }

        return result.toString();
    }
}
