package codetop.数组;

/**
 * @Description 定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * @LeetCodeURL https://leetcode-cn.com/problems/maximum-subarray/
 * @Author spli
 * @Date 2021/5/26 4:45 下午
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6。
 * 思路：假设数组长度为 n，下标从 0 ~ n-1
 * 用 f(i) 表示以第 i 个数结尾的「连续子数组的最大和」，我们只需要求出每个位置的 f(i)，然后返回 f 数组中的最大值即可，那么如何得到 f(i)
 * 我们可以考虑 num[i] 单独成为一段还是加入前面 f(i-1) 的那一段，这取决于 nums[i] 和 f(i-1)+nums[i] 的大小，我们希望得到一个比较大的：
 * f(i) = max{f(i-1)+nums[i] , nums[i]}
 * 即用一个 f 数组保存 f(i) 的值，用一个循环求出所有 f(i)。
 * 翻译过来：从头往后加，当你前面的数加当前数还不如当前数大的时候，当前最大值就是当前数，然后当前数再往后遍历
 */
public class 最大子序集合 {

    public int maxSubArray(int[] nums) {
        int pre = 0;
        int result = nums[0];
        for (int x : nums) {
            pre = Math.max(pre + x, x);
            result = Math.max(result, pre);
        }
        return result;
    }

    public static void main(String[] args) {
        最大子序集合 t = new 最大子序集合();
        int[] arr = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int result = t.maxSubArray(arr);
        System.out.println(result);
    }
}
