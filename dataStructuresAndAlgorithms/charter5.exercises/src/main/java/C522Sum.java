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
C-5.22 Suppose you are given an array, A, containing n distinct integers that are listed
in increasing order. Given a number k, describe a recursive algorithm to find two
integers in A that sum to k, if such a pair exists. What is the running time of your
algorithm?
 */


public class C522Sum {
    private static void find(int[] indexes, int[] nums, long k) {
        // walk from both sides towards center.
        // index[0] keep left side index, index[1] keep right side index,
        // runtime O(N)
        int l = indexes[0];
        int r = indexes[1];
        if (l == r) {
            indexes[0] = -1;
            indexes[1] = -1;
            return;
        }
        if (nums[l] + nums[r] == k) {
            return;

        }
        if (nums[l] + nums[r] < k) {
            indexes[0]++;
            find(indexes, nums, k);
            return;
        }
        indexes[1]--;
        find(indexes, nums, k);
    }

    public static int[] find(int[] nums, int k) {
        int[] r = new int[2];
        r[0] = 0;
        r[1] = nums.length - 1;
        find(r, nums, k);
        return r;
    }
}
