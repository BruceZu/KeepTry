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

package locked.nosubmmitted;

import java.util.Arrays;

/**
 * Difficulty: Hard <pre>
 * There is a new alien language which uses the latin alphabet.
 * However, the order among letters are unknown to you.
 * You receive a list of words from the dictionary,
 * where words are sorted lexicographically by the rules of this new language.
 * Derive the order of letters in this language.
 * a
 * For example,
 * Given the following words in dictionary,
 * <p/>
 * [
 * "wrt",
 * "wrf",
 * "er",
 * "ett",
 * "rftt"
 * ]
 * The correct order is: "wertf".
 * <p/>
 * Note:
 * You may assume all letters are in lowercase.
 * If the order is invalid, return an empty string.
 * There may be multiple valid order of letters, return any one of them is fine.
 * Hide Company Tags: Google Airbnb Facebook Twitter Snapchat Pocket Gems
 * Hide Tags: Graph, Topological Sort
 * Hide Similar Problems (M) Course Schedule II
 */
public class LC269AlienDictionary {

    // beat 96.4% this is not the fast one
    /**
     * The key to this problem is:
     * <p/>
     * A topological ordering is possible if and only if the graph has no directed cycles
     * Let's build a graph and perform a DFS. The following states made things easier.
     * <p/>
     * visited[i] = -1. Not even exist.
     * visited[i] = 0. Exist. Non-visited.
     * visited[i] = 1. Visiting.
     * visited[i] = 2. Visited.
     * Similarly, there is another simple BFS version.
     * This problem is very basic. There are only two steps:
     * <p/>
     * Build Graph
     * <p/>
     * Init indegree[26] for number of links pointing to w[i], adj[26] for neighbors of w[i].
     * For each first seeing character w[i] with indegree initially-1, mark it as indegree = 0.
     * For each adjacent words w1 and w2, if w1[i] != w2[i], insert w1[i] -> w2[i] into graph.
     * Increase indegree[w2[i]] by 1.
     * Topological Sort
     * <p/>
     * Start from queue filled with indegree = 0 characters (lexicographically earliest).
     * poll queue, append these 0 indegree guys, and reduce indegree of their neighbors by 1.
     * If neighbors reduced to indegree = 0, add them back to queue.
     * Peel level by level until queue is empty.
     * The run time is 7ms. Hope it helps!
     */

    private final int N = 26;

    public String alienOrder(String[] words) {
        boolean[][] adj = new boolean[N][N];
        int[] visited = new int[N];
        buildGraph(words, adj, visited);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            if (visited[i] == 0) {                 // unvisited
                if (!dfs(adj, visited, sb, i)) return "";
            }
        }
        return sb.reverse().toString();
    }

    public boolean dfs(boolean[][] adj, int[] visited, StringBuilder sb, int i) {
        visited[i] = 1;                            // 1 = visiting
        for (int j = 0; j < N; j++) {
            if (adj[i][j]) {                        // connected
                if (visited[j] == 1) return false;  // 1 => 1, cycle
                if (visited[j] == 0) {              // 0 = unvisited
                    if (!dfs(adj, visited, sb, j)) return false;
                }
            }
        }
        visited[i] = 2;                           // 2 = visited
        sb.append((char) (i + 'a'));
        return true;
    }

    public void buildGraph(String[] words, boolean[][] adj, int[] visited) {
        Arrays.fill(visited, -1);                 // -1 = not even existed
        for (int i = 0; i < words.length; i++) {
            for (char c : words[i].toCharArray()) visited[c - 'a'] = 0;
            if (i > 0) {
                String w1 = words[i - 1], w2 = words[i];
                int len = Math.min(w1.length(), w2.length());
                for (int j = 0; j < len; j++) {
                    char c1 = w1.charAt(j), c2 = w2.charAt(j);
                    if (c1 != c2) {
                        adj[c1 - 'a'][c2 - 'a'] = true;
                        break;
                    }
                }
            }
        }
    }
}
