package codetop.a字符串;

import java.util.Arrays;

/**
 * @Description https://leetcode.cn/problems/two-sum/
 * @Author lishoupeng
 * @Date 2022/9/5 08:46
 */
public class 两数之和 {
    public static int[] twoSum(int[] nums, int target) {
        int[] ans = new int[]{};
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (target == nums[i] + nums[j]) {
                    return new int[]{nums[i], nums[j]};
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] result = new int[]{2, 7, 11, 15};
        int[] ans = twoSum(result, 9);
        System.out.println(Arrays.toString(ans));
    }
}
