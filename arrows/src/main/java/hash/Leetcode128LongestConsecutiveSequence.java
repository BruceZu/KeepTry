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

package hash;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * <a href="https://leetcode.com/problems/longest-consecutive-sequence/">LeetCode</a>
 */
public class Leetcode128LongestConsecutiveSequence {
    // get the number of longest sequence in the give array
    //
    // if a given num is in a sequence, sure it will 搭边.and 搭 only the 边
    // let 边 keep the length the current sequence's length
    public static int longestConsecutive(int[] nums) {
        int result = 0;
        // map sequence border number to sequence length
        HashMap<Integer, Integer> lengthOf = new HashMap<Integer, Integer>();
        for (int num : nums) {
            if (!lengthOf.containsKey(num)) {
                int leftBorder = num - 1, rightBoarder = num + 1;
                int l = lengthOf.containsKey(leftBorder) ? lengthOf.get(leftBorder) : 0;
                int r = lengthOf.containsKey(rightBoarder) ? lengthOf.get(rightBoarder) : 0;

                int currentSequenceLength = l + 1 + r;
                lengthOf.put(num, currentSequenceLength);
                lengthOf.put(num - l, currentSequenceLength);
                lengthOf.put(num + r, currentSequenceLength);

                result = Math.max(result, currentSequenceLength);
            } else {
                // duplicates
                continue;
            }
        }
        return result;
    }

    // ---------------------------------------------
    public static void main(String[] args) {
        System.out.println(longestConsecutive(new int[]{1, 6, 4, 2, 5, 3}));
    }

    public int longestConsecutive2(int[] nums) {
        Set<Integer> set = new HashSet<Integer>();
        for (int num : nums) {
            set.add(num);
        }

        int result = 0;
        for (int n : nums) {
            int currentSequenceLength = 0;
            if (set.isEmpty()) {
                break;
            }

            int nValue = n;
            while (set.remove(nValue--)) {
                currentSequenceLength++;
            }

            nValue = n;
            while (set.remove(++nValue)) {
                currentSequenceLength++;
            }

            result = Math.max(currentSequenceLength, result);
        }

        return result;
    }
}

