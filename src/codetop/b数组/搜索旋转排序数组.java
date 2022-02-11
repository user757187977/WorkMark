package codetop.b数组;

/**
 * @Description
 * @Author spli
 * @Date 2022/2/9 09:40
 * 给你一个原本有序的数组并进行旋转后的数组, 比如: [0,1,2,4,5,6,7] 在下标 3 处旋转, 得到 [4,5,6,7,0,1,2]
 * 从这个旋转后的数组中, 找到目标值, 返回下标
 * 直接遍历数组, 找到目标, 即可
 * 进阶: 要求时间复杂度 O(log n)
 * 其实这题的目的就是让你二分查找
 * 思路: [4,5,6,7,0,1,2] 二分了, 必然得到一个有序的数组和一个无序的数组, 有序数组进行二分查找, 无序数组递归进行二分
 */
public class 搜索旋转排序数组 {

    public static int test(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int midValue = nums[mid];
            int leftValue = nums[left];
            int rightValue = nums[right];
            if (midValue == target) {
                return mid;
            } else if (midValue < rightValue) { // 先要知道你要扫的二分数组的左边还是右边
                if (midValue < target && target <= rightValue) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {
                if (leftValue <= target && target < midValue) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{4, 5, 6, 7, 0, 1, 2};
        System.out.println(test(nums, 2));
    }

}
