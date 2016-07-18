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

/**
 * @see Leetcode78Subset
 */
public class Leetcode78Subset2 {
    /**
     * running time O(N^2)
     * in loop or recursive
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(Arrays.asList(new Integer[]{}));

        for (int i = 0; i < nums.length; i++) {
            int currentSize = result.size();
            for (int j = 0; j < currentSize; j++) {
                List l = new ArrayList<>(result.get(j));
                l.add(nums[i]);
                result.add(l);
            }
        }
        return result;
    }
}
