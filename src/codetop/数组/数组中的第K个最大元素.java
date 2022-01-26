package codetop.数组;

import java.util.Random;

/**
 * @Description 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 * @LeetCodeURL https://leetcode-cn.com/problems/kth-largest-element-in-an-array/
 * @Author spli
 * @Date 2021/5/12 9:58 上午
 */
public class 数组中的第K个最大元素 {

    Random random = new Random();

    /**
     * 思路就是快速排序+取值
     * 快速排序的步骤：
     * 分解：将数组 arr=a[l...r] 分成两个数组：arr1=a[l...q-1]、arr2=a[q+1...r]，这两个数组的特点就是 arr1 中的全部元素 ≤ a[q] ≤ arr2 中的全部元素；
     * 解决：通过递归(再把 arr1 和 arr2 拆分得到 arr11=a[l...x-1]、arr12=a[x+1...q-1]，同理 arr2...)调用快速排序，对数组 arr1 和 arr2 进行排序
     * 合并：因为 arr1 和 arr2 都是原址排序的，所以不需要合并操作，arr 已经有序
     * 取值：
     * 本题目其实与只是排序的算法题并不完全一样的点在于，我们是要取第 K 个最大，也就是说这个数组我们没必要排序完全，我们只关心取到第 K 个即可。
     * 因此我们本题做点改进：在分解过程中，如果划分得到的 q 正好是我们需要的下标，就直接返回 a[q]；否则，如果 q 比目标下标小，就递归右子区间...
     * 那我们把 快速排序+选择 合并成一个新的算法名字，叫做：「快速选择」算法
     *
     * @param nums 数组
     * @param k    目标下标
     * @return 目标下标对应的值
     */
    public int findKthLargest1(int[] nums, int k) {
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    // 快速选择
    public int quickSelect(int[] arr, int leftindex, int rightindex, int index) {
        int q = randomPartition(arr, leftindex, rightindex);
        if (q == index) {
            return arr[q];
        } else if (q < index) {
            return quickSelect(arr, q + 1, rightindex, index);
        } else {
            return quickSelect(arr, leftindex, q - 1, index);
        }
    }

    // 随机分区
    public int randomPartition(int[] a, int leftindex, int rightindex) {
        int i = random.nextInt(rightindex - leftindex + 1) + leftindex;
        swap(a, i, rightindex);
        return partition(a, leftindex, rightindex);
    }

    public int partition(int[] a, int l, int r) {
        int x = a[r];
        int i = l - 1;
        for (int j = l; j < r; ++j) {
            if (a[j] <= x) {
                swap(a, ++i, j);
            }
        }
        swap(a, i + 1, r);
        return i + 1;
    }

    public void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }


    /**
     * 传统的快速排序+选择
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest2(int[] nums, int k) {
        quicksort(nums, 0, nums.length - 1);
        return nums[nums.length - k];
    }

    public void quicksort(int[] arr, int left, int right) {
        int L, R, swapLR, referenceValue, K;
        if (left > right) {
            return;
        }
        referenceValue = arr[left];
        L = left;
        R = right;
        while (L != R) {
            while (arr[R] >= referenceValue && L < R) {
                R--;
            }
            while (arr[L] <= referenceValue && L < R) {
                L++;
            }
            if (L < R) {
                swapLR = arr[L];
                arr[L] = arr[R];
                arr[R] = swapLR;
            }
        }
        K = L;
        arr[left] = arr[L];
        arr[K] = referenceValue;
        //继续处理左边的，这里是一个递归的过程
        quicksort(arr, left, K - 1);
        //继续处理右边的 ，这里是一个递归的过程
        quicksort(arr, K + 1, right);
    }

    public static void main(String[] args) {
        数组中的第K个最大元素 t = new 数组中的第K个最大元素();
        int[] arr = new int[]{2, 1, 3, 5, 4, 0};
        //System.out.println(t.findKthLargest1(arr, 4));
        System.out.println(t.findKthLargest2(arr, 2));
    }
}
