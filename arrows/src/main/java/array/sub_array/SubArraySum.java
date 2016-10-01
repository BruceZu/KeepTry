package array.sub_array;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class SubArraySum {
    /**
     * [2,0,4,-3,-5] -> -8 true
     * [3,1,5] ->8  false
     * [1,2,1,5] ->8  true
     * <pre>
     * integer array.
     * is there is sub array whose sum is equal a given integer.
     *
     * require
     * O(N) or O (logN)
     * follow up:
     *  target = 0
     *
     * Return true in following cases
     *   a) Current element is 0
     *   b) sum of elements from 0 to i is 0
     *   c) sum is already present in hash map
     */

    // only applicable to NO Negative array.
    // like a queue, and the queue can be replaced by a pointer.
    public static boolean haveONPositiveNumberOnly(int[] arr, int sum) {
        Queue<Integer> que = new ArrayDeque();
        int curSum = 0;
        for (int i = 0; i < arr.length; i++) {
            // 1 add
            curSum += arr[i];
            que.offer(arr[i]);

            // check
            if (curSum == sum) {
                return true;
            }
            if (curSum > sum) {
                while (curSum > sum && !que.isEmpty()) {
                    int leftSide = que.poll();
                    curSum -= leftSide;
                    if (curSum == sum) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean haveON2(int[] arr, int target) {
        Set<Integer> sums = new HashSet<>();
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (sum == target) {
                return true;
            }
            sums.add(sum);

            if (sums.contains(sum - target)) {
                return true;
            }
        }
        return false;
    }

    public static boolean haveLogN(int[] arr, int sum) {
        //// TODO: 9/24/16
        return false;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2, 0, 4, -3, -5};
        int given = -8;

        System.out.println(haveON2(arr, given));

        arr = new int[]{3, 1, 5};
        given = 8;
        System.out.println(haveON2(arr, given));

        arr = new int[]{1, 2, 1, 5};
        System.out.println(haveON2(arr, given));

        arr = new int[]{1};
        given = 1;
        System.out.println(haveON2(arr, given));

        arr = new int[]{10, 2, -2, -20, 10};
        given = -10;
        System.out.println(haveON2(arr, given));
    }
}
