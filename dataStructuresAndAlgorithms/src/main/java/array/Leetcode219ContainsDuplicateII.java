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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @see <a  href="https://leetcode.com/problems/contains-duplicate-ii/">leetcode</a>
 */
public class Leetcode219ContainsDuplicateII {
    // 11ms
    public boolean containsNearbyDuplicate2(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            Integer indices = map.get(nums[i]);
            if (indices != null && i - indices <= k) {
                return true;
            }
            map.put(nums[i], i);

        }
        return false;
    }

    // 9ms
    public boolean containsNearbyDuplicate3(int[] nums, int k) {
        Set<Integer> kes = new HashSet<Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (i > k) {
                kes.remove(nums[i - k - 1]);
            }
            if (!kes.add(nums[i])) {
                return true;
            }
        }
        return false;
    }

    // sort the nums firstly, then check, it need some way to keep the origin index.
    //
    //    1 (5 ms) using quick sort, and a array to keep the origin index of element and update in sorting
    // or
    //    2 (7 ms) a class to bind the origin index with the element and implements Comparable.
    public boolean containsNearbyDuplicate4(int[] nums, int k) {
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

    // 2 ms , the fast one, depends on the leetcode test data.
    // find out all duplicated elements then check each one;
    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        ArrayList<Integer> dups = new ArrayList<>();
        for (int s = 0; s < sorted.length - 1; s++) {
            if (sorted[s] == sorted[s + 1] && !dups.contains(sorted[s])) {
                dups.add(sorted[s]);
            }
        }

        for (int v : dups) {
            int pre = -1;
            for (int i = 0; i < nums.length; i++) { // find the fist one of current duplicated elements
                if (nums[i] == v) {
                    pre = i;
                    break;
                }
            }
            for (int i = pre + 1; i < nums.length; i++) {
                if (nums[i] == v) {
                    if (i - pre <= k) {
                        return true;
                    }
                    pre = i;
                }
            }
        }
        return false;
    }
}
