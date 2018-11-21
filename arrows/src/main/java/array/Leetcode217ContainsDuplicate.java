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
import java.util.Map;

/** @see <a href="https://leetcode.com/problems/contains-duplicate/">leetcode</a> */
public class Leetcode217ContainsDuplicate {
  // it can be negative number
  public boolean containsDuplicate1(int[] nums) {
    Map<Integer, Integer> times = new HashMap();
    for (int i = 0; i < nums.length; i++) {
      if (times.get(nums[i]) != null) {
        return true;
      }
      times.put(nums[i], 1);
    }
    return false;
  }
}
