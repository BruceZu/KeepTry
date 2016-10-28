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

import java.util.Scanner;

/**
 * each stick is a positive integer.
 * sticks are left (of non-zero length)
 * calcuate the length of smallest sticks (excluding zero-length sticks)
 * @see <a href="https://www.hackerrank.com/challenges/cut-the-sticks">hacker rank</a>
 */
public class HackerrankCuttheSticks {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int arr[] = new int[n];
        int r = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
            if (arr[i] != 0) {
                r++;
                min = Math.min(min, arr[i]);
            }

        }
        System.out.println(r);
        while (true) {
            r = 0;
            int lastmin = min;
            min = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (arr[i] != 0) {
                    arr[i] -= lastmin;
                    if (arr[i] != 0) {
                        min = Math.min(min, arr[i]);
                        r++;
                    }
                }
            }
            if (r == 0) {
                break;
            } else {
                System.out.println(r);
            }
        }
    }
}
