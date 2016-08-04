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

package bfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *
 *  compared with {@link Leetcode126WordLadderII} :
 *
 *    pros:
 *      1. with 'forward' to keep the map of fromTo. make the code a little simple.
 *      2. remove 'nexts' only when it need continue next level meeting.
 *
 *   cons:
 *      1. keeping fromTo or toFrom is still not good at performance as a lot of paths end up at half way.
 *         fromTo it better than toFrom, it need not reverse at last. but this does not affect performance.
 *      2. cons of starts traceBackPath after all finding work is done:
 *         - traceBackPath has to check by itself if there is no answer at all.
 *         - 'String[] d = new String[wordList.size() + 2];' does not work,
 *           because at that time the wordList may becames empty.
 */
public class Leetcode126WordLadderII2 {
    private static void link(boolean forward, String connectTo, String cur, Map<String, List<String>> fromTo) {
        String from = forward ? cur : connectTo;
        String to = forward ? connectTo : cur;

        List<String> toes = fromTo.get(from);
        if (toes == null) {
            toes = new ArrayList();
        }
        toes.add(to);
        fromTo.put(from, toes);
    }

    private static boolean meet(Set<String> starts, Set<String> aims, Set<String> pool, Map<String, List<String>> fromTo, boolean forward) {
        if (starts.size() > aims.size()) {
            return meet(aims, starts, pool, fromTo, !forward);
        }

        boolean connected = false;
        Set<String> nexts = new HashSet();

        for (String cur : starts) {
            char[] cs = cur.toCharArray();
            for (int i = 0, len = cs.length; i < len; i++) {
                char ci = cs[i];
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c != ci) {
                        cs[i] = c;
                        String connectTo = new String(cs);
                        if (aims.contains(connectTo)) {
                            connected = true;
                            link(forward, connectTo, cur, fromTo);
                        }
                        if (!connected && pool.contains(connectTo)) {
                            nexts.add(connectTo);
                            link(forward, connectTo, cur, fromTo);
                        }
                    }
                }
                cs[i] = ci;
            }
        }

        if (connected) {
            return true;
        }
        if (nexts.isEmpty()) {
            return false;
        }
        pool.removeAll(nexts);
        return meet(nexts, aims, pool, fromTo, forward);
    }

    private static void traceBackPath(String[] d, int size, String from, String end,
                                      Map<String, List<String>> fromTo, List<List<String>> r) {

        if (from.equals(end)) {
            String[] path = new String[size];
            System.arraycopy(d, 0, path, 0, size);
            r.add(Arrays.asList(path));
            return;
        }

        List<String> toes = fromTo.get(from);
        if (toes == null) {
            return;
        }
        for (String to : toes) {
            d[size] = to;
            traceBackPath(d, size + 1, to, end, fromTo, r);
        }
    }

    public static List<List<String>> findLadders(String beginWord, String endWord, Set<String> wordList) {
        HashMap<String, List<String>> fromTo = new HashMap();

        Set<String> starts = new HashSet(), aims = new HashSet();

        starts.add(beginWord);
        aims.add(endWord);

        int maxSize = wordList.size() + 2;
        wordList.remove(beginWord);
        wordList.remove(endWord);

        List<List<String>> r = new ArrayList();
        if (meet(starts, aims, wordList, fromTo, true)) {
            String[] d = new String[maxSize];
            d[0] = beginWord;
            traceBackPath(d, 1, beginWord, endWord, fromTo, r);
        }
        return r;
    }
}
