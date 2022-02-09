package codetop.b数组;

/**
 * 输入：[7,1,5,3,6,4]
 * 输出：5
 * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
 */
public class 买卖股票的最佳时机 {

    public static int test(int[] arr) {
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if ((arr[j] - arr[i]) > result) {
                    result = arr[j] - arr[i];
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(test(new int[]{7, 1, 5, 3, 6, 4}));
    }
}
