package sort;

import java.util.Arrays;

public class Leetcode324WinggleSortII3 {
    public void wiggleSort2(int[] nums) {
        Arrays.sort(nums); // O(NlgN)
        int n = nums.length, mid = n % 2 == 0 ? n / 2 - 1 : n / 2;
        int[] copy = Arrays.copyOf(nums, n); // O(N) extra space
        int index = 0;
        for (int i = 0; i <= mid; i++) {
            nums[index] = copy[mid - i];
            if (index + 1 < n) {
                nums[index + 1] = copy[n - i - 1];
            }
            index += 2;
        }
    }

    public void wiggleSort(int[] nums) {
        Arrays.sort(nums); // O(NlgN)
        int n = nums.length, mid = n % 2 == 0 ? n / 2 - 1 : n / 2;
        int[] copy = Arrays.copyOf(nums, n); // O(N) extra space
        int index = 0;
        for (int i = 0; i <= mid; i++) {
           // todo simply it
        }
    }
}
