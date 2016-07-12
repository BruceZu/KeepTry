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

package locked.nosubmmitted;

import java.util.ArrayList;
import java.util.List;

/**
 * 163. Missing Ranges
 * https://leetcode.com/problems/missing-ranges/
 * <pre>
 *     Difficulty: Medium
 * Given a sorted integer array where the range of elements are [lower, upper] inclusive, return its missing ranges.
 *
 * For example, given [0, 1, 3, 50, 75], lower = 0 and upper = 99, return ["2", "4->49", "51->74", "76->99"].
 *
 * Hide Company Tags Google
 * Hide Tags: Array
 * Hide Similar Problems (M) Summary Ranges
 *
 * </pre>
 */
public class LC163MissingRanges {
    /**
     * the fast one beat 99.9%
     */


    public class Solution {
        /**
         * beat 12.23%
         *
         * @param nums
         * @param lower
         * @param upper
         * @return
         */
        public List<String> findMissingRanges(int[] nums, int lower, int upper) {
            List<String> res = new ArrayList<>();
            int prev, cur;
            for (int i = 0; i <= nums.length; i++) {
                cur = i == nums.length ? upper + 1 : nums[i];
                prev = i > 0 ? nums[i - 1] : lower - 1;

                if (cur > prev + 1) {
                    res.add(getRange(prev, cur));
                }

                prev = cur;
            }

            return res;
        }

        private String getRange(int prev, int cur) {
            if (cur - prev == 2) {
                return String.valueOf(cur - 1);
            } else {
                return String.valueOf(prev + 1) + "->" + String.valueOf(cur - 1);
            }
        }

        /**
         * same as above
         */
        public List<String> findMissingRanges2(int[] nums, int lower, int upper) {
            List<String> list = new ArrayList<String>();
            for (int n : nums) {
                int justBelow = n - 1;
                if (lower == justBelow) list.add(lower + "");
                else if (lower < justBelow) list.add(lower + "->" + justBelow);
                lower = n + 1;
            }
            if (lower == upper) list.add(lower + "");
            else if (lower < upper) list.add(lower + "->" + upper);
            return list;
        }

        /**
         * another case
         */
        //if both start and end are same, then we just need one entry, else get the range
        public String getRangeString(int start, int end) {
            return (start == end) ? String.valueOf(start) : (start + "->" + end);
        }

        public List<String> findMissingRanges3(int[] nums, int lower, int upper) {
            List<String> list = new ArrayList<>();
            //if input array is empty, then just generate the range between lower and upper
            if (nums.length == 0) {
                list.add(getRangeString(lower, upper));
                return list;
            }

            //if the first element and the lower vary by more than 0, then it means that the input array starts
            //atleast by +1 of lower, so we need to at the minimum add the lower as an entry for output. So we
            //generate the range of lower, first_element-1
            if (nums[0] - lower > 0) {
                list.add(getRangeString(lower, nums[0] - 1));
                //set the lower as first element
                lower = nums[0];
            }

            //now just loop over the array finding the range if the difference is >=2. If the difference is just 1 then it
            //means that the two elements are contiguous, so no need to worry. If its 2 then it means we atleast miss one element,
            //so we find the range of lower+1, current_element-1
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] - lower >= 2) {
                    list.add(getRangeString(lower + 1, nums[i] - 1));
                }
                lower = nums[i];
            }

            //for the last element, we need to get the range between last_element+1 and the upper
            if (lower + 1 <= upper) {
                list.add(getRangeString(lower + 1, upper));
            }

            //return the list
            return list;
        }

    }
}
