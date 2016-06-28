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

package array;

import java.util.HashSet;
import java.util.Set;

public class FirstRepeatedString {
    /**
     * Precess null respectively
     *
     * @return null if there is not found. “nullnull” if the the second null is first found.
     * That is we are assuming there is not string "nullnull".
     * This need verify with user.
     *
     * runtime O(N)
     */
    public static String firstDuplicate(String[] strs) {
        if (strs == null || strs.length <= 1) {
            return null;
        }
        Set<String> sn = new HashSet<>(strs.length); // str -> numbers

        int nullTimes = 0;
        for (int i = 0; i < strs.length; i++) {
            String v = strs[i];
            if (v == null) {
                nullTimes++;
                if (nullTimes == 2) {
                    return "nullnull";
                }
            } else {
                if (sn.contains(v)) {
                   return v;
                } else {
                    sn.add(v);
                }
            }
        }
        return null;
    }
}
