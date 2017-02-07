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

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 1 You may assume the string contains only lowercase alphabets.
 *
 * 2 What if the inputs contain unicode characters?
 * How would you adapt your solution to such case?
 */

public class Leetcode242ValidAnagram {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] s_char_number = new int[26];
        int[] t_char_number = new int[26];
        for (int i = 0; i < s.length(); i++) {
            s_char_number[s.charAt(i) - (int) 'a']++;
            t_char_number[t.charAt(i) - (int) 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if (s_char_number[i] != t_char_number[i]) {
                return false;
            }
        }
        return true;
    }

    //assume Supplementary Characters are always valid
    public static boolean isAnagram1(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<String, Integer> s_char_number = new HashMap<>();
        Map<String, Integer> t_char_number = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            String key;
            Integer n;
            //s
            if (Character.isLetter(s.charAt(i))) {
                key = "" + s.charAt(i);
                n = s_char_number.get(key);
                s_char_number.put(key, n == null ? 1 : n + 1);
            } else {
                char[] sc = new char[]{s.charAt(i), s.charAt(i + 1)};
                key = String.valueOf(sc);
                n = s_char_number.get(key);
                s_char_number.put(key, n == null ? 1 : n + 1);
                i++;
            }
            // t
            if (Character.isLetter(t.charAt(i))) {
                key = "" + t.charAt(i);
                n = t_char_number.get(key);
                t_char_number.put(key, n == null ? 1 : n + 1);
            } else {
                char[] sc = new char[]{t.charAt(i), t.charAt(i + 1)};
                key = String.valueOf(sc);
                n = t_char_number.get(key);
                t_char_number.put(key, n == null ? 1 : n + 1);
                i++;
            }
        }
       return s_char_number.equals(t_char_number);
    }
}
