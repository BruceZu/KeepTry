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

package array;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @see <a href="https://www.hackerrank.com/challenges/find-digits">hacker rank</a>
 */
public class HackerrankFindDigits {

    private static void calculate(Scanner s) {
        Integer t = s.nextInt(); // here

        char[] digits = String.valueOf(t).toCharArray();
        int r = 0;
        Set<Integer> divisible = new HashSet();
        for (int i = 0; i < digits.length; i++) {
            if (digits[i] == '0') { // here
                continue;
            }
            if (divisible.contains((int) digits[i])) { // care need (int)
                r++;
            } else if (t % (digits[i] - '0') == 0) { // here
                divisible.add((int) digits[i]); // here can just save (int) digits[i]
                r++;
            }
        }
        System.out.println(r);
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int tests = s.nextInt();
        for (int i = 0; i < tests; i++) {
            calculate(s);
        }
    }
}
