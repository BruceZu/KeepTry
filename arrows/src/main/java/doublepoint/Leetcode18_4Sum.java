//  Copyright 2017 The keepTry Open Source Project
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

package doublepoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <pre>
 * n*(n-1)*(n-2)*(n-3),  O(N^4)
 * Two pointer: O(N^3)
 * BinarySearch O(N^2*logN)
 *
 * duplicates, -> sort (nlogn), then ...
 */

public class Leetcode18_4Sum {
    /**
     * Two pointer: O(N^3)
     * performance improvement: bounder checking to save loop times
     * || num[i] * 4 > target
     * || num[i] + num[num.length - 1] * 3 < target
     * ----
     * || num[i] + num[j] * 3 > target
     * || num[i] + num[j] + num[num.length - 1] * 2 < target
     */
    public List<List<Integer>> fourSum_two_pointer(int[] num, int target) {
        ArrayList<List<Integer>> ans = new ArrayList<>();
        if (num.length < 4) return ans;
        Arrays.sort(num);
        for (int i = 0; i < num.length - 3; i++) {
            if (i > 0 && num[i] == num[i - 1]) continue;

            for (int j = i + 1; j < num.length - 2; j++) {
                if (j > i + 1 && num[j] == num[j - 1]) continue;

                int L = j + 1, R = num.length - 1;
                while (L < R) {
                    int sum = num[i] + num[j] + num[L] + num[R];
                    if (sum == target) {
                        ans.add(Arrays.asList(num[i], num[j], num[L], num[R]));
                        // while (L < R && num[L] == num[L + 1]) L++; // move one pointer is enough
                        while (L < R && num[R] == num[R - 1]) R--;
                        // L++;
                        R--;
                    } else if (sum < target) L++;
                    else R--;
                }
            }
        }
        return ans;
    }
}
