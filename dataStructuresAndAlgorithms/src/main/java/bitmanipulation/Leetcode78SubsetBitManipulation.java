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

package bitmanipulation;


import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * if (nums.length <= 64) using bit manipulation with runtime O(N*(2^(N+1)-1))
 *
 *           1 2 3 4 5
 *     index 0 1 2 3 4
 *
 *           0 0 0 0 0  empty set
 *           0 0 0 0 1
 *           0 0 0 1 0
 *           0 0 0 1 1
 *            ......
 *           1 1 1 1 1
 *           @see  subset.Leetcode78Subset
 */
public class Leetcode78SubsetBitManipulation {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> r = new ArrayList<>();
        r.add(new ArrayList<Integer>());
        long max =  (long)Math.pow(2, nums.length) - 1;
        for (long i = 1; i <= max; i++) {
            long s = i; // solution of a subset
            List<Integer> aset = new ArrayList(64);
            int index = 0;

            do {
                if ((s & 1) == 1) {
                    aset.add(nums[index]);
                }

                s >>>= 1;
                index++;
            } while (s != 0);
            r.add(aset);
        }
        return r;
    }
}
