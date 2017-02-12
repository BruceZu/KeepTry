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

package array.doublepoint;

/**
 * <a href="https://leetcode.com/problems/minimum-size-subarray-sum/">leetcode</a>
 * If you have figured out the O(n) solution,
 * try coding another solution of which the time complexity is O(n log n).
 */
public class Leetcode209MinimumSizeSubarraySum {
    public int minSubArrayLen(int s, int[] nums) {
        int minimalLength = Integer.MAX_VALUE;
        int sum = 0;
        int startIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            while (sum >= s) { // care here is while not if. nums = [2, 3, 1, 9, 4, 3] s=7
                minimalLength = Math.min(minimalLength, i - startIndex + 1);
                sum -= nums[startIndex];
                startIndex++;
            }
        }
        return minimalLength == Integer.MAX_VALUE ? 0 : minimalLength;
    }
}
