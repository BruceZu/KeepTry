package sort;

public class Leetcode75SortColors {
    private void swap(int[] arr, int i, int j) {
        if (arr[i] != arr[j]) {
            arr[i] ^= arr[j];
            arr[j] ^= arr[i];
            arr[i] ^= arr[j];
        }
    }

    /*---------------------------------------------------------------------------------*/
    public void sortColors(int[] nums) {
        int left = 0, r = nums.length - 1;
        int i = 0;
        while (i <= r) { // care  <=
            if (nums[i] == 0) {
                swap(nums, i++, left++);
            } else if (nums[i] == 1) {
                i++;
            } else {
                swap(nums, i, r--); // care --
            }
        }
    }
}
