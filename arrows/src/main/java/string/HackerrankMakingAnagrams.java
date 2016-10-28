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
 * It is guaranteed that  and  consist of lowercase English letters.
 *
 * @see <a href="https://www.hackerrank.com/challenges/making-anagrams">hacker rank</a>
 */
public class HackerrankMakingAnagrams {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String a = s.nextLine();
        String b = s.nextLine();
        int[] times = new int[26];
        for (int i = 0; i < a.length(); i++) {
            times[a.charAt(i) - 'a']++;
        }
        for (int i = 0; i < b.length(); i++) {
            times[b.charAt(i) - 'a']--;
        }
        int r = 0;
        for (int i = 0; i < times.length; i++) {
            r += Math.abs(times[i]);
        }
        System.out.println(r);
    }
}
