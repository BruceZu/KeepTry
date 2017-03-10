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

import java.util.ArrayList;
import java.util.List;

public class Leetcode315CountofSmallerNumbersAfterSelf3 {
    // Merge sort with tracking of those right-to-left jumps
    // instead of sort the number in array, sort the indexes of each number.
    // O(nlogn)
    static public List<Integer> countSmaller(int[] nums) {
        int[] idxes = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            idxes[i] = i;
        }
        int[] smallNumThanElementAt = new int[nums.length];
        mergesort(nums, idxes, smallNumThanElementAt);
        List<Integer> res = new ArrayList<>();
        for (int i : smallNumThanElementAt) {
            res.add(i);
        }
        return res;
    }

    static private int[] mergesort(int[] nums, int[] idxes, int[] smallNumThanElementAt) {
        int half = idxes.length / 2;
        if (half > 0) {// divide until to 1 element

            int[] ofL = new int[half];
            for (int i = 0; i < ofL.length; i++) {
                ofL[i] = idxes[i];
            }

            int[] ofR = new int[idxes.length - half];
            for (int i = 0; i < ofR.length; i++) {
                ofR[i] = idxes[half + i];
            }

            ofL = mergesort(nums, ofL, smallNumThanElementAt);
            ofR = mergesort(nums, ofR, smallNumThanElementAt);

            int l = 0, ri = 0;
            while (l < ofL.length || ri < ofR.length) {
                if (ri == ofR.length || l < ofL.length && nums[ofL[l]] <= nums[ofR[ri]]) {
                    idxes[l + ri] = ofL[l];
                    smallNumThanElementAt[ofL[l]] += ri; //ri is just those right-to-left jumps.
                    l++;
                } else {
                    idxes[l + ri] = ofR[ri];
                    ri++;
                }
            }
        }
        return idxes;
    }
}
