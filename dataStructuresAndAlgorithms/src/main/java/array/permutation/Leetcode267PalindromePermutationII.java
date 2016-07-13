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

package array.permutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 267. Palindrome Permutation II
 * Difficulty: Medium <pre>
 * Given a string s, return all the palindromic permutations (without duplicates) of it.
 * Return an empty list if no palindromic permutation could be form.
 * <p/>
 * For example:
 * <p/>
 * Given s = "aabb", return ["abba", "baab"].
 * <p/>
 * Given s = "abc", return [].
 * <p/>
 * Hint:
 * <p/>
 * If a palindromic permutation exists, we just need to generate the first half of the string.
 * To generate all distinct permutations of a (half of) string, use a similar approach from: Permutations II or Next Permutation.
 *
 * Tags Backtracking
 * Similar Problems
 * (M) Next Permutation
 * (M) Permutations II
 * (E) Palindrome Permutation
 */
public class Leetcode267PalindromePermutationII {


    public class Solution {

        List<String> results;

        char[] chars;

        int lengthMinusOne, halfLength;

        /**
         * the top fast level is beat 99%
         * this is the second level fast
         * beat 96.7% 2ms
         * I'm generating permutations on the left side of the string,
         * then constantly copying characters to the right side to maintain the palindrome.
         * <p/>
         * There are lots of comments in the code, but please ask if there is anything that is unclear.
         *
         * @param s
         * @return
         */
        public List<String> generatePalindromes(String s) {

            results = new ArrayList<String>();

            // We will be generating permutations in place.
            chars = s.toCharArray();
            int length = chars.length;

            // Return no result for empty string.
            if (length == 0) return results;

            // Sort the array to bring duplicates together.
            Arrays.sort(chars);
            // The array now looks like aabbcdd

            // Precompute a few values for performance.
            lengthMinusOne = length - 1;
            halfLength = length / 2;

            // Prepare the first half of the string. It will contain
            // a single character from each pair. The middle character
            // is placed in the middle.
            // When done, the array will look like abdc***
            boolean foundMiddle = false;

            for (int readCursor = 0, writeCursor = 0; readCursor < length; readCursor++) {
                char c = chars[readCursor];

                // Check for pair of characters.
                if (readCursor < lengthMinusOne && c == chars[readCursor + 1]) {

                    // Found pair. Write one of them to the left of the string.
                    chars[writeCursor++] = c;
                    readCursor++;

                } else {

                    // Found isolated character. Make sure this is the only one
                    // so far, and check that the string length is odd.
                    if (!foundMiddle && (chars.length & 1) == 1) {

                        // Place the middle character.
                        foundMiddle = true;
                        chars[chars.length / 2] = c;

                    } else {

                        // Can't make palindromes from this string.
                        return results;
                    }
                }
            }

            // Generate permutations for all characters.
            generate(0);

            return results;
        }

        // Generates permutations by swapping characters.
        void generate(int start) {

            // When we run out of characters, add the result to the list.
            if (start == halfLength) {
                results.add(new String(chars));
                return;
            }

            // Swap each character into place.
            char prev = 0;
            for (int i = start; i < halfLength; i++) {

                // Check for duplicates.
                if (prev == chars[i]) continue;
                prev = chars[i];

                // Place one character.
                swap(start, i);

                // Generate permutations for remaining characters.
                generate(start + 1);

                // Return the character to it's position.
                swap(start, i);
            }
        }

        // Swap maintains the palindrome by mirroring chars on the right side.
        void swap(int a, int b) {
            char temp = chars[a];

            // Place at a on left side of palindrome.
            chars[a] = chars[b];

            // Place matching char on right side.
            chars[lengthMinusOne - a] = chars[b];

            // Place left.
            chars[b] = temp;

            // Place right.
            chars[lengthMinusOne - b] = temp;
        }
    }


    /**
     * same as above
     * My first solution was a typical hashmap/backtracking 7 ms one. However,
     * I didn't particularly like the part when it checks whether there are any
     * instances of a character left. That's the part that looks something like this:
     * <p/>
     * int count = e.getValue();
     * if (count == 0) {
     * continue;
     * }
     * When we get closer and closer to the middle, these line hit more and more often,
     * so we end up running almost the entire loop in vain. So I thought that it would be
     * nice to remove the character from the list for good when the count reaches zero.
     * But if we do that with a hashtable, the iterations will start throwing
     * ConcurrentModificationException. So we need to store characters with counts
     * in a structure that supports instant removals and re-insertions (for backtracking).
     * That's obviously a linked list. We can't use LinkedList, though because of
     * the same reason—concurrent modifications aren't allowed. So I had to roll out
     * my simple singly-linked list implementation.
     * <p/>
     * This only improved 7 ms to 6 ms. That's not a big surprise, though, since
     * it's still the same order complexity. I got 2 ms by doing away with the hashmap.
     * <p/>
     * On a side note, I see a lot of solutions using int[128] or int[256] for the
     * frequency table. This will obviously screw up anything containing non-ASCII
     * characters and the problem does not say it's limited to Latin characters.
     * One interesting question to ask if you encounter this problem at an interview
     * is are characters limited to ASCII or even BMP. Because if there are characters
     * outside BMP, we may have to handle surrogate pairs, so even int[65536] / HashMap<Character, Int>
     * solutions are, strictly speaking, incorrect!
     */
    public List<String> generatePalindromes(String s) {
        int[] freq = new int[65536];
        char[] chars = new char[s.length()];
        int ch = 0;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (++freq[c] == 1) {
                chars[ch++] = c; // save distinct characters
            }
        }
        List<String> result = new ArrayList<>();
        boolean odd = false;
        char[] buffer = new char[s.length()];
        Node head = null, tail = null;
        for (int i = 0; i < ch; ++i) {
            char c = chars[i];
            int count = freq[c];
            if (count % 2 != 0) {
                if (s.length() % 2 == 0 || odd) {
                    return result;
                } else {
                    odd = true;
                    buffer[buffer.length / 2] = c;
                }
                if (--count == 0) {
                    continue;
                }
            }
            Node n = new Node(c, count / 2);
            if (head == null) {
                head = tail = n;
            } else {
                tail.next = n;
                tail = n;
            }
        }
        buildCombinations(result, head, buffer, 0);
        return result;
    }

    private static void buildCombinations(List<String> result, Node head, char[] buffer, int pos) {
        if (pos == (buffer.length | 1) / 2) {
            result.add(new String(buffer));
            return;
        }
        Node current = head, prev = head;
        while (current != null) {
            buffer[pos] = buffer[buffer.length - 1 - pos] = current.c;
            if (current.count == 1) {
                if (current == head) {
                    buildCombinations(result, head.next, buffer, pos + 1);
                } else {
                    prev.next = current.next;
                    buildCombinations(result, head, buffer, pos + 1);
                    prev.next = current; // backtrack
                }
            } else {
                --current.count;
                buildCombinations(result, head, buffer, pos + 1);
                ++current.count; // backtrack
            }
            prev = current;
            current = current.next;
        }
    }

    private static class Node {
        final char c;
        int count;
        Node next;

        Node(char c, int count) {
            this.c = c;
            this.count = count;
        }
    }
}
