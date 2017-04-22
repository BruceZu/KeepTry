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

package hash;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Note: All inputs will be in lower-case.
 *
 * @see <a href="https://leetcode.com/problems/anagrams/">leetcode</a>
 */
public class Leetcode49GroupAnagrams {
    // too slow
    public static List<List<String>> groupAnagrams3(String[] strs) {
        Map<Map<Character, Integer>, List<String>> r = new HashMap();
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            Map<Character, Integer> id = new HashMap();
            for (int j = 0; j < str.length(); j++) {
                Integer times = id.get(str.charAt(j));
                id.put(str.charAt(j), times == null ? 1 : times + 1);
            }
            if (r.containsKey(id)) {
                r.get(id).add(str);
            } else {
                List<String> s = new ArrayList();
                s.add(str);
                r.put(id, s);
            }
        }
        return new ArrayList<>(r.values());
    }

    // 19 ms O(?)
    public List<List<String>> groupAnagrams2(String[] strs) {
        Map<String, List<String>> map = new HashMap();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String hash = new String(chars);

            List<String> l = map.get(hash);
            if (l == null) {
                l = new ArrayList();
                map.put(hash, l);
            }
            l.add(str);
        }
        return new ArrayList(map.values());
    }

    static public List<List<String>> groupAnagrams(String[] strs) {
        int[] prime = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31,
                37, 41, 43, 47, 53, 59, 61, 67, 71, 73,
                79, 83, 89, 97, 101, 103}; // 26 letter. start from 3. Not 2.

        Map<Integer, List<String>> map = new HashMap<>();
        for (String str : strs) {
            int hash = 1;
            for (char c : str.toCharArray()) {
                hash *= prime[c - 'a'];
            }

            List<String> l = map.get(hash);
            if (l == null) {
                l = new ArrayList();
                map.put(hash, l);
            }
            l.add(str);
        }
        return new ArrayList(map.values());
    }

    public static void main(String[] args) {
        groupAnagrams(new String[]{"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"});
    }
}
