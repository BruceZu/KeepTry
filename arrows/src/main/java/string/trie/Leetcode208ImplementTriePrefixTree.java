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

package string.trie;

/**
 * <pre>
 *  208. Implement Trie (Prefix Tree)
 *          Difficulty: Medium
 * Implement a trie with insert, search, and startsWith methods.
 *
 * Note:
 * You may assume that all inputs are consist of <strong>lowercase letters a-z</strong>.
 *
 *  Company Tags
 *      Google
 *      Uber
 *      Facebook
 *      Twitter
 *      Microsoft
 *      Bloomberg
 *  Tags
 *      Design
 *      Trie
 *  Similar Problems
 *          (M) Add and Search Word - Data structure design
 *   @see <a href="https://leetcode.com/problems/implement-trie-prefix-tree/">leetcode</a>
 *   @see <a href="https://en.wikipedia.org/wiki/Trie">Trie</a>
 */
public class Leetcode208ImplementTriePrefixTree {


    public class Trie {
        class TrieNode {
            // public char v;
            public boolean isThereWordEnduphere;
            public TrieNode[] nexts = new TrieNode[26]; // Assume only consist of lowercase letters a-z
        }
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode node = root;
            for (int i = 0; i < word.length(); i++) {
                char v = word.charAt(i);
                int index = v - 'a';
                if (node.nexts[index] == null) {
                    node.nexts[index] = new TrieNode();
                }
                node = node.nexts[index];
            }
            node.isThereWordEnduphere = true;
        }

        public boolean search(String word) {
            TrieNode reach = reachOfTrieAlongWith(word);
            return reach != null && reach.isThereWordEnduphere;
        }

        public boolean startsWith(String prefix) {
            return reachOfTrieAlongWith(prefix) != null;
        }

        // search() and startsWith() can abstract out a common method.
        private TrieNode reachOfTrieAlongWith(String word) {
            TrieNode node = root;
            for (int i = 0; i < word.length(); i++) {
                char v = word.charAt(i);
                int index = v - 'a';
                if (node.nexts[index] == null) {
                    return null;
                }
                node = node.nexts[index];
            }
            return node;
        }
    }
}

// Your Trie object will be instantiated and called as such:
// Trie trie = new Trie();
// trie.insert("somestring");
// trie.search("key");
