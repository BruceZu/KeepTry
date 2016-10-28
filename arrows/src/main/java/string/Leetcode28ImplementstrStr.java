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
 * Note:
 * 1   if needle is empty ""
 * 2   i <=
 * 3   need boolean found
 * //todo:
 * <br><a href= "https://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_string_search_algorithm">Boyer–Moore string search algorithm </a>
 */
public class Leetcode28ImplementstrStr {
    public static int strStr(String haystack, String needle) {
        if (haystack == null || needle == null || needle.length() > haystack.length()) {
            return -1;
        }

        if (needle.equals("")) { // 1 corner case, special
            return 0;
        }

        for (int i = 0; i <= haystack.length() - needle.length(); i++) { // 2

            if (haystack.charAt(i) == needle.charAt(0)) {
                // can use haystack.substring(i,needle.length());
                boolean found = true;
                for (int j = 0; j < needle.length(); j++) { //
                    if (haystack.charAt(i + j) != needle.charAt(j)) {
                        found = false;
                        break;
                    }
                }
                if (found) { // 3
                    return i;
                }
            }
        }
        return -1;
    }
    /* --------------------------------------------------------------------------------------------------------------*/

    public static void main(String[] args) {
        System.out.println(strStr("", ""));
        System.out.println(strStr("", "a"));
        System.out.println(strStr("a", ""));
        System.out.println(strStr("a", "a"));
        System.out.println(strStr("mississippi", "issip"));
    }
}
