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

import java.util.Arrays;

public class Leetcode219ContainsDuplicateII2 {
    // sort the nums firstly, then check, it need some way to keep the origin index.
    //
    //    1 (5 ms) using quick sort, and a array to keep the origin index of element and update in sorting
    // or
    //    2 (7 ms) a class to bind the origin index with the element and implements Comparable.
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        class BindIndex implements Comparable {
            int v;
            int index;

            @Override
            public int compareTo(Object o) {
                BindIndex o2 = (BindIndex) o;
                return this.v != o2.v ? this.v - o2.v : this.index - o2.index;
            }
        }

        int len = nums.length;
        BindIndex[] b = new BindIndex[len];
        for (int i = 0; i < len; ++i) {
            b[i] = new BindIndex();
            b[i].v = nums[i];
            b[i].index = i;
        }
        Arrays.sort(b, 0, len);
        for (int i = 0; i < len - 1; i++) {
            if (b[i].v == b[i + 1].v && b[i + 1].index - b[i].index <= k) {
                return true;
            }
        }
        return false;
    }
}
