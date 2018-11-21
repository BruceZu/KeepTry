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

package array; /*
               Given a collection of integers that might contain duplicates, nums, return all possible subsets.

               Note:
               Elements in a subset must be in non-descending order.
               The solution set must not contain duplicate subsets.
               For example,
               If nums = [1,2,2], a solution is:

               [
                 [2],
                 [1],
                 [1,2,2],
                 [2,2],
                 [1,2],
                 []
               ]
                */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leetcode90SubsetsII {

  // O(?)
  public static List<List<Integer>> subsetsWithDup(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> results = new ArrayList<>();

    int size = 0; // size prior to adding
    results.add(new ArrayList<>()); // empty

    for (int i = 0; i < nums.length; i++) {
      int curToAdd = nums[i];
      int from = i > 0 && curToAdd != nums[i - 1] ? 0 : size;

      size = results.size();
      for (; from < size; from++) {
        List<Integer> list = new ArrayList(results.get(from));
        list.add(nums[i]);
        results.add(list);
      }
    }

    return results;
  }

  public static void main(String[] args) {
    subsetsWithDup(new int[] {1, 2, 2});
  }
}
