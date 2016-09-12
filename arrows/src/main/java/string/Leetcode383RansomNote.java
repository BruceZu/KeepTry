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

/**
 * You may assume that both strings contain only lowercase letters.
 * Each  letter  in  the  magazine  string  can  only  be  used  once  in  your  ransom  note.
 *
 * @see <a href="https://leetcode.com/problems/ransom-note/">leetcode</a>
 */
public class Leetcode383RansomNote {
    public boolean canConstruct(String ransomNote, String magazine) {
        int[] cNums = new int[26];
        char[] magazineArray = magazine.toCharArray();
        char[] ransomNoteArray = ransomNote.toCharArray();
        for (char c : magazineArray) {
            cNums[(int) c - (int) 'a']++;
        }
        for (char c : ransomNoteArray) {
            if (--cNums[(int) c - (int) 'a'] < 0) {
                return false;
            }
        }
        return true;
    }
}
