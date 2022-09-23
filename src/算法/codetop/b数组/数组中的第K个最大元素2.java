package 算法.codetop.b数组;

/**
 * @Description
 * @Author lishoupeng
 * @Date 2022/9/9 08:20
 */
public class 数组中的第K个最大元素2 {

    public static int findKthLargest(int[] nums, int k) {
        int asw = nums[0];
        for (int i = 0; i < k; i++) {
            int j_temp = 0;
            for (int j = 0; j < nums.length; j++) {
                if (nums[j] > asw) {
                    asw = nums[j];
                    j_temp = j;
                }
            }
            nums[j_temp] = Integer.MIN_VALUE;
        }
        return asw;
    }

    public static void main(String[] args) {
        System.out.println(findKthLargest(new int[]{1, 4, 6, 2}, 2));
    }
}
