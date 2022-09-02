package codetop.b数组.合并;

import java.util.Arrays;

/**
 * @Description
 * @Author spli
 * @Date 2021/5/18 10:15 上午
 * 给你两个数组, 每个数组一个数字, 表示这个数组有效元素个数, 合并这连个数组
 * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * 输出：[1,2,2,3,5,6]
 * 解释：需要合并 [1,2,3] 和 [2,5,6] 。
 */
public class 合并两个有序数组 {


    // 方法 1, 先把两个数组合并到一起, 再排序
    public static void test1(int[] nums1, int m, int[] nums2, int n) {
        for (int i = 0; i < n; i++) {
            nums1[m + i] = nums2[i];
        }
        // 排序简写, 有空单独写数组排序的几种方式
        Arrays.sort(nums1);
    }

    // 方法 2, 双指针, 注意: 两个数组一定要先排序好
    public static void test2(int[] nums1, int m, int[] nums2, int n) {
        int   p1     = 0;
        int   p2     = 0;
        int[] result = new int[m + n];
        int   temp;
        while (p1 < m || p2 < n) {
            if (p1 == m) {
                temp = nums2[p2++];
            } else if (p2 == n) {
                temp = nums1[p1++];
            } else if (nums1[p1] < nums2[p2]) {
                temp = nums1[p1++];
            } else {
                temp = nums2[p2++];
            }
            result[p1 + p2 - 1] = temp;
        }
        for (int i = 0; i < m + n; i++) {
            nums1[i] = result[i];
        }
    }

    public static void main(String[] args) {
        test1(new int[]{1, 2, 3, 0, 0, 0}, 3, new int[]{2, 5, 6}, 3);
        test2(new int[]{1, 2, 3, 0, 0, 0}, 3, new int[]{2, 5, 6}, 3);
    }
}
