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

/**
 * Given a string with parentheses, return a string with balanced parentheses by
 * removing the fewest characters possible, do not add anything to the string.
 * Examples:
 * "()"      ->"()"
 * "((((("   ->""
 * "(()()("  ->"()()"
 * ")(())("  ->"(())"
 */
public class ParseBalancedParentheses {
    static char LEFT = '(';
    static char RIGHT = ')';

    private static int prep(char[] s, int p) {
        for (int i = p - 1; i >= 0; i--) {
            if (s[i] != 0) {
                return i;
            }
        }
        return -1;
    }

    public static String parse(String str) {
        char[] s = str.toCharArray();
        int[] index = new int[s.length];

        for (int p = 1; p < s.length; p++) {
            int pp = prep(s, p);
            if (pp != -1 && s[p] == RIGHT && s[pp] == LEFT) {
                index[p] = s[p];
                index[pp] = s[pp];
                s[p] = 0;
                s[pp] = 0;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length; i++) {
            if (index[i] != 0) {
                sb.append((char) index[i]);
            }
        }
        return sb.toString();
    }
}