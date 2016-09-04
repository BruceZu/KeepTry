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

import java.util.BitSet;

public class Leetcode217ContainsDuplicate2 {

    // can not work
    public static boolean containsDuplicate2(int[] nums) {
        long[] positive = new long[Integer.MAX_VALUE];//java.lang.OutOfMemoryError: Requested array size exceeds VM limit
        int zeroNumber = 0;
        long[] negative = new long[Integer.MAX_VALUE];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                if (positive[nums[i]] != 0) {
                    return true;
                }
                positive[nums[i]] = 1;
            } else if (nums[i] == 0) {
                if (zeroNumber != 0) {
                    return true;
                }
                zeroNumber++;
            } else {
                if (negative[-nums[i]] != 0) {
                    return true;
                }
                negative[-nums[i]] = 1;
            }
        }
        return false;
    }

    // cheating the leetcode this is same idea as above. so it can not work when leetcode have enough test case
    public static boolean containsDuplicate3(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return false;
        }
        int max = nums[0], min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            max = Math.max(max, nums[i]);
            min = Math.min(min, nums[i]);
        }

        int[] times = new int[max - min + 1];
        int adjust = -min;

        for (int i = 0; i < nums.length; i++) {
            if (times[nums[i] + adjust] != 0) {
                return true;
            }
            times[nums[i] + adjust] = 1;
        }
        return false;
    }

    // replace array with BitSet, still can not work, just cheating leetcode.
    // [-2147483648, 2147483647,-2147483648, 2147483647] java.lang.IndexOutOfBoundsException: bitIndex < 0: -1
    // [-2147483648, 2147483647]
    public static boolean containsDuplicate(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return false;
        }
        int max = nums[0], min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            max = Math.max(max, nums[i]);
            min = Math.min(min, nums[i]);
        }
        BitSet times = new BitSet(max - min + 1);
        int adjust = -min;
        for (int i = 0; i < nums.length; i++) {
            if (times.get(nums[i] + adjust)) {// public boolean get(int bitIndex) ; It is int type, and  Integer.MAX_VALUE-Integer.MIN_VALUE will be -1
                return true;
            } else {
                times.set(nums[i] + adjust);
            }
        }
        return false;
    }
}
