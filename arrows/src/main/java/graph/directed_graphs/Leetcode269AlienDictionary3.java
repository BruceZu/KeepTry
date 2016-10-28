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
     * @param inNum empty map from v to its incoming number
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
        for (int wi = 1; wi < words.length; wi++) {
            String pre = words[wi - 1];
            String cur = words[wi];

            int length = Math.min(pre.length(), cur.length());
            boolean notFoundEdge = true;

            for (int ci = 0; ci < length; ci++) {

                char curChar = pre.charAt(ci);
                char nextChar = cur.charAt(ci);

                if (curChar != nextChar) {
                    notFoundEdge = false;

                    if (!outs.get(curChar).contains(nextChar)) {
                        // it has had the default value 0;
                        inNum.put(nextChar, inNum.get(nextChar) + 1);
                    }

                    outs.get(curChar).add(nextChar);

                    break;
                }
            }
            if (notFoundEdge && length < pre.length()) {
                throw new InputMismatchException("avoid \"lexicographically\" order");
            }
        }
    }

    /**
     * Upside: It can check if there is circle. Do not require checking circle before call it
     * Downside:
     * 1 At first it need provided all vertexes without incoming edges of the graph
     * 2 It need keep incomings or incoming number
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
