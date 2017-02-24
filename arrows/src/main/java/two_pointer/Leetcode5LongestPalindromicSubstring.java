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

package two_pointer;

public class Leetcode5LongestPalindromicSubstring {
    class Solution {
        private int leftIndex, size;

        public String longestPalindrome(String s) {
            int length = s.length();
            if (length < 2)
                return s;

            for (int i = 0; i < length - 1; i++) {
                extendPalindrome(s, i, i);  //assume odd length
                extendPalindrome(s, i, i + 1); //assume even length.
            }
            return s.substring(leftIndex, leftIndex + size);
        }

        private void extendPalindrome(String s, int l, int r) {
            while (l >= 0 && r <= s.length() - 1 && s.charAt(l) == s.charAt(r)) {
                l--;
                r++;
            }
            if (size < r - l - 1) {
                leftIndex = l + 1;
                size = r - l - 1;
            }
        }
    }
    //  checking from outside towards inner side is not simple
    //    a b c, c b a, c b a
    //    a b c m n c b a
    //
    //    a b b a
    //    a b c b a
}
