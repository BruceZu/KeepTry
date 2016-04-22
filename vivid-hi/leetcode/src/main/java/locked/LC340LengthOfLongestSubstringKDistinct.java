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

package locked;

import java.util.HashMap;

/**
 * https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/
 * Given a string, find the length of the longest substring T that contains at most k distinct characters.
 * <p/>
 * For example, Given s = “eceba” and k = 2,
 * <p/>
 * T is "ece" which its length is 3.
 * <p/>
 * Hide Company Tags Google
 * Hide Tags Hash Table String
 * Hide Similar Problems (H) Longest Substring with At Most Two Distinct Characters
 */

public class LC340LengthOfLongestSubstringKDistinct {

    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        int startIndex = 0;
        int length = 0;
        HashMap<Character, Integer> map = new HashMap();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            map.put(c, map.get(c) == null ? 1 : map.get(c) + 1);
            if (map.keySet().size() > k) {
                // move startIndex  ahead till
                while (map.keySet().size() != k) {
                    char startc = s.charAt(startIndex);
                    if (map.get(startc) != 1) {
                        map.put(startc, map.get(startc) - 1);
                    } else {
                        map.remove(startc);
                    }
                    startIndex++;
                }
            }
            length = Math.max(length, i - startIndex + 1);
        }
        return length;
    }

    public static void main(String[] args) {
        System.out.println(new LC340LengthOfLongestSubstringKDistinct().lengthOfLongestSubstringKDistinct("eceba", 2));
    }

    public int lengthOfLongestSubstringKDistinct2(String s, int k) {
        // ASCII printable code chart  [32 - 126]
        // new int [256] do not affect while in this case
        int[] distinctCharCount = new int[256];
        int num = 0, i = 0, maxSubStrLength = 0;

        for (int j = 0; j < s.length(); j++) {
            char currentChar = s.charAt(j);
            if (distinctCharCount[currentChar] == 0) {
                num++;

            }
            distinctCharCount[currentChar]++;

            // update startIndex
            if (num > k) {
                while (true) {
                    char startChar = s.charAt(i);
                    --distinctCharCount[startChar];
                    i++;
                    if (distinctCharCount[startChar] == 0) {
                        break;
                    }
                }
                num--;
            }
            //record the maxSubStrLength
            // it is over kill to tuning by adding 'if (j == s.length() - 1 || count[s.charAt(j + 1)] == 0)'
            maxSubStrLength = Math.max(maxSubStrLength, j - i + 1);

        }
        return maxSubStrLength;
    }
}