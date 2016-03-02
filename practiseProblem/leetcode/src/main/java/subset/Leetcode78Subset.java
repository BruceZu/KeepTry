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

package subset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Given a set of distinct integers, nums, return all possible subsets.

Note:
Elements in a subset must be in non-descending order.
The solution set must not contain duplicate subsets.
For example,
If nums = [3,2,1], a solution is:

[
  [3],
  [1],
  [2],
  [1,2,3],
  [1,3],
  [2,3],
  [1,2],
  []
]
 */
public class Leetcode78Subset {
    // List all subset of n unique elements.
    // running time O(?) beats 60.29% of java submissions on Feb 3, 2016
    private static void subsets(int[] nums, List<List<Integer>> result, ArrayList<Integer> current, int indexFrom) {
        for (int index = indexFrom; index < nums.length; index++) {
            current.add(nums[index]);
            result.add((ArrayList<Integer>) current.clone());
            if (index + 1 < nums.length) {
                subsets(nums, result, current, index + 1);
            }
            current.remove(current.size() - 1);
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        Arrays.sort(nums);
        ArrayList<List<Integer>> result = new ArrayList<List<Integer>>();
        result.add(new ArrayList<Integer>());
        if (nums.length <= 64) {
            // if (nums.length <= 64) using bit manipulation with runtime O(N*2^N)
            int n = nums.length;
            for (long i = 1; i < Math.pow(2, n); i++) {
                long bit = i;
                List subset = new ArrayList<Integer>();
                int index = 0;
                do {
                    if ((bit & 1) == 1) {
                        subset.add(nums[index]);
                    }
                    index++;
                    bit >>= 1;

                } while (bit != 0);
                result.add(subset);
            }
            return result;
        }
        subsets(nums, result, new ArrayList<Integer>(), 0);
        return result;
    }

    // running time O(?) beats 60.29% of java submissions on Feb 3, 2016
    private static void subsets2(int[] nums, ArrayList<List<Integer>> result, int from) {
        final int current = nums[from];
        ArrayList<List<Integer>> newSets = new ArrayList();
        for (List<Integer> i : result) {
            i = (List<Integer>) ((ArrayList) i).clone();
            i.add(current);
            newSets.add(i);
        }
        result.addAll(newSets);
        if (from + 1 < nums.length) {
            subsets2(nums, result, from + 1);
        }
    }

    public List<List<Integer>> subsets2(int[] nums) {
        Arrays.sort(nums);
        ArrayList<List<Integer>> result = new ArrayList<List<Integer>>();
        result.add(new ArrayList<Integer>());
        if (nums.length <= 64) {
            // if (nums.length <= 64) using bit manipulation with runtime O(N*2^N)?
            int n = nums.length;
            for (long i = 1; i < Math.pow(2, n); i++) {
                long bit = i;
                List subset = new ArrayList<Integer>();
                int index = 0;
                do {
                    if ((bit & 1) == 1) {
                        subset.add(nums[index]);
                    }
                    index++;
                    bit >>= 1;

                } while (bit != 0);
                result.add(subset);
            }
            return result;
        }
        subsets2(nums, result, 0);
        return result;
    }
}
