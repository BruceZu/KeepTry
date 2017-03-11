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

package dp;

import java.util.List;
import java.util.Set;

/**
 * <a href="https://leetcode.com/problems/word-break/">leetcode</a>
 * Pocket Gems
 */
public class Leetcode139WordBreak {
    // O(N^2) assume dict.contains(candidateOfLastSubstr) is O(1)
    public boolean wordBreak(String s, List<String> dict) {
        boolean[] knownStatusOfSubStrTo = new boolean[s.length() + 1];
        knownStatusOfSubStrTo[0] = true;
        for (int to = 1; to <= s.length(); to++) {
            for (int from = 0; from < to; from++) {
                String candidateOfLastSubstr = s.substring(from, to);
                if (knownStatusOfSubStrTo[from] && dict.contains(candidateOfLastSubstr)) {
                    knownStatusOfSubStrTo[to] = true;
                    break;
                }
            }
        }
        return knownStatusOfSubStrTo[s.length()];
    }

    //  O(n*Dick's length).
    public boolean wordBreak2(String s, List<String> dict) {
        boolean[] isAbleToBreakStrTo = new boolean[s.length() + 1];
        isAbleToBreakStrTo[0] = true;

        for (int to = 1; to <= s.length(); to++) {
            for (String dic : dict) {
                if (dic.length() <= to
                        && isAbleToBreakStrTo[to - dic.length()]
                        && s.substring(to - dic.length(), to).equals(dic)) {
                    isAbleToBreakStrTo[to] = true;
                    break;
                }
            }
        }
        return isAbleToBreakStrTo[s.length()];
    }
}
