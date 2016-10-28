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

import java.util.Scanner;

/**
 * only space and 26 letter(lower case and upper case)
 * @see <a href="https://www.hackerrank.com/challenges/pangrams">hacker rank</a>
 */
public class HackerrankPangrams {
    public static void main(String[] args) {
        String s = new Scanner(System.in).nextLine();
        int count = 0;
        boolean[] have = new boolean[26];
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') { // here
                int index = Character.toLowerCase(s.charAt(i)) - 'a';
                if (!have[index]) {
                    have[index] = true;

                    if (++count == 26) {
                        System.out.println("pangram");
                        return;
                    }
                }
            }
        }
        System.out.println("not pangram");
    }
}
