package code.hot100.数组;

/**
 * @Description https://leetcode.cn/problems/trapping-rain-water/
 * @Author lishoupeng
 * @Date 2022/10/28 08:41
 */
public class 接雨水 {

    public static int trap(int[] arr) {
        int sum = 0;
        int maxHeight = 0;
        for (int i : arr) if (i > maxHeight) maxHeight = i;
        for (int curHeight = 1; curHeight <= maxHeight; curHeight++) {
            boolean isStart = false;
            int sumTemp = 0;
            for (int i : arr) {
                if (isStart && i < curHeight) {
                    sumTemp++;
                }
                if (i >= curHeight) {
                    sum += sumTemp;
                    sumTemp = 0;
                    isStart = true;
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
    }

}
