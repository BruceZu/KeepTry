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

package array.duplicated;

import java.util.LinkedList;
import java.util.List;

public class DuplicatedNumbers {
    private static int[] toArray(List<Integer> l) {
        int[] r = new int[l.size()];
        for (int i = 0; i < l.size(); i++) {
            r[i] = l.get(i);
        }
        return r;
    }

    /**
     * <pre>
     * Given an array of n elements which contains elements from 0 to n-1,
     * with any of these numbers appearing any number of times.
     * Find these repeating numbers in O(n) and using only constant memory space.
     *
     * For example:
     *      let n be 7 and array be {1, 2, 3, 1, 3, 6, 6},
     *      the answer should be 1, 3 and 6.
     *
     *
     * Idea:
     *  1  All elements > 0; So => value as index, check times.
     *  2  n elements from 0 to n-1.
     *     So => if we can update the array:
     *        21 value as index, check sign.
     *        22 swap;
     *     Runtime O(N)
     *
     *   index   0 1  2 3  4 5 6
     *                             current index
     *   nums:   2 4  6 4  1 0 5      0
     *           6 4  2 4  1 0 5      0
     *           5 4  2 4  1 0 6      0
     *           0 4  2 4  1 5 6      -> 1
     *           0 1  2 4  4 5 6      -> 3,4,5,6
     *                  |
     *                 4 is duplicated and  3 is missing.
     *
     *                                 current index
     *
     *   index   0  1  2  3  4  5  6
     *
     *    nums   1, 2, 3, 1, 3, 6, 6     0
     *           2, 1, 3, 1, 3, 6, 6     0
     *           3, 1, 2, 1, 3, 6, 6     0
     *           1, 1, 2, 3, 3, 6, 6     0
     *           |           |  |         0,1,2,3,4,5,6
     */
    public static int[] duplicatedNums(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return null;
        }
        List<Integer> dups = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            while (true) {
                if (i == nums[i]) {
                    break;
                }
                int vIndex = nums[i];
                if (vIndex == nums[vIndex]) {
                    dups.add(vIndex);
                    break;
                }
                nums[i] ^= nums[vIndex];
                nums[vIndex] ^= nums[i];
                nums[i] ^= nums[vIndex];
            }
        }
        return toArray(dups);
    }

    /**
     * <pre>
     * Note:  Maybe the nums[i] is already marked as negative number.
     *
     *   index   0 1 2 3 4 5 6
     *   nums:   2 4 6 4 1 0 5
     *   mark:  -2-4-6 4-1-0-5
     *                 |
     *                 4 is duplicated and 3 is missing.
     *
     *
     *   index   0  1  2  3  4  5  6
     *    nums   1, 2, 3, 1, 3, 6, 6     0
     *    mark   1,-2,-3,-1, 3, 6,-6
     *                    |  |     |
     */
    public static int[] duplicatedNumsByMarkSign(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return null;
        }
        List<Integer> dups = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            int vIndex = nums[i];
            vIndex = Math.abs(vIndex); // Note
            if (nums[vIndex] < 0) {
                dups.add(vIndex);
                continue;
            }
            nums[vIndex] *= -1;
        }

        return toArray(dups);
    }
}
