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

/*
Suppose you are given an array, A, containing n distinct integers that are listed
in increasing order. Given a number k, describe a recursive algorithm to find two
integers in A that sum to k, if such a pair exists. What is the running time of your
algorithm?
 */


import java.util.Arrays;

// Suppose you are given an array, A, containing n distinct integers that are listed
// in increasing order. Given a number k, describe a recursive algorithm to find two
// integers in A that sum to k, if such a pair exists. What is the running time of your
// algorithm?
public class C522Sum {
    private static boolean find(int[] nums, long k, int[] ids) {
        // walk from both sides towards center.
        // index[0] keep left side index, index[1] keep right side index,
        // runtime O(N)
        int l = ids[0];
        int r = ids[1];
        if (l == r) {
            ids[0] = -1;
            ids[1] = -1;
            return false;
        }
        if (nums[l] + nums[r] == k) {
            ids[0]++;
            ids[1]++;
            return true;
        }
        if (nums[l] + nums[r] < k) {
            ids[0]++;
        } else {
            ids[1]--;
        }
        return find(nums, k, ids);
    }

    public static boolean twoSum(final int[] nums, int target) {
        Arrays.sort(nums); //if the nums is not sorted, then sorted it firstly, thus the running time will be O(NlogN)
        int[] ids = new int[2];
        ids[0] = 0;
        ids[1] = nums.length - 1;
        return find(nums, target, ids);
    }
}
