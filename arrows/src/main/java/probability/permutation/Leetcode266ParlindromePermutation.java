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

package probability.permutation;

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * 266. Palindrome Permutation
 * https://leetcode.com/problems/palindrome-permutation/
 *
 * Difficulty: Easy
 * Given a string, determine if a permutation of the string could form a palindrome.
 *
 * For example,
 * "code" -> False, "aab" -> True, "carerac" -> True.
 *
 * Hint:
 *
 * Consider the palindromes of odd vs even length. What difference do you notice?
 * Count the frequency of each character.
 * If each character occurs even number of times, then it must be a palindrome.
 * How about character which occurs odd number of times?
 *
 * Company Tags Google Uber
 *
 * Tags: Hash Table
 *
 * Similar Problems
 * (M) Longest Palindromic Substring
 * (E) Valid Anagram
 * (M) Palindrome Permutation II
 * =============================================================================
 *
 * Assume:
 *    There is not <STRONG> supplementary characters </STRONG>
 *    Is char limited to that in <STRONG>  ASCII chart </STRONG>?
 */
public class Leetcode266ParlindromePermutation {
    public boolean canPermutePalindrome3(String s) {
        char[] arr = s.toCharArray();
        int[] evenOdd = new int[256];

        // index scope of times
        int l = arr[0];
        int r = arr[0];

        for (char c : arr) {
            evenOdd[c] ^= 1;
            l = Math.min(l, c);
            r = Math.max(r, c);
        }
        int sum = 0;
        for (int i = l; i <= r; i++) {
            sum += evenOdd[i];
        }
        return (sum <= 1);
    }

    public boolean canPermutePalindrome2(String s) {
        char[] arr = s.toCharArray();
        int oddCount = 0;
        int[] times = new int[128];
        for (int i = 0; i < arr.length; i++) {
            times[arr[i]]++;
            oddCount = (times[arr[i]] % 2 == 0) ? oddCount - 1 : oddCount + 1;
        }
        return oddCount <= 1;
    }

    public boolean canPermutePalindrome(String s) {
        char[] arr = s.toCharArray();
        Set<Character> addSub =  new HashSet ();

        for (int i = 0; i < arr.length; i++) {
            char  c= arr[i];
            if(addSub.contains(c)){
                addSub.remove(c);
            }else{
                addSub.add(c);
            }
        }
        return addSub.size() <= 1;
    }
}
