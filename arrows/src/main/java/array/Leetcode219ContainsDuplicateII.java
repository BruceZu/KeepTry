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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** @see <a href="https://leetcode.com/problems/contains-duplicate-ii/">leetcode</a> */
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
  public boolean containsNearbyDuplicate(int[] nums, int k) {
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
}
