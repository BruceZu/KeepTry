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
 * @see <a href="https://www.hackerrank.com/challenges/missing-numbers">hacker ranker</a>
 */
public class HackerrankMissingNumber {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        int[] a = new int[s.nextInt()];
        for (int i = 0; i < a.length; i++) {
            a[i] = s.nextInt();
        }

        int[] b = new int[s.nextInt()];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < b.length; i++) {
            b[i] = s.nextInt();
            min = Math.min(min, b[i]);
            max = Math.max(max, b[i]);
        }

        Integer[] times = new Integer[max - min + 1];

        for (int i = 0; i < b.length; i++) {
            times[b[i] - min] = times[b[i] - min] == null ? 1 : times[b[i] - min] + 1;
        }

        for (int i = 0; i < a.length; i++) {
            times[a[i] - min]--;// care: it is a[i]
        }

        for (int i = 0; i < times.length; i++) {
            if (times[i] != null && times[i] != 0) {
                System.out.print(i + min + " ");
            }
        }
    }
}
