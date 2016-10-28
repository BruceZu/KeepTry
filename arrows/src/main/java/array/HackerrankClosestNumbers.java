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

import java.util.Arrays;
import java.util.Scanner;

/**
 * 2<= N <= 200000
 * -10^7 <= ai <= 10^7
 * ai!=aj,  1 <= i <= j <= N
 *
 * @see <a href="https://www.hackerrank.com/challenges/closest-numbers">hacker rank</a>
 */
public class HackerrankClosestNumbers {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int[] ar = new int[s.nextInt()];
        for (int i = 0; i < ar.length; i++) {
            ar[i] = s.nextInt();
        }
        Arrays.sort(ar);
        int min = Integer.MAX_VALUE;
        int[] r = new int[ar.length * 2];
        int size = 0;
        for (int i = 1; i < ar.length; i++) {
            int dis = ar[i] - ar[i - 1];
            if (dis < min) {
                min = dis;
                size = 0;
                r[size++] = ar[i - 1];
                r[size++] = ar[i];

            } else if (dis == min) {
                r[size++] = ar[i - 1];
                r[size++] = ar[i];
            }
        }
        for (int i = 0; i < size; i++) {
            System.out.print(r[i] + " ");
        }
    }
}
