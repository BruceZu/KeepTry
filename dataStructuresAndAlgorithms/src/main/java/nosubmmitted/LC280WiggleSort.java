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

package nosubmmitted;

/**
 * 280. Wiggle Sort
 * https://leetcode.com/problems/wiggle-sort/
 * Difficulty: Medium <pre>
 * Given an unsorted array nums, reorder it in-place such that nums[0] <= nums[1] >= nums[2] <= nums[3]....
 * <p/>
 * For example, given nums = [3, 5, 2, 1, 6, 4], one possible answer is [1, 6, 2, 5, 3, 4].
 * <p/>
 * Hide Company Tags Google
 * Hide Tags Array Sort
 * Hide Similar Problems (M) Sort Colors (M) Wiggle Sort II
 */
public class LC280WiggleSort {

    /**
     * beat 71.74 <pre>
     * I have to say nobody explains the sufficiency of the following algo:
     * <p/>
     * The final sorted nums needs to satisfy two conditions:
     * If i is odd, then nums[i] >= nums[i - 1];
     * If i is even, then nums[i] <= nums[i - 1].
     * <p/>
     * The code is just to fix the orderings of nums that do not satisfy 1 and 2.
     * (from https://leetcode.com/discuss/57120/4-lines-o-n-c)
     * <p/>
     * <p/>
     * <p/>
     * <p/>
     * why is this greedy solution can ensure previous sequences and coming sequences W.R.T position i wiggled?
     * <p/>
     * My explanation is recursive,
     * <p/>
     * suppose nums[0 .. i - 1] is wiggled, for position i:
     * <p/>
     * if i is odd, we already have, nums[i - 2] >= nums[i - 1],
     * <p/>
     * if nums[i - 1] <= nums[i], then we does not need to do anything, its already wiggled.
     * <p/>
     * if nums[i - 1] > nums[i], then we swap element at i -1 and i.
     * Due to previous wiggled elements (nums[i - 2] >= nums[i - 1]),
     * we know after swap the sequence is ensured to be nums[i - 2] > nums[i - 1] < nums[i], which is wiggled.
     * <p/>
     * similarly,
     * <p/>
     * if i is even, we already have, nums[i - 2] <= nums[i - 1],
     * <p/>
     * if nums[i - 1] >= nums[i], pass
     * <p/>
     * if nums[i - 1] < nums[i], after swap, we are sure to have wiggled nums[i - 2] < nums[i - 1] > nums[i].
     * <p/>
     * The same recursive solution applies to all the elements in the sequence, ensuring the algo success.
     *
     * @param nums
     */
    public void wiggleSort(int[] nums) {
        for (int i = 1; i < nums.length; i++)
            if (((i & 1) == 0) == (nums[i - 1] < nums[i])) xwap(nums, i);
    }

    private void xwap(int[] a, int i) {
        int t = a[i];
        a[i] = a[i - 1];
        a[i - 1] = t;
    }


    /**
     * same fast as above  <pre>
     * This questions initially seems a lot more daunting than it actually is. At first I kept
     * trying to find a median then do a quicksort pass then swap the end of the list with the
     * beginning of the list. This all was too much work for what the question requires.
     * The insight came as I wrote out a few examples and convinced myself that a more greedy
     * approach would work. To give a more concise example, lets consider the following input:
     * [1, 2, 3, 4, 5, 6]
     * <p/>
     * We now want to think about how to attack this problem. One good way to think about it
     * is to consider the list from 1...(i -1) properly sorted and then you only need to worry
     * about i...n. Thus if we have reached index i, we never have to worry about the previous
     * portion of the list beyond i - 1. Lets try a couple of quick passes with the input above.
     * <p/>
     * Using the condition above, we assume i = 0 to be properly sorted. So we start at i = 1.
     * Since the requirement of the problem states that the first index should be greater than
     * the second, and swaps after every index, we will keep track of a boolean to help us
     * determine how we should compare the previous value.
     * <p/>
     * Comparison 1 bool greater = true, i = 1: if nums[i] < nums[i - 1], then swap(nums[i], nums[i-1])
     * <p/>
     * Comparison 2 bool greater = false, i = 2: if nums[i] > nums[i - 1], then swap(nums[i], nums[i - 1])
     * <p/>
     * For comparisons beyond 2 we simply alternate between Comparison 1 and 2. Why does this work, though?
     * It seems like you could get in a situation where just looking at the last element and swapping
     * if it is incorrect could get you in situation where you couldn't properly complete the
     * requirements of WiggleSort as you got to the end of the list. We need to make sure
     * we aren't backing ourselves into a corner. However, let's remember our consideration above in that
     * everything up to index i is already properly sorted. That means that at index i, if it is less than,
     * and it should be greater than, swapping them can only further enforce the WiggleSort requirement.
     * This is because at index i, if it should be greater than, then at index i - 1, it should be less
     * than index i - 2. That means that if index i is less than index i - 1, then swapping them will
     * only ensure that i -1 is only ever going to decrease. This ensure that we will never break the
     * requirements of WiggleSort. Likewise, if we swap i - 1, with i when it should be less than,
     * but is actually greater than we can only ever increase the previous value. Thus we can greedily
     * swap the values with a single O(n) traversal over the list.
     * <p/>
     * Below is what the list above would look like after each comparison:
     * i = 1: [1, 2, 3, 4, 5, 6],
     * i = 2: [1, 3, 2, 4, 5, 6],
     * i = 3: [1, 3, 2, 4, 5, 6],
     * i = 4: [1, 3, 2, 4, 5, 6],
     * i = 5: [1, 3, 2, 5, 4, 6],
     * i = 6: [1, 3, 2, 5, 4, 6]
     * <p/>
     * With all of this in mind, here is my short solution to the problem:
     */
    public void wiggleSort2(int[] nums) {
        if (nums.length == 0) {
            return;
        }

        boolean greaterThan = true;
        for (int i = 1; i < nums.length; i++) {
            if ((greaterThan && nums[i] < nums[i - 1]) ||
                    (!greaterThan && nums[i] > nums[i - 1])) {
                int temp = nums[i];
                nums[i] = nums[i - 1];
                nums[i - 1] = temp;
            }

            greaterThan = !greaterThan;
        }
    }


    /**
     * save fast as above
     * Use {1,3,6,9,8,2} as input data.
     * Step 1: make each [even, odd] pair to be [small, large] --> {1,3,6,9,2,8}
     * Step 2: make each [odd, even] pair to be [large, small] --> {1,6,3,9,2,8}
     * Done!
     *
     * @param nums
     */
    public void wiggleSort3(int[] nums) {
        for (int i = 1; i < nums.length; i += 2)
            if (nums[i] < nums[i - 1]) swap(nums, i, i - 1);
        for (int i = 2; i < nums.length; i += 2)
            if (nums[i] > nums[i - 1]) swap(nums, i, i - 1);
    }

    public void swap(int[] a, int p, int q) {
        int t = a[p];
        a[p] = a[q];
        a[q] = t;
    }
}
