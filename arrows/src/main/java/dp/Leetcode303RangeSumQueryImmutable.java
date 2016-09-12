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

package dp;

/**
 * @see <a href="https://leetcode.com/problems/range-sum-query-immutable/">leetcode</a>
 */
public class Leetcode303RangeSumQueryImmutable {
    //  Time Limit Exceeded
    class NumArray {
        private int[][] r;

        public NumArray(int[] nums) {
            r = new int[nums.length][nums.length];
            for (int i = 0; i < nums.length; i++) {
                for (int j = i; j < nums.length; j++) {
                    if (i == j) {
                        r[i][j] = nums[i];
                    } else {
                        r[i][j] = nums[j] + r[i][j - 1];
                    }
                }
            }
        }

        public int sumRange(int i, int j) {
            return r[i][j];
        }
    }

    // Time Limit Exceeded
    class NumArray2 {
        private int[][] cache;
        private int[] data;

        public NumArray2(int[] nums) {
            cache = new int[nums.length][nums.length];
            data = nums;
        }

        public int sumRange(int i, int j) {
            if (cache[i][j] != 0) {
                return cache[i][j];
            }
            if (j == i) {
                return data[i];
            }
            cache[i][j] = data[j] + sumRange(i, j - 1);
            return cache[i][j];
        }
    }
}
