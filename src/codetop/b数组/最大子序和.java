package codetop.b数组;

/**
 * @Description
 * @Author spli
 * @Date 2022/2/8 09:39
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 */
public class 最大子序和 {

    public static int maxSubArray(int[] nums) {
        int res = nums[0];
        int sum = 0;
        for (int num : nums) {
            if (sum > 0)
                sum += num;
            else
                sum = num;
            res = Math.max(res, sum);
        }
        return res;
    }

    public static void main(String[] args) {
        int re = maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4});
        System.out.println(re);
    }

}
