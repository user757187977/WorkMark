package code.hot100.b数组;

import java.util.Arrays;

/**
 * @Description
 * @Author lishoupeng
 * @Date 2022/9/19 08:32
 */
public class 两数之和 {

    public static int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum(new int[]{1, 2}, 3)));
    }
}
