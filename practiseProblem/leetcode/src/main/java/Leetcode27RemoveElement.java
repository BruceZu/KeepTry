//  Copyright 2016 The Minorminor Open Source Project
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

public class Leetcode27RemoveElement {
    // Leetcode 27 Remove Element
    // runtime beats 73.63% of java submissions.

    // Given an array and a value, remove all instances of that value in place and return the new length.
    // The order of elements can be changed. It doesn't matter what you leave beyond the new length.
    public static int removeElement(int[] nums, int val) {
        int newIndex = 0;
        int i = 0;
        while (i < nums.length) {
            int current = nums[i];
            if (current != val) {
                nums[newIndex++] = nums[i];
            }
            i++;
        }
        return newIndex;
    }
}
