package code.codetop.b数组.求和;

/**
 * @Description https://leetcode.cn/problems/maximum-subarray/
 * @Author spli
 * @Date 2022/2/8 09:39
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 * 思路
 */
public class 最大子序和 {

    public static int maxSubArray(int[] nums) {
        if (nums.length == 0) return 0;
        int history_max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] > 0) {
                nums[i] += nums[i - 1];
            } else {
                nums[i] = nums[i];
            }
            history_max = Math.max(nums[i], history_max);
        }

        return history_max;
    }

    public static void main(String[] args) {
        int re = maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
        System.out.println(re);
    }

}
