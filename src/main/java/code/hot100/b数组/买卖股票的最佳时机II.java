package code.hot100.b数组;

/**
 * @Description https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-ii/
 * @Author lishoupeng
 * @Date 2023/1/27 21:32
 * 输入：prices = [7,1,5,3,6,4]
 * 输出：7
 * 解释：第二天买入, 第三天卖出, 第四天买入, 第五天卖出, (5-1) + (6-3) = 7
 */
public class 买卖股票的最佳时机II {

    public static int maxProfit(int[] prices) {
        int asw = 0;
        for (int i = 1; i < prices.length - 1; i++) {
            int cur = prices[i];
            int pre = prices[i - 1];
            int up = cur - pre;
            if (up > 0) {
                asw += up;
            }
        }
        return asw;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7, 1, 5, 3, 6, 4};
        System.out.println(maxProfit(arr));
    }
}
