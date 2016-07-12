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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>Difficulty: Easy
 * Given a string, we can "shift" each of its letter to its successive letter,
 * for example: "abc" -> "bcd". We can keep "shifting" which forms the sequence:
 * <p/>
 * "abc" -> "bcd" -> ... -> "xyz"
 * Given a list of strings which contains only lowercase alphabets,
 * group all strings that belong to the same shifting sequence.
 * <p/>
 * For example, given: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"],
 * Return:
 * <p/>
 * [
 * ["abc","bcd","xyz"],
 * ["az","ba"],
 * ["acef"],
 * ["a","z"]
 * ]
 * Note: For the return value, each inner list's elements must follow the lexicographic order.
 * <p/>
 * Hide Company Tags: Google, Uber
 * Hide Tags: Hash Table, String
 * Hide Similar Problems: (M) Group Anagrams
 * </pre>
 */

public class LC249GroupShiftedStrings {
    // the fast beat 98% no code shared

    /**
     * beat 90.57
     *
     * @param strings
     * @return
     */
    // idea is similar to group anagram
    public List<List<String>> groupStrings(String[] strings) {
        Arrays.sort(strings); // sort to get lexicographic order
        // all keys in map start with 'a'
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        String shifted;
        for (String s : strings) {
            shifted = shiftToA(s);
            if (!map.containsKey(shifted))
                map.put(shifted, new ArrayList<String>());
            map.get(shifted).add(s);
        }

        return new ArrayList<List<String>>(map.values());
    }

    private String shiftToA(String s) {
        if (s.charAt(0) == 'a') return s;
        StringBuilder sb = new StringBuilder();
        int shiftNum = s.charAt(0) - 'a';
        int letterNum;
        for (int i = 0; i < s.length(); i++) {
            letterNum = s.charAt(i) - 'a';
            if (letterNum >= shiftNum)
                sb.append((char) ('a' + (letterNum - shiftNum)));
            else
                sb.append((char) ('a' + (letterNum + 26 - shiftNum)));
        }

        return sb.toString();
    }
    /**
     *  other idea:
     *  Create a hashmap. key is a number (the offset compared to its first char),
     //value is a list of strings which have the same offset.
     //For each string, convert it to char array
     //Then subtract its first character for every character
     //eg. "abc" -> "0,1,2,"        "am"->"0,12,"

     ==
     HashMap<List<Integer>, List<String>> map:
     for "abc","bcd","xyz", the key would be [3, 1, 1] [how many numbers, 2nd - 1st, 3rd - 2nd]

     for "az","ba", the key would be [2, 25] [how many numbers, 2nd - 1st]
     (NOTICE: for "ba": a - b, since a < b, the result would be 26 + 'a' - 'b')

     thus, we have one unique key as List<Integer> for each Group, the List<String> for each key would be each group's result

     finally, iterate through the res and sort each List
     */
}
