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

/**
 * <a href="https://leetcode.com/problems/reverse-string/#/description">Leetcode</a>
 */
public class Leetcode344ReverseString {
    // takes a string as input and returns the string reversed.
    // O(N)
    public String reverseString(String s) {
        if (s == null || s.length() <= 1) return s;

        char[] chars = s.toCharArray();
        int l = 0, r = chars.length - 1;
        while (l < r) {
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
