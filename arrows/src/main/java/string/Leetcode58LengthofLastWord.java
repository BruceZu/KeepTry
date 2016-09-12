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
 * @see <a href="https://leetcode.com/problems/length-of-last-word/">leetcode</a>
 */
public class Leetcode58LengthofLastWord {
    // case: "a "
    public int lengthOfLastWord(String s) {
        int lastLetter = -1;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) != ' ') {
                lastLetter = i;
                break;
            }
        }
        if (lastLetter == -1) {
            return 0;
        }

        int lastSpace = s.lastIndexOf(" ", lastLetter);
        if (lastSpace == -1) {
            return lastLetter + 1;
        }
        return lastLetter - lastSpace;
    }

    //  return s.trim().length()-1 -s.trim().lastIndexOf(" ");
}
