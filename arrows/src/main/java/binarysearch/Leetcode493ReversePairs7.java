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

public class Leetcode493ReversePairs7 {
    // BIT
    // loop, start from right, find <= (y-1)/2
    // Note need care binary serch index method:  key not found.
    static public int reversePairs(int[] nums) {
        int[] BIT = new int[nums.length + 1];

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int result = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            int targetIndex = Arrays.binarySearch(sorted, (int) Math.floor((nums[i] - 1) * 0.5d));
            if (targetIndex < 0) {
                targetIndex = ~targetIndex - 1;
            }
            result += sum(BIT, targetIndex);

            mark(BIT, Arrays.binarySearch(sorted, nums[i]));
        }

        return result;
    }

    static private void mark(int[] BIT, int index) {
        int idx = index + 1;
        while (idx < BIT.length) {
            BIT[idx]++;
            idx += idx & -idx;
        }
    }

    static private int sum(int[] BIT, int index) {
        int idx = index + 1;
        int sum = 0;
        while (idx > 0) {
            sum += BIT[idx];
            idx -= idx & -idx;
        }
        return sum;
    }

    // ---------------------------------------------------------
    public static void main(String[] args) {
        System.out.println(reversePairs(new int[]{1, 3, 2, 3, 1})); // 2
        System.out.println(reversePairs(new int[]{7, 1, 3, 2, 3, 1})); // 7
        System.out.println(reversePairs(new int[]{1, 0, 25, 0})); // 3
    }
}
