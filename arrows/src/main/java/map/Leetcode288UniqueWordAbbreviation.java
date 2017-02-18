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

package map;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://leetcode.com/problems/unique-word-abbreviation/?tab=Description">Leetcode</a>
 * <pre>
 */
public class Leetcode288UniqueWordAbbreviation {
    static class ValidWordAbbr {
        private String abbreviation(String str) {
            int length = str.length();
            if (length <= 2) {
                return str;
            }
            return new StringBuilder().append(str.charAt(0))
                    .append(str.length() - 2)
                    .append(str.charAt(length - 1))
                    .toString();
        }

        private Map<String, String> abbreToFroms; // also can be use array [][][] to map

        public ValidWordAbbr(String[] dictionary) {
            abbreToFroms = new HashMap();
            for (String str : dictionary) {
                String abbre = abbreviation(str);
                // value "" marks those abbreviation with more than one string
                if (abbreToFroms.containsKey(abbre)) {
                    if (!abbreToFroms.get(abbre).equals(str)) {
                        abbreToFroms.put(abbre, "");
                    }
                    // duplicated word in dictionary
                } else {
                    abbreToFroms.put(abbre, str);
                }
            }
        }

        public boolean isUnique(String word) {
            String abbre = abbreviation(word);
            return !abbreToFroms.containsKey(abbre) || abbreToFroms.get(abbre).equals(word);
        }
    }
}
