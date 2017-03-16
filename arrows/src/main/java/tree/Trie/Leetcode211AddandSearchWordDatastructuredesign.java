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

package tree.Trie;

import string.trie.Leetcode208ImplementTriePrefixTree;

/**
 * <pre>
 *   <a href="https://leetcode.com/problems/add-and-search-word-data-structure-design/#/description"> LeetCode</a>
 */
public class Leetcode211AddandSearchWordDatastructuredesign {
    class WordDictionary {
        class TrieNode {
            // public char v;
            public boolean isThereWordEnduphere;
            public TrieNode[] nexts = new TrieNode[26]; // Assume only consist of lowercase letters a-z
        }

        private TrieNode root;

        public WordDictionary() {
            root = new TrieNode();
        }

        public void addWord(String word) {
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

        // suport regular expression "."
        public boolean search(String word) {
            return search(root, word.toCharArray(), 0);
        }

        public boolean search(TrieNode from, char[] chars, int fromIndexOfW) {
            TrieNode node = from;
            for (int ci = fromIndexOfW; ci < chars.length; ci++) {
                char c = chars[ci];
                if (c == '.') {
                    for (TrieNode next : node.nexts) {
                        if (next != null && search(next, chars, ci + 1)) {
                            return true;
                        }
                    }
                    // all checked and false.
                    return false;
                }
                // not regular expression
                int index = c - 'a';
                if (node.nexts[index] == null) {
                    return false;
                }
                node = node.nexts[index];
            }
            return node.isThereWordEnduphere;
        }
    }
}