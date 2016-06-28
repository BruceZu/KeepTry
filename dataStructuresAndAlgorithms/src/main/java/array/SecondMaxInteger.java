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

public class SecondMaxInteger {

    /**
     * Running timeO(N)
     * <p>
     * Return -1 if the second max does not exists.
     */
    public static int secondMax(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return -1;
        }
        int max = nums[0] < nums[1] ? nums[1] : nums[0];
        int second = max == nums[0] ? nums[1] : nums[0];
        for (int i = 2; i < nums.length; i++) {
            int v = nums[i];
            if (v > max) {
                second = max;
                max = v;
            } else if (v > second) { // note else if, not if only
                second = v;
            }
        }
        return second;
    }
}
