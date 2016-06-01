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

package string;

/**
 * How to find first non repeated character of a given String?
 * Ex String: String myStr = "find first non repeated character of a given String"
 */
public class FirstNoRepeatedChar {
    /**
     * https://www.interviewzen.com/interview/3qSHDcT
     *
     * Find first non repeated character of a given String
     *
     * @param s given string
     * @return char   return first non repeated character or '\0' when no found
     */
    public static char firstNoReChar(String s) {
        // input check
        if (s == null || s.length() <= 1) {// Note!
            return '\0'; // no found.
        }
        // int array: using char's int value as index, value is this char's repeat times
        int[] exists = new int[256];

        // keep chars in sequence
        char[] sequence = new char[256];  // Note!
        int size = 0; // size of chars in sequence

        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            int v = (int) cur;
            if (exists[v] == 0) {
                sequence[size++] = cur;
            }
            exists[v]++;
        }
        // find the first non repeated  char
        for (int i = 0; i < size; i++) {
            char cur = sequence[i];
            if (exists[(int) cur] == 1) {
                return cur;
            }
        }
        return '\0'; // no found // Note!
    }
}
