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
 * <pre>
 * 1 Filter: What char to care, and what is not.
 *      Assume this is <strong>NO supplementary character</strong>.
 *      ' ', ',' and '.' is not digit or letter.
 *      word only contains digit or letter and only care digit and letter.
 * 2 when world is digits or only only letter. do not reverse
 *
 * 3 Check word:
 *   i++
 *   i-- once find the right bound of a word when the current index i is out of bounds
 *   of arr or is not letter or digit.
 *
 * swap:
 *      if the value is same and using ^ then then error: 0
 *       ++ and --
 */
public class ReverseWords {

    public static String reverseWords(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }
        char[] arr = s.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];

            if (!Character.isLetterOrDigit(arr[i])) {
                continue;
            }
            int iw = i;

            boolean isDigit = Character.isDigit(c);
            i++;
            while (Character.isLetterOrDigit(arr[i]) && i < arr.length) { // from i
                if (isDigit && Character.isLetter(arr[i])) {
                    isDigit = false;
                }
                i++;
            }
            i--;  // note

            if (!isDigit && i != iw) {
                int wi = i;
                while (iw < wi && arr[iw] != arr[wi]) {
                    arr[iw] ^= arr[wi];
                    arr[wi] ^= arr[iw];
                    arr[iw] ^= arr[wi];
                    iw++;
                    wi--;
                }
            }
        }
        return new String(arr);
    }
}
