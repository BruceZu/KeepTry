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

package probability.permutation;

/**
 * <pre>
 *    1 2 3 4    r
 *    0 1 2 3 <-- index
 *
 *               k    k/6    (k-1)/6 +1     (k-1)/6
 *    1 2 3 4    1      0        1            0
 *    1 2 4 3    2      0        1            0
 *    1 3 2 4    3      0        1            0
 *    1 3 4 2    4      0        1            0
 *    1 4 2 3    5      0        1            0
 *    1 4 3 2    6      1        1            0
 *
 *    2 1 3 4    7      1        2            1
 *    2 1 4 3    8      1        2            1
 *    2 3 1 4    9      1        2            1
 *    2 3 4 1    10     1        2            1
 *    2 4 1 3    11     1        2            1
 *    2 4 3 1    12     2        2            1
 *
 *    3 1 2 4    13     2        3            2
 *
 *    if( k % factorial ==0){
 *        reverse remains
 *        or
 *        k = factorial;
 *           e.g. k = 12, the first number index is (12-1)/6 = 1, the first number is '2',
 *           adjust r to
 *              2 1 3 4
 *                0 1 2
 *              0 1 2 3
 *           next loop:
 *                 k = 6, factorial = 2, the second number index is (6-1)/2 = 2, it is '4'
 *           adjust r to
 *              2 4 1 3
 *                  0 1
 *              0 1 2 3
 *           next loop:
 *                 k = 2, factorial = 1, the third number index is (2-1)/1 = 1, it is '3'
 *           adjust r to
 *              2 4 3 1
 *                    0
 *              0 1 2 3
 *    }
 */
public class Leetcode60PermutationSequence2 {

    private char[] r;
    private int size;

    private boolean[] selectedIndex;
    private int[] facts;
    private int[] choices;

    private void init(int n) {
        choices = new int[n];
        facts = new int[n + 1]; //factorial
        facts[0] = 1;
        for (int i = 1; i <= n; i++) {
            choices[i - 1] = i;
            facts[i] = i * facts[i - 1];
        }

        selectedIndex = new boolean[n];
        r = new char[n];
        size = 0;
    }

    private void plusLeft() {
        if (size != choices.length) {
            for (int i = choices.length - 1; i >= 0; i--) {
                if (!selectedIndex[i]) {
                    r[size++] = (char) (choices[i] + '0'); //Note: Given n will be between 1 and 9 inclusive.
                    if (size == choices.length) {
                        break;
                    }
                }
            }
        }
    }

    private int choiceIndex(int choiceth) {
        int c = 0;
        for (int i = 0; i < choices.length; i++) {
            if (selectedIndex[i] == false) {
                c++;
                if (c == choiceth) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void per(int n, int k) {
        int f = facts[n - 1];
        int choiceth = (k - 1) / f + 1;
        int left = k % f;
        int cIndex = choiceIndex(choiceth);
        selectedIndex[cIndex] = true;
        r[size++] = (char) (choices[cIndex] + '0');

        if (left != 0) {
            per(n - 1, left);
        }
    }

    public String getPermutation(int n, int k) {
        init(n);
        per(n, k);
        plusLeft();
        return new String(r);
    }
}
