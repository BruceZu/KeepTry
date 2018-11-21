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
 * n is odd 1<= n <= 1000001 -10000 <= x <= 10000
 *
 * @see <a
 *     href="https://www.hackerrank.com/challenges/find-the-median/submissions/code/27801277">hacker
 *     rank</a>
 */
public class HackerrankFindtheMedian {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    way1(s);
  }

  public static void way(Scanner s) {
    int total = s.nextInt();
    int[] num = new int[20001];

    for (int i = 1; i <= total; i++) {
      num[s.nextInt() + 10000]++; // -10000 will be 0
    }

    int count = 0;
    for (int i = 0; i < 20001; i++) {
      if (num[i] != 0) {
        if (num[i] + count >= total / 2 + 1) { // care: >=
          System.out.println(i - 10000);
          break;
        } else {
          count += num[i];
        }
      }
    }
  }

  public static void way1(Scanner s) {
    int[] tmp = new int[s.nextInt()];
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < tmp.length; i++) {
      tmp[i] = s.nextInt();
      min = Math.min(min, tmp[i]);
      max = Math.max(max, tmp[i]);
    }
    int[] num = new int[max - min + 1]; // ar may be have duplicated member -10000~10000
    for (int i = 0; i < tmp.length; i++) {
      num[tmp[i] - min]++;
    }
    int count = 0;
    for (int i = 0; i < num.length; i++) {
      if (num[i] != 0 && num[i] + count >= tmp.length / 2 + 1) { // care: >=
        System.out.println(i + min);
        break;
      } else {
        count += num[i];
      }
    }
  }
}
