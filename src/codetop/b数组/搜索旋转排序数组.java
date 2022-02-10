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
 * 思路: 把 [4,5,6,7,0,1,2] 二分, 其中必然有一个是有序的, 另外一个是部分有序,
 * 然后有序部分用二分查找, 无序部分再一分二, 其中又会出现一个有序 另外一个部分有序, 一次类推
 */
public class 搜索旋转排序数组 {

    public static int test(int[] nums, int target, int left, int right) {
        if (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                test(nums, target, left, mid - 1);
            } else if (nums[mid] < target) {
                test(nums, target, mid + 1, right);
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{4, 5, 6, 7, 0, 1, 2};
        System.out.println(test(nums, 0, 0, nums.length - 1));
    }

}
