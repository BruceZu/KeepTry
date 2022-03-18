//  Copyright 2022 The KeepTry Open Source Project
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

package two_pointer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// Write a function that takes a string as input and reverse only the vowels of a string.
public class Leetcode345ReverseVowelsofaString {
    static public String reverseVowels(String s) {
        if (s == null | s.length() <= 1) return s;
        char[] chars = s.toCharArray();
        int l = 0, r = chars.length - 1;
        while (l < r) { // Care
            while (l < r
                    && chars[l] != 'a' && chars[l] != 'e' && chars[l] != 'i' && chars[l] != 'o' && chars[l] != 'u'
                    && chars[l] != 'A' && chars[l] != 'E' && chars[l] != 'I' && chars[l] != 'O' && chars[l] != 'U'
                    ) {
                l++;
            }
            if (l == r) {
                break;
            }
            while (l < r
                    && chars[r] != 'a' && chars[r] != 'e' && chars[r] != 'i' && chars[r] != 'o' && chars[r] != 'u'
                    && chars[r] != 'A' && chars[r] != 'E' && chars[r] != 'I' && chars[r] != 'O' && chars[r] != 'U'
                    ) {
                r--;
            }
            if (l == r) {
                break;
            }
            if (chars[l] != chars[r]) {
                chars[l] ^= chars[r];
                chars[r] ^= chars[l];
                chars[l] ^= chars[r];
            }
            l++;
            r--;
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        // null, [], [a], [ai],[aA]
        System.out.println(reverseVowels("aA"));
    }

    static public String reverseVowels2(String s) {

        if (s == null | s.length() <= 1) return s;
        char[] chars = s.toCharArray();
        int l = 0, r = chars.length - 1;
        Set<Character> wowels = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));
        while (l < r) {
            while (l < r && !wowels.contains(chars[l])) {
                l++;
            }
            while (l < r && !wowels.contains(chars[r])) {
                r--;
            }
            if (chars[l] != chars[r]) {
                chars[l] ^= chars[r];
                chars[r] ^= chars[l];
                chars[l] ^= chars[r];
            }
            l++;
            r--;
        }
        return new String(chars);
    }
}
