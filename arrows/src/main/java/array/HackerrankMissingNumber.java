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
 * The difference between maximum and minimum number in B is less than or equal to 100
 * 1<=n,m<=1000010; n<=m
 * 1<=x<=10000;
 * Integer.MAX_VALUE: 2147483647, 2G
 *
 * @see <a href="https://www.hackerrank.com/challenges/missing-numbers">hacker ranker</a>
 */
public class HackerrankMissingNumber {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        // way1(s);
        int n = s.nextInt();
        int[] times = new int[10000];
        for (int i = 1; i <= n; i++) {
            times[s.nextInt()]--;
        }

        int m = s.nextInt();

        for (int i = 1; i <= m; i++) {
            times[s.nextInt()]++;
        }
        for (int i = 0; i < times.length; i++) {
            if (times[i] != 0) {
                System.out.print(i + " ");
            }
        }
    }

    public static void way1(Scanner s) {
        int n = s.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < a.length; i++) {
            a[i] = s.nextInt();
        }

        int m = s.nextInt();
        int[] b = new int[m];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < b.length; i++) {
            b[i] = s.nextInt();
            min = Math.min(min, b[i]);
            max = Math.max(max, b[i]);
        }

        int[] times = new int[max - min + 1];

        for (int i = 0; i < m; i++) {
            times[b[i] - min]++;
        }

        for (int i = 0; i < n; i++) {
            times[a[i] - min]--;// care: it is a[i]
        }

        for (int i = 0; i < times.length; i++) {
            if (times[i] != 0) {
                System.out.print(i + min + " ");
            }
        }
    }
}
