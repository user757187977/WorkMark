package code.codetop.b数组.求和;

/**
 * @Description
 * @Author lishoupeng
 * @Date 2022/9/1 10:13
 */
public class 最大子序和2 {
    public static int maxSubArray(int[] nums) {
        int history_max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] > 0) {
                nums[i] += nums[i - 1];
            }
            if (nums[i] > history_max) {
                history_max = nums[i];
            }
        }
        return history_max;
    }

    public static void main(String[] args) {
        int re = maxSubArray(new int[]{100, -3, 4});
        System.out.println(re);
    }
}
