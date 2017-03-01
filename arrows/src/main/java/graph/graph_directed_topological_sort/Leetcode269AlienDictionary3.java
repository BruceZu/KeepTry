//  Copyright 2017 The keepTry Open Source Project
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

package graph.graph_directed_topological_sort;

import java.util.*;

/**
 * Use Kahn's Algorithm Topological Sorting
 */
public class Leetcode269AlienDictionary3 {
    public static String alienOrder(String[] words) {
        if (words == null || words.length == 0) {
            return "";
        }
        Map<Character, Set<Character>> vToFollowings = new HashMap();
        Map<Character, Integer> vToPresNumber = new HashMap();
        try {
            buildGraph(words, vToFollowings, vToPresNumber);
        } catch (InputMismatchException e) {
            return "";
        }

        return KahnTopologicalSort(vToFollowings, vToPresNumber);
    }

    public static void buildGraph(String[] words,
                                  Map<Character, Set<Character>> vToFollowings,
                                  Map<Character, Integer> vToPresNumber) {
        for (String s : words) {
            for (char c : s.toCharArray()) {
                vToPresNumber.put(c, 0);
                vToFollowings.put(c, new HashSet()); // make it easy to handle in following
            }
        }

        for (int i = 1; i < words.length; i++) {
            String preW = words[i - 1];
            String curW = words[i];

            int length = Math.min(preW.length(), curW.length());
            boolean notFoundEdge = true;

            for (int j = 0; j < length; j++) {

                char preC = preW.charAt(j);
                char curC = curW.charAt(j);

                if (preC != curC) {
                    notFoundEdge = false;

                    if (!vToFollowings.get(preC).contains(curC)) {
                        // it has had the default value 0;
                        vToPresNumber.put(curC, vToPresNumber.get(curC) + 1);
                    }

                    vToFollowings.get(preC).add(curC);
                    break;
                }
            }
            if (notFoundEdge && length < preW.length()) {
                throw new InputMismatchException("avoid \"lexicographically\" order");
            }
        }
    }

    public static String KahnTopologicalSort(
            Map<Character, Set<Character>> vToFollowings,
            Map<Character, Integer> vToPresNumber) {

        Queue<Character> queue = new ArrayDeque<>();
        for (char vID : vToPresNumber.keySet()) {
            if (vToPresNumber.get(vID) == 0) {
                queue.add(vID);
            }
        }

        StringBuilder result = new StringBuilder();
        while (!queue.isEmpty()) {
            char v = queue.remove();
            result.append(v);
            for (char follow : vToFollowings.get(v)) {
                vToPresNumber.put(follow, vToPresNumber.get(follow) - 1);
                if (vToPresNumber.get(follow) == 0) {
                    queue.add(follow);
                }
            }
        }
        if (result.length() != vToPresNumber.size()) { // have circle in directed graph
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
