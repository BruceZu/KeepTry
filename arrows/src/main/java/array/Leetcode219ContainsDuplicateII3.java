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

import java.util.ArrayList;
import java.util.Arrays;

public class Leetcode219ContainsDuplicateII3 {

    // 2 ms , the fast one, depends on the leetcode test data.
    // find out all duplicated elements then check each one;
    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        ArrayList<Integer> dups = new ArrayList<>();
        for (int s = 0; s < sorted.length - 1; s++) {
            if (sorted[s] == sorted[s + 1] && !dups.contains(sorted[s])) {
                dups.add(sorted[s]);
            }
        }

        for (int v : dups) {
            int pre = -1;
            for (int i = 0; i < nums.length; i++) { // find the fist one of current duplicated elements
                if (nums[i] == v) {
                    pre = i;
                    break;
                }
            }
            for (int i = pre + 1; i < nums.length; i++) {
                if (nums[i] == v) {
                    if (i - pre <= k) {
                        return true;
                    }
                    pre = i;
                }
            }
        }
        return false;
    }
}
