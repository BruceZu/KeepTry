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

package locked;

import java.util.HashMap;
import java.util.Map;

/**
 * 294. Flip Game II
 * https://leetcode.com/problems/flip-game-ii/
 * Difficulty: Medium
 * <p/>
 * You are playing the following Flip Game with your friend: <pre>
 * Given a string that contains only these two characters: + and -,
 * you and your friend take turns to flip two consecutive "++" into "--".
 * The game ends when a person can no longer make a move and therefore the other person will be the winner.<pre>
 * <p/>
 * Write a function to determine if the starting player can guarantee a win.
 * <p/>
 * For example, given s = "++++", return true. The starting player can guarantee a win by flipping the middle "++" to become "+--+".
 * <p/>
 * Follow up:
 * Derive your algorithm's runtime complexity.
 * <p/>
 * Hide Company Tags Google
 * Hide Tags Backtracking
 * Hide Similar Problems (E) Nim Game (E) Flip Game
 */
public class LC294FlipGameII {
    //  without shared solution which beat 99.5

    // idea only, beat 92, using char array will be beat 98.45, but not easy to read.
    Map<String, Boolean> cachedResult = new HashMap();

    public boolean canWin(String s) {
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.startsWith("++", i)) {
                String str = s.substring(0, i) + "--" + s.substring(i + 2);
                if (!cachedResult.containsKey(str)) {
                    cachedResult.put(str, canWin(str)); //cache all
                }
                if (!cachedResult.get(str)) { //defeat other with this way, so I win.
                    return true;
                }
            }
        }
        // all ways are failed, I failed completely
        return false;
    }
}
