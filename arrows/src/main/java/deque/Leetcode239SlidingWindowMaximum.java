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
 * Hide Similar Problems
 * (H) Minimum Window Substring
 * (E) Min Stack
 * (H) Longest Substring with At Most Two Distinct Characters
 * (H) Paint House II
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
    public int[] maxSlidingWindow(int[] nums, int k) {
        // corner case check
        if (nums == null || nums.length == 0) {
            return nums;
        }
        int[] r = new int[nums.length - k + 1];
        Deque<Integer> q = new ArrayDeque(k);
        for (int i = 0; i < nums.length; i++) {
            if (!q.isEmpty() && q.peek() < i - (k - 1)) {
                q.poll();
            }

            while (!q.isEmpty() && nums[q.peekLast()] < nums[i]) {
                q.pollLast();
            }
            q.offerLast(i); // keep index
            if (i >= k - 1) {
                r[i - (k - 1)] = nums[q.peek()]; // value
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
        Deque<Integer> q = new ArrayDeque(k);
        for (int i = 0; i < nums.length; i++) {
            if (!q.isEmpty() && q.peek() < i - (k - 1)) {
                q.poll();
            }

            while (!q.isEmpty() && nums[q.peekLast()] > nums[i]) { // only update here
                q.pollLast();
            }
            q.offerLast(i); // keep index
            if (i >= k - 1) {
                r[i - (k - 1)] = nums[q.peek()]; // value
            }
        }
        return r;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(minSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 8)));
    }
}

