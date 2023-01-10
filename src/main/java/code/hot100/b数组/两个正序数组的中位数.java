package code.hot100.b数组;

import java.util.Arrays;

/**
 * @Description https://leetcode.cn/problems/median-of-two-sorted-arrays/
 * @Author lishoupeng
 * @Date 2022/10/28 09:00
 */
public class 两个正序数组的中位数 {

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        double asw;
        int m = nums1.length;
        int n = nums2.length;
        int[] arr = new int[m + n];
        for (int mTemp = 0; mTemp < m; mTemp++) {
            arr[mTemp] = nums1[mTemp];
        }
        for (int nTemp = 0; nTemp < n; nTemp++) {
            arr[nTemp + n] = nums2[nTemp];
        }
        Arrays.sort(arr);
        if (arr.length % 2 == 0) {
            asw = arr[(arr.length / 2) - 1];
        } else {
            asw = arr[arr.length / 2];
        }
        return asw;

    }


    public static void main(String[] args) {
        System.out.println(findMedianSortedArrays(new int[]{1, 3}, new int[]{2, 4}));
    }
}
