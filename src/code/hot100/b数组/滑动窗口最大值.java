package code.hot100.b数组;

import java.util.Arrays;

/**
 * @Description
 * @Author lishoupeng
 * @Date 2022/11/1 08:41
 */
public class 滑动窗口最大值 {
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int[] asw = new int[nums.length - k + 1];
        int[][] allWindow = new int[nums.length - k + 1][k];
        for (int i = 0; i <= nums.length - k; i++) {
            int[] allWindowTemp = new int[k];
            for (int j = 0; j < k; j++) {
                allWindowTemp[j] = nums[i + j];
            }
            allWindow[i] = allWindowTemp;
        }
        int tempMax = 0;
        for (int i = 0; i < allWindow.length; i++) {
            int temp = allWindow[i][0];
            for (int j = 0; j < allWindow[i].length; j++) {
                temp = Math.max(allWindow[i][j], temp);
            }
            asw[i] = Math.max(tempMax, temp);
            tempMax = Math.max(tempMax, temp);
        }
        return asw;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3)));
    }
}
