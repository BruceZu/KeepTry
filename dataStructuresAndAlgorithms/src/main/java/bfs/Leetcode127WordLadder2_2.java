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

import java.util.HashSet;
import java.util.Set;

public class Leetcode127WordLadder2_2 {
    /**
     * Note:
     * stop check
     * return  and r
     * start
     */
    private static int goToMeet(Set<String> starts, Set<String> aims, Set<String> pool, int r) {
        //
        if (starts.isEmpty()) {
            return 0;
        }

        if (starts.size() > aims.size()) {
            return goToMeet(aims, starts, pool, r);  //
        }

        //
        Set<String> nexts = new HashSet();
        for (String cur : starts) {
            char[] cs = cur.toCharArray();
            for (int i = 0; i < cs.length; i++) {
                char ci = cs[i];
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c != ci) {
                        cs[i] = c;
                        String neighbor = new String(cs);
                        if (aims.contains(neighbor)) {
                            return r;
                        }
                        if (pool.contains(neighbor)) {
                            nexts.add(neighbor);
                            pool.remove(neighbor);
                        }
                    }
                }
                cs[i] = ci;
            }
        }

        return goToMeet(nexts, aims, pool, r + 1);
    }

    public static int ladderLength(String beginWord, String endWord, Set<String> wordList) {
        //15:06
        Set<String> starts = new HashSet();
        Set<String> aims = new HashSet();

        starts.add(beginWord);
        aims.add(endWord);

        wordList.remove(beginWord);
        wordList.remove(endWord);

        return goToMeet(starts, aims, wordList, 2);
    }
}
