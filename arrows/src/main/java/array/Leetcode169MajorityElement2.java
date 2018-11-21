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

/** other ideas: {@link bitmanipulation.Leetcode169MajorityElement#majorityElement(int[])} */
public class Leetcode169MajorityElement2 {
  public int majorityElement(int[] nums) {
    int vote = 1;
    int candidator = nums[0];
    for (int i = 1; i < nums.length; i++) {
      if (nums[i] == candidator) {
        vote++;
        continue;
      }
      if (vote > 0) {
        vote--;
        continue;
      }
      vote = 1;
      candidator = nums[i];
    }
    return candidator;
  }
}
