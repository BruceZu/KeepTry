//  Copyright 2017 The KeepTry Open Source Project
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

//    S is a endless string ”...zabcdefghijklmnopqrstuvwxyza...”
//    P is a given string.
//    - P contains only English lower case characters
//    - P's length is may great than 10,000.
//    How many distinguish substrings in P. That each of them
//    is also substring of S.
//
//   E.g.:
//   input P：a
//   output ：1
//   explain： only 'a' is substring of S
//
//   input P：cac
//   output ：2
//   explain： only 'a' and 'c' is substring of S
//
//   input P：zab
//   output ：6
//   explain： 'z','a','b','za','ab','zab' are substring of S
public class SubStrOfCircleStr {
    // runtime complexity O(N)，
    // space complexity O(1)
    public static int numberOfDistinguishContinousSubStringsOfSIn(String P) {
        if (P == null || P.length() == 0) {
            return 0;
        }
        if (P.length() == 1) {
            return 1;
        }

        int magicNumber = 1;
        int[] map = new int[26];
        map[P.charAt(0) - 'a'] = magicNumber;

        for (int i = 1; i < P.length(); i++) {
            char cur = P.charAt(i);
            magicNumber =
                    cur - P.charAt(i - 1) == 1 || cur == 'a' && P.charAt(i - 1) == 'z'
                            ? magicNumber + 1
                            : 1;
            if (magicNumber > map[cur - 'a']) {
                map[cur - 'a'] = magicNumber;
            }
        }
        int sum = 0;
        for (int i = 0; i < map.length; i++) {
            sum += map[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(
                numberOfDistinguishContinousSubStringsOfSIn("zabcxabcdefxcdefxcdexbcdem"));
        System.out.println(numberOfDistinguishContinousSubStringsOfSIn("zabcabcdefcdefcdebcde"));
    }
}
