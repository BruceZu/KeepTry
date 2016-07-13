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

package nosubmmitted;

import java.util.ArrayList;
import java.util.List;

/**
 * Difficulty: Easy
 * 293. Flip Game
 * https://leetcode.com/problems/flip-game/
 * <p/>
 * You are playing the following Flip Game with your friend: Given a string that contains only these two characters:<pre>
 * + and -, you and your friend take turns to flip two consecutive "++" into "--".
 * The game ends when a person can no longer make a move and therefore the other person will be the winner.
 * <p/>
 * Write a function to compute all possible states of the string after one valid move.
 * <p/>
 * For example, given s = "++++", after one move, it may become one of the following states:
 * <p/>
 * [
 * "--++",
 * "+--+",
 * "++--"
 * ]
 * If there is no valid move, return an empty list [].
 * <p/>
 * Hide Company Tags Google
 * Hide Tags String
 * Hide Similar Problems (M) Flip Game II
 */
public class LC293FlipGame {
    public List<String> generatePossibleNextMoves2(String s) {
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < s.length() - 1; i++)
            if (s.substring(i, i + 2).equals("++"))
                ret.add(s.substring(0, i) + "--" + s.substring(i + 2));
        return ret;
    }

    public List<String> generatePossibleNextMoves3(String s) {
        List<String> ans = new ArrayList<>();
        // valid inputs
        if (s == null || s.length() < 2) {
            return ans;
        }
        for (int pos = s.indexOf("++"); pos != -1; pos = s.indexOf("++", pos + 1)) {
            ans.add(s.substring(0, pos) + "--" + s.substring(pos + 2));
        }
        return ans;
    }

    public List<String> generatePossibleNextMoves4(String s) {
        List<String> list = new ArrayList<String>();
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == '+' && s.charAt(i - 1) == '+') {
                list.add(s.substring(0, i - 1) + "--" + s.substring(i + 1, s.length()));
            }
        }
        return list;
    }

    public List<String> generatePossibleNextMoves(String s) {
        List list = new ArrayList();
        for (int i = -1; (i = s.indexOf("++", i + 1)) >= 0; )
            list.add(s.substring(0, i) + "--" + s.substring(i + 2));
        return list;
    }
}
