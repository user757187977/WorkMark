package codetop.数组;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @Description 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ? 找出所有和为 0 且不重复的三元组。
 * @LeetCodeURL https://leetcode-cn.com/problems/3sum/
 * @Author spli
 * @Date 2021/5/28 10:16 上午
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 */
public class 三数之和 {

    public static void main(String[] args) {
        三数之和 test = new 三数之和();
        int[] arr = new int[]{-1, 0, 1, 2, -1, -4};
        //int[] arr = new int[]{-2,0,0,2,2};
        System.out.println(test.threeSum(arr));
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> lists = new ArrayList<>();
        Arrays.sort(nums);//排序{-4,-1,-1,0,1,2}
        int len = nums.length;
        for (int i = 0; i < len; ++i) {
            if (nums[i] > 0) {
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int curr = nums[i];
            int L = i + 1, R = len - 1;
            while (L < R) {
                int tmp = curr + nums[L] + nums[R];
                if (tmp == 0) {
                    List<Integer> list = new ArrayList<>();
                    list.add(curr);
                    list.add(nums[L]);
                    list.add(nums[R]);
                    lists.add(list);
                    ++L;
                    --R;
                } else if (tmp < 0) {
                    ++L;
                } else {
                    --R;
                }
            }
        }
        //用于处理去重
        List<List<Integer>> result = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        for (List<Integer> integers : lists) {
            String key = integers.toString();
            if (!hashMap.containsKey(key)) {
                hashMap.put(key, "1");
                result.add(integers);
            }
        }
        return result;
    }


}
