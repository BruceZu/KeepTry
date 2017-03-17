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

public class Leetcode315CountofSmallerNumbersAfterSelf2 {
    //  Merge sort with tracking of those right-to-left jumps
    static class V_I {
        int v;
        int idx;

        V_I(int number, int index) {
            this.v = number;
            this.idx = index;
        }
    }

    static public List<Integer> countSmaller(int[] nums) {
        V_I[] array = new V_I[nums.length];
        for (int i = 0; i < nums.length; i++) {
            array[i] = new V_I(nums[i], i);
        }
        int[] smallNumThanElementAt = new int[nums.length];
        mergeSortWithTrackingSmaller(array, smallNumThanElementAt);
        List<Integer> res = new ArrayList<>();
        for (int i : smallNumThanElementAt) {
            res.add(i);
        }
        return res;
    }

    // O(nlogn)
    static private V_I[] mergeSortWithTrackingSmaller(V_I[] sortingArray, int[] smallNumThanElementAt) {
        int half = sortingArray.length / 2;
        if (half > 0) {// divide until cannot divide any more when one 1 element.

            V_I[] ofL = new V_I[half];
            for (int i = 0; i < ofL.length; i++) {
                ofL[i] = sortingArray[i];
            }

            V_I[] ofR = new V_I[sortingArray.length - half];
            for (int i = 0; i < ofR.length; i++) {
                ofR[i] = sortingArray[half + i];
            }

            ofL = mergeSortWithTrackingSmaller(ofL, smallNumThanElementAt);
            ofR = mergeSortWithTrackingSmaller(ofR, smallNumThanElementAt);

            int l = 0, ri = 0;
            while (l < ofL.length || ri < ofR.length) {
                if (ri == ofR.length || l < ofL.length && ofL[l].v <= ofR[ri].v) {
                    sortingArray[l + ri] = ofL[l];
                    smallNumThanElementAt[ofL[l].idx] += ri; //ri is just those right-to-left jumps.
                    l++;
                } else {
                    sortingArray[l + ri] = ofR[ri];
                    ri++;
                }
            }
        }
        return sortingArray;
    }
}
