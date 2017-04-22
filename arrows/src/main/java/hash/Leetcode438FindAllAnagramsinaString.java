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

package hash;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Strings consists of
 * lowercase English letters only and
 * the length of both strings s and p will not be larger than 20,100.
 * The order of output does not matter.
 *
 * <a href="https://leetcode.com/problems/find-all-anagrams-in-a-string/#/description">Leetcode</a>
 */
public class Leetcode438FindAllAnagramsinaString {
    // O(N)
    static public List<Integer> findAnagrams2(String s, String p) {
        List<Integer> list = new ArrayList<>();
        if (s == null || s.length() == 0 || p == null || p.length() == 0 || p.length() > s.length()) return list;

        int[] map = new int[128]; //lowercase English letters only
        char[] chars = p.toCharArray();
        for (char c : chars) {
            map[c]++;
        }

        int l = 0, r = 0, len = p.length();

        while (r < s.length()) {
            if (map[s.charAt(r)] >= 1) {
                len--;
            }
            map[s.charAt(r)]--; // according to map


            if (len == 0) {
                list.add(l);
            }

            if (r - l == p.length() - 1) {
                if (map[s.charAt(l)] >= 0) {
                    len++;
                }
                map[s.charAt(l)]++; // according to  map[s.charAt(r)]--;
                l++;
            }

            r++;
        }
        return list;
    }

    public static void main(String[] args) {
        findAnagrams("cbaebabacd", "abc");
    }

    // O(N)
    static public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s == null || s.length() == 0 || p == null || p.length() == 0 || p.length() > s.length()) {
            return result;
        }

        int[] map = new int[26]; //lowercase English letters only
        char[] chars = p.toCharArray();
        for (char c : chars) map[c - 'a']++;

        int l = 0, r = 0, len = p.length();
        while (r < s.length()) {
            // if current char's account >=0, 'plus it' will affect len.
            if (r - l == p.length()
                    && map[s.charAt(l++) - 'a']++ >= 0) len++;

            // if current char's account >=1, 'minus it' will affect len.
            if (map[s.charAt(r++) - 'a']-- >= 1) len--;

            if (len == 0) result.add(l);

        }
        return result;
    }

    public List<Integer> findAnagrams3(String s, String p) {
        List<Integer> result = new ArrayList();
        int[] map = new int[128];
        for (char c : p.toCharArray()) map[c]++;

        int l = 0, r = 0, len = p.length();

        while (r < s.length()) {
            if (map[s.charAt(r++)]-- >= 1) len--;
            while (len == 0) { // ?
                if (r - l == p.length()) result.add(l);
                if (map[s.charAt(l++)]++ >= 0) len++;
            }
        }
        return result;
    }
}
