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

import stack.ArrayStack;
import stack.IStack;

public class ValidDelimiters {
    private static String OPENING = "{[(<";
    private static String CLOSING = "}])>";

    public static boolean isValidDelimiters(String str) {
        char[] chars = str.toCharArray();
        IStack<Character> stack = new ArrayStack(20);
        for (char c : chars) {
            int oi = OPENING.indexOf(c);
            if (oi != -1) {
                stack.push(c);
                continue;
            }

            int ci = CLOSING.indexOf(c);
            if (ci != -1) {
                if (stack.isEmpty()) {
                    return false;
                }
                if (stack.peek().equals(OPENING.charAt(ci))) {
                    stack.pop();
                    continue;
                }
                return false;
            }
        }
        if (stack.isEmpty()) {
            return true;
        }
        return false;
    }
}
