package hot100.数组;

/**
 * @Description https://leetcode.cn/problems/maximum-subarray/comments/
 * @Author lishoupeng
 * @Date 2022/9/19 08:32
 */
public class 最大自序和 {

    public static int maxSubArray(int[] nums) {
        int pre = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            pre = Math.max(pre + nums[i], nums[i]);
            max = Math.max(max, pre);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }
}
