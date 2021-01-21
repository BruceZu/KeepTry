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

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

/**
 * Next Permutation see {@link Leetcode31NextPermutation}
 * <p>
 * The way of factorials here is like: permutations number of [112] = 3!/2! = (1*2*3/ 1*2*1)
 * factorials see {@link math.factorial.Factorials}
 *
 * @see <a href="http://advancedmathtutoring.com/permutations-with-and-without-repetition/">Permutations With and Without Repetition</a>
 * @see <a href="https://discuss.leetcode.com/topic/50864/who-have-the-fast-resolution/8">leetcode discuss</a>
 */
public class Leetcode47PermutationsII4 extends AbstractList<List<Integer>> {
    private int[] nums;
    private int permutationsSize;
    private List<Integer> currentP;

    public List<List<Integer>> permuteUnique(int[] nums) {
        this.nums = nums;
        Arrays.sort(nums);

        permutationsSize = 1;
        int i = 1, div = 1, prev = 0;
        for (int num : nums) {
            if (div > 1 && num != prev) {
                div = 1;
            }
            permutationsSize = permutationsSize * i / div;
            i++;
            div++;
            prev = num;
        }

        return this;
    }

    public int size() {
        return permutationsSize;
    }

    public List get(int index) {
        class IntList extends AbstractList {
            private int[] array;

            public IntList(int[] array) {
                this.array = array;
            }

            public Integer get(int index) {
                return array[index];
            }

            public int size() {
                return array.length;
            }
        }
        if (currentP == null) {
            return currentP = new IntList(nums);
        }
        //Next Permutation
        int prePeak = nums.length - 2;
        while (prePeak >= 0 && nums[prePeak] >= nums[prePeak + 1]) {
            prePeak--;
        }
        int j = nums.length - 1;
        while (nums[j] <= nums[prePeak]) {
            j--;
        }
        int tmp = nums[prePeak];
        nums[prePeak] = nums[j];
        nums[j] = tmp;

        int i = prePeak + 1;
        j = nums.length - 1;
        while (i < j) {
            tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;

            i++;
            j--;
        }
        return currentP;
    }
}
