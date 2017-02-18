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

package two_pointer;

import sun.rmi.server.InactiveGroupException;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * <a href="https://leetcode.com/problems/3sum-closest/">Leetcode</a>
 * <a href="http://stackoverflow.com/questions/18565485/why-is-absolute-of-integer-min-value-equivalent-to-integer-min-value">
 * absolute-of-integer-min-value-equivalent-to-integer-min-value
 * </a>
 */
public class Leetcode16_3SumClosest {

    /**
     * assume that each input would have exactly one solution.
     * 3SUM -> 2 SUM  2 pointer
     * O(N*N)
     * improvement: check both said.
     */
    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int result = 0;
        int abc = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length - 2; i++) {// i scope

            for (int j = i + 1, k = nums.length - 1; j < k; ) {
                int curSum = nums[i] + nums[j] + nums[k];
                if (curSum == target) {
                    return target;
                } else if (curSum > target) {
                    k--;
                    if (curSum - target < abc) {
                        abc = curSum - target;
                        result = curSum;
                    }
                } else {
                    j++;
                    if (target - curSum < abc) {
                        abc = target - curSum;
                        result = curSum;
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        //System.out.println(threeSumClosest(new int[]{0, 0, 0}, 1));
        System.out.println(threeSumClosest(new int[]{0, 1, 2}, 0));
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));
        System.out.println("0" + Integer.toBinaryString(Integer.MAX_VALUE));
    }
}
