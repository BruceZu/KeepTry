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

package array.permutation;

public class Leetcode60PermutationSequence2 {

    private int[] r;
    private int size;

    private boolean[] selectedIndex;
    private int[] facts;
    private int[] choices;

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

    private void init(int n) {
        choices = new int[n];
        for (int i = 0; i < n; i++) {
            choices[i] = i + 1;
        }

        facts = new int[n];
        facts[0] = 1;
        for (int i = 1; i < n; i++) {
            facts[i] = i * facts[i - 1];
        }

        selectedIndex = new boolean[n];
        r = new int[n];
        size = 0;
    }

    private void plusLeft() {
        if (size != choices.length) {
            for (int i = choices.length - 1; i >= 0; i--) {
                if (!selectedIndex[i]) {
                    r[size++] = choices[i];
                    if (size == choices.length) {
                        break;
                    }
                }
            }
        }
    }

    private void per(int n, int k) {
        int fact = facts[n - 1];
        int choiceth = k / fact;
        int left = k % fact;
        if (left == 0) {
            int cIndex = choiceIndex(choiceth);
            selectedIndex[cIndex] = true;
            r[size++] = choices[cIndex];
        } else {
            choiceth++;
            int cIndex = choiceIndex(choiceth);
            selectedIndex[cIndex] = true;
            r[size++] = choices[cIndex];
            per(n - 1, left);
        }
    }

    public String getPermutation(int n, int k) {
        init(n);
        per(n, k);
        plusLeft();
        StringBuilder re = new StringBuilder();
        for (int i = 0; i < n; i++) {
            re.append(r[i]);
        }
        return re.toString();
    }
}
