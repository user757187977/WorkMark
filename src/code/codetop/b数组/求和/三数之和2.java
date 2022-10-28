package code.codetop.b数组.求和;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description https://leetcode.cn/problems/3sum/
 * @Author lishoupeng
 * @Date 2022/9/14 08:33
 */
public class 三数之和2 {

    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> asw = new ArrayList<>();
        Arrays.sort(nums);
        int length = nums.length;
        for (int i = 0; i < nums.length; i++) {
            int left = i + 1;
            int right = length - 1;
            while (left < right) {
                if (nums[i] + nums[left] + nums[right] == 0) {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(nums[i]);
                    temp.add(nums[left]);
                    temp.add(nums[right]);
                    asw.add(temp);
                }
                left++;
                right--;
            }
        }
        return asw;
    }

    public static void main(String[] args) {
        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
    }

}
