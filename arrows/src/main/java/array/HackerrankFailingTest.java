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
 * @see <a href ="https://www.hackerrank.com/contests/citrix-code-master/challenges/failing-tests/submissions/code/6963408">hacker rank</a>
 */
public class HackerrankFailingTest {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        float[] lows = new float[5];

        int size = 0;
        while (true) {
            float cur;
            try {
                cur = s.nextFloat();
            } catch (Exception e) {
                break;
            }
            if (size == 0) { // care
                lows[size++] = cur; // care
            } else if (cur > lows[size - 1]) {
                if (size < 5) {
                    lows[size++] = cur;
                }
            } else {
                int insertTo = -1;
                for (int i = 0; i < size; i++) {
                    if (cur < lows[i]) {
                        insertTo = i;
                        break; //care
                    }
                }
                int from = Math.min(4, size); // care
                for (int j = from; j >= insertTo + 1; j--) {
                    lows[j] = lows[j - 1];

                }
                lows[insertTo] = cur;
                if (size < 5) {
                    size++;
                }
            }

        }

        for (int i = 0; i < 5; i++) {
            int pre = (int) lows[i];
            int left = (int) (lows[i] * 1000000 - pre * 1000000);
            String l = String.valueOf(left);
            System.out.print(pre + ".");
            if (l.length() < 6) { //care
                for (int j = 1; j <= 6 - l.length(); j++) {
                    System.out.print("0");
                }
            }
            System.out.println(left);
        }
    }
}
