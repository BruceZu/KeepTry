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

package binarysearch;

import java.util.Arrays;

public class Leetcode493ReversePairs5 {
    // BIT
    // loop, start from left, find >= 2y+1 directly
    // O(nlogn)
    public int reversePairs(int[] nums) {
        int[] BIT = new int[nums.length + 1];

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int result = 0;
        for (int num : nums) {
            result += countEqualGreatThanByCovers(BIT, indexIn(sorted, 2L * num + 1));
            markCovers(BIT, indexIn(sorted, num));
        }

        return result;
    }

    private int indexIn(int[] sorted, long v) {
        int l = 0, r = sorted.length - 1, m;
        while (l <= r) {
            m = (r + l) >> 1;
            if (sorted[m] > v) {
                r = m - 1;
            } else if (sorted[m] == v) {
                return m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

    // search those >= v
    private int countEqualGreatThanByCovers(int[] BIT, int index) {
        int idx = index + 1;
        int sum = 0;
        while (idx < BIT.length) {
            sum += BIT[idx];
            idx += idx & -idx;
        }
        return sum;
    }

    private void markCovers(int[] BIT, int index) {
        int idx = index + 1;
        while (idx > 0) {
            BIT[idx] += 1;
            idx -= idx & -idx;
        }
    }
}
