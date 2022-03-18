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

public class Leetcode541ReverseStringII {
    /**
     * <pre>
     * Given a string and an integer k, you need to reverse the first k characters for every
     * 2k characters counting from the start of the string. If there are less than k characters left,
     * reverse all of them. If there are less than 2k but greater than or equal to k characters,
     * then reverse the first k characters and left the other as original.
     *
     * The string consists of lower English letters only.
     * Length of the given string and k will in the range [1, 10000]
     */
    public String reverseStr2(String str, int k) {
        int s = 0;
        char[] chars = str.toCharArray();
        int lastIndex = chars.length - 1;
        while (true) {
            if (lastIndex < s) {
                return new String(chars);
            }
            if (s <= lastIndex && lastIndex < s + k) {
                swap(chars, s, lastIndex);
                return new String(chars);
            }
            if (s + k <= lastIndex) {
                swap(chars, s, s + k - 1);
            }
            s = s + 2 * k;
        }
    }

    private void swap(char[] chars, int l, int r) {
        while (l < r) {
            if (chars[l] != chars[r]) {
                chars[l] ^= chars[r];
                chars[r] ^= chars[l];
                chars[l] ^= chars[r];
            }
            l++;
            r--;
        }
    }

    public String reverseStr(String str, int k) {
        int s = 0;
        char[] chars = str.toCharArray();
        int lastIndex = chars.length - 1;
        while (s<chars.length ) {
            swap(chars, s, Math.min(s+k-1,lastIndex));
            s = s + 2 * k;
        }
        return  new String (chars);
    }
}
