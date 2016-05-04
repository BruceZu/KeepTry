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

/**
 * 266. Palindrome Permutation
 * https://leetcode.com/problems/palindrome-permutation/
 * <p/>
 * Difficulty: Easy <pre>
 * Given a string, determine if a permutation of the string could form a palindrome.
 * <p/>
 * For example,
 * "code" -> False, "aab" -> True, "carerac" -> True.
 * <p/>
 * Hint:
 * <p/>
 * Consider the palindromes of odd vs even length. What difference do you notice?
 * Count the frequency of each character.
 * If each character occurs even number of times, then it must be a palindrome. How about character which occurs odd number of times?
 * <p/>
 * <p/>
 * Hide Company Tags Google Uber
 * Hide Tags: Hash Table
 * Hide Similar Problems (M) Longest Palindromic Substring (E) Valid Anagram (M) Palindrome Permutation II
 */
public class LC266PalindromePermutation {
    /**
     * fast one currently
     *
     * @param s
     * @return
     */
    public boolean canPermutePalindrome(String s) {
        int[] set = new int[256];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (char c : s.toCharArray()) {
            set[c] ^= 1;
            min = Math.min(min, c);
            max = Math.max(max, c);
        }
        int sum = 0;
        for (int i = min; i <= max; i++) {
            sum += set[i];
        }
        return (sum <= 1);
    }

    /**
     * same fast
     */
    public boolean canPermutePalindrome2(String s) {
        int[] map = new int[128];
        char[] string = s.toCharArray();
        int oddCount = 0;
        for (int i = 0; i < string.length; i++) {
            map[string[i]]++;
            oddCount = (map[string[i]] % 2 == 0) ? oddCount - 1 : oddCount + 1;
        }
        return oddCount <= 1;
    }

    // other ideas
    // s should have 0 or 1 odd number character
    // map,  have then remove it ,else add it

}
