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

package array_two_pointer;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * <a href="https://leetcode.com/problems/minimum-window-substring/?tab=Description">LeetCode</a>
 * data structure:  using mapping and length of target
 * algorithm: 2 pointers
 */
public class Leetcode76MinimumWindowSubstring {

    // using array as mapping, assume only ASCII letter
    static public String minWindow(String str, String t) {
        char[] s = str.toCharArray();
        char[] target = t.toCharArray();
        int AllTCharCounts = target.length;
        int[] sizeOfTChar = new int[128];
        for (char c : target) {
            sizeOfTChar[c]++;
        }

        int l = 0, start = 0, end = Integer.MAX_VALUE;
        for (int r = 0; r < s.length; r++) {
            char charAtR = s[r];
            if (sizeOfTChar[charAtR] > 0) {
                AllTCharCounts--;
            }
            sizeOfTChar[charAtR]--;

            if (AllTCharCounts == 0) { // check update: covered Target
                while (AllTCharCounts == 0) {
                    if (sizeOfTChar[s[l]]++ == 0) { // restore what r done to find the minimum one end with r
                        AllTCharCounts++; // invalid and end the loop
                        if (r - l < end - start) {
                            start = l;
                            end = r;
                        }
                    }
                    l++;
                }
            }
        }
        return end == Integer.MAX_VALUE ? "" : str.substring(start, end + 1);
    }

    // ------------------------------using Map--------------------------------------------------------
    static public String minWindow2(String s, String t) {
        int start = 0, end = Integer.MAX_VALUE;
        Map<Character, Integer> charToCount = new HashMap();
        int allCount = t.length();

        for (int i = 0; i < t.length(); i++) {
            Integer counts = charToCount.get(t.charAt(i));
            charToCount.put(t.charAt(i), counts == null ? 1 : counts + 1);
        }

        int l = 0;
        for (int r = 0; r < s.length(); r++) {
            Character rChar = s.charAt(r);
            if (charToCount.containsKey(rChar)) {
                Integer rCharCount = charToCount.get(rChar);
                if (rCharCount > 0) {
                    allCount--;
                }
                charToCount.put(rChar, rCharCount - 1);

                if (allCount == 0) {// check update
                    while (allCount == 0) {
                        Character lChar = s.charAt(l);
                        if (charToCount.containsKey(lChar)) {
                            Integer lCharCount = charToCount.get(lChar);
                            if (lCharCount == 0) {
                                allCount++;
                                if (r - l < end - start) {
                                    start = l;
                                    end = r;
                                }
                            }
                            charToCount.put(lChar, lCharCount + 1);
                        }
                        l++;
                    }
                }
            }
        }
        return end == Integer.MAX_VALUE ? "" : s.substring(start, end + 1);
    }

    // ------------ Longest Substring with At Most Two Distinct Characters -------------------
    static int lengthOfLongestSubstringTwoDistinct(char[] s) {
        int[] map = new int[128];
        int charCount = 0, l = 0, r = 0, LengestSubstrLength = 0;
        while (r < s.length) {
            if (map[s[r]] == 0) {
                charCount++;
            }

            map[s[r]]++;
            r++;
            while (charCount > 2) {
                if (map[s[l++]]-- == 1) {
                    charCount--;
                }
            }
            LengestSubstrLength = Math.max(LengestSubstrLength, r - l);
        }
        return LengestSubstrLength;
    }

    // ----------- Longest Substring Without Repeating Characters --------------------------
    static int lengthOfLongestSubstringWithoutRepeating(char[] s) {
        int[] map = new int[128];
        int countOfMoreThanOne = 0, l = 0, r = 0, lengthOfLongestSubstr = 0;
        while (r < s.length) {
            if (map[s[r++]]++ > 0) {
                countOfMoreThanOne++;
            }
            while (countOfMoreThanOne > 0) {
                if (map[s[l++]]-- > 1) {
                    countOfMoreThanOne--;
                }
            }
            lengthOfLongestSubstr = Math.max(lengthOfLongestSubstr, r - l); //while valid, update d
        }
        return lengthOfLongestSubstr;
    }

    // -----------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println(minWindow("aaaaaaaaaaaabbbbbcdd", "abcdd")); // "abbbbbcdd"
        System.out.println(minWindow("a", "aa")); // ""
        System.out.println(minWindow("bdab", "ab")); //"ab"
        System.out.println(minWindow("ab", "b")); // b
        System.out.println(minWindow("bba", "ab")); // ba
        System.out.println(minWindow("abc", "ac")); // abc
        System.out.println(minWindow("a", "ab")); // ""
        System.out.println(minWindow("a", "a")); // a

        System.out.println(lengthOfLongestSubstringTwoDistinct("aabc".toCharArray()));
        System.out.println(lengthOfLongestSubstringWithoutRepeating("abcb123".toCharArray()));
    }

    // ---------------------------- Permutation. window equal with target exactly on char and number of char -------------------------------------------------------------------
    static public String minWindow_withExactlyNumberOfCharacter(String s, String t) {
        int[] startEnd = new int[2];
        startEnd[0] = 0;
        startEnd[1] = Integer.MAX_VALUE;
        Map<Character, Integer> target = new HashMap();
        int i = 0;
        while (i < t.length()) {
            Integer counts = target.get(t.charAt(i));
            target.put(t.charAt(i), counts == null ? 1 : counts + 1);
            i++;
        }
        Map<Character, Integer> status = new HashMap();
        int sub = -1;
        for (int add = 0; add < s.length(); add++) {
            Character toAddC = s.charAt(add);
            if (target.containsKey(toAddC)) {
                Integer counts = status.get(toAddC);
                if (counts == target.get(toAddC)) {
                    while (true) {
                        Character toSubC = s.charAt(sub);
                        if (!target.containsKey(toSubC)) {
                            sub++;
                            continue;
                        }
                        status.put(toSubC, status.get(toSubC) - 1);
                        sub++;
                        if (toSubC == toAddC) {
                            break;
                        }
                    }
                    while (!target.containsKey(s.charAt(sub))) {
                        sub++;
                    }
                    status.put(toAddC, counts);
                } else if (counts == null) {

                    if (sub == -1) {
                        sub = add;
                    }
                    status.put(toAddC, 1);
                } else { // counts < target.get(toAddC)
                    status.put(toAddC, counts + 1);
                }
                checkUpdate(target, status, toAddC, add, sub, startEnd);
            }
        }
        return startEnd[1] == Integer.MAX_VALUE ? "" : s.substring(startEnd[0], startEnd[1] + 1);
    }

    static private void checkUpdate(Map<Character, Integer> target,
                                    Map<Character, Integer> status,
                                    Character toAddC,
                                    int add,
                                    int sub,
                                    int[] startEnd) {
        if (target.get(toAddC) == status.get(toAddC) && target.equals(status)) {
            if (add - sub < startEnd[1] - startEnd[0]) {
                startEnd[0] = sub;
                startEnd[1] = add;
            }
        }
    }
}
