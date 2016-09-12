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

package stack;

/**
 * @see <a herf="http://docs.oracle.com/javase/tutorial/essential/regex/literals.html">metacharacter</a>
 * <([{\^-=$!|]})?*+.>
 */
public class Leetcode20ValidParentheses3 {
    // very slow
    public static boolean isValid3(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        if (s.length() % 2 != 0) {
            return false;
        }
        boolean done = false;
        while (!done) {
            done = true;
            if (s.contains("{}") || s.contains("[]") || s.contains("()")) {
                done = false;
                s = s.replaceAll("(\\Q{\\E})?", "");
                s = s.replaceAll("(\\[\\])?", "");
                s = s.replaceAll("(\\(\\))?", "");
            } else {
                continue;
            }
        }
        return s.length() == 0;
    }
}
