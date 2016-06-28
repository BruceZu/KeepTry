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

import java.util.HashMap;
import java.util.Map;

public class FirstRepeatedString {
    /**
     * Precess null respectively
     *
     * @return null if there is not found. “nullnull” if the the second null is first found.
     */
    public static String firstDuplicate(String[] strs) {
        if (strs == null || strs.length <= 1) {
            return null;
        }
        Map<String, Integer> sn = new HashMap(strs.length); // str -> numbers

        sn.put(strs[0], 1);
        int nullTimes = 0;
        for (int i = 1; i < strs.length; i++) {
            String v = strs[i];
            if (v == null) {
                nullTimes++;
                if (nullTimes == 2) {
                    return "nullnull";
                }
            } else {
                if (sn.get(v) == null) {
                    sn.put(v, 1);
                } else {
                    return v;
                }
            }
        }
        return null;
    }
}
