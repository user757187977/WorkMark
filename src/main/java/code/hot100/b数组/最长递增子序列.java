package code.hot100.b数组;

/**
 * @Description https://leetcode.cn/problems/longest-increasing-subsequence/
 * @Author lishoupeng
 * @Date 2023/1/30 08:27
 * 输入：nums = [10,9,2,5,3,7,101,18]
 * 输出：4
 * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
 */
public class 最长递增子序列 {

    public static int lengthOfLIS(int[] nums) {
        int asw = 1;
        int[] dp = new int[nums.length];
        for (int right = 0; right < nums.length; right++) {
            dp[right] = 1;
            for (int left = 0; left < right; left++) {
                if (nums[left] < nums[right]) {
                    dp[right] = Math.max(dp[right], dp[left] + 1);
                }
            }
            asw = Math.max(asw, dp[right]);
        }
        return asw;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println(lengthOfLIS(arr));
    }

}
