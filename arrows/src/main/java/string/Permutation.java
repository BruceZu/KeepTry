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

public class Permutation {

    /**
     * www.hackerrank.com test used by TrialPay a Visa company
     * <p>
     * For example:
     * isPermutation( "abc", "cba") return true.
     * isPermutation( "abc", "cbe") return false.
     * <p>
     * The time complexity for this function should be O(n)
     */

    static boolean isPermutation(String a, String b) {
        if (a == null || b == null) {
            return false;
        }
        if (a.length() != b.length()) {
            return false;
        }
        int maxIndex = 0;
        for (int i = 0; i < a.length(); i++) {
            int index = (int) a.charAt(i);
            if (index > maxIndex) {
                maxIndex = index;
            }
        }
        for (int i = 0; i < b.length(); i++) {
            int index = (int) b.charAt(i);
            if (index > maxIndex) {
                maxIndex = index;
            }
        }
        int[] v = new int[maxIndex + 1];
        for (int i = 0; i < a.length(); i++) {
            v[(int) a.charAt(i)]++;
        }
        for (int i = 0; i < b.length(); i++) {
            if (v[(int) b.charAt(i)] == 0) {
                return false;
            }
            v[(int) b.charAt(i)]--;
        }

        for (int i = 0; i < maxIndex; i++) {
            if (v[i] != 0) {
                return false;
            }
        }
        return true;
    }

    static boolean isPermutation_ok(String a, String b) {
        if (a == null || b == null) {
            return false;
        }
        if (a.length() != b.length()) {
            return false;
        }
        HashMap<Character, Integer> map = new HashMap(a.length());
        for (int i = 0; i < a.length(); i++) {
            char c = a.charAt(i);
            map.put(c, map.get(c) == null ? 1 : map.get(c) + 1);
        }
        for (int i = 0; i < b.length(); i++) {
            char c = b.charAt(i);
            if (map.get(c) == null || map.get(c) == 0) {
                return false;
            } else {
                map.put(c, map.get(c) - 1);
            }
        }
        for (Character c : map.keySet()) {
            if (map.get(c) != 0) {
                return false;
            }
        }
        return true;
    }
}
