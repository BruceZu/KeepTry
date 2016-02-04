//  Copyright 2016 The Minorminor Open Source Project
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

import java.util.ArrayList;
import java.util.List;


// Majority Element II Total
// Given an integer array of size n, find all elements that appear more than⌊ n/3 ⌋ times.
// The algorithm should run in linear time and in O(1) space.

public class Leetcode229MajorityElementII {
    public static List<Integer> MajorityElementOf(int[] nums) {
        int numb1 = 0, numb2 = 0;
        int time1 = 0, time2 = 0;
        for (int i = 0; i < nums.length; i++) {
            int n = nums[i];
            if (time1 == 0) {
                numb1 = n;
                time1++;
                continue;
            }
            if (n == numb1) {
                time1++;
                continue;
            }
            if (time2 == 0) {
                numb2 = n;
                time2++;
                continue;
            }
            if (n == numb2) {
                time2++;
                continue;
            }
            time1--;
            time2--;
        }

        time1 = time2 = 0;
        for (int j = 0; j < nums.length; j++) {
            int n = nums[j];
            if (n == numb1) {
                time1++;
                continue;
            }
            if (n == numb2) {
                time2++;
            }
        }

        List result = new ArrayList(2);
        if (time1 > nums.length / 3) {
            result.add(numb1);
        }
        if (time2 > nums.length / 3) {
            result.add(numb2);
        }
        return result;
    }
}
