package sort;

import java.util.Arrays;

public class Leetcode324WinggleSortII3 {
    public void wiggleSort(int[] nums) {
        Arrays.sort(nums); // O(NlgN)
        int n = nums.length, mid = n % 2 == 0 ? n / 2 - 1 : n / 2;
        int[] temp = Arrays.copyOf(nums, n); // O(N) extra space
        int index = 0;
        for (int i = 0; i <= mid; i++) {
            nums[index] = temp[mid - i];
            if (index + 1 < n)
                nums[index + 1] = temp[n - i - 1];
            index += 2;
        }
    }
}
