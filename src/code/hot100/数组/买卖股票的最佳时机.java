package code.hot100.数组;

/**
 * @Description https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/
 * @Author lishoupeng
 * @Date 2022/9/19 09:11
 * 输入: [7,1,5,3,6,4]
 * 输出: 5
 * 解释: 第 2 天(股票价格: 1)买入, 第 5 天(股票价格: 6)卖出, 最大利润: 6-1 = 5.
 */
public class 买卖股票的最佳时机 {

    // 暴力解法
    public static int maxProfit(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int maxTemp = nums[j] - nums[i];
                max = Math.max(maxTemp, max);
            }
        }
        return max;
    }

    // 单次遍历解法: 这个数组中的最大值 - 这个数组中的最小值, 就是最大差值, 就是股票的最大利润
    // 只不过要注意一点, 这个最大值的下标 > 最小值的下标
    public static int maxProfit2(int[] nums) {
        int min = nums[0], max = 0;
        for (int i = 1; i < nums.length; i++) {
            max = Math.max(max, nums[i] - min);
            min = Math.min(min, nums[i]);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(maxProfit2(new int[]{7, 1, 5, 3, 6, 4}));
    }
}
