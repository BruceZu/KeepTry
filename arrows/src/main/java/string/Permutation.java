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

import java.util.HashMap;
import java.util.Map;

public class Permutation {

    /**
     * www.hackerrank.com test used by TrialPay a Visa company
     * <pre>
     * For example:
     * isPermutation( "abc", "cba") return true.
     * isPermutation( "abc", "cbe") return false.
     *
     * The time complexity for this function should be O(n)
     *
     * lower case or not.
     * A = 65  Z = 90
     * a = 97  z = 112
     * use int[] = 112
     * But it may be not other character
     */
    static boolean isPermutation(String a, String b) {
        if (a == null || b == null) {
            return false;
        }
        if (a.length() != b.length()) {
            return false;
        }
        HashMap<Character, Integer> ACharTimes = new HashMap(a.length());
        for (int i = 0; i < a.length(); i++) {
            char c = a.charAt(i);
            Integer times = ACharTimes.get(c);
            ACharTimes.put(c, times == null ? 1 : times + 1);
        }
        for (int i = 0; i < b.length(); i++) {
            char bChar = b.charAt(i);
            Integer times = ACharTimes.get(bChar);
            if (times == null) {
                return false;
            } else {
                ACharTimes.put(bChar, times - 1);
            }
        }

        for (Map.Entry<Character, Integer> cur : ACharTimes.entrySet()) {
            if (cur.getValue() != 0) {// >0  or <0
                return false;
            }
        }
        return true;
    }
}
