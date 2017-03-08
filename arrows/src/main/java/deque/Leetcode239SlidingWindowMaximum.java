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

package deque;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * <pre>
 *  You may assume k is always valid, ie: 1 ≤ k ≤ input array's size for non-empty array.
 *
 *     Follow up:
 *      Could you solve it in linear time?
 *
 *          Hint:
 *
 *          How about using a data structure such as deque (double-ended queue)?
 *          The queue size need not be the same as the window’s size.
 *          Remove redundant elements and the queue should store only elements that need to be considered.
 *
 * Tag:  Heap (Priority Queue)
 *  =============================================================================
 *     keep index of element. skill 1
 *     before add current element's index do:
 *      step 1: valid index scope check, left side. skill 2
 *      step 2: make sure descending order in this question, each member in deque make sure all elements before that is
 *      bigger than itself, skill 3
 *
 *
 *     Note: return array 'r' with the value not index.
 *
 * @see <a href="https://leetcode.com/problems/sliding-window-maximum/">leetcode</a>
 */
public class Leetcode239SlidingWindowMaximum {
    /**
     * O(NK)
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        // corner case check
        if (nums == null || nums.length == 0) {
            return nums;
        }
        int[] r = new int[nums.length - k + 1];
        Deque<Integer> descenIndex = new ArrayDeque(k);
        for (int i = 0; i < nums.length; i++) {
            int indexNeedToMoveFromWindow = i - k;
            if (!descenIndex.isEmpty() &&
                    descenIndex.peek() == indexNeedToMoveFromWindow) {
                descenIndex.poll();// may be it has been move out of the window already
            }

            while (!descenIndex.isEmpty() &&
                    nums[descenIndex.peekLast()] < nums[i]) {// O(K)
                descenIndex.pollLast();
            }
            descenIndex.offerLast(i); // keep index
            if (i >= k - 1) {
                r[i - (k - 1)] = nums[descenIndex.peek()]; // value
            }
        }
        return r;
    }

    public static int[] minSlidingWindow(int[] nums, int k) {
        // corner case check
        if (nums == null || nums.length == 0) {
            return nums;
        }
        int[] r = new int[nums.length - k + 1];
        Deque<Integer> indexOfWindowAscendingOrder = new ArrayDeque(k);
        for (int i = 0; i < nums.length; i++) {
            if (!indexOfWindowAscendingOrder.isEmpty() && indexOfWindowAscendingOrder.peek() < i - (k - 1)) {
                indexOfWindowAscendingOrder.poll();
            }

            while (!indexOfWindowAscendingOrder.isEmpty() && nums[indexOfWindowAscendingOrder.peekLast()] > nums[i]) { // only update here
                indexOfWindowAscendingOrder.pollLast();
            }
            indexOfWindowAscendingOrder.offerLast(i); // keep index
            if (i >= k - 1) {
                r[i - (k - 1)] = nums[indexOfWindowAscendingOrder.peek()]; // value
            }
        }
        return r;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(minSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 8)));
    }
}

