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

/**
 * <pre>
 * based on {@link Leetcode127WordLadder} where
 * each string of current starts(size=S) will 'canGo' each string of aims (size=A)
 * S*A*length
 * each string of current starts(size=m) will compare each string of left pool (size=P)
 * S*P*length
 * so in total in worse case: S*length*(A+P)
 *
 * can not harness the hash benefit of Hashset
 *
 * In this version, with the hash of Hashset
 *
 * S*25*length  VS  S*length*(A+P)
 * 25         VS   A+P
 *
 * performance:
 *   it is possible now remove the 'transformed' in time
 *
 * Recursively version see {@link Leetcode127WordLadder2_2}
 */
public class Leetcode127WordLadder2 {

    public static int ladderLength(String start, String end, Set<String> pool) {
        Set<String> starts = new HashSet<>(pool.size());
        Set<String> aims = new HashSet<>(pool.size());

        starts.add(start);
        aims.add(end);

        pool.remove(start);
        pool.remove(end);

        int num = 1;

        while (!starts.isEmpty()) {
            Set<String> nexts = new HashSet<>();

            for (String cur : starts) {
                char[] cs = cur.toCharArray();
                for (int i = 0; i < cs.length; i++) {
                    char ci = cs[i];
                    for (char a = 'a'; a <= 'z'; a++) {
                        if (a == ci) {
                            continue;
                        }
                        cs[i] = a;
                        String transformed = new String(cs);
                        if (aims.contains(transformed)) {
                            return ++num;
                        }
                        if (pool.contains(transformed)) {
                            nexts.add(transformed);
                            pool.remove(transformed);
                        }
                        cs[i] = ci;
                    }
                }
            }

            if (nexts.isEmpty()) {
                return 0;
            }
            if (nexts.size() < aims.size()) {
                starts = nexts;
            } else {
                starts = aims;
                aims = nexts;
            }
            num++;
        }
        return 0;
    }
}
