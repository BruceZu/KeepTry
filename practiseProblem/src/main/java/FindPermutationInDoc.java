//  Copyright 2016 The Minorminor Open Source Project
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FindPermutationInDoc {
    /* string array， A and B. B is like a doc.
       check if there is a sub-list of doc that is A's permutation，
       require the sub-list is consecutive sub list,
        e.g.
        A = ["a", "b"],
        B = ["c", "b", "a"],
        return yes

        A = ["a", "b"],
        B = ["c", "b", "d", "a"],
        return no
     */

    private static Map getMapOf(String[] arr, int startIndexInclusive, int endIndexInclusive) {
        HashMap<String, Integer> r = new HashMap<String, Integer>();
        for (int i = startIndexInclusive; i <= endIndexInclusive; i++) {
            String w = arr[i];
            if (r.containsKey(w)) {
                r.put(w, r.get(w) + 1);
                continue;
            }
            r.put(w, 1);
        }
        return r;
    }

    public static boolean hasPermutationIn(String[] given, String[] doc) {
        Map givenMap = getMapOf(given, 0, given.length - 1);
        int index = 0;

        while (doc.length - index >= given.length) {
            String[] checkPart = Arrays.copyOf(doc, given.length);
            Map withMap = getMapOf(doc, index, index + given.length - 1);
            if (givenMap.equals(withMap)) {
                return true;
            }
            index++;
        }
        return false;
    }
}
