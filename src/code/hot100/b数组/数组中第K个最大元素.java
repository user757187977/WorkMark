package code.hot100.b数组;

/**
 * @Description https://leetcode.cn/problems/kth-largest-element-in-an-array/
 * @Author lishoupeng
 * @Date 2022/11/15 08:28
 */
public class 数组中第K个最大元素 {

    public static int findKthLargest(int[] nums, int k) {
        selectSort(nums);
        return nums[nums.length - k];
    }

    public static void selectSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            // 向后遍历得到最小的值的下标
            int min = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[min]) min = j;
            }
            // 替换
            int temp = nums[i];
            nums[i] = nums[min];
            nums[min] = temp;
        }
    }

    public static void main(String[] args) {
        System.out.println(findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
    }
}
