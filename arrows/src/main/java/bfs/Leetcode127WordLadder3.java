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

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Leetcode127WordLadder3 {
    // using a FIFO queue to keep 'starts' and 'nexts'.
    // make sure endWord is in pool and checking each next.equal(endWord)
    public static int ladderLength(String beginWord, String endWord, Set<String> pool) {
        pool.remove(beginWord);
        pool.add(endWord); //here

        Queue<String> q = new LinkedList(); // FIFO queue keep next level strings
        q.offer(beginWord);
        int level = 0;

        while (!q.isEmpty()) {
            level++;
            int size = q.size();
            while (size > 0) {
                String cur = q.poll();
                if (cur.equals(endWord)) { //here
                    return level;
                }
                // nexts
                char[] chars = cur.toCharArray();
                for (int i = 0; i < cur.length(); i++) {
                    char ci = cur.charAt(i);
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c != ci) {
                            chars[i] = c;
                            String t = new String(chars); //transformed, neighbor
                            if (pool.contains(t)) {
                                pool.remove(t);
                                q.offer(t);
                            }
                            chars[i] = ci;
                        }
                    }
                }
                size--;
            }
        }
        return 0;
    }
}
